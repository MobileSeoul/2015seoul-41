package com.cdk.helpme.activity;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdk.helpme.R;
import com.cdk.helpme.common.CommonSharedPreferences;
import com.cdk.helpme.common.LocationSettingUtil;
import com.cdk.helpme.model.Favorite;
import com.google.common.collect.Lists;

public class MainActivity extends Activity implements OnClickListener {
	LinearLayout mainMenuLayout;
	LinearLayout favoriteMenuLayout;
	GridView favoriteGridview;
	ImageView mainMenuImage;
	ImageView favoriteMenuImage;
	ImageView sosButton;
	ImageView settingButton;
	ImageView findNearEmergencyButton;
	ImageView findEmergencyBySubjectButton;
	ImageView findRealtimeHospitalButton;
	ImageView findRealtimePharmacyButton;
	TextView hasNoFavoriteText;

	ArrayList<Favorite> favoriteList;
	SimpleGridCellAdapter favoriteGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
		initEvent();

		favoriteList = Lists.newArrayList();
		favoriteGridAdapter = new SimpleGridCellAdapter(getBaseContext(), R.layout.simple_grid_cell, favoriteList);
		favoriteGridview.setAdapter(favoriteGridAdapter);
		favoriteGridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Favorite favorite = favoriteGridAdapter.getItem(position);
				if ("hospital".equals(favorite.getType())) {
					Intent intent = new Intent(MainActivity.this, HospitalActivity.class);
					intent.putExtra("hospitalId", favorite.getReferer());
					startActivity(intent);
				}
			}
		});
		favoriteGridview.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
				CharSequence[] boardMenuItems = { "삭제" };
				new AlertDialog.Builder(MainActivity.this).setItems(boardMenuItems, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Favorite favorite = favoriteGridAdapter.getItem(position);
							favoriteGridAdapter.remove(favorite);
							favorite.delete();
							break;
						default:
							break;
						}
						reloadList();
					}
				}).setNegativeButton("취소", null).show();
				return false;
			}

		});
		LocationSettingUtil.checkLocationSetting(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initUI() {
		// 메인메뉴, 즐겨찾기 설정
		mainMenuLayout = (LinearLayout) findViewById(R.id.layout_main_menu);
		favoriteMenuLayout = (LinearLayout) findViewById(R.id.layout_favorite_menu);

		mainMenuImage = (ImageView) findViewById(R.id.btn_main_menu);
		favoriteMenuImage = (ImageView) findViewById(R.id.btn_favorite_menu);

		sosButton = (ImageView) findViewById(R.id.btn_main_sos);

		findNearEmergencyButton = (ImageView) findViewById(R.id.btn_main_search_nearby);
		findEmergencyBySubjectButton = (ImageView) findViewById(R.id.btn_main_search_subject);
		findRealtimeHospitalButton = (ImageView) findViewById(R.id.btn_main_realtime_hospital);
		findRealtimePharmacyButton = (ImageView) findViewById(R.id.btn_main_realtime_pharmacy);

		favoriteGridview = (GridView) findViewById(R.id.favorite_gridview);
		hasNoFavoriteText = (TextView) findViewById(R.id.text_no_favorite);

		settingButton = (ImageView) findViewById(R.id.btn_main_setting);
	}

	private void initEvent() {
		mainMenuImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainMenuLayout.setVisibility(View.VISIBLE);
				favoriteMenuLayout.setVisibility(View.GONE);
				mainMenuImage.setImageResource(R.drawable.menu_on);
				favoriteMenuImage.setImageResource(R.drawable.favorite_off);
			}
		});

		favoriteMenuImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainMenuLayout.setVisibility(View.GONE);
				favoriteMenuLayout.setVisibility(View.VISIBLE);
				mainMenuImage.setImageResource(R.drawable.menu_off);
				favoriteMenuImage.setImageResource(R.drawable.favorite_on);

				if (favoriteGridview.getChildCount() == 0) {
					hasNoFavoriteText.setVisibility(View.VISIBLE);
				} else {
					hasNoFavoriteText.setVisibility(View.GONE);
				}
				reloadList();
			}
		});

		sosButton.setOnClickListener(this);
		settingButton.setOnClickListener(this);
		findNearEmergencyButton.setOnClickListener(this);
		findEmergencyBySubjectButton.setOnClickListener(this);
		findRealtimeHospitalButton.setOnClickListener(this);
		findRealtimePharmacyButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_main_sos:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("정말 요청하시겠습니까?");
			builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CommonSharedPreferences.setLong(MainActivity.this, CommonSharedPreferences.LAST_SOS_TIME, new Date().getTime());
					Intent intent = new Intent(MainActivity.this, SosActivity.class);
					startActivity(intent);
				}
			});
			builder.setNegativeButton("아니오", null);
			builder.show();
			break;
		case R.id.btn_main_setting:
			Intent intent = new Intent(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_main_search_nearby:
			Intent nearIntent = new Intent(MainActivity.this, NearEmergencyActivity.class);
			startActivity(nearIntent);
			break;
		case R.id.btn_main_search_subject:
			Intent subjectIntent = new Intent(MainActivity.this, SubjectEmergencyActivity.class);
			startActivity(subjectIntent);
			break;
		case R.id.btn_main_realtime_hospital:
		case R.id.btn_main_realtime_pharmacy:
			notReadyAlertDialog();
		default:
			break;
		}
	}

	private void notReadyAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("준비중인 기능입니다.");
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		builder.show();
	}

	private void reloadList() {
		favoriteList = Lists.newArrayList(Favorite.findAll(Favorite.class));
		favoriteGridAdapter.clear();
		favoriteGridAdapter.addAll(favoriteList);
		if (favoriteGridAdapter.isEmpty()) {
			favoriteGridview.setVisibility(View.GONE);
			hasNoFavoriteText.setVisibility(View.VISIBLE);
		} else {
			favoriteGridview.setVisibility(View.VISIBLE);
			hasNoFavoriteText.setVisibility(View.GONE);
		}
	}
}
