package com.lorenzobraghetto.cooptdm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import com.lorenzobraghetto.cooptdm.ui.SplashActivity;

public class NotificationFactory {

	public static final int NOTIFICATION_ID_NEWS = 1;
	public static final int NOTIFICATION_ID_NOTIFICATION = 2;

	public static Notification buildNotification(Context context, String title, String message, PendingIntent notificationIntent) {
		Builder notificationBuilder = new NotificationCompat.Builder(context);
		notificationBuilder.setSmallIcon(R.drawable.ic_launcher);

		notificationBuilder.setContentTitle(title);

		notificationBuilder.setContentText(message);

		notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher));

		if (notificationIntent != null) {
			notificationBuilder.setContentIntent(notificationIntent);
		} else {
			Intent app = new Intent(context, SplashActivity.class);
			app.putExtra("fromNotif", true);
			PendingIntent pintent = PendingIntent.getActivity(context, 0, app, PendingIntent.FLAG_UPDATE_CURRENT);

			notificationBuilder.setContentIntent(pintent);
		}
		return new NotificationCompat.BigTextStyle(notificationBuilder)
				.bigText(message).build();

	}

	public static void showNotification(Context context, Notification notification, int id) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		if (sp.getBoolean("notification", true)) {
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(id, notification);
		}
	}

}
