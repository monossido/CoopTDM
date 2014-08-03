package com.lorenzobraghetto.cooptdm.ui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.text.Html;

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
						.setMessage(Html.fromHtml(getString(R.string.pref_about_dialog)))
						.create().show();
				return true;
			}
		});

		CheckBoxPreference notification = (CheckBoxPreference) findPreference("pref_notification");
		notification.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Settings.this);
				Editor edit = sp.edit();
				edit.putBoolean("notification", preference.isEnabled());
				edit.commit();
				return true;
			}
		});
	}
}
