package com.grenoble.miage.metromobilite.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

@SuppressLint("Registered")
public class MyActivity extends AppCompatActivity {

    ProgressDialog progress;
    Context context;

    //tag of the logger
    private String TAG;

    public String getTAG() {
        if(TAG == null){
            setTAG("UndefinedActivity");
        }
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void loading(){
        if(progress == null){
            createProgressDialog();
        }
        progress.show();
    }

    public void notLoading(){
        if(progress != null){
            progress.dismiss();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createProgressDialog();
    }


    @Override
    public void onStop() {
        super.onStop();
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    private void createProgressDialog(){
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        //progress.setMessage("Wait!!");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setMessage("veuillez patienter");
    }
}
