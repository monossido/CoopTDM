package com.lorenzobraghetto.cooptdm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lorenzobraghetto.cooptdm.logic.CoopTDMNewsService;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, CoopTDMNewsService.class));
	}

}
