package POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class Comentario implements Parcelable {
    int id, id_resena, id_perfil;
    String comentario, fecha;

    //Esta línea es solo para facilitar la consulta a la db y no tener que tocar más
    //la parte del JSON
    String autor;

    public Comentario(int id, int id_resena, int id_perfil, String comentario, String fecha) {
        this.id = id;
        this.id_resena = id_resena;
        this.id_perfil = id_perfil;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public Comentario(int id, int id_resena, int id_perfil, String comentario, String fecha, String autor) {
        this.id = id;
        this.id_resena = id_resena;
        this.id_perfil = id_perfil;
        this.comentario = comentario;
        this.fecha = fecha;
        this.autor = autor;
    }

    protected Comentario(Parcel in) {
        id = in.readInt();
        id_resena = in.readInt();
        id_perfil = in.readInt();
        comentario = in.readString();
        fecha = in.readString();
        autor = in.readString();
    }

    public static final Creator<Comentario> CREATOR = new Creator<Comentario>() {
        @Override
        public Comentario createFromParcel(Parcel in) {
            return new Comentario(in);
        }

        @Override
        public Comentario[] newArray(int size) {
            return new Comentario[size];
        }
    };

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", id_resena=" + id_resena +
                ", id_perfil=" + id_perfil +
                ", comentario='" + comentario + '\'' +
                ", fecha='" + fecha + '\'' +
                ", autor='" + autor + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_resena() {
        return id_resena;
    }

    public void setId_resena(int id_resena) {
        this.id_resena = id_resena;
    }

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_resena);
        dest.writeInt(id_perfil);
        dest.writeString(comentario);
        dest.writeString(fecha);
        dest.writeString(autor);
    }
}
