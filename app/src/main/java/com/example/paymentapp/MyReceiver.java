package com.example.paymentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.params.Capability;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    Context context;
    boolean isconnect = false;
    View view;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        isConnected(connectivityManager);

    }


    public void isConnected(ConnectivityManager connectivityManager) {

        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Toast.makeText(context, "cellular data", Toast.LENGTH_SHORT).show();

                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Toast.makeText(context, "wifi connected", Toast.LENGTH_SHORT).show();

                }
            } else
                Toast.makeText(context, "no network", Toast.LENGTH_SHORT).show();

        }

    }
}
