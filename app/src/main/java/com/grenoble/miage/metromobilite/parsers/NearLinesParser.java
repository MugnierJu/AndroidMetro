package com.grenoble.miage.metromobilite.parsers;

import com.grenoble.miage.metromobilite.model.NearLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubata on 18/05/18.
 */

public class NearLinesParser {

    private String toParse;
    private List<NearLine> nearLines;

    public NearLinesParser(String toParse) {
        this.toParse = toParse;
        nearLines = new ArrayList<>();
    }

    public List<NearLine> parse() {

        try {
            JSONArray jsonArray = new JSONArray(toParse);
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                JSONArray arr = jsonObject.getJSONArray("lines");
                List<String> lines = new ArrayList<>();
                for (int j =0; j<arr.length(); j++) {
                    lines.add(arr.getString(j));
                }
                nearLines.add(
                        new NearLine(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                (float)jsonObject.getDouble("lon"),
                                (float)jsonObject.getDouble("lat"),
                                lines
                        )
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nearLines;
    }

}
