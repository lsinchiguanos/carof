package uteq.student.project.carof.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import uteq.student.project.carof.R;
import uteq.student.project.carof.fragments.VehiculosFragment;

public class VehiculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new VehiculosFragment()).commit();
    }
}