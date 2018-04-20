package com.grenoble.miage.metromobilite.controller;

import android.content.Context;

import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.LineArrival;
import com.grenoble.miage.metromobilite.model.Preference;
import com.grenoble.miage.metromobilite.parsers.ArrivalParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A service which refresh every 30 seconds the transports date for the preferences.
 */
public class PreferencesLoader extends Observable {

    private static PreferencesLoader instance = null;

    private List<Preference> prefList;

    private HashMap<Preference,List<Arrival>> nextArrivalList;

   // private List<Observer> observerList;

    private PreferencesLoader(final Context ctx){
        prefList = new ArrayList<>();
        nextArrivalList = new HashMap<>();
        //observerList = new ArrayList<>();

        ScheduledExecutorService prefLoaderExecutor = Executors.newSingleThreadScheduledExecutor();
        prefLoaderExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                HashMap<Preference,List<Arrival>> newArrivalList = new HashMap<>();

                PreferencesHandler prefHandler = new PreferencesHandler();
                prefList = prefHandler.getPreferences(ctx);
                for(final Preference pref : prefList){
                    if(!pref.isMute()) {
                        List<LineArrival> lineArrivalList = new ArrayList<>();

                        //Getting the data of each preference
                        ExecutorService arrivalExecutor = Executors.newSingleThreadExecutor();
                        Callable<String> arrivalGetterCallable = new Callable<String>() {
                            @Override
                            public String call() {
                                return DataExtractor.getInstance().getNextArrival(pref.getStopCode());
                            }
                        };

                        Future<String> futureArrival = arrivalExecutor.submit(arrivalGetterCallable);
                        try {
                            lineArrivalList = new ArrivalParser(futureArrival.get(10, TimeUnit.SECONDS)).parse(pref.getLineId());
                            //TODO handle the exceptions properly
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            e.printStackTrace();
                        }

                        // Take only the 2 next arrivals for the selected direction
                        List<Arrival> twoNextArrival = new ArrayList<>();
                        for (LineArrival lineArrival : lineArrivalList) {
                            if (lineArrival.getDirection().equals(pref.getDirection())) {
                                twoNextArrival.add(lineArrival.getListArrivals().get(0));
                                twoNextArrival.add(lineArrival.getListArrivals().get(1));
                            }
                        }
                        newArrivalList.put(pref, twoNextArrival);
                    }
                }
                nextArrivalList = newArrivalList;
                setChanged();
                notifyObservers();
            }
        }, 0, 5, TimeUnit.SECONDS);

    }

    public static PreferencesLoader getInstance(Context ctx){
        if(instance == null){
            instance = new PreferencesLoader(ctx);
        }
        return instance;
    }

    public List<Preference> getPreferenceList(){
        return prefList;
    }

    public HashMap<Preference,List<Arrival>> getNextArrivalList(){
        return nextArrivalList;
    }
}
