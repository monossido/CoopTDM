package com.lorenzobraghetto.cooptdm.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CooperativaTerraDiMezzoApplication extends Application {

	private List<News> news = new ArrayList<News>();
	private List<News> newsTot = new ArrayList<News>();
	private List<Categories> categories = new ArrayList<Categories>();

	public void getNews(final CallbackNews callback) {//TODO cats
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://monossido.ath.cx/cooptdm/api/news.php", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				JSONObject jso_response = null;
				try {
					jso_response = new JSONObject(response);
					JSONArray jsa = jso_response.getJSONArray("news");

					for (int i = 0; i < jsa.length(); i++) {
						JSONObject jso = jsa.getJSONObject(i);

						news.add(new News(jso.getString("titolo"), jso.getString("data")
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
				newsTot = news;
				callback.onDownloaded();
			}
		});
	}

	public List<News> getNewsList(Integer idCategoria) {
		if (idCategoria != null) {
			for (Iterator<News> i = news.iterator(); i.hasNext();) {
				News singolaNews = i.next();

				if (singolaNews.getCategoria() != idCategoria) {
					i.remove();
				}
			}
		} else {
			news = newsTot;
		}
		return news;
	}

	public List<News> getNewsTotList() {
		return newsTot;
	}

	public List<Categories> getCats() {
		return categories;
	}
}
