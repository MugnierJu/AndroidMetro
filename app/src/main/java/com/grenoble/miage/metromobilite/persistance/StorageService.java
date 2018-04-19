package com.grenoble.miage.metromobilite.persistance;

import android.content.Context;

import java.util.List;

public interface StorageService {

    /**
     * InputStreamReader recomanded in order to read utf-8 values
     * @param ctx
     * @return
     */
    public String getValues(Context ctx);

    /**
     * OutputStreamWriter recomanded in order to write utf-8 values
     * @param value
     * @param ctx
     */
    public void addValue(String value,Context ctx);

    /**
     * OutputStreamWriter recomanded in order to write utf-8 values
     * @param value
     * @param ctx
     */
    public void removeValue(String value,Context ctx);

    public void clearAllValues(Context ctx);
}
