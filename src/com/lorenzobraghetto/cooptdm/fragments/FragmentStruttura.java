package com.lorenzobraghetto.cooptdm.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;
import com.lorenzobraghetto.cooptdm.R;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class FragmentStruttura extends SherlockFragment {

	public final static int STRUTTURA_PARCO = 0;
	private Bundle mArguments;
	private FadingActionBarHelper mFadingHelper;
	public static final String ARG_IMAGE_RES = "image_source";
	public static final String ARG_ACTION_BG_RES = "image_action_bs_res";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = mFadingHelper.createView(inflater);

		ImageView img_header = (ImageView) view.findViewById(R.id.image_header);

		switch (getArguments().getInt("Struttura")) {
		case STRUTTURA_PARCO:
			img_header.setImageResource(R.drawable.strutture_parco_header);
			break;
		default:
			break;
		}

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mFadingHelper = new FadingActionBarHelper()
				.actionBarBackground(R.drawable.ab_background_light)
				.headerLayout(R.layout.fragment_header)
				.contentLayout(R.layout.fragment_struttura);

		mFadingHelper.initActionBar(activity);
	}
}
