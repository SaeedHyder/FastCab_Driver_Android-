

package com.app.fastcabdriver.locationrecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.gpshelpers.GPSConstants;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.retrofit.WebServiceFactory;
import com.app.fastcabdriver.utils.LegacyLastLocationFinder;


public class PassiveLocationChangedReceiver extends BroadcastReceiver {

    protected static String TAG = "PassiveLocationChangedReceiver";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        final BasePreferenceHelper prefHelper = new BasePreferenceHelper(context);


        new Thread(new Runnable() {
            public void run() {

                String key = LocationManager.KEY_LOCATION_CHANGED;
                Location location = null;

                if (intent.hasExtra(key)) {
                    // This update came from Passive provider, so we can extract the location
                    // directly.
                    location = (Location) intent.getExtras().get(key);
                } else {
                    // This update came from a recurring alarm. We need to determine if there
                    // has been a more recent Location received than the last location we used.

                    // Get the best last location detected from the providers.
                    LegacyLastLocationFinder lastLocationFinder = new LegacyLastLocationFinder(context);
                    location = lastLocationFinder.getLastBestLocation(GPSConstants.MAX_DISTANCE, System.currentTimeMillis() - GPSConstants.MAX_TIME);

                    // Get the last location we used to get a listing.
                    long lastTime = prefHelper.getListUpdateTime();
                    long lastLat = prefHelper.getListUpdateLat();
                    long lastLng = prefHelper.getListUpdateLng();
                    Location lastLocation = new Location(GPSConstants.CONSTRUCTED_LOCATION_PROVIDER);
                    lastLocation.setLatitude(lastLat);
                    lastLocation.setLongitude(lastLng);

                    // Check if the last location detected from the providers is either too soon, or too close to the last
                    // value we used. If it is within those thresholds we set the location to null to prevent the update
                    // Service being run unnecessarily (and spending battery on data transfers).
                    if ((lastTime > System.currentTimeMillis() - GPSConstants.MAX_TIME) ||
                            (lastLocation.distanceTo(location) < GPSConstants.MAX_DISTANCE))
                        location = null;
                }


                if (location != null) {
                    try {
                        if(prefHelper.getDriver() != null) {
                            LocationUpdater mLocationUpdater = new LocationUpdater(context,  WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context, WebServiceConstants.SERVICE_URL));
                            mLocationUpdater.updateLatLong(location,prefHelper.getDriverId());

                        }
                    } catch (Exception error) {
                        error.printStackTrace();
                    }

                }
            }
        }).start();
    }


}