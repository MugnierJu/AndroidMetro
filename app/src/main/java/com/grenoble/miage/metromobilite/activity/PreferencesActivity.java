package com.grenoble.miage.metromobilite.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.adapter.PreferenceAdapter;
import com.grenoble.miage.metromobilite.controller.PreferencesHandler;
import com.grenoble.miage.metromobilite.controller.PreferencesLoader;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.LineArrival;
import com.grenoble.miage.metromobilite.model.Preference;
import com.grenoble.miage.metromobilite.parsers.ArrivalParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PreferencesActivity extends MyActivity implements Observer {
    HashMap<Preference,List<Arrival>> preferenceListHashMap;
    ListView prefListView;
    PreferenceAdapter preferenceAdapter;
    List<Preference> preferenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        preferenceListHashMap = new HashMap<>();

        PreferencesLoader.getInstance(this);
        preparePrefenreces();

        prefListView = (ListView) findViewById(R.id.favView);
        preferenceAdapter = new PreferenceAdapter(this,preferenceListHashMap,preferenceList);
        prefListView.setAdapter(preferenceAdapter);

        
    }

    private void preparePrefenreces(){
        preferenceListHashMap =  PreferencesLoader.getInstance(this).getNextArrivalList();
        preferenceList = PreferencesLoader.getInstance(this).getPreferenceList();
    }

    @Override
    public void update(Observable o, Object arg) {
        preparePrefenreces();
    }

    /**
     * unMute a preference and mute all other
     * @param position the position of the preference
     */
    public void unMute(int position){
        PreferencesHandler preferencesHandler = new PreferencesHandler();
        preferencesHandler.unMutePreference(preferenceList,position,this);

        reloadAdapters();

    }

    /**
     * mute all the rpeferecnes
     */
    public  void mute(){
        PreferencesHandler preferencesHandler = new PreferencesHandler();
        preferencesHandler.muteAllPreferences(this);

        reloadAdapters();
    }

    public void deletePreference(int posisiton){
        PreferencesHandler preferencesHandler = new PreferencesHandler();
        preferencesHandler.deletePreference(preferenceList,posisiton,this);

        reloadAdapters();
    }


    private void reloadAdapters(){
        PreferencesLoader.getInstance(this).reloadPreferences();
        preferenceList = PreferencesLoader.getInstance(this).getPreferenceList();
        preferenceListHashMap =  PreferencesLoader.getInstance(this).getNextArrivalList();

        preferenceAdapter = new PreferenceAdapter(this,preferenceListHashMap,preferenceList);
        prefListView.setAdapter(preferenceAdapter);
    }
}
