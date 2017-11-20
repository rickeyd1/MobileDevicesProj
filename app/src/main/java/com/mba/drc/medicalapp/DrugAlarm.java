package com.mba.drc.medicalapp; /**
 * Created by dagan on 11/19/17.
 */
import com.mba.drc.medicalapp.DayGroup;

import java.time.LocalTime;

public class DrugAlarm {
    private String mName;
    private TimePack mTime;
    private DayGroup mDays;
    private String mDosage;
    private Urgency mUrgency;
    // Id in the database
    private int mId;

    DrugAlarm(){
        mName = "";
        mTime = new TimePack(0, 0);
        mDays = new DayGroup();
        mDosage = "";
        mUrgency = Urgency.MID_URGENCY;
    }

    String name(){return mName;}
    TimePack time(){return mTime;}
    DayGroup days(){return mDays;}
    String dosage(){return mDosage;}
    Urgency urgency(){return mUrgency;}
    int id(){return mId;}

    void setName(String n){mName = n;};
    void setTime(TimePack t){mTime = t;}
    void setDays(DayGroup d){mDays = d;}
    void setDosage(String d){mDosage = d;}
    void setUrgency(Urgency u){mUrgency = u;}
    void setId(int i){mId = i;}


}
