package com.example.moderator.xcell;

import android.net.nsd.NsdServiceInfo;
import android.util.Log;

import java.io.*;
import java.net.Socket;

public class Communication {

    private static final String TAG = Communication.class.getSimpleName();
    private Socket socket;
    private BufferedWriter dataOut;
    private BufferedReader dataIn;
    private String status = null;
    private NsdServiceInfo info;
    private boolean isRunning;

    public Communication(NsdServiceInfo info){
        this.info = info;
        try {
            this.socket = new Socket(info.getHost(), info.getPort());
            this.dataOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e ){
            Log.e(TAG,"IOError: " + e);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                job();
            }
        }).start();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public String getStatus() {
        return status;
    }


    public void closeConn(){
        try{
            socket.close();
            dataIn.close();
            dataOut.close();
            isRunning=false;
            Thread.currentThread().interrupt();
        } catch (IOException err){

        }
    }


    public void sendMsg(String msg){
        try{
            dataOut.write(msg);
            dataOut.flush();
        } catch (IOException IOe){
            Log.i(TAG, "failed to send message + " + IOe);
            closeConn();
        }
    }


    public String receiveMsg(){
        try {
            status = dataIn.readLine();
            if (status != null){
                Log.i(TAG, status);
            }
            else{
                Log.i(TAG, "Null status");
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException " + e);
            closeConn();
        }
        return status;
    }


    public void job() {
        Log.i(TAG, "Message recv loop");
        isRunning = true;
        while (socket != null && !socket.isClosed() && isRunning) {
            sendMsg("STATUS");
            receiveMsg();
        }
    }
}
