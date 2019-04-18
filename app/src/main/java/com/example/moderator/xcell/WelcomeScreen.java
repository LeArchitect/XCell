package com.example.moderator.xcell;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WelcomeScreen extends AppCompatActivity {
    public static final String TAG = WelcomeScreen.class.getSimpleName();
    private int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0;
    private int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1;
    private int PERMISSIONS_REQUEST_CODE_CHANGE_WIFI_STATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.welcome_screen);
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
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

        }else{
            //do something, permission was previously granted; or legacy device
        }
        scheduleJob();
    }


    private void scheduleJob(){
        ComponentName wifiJobService = new ComponentName(getApplicationContext(), WiFiJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(8,wifiJobService)
                .setRequiresCharging(false)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int res = jobScheduler.schedule(jobInfo);
        if (res == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG, "Jobscheduled");
        } else {
            Log.i(TAG,"Failed");
        }
    }
}
