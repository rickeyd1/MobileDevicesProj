package com.mba.drc.medicalapp;

/*
 * This performs the actions necessary once an alarm runs
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        // ...and that basically means just going to AlarmActivity
        Intent alarmActivity = new Intent(context, AlarmActivity.class);
        alarmActivity.putExtra("id", intent.getIntExtra("id", -1));
        context.startActivity(alarmActivity);
    }
}