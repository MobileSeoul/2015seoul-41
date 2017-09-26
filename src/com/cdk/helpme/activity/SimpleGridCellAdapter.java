package com.cdk.helpme.activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cdk.helpme.R;
import com.cdk.helpme.model.Favorite;

public class SimpleGridCellAdapter extends ArrayAdapter<Favorite> {
	private Context mContext;

	public SimpleGridCellAdapter(Context context, int textViewResourceId, ArrayList<Favorite> items) {
		super(context, textViewResourceId, items);
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			v = inflater.inflate(R.layout.simple_grid_cell, parent, false);
		}
		Favorite favorite = getItem(position);

		TextView tvSubject = (TextView) v.findViewById(R.id.favorite_cell_text);
		tvSubject.setText(favorite.getTitle());

		if (position + 2 < getCount()) {
			v.findViewById(R.id.grid_cell_bridge).setVisibility(View.VISIBLE);
		} else {
			v.findViewById(R.id.grid_cell_bridge).setVisibility(View.GONE);
		}

		return v;
	}
}
