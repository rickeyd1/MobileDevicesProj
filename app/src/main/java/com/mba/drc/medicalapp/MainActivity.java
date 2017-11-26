package com.mba.drc.medicalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import android.support.v7.widget.Toolbar;
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

            Toolbar toolbar = (Toolbar) findViewById(R.id.the_toolbar);
            setSupportActionBar(toolbar);


        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void addNewDrugAlarm(View view){
        Intent inputActivity = new Intent(this, InputActivity.class);
        inputActivity.putExtra(INTENT_EXTRA_UPDATE_DRUG_ALARM, false);
        startActivity(inputActivity);
    }

    // Note: drugId is the SQL id
    public void updateDrugAlarm(int drugId){
        Intent inputActivity = new Intent(this, InputActivity.class);
        inputActivity.putExtra(INTENT_EXTRA_UPDATE_DRUG_ALARM, true);
        inputActivity.putExtra(INTENT_EXTRA_DRUG_ALARM_ID, drugId);
        startActivity(inputActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_debug:
                Intent intent = new Intent(this, DebugActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
