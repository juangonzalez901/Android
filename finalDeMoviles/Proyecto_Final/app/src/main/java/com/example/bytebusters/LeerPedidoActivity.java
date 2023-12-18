package com.example.bytebusters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LeerPedidoActivity extends AppCompatActivity {

    ListView listViewPedidos;
    ArrayList<String> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_pedido);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("pedidos");
        listaPedidos = new ArrayList<>();
        listViewPedidos = findViewById(R.id.listViewPedidos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPedidos);
        listViewPedidos.setAdapter(adapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot pedidoSnapshot : snapshot.getChildren()) {
                    String idpedido = pedidoSnapshot.getKey().toString();
                    listaPedidos.add(idpedido);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TraerPedidosActivity", "Cantidad de pedidos: " + listaPedidos.size());
            }
        });

    }


}