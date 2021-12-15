package com.example.delivery_chile.repartidor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery_chile.R;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import java.util.Locale;

public class detallePedidoActivity extends AppCompatActivity {

    //Este boton se encargara de enviar por POST los datos de Estado del pedido a un numero de telefono mediante VONAGE(nexmo)
    EditText latitud, longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        latitud = findViewById(R.id.latitud);
        longitud = findViewById(R.id.longitud);




        String idPedido = "";
        String idUsuario = "";
        String idTienda = "";
        String telefono = "";
        String direccion = "";
        String obtenerlatitud = "";
        String obtenerLongitud = "";
        String valorTotal = "";
        String idEstado = "";


        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            idPedido = extras.getString("id_pedido");
            idUsuario = extras.getString("id_usuario");
            idTienda = extras.getString("id_tienda");
            direccion = extras.getString("direccion");
            obtenerlatitud = extras.getString("latitud");
            obtenerLongitud = extras.getString("longitud");
            valorTotal = extras.getString("valor_total");
            idEstado = extras.getString("id_estado");
        }

        TextView txtIdPedido = findViewById(R.id.id_pedido);
        txtIdPedido.setText(idPedido);
        TextView txtIdUsuario = findViewById(R.id.usuario);
        txtIdUsuario.setText(idUsuario);
        TextView txtIdTienda = findViewById(R.id.tienda);
        txtIdTienda.setText(idTienda);
        TextView txtTelefono = findViewById(R.id.telefono);
        txtTelefono.setText("+56"+telefono);
        TextView txtDireccion = findViewById(R.id.direccion);
        txtDireccion.setText(direccion);
        EditText edtLatitud = findViewById(R.id.latitud);
        edtLatitud.setText(obtenerlatitud);
        EditText edtLongitud = findViewById(R.id.longitud);
        edtLongitud.setText(obtenerLongitud);
        TextView txtValor = findViewById(R.id.valor_total);
        txtValor.setText(valorTotal);
        TextView txtIdEstado = findViewById(R.id.estado);
        txtIdEstado.setText(idEstado);


        Button btnVerMapa = findViewById(R.id.btnVerMapa);
        
        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Abriendo Maps", Toast.LENGTH_SHORT).show();
                String latit = latitud.getText().toString();
                String longit = longitud.getText().toString();

                // convert into Double
                double lat = Double.parseDouble(latit);
                double lon = Double.parseDouble(longit);




                try {
                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", lat,lon);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error: "+ e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        
        Button btnEnviarNotificacion = findViewById(R.id.btnEnviarNotificacion);

        btnEnviarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"El evento funciona, pero el codigo no",Toast.LENGTH_LONG).show();

                if (ContextCompat.checkSelfPermission(detallePedidoActivity.this,
                        Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    //Cuando se da el permiso
                    //Crear el metodo
                    sendMessage();

                }else{
                    ActivityCompat.requestPermissions(detallePedidoActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

                /**
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
            private void sendMessage(){
                String sPhone = "56949142066";
                String sMessage = "Su pedido: +++++++++++++ ha salido a reparto";

                try {
                    if (!sPhone.equals("") && !sMessage.equals("")){
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(sPhone, null, sMessage, null, null);
                        Toast.makeText(getApplicationContext(), "Mensaje enviado correctamente", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){

                }


            }


        });










    }


}