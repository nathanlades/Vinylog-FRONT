package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Artista implements Parcelable {
    int id, ano_inicio, ano_fin;
    String nombre, nacionalidad, foto, bio;

    public Artista(int id, int ano_inicio, int ano_fin, String nombre, String nacionalidad, String foto, String bio) {
        this.id = id;
        this.ano_inicio = ano_inicio;
        this.ano_fin = ano_fin;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.foto = foto;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    protected Artista(Parcel in) {
        id = in.readInt();
        ano_inicio = in.readInt();
        ano_fin = in.readInt();
        nombre = in.readString();
        nacionalidad = in.readString();
        foto = in.readString();
        bio = in.readString();
    }

    public static final Creator<Artista> CREATOR = new Creator<Artista>() {
        @Override
        public Artista createFromParcel(Parcel in) {
            return new Artista(in);
        }

        @Override
        public Artista[] newArray(int size) {
            return new Artista[size];
        }
    };

    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", ano_inicio=" + ano_inicio +
                ", ano_fin=" + ano_fin +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", foto='" + foto + '\'' +
                ", bio ='" + bio + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno_inicio() {
        return ano_inicio;
    }

    public void setAno_inicio(int ano_inicio) {
        this.ano_inicio = ano_inicio;
    }

    public int getAno_fin() {
        return ano_fin;
    }

    public void setAno_fin(int ano_fin) {
        this.ano_fin = ano_fin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(ano_inicio);
        dest.writeInt(ano_fin);
        dest.writeString(nombre);
        dest.writeString(nacionalidad);
        dest.writeString(foto);
        dest.writeString(bio);
    }
}
