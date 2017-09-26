package com.cdk.helpme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cdk.helpme.R;
import com.cdk.helpme.common.CommonSharedPreferences;

public class SettingActivity extends Activity {

	EditText number1Edit;
	EditText number2Edit;
	EditText number3Edit;
	Button doneButton;
	Button sirenButton;

	boolean sirenOn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_setting_sos);

		number1Edit = (EditText) findViewById(R.id.setting_sos_number_edit_1_edit);
		number1Edit.setText(CommonSharedPreferences.getString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_1_KEY));

		number2Edit = (EditText) findViewById(R.id.setting_sos_number_edit_2_edit);
		number2Edit.setText(CommonSharedPreferences.getString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_2_KEY));

		number3Edit = (EditText) findViewById(R.id.setting_sos_number_edit_3_edit);
		number3Edit.setText(CommonSharedPreferences.getString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_3_KEY));

		doneButton = (Button) findViewById(R.id.setting_sos_done_btn);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonSharedPreferences.setString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_1_KEY, number1Edit.getText().toString());
				CommonSharedPreferences.setString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_2_KEY, number2Edit.getText().toString());
				CommonSharedPreferences.setString(getBaseContext(), CommonSharedPreferences.SOS_NUMBER_3_KEY, number3Edit.getText().toString());

				openMain();

				finish();
			}
		});

		sirenButton = (Button) findViewById(R.id.setting_sos_siren_btn);
		sirenButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isSirenOn = CommonSharedPreferences.getBoolean(getBaseContext(), CommonSharedPreferences.SOS_SIREN_ON);
				setSirenView(!isSirenOn);
				CommonSharedPreferences.setBoolean(getBaseContext(), CommonSharedPreferences.SOS_SIREN_ON, !isSirenOn);
			}
		});

		boolean isSirenOn = CommonSharedPreferences.getBoolean(getBaseContext(), CommonSharedPreferences.SOS_SIREN_ON);
		setSirenView(isSirenOn);
	}

	private void setSirenView(boolean isSirenOn) {
		if (isSirenOn) {
			sirenButton.setBackgroundResource(R.drawable.check_on);
		} else {
			sirenButton.setBackgroundResource(R.drawable.check_off);
		}
	}

	private void openMain() {
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		startActivity(intent);
	}
}
