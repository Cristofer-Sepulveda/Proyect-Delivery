package com.example.delivery_chile.repartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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


        btnEnviarNotificacion.setOnClickListener(view -> {
            //Aqui tambien se importa la clase, esto solo es posible si se ha implementado en el archivo gradle
            //Ahora solo queda pasar los datos correctos como string al mensaje y la api se encargara del resto


             // Esto esta dando error, no deberia pero lo hace, supongo que la respuesta que obtiene es el error
            try {
                VonageClient client = VonageClient.builder().apiKey("5e5b65d1").apiSecret("uTaCIRfPb3pmW9JJ").build();

                TextMessage message = new TextMessage("Vonage APIs",
                        "56975225722",
                        "A text message sent using the Vonage SMS API"
                );



             //Esta linea es la que genera el error de tipo No static field INSTANCE of type Lorg/apache/http/conn/ssl/AllowAllHostnameVerifier
             // No se como avanzar sin hacer funcionar esto
                SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

                if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                    System.out.println("Message sent successfully. " + response.getMessages());
                } else {
                    System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
                }
            }catch (Exception exception){
                Toast.makeText(getApplicationContext(),"Error: " + exception.toString(),Toast.LENGTH_LONG).show();
            }



        });
    }
}