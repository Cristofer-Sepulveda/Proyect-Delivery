package com.example.delivery_chile.repartidor;



public class Pedido {
    private String id_pedido;
    private String usuario_id_usuario;
    private String tienda_id_tienda;
    private String descripcion;
    private String telefono;
    private String direccion_destino;
    private String latitud;
    private String longitud;
    private String fecha_pedido;
    private String valor_total;
    private String id_estado;
    private String fecha_modificacion;

    public Pedido(){

    }
    public Pedido(String id_pedido,String usuario_id_usuario, String tienda_id_tienda, String descripcion, String telefono, String direccion_destino, String latitud, String longitud, String fecha_pedido, String valor_total, String id_estado, String fecha_modificacion) {
        this.id_pedido = id_pedido;
        this.usuario_id_usuario = usuario_id_usuario;
        this.tienda_id_tienda = tienda_id_tienda;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.direccion_destino = direccion_destino;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha_pedido = fecha_pedido;
        this.valor_total = valor_total;
        this.id_estado = id_estado;
        this.fecha_modificacion = fecha_modificacion;

    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getUsuario_id_usuario() {
        return usuario_id_usuario;
    }

    public void setUsuario_id_usuario(String usuario_id_usuario) {
        this.usuario_id_usuario = usuario_id_usuario;
    }

    public String getTienda_id_tienda() {
        return tienda_id_tienda;
    }

    public void setTienda_id_tienda(String tienda_id_tienda) {
        this.tienda_id_tienda = tienda_id_tienda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion_destino() {
        return direccion_destino;
    }

    public void setDireccion_destino(String direccion_destino) {
        this.direccion_destino = direccion_destino;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getValor_total() {
        return valor_total;
    }

    public void setValor_total(String valor_total) {
        this.valor_total = valor_total;
    }

    public String getId_estado() {
        return id_estado;
    }

    public void setId_estado(String id_estado) {
        this.id_estado = id_estado;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }
}
