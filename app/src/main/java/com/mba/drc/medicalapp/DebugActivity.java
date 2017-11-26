package com.mba.drc.medicalapp;

import android.app.AlarmManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug);


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String stringInfo = "...";
        try{
            stringInfo = String.format(Locale.US,
                    "%d",
                    alarmManager.getNextAlarmClock().getTriggerTime());
        }catch(Exception e){stringInfo="oh no";}

        TextView tv = findViewById(R.id.debugTV);
        tv.setText(stringInfo);

    }
}
