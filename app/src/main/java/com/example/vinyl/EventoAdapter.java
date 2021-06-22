package com.example.vinyl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import POJO.Perfil;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.MyViewHolder> {

    Context context;
    List<Perfil> perfiles;

    public EventoAdapter(Context context, List<Perfil> perfiles) {
        this.context = context;
        this.perfiles = perfiles;
    }

    @NonNull
    @Override
    public EventoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_evento_asistentes, parent, false);
        return new EventoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return perfiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_evento_asistentes1, iv_evento_asistentes2, iv_evento_asistentes3;
        TextView tv_evento_asistentes_lista;
        String s;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_evento_asistentes1 = itemView.findViewById(R.id.iv_evento_asistentes1);
            iv_evento_asistentes2 = itemView.findViewById(R.id.iv_evento_asistentes2);
            iv_evento_asistentes3 = itemView.findViewById(R.id.iv_evento_asistentes3);
            tv_evento_asistentes_lista = itemView.findViewById(R.id.tv_evento_asistentes_lista);
        }
    }
}
