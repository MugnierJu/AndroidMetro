package com.grenoble.miage.metromobilite;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.LineArrival;
import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.model.TransportStop;
import com.grenoble.miage.metromobilite.parsers.ArrivalParser;
import com.grenoble.miage.metromobilite.parsers.StopParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SelectStopActivity extends AppCompatActivity {
    List<TransportStop> stopList;
    List<LineArrival> lineArrivalList;
    TransportStop selectedStrop = null;

    //view elements
    Spinner directionSpinner;
    ListView nextArrivalView;
    Spinner stopSpinner;

    //Adapters
    ArrayAdapter<String> stopsAdapter;
    ArrayAdapter<String> directionAdapter;
    ArrayAdapter<String> arrivalAdapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stop);
        context =this;

        final TransportLine transportLine = (TransportLine) getIntent().getExtras().get("line");

        TextView line = (TextView) findViewById(R.id.actualLine);
        line.setText(transportLine.getLongName());

        prepareStopListData(transportLine);


        //Stop treatment
        stopSpinner = (Spinner) findViewById(R.id.spinnerstop);

        List<String> stopsList = new ArrayList<>();
        for (TransportStop stop : stopList){
            stopsList.add(stop.getName());
        }

        stopsAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, stopsList);
        stopsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stopSpinner.setAdapter(stopsAdapter);


        //Instanciate the direction spinner and the list of next arrivals
        directionSpinner = (Spinner) findViewById(R.id.spinnerdirection);
        nextArrivalView = (ListView) findViewById(R.id.nextarrival);

        stopSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedStrop = stopList.get(position);
                //Next arrival treatment
                prepareArrivalListData(transportLine,selectedStrop);

                //set the destination
                List<String> directionList = new ArrayList<>();
                for(LineArrival lineArrival : lineArrivalList){
                    directionList.add(lineArrival.getDirection());
                }

                directionAdapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, directionList);
                directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                directionSpinner.setAdapter(directionAdapter);


                //set the next arrival view
                arrivalAdapter = new ArrayAdapter<String>(SelectStopActivity.this,
                        android.R.layout.simple_list_item_1, getArrivalForCurrentDirection());
                nextArrivalView.setAdapter(arrivalAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // nothing here yet
            }

        });

        directionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //set the next arrival view
                arrivalAdapter = new ArrayAdapter<String>(SelectStopActivity.this,
                        android.R.layout.simple_list_item_1, getArrivalForCurrentDirection());
                nextArrivalView.setAdapter(arrivalAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // nothing here yet
            }

        });
    }

    private List<String> getArrivalForCurrentDirection(){
        List<String> resultArrivals = new ArrayList<>();
        for(LineArrival line : lineArrivalList){
            if(directionSpinner.getSelectedItem().toString().equals(line.getDirection())){
                for(Arrival arrival : line.getListArrivals()){
                    resultArrivals.add(arrival.getTime());
                }
            }
        }
        return resultArrivals;
    }


    private void prepareStopListData(final TransportLine line){
        stopList = new ArrayList<>();

        //Getting the data of the lines from the API
        ExecutorService stopExecutor = Executors.newSingleThreadExecutor();
        Callable<String> stopGetterCallable = new Callable<String>() {
            @Override
            public String call(){
                return DataExtractor.getInstance().getStops(line);
            }
        };

        Future<String> futureStops = stopExecutor.submit(stopGetterCallable);
        try {
            stopList = new StopParser(futureStops.get(10, TimeUnit.SECONDS)).parse();
            //TODO handle the exceptions properly
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    public void prepareArrivalListData(TransportLine line, final TransportStop stop){
        lineArrivalList = new ArrayList<>();

        //Getting the data of the lines from the API
        ExecutorService arrivalExecutor = Executors.newSingleThreadExecutor();
        Callable<String> arrivalGetterCallable = new Callable<String>() {
            @Override
            public String call(){
                return DataExtractor.getInstance().getNextArrival(stop);
            }
        };

        Future<String> futureArrival = arrivalExecutor.submit(arrivalGetterCallable);
        try {
            lineArrivalList = new ArrivalParser(futureArrival.get(10, TimeUnit.SECONDS)).parse(line);
            //TODO handle the exceptions properly
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
