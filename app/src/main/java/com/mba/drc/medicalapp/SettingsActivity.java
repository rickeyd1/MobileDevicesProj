package com.mba.drc.medicalapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Activity for the settings
 */

public class SettingsActivity extends AppCompatActivity {
    private EditText lowET;
    private EditText midET;
    private EditText highET;
    SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        lowET = (EditText) findViewById(R.id.lowUrgencyET);
        midET = (EditText) findViewById(R.id.midUrgencyET);
        highET = (EditText) findViewById(R.id.highUrgencyET);
        lowET.setText(String.valueOf(
                preferences.getInt(Settings.LOW_URGENCY_TIMEOUT, Settings.LOW_URGENCY_TIMEOUT_DV)
        ));
        lowET.setText(String.valueOf(
                preferences.getInt(Settings.MID_URGENCY_TIMEOUT, Settings.MID_URGENCY_TIMEOUT_DV)
        ));
        lowET.setText(String.valueOf(
                preferences.getInt(Settings.HIGH_URGENCY_TIMEOUT, Settings.HIGH_URGENCY_TIMEOUT_DV)
        ));
    }

    public void reset(View view){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(Settings.LOW_URGENCY_TIMEOUT, Settings.LOW_URGENCY_TIMEOUT_DV);
        edit.putInt(Settings.MID_URGENCY_TIMEOUT, Settings.MID_URGENCY_TIMEOUT_DV);
        edit.putInt(Settings.HIGH_URGENCY_TIMEOUT, Settings.HIGH_URGENCY_TIMEOUT_DV);
        edit.putInt(Settings.SNOOZE_TIME, Settings.SNOOZE_TIME_DV);
        edit.apply();
        Toast.makeText(this, "Restored to Default settings", Toast.LENGTH_SHORT).show();
    }

    public void changeSettings(View view){
        try {
            SharedPreferences.Editor edit = preferences.edit();
            final int low = Integer.parseInt(lowET.getText().toString());
            final int mid = Integer.parseInt(midET.getText().toString());
            final int high = Integer.parseInt(highET.getText().toString());

            edit.putInt(Settings.LOW_URGENCY_TIMEOUT, low);
            edit.putInt(Settings.MID_URGENCY_TIMEOUT, mid);
            edit.putInt(Settings.HIGH_URGENCY_TIMEOUT, high);
            Toast.makeText(this, "Settings have been updated", Toast.LENGTH_SHORT).show();
            edit.apply();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        finish();
    }
}