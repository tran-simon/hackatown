package com.example.hackatown;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData extends AsyncTask<Integer, String, JSONArray>
{
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	// https://stackoverflow.com/a/2938787
	// https://stackoverflow.com/a/16450705
	@Override
	protected JSONArray doInBackground(final Integer... params) {
		URL url;
		try {
			String s = "https://dev.concati.me/data";
			if (params[0] > -1) s += "?id=" + params[0];
			url = new URL(s);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(1000);
			urlConnection.setConnectTimeout(1500);
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

			String jsonString = br.readLine();

			if (params[0] > -1)
				jsonString = '[' + jsonString + ']';
			br.close();
			System.out.println("JSON: " + jsonString);

			return new JSONArray(jsonString);
		} catch (IOException | JSONException ex) {
			System.err.println("Error occurred.");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(JSONArray result) {
	}
}
