package com.grenoble.miage.metromobilite.controller;

import android.content.Context;

import com.grenoble.miage.metromobilite.model.Preference;
import com.grenoble.miage.metromobilite.persistance.StorageServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHandler {

    /**
     * Save a preference with the format [stopCode][stopName][lineId][lineLongName][direction][isMute];
     * @param pref
     * @param ctx
     */
    public void savePreference(Preference pref, Context ctx){
        String dataToSave = "["+pref.getStopCode()+"]["+pref.getStopName()+"]["+pref.getLineId()+"]["+pref.getLineLongName()+"]["+pref.getDirection()+"]["+pref.isMute()+"];";
        StorageServiceImpl.getInstance().addValue(dataToSave,ctx);
    }

    public List<Preference> getPreferences(Context ctx){
        List<Preference> preferenceList = new ArrayList<>();
        String savedDatas = StorageServiceImpl.getInstance().getValues(ctx);

        String[] preferences = savedDatas.split(";");
        for(String preference : preferences){
            Preference newPref = new Preference();
            String[] attributes = preference.split("]");
            for(int i=0;i<attributes.length;i++){
                attributes[i] = attributes[i].replace("[", "");

                switch (i) {
                    case 0: newPref.setStopCode(attributes[i]);
                        break;
                    case 1: newPref.setStopName(attributes[i]);
                        break;
                    case 2: newPref.setLineId(attributes[i]);
                        break;
                    case 3: newPref.setLineLongName(attributes[i]);
                        break;
                    case 4: newPref.setDirection(attributes[i]);
                        break;
                    case 5: newPref.setMute(Boolean.getBoolean(attributes[i]));
                        break;
                    default:
                        break;
                }
            }
            preferenceList.add(newPref);
        }
        return preferenceList;
    }

    public void clearPreferences(Context ctx){
        StorageServiceImpl.getInstance().clearAllValues(ctx);
    }
}
