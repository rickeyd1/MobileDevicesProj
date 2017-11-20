package com.mba.drc.medicalapp;

/**
 * Created by dagan on 11/19/17.
 */

public class DrugAlarmEvent extends DrugAlarm{
    private TimePack mTimeOccurred;
    private TimePack mTimeResponded;
    private Response mResponse;

    public DrugAlarmEvent(){
        super();
        mTimeOccurred = new TimePack(0, 0);
        mTimeResponded = new TimePack(0, 0);
        mResponse = Response.PENDING_RESPONSE;
    }


    public TimePack timeOccurred (){return mTimeOccurred;}
    public TimePack timeResponded(){return mTimeResponded;}
    public Response response(){return mResponse;}
    public void setTimeOccurred(TimePack t){mTimeOccurred=t;}
    public void setTimeResponded(TimePack t){mTimeResponded=t;}
    public void setReponse(Response r){mResponse=r;}
}
