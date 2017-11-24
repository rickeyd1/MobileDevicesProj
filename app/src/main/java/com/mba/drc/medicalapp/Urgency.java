package com.mba.drc.medicalapp;

/**
 * How urgent it is you take your meds
 */

public enum Urgency {
    LOW_URGENCY(0), MID_URGENCY(1), HIGH_URGENCY(2);

    private final int mValue;
    Urgency(int value){
        this.mValue = value;
    }
    int value(){
        return mValue;
    }

    public String toString(){
        switch(mValue){
            case 0:
                return "LOW_URGENCY";
            case 1:
                return "MID_URGENCY";
            case 2:
                return "HIGH_URGENCY";
            default:
                return "NULL_URGENCY";
        }
    }
}

