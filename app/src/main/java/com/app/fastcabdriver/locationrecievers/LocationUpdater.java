package com.app.fastcabdriver.locationrecievers;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.entities.UpdatedLocationEnt;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.interfaces.OnlocationUpdateListener;
import com.app.fastcabdriver.retrofit.WebService;

import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.GraphRequest.TAG;

/**
 * Created by khan_muhammad on 7/12/2017.
 */

public class LocationUpdater {
    private Context mContext;


    private OnlocationUpdateListener updateListener;
    private Intent intent;
    private WebService webservice;
    public LocationUpdater(Context mContext, WebService webservice) {
        this.mContext = mContext;
        this.webservice = webservice;
        intent = new Intent(AppConstants.LOCATION_RECIEVED);
    }

    public void updateLatLong(Location location, final String driverID){

        Call<ResponseWrapper> call = webservice.UpdateLatLng(
                driverID + "",
                location.getLatitude()+"",
                location.getLongitude()+"");

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
               getUpdatedLocation(driverID);
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

            }
        });

    }

    private void getUpdatedLocation(String driverID) {
        Call<ResponseWrapper<UpdatedLocationEnt>>call = webservice.getUpdatedLocation(driverID);
        call.enqueue(new Callback<ResponseWrapper<UpdatedLocationEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UpdatedLocationEnt>> call, Response<ResponseWrapper<UpdatedLocationEnt>> response) {
                if (response!=null&&response.body()!=null&&response.body().getResponse()!=null&&response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)){
                    intent.putExtra("lat",response.body().getResult().getLatitude()+"");
                    intent.putExtra("lon",response.body().getResult().getLongitude()+"");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UpdatedLocationEnt>> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });

    }

    public OnlocationUpdateListener getUpdateListener() {
        return updateListener;
    }

    public void setUpdateListener(OnlocationUpdateListener updateListener) {
        this.updateListener = updateListener;
    }
}
