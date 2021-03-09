package uteq.student.project.carof;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

import uteq.student.project.carof.models.MonitoreoModel;
import uteq.student.project.carof.models.VehiculoModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Spinner spinner;
    private Button button;
    private Button btnOns;
    private Button btnOffs;
    private ListView listView;

    private CameraUpdate cameraUpdate;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;

    private MonitoreoModel model;

    private String id_duenio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        id_duenio = getIntent().getExtras().getString("id_duenio");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.list_Carros);
        button = findViewById(R.id.btnBuscar);
        btnOns = findViewById(R.id.btnOn);
        btnOffs = findViewById(R.id.btnOff);
        listView = findViewById(R.id.logMonitoreo);
        init();
        desbloquear();
        bloquear();
    }

    private void desbloquear() {
        btnOns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLogs(false);
                cargarMapa();
                recuperaLog();
            }
        });
    }

    private void bloquear() {
        btnOffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLogs(true);
                cargarMapa();
                recuperaLog();
            }
        });
    }

    private void countDownTime() {
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                onMapReady(mMap);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-1.0197, -79.500118), 15);
        mMap.moveCamera(cameraUpdate);
    }

    private void init() {
        ArrayList<VehiculoModel> vehiculoModels = new ArrayList<>();
        firebaseFirestore.collection("vehiculo")
                .whereEqualTo("duenio", id_duenio)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                                Log.d("ID", documentSnapshot.getId());
                                Log.d("PLACA", documentSnapshot.getString("placa"));
                                Log.d("ES", "");
                                String id_vehiculo = documentSnapshot.getId();
                                String placa = documentSnapshot.getString("placa");
                                vehiculoModels.add(new VehiculoModel(id_vehiculo, placa));
                            }
                            part2(vehiculoModels);
                        } else {
                            Log.d("ERROR FIREBASE", "Error al obtener documento: ", task.getException());
                        }
                    }
                });
    }

    private void registerLogs(boolean bloqueado_logs) {
        if (model.getEstado().equals("ENCENDIDO") && model.getVelocidad() >= 0) {
            Toast.makeText(this, "Carro en movimiento, acción no ejecutable", Toast.LENGTH_LONG).show();
        } else {
            VehiculoModel vehiculoModel = (VehiculoModel) spinner.getSelectedItem();
            documentReference = firebaseFirestore.collection("monitoreo_log").document(vehiculoModel.getId_vehiculo());
            model.setBloqueado(bloqueado_logs);
            model.setFecha(Timestamp.now());
            documentReference.collection("registros").add(model);
            Toast.makeText(this, "Acción ejecutada", Toast.LENGTH_LONG).show();
        }
    }

    private void recuperaLog() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        VehiculoModel vehiculoModel = (VehiculoModel) spinner.getSelectedItem();
        documentReference = firebaseFirestore.collection("monitoreo_log").document(vehiculoModel.getId_vehiculo());
        documentReference.collection("registros").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        boolean bloqueado_log = documentSnapshot.getBoolean("bloqueado_log");
                        String estado_log = documentSnapshot.getString("estado_log");
                        Timestamp fecha_log = documentSnapshot.getTimestamp("fecha_log");
                        double latitud_log = documentSnapshot.getDouble("latitud_log");
                        double longitud_log = documentSnapshot.getDouble("longitud_log");
                        double velocidad_log = documentSnapshot.getDouble("velocidad_log");
                        String cadena = "fecha: " + fecha_log.toDate() + "\n LatLong: [" + latitud_log + ", " + longitud_log + "]\n velocidad: " + velocidad_log + "\n estado: " + estado_log +" \n bloquedo: " + bloqueado_log + "\n";
                        stringArrayList.add(cadena);
                    }
                    part3(stringArrayList);
                } else {
                    Log.d("ERROR FIREBASE", "Error al obtener documento: ", task.getException());
                }
            }
        });
    }

    private void part2(ArrayList<VehiculoModel> vehiculoModels) {
        ArrayAdapter<VehiculoModel> listAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, vehiculoModels);
        spinner.setAdapter(listAdapter);
    }

    private void part3(ArrayList<String> vehiculoModels) {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vehiculoModels);
        listView.setAdapter(listAdapter);
    }

    public void load_points(View view) {
        cargarMapa();
        recuperaLog();
    }

    private void cargarMapa() {
        VehiculoModel vehiculoModel = (VehiculoModel) spinner.getSelectedItem();
        firebaseFirestore.collection("monitoreo").document(vehiculoModel.getId_vehiculo()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    mMap.clear();
                    String estado = value.getString("estado");
                    double latitud = value.getDouble("latitud");
                    double longitud = value.getDouble("longitud");
                    double velocidad = value.getDouble("velocidad");
                    boolean bloqueado = value.getBoolean("bloqueado");
                    model = new MonitoreoModel(estado, latitud, longitud, velocidad, bloqueado);
                    markerOptions = new MarkerOptions();
                    markerOptions.title(vehiculoModel.getPlaca());
                    markerOptions.position(new LatLng(latitud, longitud));
                    markerOptions.snippet(estado);
                    if (bloqueado == true){
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                    cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 18);
                    mMap.moveCamera(cameraUpdate);
                    mMap.addMarker(markerOptions);
                }
            }
        });
    }
}