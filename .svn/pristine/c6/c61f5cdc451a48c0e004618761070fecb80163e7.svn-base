package com.cdk.helpme.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdk.helpme.R;
import com.cdk.helpme.api.ApiClient;
import com.cdk.helpme.api.ApiListener;
import com.cdk.helpme.api.ApiUtils;
import com.cdk.helpme.info.InfoUtils;
import com.cdk.helpme.model.Favorite;
import com.google.common.collect.Lists;

public class SelectedSubjectEmergencyActivity extends Activity {
	private boolean needLocationUpdate = false;

	ProgressDialog progressDialog = null;

	LocationManager locationManager;
	LocationListener locationListener;
	String[] subjectInfos;
	Button locationButton;

	TextView titleText;

	GridView favoriteGridview;
	ArrayList<Favorite> favoriteList;
	SimpleGridCellAdapter favoriteGridAdapter;
	TextView hasNoText;
	LinearLayout locationArea;

	double lon;
	double lat;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		subjectInfos = getIntent().getStringExtra("subjectInfo").split(":");

		setContentView(R.layout.activity_selected_subject_emergency);

		locationArea = (LinearLayout) findViewById(R.id.subject_location_area);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new ChangeLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		favoriteList = Lists.newArrayList();
		favoriteGridview = (GridView) findViewById(R.id.selected_subject_emergency_grid);
		favoriteGridAdapter = new SimpleGridCellAdapter(getBaseContext(), R.layout.simple_grid_cell, favoriteList);
		favoriteGridview.setAdapter(favoriteGridAdapter);
		favoriteGridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(SelectedSubjectEmergencyActivity.this, HospitalActivity.class);
				intent.putExtra("hospitalId", favoriteGridAdapter.getItem(position).getReferer());
				startActivity(intent);
			}
		});
		locationButton = (Button) findViewById(R.id.subject_location_btn);
		locationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final List<String> guList = InfoUtils.findAllGu();
				new AlertDialog.Builder(SelectedSubjectEmergencyActivity.this).setItems(guList.toArray(new CharSequence[guList.size()]), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String gu = guList.get(which);
						locationButton.setText(gu);
						getHospitalList("서울특별시", gu);
					}
				}).setNegativeButton("취소", null).show();
			}
		});

		titleText = (TextView) findViewById(R.id.selected_subject_title);
		titleText.setText("가까운 " + subjectInfos[1] + " 목록");

		hasNoText = (TextView) findViewById(R.id.text_no_subject_emergency);

		needLocationUpdate = true;
		progressDialog = ProgressDialog.show(SelectedSubjectEmergencyActivity.this, "헬프미!", "위치 정보를 수신하는 중입니다. 수신 후 정보를 조회합니다.");
	}

	@Override
	protected void onDestroy() {
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}

	private void getHospitalList(String string, String gu) {
		ApiClient.getEmergencyInfoListBySubject(string, gu, subjectInfos[0], new ApiListener() {
			@Override
			public void onSuccess(JSONObject successJson) {
				locationArea.setVisibility(View.VISIBLE);
				try {
					JSONArray jsonArray = ApiUtils.getItemArray(successJson);
					List<Favorite> newFavoriteList = Lists.newArrayList();
					for (int i = 0; i < jsonArray.length(); i++) {
						Favorite favorite = new Favorite();
						favorite.setReferer(jsonArray.getJSONObject(i).getString("hpid"));
						favorite.setTitle(jsonArray.getJSONObject(i).getString("dutyName"));
						favorite.setType("hospital");
						newFavoriteList.add(favorite);
					}
					favoriteGridAdapter.clear();
					favoriteGridAdapter.addAll(newFavoriteList);
					if (favoriteGridAdapter.isEmpty()) {
						favoriteGridview.setVisibility(View.GONE);
						hasNoText.setVisibility(View.VISIBLE);
					} else {
						favoriteGridview.setVisibility(View.VISIBLE);
						hasNoText.setVisibility(View.GONE);
					}
				} catch (Exception e) {
				}
			}

			@Override
			public void onError(JSONObject errorJson) {
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
			}
		});
	}

	private class ChangeLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location loc) {
			if (needLocationUpdate == false) {
				return;
			} else {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				needLocationUpdate = false;
				locationManager.removeUpdates(locationListener);
			}
			/*------- To get city name from coordinates -------- */
			Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
			List<Address> addresses;
			try {
				lon = loc.getLongitude();
				lat = loc.getLatitude();
				addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				String stage1 = addresses.get(0).getLocality();
				String stage2 = addresses.get(0).getSubLocality();
				locationButton.setText(stage2);
				getHospitalList(stage1, stage2);
			} catch (IOException e) {

			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
