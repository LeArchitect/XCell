package com.example.moderator.xcell;

import android.app.Service;
import android.content.*;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WiFiService extends Service {
    public WiFiService() {
    }

    private WifiP2pManager manager;
    private boolean setIsWifiP2pEnabled = false;
    private boolean retryChannel = false;

    private final IntentFilter intentFilter= new IntentFilter();
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver = null;

    private List<WifiP2pDevice> p2pPeers = new ArrayList<WifiP2pDevice>();

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener(){

        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {

            List<WifiP2pDevice> refreshedPeers = (List)peers.getDeviceList();

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

    @Override
    public void onCreate() {
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
        //receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                // Code for when the discovery initiation is successful goes here.
                // No services have actually been discovered yet, so this method
                // can often be left blank. Code for peer discovery goes in the
                // onReceive method, detailed below.
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    */

    public void setPeers(List<WifiP2pDevice> peers) {
        this.peers = peers;
    }
}
