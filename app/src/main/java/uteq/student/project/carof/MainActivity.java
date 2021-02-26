package uteq.student.project.carof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import uteq.student.project.carof.activities.LoginActivity;
import uteq.student.project.carof.activities.VehiculoActivity;
import uteq.student.project.carof.fragments.MenuFragment;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, MenuFragment.OnFragmentInteractionListener {

    private Fragment fragmentMenu;
    private Intent intent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private String emailUser,id_duenio;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailUser = getIntent().getExtras().getString("email");
        id_duenio = getIntent().getExtras().getString("id_duenio");
        fragmentMenu = new MenuFragment();
        preferences = getSharedPreferences(getString(R.string.preference), Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("email", emailUser);
        editor.putString("id_duenio", id_duenio);
        editor.apply();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentMenu).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void vehiculo(String id) {
        intent = new Intent(this, VehiculoActivity.class);
        b.putString("id_duenio",b.getString("id_duenio"));
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void contratos() {

    }

    @Override
    public void monitoreo() {

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