package com.grenoble.miage.metromobilite.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.grenoble.miage.metromobilite.R;

public class MyActivity extends AppCompatActivity {

    ProgressDialog progress;
    Context context;


    public void loading(){

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    public void notLoading(){
        if(progress != null){
            progress.dismiss();
        }
    }
}
