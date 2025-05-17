package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    
    private int idPedido;
    private String nroPed;
    private Cliente cliente;
    private Delivery delivery;
    private String fecha;
    private double montoTotal;
    private String estado;
    private List<DetallePedido> detalles;
    private int cantDetalles;
    private int metodoPago;

    public String getNroPed() {
        return nroPed;
    }

    public int getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(int metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setNroPed(String nroPed) {
        this.nroPed = nroPed;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public int getCantDetalles() {
        return cantDetalles;
    }

    public void setCantDetalles(int cantDetalles) {
        this.cantDetalles = cantDetalles;
    }
}
