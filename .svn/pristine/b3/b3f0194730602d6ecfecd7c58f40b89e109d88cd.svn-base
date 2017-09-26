package com.cdk.helpme.activity;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdk.helpme.R;

public class SubjectGridCellAdapter extends ArrayAdapter<String> {
	private Context mContext;

	public SubjectGridCellAdapter(Context context, int textViewResourceId, ArrayList<String> items) {
		super(context, textViewResourceId, items);
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			v = inflater.inflate(R.layout.subject_grid_cell, parent, false);
		}
		String subjectInfo = getItem(position);
		if (subjectInfo != null) {
			ImageView ivSubject = (ImageView) v.findViewById(R.id.subject_cell_image);
			TextView tvSubject = (TextView) v.findViewById(R.id.subject_cell_text);
			String[] splittedSubject = subjectInfo.split(":");
			if (ivSubject != null) {
				int randomNum = new Random().nextInt(6);
				switch (randomNum) {
				case 0:
					ivSubject.setImageResource(R.drawable.btn4_1);
					break;
				case 1:
					ivSubject.setImageResource(R.drawable.btn4_2);
					break;
				case 2:
					ivSubject.setImageResource(R.drawable.btn4_3);
					break;
				case 3:
					ivSubject.setImageResource(R.drawable.btn4_4);
					break;
				case 4:
					ivSubject.setImageResource(R.drawable.btn4_5);
					break;
				case 5:
				default:
					ivSubject.setImageResource(R.drawable.btn4_6);
					break;
				}
			}

			if (tvSubject != null) {
				tvSubject.setText(splittedSubject[1]);
				tvSubject.setTag(splittedSubject[0]);
			}
		}
		return v;
	}
}
