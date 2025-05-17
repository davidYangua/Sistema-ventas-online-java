package Modelo;

public class DetallePedido {
    
    private int idDetallePed;
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private double precio;
    private int atendido;
    
    public double Total(){
        return producto.getPrecio() * cantidad;
    }
    
    public double getTotal(){
        return cantidad*precio;
    }
    
    public void AumentarCantidad(int cantidad){
        this.cantidad+= cantidad;
    }
    
    public double getSubTotalPrecioUnitario() {
        return (double) (this.precio*0.82);
    }
    
    public double getSubTotalProducto() {
        return (double) (precio*cantidad*0.82);
    }

    public int getAtendido() {
        return atendido;
    }

    public void setAtendido(int atendido) {
        this.atendido = atendido;
    }

    public int getIdDetallePed() {
        return idDetallePed;
    }

    public void setIdDetallePed(int idDetallePed) {
        this.idDetallePed = idDetallePed;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
