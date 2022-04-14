package com.venndor.venndor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.irozon.sneaker.Sneaker;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

public class ForgetPassword  extends BaseActivity {
    EditText etnewPassword,etConfirmPassword;
    LinearLayout llUpdatePassword;
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        init();
    }
    /*forget*/
    private void init() {
        pref=new Preferences(mActivity);

        llUpdatePassword=findViewById(R.id.llUpdatePassword);
        etnewPassword=findViewById(R.id.etnewPassword);
        etConfirmPassword=findViewById(R.id.etConfirmPassword);
        llUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordd=   etnewPassword.getText().toString();
                String Cpasswordd=   etConfirmPassword.getText().toString();
                if (passwordd.length() < 5) {
                    Sneaker.with(ForgetPassword.this)
                            .setTitle("The password must be at least 5 characters!")
                            .setMessage("")
                            .sneakWarning();

                } else if (!passwordd.equals(Cpasswordd)) {

                    Sneaker.with(ForgetPassword.this)
                            .setTitle("Confirm password not match")
                            .setMessage("")
                            .sneakWarning();

                }else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        String userId = pref.get("user_id");
                        String otp = pref.get("otp");
                        PostForgetApi(userId, otp, passwordd, Cpasswordd);
                    } else {
                        // AppUtils.showErrorMessage(etnewPassword, getString(R.string.errorInternet), mActivity);
                        Toast.makeText(ForgetPassword.this, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();

                    }

                }

          /*
                if (etnewPassword.getText().toString().isEmpty()) {
                   // AppUtils.showErrorMessage(etnewPassword, "please enter Password ", mActivity);
                    Toast.makeText(ForgetPassword.this, "please enter Password ", Toast.LENGTH_SHORT).show();
                } else if (etConfirmPassword.getText().toString().isEmpty()) {
                   // AppUtils.showErrorMessage(etnewPassword, "please enter Confirm Password", mActivity);
                    Toast.makeText(ForgetPassword.this, "please enter Confirm Password ", Toast.LENGTH_SHORT).show();

                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        String userId = pref.get("user_id");
                        String otp = pref.get("otp");
                        String newPassword = etnewPassword.getText().toString();
                        String ConfPassword = etConfirmPassword.getText().toString();
                        PostForgetApi(userId, otp, newPassword, ConfPassword);


                    } else {
                       // AppUtils.showErrorMessage(etnewPassword, getString(R.string.errorInternet), mActivity);
                        Toast.makeText(ForgetPassword.this, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();

                    }


                }*/
            }

        });
    }
    private void PostForgetApi(String userId, String otp, String newPassword, String confPassword) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostvendornewPassWord;
        AndroidNetworking.post(url)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("otp", otp)
                .addBodyParameter("password", newPassword)
                .addBodyParameter("password_confirmation", confPassword)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseForgetJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseForgetJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //  AppUtils.showToastSort(mActivity, "" + message);

                Toast.makeText(this, message+"", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(mActivity, loginWithEmail.class));
            }else{
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                // AppUtils.showToastSort(mActivity, "" + error);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }


}