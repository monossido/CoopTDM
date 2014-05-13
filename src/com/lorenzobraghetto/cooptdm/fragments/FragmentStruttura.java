package com.lorenzobraghetto.cooptdm.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.lorenzobraghetto.cooptdm.R;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

public class FragmentStruttura extends SherlockFragment implements OnTabChangeListener, OnTouchListener {

	public final static int STRUTTURA_PARCO = 0;
	public final static int STRUTTURA_ZELEGHE = 1;
	public final static int STRUTTURA_MUSEI = 2;
	public final static int STRUTTURA_HOSTEL = 3;
	public final static int STRUTTURA_CASA = 4;
	public final static int STRUTTURA_OSTELLO = 5;

	private FadingActionBarHelper mFadingHelper;
	private FrameLayout expanded_view;
	protected boolean expanded = false;
	private int previousTab;
	private TabHost mTabHost;
	private int height;
	private int int_struttura;
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

		int_struttura = getArguments().getInt("Struttura");

		switch (getArguments().getInt("Struttura")) {
		case STRUTTURA_PARCO:
			img_header.setImageResource(R.drawable.strutture_parco_header);
			getActivity().setTitle(items[2]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_1);
			orari.setText(R.string.parco_fiorine_orario);
			prezzi.setText(R.string.parco_fiorine_costi);
			luogo.setText(R.string.parco_fiorine_dove);
			struttura_content.setText(Html.fromHtml(getString(R.string.parco_fiorine_presentazione)));
			break;
		case STRUTTURA_ZELEGHE:
			img_header.setImageResource(R.drawable.strutture_zeleghe_header);
			getActivity().setTitle(items[3]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_2);
			luogo.setText(R.string.zeleghe_dove);
			struttura_content.setText(Html.fromHtml(getString(R.string.zeleghe_presentazione)));
			break;
		case STRUTTURA_MUSEI:
			img_header.setImageResource(R.drawable.strutture_musei_colli_euganei_header);
			getActivity().setTitle(items[4]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_3);
			orari.setText(R.string.musei_orario);
			prezzi.setText(R.string.musei_costi);
			luogo.setText(R.string.musei_dove);
			struttura_content.setText(Html.fromHtml(getString(R.string.musei_presentazione)));
			break;
		case STRUTTURA_HOSTEL:
			img_header.setImageResource(R.drawable.strutture_venetian_hostel_header);
			getActivity().setTitle(items[5]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_4);
			orari.setText(R.string.hostel_orario);
			prezzi.setText(R.string.hostel_costi);
			luogo.setText(R.string.hostel_dove);
			struttura_content.setText(Html.fromHtml(getString(R.string.hostel_presentazione)));
			break;
		case STRUTTURA_CASA:
			img_header.setImageResource(R.drawable.strutture_casa_marina_header);
			getActivity().setTitle(items[6]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_5);
			break;
		case STRUTTURA_OSTELLO:
			img_header.setImageResource(R.drawable.strutture_ostello_colli_euganei_header);
			getActivity().setTitle(items[7]);
			getSherlockActivity().getSupportActionBar().setIcon(R.drawable.ico_6);
			break;
		default:
			break;
		}

		expanded_view = (FrameLayout) view.findViewById(android.R.id.tabcontent);

		mTabHost = (TabHost) view.findViewById(R.id.distributionTabhost);
		mTabHost.setup();
		if (int_struttura != STRUTTURA_ZELEGHE) {
			mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.orario)).setIndicator(getString(R.string.orario)).setContent(R.id.orari2));
			mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.costi)).setIndicator(getString(R.string.costi)).setContent(R.id.prezzi2));
		}
		mTabHost.addTab(mTabHost.newTabSpec(getString(R.string.dove)).setIndicator(getString(R.string.dove)).setContent(R.id.luogo2));
		mTabHost.setCurrentTab(-1);
		mTabHost.setOnTabChangedListener(this);

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
			mTabHost.getTabWidget().getChildAt(i).setTag(i + "");
		}

		setHasOptionsMenu(true);

		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		switch (int_struttura) {
		case STRUTTURA_PARCO:
			inflater.inflate(R.menu.fragment_menu_parco, menu);
			break;
		case STRUTTURA_MUSEI:
			inflater.inflate(R.menu.fragment_menu_musei, menu);
			break;
		default:
			break;
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (int_struttura) {
		case STRUTTURA_PARCO:
			if (item.getItemId() == R.id.facebook_settings) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/pages/Parco-Avventura-Le-Fiorine/376460802413091"));
				startActivity(browserIntent);
			}
			break;
		case STRUTTURA_MUSEI:
			if (item.getItemId() == R.id.facebook_settings) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/museicollieuganei"));
				startActivity(browserIntent);
			} else if (item.getItemId() == R.id.twitter_settings) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/MuseiEuganei"));
				startActivity(browserIntent);
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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

	public void expand(final View v) {
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
		expanded = true;
	}

	public void collapse(final View v) {
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
		expanded = false;
	}

	@Override
	public void onTabChanged(String tabId) {
		if (!expanded)
			expand(expanded_view);
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			previousTab = mTabHost.getCurrentTab();
			int currentTab = Integer.parseInt((String) v.getTag());
			if (previousTab == currentTab) {
				if (expanded)
					collapse(expanded_view);
				else
					expand(expanded_view);
				return true;
			}
		}
		return false;
	}

}
