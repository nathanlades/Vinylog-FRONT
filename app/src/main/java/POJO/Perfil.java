package POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Perfil implements Parcelable {
    private int id;
    private String nombre, poblacion, fecha_nac, perfil_spotify, usuario, mail, foto, biografia;

    public Perfil(){

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
        biografia = in.readString();
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
                ", foto='" + foto + '\'' +
                ", biografia='" + biografia + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Perfil(int id, String nombre, String poblacion, String fecha_nac, String perfil_spotify, String usuario, String mail, String foto, String biografia) {
        this.id = id;
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.fecha_nac = fecha_nac;
        this.perfil_spotify = perfil_spotify;
        this.usuario = usuario;
        this.mail = mail;
        this.foto = foto;
        this.biografia = biografia;
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
        dest.writeString(foto);
        dest.writeString(biografia);
    }
}
