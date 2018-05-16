package com.grenoble.miage.metromobilite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grenoble.miage.metromobilite.R;

public class MainActivity extends MyActivity {

    private Button selectLineButton;
    private Button preferenceButton;
    private Button geolocalisationButton;
    public static final int REQUEST_ID_Line = 1;
    public static final int REQUEST_ID_Pref = 2;
    public static final int REQUEST_ID_Geol = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;

        Button selectLineButton = (Button) findViewById(R.id.selectLineButton);
        selectLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SelectLineActivity.class);
                startActivityForResult(addIntent, REQUEST_ID_Line);
            }
        });

        Button preferenceButton = (Button) findViewById(R.id.goToFavButton);
        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivityForResult(addIntent, REQUEST_ID_Pref);
            }
        });

        geolocalisationButton = (Button) findViewById(R.id.geolocalisation);
        geolocalisationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, GeolocalisationActivity.class);
                startActivityForResult(addIntent, REQUEST_ID_Geol);
            }
        });
    }
}
