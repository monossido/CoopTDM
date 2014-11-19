package com.lorenzobraghetto.cooptdm.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.fragments.FragmentNews;
import com.lorenzobraghetto.cooptdm.fragments.FragmentStruttura;
import com.lorenzobraghetto.cooptdm.fragments.SettingsFragment;
import com.lorenzobraghetto.cooptdm.logic.Header;
import com.lorenzobraghetto.cooptdm.logic.Item;
import com.lorenzobraghetto.cooptdm.logic.ItemAdapter;
import com.lorenzobraghetto.cooptdm.logic.ListItem;

public class MainActivity extends ActionBarActivity {

	protected DrawerLayout drawer;
	private ItemAdapter adapter;
	protected ListView drawerList;
	protected FragmentManager manager;
	private FragmentNews newsFragment;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	public Toolbar toolbar;
	private Toolbar previousToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		setNewToolbar(toolbar, false);

		manager = getSupportFragmentManager();

		startFirstFragment();
	}

	public void setUpDrawer(Toolbar toolbar) {
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		String[] items = getResources().getStringArray(R.array.drawerArray);

		ListItem item1 = new ListItem(items[0], R.drawable.ic_launcher, this);

		Header item2 = new Header(items[1]);
		ListItem item3 = new ListItem(items[2], R.drawable.ico_1, this);
		ListItem item4 = new ListItem(items[3], R.drawable.ico_2, this);
		ListItem item5 = new ListItem(items[4], R.drawable.ico_3, this);
		ListItem item6 = new ListItem(items[5], R.drawable.ico_4, this);
		ListItem item7 = new ListItem(items[6], R.drawable.ico_5, this);
		ListItem item8 = new ListItem(items[7], R.drawable.ico_6, this);
		Header item9 = new Header(items[8]);
		ListItem item10 = new ListItem(items[9], R.drawable.ic_launcher, this);

		List<Item> itemsList = new ArrayList<Item>();
		itemsList.add(item1);
		itemsList.add(item2);
		itemsList.add(item3);
		itemsList.add(item4);
		itemsList.add(item5);
		itemsList.add(item6);
		itemsList.add(item7);
		itemsList.add(item8);
		itemsList.add(item9);
		itemsList.add(item10);

		adapter = new ItemAdapter(this, itemsList);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this
				, drawer
				, toolbar
				, R.string.navigation_drawer_open
				, R.string.navigation_drawer_close) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
		};

		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				openFragment(position);
			}

		});

		drawer.setDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();
	}

	protected void startFirstFragment() {
		newsFragment = new FragmentNews();
		manager.beginTransaction()
				.replace(R.id.content_frame, newsFragment).commit();
		drawer.openDrawer(drawerList);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (drawer.isDrawerOpen(drawerList)) {
				drawer.closeDrawer(drawerList);
			} else {
				drawer.openDrawer(drawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	protected void openFragment(int position) {
		FragmentStruttura strutturaFragment = new FragmentStruttura();
		Bundle arguments = new Bundle();
		switch (position) {
		case 0:
			if (!newsFragment.isVisible()) {
				manager.beginTransaction()
						.replace(R.id.content_frame, newsFragment).addToBackStack("Main").commit();
			}
			break;
		case 2:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 3:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_ZELEGHE);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 4:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_MUSEI);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 5:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_HOSTEL);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 6:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_CASA);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 7:
			arguments.putInt("Struttura", FragmentStruttura.STRUTTURA_OSTELLO);
			strutturaFragment.setArguments(arguments);
			manager.beginTransaction()
					.replace(R.id.content_frame, strutturaFragment).addToBackStack("Struttura").commit();
			break;
		case 9:
			manager.beginTransaction()
					.replace(R.id.content_frame, new SettingsFragment()).addToBackStack("Settings").commit();
			break;
		default:
			break;
		}
		drawer.closeDrawer(drawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		if (newsFragment != null) {
			if (newsFragment.getSpinnerClick() == -1)
				super.onBackPressed();
			else
				newsFragment.setSpinnerClick(0);
		}
	}

	public void setNewToolbar(Toolbar mToolBar, boolean fromStruttura) {
		if (previousToolbar != null)
			previousToolbar.setVisibility(View.GONE);
		if (mToolBar == null)
			mToolBar = toolbar;
		mToolBar.setVisibility(View.VISIBLE);
		mToolBar.setBackgroundColor(getResources().getColor(R.color.tdm_green_light));
		setSupportActionBar(mToolBar);
		setUpDrawer(mToolBar);
		previousToolbar = mToolBar;
	}
}
