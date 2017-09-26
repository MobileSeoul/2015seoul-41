package com.cdk.helpme.sms;

import java.util.Date;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.cdk.helpme.activity.SosActivity;
import com.cdk.helpme.common.CommonSharedPreferences;

public class SmsReceiver extends BroadcastReceiver {
	ProgressDialog progressDialog = null;
	LocationManager locationManager;
	LocationListener locationListener;
	boolean sosOn = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		final Bundle bundle = intent.getExtras();
		try {
			if (bundle != null) {
				final Object[] pdusObj = (Object[]) bundle.get("pdus");
				for (int i = 0; i < pdusObj.length; i++) {
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage.getDisplayOriginatingAddress();
					String senderNum = phoneNumber;
					String message = currentMessage.getDisplayMessageBody();
					Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
					int duration = Toast.LENGTH_LONG;
					long lastSosTime = CommonSharedPreferences.getLong(context, CommonSharedPreferences.LAST_SOS_TIME);
					if (lastSosTime == -1 || new Date().getTime() - lastSosTime > 1000 * 60 * 60) {
						return;
					}
					Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, duration).show();
					if (message.contains("어디")) {
						Intent newIntent = new Intent(context, SosActivity.class);
						newIntent.putExtra("senderNum", senderNum);
						newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(newIntent);
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
