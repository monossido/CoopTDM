package com.lorenzobraghetto.cooptdm;

import java.io.IOException;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorenzobraghetto.cooptdm.logic.CallbackNews;
import com.lorenzobraghetto.cooptdm.logic.CoopTDMApplication;

public class SplashActivity extends Activity {

	String SENDER_ID = "22089529162";
	private GoogleCloudMessaging gcm;
	private String regid;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(this);

			if (regid.isEmpty()) {
				registerInBackground();
			}
		} else {
			Log.i("COOPTDM", "No valid Google Play Services APK found.");
		}

		CallbackNews callback = new CallbackNews() {

			@Override
			public void onDownloaded() {
				finish();
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}

		};

		((CoopTDMApplication) getApplication()).getNews(callback);
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						9000).show();
			}
		} else {
			return true;
		}
		return false;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getSharedPreferences("CoopTDM", Context.MODE_PRIVATE);
		String registrationId = prefs.getString("RegisterId", "");
		if (registrationId.isEmpty()) {
			Log.i("COOPTDM", "Registration not found.");
			return "";
		}

		int registeredVersion = prefs.getInt("AppVersion", Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("COOPTDM", "App version changed.");
			return "";
		}
		return registrationId;
	}

	private void registerInBackground() {
		new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(SplashActivity.this);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration ID=" + regid;

					sendRegistrationIdToBackend();

					storeRegistrationId(SplashActivity.this, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();

				}
				return msg;
			}

			@Override
			protected void onPostExecute(Object msg) {
			}

		}.execute();

	}

	private void sendRegistrationIdToBackend() {
		String possibleEmail = "";
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
		if (emailPattern.matcher(accounts[0].name).matches()) {
			possibleEmail = accounts[0].name;
		}
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("registerId", regid);
		params.put("email", possibleEmail);
		params.put("android_version", Build.VERSION.SDK_INT + "");
		params.put("app_version", getAppVersion(this) + "");
		client.post(CoopTDMParams.BASE_URL + "api/register.php?api_ley=" + CoopTDMParams.API_KEY, params, new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				Log.v("COOPTDM", "response=" + response);
			}
		});

	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getSharedPreferences("CoopTDM", Context.MODE_PRIVATE);
		int appVersion = getAppVersion(context);
		Log.i("COOPTDM", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("RegisterId", regId);
		editor.putInt("AppVersion", appVersion);
		editor.commit();
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}
}
