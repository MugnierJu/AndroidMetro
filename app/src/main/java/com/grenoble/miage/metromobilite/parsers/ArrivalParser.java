package com.grenoble.miage.metromobilite.parsers;

import android.util.Log;

import com.grenoble.miage.metromobilite.activity.MyActivity;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.LineArrival;
import com.grenoble.miage.metromobilite.model.TransportLine;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrivalParser {

    private String toParse;
    private ArrayList<LineArrival> lineArrivals = new ArrayList<>();

    public ArrivalParser(String toParse) {
        this.toParse = toParse;
    }

    public ArrayList<LineArrival> parse(String lineId) {
        try {
            JSONArray jsonArray = new JSONArray(toParse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                //Analyse the pattern
                JSONObject pattern = (JSONObject) jsonObject.get("pattern");
                String id = (String) pattern.get("id");

                //Check if the list of arrivals is for the good line
                if (id.contains(lineId)) {
                    LineArrival lineArival = new LineArrival();

                    //And if the direction hasn't been already taken (weird stuff appended for the A tram)
                    boolean isDouble = false;
                    for(LineArrival existingLine : lineArrivals){
                        if(existingLine.getDirection().equals(pattern.getString("desc"))){
                            isDouble = true;
                        }
                    }
                    if(!isDouble && !pattern.getString("desc").contains("SANS VOYAGEUR BUS") && !pattern.getString("desc").contains("SANS VOYAGEUR TRAM")) {

                        lineArival.setDirection(pattern.getString("desc"));
                        JSONArray times = jsonObject.getJSONArray("times");

                        for (int j = 0; j < times.length(); j++) {
                            JSONObject jsonObject2 = (JSONObject) times.get(j);

                            //JSONObject jsonObject2 = (JSONObject) slide;

                            lineArival.addArrival(new Arrival(
                                    jsonObject2.optString("stopId"),
                                    jsonObject2.optString("stopName"),
                                    jsonObject2.optInt("scheduledArrival"),
                                    jsonObject2.optInt("scheduledDeparture"),
                                    jsonObject2.optInt("realtimeArrival"),
                                    jsonObject2.optInt("realtimeDeparture"),
                                    jsonObject2.optInt("arrivalDelay"),
                                    jsonObject2.optInt("departureDelay"),
                                    jsonObject2.optBoolean("timepoint"),
                                    jsonObject2.optBoolean("realtime"),
                                    jsonObject2.optInt("serviceDay"),
                                    jsonObject2.optInt("tripId")
                            ));
                        }
                        lineArrivals.add(lineArival);
                    }
                }
            }
        } catch (JSONException e) {
            Log.w("ArrivalParser",e.getMessage());
        }

        return lineArrivals;
    }

    public ArrayList<LineArrival> parse(TransportLine line) {
        try {
            JSONArray jsonArray = new JSONArray(toParse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                //Analyse the pattern
                JSONObject pattern = (JSONObject) jsonObject.get("pattern");
                String id = (String) pattern.get("id");

                //Check if the list of arrivals is for the good line
                if (id.contains(line.getId())) {
                    LineArrival lineArival = new LineArrival();

                    //And if the direction hasn't been already taken (weird stuff appended for the A tram)
                    boolean isDouble = false;
                    for(LineArrival existingLine : lineArrivals){
                        if(existingLine.getDirection().equals(pattern.getString("desc"))){
                            isDouble = true;
                        }
                    }
                    if(!isDouble && !pattern.getString("desc").equals("SANS VOYAGEUR BUS")) {

                        lineArival.setDirection(pattern.getString("desc"));
                        JSONArray times = jsonObject.getJSONArray("times");

                        for (int j = 0; j < times.length(); j++) {
                            JSONObject jsonObject2 = (JSONObject) times.get(j);

                            //JSONObject jsonObject2 = (JSONObject) slide;

                            lineArival.addArrival(new Arrival(
                                    jsonObject2.optString("stopId"),
                                    jsonObject2.optString("stopName"),
                                    jsonObject2.optInt("scheduledArrival"),
                                    jsonObject2.optInt("scheduledDeparture"),
                                    jsonObject2.optInt("realtimeArrival"),
                                    jsonObject2.optInt("realtimeDeparture"),
                                    jsonObject2.optInt("arrivalDelay"),
                                    jsonObject2.optInt("departureDelay"),
                                    jsonObject2.optBoolean("timepoint"),
                                    jsonObject2.optBoolean("realtime"),
                                    jsonObject2.optInt("serviceDay"),
                                    jsonObject2.optInt("tripId")
                            ));
                        }
                        lineArrivals.add(lineArival);
                    }
                }
            }
        } catch (JSONException e) {
            Log.w("ArrivalParser",e.getMessage());
        }

        return lineArrivals;
    }
}