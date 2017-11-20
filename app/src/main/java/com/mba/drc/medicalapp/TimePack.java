package com.mba.drc.medicalapp;

/**
 * Created by dagan on 11/19/17.
 */

public class TimePack {
    int hour;
    int minute;
    TimePack(int theHour, int theMinute){
        hour = theHour;
        minute = theMinute;
    }

    // Must be able to convert to and fro integer
    // in order to store in SQL database
    int toInt(){
        int rv = hour<<16;// First 16 bits are the hour
        rv |= minute;// Last 16 bits are the minute
        return rv;
    }
}
