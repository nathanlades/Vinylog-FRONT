package com.example.vinyl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import POJO.Tema;

public class DiscoTemasAdapter extends RecyclerView.Adapter<DiscoTemasAdapter.MyViewHolder> implements View.OnClickListener{

    Context context;
    List<Tema> temas;
    String imagen;
    private View.OnClickListener listener;

    public DiscoTemasAdapter(Context context, List<Tema> temas, String imagen) {
        this.context = context;
        this.temas = temas;
        this.imagen = imagen;
    }

    @NonNull
    @Override
    public DiscoTemasAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_disco_temas, parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoTemasAdapter.MyViewHolder holder, int position) {
        Tema tema = temas.get(position);
        holder.tv_disco_tema_titulo.setText((position + 1) + " - " + tema.getNombre());
        holder.tv_disco_tema_interprete.setText(tema.getArtista());
        int time1 = tema.getDuracion()/60;
        int time2 = tema.getDuracion()%60;
        if (time2<10) {
            holder.tv_disco_tema_duracion.setText(String.valueOf(time1 + ":0" + time2));
        } else {
            holder.tv_disco_tema_duracion.setText(String.valueOf(time1 + ":" + time2));
        }
        //holder.tv_disco_tema_duracion.setText(String.valueOf(tema.getDuracion()));
        Glide.with(context).load(imagen).into(holder.iv_disco_tema_foto);
    }

    @Override
    public int getItemCount() {
        return temas.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_disco_tema_foto;
        TextView tv_disco_tema_interprete, tv_disco_tema_duracion, tv_disco_tema_titulo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_disco_tema_foto = itemView.findViewById(R.id.iv_disco_tema_foto);
            tv_disco_tema_duracion = itemView.findViewById(R.id.tv_disco_tema_duracion);
            tv_disco_tema_interprete = itemView.findViewById(R.id.tv_disco_tema_interprete);
            tv_disco_tema_titulo = itemView.findViewById(R.id.tv_disco_tema_titulo);
        }
    }
}
