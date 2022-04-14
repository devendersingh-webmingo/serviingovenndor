package com.venndor.venndor.ui.Presenter;

import android.content.Context;

import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeorderPresenter {

    private ChangeorderView view;

    public ChangeorderPresenter(ChangeorderView view) {
        this.view = view;
    }

    public void ChangeCODorder(Context context, RequestBody orderid, RequestBody status, RequestBody payment_collected, RequestBody remark, MultipartBody.Part image) {
        view.showHideProgress(true);

        Call<ResponseBody> call = ApiManager.getApi(context).Changeorder(orderid, status, payment_collected, remark, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onCODChangeorderSuccess(response.body(), response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void ChangeWithoutCODorder(Context context, RequestBody orderid, RequestBody status, RequestBody remark, MultipartBody.Part image) {
        view.showHideProgress(true);

        Call<ResponseBody> call = ApiManager.getApi(context).ChangeWithoutCODorder(orderid, status, remark, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onCODChangeorderSuccess(response.body(), response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void VerifyWithoutCODCompleteser(Context context, String order_id, String status, String remark, String otp) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).VerifyWithoutCODCompleteserviceotp(order_id,status,remark,otp);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onVerifyCompleteserviceotpSuccess(response.body(), response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }
    public void VerifyCODCompleteser(Context context, String order_id, String status, String remark, String otp,String payment) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).VerifyCODCompleteserviceotp(order_id,status,remark,otp,payment);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onVerifyCompleteserviceotpSuccess(response.body(), response.message());
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public interface ChangeorderView {

        void onOTPVerifyError(String message);

        void onCODChangeorderSuccess(ResponseBody response, String message);

        void onVerifyCompleteserviceotpSuccess(ResponseBody response, String message);


        void showHideProgress(boolean isShow);

        void onOTPVerifyFailure(Throwable t);
    }
}
