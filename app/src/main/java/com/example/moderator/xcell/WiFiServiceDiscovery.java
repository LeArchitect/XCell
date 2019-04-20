package com.example.moderator.xcell;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.support.v4.content.ContextCompat.getSystemService;
import static android.support.v4.content.ContextCompat.getSystemServiceName;


public class WiFiServiceDiscovery  {

    private static final String TAG = WiFiServiceDiscovery.class.getSimpleName();
    private static final String SERVICE_TYPE = "_xcell._tcp.";

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
            Log.i(TAG, "Service discovery success" + service);
            nsdManager.resolveService(service, resolveListener);

        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // When the network service is no longer available.
            // Internal bookkeeping code goes here.
            Log.e(TAG, "service lost: " + service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode + " " + serviceType);
            nsdManager.stopServiceDiscovery(this);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Log.e(TAG, "Discovery failed: Error code:" + errorCode);
            nsdManager.stopServiceDiscovery(this);
        }
    };


    private NsdManager.ResolveListener resolveListener = new NsdManager.ResolveListener() {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            // Called when the resolve fails. Use the error code to debug.
            Log.e(TAG, "Resolve failed: " + errorCode);
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            Log.e(TAG, "Resolve Succeeded. " + serviceInfo);

            if (serviceInfo.getServiceName().equals("asd")) {
                Log.d(TAG, "Same IP.");
                return;
            }
            mService = serviceInfo;
            int port = mService.getPort();
            InetAddress host = mService.getHost();
            try {
                Socket socket = new Socket(host, port);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                out.write("TstMsg");
            } catch (UnknownHostException e) {

            } catch (IOException e) {

            }
        }
    };

    public WiFiServiceDiscovery(NsdManager nsdManager){
        this.nsdManager = nsdManager;
    }

    public void startServiceDiscovery(){
        nsdManager.discoverServices(
                SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopSerivceDiscovery(){
        nsdManager.stopServiceDiscovery(discoveryListener);
    }



}
