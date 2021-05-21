package POJO;

// Clase POJO Perfil

public class Perfil {
    private int id;
    private String nombre, poblacion, fecha_nac, perfil_spotify, usuario, mail;

    public Perfil(){

    }

    //Recordar que el id se genera automáticamente con el registro en la db, así que no debemos definirlo nosotros
    //si fuésemos a hacer un registro nuevo, deberíamos dejarlo en null
    public Perfil(int id, String nombre, String poblacion, String fecha_nac, String perfil_spotify, String usuario, String mail) {
        this.id = id;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.fecha_nac = fecha_nac;
        this.perfil_spotify = perfil_spotify;
        this.usuario = usuario;
        this.mail = mail;
    }

    public void setId(){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getPerfil_spotify() {
        return perfil_spotify;
    }

    public void setPerfil_spotify(String perfil_spotify) {
        this.perfil_spotify = perfil_spotify;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", poblacion='" + poblacion + '\'' +
                ", fecha_nac='" + fecha_nac + '\'' +
                ", perfil_spotify='" + perfil_spotify + '\'' +
                ", usuario='" + usuario + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
