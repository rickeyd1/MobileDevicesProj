package com.mba.drc.medicalapp;

import android.content.Intent;
import android.content.res.Resources;
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
 * Created by dagan on 11/24/17.
 */

public class AlarmActivity extends AppCompatActivity {
    private Ringtone ringtone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snooze_screen);

        Intent intent = getIntent();
        TextView infoTV = findViewById(R.id.infoTV);
        infoTV.setText(String.format(Locale.US,
                "%s of %s\n%s",
                intent.getStringExtra("drug"),
                intent.getStringExtra("dosage"),
                intent.getStringExtra("time")
                ));

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
        ringtone.play();


    }

    public void stop(View view){
        ringtone.stop();
    }
}
