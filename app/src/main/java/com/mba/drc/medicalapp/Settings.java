package com.mba.drc.medicalapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Gives the default value for settings
 */

final class Settings {
    private final static int second = 1000;
    private final static int minute = 60*second;
    private final static int hour = 60*minute;
    final static int LOW_URGENCY_TIMEOUT=30*second;
    final static int MID_URGENCY_TIMEOUT=5*minute;
    final static int HIGH_URGENCY_TIMEOUT=24*hour;
    private final static int SNOOZE_TIME=1;// in minutes

    static int getSnoozeTime(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt("snooze_time", SNOOZE_TIME);

    }
    static void getLowUrgencyTimeout(){

    }
}