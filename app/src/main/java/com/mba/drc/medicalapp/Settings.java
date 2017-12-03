package com.mba.drc.medicalapp;


/**
 * Gives the default value for settings
 */

final class Settings {
    private final static int second = 1000;
    private final static int minute = 60*second;
    private final static int hour = 60*minute;
    final static String LOW_URGENCY_TIMEOUT="low_urgency_timeout";
    final static String MID_URGENCY_TIMEOUT="mid_urgency_timeout";
    final static String HIGH_URGENCY_TIMEOUT="high_urgency_timeout";
    final static int LOW_URGENCY_TIMEOUT_DV=30*second;
    final static int MID_URGENCY_TIMEOUT_DV=5*minute;
    final static int HIGH_URGENCY_TIMEOUT_DV=24*hour;
    final static String SNOOZE_TIME="snooze_time";
    final static int SNOOZE_TIME_DV=1;// in minutes
}
