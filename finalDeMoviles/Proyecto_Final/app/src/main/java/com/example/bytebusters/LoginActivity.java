package com.example.bytebusters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Intent acceder;
    Intent registrar;

    EditText usuario,correo,contraseña;
    Button logearse;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String usuario1 ="", correo1 = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*NO TOCAR*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /**/
        usuario = findViewById(R.id.ingresarusuario);
        correo = findViewById(R.id.ingresarmail);
        contraseña = findViewById(R.id.contrasena);
        logearse = findViewById(R.id.iniciarbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("espere porfavor");
        progressDialog.setCanceledOnTouchOutside(false);

        logearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });
        //

        MaterialButton registrarbtn = (MaterialButton) findViewById(R.id.registrarbtn);

        registrarbtn.setOnClickListener(new View.OnClickListener() {//REGISTRARSE
            @Override
            public void onClick(View v) {
                registrar = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(registrar);
            }
        });


    }


    private void ValidarDatos() {
        usuario1 =usuario.getText().toString();
        correo1 = correo.getText().toString();
        password = contraseña.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(correo1).matches()){
            Toast.makeText( LoginActivity.this,  "CORREO NO ACEPTABLE",Toast.LENGTH_SHORT).show();

        } else if(usuario1.isEmpty() || correo1.isEmpty() || password.isEmpty()){
                Toast.makeText( LoginActivity.this,  "CAMPOS NO COMPLETADOS",Toast.LENGTH_SHORT).show();
        }else{
            LoginDeUsuario(usuario1);
        }

    }

    private void LoginDeUsuario(String usuario1) {
        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo1,password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            if(user.getEmail().equals("correo1@correo.com")){
                                acceder = new Intent(LoginActivity.this,ClienteActivity.class);
                            } else if (user.getEmail().equals("correo2@correo.com")) {
                                acceder = new Intent(LoginActivity.this,EmpleadoActivity.class);
                            }
                            acceder.putExtra("nombre",usuario1);
                            startActivity(acceder);

                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText( LoginActivity.this,  "Credenciales incorrectas",Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( LoginActivity.this,  " "+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
