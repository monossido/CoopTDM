package com.lorenzobraghetto.cooptdm.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
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
import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.fragments.FragmentNews;
import com.lorenzobraghetto.cooptdm.fragments.FragmentStruttura;
import com.lorenzobraghetto.cooptdm.logic.Header;
import com.lorenzobraghetto.cooptdm.logic.Item;
import com.lorenzobraghetto.cooptdm.logic.ItemAdapter;
import com.lorenzobraghetto.cooptdm.logic.ListItem;

public class MainActivity extends SherlockFragmentActivity {

	protected DrawerLayout drawer;
	private ItemAdapter adapter;
	protected ListView drawerList;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	protected FragmentManager manager;
	private boolean fromStrutture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extra = getIntent().getExtras();
		if (extra != null)
			fromStrutture = extra.getBoolean("fromStrutture", false);

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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

		startFirstFragment();
	}

	protected void startFirstFragment() {
		FragmentNews firstFragment = new FragmentNews();
		Bundle bundle = new Bundle();
		firstFragment.setArguments(bundle);
		manager.beginTransaction()
				.replace(R.id.content_frame, firstFragment).commit();
		if (!fromStrutture)
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
		FragmentNews newsFragment = new FragmentNews();
		Intent struttureIntent = new Intent(MainActivity.this, StruttureActivity.class);
		struttureIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		Bundle bundle = new Bundle();
		switch (position) {
		case 0:
			newsFragment.setArguments(bundle);
			manager.beginTransaction()
					.replace(R.id.content_frame, newsFragment).commit();
			break;
		case 2:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_PARCO);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 3:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_ZELEGHE);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 4:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_MUSEI);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 5:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_HOSTEL);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 6:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_CASA);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 7:
			bundle.putInt("Struttura", FragmentStruttura.STRUTTURA_OSTELLO);
			struttureIntent.putExtras(bundle);
			finish();
			startActivity(struttureIntent);
			break;
		case 9:
			startActivity(new Intent(this, Settings.class));
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
