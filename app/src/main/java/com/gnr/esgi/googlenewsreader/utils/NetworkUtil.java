package com.gnr.esgi.googlenewsreader.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.gnr.esgi.googlenewsreader.GNRApplication;

public class NetworkUtil {
    public static boolean checkInternetConnection(){
        Context context = GNRApplication.getAppContext();
        ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (check != null)
        {
            NetworkInfo[] info = check.getAllNetworkInfo();

            if (info != null)
                for (int i = 0; i <info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
        }

        return false;
    }
}
