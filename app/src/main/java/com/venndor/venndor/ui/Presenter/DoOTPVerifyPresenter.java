package com.venndor.venndor.ui.Presenter;


import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.ForgetOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Repo.LoginWithOTPReop;
import com.venndor.venndor.ui.Presenter.Repo.NewUserOTPVerifyReop;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoOTPVerifyPresenter {

    private DoOTPVerifyView view;

    public DoOTPVerifyPresenter(DoOTPVerifyView view) {
        this.view = view;
    }


    public void DoOTPVerify(Context context, String otp) {
        Call<LoginWithOTPReop> loginCall = ApiManager.getApi(context).DootploginbyOTPuser(otp);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<LoginWithOTPReop>() {
            @Override
            public void onResponse(Call<LoginWithOTPReop> call, Response<LoginWithOTPReop> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onDoOTPVerifySuccess(response.body(), response.message());
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
            public void onFailure(Call<LoginWithOTPReop> call, Throwable t) {
                view.onLoginOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void ForgetOTPVerify(Context context, String otp) {
        Call<ForgetOTPVerifyReop> loginCall = ApiManager.getApi(context).DOForgetOTPVerifyReop(otp);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ForgetOTPVerifyReop>() {
            @Override
            public void onResponse(Call<ForgetOTPVerifyReop> call, Response<ForgetOTPVerifyReop> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.ForgetOTPVerifySuccess(response.body(), response.message());
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
            public void onFailure(Call<ForgetOTPVerifyReop> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void PostOTPRegistrationApi(Context context, String otp) {
        Call<NewUserOTPVerifyReop> loginCall = ApiManager.getApi(context).PostOTPRegistrationApi(otp);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<NewUserOTPVerifyReop>() {
            @Override
            public void onResponse(Call<NewUserOTPVerifyReop> call, Response<NewUserOTPVerifyReop> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {

                        view.PostOTPRegistrationSuccess(response.body(), response.message());
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
            public void onFailure(Call<NewUserOTPVerifyReop> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public interface DoOTPVerifyView {

        void onOTPVerifyError(String message);


        void onDoOTPVerifySuccess(LoginWithOTPReop response, String message);

        void ForgetOTPVerifySuccess(ForgetOTPVerifyReop response, String message);
        void PostOTPRegistrationSuccess(NewUserOTPVerifyReop response, String message);


        void showHideProgress(boolean isShow);

        void onOTPVerifyFailure(Throwable t);
        void onLoginOTPVerifyFailure(Throwable t);

    }
}
