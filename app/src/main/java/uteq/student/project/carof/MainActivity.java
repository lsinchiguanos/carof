package uteq.student.project.carof;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import uteq.student.project.carof.fragments.MenuFragment;
import uteq.student.project.carof.fragments.PublicacionFragment;
import uteq.student.project.carof.fragments.PublicacionesFragmenttv1;
import uteq.student.project.carof.fragments.VehiculoDesFragment;
import uteq.student.project.carof.fragments.VehiculosFragmentv1;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, MenuFragment.OnFragmentInteractionListener, VehiculosFragmentv1.OnFragmentInteractionListener, PublicacionFragment.OnFragmentInteractionListener{

    private Fragment fragmentMenu, fragmentVehiculo, fragmentDesVehiculo, fragmentPublicaciones, fragmentNewPublicacion;

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

        fragmentPublicaciones = new PublicacionesFragmenttv1();
        fragmentNewPublicacion = new PublicacionFragment();

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
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra("id_duenio", id_duenio);
        startActivity(intent);
    }

    @Override
    public void publicacion(String id) {
        b.putString("id_duenio", id);
        fragmentPublicaciones.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentPublicaciones).commit();
    }

    @Override
    public void addpublicacion(String id) {
        b.putString("id_duenio", id);
        fragmentNewPublicacion.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentNewPublicacion).commit();
    }

    @Override
    public void editpublicacion(String id,String id_publi, String id_vehi) {
        b.putString("id_duenio", id);
        b.putString("id_publicacion", id_publi);
        b.putString("id_vehi", id_vehi);
        fragmentNewPublicacion.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentNewPublicacion).commit();
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