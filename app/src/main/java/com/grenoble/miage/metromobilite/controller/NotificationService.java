package com.grenoble.miage.metromobilite.controller;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.activity.PreferencesActivity;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.Preference;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NotificationService implements Observer {

    private Context context;

    private int REQUEST_CODE = 1;
    private int NOTIFICATION_ID = 1;

    private Preference preference;

    public NotificationService(Context context){
        this.context = context;
    }


    @Override
    public void update(Observable o, Object arg) {
        preference =null;

        if(isAppIsInBackground()){

            Arrival arrivalToDisplay = getPrefArrival();

            // see if there is a arrival for a preference_item which is not mute
            if(arrivalToDisplay != null) {

                System.out.println("background");
                //TODO fix the notification system
                final NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                final Intent launchNotifiactionIntent = new Intent(context, PreferencesActivity.class);
                final PendingIntent pendingIntent = PendingIntent.getActivity(context,
                        REQUEST_CODE, launchNotifiactionIntent,
                        PendingIntent.FLAG_ONE_SHOT);

                Notification.Builder builder = new Notification.Builder(context)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("Metromobilité")
                        .setSmallIcon(R.drawable.circle)
                        .setContentTitle(preference.getLineName())
                        .setContentText(arrivalToDisplay.getTime())
                        .setContentIntent(pendingIntent);

                mNotification.notify(NOTIFICATION_ID, builder.build());
            }
        }else {
            System.out.println("foreground");
        }
    }

    private Arrival getPrefArrival(){
        PreferencesLoader preferencesLoader = PreferencesLoader.getInstance(context);

        for(Preference pref : preferencesLoader.getPreferenceList()){
            if(!pref.isMute()){
                preference =pref;
                List<Arrival> nextArrival =preferencesLoader.getNextArrivalList().get(pref);
                return nextArrival.get(0);
            }
        }
        return null;
    }

    /**
     * Check if the application is running on background or on foreground
     * @return
     */
    private boolean isAppIsInBackground() {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
