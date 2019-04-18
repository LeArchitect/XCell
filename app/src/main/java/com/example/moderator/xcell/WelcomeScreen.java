package com.example.moderator.xcell;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WelcomeScreen extends AppCompatActivity {
    public static final String TAG = WelcomeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.welcome_screen);
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
