package com.example.delivery_chile.repartidor;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.delivery_chile.R;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

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
        holder.cantidad.setText(pedidos.get(position).getCantidad());
        holder.direccion.setText(pedidos.get(position).getDireccion_destino());
        holder.fechaPedido.setText((CharSequence) pedidos.get(position).getFecha_pedido());
        holder.valorTotal.setText(pedidos.get(position).getValor_total());
        holder.idEstado.setText(pedidos.get(position).getId_estado());
        holder.fechaModificacion.setText((CharSequence) pedidos.get(position).getFecha_modificacion());


    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idPedido;
        TextView idUsuario;
        TextView idTienda;
        TextView idProducto;
        TextView cantidad;
        TextView direccion;
        TextView fechaPedido;
        TextView valorTotal;
        TextView idEstado;
        TextView fechaModificacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idPedido = itemView.findViewById(R.id.tv_id_pedido);
            idUsuario = itemView.findViewById(R.id.tv_id_usuario);
            idTienda = itemView.findViewById(R.id.tv_id_tienda);
            idProducto = itemView.findViewById(R.id.tv_id_producto);
            cantidad = itemView.findViewById(R.id.tv_cantidad);
            direccion = itemView.findViewById(R.id.tv_direccion);
            fechaPedido = itemView.findViewById(R.id.tv_fecha);
            valorTotal = itemView.findViewById(R.id.tv_valor);
            idEstado = itemView.findViewById(R.id.tv_estado);
            fechaModificacion = itemView.findViewById(R.id.tv_fecha_modificacion);
        }
    }
}

