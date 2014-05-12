package com.lorenzobraghetto.cooptdm.ui;

import android.content.Intent;
import android.os.Bundle;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.fragments.FragmentStruttura;

public class StruttureActivity extends MainActivity {

	@Override
	protected void startFirstFragment() {
		FragmentStruttura strutturaFragment = new FragmentStruttura();
		Bundle arguments = new Bundle();
		if (getIntent() == null || getIntent().getExtras() == null)
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
		else
			arguments.putInt("Struttura", getIntent().getExtras().getInt("Struttura"));

		strutturaFragment.setArguments(arguments);
		manager.beginTransaction()
				.replace(R.id.content_frame, strutturaFragment).commit();

		int padding_in_dp = 40;
		final float scale = getResources().getDisplayMetrics().density;
		int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
		drawerList.setPadding(0, padding_in_px, 0, 0);
	}

	@Override
	protected void openFragment(int position) {
		Intent newsIntent = new Intent(StruttureActivity.this, MainActivity.class);
		FragmentStruttura strutturaFragment = new FragmentStruttura();
		Bundle arguments = new Bundle();
		switch (position) {
		case 0:
			Bundle bundle = new Bundle();
			newsIntent.putExtras(bundle);
			finish();
			startActivity(newsIntent);
			break;
		case 2:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 3:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_ZELEGHE);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 4:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_MUSEI);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 5:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_HOSTEL);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 6:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_CASA);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 7:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_OSTELLO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).commit();
			break;
		case 9:
			startActivity(new Intent(this, Settings.class));
			break;
		default:
			break;
		}
		drawer.closeDrawer(drawerList);
	}
}
