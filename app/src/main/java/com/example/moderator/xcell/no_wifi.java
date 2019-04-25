package com.example.moderator.xcell;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class no_wifi extends AppCompatActivity {
    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_wifi);


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Button join = (Button) findViewById(R.id.join_joined);

        /* Checking wifi state.
         * If wifi is enabled, display "wifi is on" and set toggle button to on position.
         * If Wifi is disabled, display "wifi is off" and set toggle button to off position.
         */
        if (wifiManager.isWifiEnabled()) {
            join.setText("Joined");
        } else {
            join.setText("Join");
        }


    }

    public void enableWifi(View view) {
            //Toggle wifi
            //wifiManager.setWifiEnabled(true);
            Toast.makeText(this, "Wifi may take a moment to turn on", Toast.LENGTH_LONG).show();
            //wait for wifi
            Wi_Fi_checker();

    }
    public void Wi_Fi_checker()
    {
        /*
        while(!wifiManager.isWifiEnabled())
        {

         */
        int x = 0;
        while(x < 4)
        {
            try
            {
            Thread.sleep(1000);
            }
             catch (InterruptedException e) {
                e.printStackTrace();
            }
            x++;
        }
        Toast.makeText(this, "Wifi is enabled", Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(getApplicationContext(), com.example.moderator.xcell.WelcomeScreen.class);
        getApplicationContext().startActivity(myIntent);

    }
}
