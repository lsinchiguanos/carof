package uteq.student.project.carof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import uteq.student.project.carof.R;
import uteq.student.project.carof.models.DuenioModel;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout tipoIdentificacion;
    private TextInputLayout apellidoMaterno;
    private TextInputLayout apellidoPaterno;
    private TextInputLayout nombres;
    private TextInputLayout email;
    private TextInputLayout identificacion;
    private TextInputLayout fechaNacimiento;
    private TextInputLayout userName;
    private TextInputLayout password;
    private TextInputLayout telefono;
    private TextInputLayout validapass;
    private DuenioModel duenioModel;
    private Intent intent;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        duenioModel = new DuenioModel();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.list_item);
        String[] items = new String[]{"RUC", "CEDULA"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpActivity.this, R.layout.item_select, items);
        autoCompleteTextView.setAdapter(adapter);
        settingcontrollers();
    }

    private void settingcontrollers(){
        password = findViewById(R.id.passTextEdit);
        validapass = findViewById(R.id.validpassTextEdit);
        tipoIdentificacion = findViewById(R.id.tipoIdentificacionTextEdit);
        identificacion = findViewById(R.id.identificacionTextEdit);
        apellidoMaterno = findViewById(R.id.apellidoMaternoTextEdit);
        apellidoPaterno = findViewById(R.id.apellidoPaternoTextEdit);
        nombres = findViewById(R.id.nombresTextEdit);
        fechaNacimiento = findViewById(R.id.fechaNacimientoTextEdit);
        telefono = findViewById(R.id.telefonoTextEdit);
        userName = findViewById(R.id.userNameTextEdit);
        email = findViewById(R.id.emailTextEdit);
    }

    private void getDataControllers(){
        String a = password.getEditText().getText().toString();
        String b = validapass.getEditText().getText().toString();
        if (a.equals(b)){
            duenioModel.setCedula(identificacion.getEditText().getText().toString());
            duenioModel.setTipo_identificacion(tipoIdentificacion.getEditText().getText().toString());
            duenioModel.setNombres(nombres.getEditText().getText().toString());
            duenioModel.setApellido_paterno(apellidoPaterno.getEditText().getText().toString());
            duenioModel.setApellido_materno(apellidoMaterno.getEditText().getText().toString());
            duenioModel.setTelefono(telefono.getEditText().getText().toString());
            duenioModel.setEmail(email.getEditText().getText().toString());
            duenioModel.setUser_name(userName.getEditText().getText().toString());
            duenioModel.setPassword(password.getEditText().getText().toString());
            duenioModel.setFecha_nacimiento(fechaNacimiento.getEditText().getText().toString());
        }
    }

    public void returnLoggin(View view) {
        intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void createUser(View view) {
        getDataControllers();
        firebaseAuth.createUserWithEmailAndPassword(duenioModel.getEmail(), duenioModel.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    duenioModel.setCreated_at(FieldValue.serverTimestamp());
                    db.collection("duenio").document(duenioModel.getCedula()).set(duenioModel);
                    Toast.makeText(SignUpActivity.this, "Registro existoso", Toast.LENGTH_SHORT).show();
                    intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}