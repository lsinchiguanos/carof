package uteq.student.project.carof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
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
    private String emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailUser = getIntent().getExtras().getString("email");
        fragmentMenu = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentMenu).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void vehiculo() {
        intent = new Intent(this, VehiculoActivity.class);
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

    @Override
    public void signUp() {
        FirebaseAuth.getInstance().signOut();
        onBackPressed();
    }
}