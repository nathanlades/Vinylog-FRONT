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

import POJO.Disco;

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.MyViewHolder> implements View.OnClickListener{

    List<POJO.Disco> discos;
    Context context;
    private View.OnClickListener listener;

    public ArtistaAdapter(Context ct, List<POJO.Disco> discosF){
        discos = discosF;
        context = ct;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_discografia,parent,false);
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Disco disco = discos.get(position);
        Glide.with(context).load(disco.getPortada()).into(holder.iv_fila_discografia);
        holder.tv_fila_discografia.setText(disco.getNombre() + "\n" + disco.getAno());
    }

    @Override
    public int getItemCount() {
        return discos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_fila_discografia;
        TextView tv_fila_discografia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_fila_discografia = itemView.findViewById(R.id.iv_fila_discografia);
            tv_fila_discografia = itemView.findViewById(R.id.tv_fila_discografia);
        }
    }
}
