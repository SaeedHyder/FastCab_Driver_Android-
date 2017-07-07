package com.app.fastcab.entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created on 6/29/2017.
 */

public class LocationEnt {
    private String Address;
    private LatLng latlng;

    public LocationEnt(String address, LatLng latlng) {
        Address = address;
        this.latlng = latlng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }
}
