package me.voidmain.nfc.mindreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class HandleActivity extends Activity {

	private static final String TAG = HandleActivity.class.getCanonicalName();
	
	class RequestThread extends Thread {

		@Override
		public void run() {
			fetchPage();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_handle);

		new RequestThread().start();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		new RequestThread().start();
	}

	private void fetchPage() {
		BufferedReader in = null;
		String data = null;

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(
					"http://mindreading.heroku.com/voidmain/test");

			HttpResponse response = null;
			response = client.execute(request);

			int status_code = response.getStatusLine().getStatusCode();
			Log.d(TAG, "Response Code returned =" + status_code);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			StringBuffer sb = new StringBuffer("");
			String line = "";
			String newline = System.getProperty("line.separator");
			try {
				while ((line = in.readLine()) != null) {
					sb.append(line + newline);
				}
				data = sb.toString();
				Log.d(TAG, data);
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}
			try {
				in.close();
			} catch (IOException e) {
				Log.d(TAG, e.toString());
			}
			data = sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		Log.e(TAG, data);

		Uri uri = Uri.parse(data);
		Intent openBrowseIntent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(openBrowseIntent);

		this.finish();
	}

}
