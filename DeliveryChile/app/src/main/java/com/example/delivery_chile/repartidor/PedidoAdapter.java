package com.example.delivery_chile.repartidor;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivery_chile.MainActivity;
import com.example.delivery_chile.R;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder>{

    LayoutInflater inflater;
    List<Pedido> pedidos;

    public PedidoAdapter(Context contexto, List<Pedido> pedidos) {
        this.inflater = LayoutInflater.from(contexto);
        this.pedidos = pedidos;
    }




    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




        holder.idPedido.setText(pedidos.get(position).getId_pedido());
        holder.idUsuario.setText(pedidos.get(position).getUsuario_id_usuario());
        holder.idTienda.setText(pedidos.get(position).getTienda_id_tienda());
        holder.idProducto.setText(pedidos.get(position).getProducto_id_producto());
        holder.telefono.setText(pedidos.get(position).getTelefono());
        holder.direccion.setText(pedidos.get(position).getDireccion_destino());
        holder.latitud.setText(pedidos.get(position).getLatitud());
        holder.longitud.setText(pedidos.get(position).getLongitud());
        holder.fechaPedido.setText((CharSequence) pedidos.get(position).getFecha_pedido());
        holder.valorTotal.setText(pedidos.get(position).getValor_total());
        holder.idEstado.setText(pedidos.get(position).getId_estado());
        holder.fechaModificacion.setText((CharSequence) pedidos.get(position).getFecha_modificacion());
        // Aqui van los botones y sus listener para abrir otro activity y pasar los datos necesarios para ver o editar
        holder.setOnClickListeners();


    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView idPedido;
        TextView idUsuario;
        TextView idTienda;
        TextView idProducto;
        TextView telefono;
        TextView direccion;
        TextView latitud;
        TextView longitud;
        TextView fechaPedido;
        TextView valorTotal;
        TextView idEstado;
        TextView fechaModificacion;

        Button btnVerDetalles;
        Button btnEditar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            idPedido = itemView.findViewById(R.id.tv_id_pedido);
            idUsuario = itemView.findViewById(R.id.tv_id_usuario);
            idTienda = itemView.findViewById(R.id.tv_id_tienda);
            idProducto = itemView.findViewById(R.id.tv_id_producto);
            telefono = itemView.findViewById(R.id.tv_telefono);
            direccion = itemView.findViewById(R.id.tv_direccion);
            latitud = itemView.findViewById(R.id.tv_latitud);
            longitud = itemView.findViewById(R.id.tv_longitud);
            fechaPedido = itemView.findViewById(R.id.tv_fecha);
            valorTotal = itemView.findViewById(R.id.tv_valor);
            idEstado = itemView.findViewById(R.id.tv_estado);
            fechaModificacion = itemView.findViewById(R.id.tv_fecha_modificacion);
            btnVerDetalles = itemView.findViewById(R.id.btnVerDetalles);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }

        void setOnClickListeners(){
            btnVerDetalles.setOnClickListener(this);
            btnEditar.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnVerDetalles:
                    try {
                        Intent intent = new Intent(context, detallePedidoActivity.class);
                        // En versiones de android actuales, si no se pasa el FLAG (Bandera) puede dar error y no abrir el activity
                        // , por eso tengo esto dentro de un try catch

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("id_pedido", idPedido.getText());
                        intent.putExtra("id_usuario", idUsuario.getText());
                        intent.putExtra("id_tienda", idTienda.getText());
                        intent.putExtra("telefono", telefono.getText());
                        intent.putExtra("direccion", direccion.getText());
                        intent.putExtra("latitud", latitud.getText());
                        intent.putExtra("longitud", longitud.getText());
                        intent.putExtra("valor_total", valorTotal.getText());
                        intent.putExtra("id_estado", idEstado.getText());
                        context.startActivity(intent);
                        break;
                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(), "Error: "+ e.toString() , Toast.LENGTH_SHORT).show();
                    }

                case R.id.btnEditar:
                    break;
            }
        }
    }
}

