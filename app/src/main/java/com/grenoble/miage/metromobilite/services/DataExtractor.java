package com.grenoble.miage.metromobilite.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by rubata on 21/03/18.
 */

public class DataExtractor{

    private URL transportLigneUrl ;

    private static DataExtractor instance = null;

    private DataExtractor(){
        //sale
        try {
            transportLigneUrl = new URL("https://data.metromobilite.fr/api/routers/default/index/routes");
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
     * @return String comportant la liste de tous les trnasports
     */
    public String getTransportLines(){
        String inline = "";
        try {

            //Ouvrir la connexion
            HttpURLConnection conn = (HttpURLConnection)transportLigneUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int response = conn.getResponseCode();

            //dervrai test quel exception est renvoyée

            if(response == 200)
            {
                Scanner sc = new Scanner(transportLigneUrl.openStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                sc.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return inline;
    }


}
