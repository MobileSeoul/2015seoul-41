package com.cdk.helpme.activity;

import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdk.helpme.R;
import com.cdk.helpme.api.ApiClient;
import com.cdk.helpme.api.ApiListener;
import com.cdk.helpme.common.CommonSharedPreferences;
import com.cdk.helpme.model.Favorite;
import com.cdk.helpme.sms.SmsUtils;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class HospitalActivity extends Activity {
	TextView hospitalName;
	TextView findNearHospital;
	TextView sendMyLocation;
	TextView hospitalPhoneNumber;
	TextView hospitalAvailableNumber;
	TextView hospitalWaitNumber;
	ImageView addFavorite;
	ProgressDialog loadingDialog;

	String mHospitalId;

	JSONObject hospitalInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hospital);
		initUI();

		mHospitalId = getIntent().getStringExtra("hospitalId");
		loadingDialog = ProgressDialog.show(HospitalActivity.this, "처리중", "기다려 착하지");

		ApiClient.getEmergencyInfo(mHospitalId, new ApiListener() {
			@Override
			public void onSuccess(JSONObject successJson) {
				if (loadingDialog != null && loadingDialog.isShowing()) {
					loadingDialog.hide();
				}
				try {
					JSONObject bodyObject = successJson.getJSONObject("body");
					JSONObject itemsObject = bodyObject.getJSONObject("items");
					hospitalInfo = itemsObject.getJSONObject("item");
					setHospitalInfo();
				} catch (Exception e) {
				}
			}

			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				loadingDialog = new ProgressDialog(HospitalActivity.this);
				loadingDialog.show();
			}

			@Override
			public void onError(JSONObject errorJson) {
				if (loadingDialog != null && loadingDialog.isShowing()) {
					loadingDialog.hide();
				}
			}
		});
	}

	private void initUI() {
		hospitalName = (TextView) findViewById(R.id.detail_hospital_name);
		hospitalAvailableNumber = (TextView) findViewById(R.id.detail_available_number);
		hospitalWaitNumber = (TextView) findViewById(R.id.detail_wait_number);
		findNearHospital = (TextView) findViewById(R.id.detail_find_near_hospital);
		findNearHospital.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(HospitalActivity.this, NaverMapActivity.class));
			}
		});
		sendMyLocation = (TextView) findViewById(R.id.detail_send_my_location);
		sendMyLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					SmsUtils.send(HospitalActivity.this, "[헬프미-병원정보]" + hospitalInfo.getString("dutyName") + "(" + hospitalInfo.getString("dutyTel1") + ")", getPhoneNumbers());
					SmsUtils.send(HospitalActivity.this, "[헬프미-병원주소]" + hospitalInfo.getString("dutyAddr"), getPhoneNumbers());
				} catch (Exception e) {
				}
			}
		});
		hospitalPhoneNumber = (TextView) findViewById(R.id.detail_phone_number);
		addFavorite = (ImageView) findViewById(R.id.detail_favorite_add);
		addFavorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Favorite favorite = new Favorite();
					favorite.setType("hospital");
					favorite.setReferer(mHospitalId);
					favorite.setTitle(hospitalInfo.getString("dutyName"));
					favorite.save();
					Toast.makeText(HospitalActivity.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(HospitalActivity.this, "즐겨찾기에 추가를 실패하였습니다.", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void setHospitalInfo() throws Exception {
		hospitalName.setText(hospitalInfo.getString("dutyName"));
		hospitalPhoneNumber.setText(hospitalInfo.getString("dutyTel1"));

		int realtimeBedCount = hospitalInfo.getInt("hvec");
		if (realtimeBedCount < 0) {
			realtimeBedCount *= -1;
			hospitalAvailableNumber.setText("0");
			hospitalWaitNumber.setText(String.valueOf(realtimeBedCount));
		} else {
			hospitalAvailableNumber.setText(String.valueOf(realtimeBedCount));
			hospitalWaitNumber.setText("0");
		}
	}

	private List<String> getPhoneNumbers() {
		List<String> settingList = Lists.newArrayList();
		String number1 = CommonSharedPreferences.getString(HospitalActivity.this, CommonSharedPreferences.SOS_NUMBER_1_KEY);
		String number2 = CommonSharedPreferences.getString(HospitalActivity.this, CommonSharedPreferences.SOS_NUMBER_2_KEY);
		String number3 = CommonSharedPreferences.getString(HospitalActivity.this, CommonSharedPreferences.SOS_NUMBER_3_KEY);
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
