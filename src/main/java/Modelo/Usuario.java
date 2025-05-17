package Modelo;

public class Usuario {
    
    private int idUsu;
    private String username;
    private String codUsuario;
    private String correo;
    private String password;
    private int estado;
    private String fecha;
    private Rol rol;
    private int id; //ADMIN (idAdmin) / Cliente (idCliente)
    
    @Override
    public String toString() {
        return "Usuario{" + "idUsu=" + idUsu + ", correo=" + correo + ", password=" + password + ", estado=" + estado + ", username=" + username + ", rol=" + rol + '}';
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
    
    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
