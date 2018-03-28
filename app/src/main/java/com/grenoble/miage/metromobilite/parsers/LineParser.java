package com.grenoble.miage.metromobilite.parsers;

import com.grenoble.miage.metromobilite.model.TransportLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by rubata on 21/03/18.
 */

public class LineParser {

    private String toParse;
    private ArrayList<TransportLine> transportLines = new ArrayList<>();

    public LineParser(String toParse) {
        this.toParse = toParse;
    }

    public ArrayList<TransportLine> parse() {

        try {
            JSONArray jsonArray = new JSONArray(toParse);
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                transportLines.add(
                    new TransportLine(
                        jsonObject.getString("id"),
                        jsonObject.getString("shortName"),
                        jsonObject.getString("longName"),
                        jsonObject.getString("color"),
                        jsonObject.getString("textColor"),
                        jsonObject.getString("mode"),
                        jsonObject.getString("type")
                    )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return transportLines;
    }

}
