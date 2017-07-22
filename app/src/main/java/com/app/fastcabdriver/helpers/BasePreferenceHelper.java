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


    //For GPS Service
    protected static final String SP_KEY_FOLLOW_LOCATION_CHANGES = "SP_KEY_FOLLOW_LOCATION_CHANGES";
    protected static final String SP_KEY_LAST_LIST_UPDATE_TIME = "SP_KEY_LAST_LIST_UPDATE_TIME";
    protected static final String SP_KEY_LAST_LIST_UPDATE_LAT = "SP_KEY_LAST_LIST_UPDATE_LAT";
    protected static final String SP_KEY_LAST_LIST_UPDATE_LNG = "SP_KEY_LAST_LIST_UPDATE_LNG";
    protected static final String SP_KEY_RUN_ONCE = "SP_KEY_RUN_ONCE";
    protected static final String EXTRA_KEY_IN_BACKGROUND = "EXTRA_KEY_IN_BACKGROUND";


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



    public void setIsFirstRun(boolean value){
        putBooleanPreference(context, FILENAME, SP_KEY_RUN_ONCE, value);

    }

    public boolean getIsFirstRun(){
        return getBooleanPreference(context,FILENAME,SP_KEY_RUN_ONCE);

    }

    public void setExtraInBackground(boolean value){
        putBooleanPreference(context,FILENAME,EXTRA_KEY_IN_BACKGROUND,value);

    }

    public boolean getExtraInBackground(){
        return getBooleanPreference(context,FILENAME,EXTRA_KEY_IN_BACKGROUND);

    }

    public void setFollowingLocationChanges(boolean value){
        putBooleanPreference(context,FILENAME,SP_KEY_FOLLOW_LOCATION_CHANGES,value);

    }

/*
   public boolean getFollowingLocationChanges(){
        return getTrueBooleanPreference(context, FILENAME, SP_KEY_FOLLOW_LOCATION_CHANGES);

    }
*/

    public void setListUpdateTime(long value){
        putLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_TIME, value);

    }

    public long getListUpdateTime(){
        return getLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_TIME);

    }

    public void setListUpdateLat(long value){
        putLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_LAT, value);

    }

    public long getListUpdateLat(){
        return getLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_LAT);

    }

    public void setListUpdateLng(long value){
        putLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_LNG, value);

    }

    public long getListUpdateLng(){
        return getLongPreference(context, FILENAME, SP_KEY_LAST_LIST_UPDATE_LNG);

    }


}
