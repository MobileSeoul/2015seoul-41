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
import android.content.DialogInterface.OnCancelListener;
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
import android.widget.Toast;

import com.cdk.helpme.R;
import com.cdk.helpme.api.ApiClient;
import com.cdk.helpme.api.ApiListener;
import com.cdk.helpme.api.ApiUtils;
import com.cdk.helpme.common.LocationSettingUtil;
import com.cdk.helpme.info.InfoUtils;
import com.cdk.helpme.model.Favorite;
import com.google.common.collect.Lists;

public class NearEmergencyActivity extends Activity {
	private boolean needLocationUpdate = false;

	ProgressDialog progressDialog = null;

	LocationManager locationManager;
	LocationListener locationListener;
	String defaultLocationProvider = LocationManager.GPS_PROVIDER;
	Button locationButton;

	LinearLayout locationArea;
	GridView favoriteGridview;
	ArrayList<Favorite> favoriteList;
	SimpleGridCellAdapter favoriteGridAdapter;
	TextView hasNoText;

	double lon;
	double lat;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_near_emergency);

		locationArea = (LinearLayout) findViewById(R.id.location_area);
		hasNoText = (TextView) findViewById(R.id.text_no_near_emergency);
		favoriteList = Lists.newArrayList();
		favoriteGridview = (GridView) findViewById(R.id.near_emergency_grid);
		favoriteGridAdapter = new SimpleGridCellAdapter(getBaseContext(), R.layout.simple_grid_cell, favoriteList);
		favoriteGridview.setAdapter(favoriteGridAdapter);
		favoriteGridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(NearEmergencyActivity.this, HospitalActivity.class);
				intent.putExtra("hospitalId", favoriteGridAdapter.getItem(position).getReferer());
				startActivity(intent);
			}
		});

		locationButton = (Button) findViewById(R.id.near_location_btn);
		locationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final List<String> guList = InfoUtils.findAllGu();
				new AlertDialog.Builder(NearEmergencyActivity.this).setItems(guList.toArray(new CharSequence[guList.size()]), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String gu = guList.get(which);
						locationButton.setText(gu);
						getHospitalList("서울특별시", gu);
					}
				}).setNegativeButton("취소", null).show();
			}
		});

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new ChangeLocationListener();

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, locationListener);
		
		progressDialog = ProgressDialog.show(NearEmergencyActivity.this, "헬프미 - 가까운 응급실 찾기", "위치 정보를 수신하는 중입니다. 수신 후 가까운 응급실 정보를 조회합니다.");
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				Toast.makeText(NearEmergencyActivity.this, "위치정보 수신에 실패하여\n메인화면으로 돌아갑니다.", Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (LocationSettingUtil.isLocationEnabled(this)) {
			needLocationUpdate = true;
		} else {
			LocationSettingUtil.checkLocationSetting(this);
		}
	}

	@Override
	protected void onDestroy() {
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
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
			}

			/*------- To get city name from coordinates -------- */
			Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
			List<Address> addresses;
			try {
				addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				lon = loc.getLongitude();
				lat = loc.getLatitude();
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

	private void getHospitalList(String stage1, String stage2) {
		ApiClient.getRealtimeAvailableEmegency(stage1, stage2, new ApiListener() {
			@Override
			public void onSuccess(JSONObject successJson) {
				locationArea.setVisibility(View.VISIBLE);
				try {
					final JSONArray jsonArray = ApiUtils.getItemArray(successJson);
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
}
