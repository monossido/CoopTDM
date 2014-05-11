package com.lorenzobraghetto.cooptdm.logic;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;

public class NewsAdapter extends BaseAdapter {

	private List<News> mListCard;
	private LayoutInflater mInflater;
	private Activity context;

	public NewsAdapter(Activity context, List<News> listCard) {
		this.context = context;
		this.mListCard = listCard;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mListCard.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mListCard.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	public static class ViewHolder {
		public TextView txtView;
		public TextView data;
		public TextView ora;
		public TextView testo;
		public TextView luogo;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parentView) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.item_news, null);
			holder.txtView = (TextView) convertView
					.findViewById(R.id.titoloNews);
			holder.luogo = (TextView) convertView
					.findViewById(R.id.luogoNews);
			holder.data = (TextView) convertView
					.findViewById(R.id.dataNews);
			holder.ora = (TextView) convertView
					.findViewById(R.id.oraNews);
			holder.testo = (TextView) convertView
					.findViewById(R.id.testoNews);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtView.setText(mListCard.get(arg0).getTitolo());

		int category_int = mListCard.get(arg0).getCategoria();
		Integer category_color = ((CoopTDMApplication) context.getApplication()).getCategoryColor(category_int);
		holder.txtView.setTextColor(category_color);
		Log.v("COOPTDM", "category_color=" + category_color);
		String luogo = mListCard.get(arg0).getLuogo();
		if (luogo.length() > 0)
			holder.luogo.setText(luogo);
		else
			holder.luogo.setVisibility(View.GONE);
		holder.data.setText(mListCard.get(arg0).getData());
		holder.testo.setText(mListCard.get(arg0).getTesto().replace("\\n", "\n"));
		String ora = mListCard.get(arg0).getOra();
		if (ora.length() != 0)
			holder.ora.setText(ora);
		else
			holder.ora.setVisibility(View.GONE);

		return convertView;
	}
}
