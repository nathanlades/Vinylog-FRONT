package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Resena implements Parcelable {
     int id, puntuacion;
     String titulo,texto,imagen,fecha,usuario;

    public Resena(int id, int puntuacion, String titulo, String texto, String imagen, String fecha, String usuario) {
        this.id = id;
        this.puntuacion = puntuacion;
        this.titulo = titulo;
        this.texto = texto;
        this.imagen = imagen;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    protected Resena(Parcel in) {
        id = in.readInt();
        puntuacion = in.readInt();
        titulo = in.readString();
        texto = in.readString();
        imagen = in.readString();
        fecha = in.readString();
        usuario = in.readString();
    }

    public static final Creator<Resena> CREATOR = new Creator<Resena>() {
        @Override
        public Resena createFromParcel(Parcel in) {
            return new Resena(in);
        }

        @Override
        public Resena[] newArray(int size) {
            return new Resena[size];
        }
    };

    @Override
    public String toString() {
        return "Resena{" +
                "id=" + id +
                ", likes=" + puntuacion +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fecha='" + fecha + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(puntuacion);
        dest.writeString(titulo);
        dest.writeString(texto);
        dest.writeString(imagen);
        dest.writeString(fecha);
        dest.writeString(usuario);
    }
}
