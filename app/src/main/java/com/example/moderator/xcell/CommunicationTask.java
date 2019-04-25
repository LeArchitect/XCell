package com.example.moderator.xcell;

import android.os.AsyncTask;
import android.util.Log;

class CommunicationTask extends AsyncTask<String, Void, String> {
    public static final String TAG = CommunicationTask.class.getSimpleName();
    protected String doInBackground(String... params) {
        WiFiServiceDiscovery.getInstance().getServiceComms().get(params[0]).sendMsg(params[1]);
        return WiFiServiceDiscovery.getInstance().getServiceComms().get(params[0]).receiveMsg();
    }

    protected void onPostExecute(String result) {
        Log.d(TAG, "onPostExecute" + result);
    }
}
