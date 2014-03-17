package com.lorenzobraghetto.cooptdm;

import android.app.Activity;
import android.content.Intent;

import com.lorenzobraghetto.cooptdm.logic.CallbackNews;
import com.lorenzobraghetto.cooptdm.logic.CooperativaTerraDiMezzoApplication;

public class SplashActivity extends Activity {

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		CallbackNews callback = new CallbackNews() {

			@Override
			public void onDownloaded() {
				finish();
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}

		};

		((CooperativaTerraDiMezzoApplication) getApplication()).getNews(callback);
	}
}
