package com.app.fastcabdriver.gpshelpers;

import android.app.Activity;
import android.location.LocationManager;



public class CheckGPSAvailibility {

    private static boolean isGPSEnabled = false;
    private static boolean isNetworkEnabled = false;
    private static LocationManager locationManager;

    public static boolean checkGPSAvailibility(Activity mContext) {
        locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            GPSHelper.showGPSDisabledAlertToUser(mContext,null);
            return false;
        }
        else{
            return true;
        }
    }

}
