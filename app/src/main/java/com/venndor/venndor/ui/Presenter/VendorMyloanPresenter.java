package com.venndor.venndor.ui.Presenter;


import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.VendorMyloan;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorMyloanPresenter {

    private VendorMyloanView view;

    public VendorMyloanPresenter(VendorMyloanView view) {
        this.view = view;
    }


    public void VendorMyloan(Context context) {
        Call<VendorMyloan> loginCall = ApiManager.getApi(context).VendorMyloan();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<VendorMyloan>() {
            @Override
            public void onResponse(Call<VendorMyloan> call, Response<VendorMyloan> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onVendorMyloanSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onVendorMyloanError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onVendorMyloanError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onVendorMyloanError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onVendorMyloanError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<VendorMyloan> call, Throwable t) {
                view.onVendorMyloanFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public interface VendorMyloanView {

        void onVendorMyloanError(String message);


        void onVendorMyloanSuccess(VendorMyloan response, String message);


        void showHideProgress(boolean isShow);

        void onVendorMyloanFailure(Throwable t);

    }
}
