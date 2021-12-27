package com.example.delivery_chile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.delivery_chile.repartidor.repartidorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnIngresar;
    String email, password;
    String quetrae;

    //String URL= "http://delivery-chile.cl/loginMovil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.edtEmail);
        editPassword = findViewById(R.id.edtPassword);
        btnIngresar = findViewById(R.id.btnIngresar);


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editEmail.getText().toString();
                password = editPassword.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()){
                    validarUsuario("https://www.delivery-chile.cl/validar_usuario");

                }else{
                    Toast.makeText(MainActivity.this, "No se aceptan campos vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String datos;
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response);
                    String id_user = obj.getString("id_usuario");
                    //Toast.makeText(getApplicationContext(), "" + id_user, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    // Aqui se reemplaza el "\n" por "" para que la respuesta en json no devuelva datos nulos y se salte el paso del if que viene
                    datos= response.replace("\n","");
                    // Aca se reemplaza el caracter vacio " " por "" para que no exista nada, nisiquiera un espacio, probablemente este es el que si funciona
                    datos.replace(" ", "");
                    // Ahora si se puede comprobar, si datos esta vacio
                    if (!datos.isEmpty()){
                        // Crea un toast que notifica que se inicio sesion
                        Toast.makeText(MainActivity.this, "Sesion iniciada", Toast.LENGTH_LONG).show();
                        // Se crea un nuevo intento para el activity repartidor
                        Intent intent = new Intent(getApplicationContext(), repartidorActivity.class);
                        // Aqui se debe enviar el id del usuario con un put extra?
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        obj = new JSONObject(response);
                        String id_user = obj.getString("id_usuario");
                        intent.putExtra("id_usuario", id_user);

                        startActivity(intent);
                    }else{
                        // Ahora si se cumple la condicion de que no devuelva nada coincidente desde la base de datos y arroje el siguiente aviso
                        Toast.makeText(MainActivity.this, "Usuario o contrasena incorrecta" , Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(MainActivity.this,e.toString(), Toast.LENGTH_LONG).show();
                }

                response.replaceAll("[\\n\\t ]", "");
                quetrae = response.toString();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", editEmail.getText().toString());
                parametros.put("password", editPassword.getText().toString());
                return parametros;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}