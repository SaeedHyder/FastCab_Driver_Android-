package com.app.fastcabdriver.helpers;

import android.content.Context;
import android.location.Location;


import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.retrofit.WebService;
import com.facebook.login.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by khan_muhammad on 7/12/2017.
 */

public class LocationUpdaterHelper {

    public void updateLatLong(Location location, WebService webService, final DriverEnt loginResult){

        Call<ResponseWrapper> call = webService.UpdateLatLng(
                loginResult.getId() + "",
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

}
