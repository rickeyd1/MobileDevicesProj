package com.mba.drc.medicalapp;

/* This class was generated by script */
public class DayGroup {
    private boolean mSunday;
    private boolean mMonday;
    private boolean mTuesday;
    private boolean mWednesday;
    private boolean mThursday;
    private boolean mFriday;
    private boolean mSaturday;

    public DayGroup() {
        mSunday = false;
        mMonday = false;
        mTuesday = false;
        mWednesday = false;
        mThursday = false;
        mFriday = false;
        mSaturday = false;
    }

    public boolean sunday(){return mSunday;}
    public boolean monday(){return mMonday;}
    public boolean tuesday(){return mTuesday;}
    public boolean wednesday(){return mWednesday;}
    public boolean thursday(){return mThursday;}
    public boolean friday(){return mFriday;}
    public boolean saturday(){return mSaturday;}

    public void setSunday(boolean s){ mSunday=s; }
    public void setSunday(){ mSunday=true; }
    public void setMonday(boolean s){ mMonday=s; }
    public void setMonday(){ mMonday=true; }
    public void setTuesday(boolean s){ mTuesday=s; }
    public void setTuesday(){ mTuesday=true; }
    public void setWednesday(boolean s){ mWednesday=s; }
    public void setWednesday(){ mWednesday=true; }
    public void setThursday(boolean s){ mThursday=s; }
    public void setThursday(){ mThursday=true; }
    public void setFriday(boolean s){ mFriday=s; }
    public void setFriday(){ mFriday=true; }
    public void setSaturday(boolean s){ mSaturday=s; }
    public void setSaturday(){ mSaturday=true; }
}
