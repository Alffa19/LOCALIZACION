package com.eitsistemas.localizacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvMensaje, tvMensaje2;
    private  static final long MIN_TIME=10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMensaje = (TextView) findViewById(R.id.txtLongitud);
        tvMensaje2 = (TextView) findViewById(R.id.txtLatitud);
          try{
              if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                      !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                      !=PackageManager.PERMISSION_GRANTED){
                  ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, },1000);
              }else{
                  iniciarLocalizacion();
              }
          }catch (Exception e){
              e.getMessage();
          }

    }
    private void iniciarLocalizacion()
    {
        LocationManager locationManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion local = new Localizacion();
        local.setMainActivity(this, tvMensaje);
        final boolean gpsEnable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnable){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,0,local);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME, 0,local);
    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[]grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarLocalizacion();
                return;
            }
        }
    }
    public void setLocation(Location location){
        if(location.getLatitude() !=0.0 && location.getLongitude() !=00){
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list=geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if(!list.isEmpty()){
                    Address DirCalle =list.get(0);
                    tvMensaje2.setText("Mi Direccion es: \n" + DirCalle.getAddressLine(0));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}