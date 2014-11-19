package com.lorenzobraghetto.cooptdm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.lorenzobraghetto.cooptdm.logic.CoopTDMNewsService;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

		if (sp.getBoolean("cron", true)) {
			setAlarm(context);
		}
	}

	public static void setAlarm(Context context) {
		String alarm = Context.ALARM_SERVICE;
		AlarmManager am = (AlarmManager) context.getSystemService(alarm);

		Intent serviceIntent = new Intent(context, CoopTDMNewsService.class);
		PendingIntent pi = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		int type = AlarmManager.ELAPSED_REALTIME;
		long interval = AlarmManager.INTERVAL_DAY;
		//long interval = 1000 * 5;
		long triggerTime = SystemClock.elapsedRealtime();

		am.setRepeating(type, triggerTime, interval, pi);
	}
}
