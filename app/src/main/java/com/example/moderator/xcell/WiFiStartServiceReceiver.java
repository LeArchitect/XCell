package com.example.moderator.xcell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WiFiStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("asd","joujou");
        WiFiScheduler.scheduleJob(context);

    }
}
