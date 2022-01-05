package com.example.delivery_chile.repartidor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.delivery_chile.MainActivity;
import com.example.delivery_chile.R;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class detallePedidoActivity extends AppCompatActivity {

    //Este boton se encargara de enviar por POST los datos de Estado del pedido a un numero de telefono mediante VONAGE(nexmo)
    EditText latitud, longitud, id_pedido, telefono, descripcion;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);

        builder = new AlertDialog.Builder(this);


        latitud = findViewById(R.id.latitud);
        longitud = findViewById(R.id.longitud);




        String idPedido = "";
        String idUsuario = "";
        String idTienda = "";
        String descripcion = "";
        String telefono = "";
        String direccion = "";
        String obtenerlatitud = "";
        String obtenerLongitud = "";
        String valorTotal = "";
        String idEstado = "";
        String estado ="";


        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            idPedido = extras.getString("id_pedido");
            idUsuario = extras.getString("id_usuario");
            idTienda = extras.getString("id_tienda");
            descripcion = extras.getString("descripcion");
            telefono = extras.getString("telefono");
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
        TextView txtdescripcion = findViewById(R.id.descripcion);
        txtdescripcion.setText(descripcion);
        TextView txtTelefono = findViewById(R.id.telefono);
        txtTelefono.setText("56"+telefono);
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
        TextView tvStringEstado = findViewById(R.id.tv_string_estado);
        if (txtIdEstado.getText().equals("1")){
            tvStringEstado.setText("En Espera");
        } else if (txtIdEstado.getText().equals("2")){
            tvStringEstado.setText("En Reparto");
        } else if (txtIdEstado.getText().equals("3")){
            tvStringEstado.setText("Entregado");
        } else if (txtIdEstado.getText().equals("4")){
            tvStringEstado.setText("Cancelado");
        }


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

                String txtIdPedido2 = txtIdPedido.getText().toString();
                String txtIdEstado2 = "2";


                String dialog_title = "Cambiar estado";
                String dialog_message= "En reparto";
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(dialog_message) .setTitle(dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("¿Desea enviar una notificación al cliente?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(detallePedidoActivity.this,
                                        Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                    //Cuando se da el permiso
                                    //Crear el metodo
                                    actualizarEstado("https://delivery-chile.cl/updatePedidoMovil", txtIdPedido2, txtIdEstado2);
                                    sendMessage();

                                }else{
                                    ActivityCompat.requestPermissions(detallePedidoActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Cambiar a "+"'En reparto'"+"");
                alert.show();


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
                String sPhone = txtTelefono.getText().toString();
                String sMessage = "Su pedido Nro: "+txtIdPedido.getText()+"\n"+"Contenido: "+txtdescripcion.getText()+" ha salido a reparto";

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

        Button btnActualizarEstado = findViewById(R.id.btnActualizarEstado);

        btnActualizarEstado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String txtIdPedido3 = txtIdPedido.getText().toString();
                String txtIdEstado3 = "3";


                String dialog_title = "Cambiar estado";
                String dialog_message= "Entregado";
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(dialog_message) .setTitle(dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("¿Desea enviar una notificación al cliente?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(detallePedidoActivity.this,
                                        Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                    //Cuando se da el permiso
                                    //Crear el metodo
                                    actualizarEstado("https://delivery-chile.cl/updatePedidoMovil", txtIdPedido3, txtIdEstado3);
                                    sendMessage();

                                }else{
                                    ActivityCompat.requestPermissions(detallePedidoActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Cambiar a "+"'Entregado'"+"");
                alert.show();

            }


            private void sendMessage(){
                String sPhone = txtTelefono.getText().toString();
                String sMessage = "Su pedido Nro: "+txtIdPedido.getText()+"\n"+"Contenido: "+txtdescripcion.getText()+" ha sido entregado con exito";

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

        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtIdPedido4 = txtIdPedido.getText().toString();
                String txtIdEstado4 = "4";


                String dialog_title = "Cambiar estado";
                String dialog_message= "Cancelado";
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(dialog_message) .setTitle(dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("¿Desea enviar una notificación al cliente?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (ContextCompat.checkSelfPermission(detallePedidoActivity.this,
                                        Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                    //Cuando se da el permiso
                                    //Crear el metodo
                                    actualizarEstado("https://delivery-chile.cl/updatePedidoMovil", txtIdPedido4, txtIdEstado4);
                                    sendMessage();

                                }else{
                                    ActivityCompat.requestPermissions(detallePedidoActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                                }
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Cambiar a "+"'Cancelado'"+"");
                alert.show();

            }

            private void sendMessage(){
                String sPhone = txtTelefono.getText().toString();
                String sMessage = "Su pedido Nro: "+txtIdPedido.getText()+"\n"+"Contenido: "+txtdescripcion.getText()+" no se ha podido entregar.\n   PEDIDO CANCELADO";

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

    private void actualizarEstado(String URL, String txtIdPedido, String txtIdEstado){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            public void onResponse(String response){

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(detallePedidoActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_pedido", txtIdPedido);
                parametros.put("estado_id_estado", txtIdEstado);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}