package com.grenoble.miage.metromobilite.activity;


import android.os.Bundle;
import android.widget.ListView;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.adapter.PreferenceAdapter;
import com.grenoble.miage.metromobilite.controller.PreferencesHandler;
import com.grenoble.miage.metromobilite.controller.PreferencesLoader;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.Preference;


import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

        context = this;

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
    public void unMute(final int position){
        final PreferencesHandler preferencesHandler = new PreferencesHandler();

        preferencesHandler.unMutePreference(preferenceList,position,context);

        reloadAdapters();

    }

    /**
     * mute all the rpeferecnes
     */
    public  void mute(){
        final PreferencesHandler preferencesHandler = new PreferencesHandler();

        preferencesHandler.muteAllPreferences(context);

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
