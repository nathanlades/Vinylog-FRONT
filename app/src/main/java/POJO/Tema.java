package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Tema implements Parcelable {
    int id, ano, duracion, puntuacion;
    String nombre, foto;
    //la foto se saca con un innerjoin con las tablas tema_disco y disco, pongo el campo aquí
    //para hacerlo más fácil a la hora de implementar el objeto JSON

    String artista;

    public Tema(int id, int ano, int duracion, int puntuacion, String nombre, String foto, String artista) {
        this.id = id;
        this.ano = ano;
        this.duracion = duracion;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
        this.foto = foto;
        this.artista = artista;
    }

    public Tema(int id, int ano, int duracion, int puntuacion, String nombre, String foto) {
        this.id = id;
        this.ano = ano;
        this.duracion = duracion;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
        this.foto = foto;
    }

    public Tema(int id, int ano, int duracion, int puntuacion, String nombre) {
        this.id = id;
        this.ano = ano;
        this.duracion = duracion;
        this.puntuacion = puntuacion;
        this.nombre = nombre;
    }

    protected Tema(Parcel in) {
        id = in.readInt();
        ano = in.readInt();
        duracion = in.readInt();
        puntuacion = in.readInt();
        nombre = in.readString();
        foto = in.readString();
        artista = in.readString();
    }

    public static final Creator<Tema> CREATOR = new Creator<Tema>() {
        @Override
        public Tema createFromParcel(Parcel in) {
            return new Tema(in);
        }

        @Override
        public Tema[] newArray(int size) {
            return new Tema[size];
        }
    };

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Tema{" +
                "id=" + id +
                ", ano=" + ano +
                ", duracion=" + duracion +
                ", puntuacion=" + puntuacion +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", artista='" + artista + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        dest.writeInt(ano);
        dest.writeInt(duracion);
        dest.writeInt(puntuacion);
        dest.writeString(nombre);
        dest.writeString(foto);
        dest.writeString(artista);
    }
}
