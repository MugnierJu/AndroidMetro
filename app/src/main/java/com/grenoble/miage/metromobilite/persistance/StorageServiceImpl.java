package com.grenoble.miage.metromobilite.persistance;

import android.content.Context;
import android.util.Log;

import com.grenoble.miage.metromobilite.activity.MyActivity;

import java.io.FileOutputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StorageServiceImpl implements StorageService {

    //Dirty...
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
            try (FileInputStream fis = openFileTORead(ctx)) {
                InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(fis), StandardCharsets.UTF_8);
                while ((c = reader.read()) != -1) {
                    temp = temp + Character.toString((char) c);
                }
                fis.close();
            }
        } catch (IOException e) {
            Log.w(((MyActivity) ctx).getTAG(),e.getMessage());
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
            Log.w(((MyActivity) ctx).getTAG(),e.getMessage());
        }
    }

    /**
     * Not implemented yet
     * @param value
     * @param ctx
     */
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
            Log.w(((MyActivity) ctx).getTAG(),e.getMessage());
        }
    }

    private FileOutputStream openFileToWrite(Context ctx){
        try {
            return ctx.openFileOutput(favFile, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            Log.w(((MyActivity) ctx).getTAG(),e.getMessage());
            return null;
        }
    }

    private FileInputStream openFileTORead(Context ctx){
        try {
            return ctx.openFileInput(favFile);
        } catch (FileNotFoundException e) {
            FileOutputStream outputStream = openFileToWrite(ctx);
            try {
                outputStream.close();
                return  openFileTORead(ctx);
            } catch (IOException e1) {
                Log.w(((MyActivity) ctx).getTAG(),e1.getMessage());
            }
            Log.w(((MyActivity) ctx).getTAG(),e.getMessage());
            return null;
        }
    }
}
