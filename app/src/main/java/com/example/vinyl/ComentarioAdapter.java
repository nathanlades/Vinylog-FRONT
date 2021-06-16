package com.example.vinyl;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import POJO.Comentario;
import POJO.Perfil;
import POJO.Resena;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.MyViewHolder> implements View.OnClickListener {

    Context context;
    List<Comentario> comentarios;
    List<Perfil> perfiles;
    private View.OnClickListener listener;

    public ComentarioAdapter(Context context, List<Comentario> comentarios, List<Perfil> perfiles) {
        this.context = context;
        this.comentarios = comentarios;
        this.perfiles = perfiles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_comentario, parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Perfil perfil = perfiles.get(position);
        Comentario comentario = comentarios.get(position);

        holder.tv_resena_comentario_nombreAutor.setText(perfil.getNombre());

        holder.s = comentario.getFecha();
        /* El primer corte separa la fecha de la hora; el segundo corte separa: 0-año, 1-mes, 2-dia
         * El tercer corte separa 0-hora, 1-minutos, 2-segundos */
        String[] primerCorte = holder.s.split(" ");
        String[] segundoCorte = primerCorte[0].split("-");
        String[] tercerCorte = primerCorte[1].split(":");
        holder.tv_resena_comentario_fecha.setText(segundoCorte[2] + "-" + segundoCorte[1] + "-" + segundoCorte[0]);

        Glide.with(context).load(perfil.getFoto()).into(holder.iv_resena_comentario_autor);

        holder.s = comentario.getComentario();
        if (holder.s.length() > 150) {
            holder.s = holder.s.substring(0, Math.min(holder.s.length(), 150));
            holder.tv_resena_comentario_texto.setText(holder.s + " ...");
            holder.tv_resena_comentario_texto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.check){
                        holder.tv_resena_comentario_texto.setText(comentario.getComentario());
                        holder.check = false;
                    } else {
                        holder.tv_resena_comentario_texto.setText(holder.s + " ...");
                        holder.check = true;
                    }
                }
            });
        } else {
            holder.tv_resena_comentario_texto.setText(comentario.getComentario());
        }

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_resena_comentario_fecha, tv_resena_comentario_nombreAutor, tv_resena_comentario_texto;
        ImageView iv_resena_comentario_autor;
        boolean check = true;
        String s;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_resena_comentario_fecha = itemView.findViewById(R.id.tv_resena_comentario_fecha);
            tv_resena_comentario_nombreAutor = itemView.findViewById(R.id.tv_resena_comentario_nombreAutor);
            tv_resena_comentario_texto = itemView.findViewById(R.id.tv_resena_comentario_texto);
            iv_resena_comentario_autor = itemView.findViewById(R.id.iv_resena_comentario_autor);
        }
    }
}
