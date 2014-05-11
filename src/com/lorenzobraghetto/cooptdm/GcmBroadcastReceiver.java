package com.lorenzobraghetto.cooptdm;

import com.lorenzobraghetto.cooptdm.ui.SplashActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("ATOOMA", "onReceive, intent=" + intent.getExtras().getString("message_type"));

		if (intent.getExtras().getString("message_type").equals("news")) {
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Intent app = new Intent(context, SplashActivity.class);
			PendingIntent pintent = PendingIntent.getActivity(context, 0, app, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification noti = new NotificationCompat.Builder(context)
					.setContentTitle(context.getString(R.string.notification_title))
					.setContentText(context.getString(R.string.notification_text))
					.setSmallIcon(R.drawable.ic_launcher)
					.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
					.setContentIntent(pintent)
					.setAutoCancel(true).build();

			notificationManager.notify(123456, noti);
		} else if (intent.getExtras().getString("message_type").equals("notification")) {
			String title = intent.getExtras().getString("title");
			String message = intent.getExtras().getString("message");

			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			Intent app = new Intent(context, SplashActivity.class);
			PendingIntent pintent = PendingIntent.getActivity(context, 0, app, PendingIntent.FLAG_UPDATE_CURRENT);

			Notification noti = new NotificationCompat.Builder(context)
					.setContentTitle(title)
					.setContentText(message)
					.setSmallIcon(R.drawable.ic_launcher)
					.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
					.setContentIntent(pintent)
					.setAutoCancel(true).build();

			notificationManager.notify(123456, noti);
		}
	}

}
