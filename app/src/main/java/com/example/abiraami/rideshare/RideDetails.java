package com.example.abiraami.rideshare;

import java.util.Date;

/**
 * Created by Abiraami on 11/30/2016.
 */

public class RideDetails {
    private String startPt;
    private String endPt;
    private Date travelDate;
    private int noSpot;

    public String getStartPt() {
        return startPt;
    }

    public void setStartPt(String startPt) {
        this.startPt = startPt;
    }

    public String getEndPt() {
        return endPt;
    }

    public void setEndPt(String endPt) {
        this.endPt = endPt;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public int getNoSpot() {
        return noSpot;
    }

    public void setNoSpot(int noSpot) {
        this.noSpot = noSpot;
    }



}
