package com.grenoble.miage.metromobilite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.grenoble.miage.metromobilite.model.MyExpandableListAdapter;
import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.parsers.LineParser;
import com.grenoble.miage.metromobilite.services.DataExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Created by mugnieju on 11/04/18.
 */

public class SelectLineActivity extends AppCompatActivity{
    ExpandableListAdapter listAdapter;
    ExpandableListView tramListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);

        // get the listview
        tramListView = (ExpandableListView) findViewById(R.id.lineList);

        // preparing list data
        prepareListData();
        listAdapter = new MyExpandableListAdapter(this,listDataHeader,listDataChild);

        // setting list adapter
        tramListView.setAdapter(listAdapter);

        tramListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Tram");
        listDataHeader.add("Chrono");
        listDataHeader.add("Proximo");
        listDataHeader.add("Flexo");

        //Getting the data of the lines from the API
        List<TransportLine> lines = new ArrayList<>();
        ExecutorService lineExecutor = Executors.newSingleThreadExecutor();
        Callable<String> lineGetterCallable = new Callable<String>() {
            @Override
            public String call(){
                return DataExtractor.getInstance().getTransportLignes();
            }
        };

        Future<String> futureLlines = lineExecutor.submit(lineGetterCallable);
        try {
            lines = new LineParser(futureLlines.get()).parse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Adding trams
        List<String> trams = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("TRAM")){
                trams.add(line.getLongName());
            }
        }

        // Adding chronos
        List<String> chronos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("CHRONO")){
                chronos.add(line.getLongName());
            }
        }

        // Adding proximos
        List<String> proximos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("PROXIMO")){
                proximos.add(line.getLongName());
            }
        }

        // Adding flexos
        List<String> flexos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("FLEXO")){
                flexos.add(line.getLongName());
            }
        }

        listDataChild.put(listDataHeader.get(0), trams);
        listDataChild.put(listDataHeader.get(1), chronos);
        listDataChild.put(listDataHeader.get(2), proximos);
        listDataChild.put(listDataHeader.get(3), flexos);
    }

}
