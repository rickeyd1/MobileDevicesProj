package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.TimePicker;

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


    }
}
