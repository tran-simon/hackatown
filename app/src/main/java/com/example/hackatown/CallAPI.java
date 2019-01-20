package com.example.hackatown;

import android.os.AsyncTask;
import android.os.Environment;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class CallAPI extends AsyncTask<Request, String, Boolean>
{

	public CallAPI(){
		//set context variables if required
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	// https://stackoverflow.com/a/2938787
	// https://stackoverflow.com/a/16450705
	@Override
	protected Boolean doInBackground(final Request... params) {
		String urlString     = "https://dev.concati.me/data";
		String fileName      = "image.png";
		String lineEnd       = "\r\n";
		String twoHyphens    = "--";
		String boundary      = "end";
		String sourceFileUri = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
		File   sourceFile    = new File(sourceFileUri);
		int maxBufferSize = 1024*1024;


		try {
			URL               url           = new URL(urlString);
			FileInputStream fileInputStream = new FileInputStream(
					sourceFile);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use cache

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("image", fileName);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			dos = new DataOutputStream(conn.getOutputStream());
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
				bufferSize = Math
						.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0,
						bufferSize);

			}

			// send multipart form data necesssary after file
			// data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens
					+ lineEnd);

			// close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

			//dos.writeBytes(params[0].xWwwFormUrlencoded());
			//dos.flush();
			//dos.close();
			conn.connect();
			System.out.println("MESSAGE DE RETOUR: " + new BufferedReader(new InputStreamReader((conn.getErrorStream()))).readLine());

		} catch (Exception e) {
			System.out.println("OOPS");
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
