package com.venndor.venndor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class SignUp extends BaseActivity {
    EditText etmobilenumber;
    Preferences pref;

    CheckBox cb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        init();
    }

    private void init() {
        pref = new Preferences(mActivity);
        cb2 = findViewById(R.id.cb2);
        etmobilenumber = findViewById(R.id.etmobilenumber);
        findViewById(R.id.llSendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etmobilenumber.getText().toString().length()!=10) {
                    // AppUtils.showErrorMessage(etmobilenumber, "please enter  mobile number", mActivity);

                    Sneaker.with(SignUp.this)
                            .setTitle("please enter Valid mobile number.")
                            .setMessage("")
                            .sneakWarning();
                } else if (!cb2.isChecked()) {
                    //  Toast.makeText(SignUp.this, "please cheched accept all the Terms and Condition ", Toast.LENGTH_LONG).show();

                    Sneaker.with(SignUp.this)
                            .setTitle(" Please accept all the terms & conditions to proceed further.")
                            .setMessage("")
                            .sneakWarning();
                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        // AppTools.showGifDialog(mActivity, R.mipmap.creative);
                        PostsignupApi(etmobilenumber.getText().toString().trim());
                        pref.set("mobilenumber", etmobilenumber.getText().toString().trim()).commit();
                    } else {
                        // AppUtils.showErrorMessage(etmobilenumber, getString(R.string.errorInternet), mActivity);
                        Toast.makeText(SignUp.this, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        findViewById(R.id.tvSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginWithMobile.class));
            }
        });
    }

    private void PostsignupApi(String mobno) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.sendOTPNewSignUp;
        AndroidNetworking.post(url)
                .addBodyParameter("mobile_number", mobno)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {

                            if (error.getErrorCode()==401)
                            {


                                Sneaker.with(SignUp.this)
                                        .setTitle("Mobile number already exist in our record, please try another mobile number.")
                                        .setMessage("")
                                        .sneakError();
                            }
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());

                        } else {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                Intent i = new Intent(mActivity, Verification.class);
                i.putExtra("moveToOtp", "signUp");
                startActivity(i);
                Toast.makeText(SignUp.this, message + "", Toast.LENGTH_LONG).show();
                //  AppUtils.showToastSort(mActivity,""+ message);

            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                //AppUtils.showToastSort(mActivity, "" + error);
                Toast.makeText(SignUp.this, error + "", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

}
