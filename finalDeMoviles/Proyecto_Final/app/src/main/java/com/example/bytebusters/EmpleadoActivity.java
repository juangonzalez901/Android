package com.example.bytebusters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmpleadoActivity extends AppCompatActivity {
    String titulo;
    ImageButton CerrarSesion;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);
//NOMBRE DE USUARIO
        Intent recibir = getIntent();
        titulo = recibir.getStringExtra("nombre");
        TextView titulousuario = findViewById(R.id.usuario);
        titulousuario.setText(titulo);
//

        CerrarSesion = (ImageButton) findViewById(R.id.cerrarbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();



        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });

        ImageButton opcion3 = findViewById(R.id.opcion3);

        opcion3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LeerPedidoActivity.class));
            }
        });
    }
    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(EmpleadoActivity.this, LoginActivity.class));
        Toast.makeText( EmpleadoActivity.this, "Cerraste sesion",Toast.LENGTH_SHORT).show();
    }
}