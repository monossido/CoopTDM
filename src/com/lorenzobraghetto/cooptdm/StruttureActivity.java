package com.lorenzobraghetto.cooptdm;

import android.content.Intent;
import android.os.Bundle;

import com.lorenzobraghetto.cooptdm.fragments.FragmentStruttura;

public class StruttureActivity extends MainActivity {

	@Override
	protected void startFirstFragment() {
		FragmentStruttura strutturaFragment = new FragmentStruttura();
		Bundle arguments = new Bundle();
		arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
		strutturaFragment.setArguments(arguments);
		manager.beginTransaction()
				.replace(R.id.content_frame, strutturaFragment).commit();

		drawerList.setPadding(0, 80, 0, 0);//TODO tradurre in dpi
	}

	@Override
	protected void openFragment(int position) {
		Intent newsIntent = new Intent(StruttureActivity.this, MainActivity.class);
		FragmentStruttura strutturaFragment = new FragmentStruttura();
		Bundle arguments = new Bundle();
		switch (position) {
		case 0:
			Bundle bundle = new Bundle();
			bundle.putBoolean("categories", false);
			newsIntent.putExtras(bundle);
			startActivity(newsIntent);
			break;
		case 1:
			Bundle bundleSecond = new Bundle();
			bundleSecond.putBoolean("categories", true);
			newsIntent.putExtras(bundleSecond);
			startActivity(newsIntent);
			break;
		case 3:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 4:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 5:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 6:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		default:
			break;
		}
		drawer.closeDrawer(drawerList);
	}
}
