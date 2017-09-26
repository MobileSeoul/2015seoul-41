package com.cdk.helpme.api;

import org.json.JSONObject;

public interface ApiListener {
    void onSuccess(JSONObject successJson);

    void onError(JSONObject errorJson);
    
    void onProgress(int bytesWritten, int totalSize);
}
