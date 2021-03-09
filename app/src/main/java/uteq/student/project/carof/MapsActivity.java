package uteq.student.project.carof;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.util.ArrayList;
import java.util.Objects;

import uteq.student.project.carof.models.MonitoreoModel;
import uteq.student.project.carof.models.VehiculoModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Spinner spinner;
    private Button button;

    private CameraUpdate cameraUpdate;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    /*private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private Task<QuerySnapshot> query;*/
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
        init();
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

    private void part2(ArrayList<VehiculoModel> vehiculoModels) {
        ArrayAdapter<VehiculoModel> listAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, vehiculoModels);
        spinner.setAdapter(listAdapter);
    }

    public void load_points(View view) {
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