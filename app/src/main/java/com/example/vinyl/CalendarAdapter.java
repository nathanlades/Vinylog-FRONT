package com.example.vinyl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import POJO.Evento;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> implements View.OnClickListener{
    Context context;
    List<Evento> eventos;
    private View.OnClickListener listener;

    public CalendarAdapter(Context context, List<Evento> eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_calendario,parent,false);
        view.setOnClickListener(this);
        return new CalendarAdapter.MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.s = evento.getFecha();
        /* El primer corte separa la fecha de la hora; el segundo corte separa: 0-año, 1-mes, 2-dia
        * El tercer corte separa 0-hora, 1-minutos, 2-segundos */
        String[] primerCorte = holder.s.split(" ");
        String[] segundoCorte = primerCorte[0].split("-");
        String[] tercerCorte = primerCorte[1].split(":");

        holder.tv_calendario_evento_dia.setText(segundoCorte[2]);
        holder.tv_calendario_evento_mes.setText(seleccionarMes(Integer.parseInt(segundoCorte[1])));
        holder.tv_calendario_evento_nombre.setText(evento.getNombre());
        holder.tv_calendario_evento_direccion.setText(evento.getLugar());
        holder.tv_calendario_evento_hora.setText(tercerCorte[0] + ":" + tercerCorte[1]);
        Glide.with(context).load(evento.getImagen()).into(holder.iv_calendario_evento);

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(evento.getLugar()));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        /*holder.ibt_calendario_evento_iramaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    context.startActivity(mapIntent);
            }
        });

        holder.iv_calendario_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventoActivity.class);
                intent.putExtra("evento", evento);
                context.startActivity(intent);
            }
        });

        holder.iv_calendario_evento_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webPage = Uri.parse(evento.getEntradas());
                Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_calendario_evento_dia, tv_calendario_evento_mes, tv_calendario_evento_nombre, tv_calendario_evento_hora, tv_calendario_evento_direccion;
        ImageView iv_calendario_evento, iv_calendario_evento_entrada;
        ImageButton ibt_calendario_evento_iramaps;
        String s;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_calendario_evento_dia = itemView.findViewById(R.id.tv_calendario_evento_dia);
            tv_calendario_evento_direccion = itemView.findViewById(R.id.tv_calendario_evento_direccion);
            tv_calendario_evento_hora = itemView.findViewById(R.id.tv_calendario_evento_hora);
            tv_calendario_evento_mes = itemView.findViewById(R.id.tv_calendario_evento_mes);
            tv_calendario_evento_nombre = itemView.findViewById(R.id.tv_calendario_evento_nombre);
            iv_calendario_evento = itemView.findViewById(R.id.iv_calendario_evento);
            ibt_calendario_evento_iramaps = itemView.findViewById(R.id.ibt_calendario_evento_iramaps);
            iv_calendario_evento_entrada = itemView.findViewById(R.id.iv_calendario_evento_entrada);
        }
    }

    private String seleccionarMes(int i){
        String mes = "";
        switch (i){
            case 1:
                mes = "Ene";
                break;
            case 2:
                mes ="Feb";
                break;
            case 3:
                mes = "Mar";
                break;
            case 4:
                mes ="Abr";
                break;
            case 5:
                mes = "May";
                break;
            case 6:
                mes = "Jun";
                break;
            case 7:
                mes = "Jul";
                break;
            case 8:
                mes = "Ago";
                break;
            case 9:
                mes = "Sep";
                break;
            case 10:
                mes = "Oct";
                break;
            case 11:
                mes = "Nov";
                break;
            case 12:
                mes = "Dic";
                break;
        }
        return mes;
    }
}
