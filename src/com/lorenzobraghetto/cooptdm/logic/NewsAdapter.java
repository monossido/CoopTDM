package com.lorenzobraghetto.cooptdm.logic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;

public class NewsAdapter extends BaseAdapter {

	private List<String> mListCard;
	private LayoutInflater mInflater;

	public NewsAdapter(Context context, List<String> listCard) {
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
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup parentView) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.item_news, null);
			holder.txtView = (TextView) convertView
					.findViewById(R.id.txtView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtView.setText(mListCard.get(arg0));

		return convertView;
	}
}
