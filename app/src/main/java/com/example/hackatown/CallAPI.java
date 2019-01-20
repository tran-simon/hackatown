package com.example.hackatown;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallAPI extends AsyncTask<Request, String, String>
{

	public CallAPI() {
		//set context variables if required
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	// https://stackoverflow.com/a/2938787
	// https://stackoverflow.com/a/16450705
	@Override
	protected String doInBackground(final Request... params) {
		final String urlString = "https://dev.concati.me/data",
				fileName = "image.png",
				lineEnd       = "\r\n";
		String twoHyphens    = "--";
		String boundary      = "end";
		String sourceFileUri = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
		File   sourceFile    = new File(sourceFileUri);
		int    maxBufferSize = 1024 * 1024;

		System.out.println("HERE");
		try {
			URL               url             = new URL(urlString);
			FileInputStream   fileInputStream = new FileInputStream(sourceFile);
			HttpURLConnection conn            = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use cache
			System.out.println("NONONONO");

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("image", fileName);

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
			dos.writeBytes(params[0].getDescription());
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

			// create a buffer of maximum size
			int bytesAvailable = fileInputStream.available();

			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];

			// read file and write it into form...
			int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			fileInputStream.close();
			dos.flush();
			dos.close();

			conn.connect();
			//System.out.println("MESSAGE DE RETOUR: " + new BufferedReader(new InputStreamReader((conn.getErrorStream()))).readLine());
			String s = new BufferedReader(new InputStreamReader((conn.getInputStream()))).readLine();
			if (s != null && !s.isEmpty())
				return s;
		} catch (Exception e) {
			System.out.println("OOPS");
			e.printStackTrace();
		}
		return "";
	}
}
