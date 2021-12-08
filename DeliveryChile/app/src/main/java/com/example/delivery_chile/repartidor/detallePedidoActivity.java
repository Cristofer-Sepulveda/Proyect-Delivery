package com.example.delivery_chile.repartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.delivery_chile.R;

public class detallePedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        String idPedido = "";

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            idPedido = extras.getString("id_pedido");
        }

        TextView txtIdPedido = findViewById(R.id.id_pedido);
        txtIdPedido.setText(idPedido);
    }
}