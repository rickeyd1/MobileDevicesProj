package com.mba.drc.medicalapp;

/**
 * Just stores hours and minutes
 */

class TimePack {
    private int mHour;
    private int mMinute;
    TimePack(int theHour, int theMinute){
        mHour = theHour;
        mMinute = theMinute;
    }

    TimePack(int timeAsInt){
        mHour = timeAsInt >> 16;
        mMinute = (timeAsInt<<16)>>16;
    }

    // Getters
    int hour(){return mHour;}
    int minute(){return mMinute;}


    // Must be able to convert to and fro integer
    // in order to store in SQL database
    int toInt(){
        int rv = mHour<<16;// First 16 bits are the hour
        rv |= mMinute;// Last 16 bits are the minute
        return rv;
    }



}
