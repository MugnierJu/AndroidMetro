package com.grenoble.miage.metromobilite.model;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.grenoble.miage.metromobilite.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header data
    private HashMap<String, List<String>> listDataChild; // child data and title

    public MyExpandableListAdapter(Context context, List<String> listDataHeader,HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size() ;
    }

    @Override
    public int getChildrenCount(int groupId) {
        try {
            return this.listDataChild.get(this.listDataHeader.get(groupId)).size();
        }catch (NullPointerException e){
            //TODO vomitif... mettre un logger
            System.out.println("You are trying to open an empty or a corrupted list : "+e);
        }
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return this.listDataHeader.get(i);
    }

    /**
     *  return the select child
     * @param groupId  group position
     * @param childId child poisition
     * @return
     */
    @Override
    public Object getChild(int groupId, int childId) {
        return this.listDataChild.get(this.listDataHeader.get(groupId)).get(childId);
    }

    /**
     * dumb method;
     * @param i
     * @return
     */
    @Override
    public long getGroupId(int i) {
        return i;
    }

    /**
     * dumb method
     * @param i  group position
     * @param i1 child poisition
     * @return
     */
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupId, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(groupId);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return view;
    }

    @Override
    public View getChildView(int groupId, int childId, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(groupId, childId);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
