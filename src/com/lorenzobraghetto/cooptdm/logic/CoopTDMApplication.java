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
	private List<News> newsTot = new ArrayList<News>();
	private List<Categories> categories = new ArrayList<Categories>();
	private ArrayList<Integer[]> colors = new ArrayList<Integer[]>();

	public void getNews(final CallbackNews callback) {
		news.clear();
		categories.clear();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", CoopTDMParams.API_KEY_MIO);
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
								, jso.getString("testo"), jso.getInt("categoria"), jso.getString("tags"), jso.getString("dataPubblicazione")));
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
				editor.putInt("lastNewsId", news.get(0).getId());
				editor.commit();
				callback.onDownloaded();
				generateUniqueColors(categories.size());
			}

			public void onFailure(Throwable error, String content) {
				newsTot.clear();
				callback.onDownloaded();
			}
		});

	}

	public void getNewsPaging(Integer page, final CallbackNews callback) {
		news.clear();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("api_key", CoopTDMParams.API_KEY_MIO);
		params.put("page", page.toString());
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
								, jso.getString("testo"), jso.getInt("categoria"), jso.getString("tags"), jso.getString("dataPubblicazione")));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				newsTot.addAll(news);
				callback.onDownloaded();
			}

			public void onFailure(Throwable error, String content) {
				callback.onDownloaded();
			}
		});
	}

	public List<News> getNewsList(Integer idCategoria) {
		news.clear();
		news.addAll(newsTot);
		if (idCategoria != null && idCategoria != 0) {
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

	public Integer[] getCategoryColor(int category_int) {
		return colors.get(category_int - 1); //Non esiste category_int = 0
	}

	private void generateUniqueColors(int amount) {
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#cc0000")
					, Color.parseColor("#ff3300")
					, Color.parseColor("#ff6600")
					, Color.parseColor("#ff9900")
					, Color.parseColor("#ffcc33") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#2b7190")
					, Color.parseColor("#2882a0")
					, Color.parseColor("#288282")
					, Color.parseColor("#28966e")
					, Color.parseColor("#28a050") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#d22852")
					, Color.parseColor("#dc4c65")
					, Color.parseColor("#dc6e7a")
					, Color.parseColor("#e68c93")
					, Color.parseColor("#e6aa96") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#2baab9")
					, Color.parseColor("#3278c8")
					, Color.parseColor("#235a96")
					, Color.parseColor("#645096")
					, Color.parseColor("#964696") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#f0a11a")
					, Color.parseColor("#dcc818")
					, Color.parseColor("#aac832")
					, Color.parseColor("#6eb428")
					, Color.parseColor("#50a032") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#000000")
					, Color.parseColor("#28001e")
					, Color.parseColor("#50193e")
					, Color.parseColor("#781b5b")
					, Color.parseColor("#960078") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#32aa8c")
					, Color.parseColor("#50b478")
					, Color.parseColor("#6eb450")
					, Color.parseColor("#82c85a")
					, Color.parseColor("#96d25a") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#be4b4b")
					, Color.parseColor("#be644b")
					, Color.parseColor("#be7d4b")
					, Color.parseColor("#c8964b")
					, Color.parseColor("#dcaf4b") };
			colors.add(arraycolor);
		}
		if (colors.size() < amount) {
			Integer[] arraycolor = { Color.parseColor("#cc0000")
					, Color.parseColor("#ff3300")
					, Color.parseColor("#ff6600")
					, Color.parseColor("#ff9900")
					, Color.parseColor("#ffcc33") };
			colors.add(arraycolor);
		}
	}

}
