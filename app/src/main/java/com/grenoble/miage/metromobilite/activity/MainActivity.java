package com.grenoble.miage.metromobilite.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.controller.NotificationService;
import com.grenoble.miage.metromobilite.controller.PreferencesHandler;
import com.grenoble.miage.metromobilite.controller.PreferencesLoader;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.Preference;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends MyActivity {

    private Button selectLineButton;
    private Button preferenceButton;
    public static final int REQUEST_ID_Line = 1;
    public static final int REQUEST_ID_Pref = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        selectLineButton = (Button) findViewById(R.id.selectLineButton);
        selectLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SelectLineActivity.class);
                startActivityForResult(addIntent, REQUEST_ID_Line);
            }
        });

        //TODO faire activity favoris (xml + java) + créer un thread qui passe toutes les 30 secondes afin de redemander ou en est l'api + faire une notif si pas dans l'application

        preferenceButton = (Button) findViewById(R.id.goToFavButton);
        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivityForResult(addIntent, REQUEST_ID_Pref);
            }
        });

        PreferencesHandler preferencesHandler = new PreferencesHandler();
        //Activate the Preference loader and the notification service;
    }

}
