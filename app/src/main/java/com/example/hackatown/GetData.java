package com.example.hackatown;

import android.os.AsyncTask;
import android.os.Environment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetData extends AsyncTask<Integer, String, JSONArray>
{

	public GetData() {
		//set context variables if required
	}

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
			if (params[0] > -1)
				s += "?id=" + params[0];
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
				jsonString = '[' + jsonString + ']';
			br.close();
			System.out.println("JSON: " + jsonString);

			return new JSONArray(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(JSONArray result) {
		// TODO: Parse as list

	}
}
