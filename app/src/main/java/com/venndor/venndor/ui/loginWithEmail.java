package com.venndor.venndor.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

public class loginWithEmail extends BaseActivity {
    EditText etEmailID, etPassword;
    Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_with_email);
        init();
    }

    private void init() {
        pref = new Preferences(mActivity);
        findViewById(R.id.llEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginUsingOTP.class));
            }
        });
        findViewById(R.id.tvForget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, GlobalScreen.class);
                startActivity(i);
            }
        });
        etEmailID = findViewById(R.id.etEmailID);
        etPassword = findViewById(R.id.etPassword);
        findViewById(R.id.llSendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etEmailID.getText().toString().trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmailID.getText().toString().trim()).matches()) {
                    Sneaker.with(mActivity)
                            .setTitle("please enter valid Email Id")
                            .setMessage("")
                            .sneakWarning();
                } else if (etPassword.getText().toString().isEmpty()) {
                    // AppUtils.showErrorMessage(etPassword, "please enter  Password", mActivity);
                    Sneaker.with(mActivity)
                            .setTitle("please enter  Password")
                            .setMessage("")
                            .sneakWarning();

                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        // AppTools.showGifDialog(mActivity, R.mipmap.creative);
                        PostloginApi(etEmailID.getText().toString().trim(), etPassword.getText().toString().trim());
                    } else {
                        // AppUtils.showErrorMessage(etPassword,getString(R.string.errorInternet), mActivity);
                        Toast.makeText(loginWithEmail.this, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        findViewById(R.id.tvOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, loginWithMobile.class);
                i.putExtra("moveToOtp", "login");
                startActivity(i);
            }
        });
        findViewById(R.id.ivtopback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.tvsignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, SignUp.class));
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

    private void PostloginApi(String email, String pass) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.login;

        AndroidNetworking.post(url)
                .addBodyParameter("global", email)
                .addBodyParameter("password", pass)
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

                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {

                            // AppUtils.showErrorMessage(etEmailID,  String.valueOf(error.getErrorDetail()), mActivity);
                            Toast.makeText(loginWithEmail.this, String.valueOf(error.getErrorDetail()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                JSONObject data = response.getJSONObject("data");
                String access_token = data.getString("access_token");
                pref.set("token", access_token).commit();
                Log.v("jh", access_token);
                startActivity(new Intent(mActivity, Container.class));
                //  AppUtils.showToastSort(mActivity, "" + message);
                Toast.makeText(loginWithEmail.this, message + "", Toast.LENGTH_LONG).show();

            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                //Toast.makeText(loginWithEmail.this, error + "", Toast.LENGTH_LONG).show();
                AlertDialogBox(error);
                // AppUtils.showToastSort(mActivity, "" + error);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }


    public void AlertDialogBox(String msg) {

        //Logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("");

        // set dialog message
        alertDialogBuilder.setIcon(R.mipmap.logo);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                })
                /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                })*/;

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
