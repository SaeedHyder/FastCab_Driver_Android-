

package com.app.fastcabdriver.locationrecievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.fastcabdriver.gpshelpers.SettingGPService;


/**
 * This Receiver class is designed to listen for system boot.
 * <p/>
 * If the app has been run at least once, the passive location
 * updates should be enabled after a reboot.
 */
public class BootReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            SettingGPService.settingGPS(context, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}