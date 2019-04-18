package com.example.moderator.xcell;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WiFiJobService extends JobService {

    private static final String TAG = WiFiJobService.class.getSimpleName();
    private boolean jobCancelled = false;
    private boolean isWorking = false;

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private List<WifiP2pDevice> services = new ArrayList<WifiP2pDevice>();


    WifiP2pManager.DnsSdServiceResponseListener dnsListner = new WifiP2pManager.DnsSdServiceResponseListener() {
        @Override
        public void onDnsSdServiceAvailable(String instanceName, String registrationType, WifiP2pDevice srcDevice) {
            Log.i(TAG, "regtype:" + registrationType);

        }
    };

    WifiP2pManager.DnsSdTxtRecordListener txtListener = new WifiP2pManager.DnsSdTxtRecordListener() {
        @Override
        public void onDnsSdTxtRecordAvailable(String fullDomainName, Map<String, String> txtRecordMap,  final WifiP2pDevice srcDevice) {
            Log.d(TAG,  "Record: " + (String)txtRecordMap.toString());
            Log.d(TAG, "Domainname: " + fullDomainName);
            Log.d(TAG, "Device: " + srcDevice);
            if (!services.contains(srcDevice)){
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = srcDevice.deviceAddress;
                manager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG, "Succefully connected to device: " + srcDevice.toString());
                        services.add(srcDevice);
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.i(TAG, "Couldnt connect to device" + reason);
                    }
                });
            }
            Log.d(TAG,"Services: " + services.toString());
        }
    };


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG,"Job started");
        isWorking = true;
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        manager.setDnsSdResponseListeners(channel,dnsListner,txtListener);
        WifiP2pDnsSdServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        manager.addServiceRequest(channel, serviceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG,"Serive request successfully started");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "Service request failed, reason: " + reason);
                isWorking = false;
            }
        });
        startWorkOnNewThread(params);
        return isWorking;
    }


    private void startWorkOnNewThread(final JobParameters jobParameters){
        new Thread(new Runnable() {
            public void run() {
                discoverServices(jobParameters);
            }
        }).start();
    }

    private void discoverServices(JobParameters jobParameters){
        Log.i(TAG, "Working" + isWorking);
        if (jobCancelled){
            return;
        }
        while (isWorking) {
            manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, "Succesfully discovered services");
                }

                @Override
                public void onFailure(int reason) {
                    Log.i(TAG, "Service discovery failer reason: " + reason);
                    isWorking = false;
                }
            });
            try { Thread.sleep(10000);} catch (Exception e){Log.i(TAG,"Error" + e);}
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "Job stopped");
        isWorking = false;
        jobFinished(params, true);
        return false;
    }
}
