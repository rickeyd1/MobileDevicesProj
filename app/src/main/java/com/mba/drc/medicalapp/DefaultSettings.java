package com.mba.drc.medicalapp;

/**
 * Created by dagan on 11/26/17.
 */

final class DefaultSettings {
    private final static int second = 1000;
    private final static int minute = 60*second;
    private final static int hour = 60*minute;
    final static int LOW_URGENCY_TIMEOUT=30*second;
    final static int MID_URGENCY_TIMEOUT=5*minute;
    final static int HIGH_URGENCY_TIMEOUT=24*hour;
    final static int SNOOZE_TIME=1;// in minutes
}
