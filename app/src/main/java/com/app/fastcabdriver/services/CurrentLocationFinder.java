package com.app.fastcabdriver.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.entities.UpdatedLocationEnt;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.retrofit.WebService;
import com.app.fastcabdriver.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/21/2017.
 */

public class CurrentLocationFinder extends Service {
    private static final String TAG = "BroadcastService";
    private final Handler handler = new Handler();
    Intent intent;
    private static double changePositionBy = 0.00005;
    private WebService webService;
    private BasePreferenceHelper preferenceHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"created");
        intent = new Intent(AppConstants.LOCATION_RECIEVED);
        webService = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(CurrentLocationFinder.this,
                WebServiceConstants.SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(CurrentLocationFinder.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG,"Started");
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

        return super.onStartCommand(intent, flags, startId);
    }


    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            broadcastUpdateInfo();
            handler.postDelayed(this, 10000); // broadcast in every 10 seconds
        }
    };

    //call your API in this method
    private void broadcastUpdateInfo() {
        Log.d(TAG, "entered DisplayLoggingInfo");

        //increment position(lat,lng) by 0.000005
        changePositionBy = changePositionBy + changePositionBy;
        Call<ResponseWrapper<UpdatedLocationEnt>>call = webService.getUpdatedLocation(preferenceHelper.getDriverId());
        call.enqueue(new Callback<ResponseWrapper<UpdatedLocationEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UpdatedLocationEnt>> call, Response<ResponseWrapper<UpdatedLocationEnt>> response) {
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)){
                    intent.putExtra("lat",response.body().getResult().getLatitude()+"");
                    intent.putExtra("lon",response.body().getResult().getLongitude()+"");
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UpdatedLocationEnt>> call, Throwable t) {
            Log.e(TAG,t.toString());
            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG,"Destroyed");
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }
}


