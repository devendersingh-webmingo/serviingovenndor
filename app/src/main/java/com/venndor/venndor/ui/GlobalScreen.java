package com.venndor.venndor.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

public class GlobalScreen extends BaseActivity {
    EditText etEmailMobileNumber;
    Preferences pref;
    Intent i=getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);
        init();
    }
    /*forget*/
    private void init() {
        pref = new Preferences(mActivity);

        etEmailMobileNumber = findViewById(R.id.etEmailMobileNumber);
        findViewById(R.id.llForgetSendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEmailMobileNumber.getText().toString().isEmpty()) {
                   // AppUtils.showErrorMessage(etEmailMobileNumber, "please enter  mobile number or Email", mActivity);
                    Toast.makeText(GlobalScreen.this, "please enter  mobile number or Email", Toast.LENGTH_SHORT).show();
                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        PostForgetOTPApi();

                        /*if (getIntent().hasExtra("forget")) {
                            String value = getIntent().getStringExtra("forget");
                            Log.v("MyValueCheck", "value " + value);
                            if (value.equalsIgnoreCase("forget")) {
                                PostloginApi();
                            } else {

                            }

                        }*/

                    } else {
                      //  AppUtils.showErrorMessage(etEmailMobileNumber, , mActivity);
                        Toast.makeText(GlobalScreen.this, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        findViewById(R.id.tvForget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(mActivity, loginWithMobile.class);
                i.putExtra("forget","forget");
                startActivity(i);*/
            }
        });
        findViewById(R.id.tvLoginWithPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginWithEmail.class));
            }
        });
        findViewById(R.id.tvSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SignUp.class));
            }
        });
        findViewById(R.id.ivtopback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.llEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginWithEmail.class));
            }
        });

        setupLinkButton();
        setupjLinkButton();
    }

    public void setupLinkButton() {

        findViewById(R.id.tvPrivacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.serviingo.com/home/privacy"));
                startActivity(intent);

            }
        });
    }

    public void setupjLinkButton() {

        findViewById(R.id.tvterms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.serviingo.com/home/terms"));
                startActivity(intent);

            }
        });
    }




    private void PostForgetOTPApi() {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostForgetAPI;
        AndroidNetworking.post(url)
                .addBodyParameter("global", etEmailMobileNumber.getText().toString().trim())
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

                           // AppUtils.showErrorMessage(etEmailMobileNumber, , mActivity);
                            Toast.makeText(GlobalScreen.this, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseForgetJsonResponse(JSONObject response) {
        try {
            if(response.getString("status").equalsIgnoreCase("true")){
                String message = response.getString("message");
                //pref.set("counter", "false").commit();
                Intent i = new Intent(mActivity, Verification.class);
                i.putExtra("moveToOtp", "forget");
                startActivity(i);
                //  AppUtils.showToastSort(mActivity, "" + message);
                Toast.makeText(GlobalScreen.this, message+"", Toast.LENGTH_SHORT).show();

            }else{
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
              //  AppUtils.showToastSort(mActivity, "" + error);
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();

                Toast.makeText(mActivity, ""+error, Toast.LENGTH_SHORT).show();

            }
               /* JSONObject messages = response.getJSONObject("message");
                AppUtils.showToastSort(mActivity, "" + messages.getString("error"));*/

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }
}
