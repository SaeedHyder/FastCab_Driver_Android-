package com.app.fastcabdriver.locationrecievers;

import android.content.Context;
import android.location.Location;


import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.interfaces.OnlocationUpdateListener;
import com.app.fastcabdriver.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by khan_muhammad on 7/12/2017.
 */

public class LocationUpdater {
    private OnlocationUpdateListener updateListener;

    public void updateLatLong(Location location, WebService webService, final String loginResult){

        Call<ResponseWrapper> call = webService.UpdateLatLng(
                loginResult + "",
                location.getLatitude()+"",
                location.getLongitude()+"");

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {

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
