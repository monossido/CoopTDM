package com.lorenzobraghetto.cooptdm.logic;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lorenzobraghetto.cooptdm.CoopTDMParams;

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
		Log.v("COOPTDM", "onHandleIntent");
		client.get(CoopTDMParams.BASE_URL + "api/lastNews.php", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
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
