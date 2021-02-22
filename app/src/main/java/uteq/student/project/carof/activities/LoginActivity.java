package uteq.student.project.carof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import uteq.student.project.carof.MainActivity;
import uteq.student.project.carof.R;
import uteq.student.project.carof.models.DuenioModel;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout userName;
    private TextInputLayout password;
    private DuenioModel duenioModel;
    private Intent intent;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        duenioModel = new DuenioModel();
        settingcontrollers();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void settingcontrollers(){
        userName = findViewById(R.id.textUserName);
        password = findViewById(R.id.textPassword);
    }

    private void getDataControllers(){
        duenioModel.setEmail(Objects.requireNonNull(userName.getEditText()).getText().toString());
        duenioModel.setPassword(Objects.requireNonNull(password.getEditText()).getText().toString());
    }

    public void goToRegisterUser(View view){
        intent = new Intent(this, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void loginFirebase(View view) {
        getDataControllers();
        firebaseAuth.signInWithEmailAndPassword(duenioModel.getEmail(), duenioModel.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Login correcto", Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("email", task.getResult().getUser().getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}