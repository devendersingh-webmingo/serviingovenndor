package com.venndor.venndor.ui.network_api_retrofit;

import org.json.JSONException;

/**
 * Created by ss-115 on 2/2/17.
 */

public interface RetrofitCallBackListener {
    public void RetrofitCallBackListener(String result, String action) throws JSONException;
//    public void RetrofitCallBackListenarFailed(String result, String action) throws JSONException;
}


