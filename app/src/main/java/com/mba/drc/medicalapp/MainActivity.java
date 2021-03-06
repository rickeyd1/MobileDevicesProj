package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    static final String INTENT_EXTRA_UPDATE_DRUG_ALARM = "update_drug_alarm";
    static final String INTENT_EXTRA_DRUG_ALARM_ID = "drug_alarm_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        DBHelper dbHelper = new DBHelper(this);
        // Make sure there are no missed alarms
        dbHelper.unscheduleMissed();
        dbHelper.scheduleAll(this, AlarmReceiver.class);

        // Populate the list of alarms on the layout
        final List<DrugAlarm> alarms;
        try {
            alarms = dbHelper.getDrugAlarms();


            ArrayAdapter<DrugAlarm> drugAlarmAdapter = new ArrayAdapter<>(this,
                    R.layout.home_item,
                    R.id.list_item_event_textView,
                    alarms);
            ListView listView = findViewById(R.id.listView_event);
            listView.setAdapter(drugAlarmAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    final int drugId = alarms.get(position).id();
                    updateDrugAlarm(drugId);
                }
            });

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    // Go to the InputActivity in add mode
    public void addNewDrugAlarm(View view){
        Intent inputActivity = new Intent(this, InputActivity.class);
        inputActivity.putExtra(INTENT_EXTRA_UPDATE_DRUG_ALARM, false);
        startActivity(inputActivity);
    }

    // Go to the InputActivity in edit mode
    // Note: drugId is the SQL id
    public void updateDrugAlarm(int drugId){
        Intent inputActivity = new Intent(this, InputActivity.class);
        inputActivity.putExtra(INTENT_EXTRA_UPDATE_DRUG_ALARM, true);
        inputActivity.putExtra(INTENT_EXTRA_DRUG_ALARM_ID, drugId);
        startActivity(inputActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuitem_Settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
