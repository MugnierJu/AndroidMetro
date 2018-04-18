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

import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.parsers.LineParser;
import com.grenoble.miage.metromobilite.persistance.StorageServiceImpl;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button selectLineButton;
    public static final int REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectLineButton = (Button) findViewById(R.id.selectLineButton);
        selectLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SelectLineActivity.class);
                startActivityForResult(addIntent, REQUEST_ID);
            }
        });

    }
}
