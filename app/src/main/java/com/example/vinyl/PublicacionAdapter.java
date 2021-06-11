package com.example.vinyl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.MyViewHolder> {

    String data1[], data2[], data3[], data4[], data5[]; // profilePic, user, cover, title, text
    int data6 = R.mipmap.menu, data7 = R.mipmap.like, data8 = R.mipmap.comments, data9 = R.mipmap.share;
    Context ct;

    public PublicacionAdapter(Context context, String profilePic[], String user[], String cover[],
                              String title[], String text[]) {
        ct = context;
        data1 = profilePic;
        data2 = user;
        data3 = cover;
        data4 = title;
        data5 = text;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.publicacion_feed, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(ct).load(data1[position]).into(holder.iv_profile_pic);
        holder.tv_user.setText(data2[position]);
        holder.ib_menu.setImageResource(data6);
        Glide.with(ct).load(data3[position]).into(holder.iv_cover);
        holder.ib_like.setImageResource(data7);
        holder.ib_comments.setImageResource(data8);
        holder.ib_share.setImageResource(data9);
        holder.tv_title.setText(data4[position]);
        holder.tv_text.setText(data5[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_user, tv_title, tv_text;
        ImageView iv_profile_pic, iv_cover;
        ImageButton ib_menu, ib_like, ib_comments, ib_share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tvUser);
            tv_title = itemView.findViewById(R.id.tvTitle);
            tv_text = itemView.findViewById(R.id.tvText);
            iv_profile_pic = itemView.findViewById(R.id.ivProfilePic);
            iv_cover = itemView.findViewById(R.id.ivCover);
            ib_menu = itemView.findViewById(R.id.ibMenu);
            ib_like = itemView.findViewById(R.id.ibLike);
            ib_comments = itemView.findViewById(R.id.ibComments);
            ib_share = itemView.findViewById(R.id.ibShare);
        }
    }
}
