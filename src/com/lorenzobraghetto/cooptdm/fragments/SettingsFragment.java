package com.lorenzobraghetto.cooptdm.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.support.v4.preference.PreferenceFragment;
import android.text.Html;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.ui.MainActivity;

public class SettingsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MainActivity activity = (MainActivity) getActivity();
		activity.setNewToolbar(null, false);

		addPreferencesFromResource(R.xml.preference);

		Preference about = findPreference("pref_about");
		about.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				PackageInfo pInfo = null;
				try {
					pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				String version = pInfo.versionName;
				String dialogText = String.format(getString(R.string.pref_about_dialog), version);

				Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.custom_dialog);
				dialog.setTitle(R.string.pref_about);

				// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.dialogText);
				text.setText(Html.fromHtml(dialogText));

				dialog.show();
				return true;
			}
		});

		CheckBoxPreference notification = (CheckBoxPreference) findPreference("pref_notification");
		notification.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) {
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
				Editor edit = sp.edit();
				edit.putBoolean("notification", preference.isEnabled());
				edit.commit();
				return true;
			}
		});
	}
}
