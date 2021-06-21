package com.example.vinyl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import POJO.Perfil;

public class ProfileActivity extends AppCompatActivity {

    RecyclerView rv;
    Perfil perfil;
    String URL = "";
    ImageButton ib_edit_profile, ib_log_out;
    ImageView iv_profile_pic;
    TextView tv_num_reviews, tv_num_followers, tv_num_followed, tv_name, tv_bio;
    Button btn_song, btn_album, btn_artist;
    int option = 1;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ib_edit_profile = findViewById(R.id.ibEditProfile);
        ib_log_out = findViewById(R.id.ibLogOut);
        iv_profile_pic = findViewById(R.id.ivProfilePic);
        tv_num_reviews = findViewById(R.id.tvNumReviews);
        tv_num_followers = findViewById(R.id.tvNumFollowers);
        tv_num_followed = findViewById(R.id.tvNumFollowed);
        tv_name = findViewById(R.id.tvNameProfile);
        tv_bio = findViewById(R.id.tvBioProfile);
        btn_song = findViewById(R.id.btnSongProfile);
        btn_album = findViewById(R.id.btnAlbumProfile);
        btn_artist = findViewById(R.id.btnArtistProfile);

        perfil = recuperarPreferencias();

        user_id = perfil.getId();
    }

    public void filtrar() {
        switch (option){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }
    }

    public void filterSelected(View view) {
        Button btn = findViewById(view.getId());
        String text = btn.getText().toString();
        clearButtons();
        if (text.equals(getResources().getString(R.string.btn_song))) {
            btn_song.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 0;
        } else if (text.equals(getResources().getString(R.string.btn_album))) {
            btn_album.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 1;
        } else if (text.equals(getResources().getString(R.string.btn_artist))) {
            btn_artist.setBackgroundColor(getResources().getColor(R.color.vinyl_selected_red));
            option = 2;
        }
        filtrar();
    }

    private void clearButtons() {
        btn_song.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
        btn_album.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
        btn_artist.setBackgroundColor(getResources().getColor(R.color.vinyl_red));
    }

    public void cerrarSesion(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

        Toast.makeText(ProfileActivity.this, "Has cerrado sesión", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void fromProfiletoSearch(View view) {
        Intent intent = new Intent(ProfileActivity.this, Buscador.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoFeed(View view) {
        Intent intent = new Intent(ProfileActivity.this, FeedActivity.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    public void fromProfiletoCalendar(View view) {
        Intent intent = new Intent(ProfileActivity.this, Calendar.class);
        intent.putExtra("perfilIntent", perfil);
        startActivity(intent);
    }

    private Perfil recuperarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        Perfil perfil = new Gson().fromJson(sharedPreferences.getString("perfilJSON", ""), Perfil.class);
        return perfil;
    }
}