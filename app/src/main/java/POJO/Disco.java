package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Disco implements Parcelable {

    int id, ano, duracion;
    String nombre, portada;
    float puntuacion;

    //Atención, este campo es solo para realizar de manera más sencilla los INNER JOIN de mysql,
    //no debe actualizarse la DB para incluirlo en la tabla de 'disco' ni utilizar un constructor
    //que lo incluya para registrar álbumes.
    String artista; ///////////////
    /////////////////

    public Disco(){

    }

    public Disco(int ano, int duracion, String nombre, String portada, float puntuacion, String artista) {
        this.ano = ano;
        this.duracion = duracion;
        this.nombre = nombre;
        this.portada = portada;
        this.puntuacion = puntuacion;
        this.artista = artista;
    }

    public Disco(int ano, int duracion, String nombre, String portada, float puntuacion ) {
        this.ano = ano;
        this.duracion = duracion;
        this.nombre = nombre;
        this.portada = portada;
        this.puntuacion = puntuacion;
    }

    protected Disco(Parcel in) {
        id = in.readInt();
        ano = in.readInt();
        duracion = in.readInt();
        nombre = in.readString();
        portada = in.readString();
        puntuacion = in.readFloat();
    }

    public static final Creator<Disco> CREATOR = new Creator<Disco>() {
        @Override
        public Disco createFromParcel(Parcel in) {
            return new Disco(in);
        }

        @Override
        public Disco[] newArray(int size) {
            return new Disco[size];
        }
    };

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", ano=" + ano +
                ", duracion=" + duracion +
                ", nombre='" + nombre + '\'' +
                ", portada='" + portada + '\'' +
                ", puntuacion=" + puntuacion +
                ", artista=" + artista +
                '}';
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
        dest.writeString(nombre);
        dest.writeString(portada);
        dest.writeFloat(puntuacion);
        dest.writeString(artista);
    }
}
