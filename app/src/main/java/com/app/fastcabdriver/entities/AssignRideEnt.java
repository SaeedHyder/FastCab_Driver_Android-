package com.app.fastcabdriver.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by saeedhyder on 7/18/2017.
 */

public class AssignRideEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ride_id")
    @Expose
    private Integer rideId;
    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("ride_status")
    @Expose
    private Integer rideStatus;
    @SerializedName("trip_status")
    @Expose
    private Integer tripStatus;
    @SerializedName("driver_detail")
    @Expose
    private DriverEnt driverDetail;
    @SerializedName("ride_detail")
    @Expose
    private RideDetail rideDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(Integer rideStatus) {
        this.rideStatus = rideStatus;
    }

    public Integer getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(Integer tripStatus) {
        this.tripStatus = tripStatus;
    }

    public DriverEnt getDriverDetail() {
        return driverDetail;
    }

    public void setDriverDetail(DriverEnt driverDetail) {
        this.driverDetail = driverDetail;
    }

    public RideDetail getRideDetail() {
        return rideDetail;
    }

    public void setRideDetail(RideDetail rideDetail) {
        this.rideDetail = rideDetail;
    }

}
