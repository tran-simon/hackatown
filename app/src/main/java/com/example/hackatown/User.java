package com.example.hackatown;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class User extends AsyncTask<Integer, String, String>
{
	OnDataReceivedListener listener;
	User(OnDataReceivedListener listener) {
		this.listener = listener;
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	// https://stackoverflow.com/a/2938787
	// https://stackoverflow.com/a/16450705
	@Override
	protected String doInBackground(final Integer... params) {
		try {
			HttpURLConnection conn = (HttpURLConnection)
					new URL("https://dev.concati.me/user").openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setDoOutput(true);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());dos.flush();
			dos.writeBytes("id=" + params[0]);
			dos.close();
			conn.connect();
			System.out.println("USER " + params[0]);
			return new BufferedReader(new InputStreamReader((conn.getInputStream()))).readLine();
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String s) {
		listener.OnDataReceived(s);
	}
}
