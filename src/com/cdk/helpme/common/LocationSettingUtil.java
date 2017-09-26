package com.cdk.helpme.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

public class LocationSettingUtil {
	public static void checkLocationSetting(final Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Activity.LOCATION_SERVICE);
		boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if (!isGpsEnabled && !isNetworkEnabled) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("앱을 사용하시려면 위치정보가 필요합니다. 위치정보 설정 페이지로 이동하시겠습니까?");
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				}
			});
			builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			builder.show();
		}
	}
	
	public static boolean isLocationEnabled(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Activity.LOCATION_SERVICE);
		boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		return isGpsEnabled || isNetworkEnabled;
	}
}
