package Modelo;

public class Comentarios {
    
    private int idComentario;
    private Usuario usuario;
    private String codCliente;
    private String texto;
    private String fecha;
    private String nroPed;
    
    @Override
    public String toString() {
        return "{Comentario" + "Usuario=" + usuario + ", comentario=" + texto + ", fecha=" + fecha + '}';
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNroPed() {
        return nroPed;
    }

    public void setNroPed(String nroPed) {
        this.nroPed = nroPed;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
