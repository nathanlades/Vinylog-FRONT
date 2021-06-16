package com.example.vinyl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import POJO.Perfil;
import POJO.Resena;

public class UsuarioResenaAdapter extends RecyclerView.Adapter<UsuarioResenaAdapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    List<Resena> resenas;
    String nombre;
    Perfil perfil;
    private View.OnClickListener listener;

    public UsuarioResenaAdapter(Context context, List<Resena> resenas, Perfil perfil) {
        this.context = context;
        this.resenas = resenas;
        this.perfil = perfil;
    }

    @NonNull
    @Override
    public UsuarioResenaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_artista_resena, parent, false);
        view.setOnClickListener(this);
        return new UsuarioResenaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioResenaAdapter.MyViewHolder holder, int position) {
        Resena resena = resenas.get(position);
        String titulo = "";
        if (resena.getTitulo().length()>24){
            titulo = resena.getTitulo().substring(0,20) + " ...";
        } else
            titulo = resena.getTitulo();
        holder.tv_artista_resena_nombreAutor.setText(titulo);
        String[] fecha = resena.getFecha().split(" ");
        holder.s = resena.getTexto();
        holder.s = holder.s.substring(0, Math.min(holder.s.length(),200));
        holder.tv_artista_resena_texto.setText(holder.s + "...");

        holder.s = resena.getFecha();
        /* El primer corte separa la fecha de la hora; el segundo corte separa: 0-año, 1-mes, 2-dia
         * El tercer corte separa 0-hora, 1-minutos, 2-segundos */
        String[] primerCorte = holder.s.split(" ");
        String[] segundoCorte = primerCorte[0].split("-");
        String[] tercerCorte = primerCorte[1].split(":");
        holder.tv_artista_resena_fecha.setText(segundoCorte[2] + "-" + segundoCorte[1] + "-" + segundoCorte[0]);

        //Establecemos la puntuacion
        int i = resena.getPuntuacion();
        switch (i){
            case 0:
                break;
            case 1:
                holder.iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                break;
            case 2:
                holder.iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                break;
            case 3:
                holder.iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                break;
            case 4:
                holder.iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                break;
            case 5:
                holder.iv_artista_resena_puntuacion1.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion2.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion3.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion4.setImageResource(R.mipmap.feed_red);
                holder.iv_artista_resena_puntuacion5.setImageResource(R.mipmap.feed_red);
                break;
        }

        holder.tv_artista_resena_texto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResenaActivity.class);
                intent.putExtra("perfilAutor", perfil);
                intent.putExtra("resena", resena);
                intent.putExtra("imagen", perfil.getFoto());
                context.startActivity(intent);

                /*if (holder.check) {
                    holder.tv_artista_resena_texto.setText(resena.getTexto());
                    holder.check = false;
                } else {
                    holder.tv_artista_resena_texto.setText(holder.s + "...");
                    holder.check = true;
                }*/
            }
        });



    }

    @Override
    public int getItemCount() {
        return resenas.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_artista_resena_nombreAutor, tv_artista_resena_fecha, tv_artista_resena_texto, tv_artista_resena_comentarios;
        ImageView iv_artista_resena_puntuacion1, iv_artista_resena_puntuacion2, iv_artista_resena_puntuacion3, iv_artista_resena_puntuacion4, iv_artista_resena_puntuacion5;
        String s;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_artista_resena_nombreAutor = itemView.findViewById(R.id.tv_resena_comentario_nombreAutor);
            tv_artista_resena_fecha = itemView.findViewById(R.id.tv_resena_comentario_fecha);
            tv_artista_resena_texto = itemView.findViewById(R.id.tv_artista_resena_texto);
            tv_artista_resena_comentarios = itemView.findViewById(R.id.tv_artista_resena_comentarios);
            iv_artista_resena_puntuacion1 = itemView.findViewById(R.id.iv_artista_resena_puntuacion1);
            iv_artista_resena_puntuacion2 = itemView.findViewById(R.id.iv_artista_resena_puntuacion2);
            iv_artista_resena_puntuacion3 = itemView.findViewById(R.id.iv_artista_resena_puntuacion3);
            iv_artista_resena_puntuacion4 = itemView.findViewById(R.id.iv_resena_puntuacion4);
            iv_artista_resena_puntuacion5 = itemView.findViewById(R.id.iv_artista_resena_puntuacion5);

        }
    }
}
