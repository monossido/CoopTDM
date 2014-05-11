package com.lorenzobraghetto.cooptdm.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.lorenzobraghetto.cooptdm.R;

public class Settings extends SherlockPreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preference);

		Preference about = findPreference("pref_about");
		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
				builder.setTitle(R.string.pref_about)
						.setMessage(R.string.pref_about_dialog)
						.create().show();
				return true;
			}
		});
	}
}
