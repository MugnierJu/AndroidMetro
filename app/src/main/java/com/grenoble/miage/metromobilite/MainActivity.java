package com.grenoble.miage.metromobilite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;

import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button selectLineButton;
    public static final int REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    System.out.println("lines : ");
                    System.out.println(DataExtractor.getInstance().getTransportLignes());
                    System.out.println("stops : ");
                    System.out.println(DataExtractor.getInstance().getStops("SEM:C4"));
                    System.out.println("arrival : ");
                    System.out.println(DataExtractor.getInstance().getNextArrival("SEM:GENLAPOYA"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        selectLineButton = (Button) findViewById(R.id.selectLineButton);
        selectLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SelectLineActivity.class);
                startActivityForResult(addIntent, REQUEST_ID);
            }
        });











        //TO DELETE

        Spinner selectLine = (Spinner) findViewById(R.id.spinnerSelectLine);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<String> list =new ArrayList<String>();
        list.add("lezard");
        list.add("dans ta mere");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        // Specify the layout to use when the list of choices appears
        selectLine.setAdapter(adapter);

        TabHost mainContainer = (TabHost)findViewById(R.id.mainContainer);

        mainContainer.setup();

        //Tab 1
        TabHost.TabSpec spec = mainContainer.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Tab One");
        mainContainer.addTab(spec);

        //Tab 2
        spec = mainContainer.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Tab Two");
        mainContainer.addTab(spec);


        ListView lv1 = (ListView)findViewById(R.id.listView1);
        ListView lv2 = (ListView)findViewById(R.id.listView2);

        lv1.setAdapter(adapter);



        //TODO vérifier le tabHost
        //Changer l'interface : le faire sur 3 page, selection des ligne -> selection de l'arret -> selection du sens

    }
}
