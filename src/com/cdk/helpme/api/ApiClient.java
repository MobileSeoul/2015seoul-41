package com.cdk.helpme.api;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiClient {
	private static final String API_HOST = "http://openapi.e-gen.or.kr/openapi/service/rest";
	private static final String API_KEY = "XPJPepDBvKY5OV8QPtPvEieRF5bP2HLVdGPnMPbrq0g6SQMszUIXRQf/IYhTjlosYxqG7+HYhOQOoPzc9x3w8w==";

	private static AsyncHttpClient client = new AsyncHttpClient();

	private static void get(String apiUrl, RequestParams params, final ApiListener apiListener) {
		params.add("ServiceKey", API_KEY);
		client.get(API_HOST + apiUrl, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				super.onSuccess(statusCode, headers, responseBody);
				try {
					apiListener.onSuccess(XML.toJSONObject(new String(responseBody)).getJSONObject("response"));
				} catch (JSONException e) {
					apiListener.onError(new JSONObject());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
				super.onFailure(statusCode, headers, responseBody, throwable);
				try {
					apiListener.onError(XML.toJSONObject(new String(responseBody)));
				} catch (JSONException e) {
					apiListener.onError(new JSONObject());
				}
			}
		});
	}

	public static void getRealtimeAvailableEmegency(String 시도, String 시군구, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("STAGE1", 시도);
		params.add("STAGE2", 시군구);
		get(ApiDomains.응급실실시간가용병상정보조회, params, apiListener);
	}

	public static void findEmergencyByLocation(double lon, double lat, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("WGS84_LON", String.valueOf(lon));
		params.add("WGS84_LAT", String.valueOf(lat));
		get(ApiDomains.응급의료기관위치정보조회, params, apiListener);
	}

	public static void getEmergencyInfoList(String 시도, String 시군구, int dayOfWeek, String 기관분류, String 진료과목, String 정렬기준, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("Q0", 시도);
		params.add("Q1", 시군구);
		params.add("QT", String.valueOf(dayOfWeek));
		params.add("QZ", 기관분류);
		params.add("QD", 진료과목);
		params.add("QRD", 정렬기준);
		get(ApiDomains.응급의료기관목록정보조회, params, apiListener);
	}

	public static void getEmergencyInfoListBySubject(String 시도, String 시군구, String 진료과목, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("Q0", 시도);
		params.add("Q1", 시군구);
		params.add("QD", 진료과목);
		get(ApiDomains.응급의료기관목록정보조회, params, apiListener);
	}

	public static void getEmergencyInfo(String HPID, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("HPID", HPID);
		get(ApiDomains.응급의료기관기본정보조회, params, apiListener);
	}

	public static void getCodeMaster(String CM_MID, ApiListener apiListener) {
		RequestParams params = new RequestParams();
		params.add("CM_MID", CM_MID);
		get(ApiDomains.코드마스터정보조회, params, apiListener);
	}
}
