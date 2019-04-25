package com.example.moderator.xcell;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.nsd.NsdManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class WelcomeScreen extends AppCompatActivity {
    public static final String TAG = WelcomeScreen.class.getSimpleName();
    private int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    private int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1;
    private int PERMISSIONS_REQUEST_CODE_CHANGE_WIFI_STATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, PERMISSIONS_REQUEST_CODE_CHANGE_WIFI_STATE);
            }

        }else{
        }
        WiFiServiceDiscovery.getInstance().setNsdManager((NsdManager)getSystemService(Context.NSD_SERVICE));

        Button welcomeButton = (Button) findViewById(R.id.buttonWelcome);

        welcomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, WiFiServiceDiscovery.getInstance().getServiceComms().get("_Light").toString());
                new CommunicationTask().execute("_Light", "STATUS");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, WiFiServiceDiscovery.getInstance().getServices().toString());
        WiFiServiceDiscovery.getInstance().stopServiceDiscovery();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WiFiServiceDiscovery.getInstance().startServiceDiscovery();
    }
}
