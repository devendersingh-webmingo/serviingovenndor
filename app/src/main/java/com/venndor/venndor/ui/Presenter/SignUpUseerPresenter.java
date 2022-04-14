package com.venndor.venndor.ui.Presenter;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.venndor.venndor.ui.Presenter.Repo.ForgetOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Repo.SignupUserRepo;
import com.venndor.venndor.ui.Presenter.Repo.NewUserOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Request.SignUpUser_request;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpUseerPresenter {

    private SignUpUseerView view;

    public SignUpUseerPresenter(SignUpUseerView view) {
        this.view = view;
    }
    public void SignUpUseer(Context context, SignUpUser_request signUpUser_request) {
        Call<SignupUserRepo> loginCall = ApiManager.getApi(context).SignupUserRepo(signUpUser_request);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<SignupUserRepo>() {
            @Override
            public void onResponse(Call<SignupUserRepo> call, Response<SignupUserRepo> response) {
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
            public void onFailure(Call<SignupUserRepo> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public void uploadImage(MultipartBody.Part image, Context context, RequestBody id){
        view.showHideProgress(true);

        Call<ResponseBody> call = ApiManager.getApi(context).SignupUserRepoWithImage(id,image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);
                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onUploadSuccess(response.body(), response.message());
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

    public interface SignUpUseerView {

        void onOTPVerifyError(String message);


        void onSignUpSuccess(SignupUserRepo response, String message);
        void onUploadSuccess(ResponseBody response, String message);



        void showHideProgress(boolean isShow);

        void onOTPVerifyFailure(Throwable t);
    }
}
