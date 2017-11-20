package com.mba.drc.medicalapp;

/**
 * Created by dagan on 11/19/17.
 */

public enum Urgency {
    LOW_URGENCY(1), MID_URGENCY(2), HIGH_URGENCY(4);

    private final int mValue;
    private Urgency(int value){
        this.mValue = value;
    }
    int value(){
        return mValue;
    }
}

