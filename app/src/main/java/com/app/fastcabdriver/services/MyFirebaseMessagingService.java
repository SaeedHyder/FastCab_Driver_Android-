package com.app.fastcabdriver.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.MainActivity;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.helpers.BasePreferenceHelper;
import com.app.fastcabdriver.helpers.NotificationHelper;
import com.app.fastcabdriver.retrofit.WebService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //handleNotification(remoteMessage.getNotification().getBody());
            getNotificationCount();
        }*/

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
              /*  JSONObject json = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, "DATA: " + json);*/
            getNotificationCount();
            buildNotification(remoteMessage);


        }
    }

   /* private void handleNotification(String message) {
        if (!NotificationHelper.getInstance().isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationHelper.getInstance().playNotificationSound(getApplicationContext());
        } else {

            // If the app is in background, firebase itself handles the notification
        }
    }*/

    private void getNotificationCount() {


      /*  webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(getApplicationContext(),
                WebServiceConstants.SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());
        Call<ResponseWrapper<countEnt>> callback = webservice.getNotificationCount(preferenceHelper.getUserId());
        callback.enqueue(new Callback<ResponseWrapper<countEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<countEnt>> call, Response<ResponseWrapper<countEnt>> response) {

                preferenceHelper.setBadgeCount(response.body().getResult().getCount());
                Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", "");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

                Log.e(TAG, "aasd" + preferenceHelper.getUserId() + response.body().getResult().getCount());
                //  SendNotification(response.body().getResult().getCount(), json);
            }

            @Override
            public void onFailure(Call<ResponseWrapper<countEnt>> call, Throwable t) {
                Log.e(TAG, t.toString());
                System.out.println(t.toString());
            }
        });*/


    }

    private void buildNotification(RemoteMessage messageBody) {
        String title = getString(R.string.app_name);
        String message = messageBody.getData().get("message");


        Log.e(TAG, "message: " + message);

        Intent resultIntent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("message", message);
        resultIntent.putExtra("tapped", true);
        Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);

        //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);

        showNotificationMessage(MyFirebaseMessagingService.this, title, message, "", resultIntent);
    }

    private void SendNotification(int count, JSONObject json) {

    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationHelper.getInstance().showNotification(context,
                R.drawable.fastcab,
                title,
                message,
                timeStamp,
                intent);
    }


}
