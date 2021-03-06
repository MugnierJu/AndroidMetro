package com.grenoble.miage.metromobilite.parsers;

import android.util.Log;

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
                if (jsonObject.getString("type").contains("PROXIMO")
                        || jsonObject.getString("type").contains("CHRONO")
                        || jsonObject.getString("type").contains("FLEXO")
                        || jsonObject.getString("type").contains("TRAM")
                        ) {
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
            }
        } catch (JSONException e) {
            Log.w("LineParser",e.getMessage());
        }

        return transportLines;
    }

}
