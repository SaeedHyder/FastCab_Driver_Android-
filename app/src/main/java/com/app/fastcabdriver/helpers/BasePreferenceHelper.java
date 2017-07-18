package com.app.fastcabdriver.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.retrofit.GsonFactory;


public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_USER_STATUS = "isOnline";
    private static final String FILENAME = "preferences";
    protected static final String KEY_DRIVER = "key_driver";
    protected static final String DRIVERID = "driverId";
    protected static final String Firebase_TOKEN = "Firebasetoken";


    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }
    public void setUserStatus( boolean isOnline ) {
        putBooleanPreference( context, FILENAME, KEY_USER_STATUS, isOnline );
    }

    public boolean isOnline() {
        return getBooleanPreference(context, FILENAME, KEY_USER_STATUS);
    }
    public void setLoginStatus( boolean isLogin ) {
        putBooleanPreference( context, FILENAME, KEY_LOGIN_STATUS, isLogin );
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }

    public DriverEnt getDriver() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_DRIVER), DriverEnt.class);
    }

    public void putDriver(DriverEnt user) {
        putStringPreference(context, FILENAME, KEY_DRIVER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public void setDriverId(String userId) {
        putStringPreference(context, FILENAME, DRIVERID, userId);
    }

    public String getDriverId() {
        return getStringPreference(context, FILENAME, DRIVERID);
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }

    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }


}
