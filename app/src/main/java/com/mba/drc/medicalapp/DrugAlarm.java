package com.mba.drc.medicalapp;

/*
    Represents a reoccurring alarm
 */

import java.util.Calendar;

public class DrugAlarm {
    private String mName;
    private TimePack mTime;
    private DayGroup mDays;
    private String mDosage;
    private Urgency mUrgency;
    // Id in the database
    private int mId;
    // Scheduling ID - only for use in DBHelper.java
    private int mSchedule;

    DrugAlarm(){
        mName = "";
        mTime = new TimePack(0, 0);
        mDays = new DayGroup();
        mDosage = "";
        mUrgency = Urgency.MID_URGENCY;
        mSchedule = 0;
    }

    String name(){return mName;}
    TimePack time(){return mTime;}
    DayGroup days(){return mDays;}
    String dosage(){return mDosage;}
    Urgency urgency(){return mUrgency;}
    int id(){return mId;}
    int schedule(){return mSchedule;}

    void setName(String n){mName = n;}
    void setTime(TimePack t){mTime = t;}
    void setDays(DayGroup d){mDays = d;}
    void setDosage(String d){mDosage = d;}
    void setUrgency(Urgency u){mUrgency = u;}
    void setId(int i){mId = i;}
    void setSchedule(int s){mSchedule=s;}

    @Override
    public String toString(){
        return String.format("%-30s\t%-30s\t%-30s",
                name(),
                days().toString(),
                time().toString());
    }

    /* Return day of the week as so:
        Sunday - 0
        Monday - 1
        ...
        Saturday - 6
     */
    private int getCurrentDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
    }

    // Return day in same manner as getCurrentDay(), but this time it's the day of the next
    // occuring alarm
    private int getDayOffsetNextAlarm(){
        final int dayInt = days().toInt();
        final int currentDay = getCurrentDay();

        //If the time (within the scope of the day) is in the future,
        // There is no day offset
        if(((1<<currentDay) & dayInt) == (1<<currentDay)){
            if(System.currentTimeMillis() < time().getTimeInMillis()){
                return 0;
            }
        }

        // If, however, the time is in the past, it means
        // it occurs at a future day, so we must find that offset
        int i=1;
        int day = (currentDay+i)%7;
        for(;i<8;i++){
            if (((1<<day) & dayInt) == (1<<day)){
                return i;
            }
            day++;
            day%=7;
        }

        // Shouldn't happen
        throw new IllegalStateException("!!");
    }

    // Return the exact time of the next alarm in milliseconds
    long getNextAlarm(){
        final int DAY = 24*60*60*1000;//24 hours in terms of milliseconds
        final int dayOffset = getDayOffsetNextAlarm();
        return time().getTimeInMillis() + dayOffset * DAY;
    }

}
