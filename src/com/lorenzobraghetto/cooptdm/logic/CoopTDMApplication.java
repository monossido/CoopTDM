package com.lorenzobraghetto.cooptdm.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lorenzobraghetto.cooptdm.CoopTDMParams;

public class CoopTDMApplication extends Application {

	private List<News> news = new ArrayList<News>();
	private List<News> newsTot;
	private List<Categories> categories = new ArrayList<Categories>();
	private ArrayList<Integer> colors = new ArrayList<Integer>();

	public void getNews(final CallbackNews callback) {
		news.clear();
		categories.clear();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", CoopTDMParams.API_KEY);
		client.post(CoopTDMParams.BASE_URL + "api/news.php", params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				JSONObject jso_response = null;
				try {
					jso_response = new JSONObject(response);
					JSONArray jsa = jso_response.getJSONArray("news");

					for (int i = 0; i < jsa.length(); i++) {
						JSONObject jso = jsa.getJSONObject(i);

						news.add(new News(jso.getInt("id"), jso.getString("titolo"), jso.getString("data")
								, jso.getString("ora"), jso.getString("luogo")
								, jso.getString("testo"), jso.getInt("categoria")));
					}

					JSONArray jsa_cats = jso_response.getJSONArray("cats");

					for (int i = 0; i < jsa_cats.length(); i++) {
						JSONObject jso = jsa_cats.getJSONObject(i);

						categories.add(new Categories(jso.getString("titolo"), jso.getInt("id")));
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				newsTot = new ArrayList<News>(news);
				SharedPreferences pref = getSharedPreferences("CoopTDMSettings", Context.MODE_PRIVATE);
				Editor editor = pref.edit();
				editor.putInt("lastNewsId", news.get(news.size() - 1).getId());
				editor.commit();
				callback.onDownloaded();
				generateUniqueColors(categories.size());
			}
		});
	}

	public List<News> getNewsList(Integer idCategoria) {
		news.clear();
		news.addAll(newsTot);
		if (idCategoria != null) {
			for (Iterator<News> i = news.iterator(); i.hasNext();) {
				News singolaNews = i.next();
				if (singolaNews.getCategoria() != idCategoria) {
					i.remove();
				}
			}
		}
		return news;
	}

	public List<News> getNewsTotList() {
		return newsTot;
	}

	public List<Categories> getCats() {
		return categories;
	}

	public Integer getCategoryColor(int category_int) {
		return colors.get(category_int);
	}

	private void generateUniqueColors(int amount) { //TODO
		if (colors.size() < amount)
			colors.add(Color.parseColor("#d10000"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#2b7190"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#ff003b"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#2b9032"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#00bbca"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#cf9600"));
		if (colors.size() < amount)
			colors.add(Color.parseColor("#000000"));
	}
}
