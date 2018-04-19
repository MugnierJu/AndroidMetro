package com.grenoble.miage.metromobilite.persistance;

import android.content.Context;

import java.io.FileOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class StorageServiceImpl implements StorageService {

    //TODO remove this
    private final String favFile = "favFile";

    private static StorageService instance = null;
    private StorageServiceImpl(){}

    public static StorageService getInstance(){
        if(instance == null){
            instance = new StorageServiceImpl();
        }
        return instance;
    }

    @Override
    public String getValues(Context ctx) {
        int c;
        String temp="";
        try {
            FileInputStream fis = openFileTORead(ctx);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            while( (c = reader.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public void addValue( String value,Context ctx) {
        String oldValues = getValues(ctx);
        try {
            FileOutputStream fos =openFileToWrite(ctx);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(oldValues+value);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeValue(String value,Context ctx) {

    }

    @Override
    public void clearAllValues(Context ctx){
        try {
            FileOutputStream fos =openFileToWrite(ctx);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FileOutputStream openFileToWrite(Context ctx){
        try {
            return ctx.openFileOutput(favFile, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private FileInputStream openFileTORead(Context ctx){
        try {
            return ctx.openFileInput(favFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
