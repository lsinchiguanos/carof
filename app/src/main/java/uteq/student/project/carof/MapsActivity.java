package uteq.student.project.carof;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

import uteq.student.project.carof.models.VehiculoModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Spinner spinner;
    private Button button;
    private GoogleMap mMap;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private Task<QuerySnapshot> query;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

}