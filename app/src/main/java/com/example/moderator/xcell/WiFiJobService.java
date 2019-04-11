package com.example.moderator.xcell;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

public class WiFiJobService extends JobService {
    private static final String TAG = "WiFiService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), WiFiService.class);
        getApplicationContext().startService(service);
        WiFiScheduler.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
