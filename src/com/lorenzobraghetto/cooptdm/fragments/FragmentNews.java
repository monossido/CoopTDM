package com.lorenzobraghetto.cooptdm.fragments;

import java.util.List;

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
import com.lorenzobraghetto.cooptdm.logic.Categories;
import com.lorenzobraghetto.cooptdm.logic.CooperativaTerraDiMezzoApplication;
import com.lorenzobraghetto.cooptdm.logic.News;
import com.lorenzobraghetto.cooptdm.logic.NewsAdapter;

public class FragmentNews extends SherlockFragment implements OnNavigationListener {

	private SherlockFragmentActivity activity;
	private ListView listView;
	private List<News> news;
	private List<News> newsTot;
	protected NewsAdapter listAdapter;
	private List<Categories> cats;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_news, container, false);

		activity = getSherlockActivity();
		listView = (ListView) view.findViewById(R.id.news_list);

		if (getArguments().getBoolean("categories")) {
			activity.setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);

			ActionBar actionBar = activity.getSupportActionBar();

			cats = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
					.getCats();

			String[] categories = new String[cats.size()];

			for (int i = 0; i < cats.size(); i++) {
				categories[i] = cats.get(i).getTitolo();
			}

			ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(getActivity(), R.layout.sherlock_spinner_item_mio, categories);

			list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item_mio);

			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			actionBar.setListNavigationCallbacks(list, this);

			news = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
					.getNewsList(actionBar.getSelectedNavigationIndex());
			newsTot = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
					.getNewsTotList();

		} else {
			news = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
					.getNewsList(null);
			newsTot = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
					.getNewsTotList();
		}
		listAdapter = new NewsAdapter(getActivity(), news);

		listView.setAdapter(listAdapter);

		return view;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		news = ((CooperativaTerraDiMezzoApplication) getActivity().getApplication())
				.getNewsList(itemPosition);
		listAdapter.notifyDataSetChanged();
		return true;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

}
