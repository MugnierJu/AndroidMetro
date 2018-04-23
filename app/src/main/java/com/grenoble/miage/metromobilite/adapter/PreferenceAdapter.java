package com.grenoble.miage.metromobilite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grenoble.miage.metromobilite.R;
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
        return data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = inflater.inflate(R.layout.preference_item, null);
        TextView text = (TextView) view.findViewById(R.id.prefName);


        text.setText(preferenceList.get(position).getLineLongName()+" - direction : "+preferenceList.get(position).getDirection());
        return view;
    }
}
