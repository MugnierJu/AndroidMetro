package com.grenoble.miage.metromobilite.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.grenoble.miage.metromobilite.R;

/**
 * Created by rubata on 25/04/18.
 */

public class GeolocalisationActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        System.out.println("shijlfhgpiruvgyn:iudnlsjxvnliuthvlthgivsuthvgliduthlidurhtvgliurnthglisuvrhligrhtvgildurhildhtilvdurthilghvudrnhvtugldiruhtgldurthiludnthldhnrtlivhndltihvdlriuthvdirthmvdrhtmvodrihtmvodrhmoidrhmoidrthmodrhtmodrtmohdrmoihtdmrtoihjmdortijhmodritjhmodritjmhordjhmdrijtmriohj");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        System.out.println(location.toString());
                    }
                }

            });
        }



    }


}
