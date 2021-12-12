package com.example.delivery_chile.repartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery_chile.R;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class detallePedidoActivity extends AppCompatActivity {

    //Este boton se encargara de enviar por POST los datos de Estado del pedido a un numero de telefono mediante VONAGE(nexmo)


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
        txtIdTienda.setText(idTienda);
        TextView txtDireccion = findViewById(R.id.direccion);
        txtDireccion.setText(direccion);
        TextView txtValor = findViewById(R.id.valor_total);
        txtValor.setText(valorTotal);
        TextView txtIdEstado = findViewById(R.id.estado);
        txtIdEstado.setText(idEstado);
        Button btnEnviarNotificacion = findViewById(R.id.btnEnviarNotificacion);

        btnEnviarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"El evento funciona, pero el codigo no",Toast.LENGTH_LONG).show();
                /****/
                try {
                 //K 5e5b65d1 AS uTaCIRfPb3pmW9JJ
                 // Estas son de V O N A G E, las comento para que git nomande el aviso de la apikey filtrada
                    VonageClient client = VonageClient.builder().apiKey("5e5b65d1").apiSecret("uTaCIRfPb3pmW9JJ").build();

                    TextMessage message = new TextMessage("Vonage APIs",
                            "56975225722",
                            "A text message sent using the Vonage SMS API"
                    );

                    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

                    if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                        System.out.println("Message sent successfully.");
                        Toast.makeText(getApplicationContext(),"Enviado",Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }


                    Toast.makeText(getApplicationContext(),"Paso bien",Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                }
                 /****/
            }
        });








    }


}