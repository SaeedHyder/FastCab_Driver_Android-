package com.app.fastcab.helpers;

import android.net.ConnectivityManager;

import com.app.fastcab.R;
import com.app.fastcab.activities.DockActivity;


/**
 * Created by khan_muhammad on 3/27/2017.
 */

public class InternetHelper {

    public static boolean CheckInternetConectivityandShowToast(DockActivity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            return true;
        } else {
            // text.setText("Look your not online");

            UIHelper.showLongToastInCenter(activity, activity.getString(R.string.connection_lost));
            return false;
        }


    }

}
