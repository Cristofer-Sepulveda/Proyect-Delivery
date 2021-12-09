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
        String idUsuario = "";
        String idTienda = "";
        String direccion = "";
        String valorTotal = "";
        String idEstado = "";

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            idPedido = extras.getString("id_pedido");
            idUsuario = extras.getString("id_usuario");
            idTienda = extras.getString("id_tienda");
            direccion = extras.getString("direccion");
            valorTotal = extras.getString("valor_total");
            idEstado = extras.getString("id_estado");
        }

        TextView txtIdPedido = findViewById(R.id.id_pedido);
        txtIdPedido.setText(idPedido);
        TextView txtIdUsuario = findViewById(R.id.usuario);
        txtIdUsuario.setText(idUsuario);
        TextView txtIdTienda = findViewById(R.id.tienda);
        txtIdPedido.setText(idTienda);
        TextView txtDireccion = findViewById(R.id.direccion);
        txtDireccion.setText(direccion);
        TextView txtValor = findViewById(R.id.valor_total);
        txtValor.setText(valorTotal);
        TextView txtIdEstado = findViewById(R.id.estado);
        txtIdEstado.setText(idEstado);
    }
}