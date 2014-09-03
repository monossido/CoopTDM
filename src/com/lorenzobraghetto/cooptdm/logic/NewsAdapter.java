package com.lorenzobraghetto.cooptdm.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ocpsoft.prettytime.PrettyTime;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;

public class NewsAdapter extends BaseAdapter {

	private List<News> mListCard;
	private LayoutInflater mInflater;
	private Activity context;
	private List<Integer> mListColorsTag = new ArrayList<Integer>();

	public NewsAdapter(Activity context, List<News> listCard) {
		this.context = context;
		this.mListCard = listCard;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mListColorsTag.add(Color.rgb(136, 136, 136));
		mListColorsTag.add(Color.rgb(0, 0, 0));
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
		return mListCard.get(arg0).getId();
	}

	public static class ViewHolder {
		public TextView txtView;
		public TextView dataPubblicazione;
		public TextView ora;
		public TextView testo;
		public TextView luogo;
		public TextView data;
		public LinearLayout hscrollview;
		public ImageView expandImage;
	}

	@Override
	public View getView(final int arg0, View convertView, ViewGroup parentView) {
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.item_news, null);
			holder.txtView = (TextView) convertView
					.findViewById(R.id.titoloNews);
			holder.luogo = (TextView) convertView
					.findViewById(R.id.luogoNews);
			holder.dataPubblicazione = (TextView) convertView
					.findViewById(R.id.dataPubblicazioneNews);
			holder.data = (TextView) convertView
					.findViewById(R.id.data);
			holder.ora = (TextView) convertView
					.findViewById(R.id.oraNews);
			holder.testo = (TextView) convertView
					.findViewById(R.id.testoNews);
			holder.hscrollview = (LinearLayout) convertView
					.findViewById(R.id.hscrollview);
			holder.expandImage = (ImageView) convertView.findViewById(R.id.expandImg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtView.setText(mListCard.get(arg0).getTitolo());

		int category_int = mListCard.get(arg0).getCategoria();
		Integer category_color = ((CoopTDMApplication) context.getApplication()).getCategoryColor(category_int);
		holder.txtView.setTextColor(category_color);

		String luogo = mListCard.get(arg0).getLuogo();
		if (luogo.length() > 0) {
			holder.luogo.setVisibility(View.VISIBLE);
			holder.luogo.setText(luogo);
		} else
			holder.luogo.setVisibility(View.GONE);

		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataPubblicazione = mListCard.get(arg0).getDataPubblicazione();
		Date dateStr;
		try {
			dateStr = parserSDF.parse(dataPubblicazione);

			PrettyTime p = new PrettyTime();

			holder.dataPubblicazione.setText(context.getString(R.string.pubblicato) + " " + p.format(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		holder.testo.setText(mListCard.get(arg0).getTesto().replace("\\n", "\n"));
		holder.data.setText(mListCard.get(arg0).getData());

		String ora = mListCard.get(arg0).getOra();
		if (ora.length() != 0) {
			holder.ora.setText(ora);
			holder.ora.setVisibility(View.VISIBLE);
		} else
			holder.ora.setVisibility(View.GONE);

		String[] tags = mListCard.get(arg0).getTags();

		holder.hscrollview.removeAllViews();

		for (int i = 0; i < tags.length; i++) {
			LinearLayout linear_tv = (LinearLayout) mInflater.inflate(R.layout.tags_layout, null);
			TextView tv = (TextView) linear_tv.findViewById(R.id.tag);
			tv.setText(tags[i]);
			tv.setTextColor(Color.WHITE);
			if (i == 0)
				tv.setBackgroundColor(category_color);
			else
				tv.setBackgroundColor(generateColor(i - 1));
			holder.hscrollview.addView(linear_tv);
		}

		holder.expandImage.setOnClickListener(new OnClickListener() {

			private boolean expanded = false;

			@Override
			public void onClick(View v) {

				if (!expanded) {
					holder.expandImage.setImageResource(R.drawable.navigation_collapse);
					holder.testo.setVisibility(View.VISIBLE);
				} else {
					holder.expandImage.setImageResource(R.drawable.navigation_expand);
					holder.testo.setVisibility(View.GONE);
				}
				expanded = !expanded;
			}
		});

		return convertView;
	}

	private Integer generateColor(int position) {
		if (mListColorsTag.size() <= position) {
			Random rand = new Random();

			int red = rand.nextInt();
			int green = rand.nextInt();
			int blue = rand.nextInt();

			Integer color = Color.rgb(red, green, blue);
			mListColorsTag.add(color);
			return color;
		} else {
			return mListColorsTag.get(position);
		}
	}
}
