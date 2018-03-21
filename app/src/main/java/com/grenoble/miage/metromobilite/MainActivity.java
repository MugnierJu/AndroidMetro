package com.grenoble.miage.metromobilite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grenoble.miage.metromobilite.services.DataExtractor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    System.out.println(DataExtractor.getInstance().getTransportLignes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
