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

        // Create message
        final Intent intent = getIntent();
        final TextView infoTV = findViewById(R.id.infoTV);
        final String dosage = intent.getStringExtra("dosage");
        infoTV.setText(String.format(Locale.US,
                "%s%s\n%s",
                dosage.equals("")?"":dosage+" of ",
                intent.getStringExtra("drug"),
                intent.getStringExtra("time")
                ));

        // Sound
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();


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
