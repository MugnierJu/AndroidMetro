package com.grenoble.miage.metromobilite.persistance;

import android.content.Context;

import java.util.List;

public interface StorageService {

    public String getValues(Context ctx);

    public void addValue(String value,Context ctx);

    public void removeValue(String value,Context ctx);
}
