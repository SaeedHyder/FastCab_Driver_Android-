package com.app.fastcab.helpers;

import android.content.Context;
import android.util.Log;

import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.retrofit.WebService;
import com.app.fastcab.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/15/2017.
 */

public class TokenUpdater {
    private static final TokenUpdater tokenUpdater = new TokenUpdater();

    public static TokenUpdater getInstance() {
        return tokenUpdater;
    }
    private WebService webservice;

    private TokenUpdater() {
    }
    public void UpdateToken(Context context , final String userid, String DeviceType, String Token) {
        if (Token.isEmpty()) {
            Log.e("Token Updater", "Token is Empty");
        }
            webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                    WebServiceConstants.SERVICE_URL);
            Call<ResponseWrapper> call = webservice.updateToken(userid, DeviceType, Token);
            call.enqueue(new Callback<ResponseWrapper>() {
                @Override
                public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {

                    Log.i("UPDATETOKEN", response.body().getResponse() + "" + userid);
                }

                @Override
                public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                    Log.e("UPDATETOKEN", t.toString());
                }
            });
        }

}
