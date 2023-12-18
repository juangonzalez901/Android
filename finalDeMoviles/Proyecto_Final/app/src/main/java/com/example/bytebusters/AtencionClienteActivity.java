package com.example.bytebusters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class AtencionClienteActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    EditText Reclamo,Asunto,Reclamante;
    Button btnEnviar;
    ProgressDialog progressDialog;
    Intent volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamar);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date fechaActual = new Date();

        String fechaFormateada = sdf.format(fechaActual);

        firebaseAuth = FirebaseAuth.getInstance();

        Reclamante = findViewById(R.id.reclamante);
        Asunto = findViewById(R.id.asunto);
        Reclamo = findViewById(R.id.reclamo);
        btnEnviar = findViewById(R.id.btnEnviar);

        Button atras = (Button) findViewById(R.id.volverR);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver = new Intent(getApplicationContext(),ClienteActivity.class);
                startActivity(volver);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reclamante = Reclamante.getText().toString().trim();
                String asunto= Asunto.getText().toString().trim();
                String reclamo = Reclamo.getText().toString().trim();


                if (reclamante.isEmpty() && asunto.isEmpty() && reclamo.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Campos obligatorios", Toast.LENGTH_SHORT).show();
                }
                else {
                    agregarReclamo(reclamante,asunto,reclamo,fechaFormateada);
                    Toast.makeText(AtencionClienteActivity.this, "Reclamo enviado", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void agregarReclamo(String reclamante, String asunto, String reclamo, String fechaFormateada) {
        String uid = firebaseAuth.getUid();
        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid",uid);

        Datos.put("Reclamador",reclamante);
        Datos.put("Asunto",asunto);
        Datos.put("Reclamo",reclamo);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("reclamos");

        databaseReference.child(fechaFormateada)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(),ClienteActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText( AtencionClienteActivity.this,  ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}