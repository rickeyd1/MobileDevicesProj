package com.mba.drc.medicalapp;

/**
 * This performs the actions necessary once an alarm runs
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Intent alarmActivity = new Intent(context, AlarmActivity.class);
        String[] identifiers = {"drug", "dosage", "time"};
        for(String identifier : identifiers){
            alarmActivity.putExtra(identifier, intent.getStringExtra(identifier));
        }
        context.startActivity(alarmActivity);
    }
}