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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String data1[], data2[], data3[];
    Context context;

    public MyAdapter(Context ct, String nombre[], String tipo[], String portada[]){
        context = ct;
        data1 = nombre;
        data2 = tipo;
        data3 = portada;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_buscador, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_nombre.setText(data1[position]);
        holder.tv_categoria.setText(data2[position]);
        Glide.with(context).load(data3[position]).into(holder.iv_imagen);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre, tv_categoria;
        ImageView iv_imagen;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_categoria = itemView.findViewById(R.id.tv_categoria);
            iv_imagen = itemView.findViewById(R.id.iv_imagen);
        }
    }
}
