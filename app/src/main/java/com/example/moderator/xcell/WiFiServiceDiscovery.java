package com.example.moderator.xcell;


import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.util.*;

public class WiFiServiceDiscovery  {

    private static final String TAG = WiFiServiceDiscovery.class.getSimpleName();
    private static final String SERVICE_TYPE = "_xcell._tcp.";
    private static WiFiServiceDiscovery instance;

    private Map<String,NsdServiceInfo> services = new HashMap<String, NsdServiceInfo>();
    private Map<String,Communication> serviceComms = new HashMap<String,Communication>();

    private boolean isDiscovering = false;
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
            Log.d(TAG, "Service found" + service);
            nsdManager.resolveService(service, new NsdManager.ResolveListener() {

                @Override
                public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                    // Called when the resolve fails. Use the error code to debug.
                    Log.e(TAG, "Resolve failed: " + errorCode + "ServiceInfo: " + serviceInfo);
                }

                @Override
                public void onServiceResolved(NsdServiceInfo serviceInfo) {
                    Log.i(TAG, "Resolve Succeeded. " + serviceInfo);
                    Log.i(TAG, services.toString() + "  ;" + serviceInfo.toString());
                    if (!services.containsKey(serviceInfo.getServiceName())){
                        Communication comm = new Communication(serviceInfo);
                        services.put(serviceInfo.getServiceName(), serviceInfo);
                        serviceComms.put(serviceInfo.getServiceName(), comm);
                    }
                }
            });
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.d(TAG, "Service lost: " + service);
            services.remove(service.getServiceName());
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
            Log.i(TAG, "Resolve Succeeded. " + serviceInfo);
            Log.i(TAG, services.toString() + "  ;" + serviceInfo.toString());
            if (!services.containsKey(serviceInfo.getServiceName())){
                Communication comm = new Communication(serviceInfo);
                services.put(serviceInfo.getServiceName(), serviceInfo);
                serviceComms.put(serviceInfo.getServiceName(), comm);
            }
        }
    };

    private WiFiServiceDiscovery() {
        if (instance != null) {

        }
    }

    public static WiFiServiceDiscovery getInstance() {
        if (instance == null) {
            instance = new WiFiServiceDiscovery();
        }
        return instance;
    }

    public void setNsdManager(NsdManager mgr) {
        this.nsdManager = mgr;
    }

    public void startServiceDiscovery(){
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
        isDiscovering = true;

    }

    public void stopServiceDiscovery(){
        nsdManager.stopServiceDiscovery(discoveryListener);
        isDiscovering = false;
    }

    public boolean isDiscovering() {
        return isDiscovering;
    }

    public String getXCellServiceType(String name) {
        return new String(getServiceInfos().get(name).getAttributes().get("type"));
    }

    public Map<String, NsdServiceInfo> getServiceInfos() {
        return services;
    }

    public Set<String> getServices(){
        return services.keySet();
    }

    public ArrayList<String> getServicesArray()
    {

        return new ArrayList<String>(services.keySet());
    }
    public Map<String, Communication> getServiceComms() {
        return serviceComms;
    }
}
