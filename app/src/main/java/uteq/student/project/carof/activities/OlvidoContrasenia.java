package uteq.student.project.carof.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import uteq.student.project.carof.R;

public class OlvidoContrasenia extends AppCompatActivity {
    MaterialButton recuperar;
    TextInputEditText emaileditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_contrasenia);
        recuperar = findViewById(R.id.btnRecuperarC);
        emaileditText = findViewById(R.id.Reemail);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    public void validate() {
        String email = emaileditText.getText().toString().trim();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emaileditText.setError("El correo no exitse");
            return;
        }
        sendEmail(email);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OlvidoContrasenia.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
    public  void sendEmail(String email)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(OlvidoContrasenia.this,"El correo fue enviado",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(OlvidoContrasenia.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(OlvidoContrasenia.this,"Correo invalido",Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

}