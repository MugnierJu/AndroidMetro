package com.grenoble.miage.metromobilite.services;

import android.util.Log;

import com.grenoble.miage.metromobilite.activity.MyActivity;
import com.grenoble.miage.metromobilite.model.TransportLine;
import com.grenoble.miage.metromobilite.model.TransportStop;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by rubata on 21/03/18.
 */

public class DataExtractor{

    private URL transportLigneUrl ;
    private String stop ;
    private String arrival ;
    private String endArrival;
    private String endStop;

    private static DataExtractor instance = null;

    private DataExtractor(){
        //sale
        try {

            //Really dirty
            transportLigneUrl = new URL("https://data.metromobilite.fr/api/routers/default/index/routes");
            stop = "https://data.metromobilite.fr/api/routers/default/index/routes/";
            arrival = "https://data.metromobilite.fr/api/routers/default/index/clusters/";
            endArrival = "/stoptimes";
            endStop = "/clusters";
        } catch (MalformedURLException e) {
            Log.w("DataExtractor",e.getMessage());
        }
    }

    public static DataExtractor getInstance(){
        if(instance == null){
            instance = new DataExtractor();
        }

        return instance;
    }

    /**
     *
     * @return String comportant la liste de tous les transports
     */
    public String getTransportLignes(){
        String lines = "";
        try {

            //Ouvrir la connexion
            HttpURLConnection conn = (HttpURLConnection)transportLigneUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();

            //devrait tester quelle exception est renvoyée

            if(response == 200)
            {
                Scanner sc = new Scanner(transportLigneUrl.openStream());
                while(sc.hasNext())
                {
                    lines+=sc.nextLine();
                }
                sc.close();
            }


        } catch (IOException e) {
            Log.w("DataExtractor",e.getMessage());
        }

        return lines;
    }


    /**
     * Get the stops from a line
     * @param line
     * @return
     */
    public String getStops(TransportLine line){
        String stops = "";
        try {
            URL stopUrl = new URL(stop+line.getId()+ endStop);
            //Ouvrir la connexion
            HttpURLConnection conn = (HttpURLConnection)stopUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();
            
            if(response == 200)
            {
                Scanner sc = new Scanner(stopUrl.openStream());
                while(sc.hasNext())
                {
                    stops+=sc.nextLine();
                }
                sc.close();
            }


        } catch (IOException e) {
            Log.w("DataExtractor",e.getMessage());
        }

        return stops;
    }

    /**
     * Get the next arrivals for a stop
     * @param stop
     * @return
     */
    public String getNextArrival(TransportStop stop){
        String arrivals = "";
        try {
            URL stopUrl = new URL(arrival+stop.getCode()+endArrival);

            //Ouvrir la connexion
            HttpURLConnection conn = (HttpURLConnection)stopUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();

            //dervrai test quel exception est renvoyée

            if(response == 200)
            {
                Scanner sc = new Scanner(stopUrl.openStream());
                while(sc.hasNext())
                {
                    arrivals+=sc.nextLine();
                }
                sc.close();
            }


        } catch (IOException e) {
            Log.w("DataExtractor",e.getMessage());
        }

        return arrivals;
    }

    /**
     * Return the next arrivals for the selected stop
     * @param stopCode the code of the stop in String
     * @return
     */
    public String getNextArrival(String stopCode){
        String arrivals = "";
        try {

            URL stopUrl = new URL(arrival+stopCode+endArrival);

            //Ouvrir la connexion
            HttpURLConnection conn = (HttpURLConnection)stopUrl.openConnection();

            conn.connect();

            int response = conn.getResponseCode();

            //dervrai test quel exception est renvoyée

            if(response == 200)
            {
                Scanner sc = new Scanner(stopUrl.openStream());
                while(sc.hasNext())
                {
                    arrivals+=sc.nextLine();
                }
                sc.close();
            }


        } catch (IOException e) {
            Log.w("DataExtractor",e.getMessage());
        }

        return arrivals;

    }
}
