package com.app.fastcab.entities;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingTripsEnt {

    String pickupTime;
    String pickup;
    String DropOff;

    public PendingTripsEnt(String pickupTime,String pickup,String DropOff)
    {
        setPickupTime(pickupTime);
        setPickup(pickup);
        setDropOff(DropOff);
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropOff() {
        return DropOff;
    }

    public void setDropOff(String dropOff) {
        DropOff = dropOff;
    }
}
