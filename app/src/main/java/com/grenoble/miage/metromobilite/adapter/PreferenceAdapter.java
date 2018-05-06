package com.grenoble.miage.metromobilite.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.grenoble.miage.metromobilite.R;
import com.grenoble.miage.metromobilite.activity.PreferencesActivity;
import com.grenoble.miage.metromobilite.model.Arrival;
import com.grenoble.miage.metromobilite.model.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PreferenceAdapter extends BaseAdapter {

    Context context;
    HashMap<Preference,List<Arrival>> data;
    private static LayoutInflater inflater = null;
    List<Preference> preferenceList;

    public PreferenceAdapter(Context context,HashMap<Preference,List<Arrival>> data,List<Preference> preferenceList){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.preferenceList = preferenceList;
    }

    @Override
    public int getCount() {
        return preferenceList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.preference_item, null);

        TextView preferenceText = (TextView) view.findViewById(R.id.prefStopName);
        TextView prefShortName = (TextView) view.findViewById(R.id.prefShortName);
        ListView listView = (ListView) view.findViewById(R.id.prefArrival);
        ImageButton muteButton = (ImageButton) view.findViewById(R.id.mutePreference);
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deletePreference);


        if(!preferenceList.get(position).isMute()){
            //ContextCompat.getDrawable(context,R.mipmap.ic_lock_silent_mode_off);
            muteButton.setImageResource(R.mipmap.ic_lock_silent_mode_off);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                muteButton.setImageTintList(context.getResources().getColorStateList(R.color.colorGoodGreen));
            }
        }

        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesActivity preferencesActivity = (PreferencesActivity)context;
                preferencesActivity.loading();
                ImageButton muteB = (ImageButton)view;
                if(preferenceList.get(position).isMute()){
                    muteB.setImageResource(R.mipmap.ic_lock_silent_mode_off);
                    preferencesActivity.unMute(position);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        muteB.setImageTintList(context.getResources().getColorStateList(R.color.colorGoodGreen));
                    }
                }else{
                    muteB.setImageResource(R.mipmap.ic_lock_silent_mode);
                    preferencesActivity.mute();
                }
                preferencesActivity.notLoading();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesActivity preferencesActivity = (PreferencesActivity) context;
                preferencesActivity.deletePreference(position);

            }
        });

        prefShortName.setText(preferenceList.get(position).getLineName());
        prefShortName.getBackground().setColorFilter(Color.parseColor("#"+preferenceList.get(position).getColor()), PorterDuff.Mode.SRC_OVER);

        preferenceText.setText(preferenceList.get(position).getStopName()+" - "+preferenceList.get(position).getDirection());

        List<Arrival> arrivalList = data.get(preferenceList.get(position));
        if(arrivalList != null) {
            List<String> nextArrival = new ArrayList<>();
            for (Arrival arrival : arrivalList) {
                nextArrival.add(arrival.getTime() + " - " + arrival.getWaitTime());
            }

            ArrayAdapter<String> stopsAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, nextArrival);
            listView.setAdapter(stopsAdapter);
        }
        return view;
    }
}
