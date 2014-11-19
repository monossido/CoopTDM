package com.lorenzobraghetto.cooptdm.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.ocpsoft.prettytime.PrettyTime;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.fragments.FragmentNews;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

	private List<News> mListCard;
	private LayoutInflater mInflater;
	private Activity context;
	private Integer[] mListColorsTag;
	private FragmentNews mFragment;

	public NewsAdapter(Activity context, FragmentNews fragment, List<News> listCard) {
		this.context = context;
		this.mListCard = listCard;
		this.mFragment = fragment;
	}

	@Override
	public int getItemCount() {
		return mListCard.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		News news = mListCard.get(position);
		holder.txtView.setText(news.getTitolo());

		final int category_int = news.getCategoria();
		mListColorsTag = ((CoopTDMApplication) context.getApplication()).getCategoryColor(category_int);

		Integer category_color = mListColorsTag[0];

		String luogo = news.getLuogo();
		if (luogo.length() > 0) {
			holder.luogo.setVisibility(View.VISIBLE);
			holder.luogo.setText(luogo);
		} else
			holder.luogo.setVisibility(View.GONE);

		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dataPubblicazione = news.getDataPubblicazione();
		Date dateStr;
		try {
			dateStr = parserSDF.parse(dataPubblicazione);

			PrettyTime p = new PrettyTime();

			holder.dataPubblicazione.setText(context.getString(R.string.pubblicato) + " " + p.format(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		holder.testo.setText(news.getTesto().replace("\\n", "\n"));
		holder.data.setText(news.getData());

		String ora = news.getOra();
		if (ora.length() != 0) {
			holder.ora.setText(ora);
			holder.ora.setVisibility(View.VISIBLE);
		} else
			holder.ora.setVisibility(View.GONE);

		String[] tags = news.getTags();

		holder.hscrollview.removeAllViews();

		for (int i = 0; i < tags.length; i++) {
			LinearLayout linear_tv = (LinearLayout) LayoutInflater.from(holder.hscrollview.getContext()).inflate(R.layout.tags_layout, null);
			TextView tv = (TextView) linear_tv.findViewById(R.id.tag);
			tv.setText(tags[i]);
			tv.setTextColor(Color.WHITE);
			if (i == 0) {
				tv.setBackgroundColor(category_color);
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mFragment.setSpinnerClick(category_int);
					}
				});
			} else
				tv.setBackgroundColor(generateColor(i));
			holder.hscrollview.addView(linear_tv);
		}

		holder.card_content.setOnClickListener(new OnClickListener() {

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

		holder.itemView.setTag(news);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, null);
		return new ViewHolder(v);
	}

	private Integer generateColor(int position) {
		if (mListColorsTag.length <= position) {
			Random rand = new Random();

			int red = rand.nextInt();
			int green = rand.nextInt();
			int blue = rand.nextInt();

			Integer color = Color.rgb(red, green, blue);
			return color;
		} else {
			return mListColorsTag[position];
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public TextView txtView;
		public TextView dataPubblicazione;
		public TextView ora;
		public TextView testo;
		public TextView luogo;
		public TextView data;
		public LinearLayout hscrollview;
		public ImageView expandImage;
		public RelativeLayout card_content;

		public ViewHolder(View itemView) {
			super(itemView);
			txtView = (TextView) itemView
					.findViewById(R.id.titoloNews);
			luogo = (TextView) itemView
					.findViewById(R.id.luogoNews);
			dataPubblicazione = (TextView) itemView
					.findViewById(R.id.dataPubblicazioneNews);
			data = (TextView) itemView
					.findViewById(R.id.data);
			ora = (TextView) itemView
					.findViewById(R.id.oraNews);
			testo = (TextView) itemView
					.findViewById(R.id.testoNews);
			hscrollview = (LinearLayout) itemView
					.findViewById(R.id.hscrollview);
			expandImage = (ImageView) itemView.findViewById(R.id.expandImg);
			card_content = (RelativeLayout) itemView.findViewById(R.id.card_content);

			//itemView.setOnClickListener(new OnClickListener() {

		}
	}

}
