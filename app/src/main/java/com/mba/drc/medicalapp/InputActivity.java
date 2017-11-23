package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;

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

            // Create new DrugAlarm and add to DB
            DrugAlarm drugAlarm = new DrugAlarm();
            drugAlarm.setName(name);
            drugAlarm.setTime(time);
            drugAlarm.setDays(days);
            drugAlarm.setDosage(dosage);
            drugAlarm.setUrgency(urgency);
            dbHelper.addDrugAlarm(drugAlarm);

            // Return to main
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

    }
}
