package com.app.fastcabdriver.entities;

/**
 * Created on 7/27/2017.
 */

public class DriverSessionEnt {
    private LocationEnt origin;
    private LocationEnt destination;
    private LocationEnt pickup;
    private double longitude;
    private double latitude;
    private boolean isRideinSession = false;
    private boolean isTitleBarChange;
    private String rideID;
    private String userID;
    private RequestRideEnt requestRideEnt;
    private AssignRideEnt pendingRideEnt;
    private AssignRideEnt currentRideEnt;
    private boolean isPendingRide = false;
    private boolean isFromNotification = false;
    private String sessionState ;
    private int TripStatus;
    public LocationEnt getOrigin() {
        return origin;
    }

    public void setOrigin(LocationEnt origin) {
        this.origin = origin;
    }

    public LocationEnt getDestination() {
        return destination;
    }

    public void setDestination(LocationEnt destination) {
        this.destination = destination;
    }

    public LocationEnt getPickup() {
        return pickup;
    }

    public void setPickup(LocationEnt pickup) {
        this.pickup = pickup;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isRideinSession() {
        return isRideinSession;
    }

    public void setRideinSession(boolean rideinSession) {
        isRideinSession = rideinSession;
    }

    public boolean isTitleBarChange() {
        return isTitleBarChange;
    }

    public void setTitleBarChange(boolean titleBarChange) {
        isTitleBarChange = titleBarChange;
    }

    public String getRideID() {
        return rideID;
    }

    public void setRideID(String rideID) {
        this.rideID = rideID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public RequestRideEnt getRequestRideEnt() {
        return requestRideEnt;
    }

    public void setRequestRideEnt(RequestRideEnt requestRideEnt) {
        this.requestRideEnt = requestRideEnt;
    }

    public AssignRideEnt getPendingRideEnt() {
        return pendingRideEnt;
    }

    public void setPendingRideEnt(AssignRideEnt pendingRideEnt) {
        this.pendingRideEnt = pendingRideEnt;
    }

    public boolean isPendingRide() {
        return isPendingRide;
    }

    public void setPendingRide(boolean pendingRide) {
        isPendingRide = pendingRide;
    }

    public boolean isFromNotification() {
        return isFromNotification;
    }

    public void setFromNotification(boolean fromNotification) {
        isFromNotification = fromNotification;
    }

    public String getSessionState() {
        return sessionState;
    }

    public void setSessionState(String sessionState) {
        this.sessionState = sessionState;
    }

    public AssignRideEnt getCurrentRideEnt() {
        return currentRideEnt;
    }

    public void setCurrentRideEnt(AssignRideEnt currentRideEnt) {
        this.currentRideEnt = currentRideEnt;
    }

    public int getTripStatus() {
        return TripStatus;
    }

    public void setTripStatus(int tripStatus) {
        TripStatus = tripStatus;
    }
}
