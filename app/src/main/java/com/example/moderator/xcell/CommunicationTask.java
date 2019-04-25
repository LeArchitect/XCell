package com.example.moderator.xcell;

import android.os.AsyncTask;
import android.util.Log;

class CommunicationTask extends AsyncTask<String, Void, String> {
    public static final String TAG = CommunicationTask.class.getSimpleName();
    protected String doInBackground(String... params) {
        Communication comm = WiFiServiceDiscovery.getInstance().getServiceComms().get(params[0]);
        if (comm != null) {
            comm.sendMsg(params[1]);
            return comm.receiveMsg();
        }
        return "Communication task failed!";
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute: " + result);
    }
}
