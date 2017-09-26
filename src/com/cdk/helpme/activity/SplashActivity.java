package com.cdk.helpme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.cdk.helpme.R;

public class SplashActivity extends Activity {
	ImageView imgView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final boolean isFirst = preferences.getBoolean("isFirstOpen", true);

		imgView = (ImageView) findViewById(R.id.splash_image);
		imgView.setVisibility(ImageView.VISIBLE);
		imgView.setBackgroundResource(R.drawable.bg_splash1);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				imgView.setBackgroundResource(R.drawable.bg_splash2);
			}
		}, 400);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				imgView.setBackgroundResource(R.drawable.bg_splash3);
			}
		}, 800);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				imgView.setBackgroundResource(R.drawable.bg_splash4);
			}
		}, 1200);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				imgView.setBackgroundResource(R.drawable.bg_splash5);
			}
		}, 1600);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (isFirst) {
					startActivity(new Intent(SplashActivity.this, SettingActivity.class));
					SharedPreferences.Editor editor = preferences.edit();
					editor.putBoolean("isFirstOpen", false);
					editor.commit();
					finish();
				} else {
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}
			}
		}, 2000);
	}
}
