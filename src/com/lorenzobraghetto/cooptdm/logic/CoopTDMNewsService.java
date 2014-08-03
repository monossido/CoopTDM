package com.lorenzobraghetto.cooptdm.logic;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorenzobraghetto.cooptdm.CoopTDMParams;
import com.lorenzobraghetto.cooptdm.NotificationFactory;
import com.lorenzobraghetto.cooptdm.R;

public class CoopTDMNewsService extends Service {

	private boolean started = false;

	@Override
	public void onCreate() { //Per ora rimane, da verificare funzionamento push
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
		params.add("api_key", CoopTDMParams.API_KEY_MIO);
		client.post(CoopTDMParams.BASE_URL + "api/lastNews.php", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONObject result = new JSONObject(response);
					if (result.getBoolean("result")) {
						Notification noti = NotificationFactory.buildNotification(CoopTDMNewsService.this, getString(R.string.notification_title)
								, getString(R.string.notification_text), null);

						NotificationFactory.showNotification(CoopTDMNewsService.this, noti, NotificationFactory.NOTIFICATION_ID_NEWS);
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
