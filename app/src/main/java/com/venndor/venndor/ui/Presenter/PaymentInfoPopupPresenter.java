package com.venndor.venndor.ui.Presenter;

import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.PaymentInfoPopup;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInfoPopupPresenter {

    private PaymentInfoPopupView view;

    public PaymentInfoPopupPresenter(PaymentInfoPopupView view) {
        this.view = view;
    }

  

  

 
    public void PaymentInfoPopup(Context context, String payout_id) {
        Call<PaymentInfoPopup> loginCall = ApiManager.getApi(context).PaymentInfoPopupDetails(payout_id);

        view.showHideProgress(true);
        loginCall.enqueue(new Callback<PaymentInfoPopup>() {
            @Override
            public void onResponse(Call<PaymentInfoPopup> call, Response<PaymentInfoPopup> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onPaymentInfoPopupSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onPaymentInfoPopupError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onPaymentInfoPopupError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onPaymentInfoPopupError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onPaymentInfoPopupError(String.valueOf(response.code()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentInfoPopup> call, Throwable t) {
                view.onPaymentInfoPopupFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public interface PaymentInfoPopupView {

        void onPaymentInfoPopupError(String message);


        void onPaymentInfoPopupSuccess(PaymentInfoPopup response, String message);


        void showHideProgress(boolean isShow);

        void onPaymentInfoPopupFailure(Throwable t);
    }
}
