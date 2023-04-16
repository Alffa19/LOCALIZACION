package com.eitsistemas.localizacion;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Localizacion  implements LocationListener {
   MainActivity mainActivity;
   TextView tvmensaje;
   public MainActivity getMainActivity(){return mainActivity;}
   public void setMainActivity(MainActivity mainActivity, TextView tvmensaje){
       this.mainActivity=mainActivity;
       this.tvmensaje = tvmensaje;
   }
   @Override
    public void onLocationChanged(Location location){
       String texto = "Mi Ubicacion Es:\n"
               +"Latitud = " + location.getLatitude()+"\n"
               + "Longitud = " + location.getLongitude();
       tvmensaje.setText(texto);
       this.mainActivity.setLocation(location);
       mapa(location.getLatitude(), location.getLongitude());
   }
   public void mapa(double latitud, double longitud){
       FragmentMaps fragment_maps = new FragmentMaps();
       Bundle bundle = new Bundle();
       bundle.putDouble("lat ", new Double(latitud));
       bundle.putDouble("lon ",new Double(longitud));

       fragment_maps.setArguments(bundle);
       FragmentManager fragmentManager = getMainActivity().getSupportFragmentManager();
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.add(R.id.map, fragment_maps, null);
       fragmentTransaction.commit();
   }
   @Override
    public void onStatusChanged(String provider, int status, Bundle extras){
       switch (status){
           case LocationProvider
                   .AVAILABLE:
               Log.d("debug","LocationProvider.AVAILABLE");
               break;
           case LocationProvider.OUT_OF_SERVICE:
               Log.d("debug","LocationProvider.OUT_OF_SERVICE");
               break;
           case LocationProvider.TEMPORARILY_UNAVAILABLE:
               Log.d("debug","LocationProvider.TEMPORARILY_UNAVAILABLE");
               break;
       }
   }

    @Override
    public void onProviderEnabled( String provider) {
        tvmensaje.setText("GPS ACTIVADO");
    }

    @Override
    public void onProviderDisabled( String provider) {
            tvmensaje.setText("GPS DESACTIVADO");
    }
}
