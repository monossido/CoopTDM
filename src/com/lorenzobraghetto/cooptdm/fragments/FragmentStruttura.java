package com.lorenzobraghetto.cooptdm.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.lorenzobraghetto.cooptdm.R;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class FragmentStruttura extends SherlockFragment {

	public final static int STRUTTURA_PARCO = 0;
	public final static int STRUTTURA_ZELEGHE = 1;
	public final static int STRUTTURA_MUSEI = 2;
	public final static int STRUTTURA_HOSTEL = 3;
	public final static int STRUTTURA_CASA = 4;
	public final static int STRUTTURA_OSTELLO = 5;

	private FadingActionBarHelper mFadingHelper;
	private LinearLayout collapsed_view;
	private LinearLayout expanded_view;
	protected boolean expanded;
	public static final String ARG_IMAGE_RES = "image_source";
	public static final String ARG_ACTION_BG_RES = "image_action_bs_res";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = mFadingHelper.createView(inflater);

		ImageView img_header = (ImageView) view.findViewById(R.id.image_header);
		String[] items = getResources().getStringArray(R.array.drawerArray);

		TextView orari = (TextView) view.findViewById(R.id.orari2);
		TextView prezzi = (TextView) view.findViewById(R.id.prezzi2);
		TextView luogo = (TextView) view.findViewById(R.id.luogo2);
		TextView struttura_content = (TextView) view.findViewById(R.id.struttura_content);

		switch (getArguments().getInt("Struttura")) {
		case STRUTTURA_PARCO:
			img_header.setImageResource(R.drawable.strutture_parco_header);
			getActivity().setTitle(items[3]);
			orari.setText(R.string.parco_fiorine_orario);
			prezzi.setText(R.string.parco_fiorine_costi);
			luogo.setText(R.string.parco_fiorine_dove);
			struttura_content.setText(Html.fromHtml(getString(R.string.parco_fiorine_presentazione)));
			break;
		case STRUTTURA_ZELEGHE:
			img_header.setImageResource(R.drawable.strutture_zeleghe_header);
			getActivity().setTitle(items[4]);
			break;
		case STRUTTURA_MUSEI:
			img_header.setImageResource(R.drawable.strutture_musei_colli_euganei_header);
			getActivity().setTitle(items[5]);
			break;
		case STRUTTURA_HOSTEL:
			img_header.setImageResource(R.drawable.strutture_venetian_hostel_header);
			getActivity().setTitle(items[6]);
			break;
		case STRUTTURA_CASA:
			img_header.setImageResource(R.drawable.strutture_casa_marina_header);
			getActivity().setTitle(items[7]);
			break;
		case STRUTTURA_OSTELLO:
			img_header.setImageResource(R.drawable.strutture_ostello_colli_euganei_header);
			getActivity().setTitle(items[8]);
			break;
		default:
			break;
		}

		collapsed_view = (LinearLayout) view.findViewById(R.id.collapsed_view);
		expanded_view = (LinearLayout) view.findViewById(R.id.expanded_view);

		collapsed_view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!expanded) {
					expand(expanded_view);
					expanded = true;
				} else {
					collapse(expanded_view);
					expanded = false;
				}
			}
		});

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

	public static void expand(final View v) {
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? LayoutParams.WRAP_CONTENT
						: (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static void collapse(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}
}
