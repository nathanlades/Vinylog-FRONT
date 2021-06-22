package com.example.vinyl;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import POJO.Evento;


public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //Cogemos el evento y tratamos de sacar las coordenadas a partir de la dirección
        EventoActivity eventoActivity = (EventoActivity) getActivity();
        Evento evento = eventoActivity.getEvento();

        Geocoder geocoder = new Geocoder(view.getContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(evento.getLugar(),1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Address location = addresses.get(0);
        location.getLatitude();
        location.getLongitude();

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title(evento.getNombre()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

               /* googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng1 = new LatLng(40.365462, -3.957401);
                        markerOptions.position(latLng1);
                        markerOptions.title("titulo");
                        googleMap.clear();
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1,10));
                        googleMap.addMarker(new MarkerOptions().position(latLng1).title("DSA"));
                    }
                });*/
            }
        });

        return view;
    }
}