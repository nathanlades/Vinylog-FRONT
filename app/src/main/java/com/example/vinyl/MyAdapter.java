package com.example.vinyl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {

    String data1[], data2[], data3[];
    Context context;
    private View.OnClickListener listener;

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

        view.setOnClickListener(this);
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

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre, tv_categoria;
        ImageView iv_imagen;

        //Para pasar a la siguiente actividad
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_categoria = itemView.findViewById(R.id.tv_categoria);
            iv_imagen = itemView.findViewById(R.id.iv_imagen);

            constraintLayout = itemView.findViewById(R.id.activity_calendar);
        }
    }
}
