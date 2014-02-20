package com.lorenzobraghetto.cooptdm.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.logic.NewsAdapter;

public class FragmentNews extends SherlockFragment implements OnNavigationListener {

	private SherlockFragmentActivity activity;
	private ListView listView;
	private ArrayList<String> news;
	private ArrayList<String> newsTot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_news, container, false);

		activity = getSherlockActivity();
		news = new ArrayList<String>();

		if (getArguments().getBoolean("categories")) {
			activity.setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

			ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(
					getActivity(), R.array.categories, R.layout.sherlock_spinner_item_mio);
			list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item_mio);

			activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			activity.getSupportActionBar().setListNavigationCallbacks(list, this);
			news.add("newsCat1");
			news.add("newsCat2");
			news.add("newsCat3");
			news.add("newsCat4");
			news.add("newsCat5");
			news.add("newsCat6");
		} else {
			news.add("news1");
			news.add("news2");
			news.add("news3");
			news.add("news4");
			news.add("news5");
			news.add("news6");
		}
		newsTot = news;
		listView = (ListView) view.findViewById(R.id.news_list);

		NewsAdapter listAdapter = new NewsAdapter(getActivity(), news);

		listView.setAdapter(listAdapter);

		return view;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		//prendere da newsTot e mettere in news solo quelel delal categoria
		return false;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

}
