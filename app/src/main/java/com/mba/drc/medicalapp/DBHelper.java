package com.mba.drc.medicalapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    // Abstract away SQL tables so we have to do less
    // string concatenation bullshit
    private class Table{
        private Map<String, String> _map;
        private ArrayList<String> _key_mirror;
        private String _name;

        Table(String name){
            _map = new LinkedHashMap<String, String>();
            _name = name;
        }

        // New table column (don't call this after you create the table)
        void addColumn(String columnName, String type){
            _map.put(columnName, type);
            _key_mirror.add(columnName);
        }

        // Return where the column is (in a list of columns)
        int columnIndex(String columnName){
            return _key_mirror.indexOf(columnName);
        }

        // export as CREATE TABLE SQL query
        String asQuery(){
            String output = "CREATE TABLE IF NOT EXISTS "+_name+" (";
            Boolean passedFirst = false;
            for(String key: _map.values()){
                if(passedFirst){
                    output += ",";
                }
                output += key + " " + _map.get(key);
                passedFirst = true;
            }
            output += ")";
            return output;
        }
    }
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MedicalAppMBA";
    private static final String DRUG_TABLE_NAME = "DrugAlarm";
    private static final String EVENT_TABLE_NAME = "Event";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TIME = "time";
    private static final String KEY_DAYS = "days";
    private static final String KEY_DOSAGE = "dosage";
    private static final String KEY_URGENCY = "urgency";
    private static final String KEY_TIME_OCCURRED = "time_occurred";
    private static final String KEY_TIME_RESPONDED = "time_responded";
    private static final String KEY_DRUG_ID = "drug_alarm_id";
    private static final String KEY_RESPONSE = "response";
    private Table drug_table;
    private Table event_table;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        drug_table = new Table(DRUG_TABLE_NAME);
        drug_table.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        drug_table.addColumn(KEY_NAME, "TEXT");
        drug_table.addColumn(KEY_TIME, "INTEGER");
        drug_table.addColumn(KEY_DAYS, "INTEGER");
        drug_table.addColumn(KEY_DOSAGE, "TEXT");
        drug_table.addColumn(KEY_URGENCY, "INTEGER");

        event_table = new Table(EVENT_TABLE_NAME);
        event_table.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        event_table.addColumn(KEY_TIME_OCCURRED, "INTEGER");
        event_table.addColumn(KEY_TIME_RESPONDED, "INTEGER");
        event_table.addColumn(KEY_DRUG_ID, "INTEGER");
        event_table.addColumn(KEY_RESPONSE, "INTEGER");
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(drug_table.asQuery());
        db.execSQL(event_table.asQuery());
    }

    //upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldv, int newv){
        db.execSQL("DROP TABLE IF EXISTS "+DRUG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE_NAME);
        onCreate(db);
    }




}
