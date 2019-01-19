package com.example.hackatown;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CallAPI extends AsyncTask<Request, String, Boolean>
{

	public CallAPI(){
		//set context variables if required
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(final Request... params) {
		String       urlString = "https://dev.concati.me/data";
		OutputStream out;

		try {
			URL               url           = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			out = new BufferedOutputStream(urlConnection.getOutputStream());

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
			writer.write(params[0].xWwwFormUrlencoded());
			writer.flush();
			writer.close();
			out.close();

			urlConnection.connect();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
