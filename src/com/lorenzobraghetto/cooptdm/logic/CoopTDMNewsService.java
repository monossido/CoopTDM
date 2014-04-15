package com.lorenzobraghetto.cooptdm.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorenzobraghetto.cooptdm.CoopTDMParams;
import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.SplashActivity;

public class CoopTDMNewsService extends Service {

	private boolean started = false;

	@Override
	public void onCreate() {
		super.onCreate();
		if (!started) {
			String alarm = Context.ALARM_SERVICE;
			AlarmManager am = (AlarmManager) getSystemService(alarm);

			Intent intent = new Intent(this, CoopTDMNewsService.class);
			PendingIntent pi = PendingIntent.getService(this, 0, intent, 0);

			int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
			long interval = AlarmManager.INTERVAL_HALF_DAY;
			//long interval = 1000 * 5;
			long triggerTime = SystemClock.elapsedRealtime() + interval;

			am.setRepeating(type, triggerTime, interval, pi);
			started = true;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		AsyncHttpClient client = new AsyncHttpClient();

		Integer lastNewsId = getSharedPreferences("CoopTDMSettings", Context.MODE_PRIVATE).getInt("lastNewsId", 0);

		RequestParams params = new RequestParams();
		params.add("lastNewsId", lastNewsId.toString());
		client.post(CoopTDMParams.BASE_URL + "api/lastNews.php?api_ley=" + CoopTDMParams.API_KEY, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject result = new JSONObject(response);
					if (result.getBoolean("result")) {
						NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
						Intent app = new Intent(CoopTDMNewsService.this, SplashActivity.class);
						PendingIntent pintent = PendingIntent.getActivity(CoopTDMNewsService.this, 0, app, PendingIntent.FLAG_UPDATE_CURRENT);

						Notification noti = new NotificationCompat.Builder(CoopTDMNewsService.this)
								.setContentTitle(getString(R.string.notification_title))
								.setContentText(getString(R.string.notification_text))
								.setSmallIcon(R.drawable.ic_launcher)
								.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
								.setContentIntent(pintent)
								.setAutoCancel(true).build();

						notificationManager.notify(123456, noti);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				stopSelf();
			}

			public void onFailure(Throwable error) {
				stopSelf();
			}
		});
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
