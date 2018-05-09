package com.grenoble.miage.metromobilite.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.grenoble.miage.metromobilite.R;

public class MyActivity extends AppCompatActivity {

    ProgressDialog progress;
    Context context;



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
