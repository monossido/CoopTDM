package com.lorenzobraghetto.cooptdm;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lorenzobraghetto.cooptdm.fragments.FragmentNews;
import com.lorenzobraghetto.cooptdm.fragments.FragmentStruttura;
import com.lorenzobraghetto.cooptdm.logic.Header;
import com.lorenzobraghetto.cooptdm.logic.Item;
import com.lorenzobraghetto.cooptdm.logic.ItemAdapter;
import com.lorenzobraghetto.cooptdm.logic.ListItem;

public class MainActivity extends SherlockFragmentActivity {

	private DrawerLayout drawer;
	private ItemAdapter adapter;
	private ListView drawerList;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private FragmentManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		String[] items = getResources().getStringArray(R.array.drawerArray);

		Header item0 = new Header(items[0]);
		ListItem item1 = new ListItem(items[1], R.drawable.ic_launcher, this);

		Header item2 = new Header(items[2]);
		ListItem item3 = new ListItem(items[3], R.drawable.ic_launcher, this);
		ListItem item4 = new ListItem(items[4], R.drawable.ic_launcher, this);
		ListItem item5 = new ListItem(items[5], R.drawable.ic_launcher, this);
		ListItem item6 = new ListItem(items[6], R.drawable.ic_launcher, this);
		ListItem item7 = new ListItem(items[7], R.drawable.ic_launcher, this);
		ListItem item8 = new ListItem(items[8], R.drawable.ic_launcher, this);

		List<Item> itemsList = new ArrayList<Item>();
		itemsList.add(item0);
		itemsList.add(item1);
		itemsList.add(item2);
		itemsList.add(item3);
		itemsList.add(item4);
		itemsList.add(item5);
		itemsList.add(item6);
		itemsList.add(item7);
		itemsList.add(item8);

		adapter = new ItemAdapter(this, itemsList);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer) {
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

		drawer.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		manager = getSupportFragmentManager();

		FragmentNews firstFragment = new FragmentNews();
		Bundle bundle = new Bundle();
		bundle.putBoolean("categories", false);
		firstFragment.setArguments(bundle);
		manager.beginTransaction()
				.replace(R.id.content_frame, firstFragment).commit();

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

	private void openFragment(int position) {
		switch (position) {
		case 0:
			FragmentNews firstFragment = new FragmentNews();
			Bundle bundle = new Bundle();
			bundle.putBoolean("categories", false);
			firstFragment.setArguments(bundle);
			manager.beginTransaction()
					.replace(R.id.content_frame, firstFragment).commit();
			break;
		case 1:
			FragmentNews secondFragment = new FragmentNews();
			Bundle bundleSecond = new Bundle();
			bundleSecond.putBoolean("categories", true);
			secondFragment.setArguments(bundleSecond);
			manager.beginTransaction()
					.replace(R.id.content_frame, secondFragment).commit();
			break;
		case 2:
			FragmentStruttura thirdFragment = new FragmentStruttura();
			manager.beginTransaction()
					.replace(R.id.content_frame, thirdFragment).commit();
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

}
