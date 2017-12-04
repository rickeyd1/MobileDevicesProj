package com.mba.drc.medicalapp;


/**
 * Gives the names of settings as well as the default values for them
 */

final class Settings {
    private final static int second = 1000;
    final static int minute = 60*second;
    private final static int hour = 60*minute;


    final static String LOW_URGENCY_TIMEOUT="low_urgency_timeout";
    final static String MID_URGENCY_TIMEOUT="mid_urgency_timeout";
    final static String HIGH_URGENCY_TIMEOUT="high_urgency_timeout";
    final static long LOW_URGENCY_TIMEOUT_DV=1*minute;
    final static long MID_URGENCY_TIMEOUT_DV=5*minute;
    final static long HIGH_URGENCY_TIMEOUT_DV=24*hour;

    final static String SNOOZE_TIME="snooze_time";
    final static int SNOOZE_TIME_DV=1;// in minutes
}
