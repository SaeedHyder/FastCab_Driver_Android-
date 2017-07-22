/*
 * Copyright 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.fastcabdriver.locationrecievers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.gpshelpers.GPSConstants;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.retrofit.WebServiceFactory;
import com.app.fastcabdriver.utils.LegacyLastLocationFinder;


/**
 * This Receiver class is designed to listen for changes in connectivity.
 * <p/>
 * When we lose connectivity the relevant Service classes will automatically
 * disable passive Location updates.
 * <p/>
 * This class re-enables passive location updates.
 */
public class ConnectivityChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            final BasePreferenceHelper prefHelper = new BasePreferenceHelper(context);

            ConnectivityManager cm = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Check if we are connected to an active data network.
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                PackageManager pm = context.getPackageManager();

                ComponentName connectivityReceiver = new ComponentName(context, ConnectivityChangedReceiver.class);
                ComponentName locationReceiver = new ComponentName(context, LocationChangedReceiver.class);
                ComponentName passiveLocationReceiver = new ComponentName(context, PassiveLocationChangedReceiver.class);

                // The default state for this Receiver is disabled. it is only
                // enabled when a Service disables updates pending connectivity.
                pm.setComponentEnabledSetting(connectivityReceiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                        PackageManager.DONT_KILL_APP);

                // The default state for the Location Receiver is enabled. it is only
                // disabled when a Service disables updates pending connectivity.
                pm.setComponentEnabledSetting(locationReceiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                        PackageManager.DONT_KILL_APP);

                // The default state for the Location Receiver is enabled. it is only
                // disabled when a Service disables updates pending connectivity.
                pm.setComponentEnabledSetting(passiveLocationReceiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                        PackageManager.DONT_KILL_APP);

                new Thread(new Runnable() {
                    public void run() {

                        try {
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
                                            LocationUpdater mLocationUpdater = new LocationUpdater();

                                            mLocationUpdater.updateLatLong(location, WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,WebServiceConstants.SERVICE_URL),prefHelper.getDriverId());

                                    }
                                } catch (Exception error) {
                                    error.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

