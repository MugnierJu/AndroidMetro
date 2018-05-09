package com.grenoble.miage.metromobilite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.controller.NotificationService;
import com.grenoble.miage.metromobilite.controller.PreferencesLoader;

/**
 * Create in order to let the PreferenceLoader start to load the preferences data from the API without delaying the other prosses at the begining
 */
public class BootingActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booting);
        context = this;

        final ProgressBar bootingBar = (ProgressBar) findViewById(R.id.bootingBar);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    PreferencesLoader.getInstance(context).addObserver(new NotificationService(context));
                    for(int i = 0; i <= bootingBar.getMax();i+= bootingBar.getMax()/100){
                        sleep(25);
                        bootingBar.setProgress(i);
                    }

                } catch (Exception e) {
                    System.out.println(e);
                } finally {

                    Intent i = new Intent(BootingActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();

    }
}
