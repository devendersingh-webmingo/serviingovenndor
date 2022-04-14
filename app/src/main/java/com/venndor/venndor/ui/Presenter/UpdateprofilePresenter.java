package com.venndor.venndor.ui.Presenter;

import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.SignupUserRepo;
import com.venndor.venndor.ui.Presenter.Repo.UpdateprofileUserRepo;

import com.venndor.venndor.ui.Presenter.Request.SignUpUser_request;
import com.venndor.venndor.ui.Presenter.Request.UpdateProfile_request;
import com.venndor.venndor.ui.Profile.MyProfile;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.venndor.venndor.view.AppUrls.getProfile;

public class UpdateprofilePresenter {

    private updateProfileView view;

    public UpdateprofilePresenter(updateProfileView view) {
        this.view = view;
    }

    public void UpdateProfileUseer(Context context, UpdateProfile_request updateProfile_request, MyProfile myProfile) {
        Call<UpdateprofileUserRepo> loginCall = ApiManager.getApi(context).UpdateprofileUserRepo(updateProfile_request);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<UpdateprofileUserRepo>() {
            @Override
            public void onResponse(Call<UpdateprofileUserRepo> call, Response<UpdateprofileUserRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        /*  view.onSignUpSuccess(response.body(), response.message());*/
                        view.onupdateProfileSuccess(response.body(),response.message());
                        myProfile.getProfile();
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
            public void onFailure(Call<UpdateprofileUserRepo> call, Throwable t) {
                view.onOTPVerifyFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void Updatepassword(Context context,String oldpass,String newpass,String confirmpass) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).UpdatePassword("PUT",oldpass,newpass,confirmpass);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onupdatPaswordSuccess(response.body(), response.message());

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

    public interface updateProfileView {

        void onOTPVerifyError(String message);


        /* void onSignUpSuccess(SignupUserRepo response, String message);*/
        void onupdateProfileSuccess(UpdateprofileUserRepo response, String message);

        void onupdatPaswordSuccess(ResponseBody response, String message);


        void showHideProgress(boolean isShow);

        void onOTPVerifyFailure(Throwable t);
    }
}
