package com.mba.drc.medicalapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.os.CountDownTimer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snooze_screen);


        try {

            // Sound
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            ringtone = RingtoneManager.getRingtone(this, uri);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            ringtone.setAudioAttributes(attributes);
            ringtone.play();

            // Unschedule in database by updating
            final Intent intent = getIntent();
            final DBHelper dbHelper = new DBHelper(this);

            id = intent.getIntExtra("id", -1);
            if (id == -1) {
                Toast.makeText(this, "Illegal identifier", Toast.LENGTH_LONG).show();
                return;
            }
            final DrugAlarm drugAlarm = dbHelper.getDrugAlarm(id);
            dbHelper.updateDrugAlarm(drugAlarm);
            // And reschedule
            dbHelper.scheduleAll(this, AlarmReceiver.class);

            // Create message
            final TextView infoTV = findViewById(R.id.infoTV);

            final String dosage = drugAlarm.dosage();
            final String name = drugAlarm.name();
            final String time = drugAlarm.time().toString();

            infoTV.setText(String.format(Locale.US,
                    "%s%s\n%s",
                    dosage.equals("") ? "" : dosage + " of ",
                    name, time
            ));


            // Stop ringtone after certain number of seconds
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            final int lowUrgencyTimeout = preferences.getInt(Settings.LOW_URGENCY_TIMEOUT,
                   Settings.LOW_URGENCY_TIMEOUT_DV);
            final int midUrgencyTimeout = preferences.getInt(Settings.MID_URGENCY_TIMEOUT,
                    Settings.MID_URGENCY_TIMEOUT_DV);
            final int highUrgencyTimeout = preferences.getInt(Settings.HIGH_URGENCY_TIMEOUT,
                    Settings.HIGH_URGENCY_TIMEOUT_DV);
            final int timeout = drugAlarm.urgency()==Urgency.LOW_URGENCY?lowUrgencyTimeout:
                    drugAlarm.urgency()==Urgency.MID_URGENCY?midUrgencyTimeout:highUrgencyTimeout;

            new CountDownTimer((long) timeout, (long)1){
                public void onTick(long timeRemaining){

                }
                public void onFinish(){
                    if(ringtone.isPlaying())ringtone.stop();
                }
            }.start();

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void dismiss(View view){
        this.finish();
    }

    public void snooze(View view){


        // Get unique ID
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        final String uniqueIntLabel = "unique_schedule_id";
        final int uniqueId = preferences.getInt(uniqueIntLabel, 1) + 1;
        edit.putInt(uniqueIntLabel, uniqueId+5);
        edit.apply();

        // Get snooze time in minutes
        final int snoozeTime = preferences.getInt(Settings.SNOOZE_TIME, Settings.SNOOZE_TIME_DV);

        // Prepare manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueId, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()+60*1000*snoozeTime, pendingIntent);

        this.finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (ringtone.isPlaying())
            ringtone.stop();
    }
}
