package com.lorenzobraghetto.cooptdm.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.logic.CallbackNews;
import com.lorenzobraghetto.cooptdm.logic.Categories;
import com.lorenzobraghetto.cooptdm.logic.CoopTDMApplication;
import com.lorenzobraghetto.cooptdm.logic.News;
import com.lorenzobraghetto.cooptdm.logic.NewsAdapter;

public class FragmentNews extends SherlockFragment implements OnNavigationListener, OnRefreshListener {

	private SherlockFragmentActivity activity;
	private ListView listView;
	private List<News> news;
	protected NewsAdapter listAdapter;
	private List<Categories> cats;
	private ScrollView no_connection;
	private SwipeRefreshLayout view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_news, container, false);

		activity = getSherlockActivity();
		listView = (ListView) view.findViewById(R.id.news_list);
		no_connection = (ScrollView) view.findViewById(R.id.no_connection);

		view.setOnRefreshListener(this);
		view.setColorScheme(R.color.holo_blue_bright,
				R.color.holo_green_light,
				R.color.holo_orange_light,
				R.color.holo_red_light);

		activity.setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

		ActionBar actionBar = activity.getSupportActionBar();

		cats = ((CoopTDMApplication) getActivity().getApplication())
				.getCats();

		String[] categories = new String[cats.size() + 1];
		categories[0] = "Tutte";
		for (int i = 0; i < cats.size(); i++) {
			categories[i + 1] = cats.get(i).getTitolo();
		}

		ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(getActivity(), R.layout.sherlock_spinner_item_mio, categories);

		list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item_mio);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(list, this);
		getNews();
		return view;
	}

	private void getNews() {
		news = ((CoopTDMApplication) getActivity().getApplication())
				.getNewsList(0);

		if (news.size() != 0) {
			listAdapter = new NewsAdapter(getActivity(), news);
			listView.setAdapter(listAdapter);
			listView.setVisibility(View.VISIBLE);
			no_connection.setVisibility(View.GONE);
		} else {
			listView.setVisibility(View.GONE);
			no_connection.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		getNews();
		return true;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

	@Override
	public void onRefresh() {
		((CoopTDMApplication) getActivity().getApplication())
				.getNews(new CallbackNews() {

					@Override
					public void onDownloaded() {
						getNews();
						view.setRefreshing(false);
					}
				});

	}
}
