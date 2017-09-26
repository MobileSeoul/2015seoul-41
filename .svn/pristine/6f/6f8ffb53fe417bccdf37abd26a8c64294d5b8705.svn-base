package com.cdk.helpme.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.cdk.helpme.R;
import com.google.common.collect.Lists;

public class SubjectEmergencyActivity extends Activity {
	private ArrayList<String> subjectInfoList;
	private final static String SUBJECT_INFO_STRING = "D001:내과,D002:소아청소년과,D003:신경과,D004:정신건강의학과,D005:피부과,D006:외과,D007:흉부외과,D008:정형외과,D009:신경외과,D010:성형외과,D011:산부인과,D012:안과,D013:이비인후과,D014:비뇨기과,D016:재활의학과,D017:마취통증의학과,D018:영상의학과,D019:치료방사선과,D020:임상병리과,D021:해부병리과,D022:가정의학과,D023:핵의학과,D024:응급의학과,D026:치과,D034:구강악안면외과";

	GridView subjectGrid;
	SubjectGridCellAdapter subjectGridAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_subject_emergency);

		subjectInfoList = Lists.newArrayList(SUBJECT_INFO_STRING.split(","));

		subjectGrid = (GridView) findViewById(R.id.subject_emergency_grid);
		subjectGridAdapter = new SubjectGridCellAdapter(getBaseContext(), R.layout.subject_grid_cell, subjectInfoList);
		subjectGrid.setAdapter(subjectGridAdapter);
		subjectGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SubjectEmergencyActivity.this, SelectedSubjectEmergencyActivity.class);
				intent.putExtra("subjectInfo", subjectInfoList.get(position));
				startActivity(intent);
			}
		});
	}
}