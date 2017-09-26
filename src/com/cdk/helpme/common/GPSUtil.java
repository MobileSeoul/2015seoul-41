package com.cdk.helpme.common;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class GPSUtil {
	static LocationManager locationManager = null;
	static Location myLocation = null;
	static Geocoder geoCoder = null;
	static double latPoint;
	static double lngPoint;
	static String myAddress;
	static String defaultLocationProvider = LocationManager.GPS_PROVIDER;
	static boolean isNetworkConnected = false;
	
	private static void init(Context context) {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		geoCoder = new Geocoder(context, Locale.KOREA);

		String mProvider = locationManager.getBestProvider(new Criteria(), true);
		if (TextUtils.equals(mProvider, LocationManager.GPS_PROVIDER)) {
			defaultLocationProvider = LocationManager.GPS_PROVIDER;
		} else {
			defaultLocationProvider = LocationManager.NETWORK_PROVIDER;
		}		
	}

	private static String getProvider() {
		return defaultLocationProvider;
	}
	
	private static String getNetworkType(Context context) {
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	
		if (manager != null) {
			NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobile != null && mobile.isConnected()) {
				isNetworkConnected = true;
				if (mobile.getSubtypeName().equals("LTE")) {
					return "LTE";
				} else {
					return "3G";
				}
			} else {
				NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (wifi.isConnected()) {
					isNetworkConnected = true;
					return "wifi";
				}
				isNetworkConnected = false;
			}
		}
		
		return "";
	}
	
	private static String getAddress() {
		StringBuffer mAddress = new StringBuffer();
		
		if(myLocation != null) {
			latPoint = myLocation.getLatitude();
			lngPoint = myLocation.getLongitude();
			 
			if(isGpsProvider()) {
				try {
					List<Address> addresses = geoCoder.getFromLocation(latPoint, lngPoint, 1);
					 
					for(Address addr: addresses){
						int index = addr.getMaxAddressLineIndex();
			
						for (int i = 0 ; i <= index ; i++){
							mAddress.append(addr.getAddressLine(i));
							mAddress.append(" ");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		myAddress = mAddress.toString();
		return myAddress;
	}
	
	private static boolean isGpsProvider() {
		return TextUtils.equals(defaultLocationProvider, LocationManager.GPS_PROVIDER);
	}

}
