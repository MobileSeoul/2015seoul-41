package com.cdk.helpme.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CommonSharedPreferences {
	public static final String SOS_NUMBER_1_KEY = "SOS_NUM_1";
	public static final String SOS_NUMBER_2_KEY = "SOS_NUM_2";
	public static final String SOS_NUMBER_3_KEY = "SOS_NUM_3";
	public static final String SOS_SIREN_ON = "SOS_SIREN_ON";
	public static final String LAST_SOS_TIME = "LAST_SOS_TIME";

	public static int getInteger(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, 0);
	}

	public static void setInteger(Context context, String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key, "");
	}

	public static void setString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, true);
	}

	public static void setBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static long getLong(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		return sharedPreferences.getLong(key, -1);
	}

	public static void setLong(Context context, String key, long value) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}

}
