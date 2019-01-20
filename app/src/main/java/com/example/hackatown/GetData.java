package com.example.hackatown;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData extends AsyncTask<Integer, String, String> {

    private OnDataReceivedListener listener;

    public GetData(OnDataReceivedListener listener) {
        this.listener = listener;
    }

    // https://stackoverflow.com/a/2938787
    // https://stackoverflow.com/a/16450705
    @Override
    protected String doInBackground(final Integer... params) {
        URL url;
        try
        {
            String s = "https://dev.concati.me/data";
            if (params[0] > -1)
            {
                s += "?id=" + params[0];
            }
            url = new URL(s);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

            String jsonString = br.readLine();

            if (params[0] > -1)
            {
                jsonString = '[' + jsonString + ']';
            }
            br.close();
            System.out.println("JSON: " + jsonString);

            return jsonString;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO: Parse as list
//        Intent intent = new Intent(initialActivity.getApplicationContext(), nextActivity);
//        intent.putExtra("info", result);
//        initialActivity.startActivity(intent);
        listener.OnDataReceived(result);

    }
}
