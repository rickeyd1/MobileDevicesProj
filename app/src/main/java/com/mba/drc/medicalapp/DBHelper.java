package com.mba.drc.medicalapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.LinkedHashMap;
import java.util.Locale;
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
            _key_mirror = new ArrayList<>();
            _map = new LinkedHashMap<>();
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

        int numberOfColumns(){
            return _key_mirror.size();
        }

        // export as CREATE TABLE SQL query
        String asQuery(){
            String output = "CREATE TABLE IF NOT EXISTS "+_name+" (";
            Boolean passedFirst = false;
            for(String key: _map.keySet()){
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
    private static final String KEY_SCHEDULE = "alarm_intent_id";// Shows the id of the AlarmManager intent
                                                    // or 0 if not scheduled
    private static final String KEY_TIME_OCCURRED = "time_occurred";
    private static final String KEY_TIME_RESPONDED = "time_responded";
    private static final String KEY_DRUG_ID = "drug_alarm_id";
    private static final String KEY_RESPONSE = "response";
    private Table drugTable;
    private Table eventTable;

    DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        drugTable = new Table(DRUG_TABLE_NAME);
        drugTable.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        drugTable.addColumn(KEY_NAME, "TEXT");
        drugTable.addColumn(KEY_TIME, "INTEGER");
        drugTable.addColumn(KEY_DAYS, "INTEGER");
        drugTable.addColumn(KEY_DOSAGE, "TEXT");
        drugTable.addColumn(KEY_URGENCY, "INTEGER");
        drugTable.addColumn(KEY_SCHEDULE, "INTEGER");

        eventTable = new Table(EVENT_TABLE_NAME);
        eventTable.addColumn(KEY_ID, "INTEGER PRIMARY KEY");
        eventTable.addColumn(KEY_TIME_OCCURRED, "INTEGER");
        eventTable.addColumn(KEY_TIME_RESPONDED, "INTEGER");
        eventTable.addColumn(KEY_DRUG_ID, "INTEGER");
        eventTable.addColumn(KEY_RESPONSE, "INTEGER");
    }



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(drugTable.asQuery());
        db.execSQL(eventTable.asQuery());
    }

    //upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldv, int newv){
        db.execSQL("DROP TABLE IF EXISTS "+DRUG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+EVENT_TABLE_NAME);
        onCreate(db);
    }

    // Wrap in quotes and make safe-ish...
    static private String sqlEscape(String string){
        string = string.replace('\'', '"');
        string = "'"+string+"'";
        return string;
    }
    
    void addDrugAlarm(DrugAlarm drugAlarm){
        SQLiteDatabase db = this.getWritableDatabase();
        final String name = drugAlarm.name();
        final int time = drugAlarm.time().toInt();
        final int days = drugAlarm.days().toInt();
        final String dosage = drugAlarm.dosage();
        final int urgency = drugAlarm.urgency().value();

        final String formatString =
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (%s, %d, %d, %s, %d, %d)";

        final String addStatement = String.format(Locale.US,
                formatString,
                DRUG_TABLE_NAME,
                KEY_NAME, KEY_TIME, KEY_DAYS, KEY_DOSAGE, KEY_URGENCY, KEY_SCHEDULE,
                sqlEscape(name), time, days, sqlEscape(dosage), urgency, 0);

        db.execSQL(addStatement);
        db.close();
    }

    void updateDrugAlarm(DrugAlarm drugAlarm){
        SQLiteDatabase db = this.getWritableDatabase();
        final int id = drugAlarm.id();
        final String name = drugAlarm.name();
        final int time = drugAlarm.time().toInt();
        final int days = drugAlarm.days().toInt();
        final String dosage = drugAlarm.dosage();
        final int urgency = drugAlarm.urgency().value();

        final String formatString =
                "UPDATE %s SET %s=%s, %s=%d, %s=%d, %s=%s, %s=%d, %s=%d WHERE %s=%d";

        final String updateStatement = String.format(Locale.US,
                formatString,
                DRUG_TABLE_NAME,
                KEY_NAME, sqlEscape(name),
                KEY_TIME, time,
                KEY_DAYS, days,
                KEY_DOSAGE, sqlEscape(dosage),
                KEY_URGENCY, urgency,
                KEY_ID, id,
                KEY_SCHEDULE, 0
                );

        db.execSQL(updateStatement);
        db.close();
    }

    // Delete a particular drug alarm
    void removeDrugAlarm(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        final String deleteStatement = String.format(
                Locale.US,
                "DELETE FROM %s WHERE %s=%d",
                DRUG_TABLE_NAME,
                KEY_ID,
                id
        );

        db.execSQL(deleteStatement);
        db.close();
    }

    // Return a particular drug alarm
    DrugAlarm getDrugAlarm(int id){
        return getDrugAlarmsByQuery(String.format(
                Locale.US,
                "SELECT * FROM %s WHERE id=%d", DRUG_TABLE_NAME, id)
        ).get(0);
    }

    // Return list of all drug alarms
    ArrayList<DrugAlarm> getDrugAlarms(){
        return getDrugAlarmsByQuery("SELECT * FROM "+DRUG_TABLE_NAME);
    }

    // Return a list of drug alarms as given by a particular query
    private ArrayList<DrugAlarm> getDrugAlarmsByQuery(String queryStatement){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<DrugAlarm> returnList = new ArrayList<>();
        // Iterate over table and keep adding drug alarms to list
        try(Cursor cursor = db.rawQuery(queryStatement, null)){
            while(cursor.moveToNext()){
                DrugAlarm drugAlarm = new DrugAlarm();
                drugAlarm.setId(
                        cursor.getInt(drugTable.columnIndex(KEY_ID))
                );
                drugAlarm.setName(
                        cursor.getString(drugTable.columnIndex(KEY_NAME))
                );
                drugAlarm.setDays(
                        // For days and time, we have to convert from ints to the class
                        new DayGroup(
                            cursor.getInt(drugTable.columnIndex(KEY_DAYS))
                        )
                );
                drugAlarm.setTime(
                        new TimePack(
                                cursor.getInt(drugTable.columnIndex(KEY_TIME))
                        )
                );
                drugAlarm.setDosage(
                        cursor.getString(
                                drugTable.columnIndex(KEY_DOSAGE)
                        )
                );
                drugAlarm.setUrgency(
                        Urgency.values()[cursor.getInt(drugTable.columnIndex(KEY_URGENCY))]
                );
                returnList.add(drugAlarm);
            }
        }
        return returnList;
    }




}
