package com.eitsistemas.localizacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {
    double latitud, longitud;

    public FragmentMaps(){

    }

        @Override
         public View onCreateView( LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle){
            View rootView = super.onCreateView(layoutInflater, viewGroup, bundle);
            if(getArguments()!=null){
                this.latitud = getArguments().getDouble("lat");
                this.longitud = getArguments().getDouble("lon");

            }
            getMapAsync(this);
            return rootView;
        }



    @Override
    public void onMapReady( GoogleMap googleMap) {
        LatLng latLng = new LatLng(longitud,latitud);
        float zoom =3;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        UiSettings settings = googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

    }
}
