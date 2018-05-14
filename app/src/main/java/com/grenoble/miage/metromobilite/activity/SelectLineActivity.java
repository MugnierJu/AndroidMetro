package com.grenoble.miage.metromobilite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.adapter.MyExpandableListAdapter;
import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.model.TransportLineInView;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by mugnieju on 11/04/18.
 */

public class SelectLineActivity extends MyActivity{
    ExpandableListAdapter listAdapter;
    ExpandableListView tramListView;
    List<String> listDataHeader;
    HashMap<String, List<TransportLine>> listDataChild;

    public static final int REQUEST_ID = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_line);
        setTAG("SelectLine");

        // get the listview
        tramListView = (ExpandableListView) findViewById(R.id.lineList);

        // preparing list data
        final List<TransportLineInView> linesInView = prepareListData();
        listAdapter = new MyExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        tramListView.setAdapter(listAdapter);

        // execute when we click on a child
        tramListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                for(TransportLineInView line : linesInView){
                    if(line.isSameLine(groupPosition,childPosition)){
                        Intent addIntent = new Intent(SelectLineActivity.this, SelectStopActivity.class);
                        addIntent.putExtra("line", line.getLine());
                        startActivityForResult(addIntent, REQUEST_ID);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * Set all the datas of the lines
     * @return the lines and their positions in the view (ex : tram B is GroupePosition 0,ChildPosition 1, TransportLine object)
     */
    private List<TransportLineInView> prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Tram");
        listDataHeader.add("Chrono");
        listDataHeader.add("Proximo");
        listDataHeader.add("Flexo");

        //Getting the data of the lines from the API
        List<TransportLine> lines = new ArrayList<>();
        List<TransportLineInView> linesInView = new ArrayList<>();
        ExecutorService lineExecutor = Executors.newSingleThreadExecutor();
        Callable<String> lineGetterCallable = new Callable<String>() {
            @Override
            public String call(){
                return DataExtractor.getInstance().getTransportLignes();
            }
        };

        Future<String> futureLines = lineExecutor.submit(lineGetterCallable);
        try {
            lines = new LineParser(futureLines.get(15, TimeUnit.SECONDS)).parse();
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Log.w(getTAG(),e.getMessage());
        }

        // Adding trams
        List<TransportLine> trams = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("TRAM")){
                linesInView.add(new TransportLineInView(0,trams.size(),line));
                trams.add(line);
            }
        }

        // Adding chronos
        List<TransportLine> chronos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("CHRONO")){
                linesInView.add(new TransportLineInView(1,chronos.size(),line));
                chronos.add(line);
            }
        }

        // Adding proximos
        List<TransportLine> proximos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("PROXIMO")){
                linesInView.add(new TransportLineInView(2,proximos.size(),line));
                proximos.add(line);
            }
        }

        // Adding flexos
        List<TransportLine> flexos = new ArrayList<>();
        for(TransportLine line : lines){
            if(line.getType().equals("FLEXO")){
                linesInView.add(new TransportLineInView(3,flexos.size(),line));
                flexos.add(line);
            }
        }

        listDataChild.put(listDataHeader.get(0), trams);
        listDataChild.put(listDataHeader.get(1), chronos);
        listDataChild.put(listDataHeader.get(2), proximos);
        listDataChild.put(listDataHeader.get(3), flexos);


        return linesInView;
    }

}
