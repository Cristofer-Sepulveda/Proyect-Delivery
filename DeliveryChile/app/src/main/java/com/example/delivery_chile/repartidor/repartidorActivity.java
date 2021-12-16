package com.example.delivery_chile.repartidor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.delivery_chile.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;

public class repartidorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Pedido> pedidos;
    private static  String JSON_URL = "https://delivery-chile.cl/listaMovilPedidos";
    PedidoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidor);



        recyclerView = findViewById(R.id.recycler_lista_pedidos);
        pedidos = new ArrayList<>();


        extractPedido();



    }

    private void extractPedido(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {

            String usuario;
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length(); i++) {
                    try {
                        JSONObject pedidoObject = response.getJSONObject(i);

                        String id_user = "";


                        Bundle extras = getIntent().getExtras();
                        if (extras !=null){
                            id_user = extras.getString("id_usuario");
                        }


                        Pedido pedido = new Pedido();
                        pedido.setId_pedido(pedidoObject.getString("id_pedido").toString());
                        pedido.setUsuario_id_usuario(pedidoObject.getString("usuario_id_usuario").toString());
                        pedido.setTienda_id_tienda(pedidoObject.getString("tienda_id_tienda").toString());
                        pedido.setDescripcion(pedidoObject.getString("descripcion").toString());
                        pedido.setTelefono(pedidoObject.getString("telefono").toString());
                        pedido.setDireccion_destino(pedidoObject.getString("direccion_destino").toString());
                        pedido.setLatitud(pedidoObject.getString("latitud").toString());
                        pedido.setLongitud(pedidoObject.getString("longitud").toString());
                        pedido.setFecha_pedido(pedidoObject.getString("fecha_pedido").toString());
                        pedido.setValor_total(pedidoObject.getString("valor_total").toString());
                        pedido.setId_estado(pedidoObject.getString("estado_id_estado").toString());
                        pedido.setFecha_modificacion(pedidoObject.getString("fecha_modificacion").toString());

                        pedidos.add(pedido);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new PedidoAdapter(getApplicationContext(),pedidos);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }
}