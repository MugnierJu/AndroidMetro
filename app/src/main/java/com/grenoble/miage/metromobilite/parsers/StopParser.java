package com.grenoble.miage.metromobilite.parsers;

import com.grenoble.miage.metromobilite.model.TransportStop;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;


/**
 * Created by rubata on 04/04/18.
 */

public class StopParser {

    private String toParse;
    private ArrayList<TransportStop> transportStops;

    public StopParser(String toParse) {
        this.toParse = toParse;
        transportStops = new ArrayList<>();
    }

    public ArrayList<TransportStop> parse() {

        try {
            JSONArray jsonArray = new JSONArray(toParse);
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                transportStops.add(
                        new TransportStop(
                                jsonObject.getString("code"),
                                jsonObject.getString("city"),
                                jsonObject.getString("name"),
                                jsonObject.getDouble("lon"),
                                jsonObject.getDouble("lat")
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transportStops;
    }

}
