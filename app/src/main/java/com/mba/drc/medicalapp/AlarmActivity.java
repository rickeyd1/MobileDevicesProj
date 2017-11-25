package com.mba.drc.medicalapp;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * This activity is created when an alarm goes off
 */

public class AlarmActivity extends AppCompatActivity {
    private Ringtone ringtone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snooze_screen);

        try {
            // Sound
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(this, uri);
            ringtone.play();

            // Create message
            final DBHelper dbHelper = new DBHelper(this);
            final Intent intent = getIntent();
            final TextView infoTV = findViewById(R.id.infoTV);
            final int id = intent.getIntExtra("id", -1);
            if (id == -1) {
                Toast.makeText(this, "Illegal identifier", Toast.LENGTH_LONG).show();
                return;
            }
            final DrugAlarm drugAlarm = dbHelper.getDrugAlarm(id);
            final String dosage = drugAlarm.dosage();
            final String name = drugAlarm.name();
            final String time = drugAlarm.time().toString();

            infoTV.setText(String.format(Locale.US,
                    "%s%s\n%s",
                    dosage.equals("") ? "" : dosage + " of ",
                    name, time
            ));
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void stop(View view){
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ringtone.stop();
    }
}
