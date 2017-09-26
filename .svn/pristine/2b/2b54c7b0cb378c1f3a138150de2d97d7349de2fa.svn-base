package com.cdk.helpme.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.cdk.helpme.R;
import com.cdk.helpme.common.CommonSharedPreferences;
import com.cdk.helpme.common.LocationSettingUtil;
import com.cdk.helpme.sms.SmsUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class SosActivity extends Activity {
	private boolean sosOn = false;
	String senderNum = "";
	ProgressDialog progressDialog = null;
	LocationManager locationManager;
	LocationListener locationListener;
	SoundPool soundManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sos);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new ChangeLocationListener();

		requestSOS();

		if (CommonSharedPreferences.getBoolean(SosActivity.this, CommonSharedPreferences.SOS_SIREN_ON)) {
			soundManager = new SoundPool(5, AudioManager.STREAM_RING, 0);
			final int soundBeep = soundManager.load(SosActivity.this, R.raw.siren, 1);

			soundManager.play(soundBeep, 1f, 1f, 0, -1, 1f);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("도움요청중입니다.\n사이렌을 종료하시려면 완료 버튼을 눌러주세요.");
			builder.setPositiveButton("완료", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					soundManager.stop(soundBeep);
				}
			});
			builder.show();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		senderNum = intent.getStringExtra("senderNum");
		requestSOS();
	}

	private void requestSOS() {
		if (LocationSettingUtil.isLocationEnabled(this)) {
			sosOn = true;
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			progressDialog = ProgressDialog.show(SosActivity.this, "헬프미!", "위치 정보를 수신하는 중입니다. 수신 후 SMS 발송됩니다.");
			progressDialog.setCancelable(true);
			progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					Toast.makeText(SosActivity.this, "위치정보 수신에 실패하여\n메인화면으로 돌아갑니다.", Toast.LENGTH_LONG).show();
					finish();
				}
			});
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
			if (sosOn == false) {
				return;
			} else {
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				sosOn = false;
				locationManager.removeUpdates(locationListener);
			}

			String longitude = "경도:" + loc.getLongitude();
			String latitude = "위도:" + loc.getLatitude();

			Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
			List<Address> addresses;
			try {
				addresses = gcd.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				String s1 = "[헬프미-도움요청]\n\"어디\"라고 답장을 하시면 위치를 다시 전송해 드립니다.";
				String s2 = "[헬프미-현재위치]\n주소:" + addresses.get(0).getAddressLine(0);
				SmsUtils.send(SosActivity.this, s1, getPhoneNumbers());
				SmsUtils.send(SosActivity.this, s2, getPhoneNumbers());
			} catch (IOException e) {
				String s1 = "[헬프미-도움요청]\n\"어디\"라고 답장을 하시면 위치를 다시 전송해 드립니다.";
				String s2 = "[헬프미-현재위치]\n" + longitude + "\n" + latitude;
				SmsUtils.send(SosActivity.this, s1, getPhoneNumbers());
				SmsUtils.send(SosActivity.this, s2, getPhoneNumbers());
				e.printStackTrace();
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

	private List<String> getPhoneNumbers() {
		List<String> numberList = Lists.newArrayList();
		if (TextUtils.isEmpty(senderNum) == false) {
			numberList.add(senderNum);
			return numberList;
		}

		List<String> settingList = Lists.newArrayList();
		String number1 = CommonSharedPreferences.getString(SosActivity.this, CommonSharedPreferences.SOS_NUMBER_1_KEY);
		String number2 = CommonSharedPreferences.getString(SosActivity.this, CommonSharedPreferences.SOS_NUMBER_2_KEY);
		String number3 = CommonSharedPreferences.getString(SosActivity.this, CommonSharedPreferences.SOS_NUMBER_3_KEY);
		if (Strings.isNullOrEmpty(number1) == false) {
			settingList.add(number1);
		}
		if (Strings.isNullOrEmpty(number2) == false) {
			settingList.add(number2);
		}
		if (Strings.isNullOrEmpty(number3) == false) {
			settingList.add(number3);
		}
		return settingList;
	}
}
