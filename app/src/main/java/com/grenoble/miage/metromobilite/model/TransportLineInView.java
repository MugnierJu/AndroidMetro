package com.grenoble.miage.metromobilite.model;

/**
 * Used to know all the lignes and where to find them
 */
public class TransportLineInView {
    int groupPosition;
    int childPosition;
    TransportLine line;

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }

    public TransportLine getLine() {
        return line;
    }

    public void setLine(TransportLine line) {
        this.line = line;
    }

    public TransportLineInView(int groupPosition, int childPosition, TransportLine line) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.line = line;
    }

    /**
     * check if the line is at the same place in the view
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public boolean isSameLine(int groupPosition, int childPosition){
        if(groupPosition == this.groupPosition && childPosition == this.childPosition){
            return true;
        }
        return false;
    }
}
