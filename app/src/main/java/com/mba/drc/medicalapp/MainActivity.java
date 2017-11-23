package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        dbHelper = new DBHelper(this);

        try {
            List<DrugAlarm> alarms = dbHelper.getDrugAlarms();
            DrugAlarm drugAlarm = new DrugAlarm();
            drugAlarm.setName("Robodril(not_in_db)");
            drugAlarm.setTime(new TimePack(8, 30));
            drugAlarm.setDays(new DayGroup());
            drugAlarm.setDosage("1 L");

            alarms.add(drugAlarm);
            alarms.add(drugAlarm);
            alarms.add(drugAlarm);



            ArrayAdapter<DrugAlarm> musicEventAdapter = new ArrayAdapter<DrugAlarm>(this,
                    R.layout.home_item,
                    R.id.list_item_event_textView,
                    alarms);
            ListView listView = (ListView) findViewById(R.id.listView_event);
            listView.setAdapter(musicEventAdapter);

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
