package com.grenoble.miage.metromobilite.controller;

import android.content.Context;

import com.grenoble.miage.metromobilite.model.Preference;
import com.grenoble.miage.metromobilite.persistance.StorageServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHandler {

    /**
     * Save a preference_item with the format [stopCode][stopName][lineId][lineLongName][color][direction][isMute];
     * @param pref
     * @param ctx
     */
    public void savePreference(Preference pref, Context ctx){
        muteAllPreferences(ctx);
        String dataToSave = "["+pref.getStopCode()+"]["+pref.getStopName()+"]["+pref.getLineId()+"]["+pref.getLineName()+"]["+pref.getColor()+"]["+pref.getDirection()+"]["+false+"];";
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
                    case 3: newPref.setLineName(attributes[i]);
                        break;
                    case 4: newPref.setColor(attributes[i]);
                        break;
                    case 5: newPref.setDirection(attributes[i]);
                        break;
                    case 6:
                            if(attributes[i].equals("true")){
                                newPref.setMute(true);
                            }else{
                                newPref.setMute(false);
                            }
                        break;
                    default:
                        break;
                }
            }
            // sometimes artifacts came into the the new prefs
            if (newPref.getStopCode() != null && newPref.getLineId() != null && !newPref.getStopCode().equals("null") && !newPref.getLineId().equals("null")) {
                preferenceList.add(newPref);
            }

        }
        return preferenceList;
    }

    /**
     * clear all the registred prefrences
     * @param ctx
     */
    public void clearPreferences(Context ctx){
        StorageServiceImpl.getInstance().clearAllValues(ctx);
    }

    /**
     * Set all the preferences mute attibute to true;
     * @param ctx
     */
    public void muteAllPreferences(Context ctx){
        List<Preference> prefList = getPreferences(ctx);
        clearPreferences(ctx);

        for(Preference pref : prefList){
            String dataToSave = "["+pref.getStopCode()+"]["+pref.getStopName()+"]["+pref.getLineId()+"]["+pref.getLineName()+"]["+pref.getColor()+"]["+pref.getDirection()+"]["+true+"];";
            StorageServiceImpl.getInstance().addValue(dataToSave,ctx);
        }
    }

    /**
     *
     * @param prefs the list of preferences
     * @param position the position of the preferecne to unmute
     * @param ctx
     */
    public void unMutePreference(List<Preference> prefs,int position,Context ctx){
        clearPreferences(ctx);

        prefs.get(position).setMute(false);
        Preference unMutedPref = prefs.get(position);

        for(Preference pref : prefs){

            if(unMutedPref.equals(pref)) {
                String dataToSave = "[" + pref.getStopCode() + "][" + pref.getStopName() + "][" + pref.getLineId() + "][" + pref.getLineName() + "][" + pref.getColor() + "][" + pref.getDirection() + "][" + false + "];";
                StorageServiceImpl.getInstance().addValue(dataToSave, ctx);
            }else{
                String dataToSave = "[" + pref.getStopCode() + "][" + pref.getStopName() + "][" + pref.getLineId() + "][" + pref.getLineName() + "][" + pref.getColor() + "][" + pref.getDirection() + "][" + true + "];";
                StorageServiceImpl.getInstance().addValue(dataToSave, ctx);
            }
        }
    }


    /**
     *
     * @param prefs list of all prefs
     * @param position pref to delete
     * @param ctx
     */
    public void deletePreference(List<Preference> prefs,int position,Context ctx){
        prefs.remove(prefs.get(position));
        clearPreferences(ctx);

        for (Preference pref : prefs){
            savePreference(pref,ctx);
        }

    }
}
