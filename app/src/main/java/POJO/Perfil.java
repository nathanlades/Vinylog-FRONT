package POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Perfil implements Parcelable {
    private int id;
    private String nombre, poblacion, fecha_nac, perfil_spotify, usuario, mail, foto;

    public Perfil(){

    }

    //Recordar que el id se genera automáticamente con el registro en la db, así que no debemos definirlo nosotros
    //si fuésemos a hacer un registro nuevo, deberíamos dejarlo en null
    public Perfil(int id, String nombre, String poblacion, String fecha_nac, String perfil_spotify, String usuario, String mail, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.fecha_nac = fecha_nac;
        this.perfil_spotify = perfil_spotify;
        this.usuario = usuario;
        this.mail = mail;
        this.foto = foto;
    }

    protected Perfil(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        poblacion = in.readString();
        fecha_nac = in.readString();
        perfil_spotify = in.readString();
        usuario = in.readString();
        mail = in.readString();
        foto = in.readString();
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public static final Creator<Perfil> CREATOR = new Creator<Perfil>() {
        @Override
        public Perfil createFromParcel(Parcel in) {
            return new Perfil(in);
        }

        @Override
        public Perfil[] newArray(int size) {
            return new Perfil[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(poblacion);
        dest.writeString(fecha_nac);
        dest.writeString(perfil_spotify);
        dest.writeString(usuario);
        dest.writeString(mail);
    }
}
