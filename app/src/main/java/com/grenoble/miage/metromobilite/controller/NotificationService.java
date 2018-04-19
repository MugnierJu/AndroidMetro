package com.grenoble.miage.metromobilite.controller;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.activity.MainActivity;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class NotificationService implements Observer {

    private Context context;

    private int REQUEST_CODE = 1;
    private int NOTIFICATION_ID = 1;

    public NotificationService(Context context){
        this.context = context;
    }


    @Override
    public void update(Observable o, Object arg) {
        if(isAppIsInBackground()){
            System.out.println("bacground");
            //TODO fix the notification system
            /*
            final NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            final Intent launchNotifiactionIntent = new Intent(context, MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    REQUEST_CODE, launchNotifiactionIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            Notification.Builder builder = new Notification.Builder(context)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("titre")
                   // .setSmallIcon(R.drawable.notification)
                    .setContentTitle("contentTitle")
                    .setContentText("duTexte ?")
                    .setContentIntent(pendingIntent);

            mNotification.notify(NOTIFICATION_ID, builder.build());
            */
        }else {
            System.out.println("foreground");
        }
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
