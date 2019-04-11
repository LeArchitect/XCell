package com.example.moderator.xcell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class WiFiStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WiFiScheduler.scheduleJob(context);
    }
}
