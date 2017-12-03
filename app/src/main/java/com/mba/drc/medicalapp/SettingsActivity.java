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
    private EditText snoozeET;
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        lowET = findViewById(R.id.lowUrgencyET);
        midET = findViewById(R.id.midUrgencyET);
        highET = findViewById(R.id.highUrgencyET);
        snoozeET = findViewById(R.id.snoozeET);
        setEditTexts();

    }

    private void setEditTexts(){
        lowET.setText(String.valueOf(
                preferences.getLong(Settings.LOW_URGENCY_TIMEOUT, Settings.LOW_URGENCY_TIMEOUT_DV)
                        /Settings.minute
        ));
        midET.setText(String.valueOf(
                preferences.getLong(Settings.MID_URGENCY_TIMEOUT, Settings.MID_URGENCY_TIMEOUT_DV)
                        /Settings.minute
        ));
        highET.setText(String.valueOf(
                preferences.getLong(Settings.HIGH_URGENCY_TIMEOUT, Settings.HIGH_URGENCY_TIMEOUT_DV)
                        /Settings.minute
        ));
        snoozeET.setText(String.valueOf(
                preferences.getInt(Settings.SNOOZE_TIME, Settings.SNOOZE_TIME_DV)
        ));

    }

    public void reset(View view){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putLong(Settings.LOW_URGENCY_TIMEOUT, Settings.LOW_URGENCY_TIMEOUT_DV);
        edit.putLong(Settings.MID_URGENCY_TIMEOUT, Settings.MID_URGENCY_TIMEOUT_DV);
        edit.putLong(Settings.HIGH_URGENCY_TIMEOUT, Settings.HIGH_URGENCY_TIMEOUT_DV);
        edit.putInt(Settings.SNOOZE_TIME, Settings.SNOOZE_TIME_DV);
        edit.apply();
        setEditTexts();
        Toast.makeText(this, "Restored to Default settings", Toast.LENGTH_SHORT).show();
    }

    public void changeSettings(View view){
        try {
            SharedPreferences.Editor edit = preferences.edit();
            final int low = Integer.parseInt(lowET.getText().toString())* Settings.minute;
            final int mid = Integer.parseInt(midET.getText().toString())* Settings.minute;
            final int high = Integer.parseInt(highET.getText().toString())* Settings.minute;
            final int snooze = Integer.parseInt(snoozeET.getText().toString());

            edit.putLong(Settings.LOW_URGENCY_TIMEOUT, low);
            edit.putLong(Settings.MID_URGENCY_TIMEOUT, mid);
            edit.putLong(Settings.HIGH_URGENCY_TIMEOUT, high);
            edit.putInt(Settings.SNOOZE_TIME, snooze);
            Toast.makeText(this, "Settings have been updated", Toast.LENGTH_SHORT).show();
            edit.apply();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        finish();
    }
}