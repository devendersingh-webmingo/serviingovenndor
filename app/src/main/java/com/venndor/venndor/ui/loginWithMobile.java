package com.venndor.venndor.ui;

import android.Manifest;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.ui.LoginSignUp.splash;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

import java.util.List;
import java.util.TimerTask;

public class loginWithMobile extends BaseActivity {
    EditText etmobilenumber;
    Preferences pref;
    Intent i = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /*forget*/
    private void init() {
        pref = new Preferences(mActivity);
        etmobilenumber = findViewById(R.id.etMobileNumber);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


        findViewById(R.id.llSendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etmobilenumber.getText().toString().length()!=10) {
                    // AppUtils.showErrorMessage(etmobilenumber, "please enter  mobile number", mActivity);

                    Sneaker.with(loginWithMobile.this)
                            .setTitle("please enter valid mobile number.")
                            .setMessage("")
                            .sneakWarning();
                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        PostloginApi();
                        /*if (getIntent().hasExtra("forget")) {
                            String value = getIntent().getStringExtra("forget");
                            Log.v("MyValueCheck", "value " + value);
                            if (value.equalsIgnoreCase("forget")) {
                                PostloginApi();
                            } else {
                                PostForgetOTPApi();
                            }

                        }*/

                    } else {
                        // AppUtils.showErrorMessage(etmobilenumber, , mActivity);
                        Toast.makeText(loginWithMobile.this, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        findViewById(R.id.tvForget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, GlobalScreen.class);

                startActivity(i);
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

    private void PostloginApi() {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.otpLogin;
        AndroidNetworking.post(url)
                .addBodyParameter("mobile_number", etmobilenumber.getText().toString().trim())
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

                            // AppUtils.showErrorMessage(etmobilenumber, String.valueOf(error.getErrorBody()), mActivity);

                        } else {
                            // AppUtils.showErrorMessage(etmobilenumber, String.valueOf(error.getErrorDetail()), mActivity);
                            Toast.makeText(loginWithMobile.this, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();



                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                pref.set("counter", "false").commit();
                Intent i = new Intent(mActivity, Verification.class);
                i.putExtra("moveToOtp", "login");
                i.putExtra("mobile", etmobilenumber.getText().toString().trim());
                startActivity(i);
                Toast.makeText(loginWithMobile.this, message+"", Toast.LENGTH_SHORT).show();
                /// AppUtils.showToastSort(mActivity, "" + message);

            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                AlertDialogBox(error);

                //AppUtils.showToastSort(mActivity, "" + error);
                //  Toast.makeText(loginWithMobile.this, error+"", Toast.LENGTH_LONG).show();

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
