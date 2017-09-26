package com.cdk.helpme.api;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiUtils {
	public static JSONArray getItemArray(JSONObject jsonObject) {
		JSONArray returnJsonArray = new JSONArray();
		try {
			JSONObject bodyObject = jsonObject.getJSONObject("body");
			JSONObject itemsObject = bodyObject.getJSONObject("items");
			JSONArray itemArray = itemsObject.optJSONArray("item");
			JSONObject itemObject = itemsObject.optJSONObject("item");

			if (itemArray != null) {
				returnJsonArray = itemArray;
			}

			if (itemObject != null) {
				returnJsonArray.put(itemObject);
			}

			return returnJsonArray;
		} catch (Exception e) {
			return returnJsonArray;
		}
	}
}
