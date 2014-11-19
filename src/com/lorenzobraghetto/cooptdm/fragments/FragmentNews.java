package com.lorenzobraghetto.cooptdm.fragments;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.logic.CallbackNews;
import com.lorenzobraghetto.cooptdm.logic.Categories;
import com.lorenzobraghetto.cooptdm.logic.CoopTDMApplication;
import com.lorenzobraghetto.cooptdm.logic.News;
import com.lorenzobraghetto.cooptdm.logic.NewsAdapter;
import com.lorenzobraghetto.cooptdm.ui.MainActivity;

public class FragmentNews extends Fragment implements OnNavigationListener, OnRefreshListener {

	private RecyclerView listView;
	private List<News> news = new ArrayList<News>();
	protected NewsAdapter listAdapter;
	private List<Categories> cats;
	private ScrollView no_connection;
	private RelativeLayout view;
	private SwipeRefreshLayout swipeView;
	private int lastCat = 0;
	private ProgressBar progressBarBachecaListView;
	private MainActivity activity;
	private ActionBar actionBar;
	private LinearLayoutManager mLayoutManager;
	private boolean paused = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getNews(0);
		activity = (MainActivity) getActivity();
		activity.setNewToolbar(null, false);
		actionBar = activity.getSupportActionBar();

		if (news.size() != 0 || lastCat != 0) {
			view = (RelativeLayout) inflater.inflate(R.layout.fragment_news, container, false);
			swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
			listView = (RecyclerView) view.findViewById(R.id.news_list);
			progressBarBachecaListView = (ProgressBar) view.findViewById(R.id.progressBarBachecaListView);

			listView.setVisibility(View.VISIBLE);
			listView.setHasFixedSize(false);
			mLayoutManager = new LinearLayoutManager(getActivity());
			listView.setLayoutManager(mLayoutManager);
			listAdapter = new NewsAdapter(getActivity(), this, news);
			listView.setAdapter(listAdapter);
			listView.setItemAnimator(new DefaultItemAnimator());

			cats = ((CoopTDMApplication) getActivity().getApplication())
					.getCats();

			String[] categories = new String[cats.size() + 1];
			categories[0] = "Tutte";
			for (int i = 0; i < cats.size(); i++) {
				categories[i + 1] = cats.get(i).getTitolo();
			}

			ArrayAdapter<CharSequence> list = new ArrayAdapter<CharSequence>(getActivity(), R.layout.sherlock_spinner_item_mio, categories);

			list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item_mio);

			//if (!paused) {
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			actionBar.setListNavigationCallbacks(list, this);
			actionBar.setSelectedNavigationItem(lastCat);
			//}
		} else {
			view = (RelativeLayout) inflater.inflate(R.layout.fragment_no_news, container, false);
			swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
			no_connection = (ScrollView) view.findViewById(R.id.no_connection);
			no_connection.setVisibility(View.VISIBLE);
		}

		swipeView.setOnRefreshListener(this);
		swipeView.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW);

		if (listView != null)
			listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
				private int preLast;
				private int paging = 1;

				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
					final int lastItem = mLayoutManager.findLastCompletelyVisibleItemPosition();
					if (lastItem == news.size() - 1) {
						if (preLast != lastItem) { //to avoid multiple calls for last item
							preLast = lastItem;
							paging++;
							progressBarBachecaListView.setVisibility(View.VISIBLE);
							((CoopTDMApplication) getActivity().getApplication())
									.getNewsPaging(paging, new CallbackNews() {

										@Override
										public void onDownloaded() {
											progressBarBachecaListView.setVisibility(View.GONE);
											getNews(lastCat);
											listAdapter.notifyDataSetChanged();
										}

									});
						}
					}
				}
			});

		listAdapter = new NewsAdapter(getActivity(), this, news);
		listView.setAdapter(listAdapter);
		return view;
	}

	private void getNews(int cat) {
		news.clear();
		news.addAll(((CoopTDMApplication) getActivity().getApplication())
				.getNewsList(cat));
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		lastCat = itemPosition;
		getNews(itemPosition);
		listAdapter.notifyDataSetChanged();
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		activity.getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		paused = true;
	}

	@Override
	public void onRefresh() {
		((CoopTDMApplication) getActivity().getApplication())
				.getNews(new CallbackNews() {

					@Override
					public void onDownloaded() {
						getNews(lastCat);
						swipeView.setRefreshing(false);
						listAdapter.notifyDataSetChanged();
					}
				});

	}

	public void setSpinnerClick(int item) {
		actionBar.setSelectedNavigationItem(item);
	}

	public int getSpinnerClick() {
		return actionBar.getSelectedNavigationIndex();
	}
}
