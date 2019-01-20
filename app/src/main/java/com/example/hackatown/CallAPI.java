package com.example.hackatown;

import android.os.AsyncTask;
import android.os.Environment;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CallAPI extends AsyncTask<Request, String, JSONObject>
{

	private OnDataReceivedListener listener;
	public CallAPI(OnDataReceivedListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	// https://stackoverflow.com/a/2938787
	// https://stackoverflow.com/a/16450705
	@Override
	protected JSONObject doInBackground(final Request... params) {
		final String urlString = "https://dev.concati.me/data",
				fileName = "image.jpg",
				lineEnd = "\r\n",
				twoHyphens = "--",
				boundary = "end",
				sourceFileUri = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
		File sourceFile    = new File(params[0].path);
		int  maxBufferSize = 1024 * 1024;

		System.out.println("HERE");
		try {
			URL               url  = new URL(urlString);
			FileInputStream   fis  = new FileInputStream(sourceFile);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use cache

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			//conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			//conn.setRequestProperty("image", fileName);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			//DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"type\"" + lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(Integer.toString(params[0].getType().ordinal()));
			dos.writeBytes(lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"position\"" + lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(Double.toString(params[0].getPosition().latitude));
			dos.writeBytes(",");
			dos.writeBytes(Double.toString(params[0].getPosition().longitude));
			dos.writeBytes(lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"description\"" + lineEnd);
			dos.writeBytes(lineEnd);
			dos.write(params[0].getDescription().getBytes(StandardCharsets.UTF_8));
			dos.writeBytes(lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"user_id\"" + lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(Integer.toString(params[0].getUserID()));
			dos.writeBytes(lineEnd);

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"" + lineEnd);
			//dos.writeBytes("Content-Type: image/jpeg" + lineEnd);
			//dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
			dos.writeBytes(lineEnd);

			int bytesAvailable = fis.available();
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];
			int bytesRead = fis.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fis.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fis.read(buffer, 0, bufferSize);
			}
			/*
			TODO: Improve
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			do {
				bytesAvailable = fis.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];
				dos.write(buffer, 0, bufferSize);
				bytesRead = fis.read(buffer, 0, bufferSize);
			} while(bytesRead > 0);*/

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			dos.flush();
			dos.close();
			fis.close();

			conn.connect();
			//System.out.println("MESSAGE DE RETOUR: " + new BufferedReader(new InputStreamReader((conn.getErrorStream()))).readLine());
			String jsonString = new BufferedReader(new InputStreamReader((conn.getInputStream()))).readLine();
			if (jsonString != null && !jsonString.isEmpty())
				return new JSONObject(jsonString);
		} catch (final IOException | JSONException ex) {
			System.err.println("Error occurred.");
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		listener.OnDataReceived("");
	}
}
