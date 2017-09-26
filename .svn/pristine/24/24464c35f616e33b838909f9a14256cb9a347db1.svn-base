package com.cdk.helpme.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.cdk.helpme.api.ApiClient;
import com.cdk.helpme.api.ApiListener;
import com.cdk.helpme.common.LocationSettingUtil;
import com.cdk.helpme.map.NMapCalloutBasicOverlay;
import com.cdk.helpme.map.NMapPOIflagType;
import com.cdk.helpme.map.NMapViewerResourceProvider;
import com.cdk.helpme.model.Hospital;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay.OnStateChangeListener;

public class NaverMapActivity extends NMapActivity {
	private static final String API_KEY = "42fab64a6495c30151880db9c6e99fab";
	NMapView mMapView;
	NMapController mMapController;
	NMapViewerResourceProvider mMapViewerResourceProvider;
	NMapOverlayManager mOverlayManager;
	NMapLocationManager mLocationManager;
	NMapMyLocationOverlay myLocationOverlay;
	
	LocationManager locationManager;
	LocationListener locationListener;

	boolean needLocationUpdate = true;
	boolean needMapChanged = false;
	boolean isFirstDraw = true;
	
	boolean isSecondApiCall = false;

	double nowLat;
	double nowLon;
	
	ProgressDialog progressDialog = null;
	List<Hospital> hospitalList;
	List<String> hospitalIdList;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		initMapView();
		hospitalIdList = new ArrayList<String>();
		hospitalList = new ArrayList<Hospital>();

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				if (needLocationUpdate) {
					needLocationUpdate = false;
					nowLat = location.getLatitude();
					nowLon = location.getLongitude();
					drawMap(location.getLatitude(), location.getLongitude());
				}
			}
		};
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (isSecondApiCall == false) {
					if (progressDialog.isShowing()) {
						Toast.makeText(NaverMapActivity.this, "위치정보를 재 탐색 하는 중입니다.", Toast.LENGTH_SHORT).show();
						drawMap(nowLat, nowLon);
					}
				}
			}
		}, 5000);	
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, locationListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (LocationSettingUtil.isLocationEnabled(this)) {
			progressDialogShow();
		} else {
			LocationSettingUtil.checkLocationSetting(this);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		locationManager.removeUpdates(locationListener);
	}

	private void initMapView() {
		mMapView = new NMapView(this);
		mMapView.setClickable(true);
		mMapView.setApiKey(API_KEY);
		mMapView.setBuiltInZoomControls(true, null);

		mMapView.setOnMapViewTouchEventListener(mapViewTouchEventListener);
		mMapView.setOnMapStateChangeListener(mapStateChangeListener);
		mMapView.setVisibility(View.INVISIBLE);
		
		mMapController = mMapView.getMapController();
		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

		mLocationManager = new NMapLocationManager(this);
		mLocationManager.enableMyLocation(true);
		myLocationOverlay = mOverlayManager.createMyLocationOverlay(mLocationManager, null);
		mOverlayManager.addOverlay(myLocationOverlay);
		
		setContentView(mMapView);
	}

	private void drawMap(double lat, double lon) {
		progressDialogShow();
		
		mMapController.setMapCenter(new NGeoPoint(lon, lat), 10);
		mMapView.setVisibility(View.VISIBLE);

		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = gcd.getFromLocation(lat, lon, 1);
			String stage1 = addresses.get(0).getLocality();
			String stage2 = addresses.get(0).getSubLocality();
			
			Toast.makeText(this, "지도내의 응급병원 위치가 표시됩니다.", Toast.LENGTH_SHORT).show();
			ApiClient.getRealtimeAvailableEmegency(stage1, stage2, new ApiListener() {
				@Override
				public void onSuccess(JSONObject successJson) {
					if (progressDialog != null && progressDialog.isShowing()) {
						progressDialog.hide();
					}

					try {
						hospitalIdList.clear();
						hospitalList.clear();
						JSONObject bodyObject = successJson.getJSONObject("body");
						int totalCount = bodyObject.getInt("totalCount");
						JSONObject itemsObject = bodyObject.getJSONObject("items");
						JSONArray itemArray = itemsObject.getJSONArray("item");

						for (int i = 0; i < totalCount; i++) {
							JSONObject object = itemArray.getJSONObject(i);
							hospitalIdList.add(object.getString("hpid"));
						}

						for (String hospitalId : hospitalIdList) {
							ApiClient.getEmergencyInfo(hospitalId, new ApiListener() {
								@Override
								public void onSuccess(JSONObject successJson) {
									isSecondApiCall = true;
									try {
										JSONObject bodyObject = successJson.getJSONObject("body");
										JSONObject itemsObject = bodyObject.getJSONObject("items");
										JSONObject item = itemsObject.getJSONObject("item");
										Hospital hospital = new Hospital();
										hospital.setId(item.getString("hpid"));
										hospital.setName(item.getString("dutyName"));
										hospital.setTel(item.getString("dutyTel3"));
										hospital.setLat(item.getDouble("wgs84Lat"));
										hospital.setLon(item.getDouble("wgs84Lon"));
										hospital.setAvailble(item.getInt("hvec"));
										hospitalList.add(hospital);
										
										if (hospitalList.size() == hospitalIdList.size()) {
											drawPOIMapData();
										}
									} catch (Exception e) {
									}
								}

								@Override
								public void onProgress(int bytesWritten, int totalSize) {
								}

								@Override
								public void onError(JSONObject errorJson) {
									hideProgressDialog();
								}
							});
						}
					} catch (Exception e) {
						hideProgressDialog();
					}
				}

				@Override
				public void onProgress(int bytesWritten, int totalSize) {
				}

				@Override
				public void onError(JSONObject errorJson) {
					hideProgressDialog();					
				}
			});
		} catch (Exception e) {
			hideProgressDialog();
		}
		
	}
	
	private void hideProgressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.hide();
		}		
	}
	
	private void drawPOIMapData() {
		// set POI data
		int markerId = NMapPOIflagType.PIN;
		int hospitalCount = hospitalList.size();
		if (hospitalCount > 0) {
			NMapPOIdata poiData = new NMapPOIdata(hospitalCount, mMapViewerResourceProvider);
			poiData.beginPOIdata(hospitalCount);

			int index = 0;
			for (Hospital hospital : hospitalList) {
				String commentBox;
				if (hospital.getAvailble() < 0) {
					commentBox = "[" + hospital.getName() + "] 대기인원수: " + hospital.getAvailble()*-1;
				} else {
					commentBox = "[" + hospital.getName() + "] 가용병상수: " + hospital.getAvailble();
				}
				poiData.addPOIitem(hospital.getLon(), hospital.getLat(), commentBox, markerId, index++);
			}
			poiData.endPOIdata();

			// create POI data overlay
			NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
			if (isFirstDraw) {
				poiDataOverlay.showAllPOIdata(0);
				isFirstDraw = false;
			}
			poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
			mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
		}			
	}

	private void progressDialogShow() {
		if (progressDialog == null || progressDialog.isShowing() == false) {
			progressDialog = ProgressDialog.show(NaverMapActivity.this, "헬프미 - 근처 병원 찾기", "근처 병원을 탐색중입니다.");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Toast.makeText(NaverMapActivity.this, "취소하였습니다.", Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
					finish();
				}
			});
		}
	}

	OnMapViewTouchEventListener mapViewTouchEventListener = new OnMapViewTouchEventListener() {
		@Override
		public void onTouchUp(NMapView arg0, MotionEvent arg1) {
		}

		@Override
		public void onTouchDown(NMapView arg0, MotionEvent arg1) {
		}

		@Override
		public void onSingleTapUp(NMapView arg0, MotionEvent arg1) {
		}

		@Override
		public void onScroll(NMapView arg0, MotionEvent arg1, MotionEvent arg2) {
		}

		@Override
		public void onLongPressCanceled(NMapView arg0) {
		}

		@Override
		public void onLongPress(NMapView arg0, MotionEvent arg1) {
		}
	};

	OnMapStateChangeListener mapStateChangeListener = new OnMapStateChangeListener() {
		@Override
		public void onZoomLevelChange(NMapView arg0, int arg1) {
		}

		@Override
		public void onMapInitHandler(NMapView arg0, NMapError errorInfo) {
			if (errorInfo == null) {
				return;
			} else {
			}

			Toast.makeText(NaverMapActivity.this, "지도 생성에 실패하였습니다.", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onMapCenterChangeFine(NMapView arg0) {
		}

		@Override
		public void onMapCenterChange(NMapView view, NGeoPoint point) {
			if (needMapChanged == false) {
				needMapChanged = true;
				return;
			}

			drawMap(point.getLatitude(), point.getLongitude());
		}

		@Override
		public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
		}
	};

	OnStateChangeListener onPOIdataStateChangeListener = new OnStateChangeListener() {
		@Override
		public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
			if (item != null) {
			} else {
			}
		}

		@Override
		public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
			Intent intent = new Intent(NaverMapActivity.this, HospitalActivity.class);
			intent.putExtra("hospitalId", hospitalIdList.get(item.getId()));
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
		}
	};

	OnCalloutOverlayListener onCalloutOverlayListener = new OnCalloutOverlayListener() {
		@Override
		public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {
			// 직접 처리인 경우엔 return null;
			return new NMapCalloutBasicOverlay(itemOverlay, overlayItem, itemBounds);
		}
	};

}