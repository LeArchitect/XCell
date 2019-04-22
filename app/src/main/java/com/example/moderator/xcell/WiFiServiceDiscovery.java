package com.example.moderator.xcell;


import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class WiFiServiceDiscovery  {

    private static final String TAG = WiFiServiceDiscovery.class.getSimpleName();
    private static final String SERVICE_TYPE = "_xcell._tcp.";

    private List<NsdServiceInfo> services = new ArrayList<NsdServiceInfo>();
    private List<Communication> comms = new ArrayList<Communication>();
    private boolean isDiscovering = false;
    private NsdServiceInfo mService;
    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener = new NsdManager.DiscoveryListener() {

        // Called as soon as service discovery begins.
        @Override
        public void onDiscoveryStarted(String regType) {
            Log.i(TAG, "Service discovery started " + regType);
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            // A service was found! Do something with it.
            Log.d(TAG, "Service found");
            if (!services.contains(service)){
                services.add(service);
                nsdManager.resolveService(service, resolveListener);
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.d(TAG, "Service lost: " + service);
            services.remove(service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode + "ServiceType_ " + serviceType);
            nsdManager.stopServiceDiscovery(this);
            isDiscovering = false;
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode + "ServiceType: " + serviceType);
            nsdManager.stopServiceDiscovery(this);
            isDiscovering = false;
        }
    };


    private NsdManager.ResolveListener resolveListener = new NsdManager.ResolveListener() {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: " + errorCode + "ServiceInfo: " + serviceInfo);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            Log.e(TAG, "Resolve Succeeded. " + serviceInfo);
            comms.add(new Communication(serviceInfo));
            Log.i(TAG, "returned");
        }
    };

    public WiFiServiceDiscovery(NsdManager nsdManager){
        this.nsdManager = nsdManager;
    }

    public void startServiceDiscovery(){
        isDiscovering = true;
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopSerivceDiscovery(){
        nsdManager.stopServiceDiscovery(discoveryListener);
    }

}
