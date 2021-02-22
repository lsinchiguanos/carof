package uteq.student.project.carof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;

import uteq.student.project.carof.fragments.MenuFragment;
import uteq.student.project.carof.interfaces.IComunicacionFragments;

public class MainActivity extends AppCompatActivity implements IComunicacionFragments, MenuFragment.OnFragmentInteractionListener {

    Fragment fragmentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a = getIntent().getExtras().getString("email");
        String b = getIntent().getExtras().getString("uid");
        fragmentMenu = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragmentMenu).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void vehiculo() {

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
}