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

    public DrugAlarm(){
        mName = "";
        mTime = new TimePack(0, 0);
        mDays = new DayGroup();
        mDosage = "";
        mUrgency = Urgency.MID_URGENCY;
    }

    public String name(){return mName;}
    public TimePack time(){return mTime;}
    public DayGroup days(){return mDays;}
    public String dosage(){return mDosage;}
    public Urgency urgency(){return mUrgency;}

    public void setName(String n){mName = n;};
    public void setTime(TimePack t){mTime = t;}
    public void setDays(DayGroup d){mDays = d;}
    public void setUrgency(Urgency u){mUrgency = u;}


}
