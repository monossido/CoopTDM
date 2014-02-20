package com.lorenzobraghetto.cooptdm.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.logic.ItemAdapter.RowType;

public class ListItem implements Item {
	private final String str1;
	private final int mDrawable;

	public ListItem(String text1, int drawable, Context context) {
		this.str1 = text1;
		this.mDrawable = drawable;
	}

	@Override
	public int getViewType() {
		return RowType.LIST_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
		if (convertView == null) {
			view = (View) inflater.inflate(R.layout.listitem, null);
			// Do some initialization
		} else {
			view = convertView;
		}

		TextView text1 = (TextView) view.findViewById(R.id.title);
		ImageView image = (ImageView) view.findViewById(R.id.icon);
		text1.setText(str1);
		image.setImageResource(mDrawable);

		return view;
	}

}
