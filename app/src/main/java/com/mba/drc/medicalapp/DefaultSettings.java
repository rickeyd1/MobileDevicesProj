package com.mba.drc.medicalapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class DefaultSettings extends Activity {
    private static int second = 1000;
    private static int minute = 60*second;
    private static int hour = 60*minute;
    public static int LOW_URGENCY_TIMEOUT=30*second;
    public static int MID_URGENCY_TIMEOUT=5*minute;
    public static int HIGH_URGENCY_TIMEOUT=24*hour;

    private EditText lowET;
    private EditText midET;
    private EditText highET;

    private int low;
    private int mid;
    private int high;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        lowET = (EditText) findViewById(R.id.lowUrgencyET);
        midET = (EditText) findViewById(R.id.midUrgencyET);
        highET = (EditText) findViewById(R.id.highUrgencyET);
    }

    public void reset(View view){
        second = 1000;
        minute = 60*second;
        hour = 60*minute;

        LOW_URGENCY_TIMEOUT = 30*second;
        MID_URGENCY_TIMEOUT = 5*minute;
        HIGH_URGENCY_TIMEOUT = 24*hour;

        Toast.makeText(this, "Restored to Default settings", Toast.LENGTH_SHORT).show();
    }

    public void changeSettings(View view){
        try {
            low = Integer.parseInt(lowET.getText().toString());
            mid = Integer.parseInt(midET.getText().toString());
            high = Integer.parseInt(highET.getText().toString());
        } catch (Exception e) {
            if(lowET.equals("")){
                lowET.setText("30");
            }
            if(midET.equals("")){
                midET.setText("30");
            }
            if(highET.equals("")){
                highET.setText("30");
            }
        }
        LOW_URGENCY_TIMEOUT = low*second;
        MID_URGENCY_TIMEOUT = mid*minute;
        HIGH_URGENCY_TIMEOUT = high*hour;
        Toast.makeText(this, "Settings have been updated", Toast.LENGTH_SHORT).show();
        finish();
    }
}
