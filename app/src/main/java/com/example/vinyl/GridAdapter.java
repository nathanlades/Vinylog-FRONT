package com.example.vinyl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import POJO.Perfil;
import POJO.Resena;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> implements View.OnClickListener {

    String data1[];
    List<Resena> resenas;
    Perfil perfil;
    Context ct;
    private View.OnClickListener listener;

    public GridAdapter(Context context, String cover[], List<Resena> resenas, Perfil perfil) {
        ct = context;
        data1 = cover;
        this.resenas = resenas;
        this.perfil = perfil;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.grid_perfil, parent, false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Resena resena = resenas.get(position);
        Glide.with(ct).load(data1[position]).into(holder.iv_cover);

        holder.iv_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, ResenaActivity.class);
                intent.putExtra("perfilAutor", perfil);
                intent.putExtra("resena", resena);
                intent.putExtra("imagen", perfil.getFoto());
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_cover;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cover = itemView.findViewById(R.id.ivGridCover);
        }

    }
}
