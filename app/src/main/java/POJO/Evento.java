package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Evento implements Parcelable {
    int id;
    String fecha,lugar,entradas,provincia,imagen;

    //Esclusivo para facilitar la consulta a la db
    String nombre; //(del grupo)

    protected Evento(Parcel in) {
        id = in.readInt();
        fecha = in.readString();
        lugar = in.readString();
        entradas = in.readString();
        provincia = in.readString();
        imagen = in.readString();
        nombre = in.readString();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel in) {
            return new Evento(in);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", lugar='" + lugar + '\'' +
                ", entradas='" + entradas + '\'' +
                ", provincia='" + provincia + '\'' +
                ", imagen='" + imagen + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getEntradas() {
        return entradas;
    }

    public void setEntradas(String entradas) {
        this.entradas = entradas;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Evento(int id, String fecha, String lugar, String entradas, String provincia, String imagen, String nombre) {
        this.id = id;
        this.fecha = fecha;
        this.lugar = lugar;
        this.entradas = entradas;
        this.provincia = provincia;
        this.imagen = imagen;
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(fecha);
        dest.writeString(lugar);
        dest.writeString(entradas);
        dest.writeString(provincia);
        dest.writeString(imagen);
        dest.writeString(nombre);
    }
}
