package com.grenoble.miage.metromobilite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.parsers.LineParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    LineParser lineParser = new LineParser(DataExtractor.getInstance().getTransportLines());
                    ArrayList<TransportLine> arr = lineParser.parse();
                    for (TransportLine t : arr) {
                        System.out.println(t.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
