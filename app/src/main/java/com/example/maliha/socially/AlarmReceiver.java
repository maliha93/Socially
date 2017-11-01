package com.example.maliha.socially;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Maliha on 11/1/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmService", "Service running");
        //new SearchForRequests(context);
        String s=intent.getStringExtra("email");
        new NotificationReceiver(context,s);
        //Toast.makeText(context, "Running.."+s, Toast.LENGTH_SHORT).show();
    }
}