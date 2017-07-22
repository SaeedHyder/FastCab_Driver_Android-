package com.app.fastcabdriver.gpshelpers;

public class GPSConstants {


    public static boolean DEVELOPER_MODE = true;

    // The maximum distance the user should travel between location updates.
    public static int MAX_DISTANCE = 500;
    // The maximum time that should pass before the user gets a location update.
    public static long MAX_TIME = 1 * 1000;

    public static int PASSIVE_MAX_DISTANCE = MAX_DISTANCE;
    // The location update time for passive updates
    public static long PASSIVE_MAX_TIME = MAX_TIME;
    // Use the GPS (fine location provider) when the Activity is visible?
    public static boolean USE_GPS_WHEN_ACTIVITY_VISIBLE = true;

    public static String ACTIVE_LOCATION_UPDATE_PROVIDER_DISABLED = "com.radioactiveyak.places.active_location_update_provider_disabled";

    public static boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
    public static boolean SUPPORTS_HONEYCOMB = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
    public static boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
    public static boolean SUPPORTS_ECLAIR = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR;

    public static String CONSTRUCTED_LOCATION_PROVIDER = "CONSTRUCTED_LOCATION_PROVIDER";
}
