package com.lorenzobraghetto.cooptdm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lorenzobraghetto.cooptdm.logic.CoopTDMNewsService;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

		if (sp.getBoolean("cron", true))
			context.startService(new Intent(context, CoopTDMNewsService.class));
		//TODO scegliere metodo di avviso
	}

}
