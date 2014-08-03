package com.lorenzobraghetto.cooptdm;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) { //TODO espandibili
		if (intent.getExtras().getString("message_type").equals("news")) {
			Notification noti = NotificationFactory.buildNotification(context, context.getString(R.string.notification_title)
					, context.getString(R.string.notification_text), null);

			NotificationFactory.showNotification(context, noti, NotificationFactory.NOTIFICATION_ID_NEWS);
		} else if (intent.getExtras().getString("message_type").equals("notification")) {
			String title = intent.getExtras().getString("title");
			String message = intent.getExtras().getString("message");

			Notification noti = NotificationFactory.buildNotification(context, title
					, message, null);

			NotificationFactory.showNotification(context, noti, NotificationFactory.NOTIFICATION_ID_NOTIFICATION);
		} else if (intent.getExtras().getString("message_type").equals("cron")) {
			boolean status = intent.getExtras().getBoolean("status");

			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
			Editor edit = sp.edit();

			edit.putBoolean("cron", status);
			edit.commit();
		}
	}

}
