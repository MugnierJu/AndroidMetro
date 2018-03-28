package com.grenoble.miage.metromobilite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

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
                    System.out.println(DataExtractor.getInstance().getTransportLignes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        Spinner selectLine = (Spinner) findViewById(R.id.spinnerSelectLine);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<String> list =new ArrayList<String>();
        list.add("lezard");
        list.add("dans ta mere");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        // Specify the layout to use when the list of choices appears
        selectLine.setAdapter(adapter);


        //TODO vérifier le tabHost
        //Préparer l'appuie des boutons

    }
}
