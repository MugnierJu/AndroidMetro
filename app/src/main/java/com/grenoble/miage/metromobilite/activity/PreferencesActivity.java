package com.grenoble.miage.metromobilite.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.model.LineArrival;
import com.grenoble.miage.metromobilite.parsers.ArrivalParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);


    }
}
