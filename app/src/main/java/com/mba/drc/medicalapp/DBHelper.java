package com.mba.drc.medicalapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.HashMap;
import java.util.Map;


public class DBHelper extends SQLiteOpenHelper {
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

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    class TableCreator{
        private Map<String, String> _map;
        private String _name;
        TableCreator(String name){
            _map = new HashMap<String, String>();
            _name = name;
        }
        public void setName(String name){_name = name;}
        public void addColumn(String columnName, String type){
            _map.put(columnName, type);
        }
        public String asQuery(){
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
    @Override
    public void onCreate(SQLiteDatabase db){
        TableCreator drug_table = new TableCreator(DRUG_TABLE_NAME);
        drug_table.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        drug_table.addColumn(KEY_NAME, "TEXT");
        drug_table.addColumn(KEY_TIME, "INTEGER");
        drug_table.addColumn(KEY_DAYS, "INTEGER");
        drug_table.addColumn(KEY_DOSAGE, "TEXT");
        drug_table.addColumn(KEY_URGENCY, "INTEGER");
        db.execSQL(drug_table.asQuery());

        TableCreator event_table = new TableCreator(EVENT_TABLE_NAME);
        event_table.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        event_table.addColumn(KEY_TIME_OCCURRED, "INTEGER");
        event_table.addColumn(KEY_TIME_RESPONDED, "INTEGER");
        event_table.addColumn(KEY_DRUG_ID, "INTEGER");
        event_table.addColumn(KEY_RESPONSE, "INTEGER");
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
