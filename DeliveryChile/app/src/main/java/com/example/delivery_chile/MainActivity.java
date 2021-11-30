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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.delivery_chile.repartidor.repartidorActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnIngresar;

    //String URL= "http://delivery-chile.cl/loginMovil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = (EditText) findViewById(R.id.edtEmail);
        editPassword = (EditText) findViewById(R.id.edtPassword);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    //SE GENERA UN ERROR :(
    public void login() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://delivery-chile.cl/LoginMovilController",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Toast.makeText(getApplicationContext(), "this is response : " + response, Toast.LENGTH_LONG).show();


                        if (response.contains("1")){
                            startActivity(new Intent(getApplicationContext(), repartidorActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Wrong email or password",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", editEmail.getText().toString());
                params.put("password", editPassword.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}