package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;

/**
 * Created by dagan on 11/23/17.
 */

public class InputActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText nameET;
    TimePicker timeTP;
    CheckBox sunCB;
    CheckBox monCB;
    CheckBox tuesCB;
    CheckBox wedCB;
    CheckBox thursCB;
    CheckBox friCB;
    CheckBox satCB;
    EditText dosageET;
    RadioGroup urgencyRG;
    DrugAlarm drugAlarm;
    boolean updateExisting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        nameET = findViewById(R.id.drug_et);
        timeTP = findViewById(R.id.time_tp);
        dosageET = findViewById(R.id.dosage_et);
        urgencyRG = findViewById(R.id.urgency_rg);

        sunCB = findViewById(R.id.sunCheck);
        monCB = findViewById(R.id.monCheck);
        tuesCB = findViewById(R.id.tuesCheck);
        wedCB = findViewById(R.id.wedCheck);
        thursCB = findViewById(R.id.thursCheck);
        friCB = findViewById(R.id.friCheck);
        satCB = findViewById(R.id.satCheck);

        dbHelper = new DBHelper(this);

        // Check if we're updating an existing thing-a-mahoozit
        Intent intent = getIntent();
        if(intent.getBooleanExtra(MainActivity.INTENT_EXTRA_UPDATE_DRUG_ALARM, false)){
            updateExisting = true;
            int drugId = intent.getIntExtra(MainActivity.INTENT_EXTRA_DRUG_ALARM_ID, -1);
            drugAlarm = dbHelper.getDrugAlarm(drugId);

            // Populate edit texts and such
            nameET.setText(drugAlarm.name());
            dosageET.setText(drugAlarm.dosage());
            timeTP.setCurrentHour(drugAlarm.time().hour());
            timeTP.setCurrentMinute(drugAlarm.time().minute());
            sunCB.setChecked(drugAlarm.days().sunday());
            monCB.setChecked(drugAlarm.days().monday());
            tuesCB.setChecked(drugAlarm.days().tuesday());
            wedCB.setChecked(drugAlarm.days().wednesday());
            thursCB.setChecked(drugAlarm.days().thursday());
            friCB.setChecked(drugAlarm.days().friday());
            satCB.setChecked(drugAlarm.days().saturday());
            switch(drugAlarm.urgency()){
                case LOW_URGENCY:
                    urgencyRG.check(R.id.low_urgency_rb);
                    break;
                case MID_URGENCY:
                    urgencyRG.check(R.id.medium_urgency_rb);
                    break;
                case HIGH_URGENCY:
                    urgencyRG.check(R.id.high_urgency_db);
                    break;
            }

            // Make different title
            ((TextView)findViewById(R.id.IL_Title)).setText(R.string.input_title_alternative);
            Button okButton = findViewById(R.id.input_ok_button);
            Button removeButton = findViewById(R.id.input_remove_button);
            okButton.setText(R.string.input_ok_button_alternative);
            removeButton.setVisibility(View.VISIBLE);

        }

    }

    public void cancel(View view){
        // Return to main
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    public void remove(View view){
        dbHelper.removeDrugAlarm(drugAlarm.id());
        cancel(view);
    }
    public void add(View view){
        // Get and validate all inputs
        String name;
        TimePack time;
        DayGroup days = new DayGroup();
        String dosage;
        Urgency urgency;
        try {
            name = nameET.getText().toString();
            if(name.equals("")){
                Toast.makeText(this, "Please enter a drug name", Toast.LENGTH_LONG).show();
                return;
            }
            time = new TimePack(timeTP.getCurrentHour(), timeTP.getCurrentMinute());
            dosage = dosageET.getText().toString();
            days.setSunday(sunCB.isChecked());
            days.setMonday(monCB.isChecked());
            days.setTuesday(tuesCB.isChecked());
            days.setWednesday(wedCB.isChecked());
            days.setThursday(thursCB.isChecked());
            days.setFriday(friCB.isChecked());
            days.setSaturday(satCB.isChecked());
            if (days.toInt() == 0){
                Toast.makeText(this, "Please select at least one day", Toast.LENGTH_LONG).show();
                return;
            }
            urgency = Urgency.MID_URGENCY;

        }catch(Exception e){
            Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show();
            return;
        }

        try {

            // Create new or existing DrugAlarm and add/update to DB
            if(!updateExisting) {
                drugAlarm = new DrugAlarm();
            }
            drugAlarm.setName(name);
            drugAlarm.setTime(time);
            drugAlarm.setDays(days);
            drugAlarm.setDosage(dosage);
            drugAlarm.setUrgency(urgency);
            if(updateExisting){
                dbHelper.updateDrugAlarm(drugAlarm);
            }else {
                dbHelper.addDrugAlarm(drugAlarm);
            }

            // Run scheduler
            dbHelper.scheduleAll(this, AlarmReceiver.class);

            // Return to main
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }


}
