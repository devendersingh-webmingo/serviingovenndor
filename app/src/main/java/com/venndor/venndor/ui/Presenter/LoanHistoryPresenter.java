package com.venndor.venndor.ui.Presenter;


import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.LoanHistoryRepo;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoanHistoryPresenter {

    private LoanHistoryView view;

    public LoanHistoryPresenter(LoanHistoryView view) {
        this.view = view;
    }
    public void LoanHistory(Context context,String id ) {
        Call<LoanHistoryRepo> loginCall = ApiManager.getApi(context).LoanHistory(id);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<LoanHistoryRepo>() {
            @Override
            public void onResponse(Call<LoanHistoryRepo> call, Response<LoanHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onSignUpSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onOTPVerifyError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onOTPVerifyError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onOTPVerifyError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onOTPVerifyError(String.valueOf(response.code()));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoanHistoryRepo> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }




    public interface LoanHistoryView {
        void onOTPVerifyError(String message);
        void onSignUpSuccess(LoanHistoryRepo response, String message);
        void showHideProgress(boolean isShow);
        void onOTPVerifyFailure(Throwable t);
    }
}
