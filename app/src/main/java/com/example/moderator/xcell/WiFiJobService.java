package com.example.moderator.xcell;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WiFiJobService extends JobService {
    private static final String TAG = "WiFiService";
    private WifiP2pManager manager;
    private boolean setIsWifiP2pEnabled = false;
    private boolean retryChannel = false;

    private final IntentFilter intentFilter= new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;

    private List<WifiP2pDevice> p2pPeers = new ArrayList<WifiP2pDevice>();


    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), WiFiService.class);
        int i = doWork();
        manager.requestPeers(channel, peerListListener);
        WiFiScheduler.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        unregisterReceiver(receiver);
        return true;

    }



    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener(){

        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {

            Collection<WifiP2pDevice> refreshedPeers = peers.getDeviceList();
            if (refreshedPeers.equals(p2pPeers)){
                p2pPeers.clear();
                p2pPeers.addAll(refreshedPeers);
                Log.d("Peers", "Tuomas");
                Log.d("Peers: ", p2pPeers.toString());
            }
            if (p2pPeers.size() == 0){
                Log.d("TEST","No devices found");
            }

        }
    };

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled){
        this.setIsWifiP2pEnabled = isWifiP2pEnabled;
    }

    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();


    public int doWork() {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
                Log.d(TAG,"success");
            }

            @Override
            public void onFailure(int reasonCode) {
                // Code for when the discovery initiation fails goes here.
                // Alert the user that something went wrong.
            }
        });

        manager.createGroup(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Device is ready to accept incoming connections from peers.
            }

            @Override
            public void onFailure(int reason) {

            }
        });


        return START_NOT_STICKY;
    }

/*
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
*/


    public void setPeers(List<WifiP2pDevice> peers) {
        this.peers = peers;
    }

}
