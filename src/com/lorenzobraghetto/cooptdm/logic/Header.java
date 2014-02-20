package com.lorenzobraghetto.cooptdm.logic;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lorenzobraghetto.cooptdm.R;
import com.lorenzobraghetto.cooptdm.logic.ItemAdapter.RowType;

public class Header implements Item {
	private final String name;

	public Header(String name) {
		this.name = name;
	}

	@Override
	public int getViewType() {
		return RowType.HEADER_ITEM.ordinal();
	}

	@Override
	public View getView(LayoutInflater inflater, View convertView) {
		View view;
		if (convertView == null) {
			view = (View) inflater.inflate(R.layout.header_item, null);
			// Do some initialization
		} else {
			view = convertView;
		}

		TextView text = (TextView) view.findViewById(R.id.title);
		text.setText(name);

		return view;
	}

}
