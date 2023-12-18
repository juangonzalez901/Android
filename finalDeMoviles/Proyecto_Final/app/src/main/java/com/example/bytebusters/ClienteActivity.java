package com.example.bytebusters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ClienteActivity extends AppCompatActivity {
    String titulo;

ImageButton opcion4;
    ImageButton CerrarSesion;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);


//NOMBRE DE USUARIO
        Intent recibir = getIntent();
        titulo = recibir.getStringExtra("nombre");
        TextView titulousuario = findViewById(R.id.usuario);
        titulousuario.setText(titulo);
//
        opcion4 = findViewById(R.id.opcion4);

        opcion4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClienteActivity.this, AtencionClienteActivity.class));
            }
        });

        CerrarSesion = (ImageButton) findViewById(R.id.cerrarbtn);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(ClienteActivity.this, LoginActivity.class));
        Toast.makeText( ClienteActivity.this, "Cerraste sesion",Toast.LENGTH_SHORT).show();
    }
}