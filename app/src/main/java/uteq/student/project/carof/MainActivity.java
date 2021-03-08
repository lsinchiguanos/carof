package uteq.student.project.carof;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import uteq.student.project.carof.activities.MonitoreoActivity;
import uteq.student.project.carof.fragments.MenuFragment;
import uteq.student.project.carof.fragments.VehiculoDesFragment;
import uteq.student.project.carof.fragments.VehiculosFragmentv1;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, MenuFragment.OnFragmentInteractionListener, VehiculosFragmentv1.OnFragmentInteractionListener {

    private Fragment fragmentMenu, fragmentVehiculo, fragmentDesVehiculo;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String emailUser, id_duenio;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    ImageView imgAuto;
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailUser = getIntent().getExtras().getString("email");
        id_duenio = getIntent().getExtras().getString("id_duenio");
        fragmentMenu = new MenuFragment();
        fragmentVehiculo = new VehiculosFragmentv1();
        fragmentDesVehiculo = new VehiculoDesFragment();
        preferences = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("email", emailUser);
        editor.putString("id_duenio", id_duenio);
        editor.apply();
        imgAuto = findViewById(R.id.imgAuto);
        b.putString("id_duenio", id_duenio);
        fragmentMenu.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentMenu).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void vehiculo(String id) {
        b.putString("id_duenio", id);
        fragmentVehiculo.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentVehiculo).commit();
    }

    @Override
    public void addvehiculo(String id) {
        b.putString("id_duenio", id);
        fragmentDesVehiculo.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentDesVehiculo).commit();
    }

    @Override
    public void editvehiculo(String id_vehiculo, String id_duenio) {
        b.putString("id_vehiculo", id_vehiculo);
        b.putString("id_duenio", id_duenio);
        fragmentDesVehiculo.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentDesVehiculo).commit();
    }

    @Override
    public void contratos() {

    }

    @Override
    public void monitoreo() {
        Intent intent = new Intent(MainActivity.this, MonitoreoActivity.class);
        intent.putExtra("id_duenio", id_duenio);
        startActivity(intent);
    }

    @Override
    public void historial() {

    }

    @Override
    public void publicacion() {

    }


    @Override
    public void informacion() {

    }

    @Override
    public void dispositivoGps() {

    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public void signUp() {
        FirebaseAuth.getInstance().signOut();
        preferences = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        onBackPressed();
    }

}