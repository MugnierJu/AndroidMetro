package com.grenoble.miage.metromobilite.persistance;

import android.content.Context;

import java.io.FileOutputStream;

import java.io.*;

public class StorageServiceImpl implements StorageService {

    //TODO remove this
    private final String favFile = "favFile";

    private static StorageService instance = null;
    private StorageServiceImpl(){}

    public static StorageService getInstance(){
        if(instance == null){
            return  new StorageServiceImpl();
        }
        return instance;
    }

    @Override
    public String getValues(Context ctx) {
        int c;
        String temp="";
        try {
            FileInputStream fis = openFileTORead(ctx);
            while( (c = fis.read()) != -1){
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
        try {
            FileOutputStream fos =openFileToWrite(ctx);
            fos.write(value.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeValue(String value,Context ctx) {

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
