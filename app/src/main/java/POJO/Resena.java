package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Resena implements Parcelable {

    int id;
    String titulo, texto, imagen, fecha;
    float puntuacion;

    // Atributos para facilitar los INNER JOIN
    String usuario, foto;

    public Resena() {
    }

    public Resena(int id, String titulo, String texto, String imagen, String fecha, float puntuacion) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.imagen = imagen;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
    }

    public Resena(int id, String titulo, String texto, String imagen, String fecha, float puntuacion, String usuario, String foto) {
        this.id = id;
        this.titulo = titulo;
        this.texto = texto;
        this.imagen = imagen;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
        this.usuario = usuario;
        this.foto = foto;
    }

    protected Resena(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        texto = in.readString();
        imagen = in.readString();
        fecha = in.readString();
        puntuacion = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(texto);
        dest.writeString(imagen);
        dest.writeString(fecha);
        dest.writeFloat(puntuacion);
        dest.writeString(usuario);
        dest.writeString(foto);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Resena{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fecha='" + fecha + '\'' +
                ", puntuacion=" + puntuacion +
                ", usuario='" + usuario + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
