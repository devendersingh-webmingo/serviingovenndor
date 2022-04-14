package com.venndor.venndor.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chaos.view.PinView;
import com.irozon.sneaker.Sneaker;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Presenter.DoOTPVerifyPresenter;
import com.venndor.venndor.ui.Presenter.Repo.ForgetOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Repo.LoginWithOTPReop;
import com.venndor.venndor.ui.Presenter.Repo.NewUserOTPVerifyReop;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

public class Verification extends BaseActivity implements DoOTPVerifyPresenter.DoOTPVerifyView {
    TextView tvCount, tvResend, tvnewlogin;
    EditText etOtp1, etOtp2, etOtp3, etOtp4;
    Intent i = getIntent();
    private EditText[] editTexts;
    Preferences pref;
    Intent intent;
    String value, mobile;
    PinView pinview;
    String otp;
    DoOTPVerifyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        init();
    }

    private void init() {

        pref = new Preferences(mActivity);
        TextView tvHeading = findViewById(R.id.tvHeading);
        pinview = findViewById(R.id.pinview);
        tvCount = findViewById(R.id.tvCount);
        tvResend = findViewById(R.id.tvResend);
        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        tvnewlogin = findViewById(R.id.tvnewlogin);
        etOtp4 = findViewById(R.id.etOtp4);
        editTexts = new EditText[]{etOtp1, etOtp2, etOtp3, etOtp4};

        etOtp1.addTextChangedListener(new PinTextWatcher(0));
        etOtp2.addTextChangedListener(new PinTextWatcher(1));
        etOtp3.addTextChangedListener(new PinTextWatcher(2));
        etOtp4.addTextChangedListener(new PinTextWatcher(3));

        etOtp1.setOnKeyListener(new PinOnKeyListener(0));
        etOtp2.setOnKeyListener(new PinOnKeyListener(1));
        etOtp3.setOnKeyListener(new PinOnKeyListener(2));
        etOtp4.setOnKeyListener(new PinOnKeyListener(3));
        presenter = new DoOTPVerifyPresenter(this);

        intent = getIntent();
        if (intent != null) {
            value = intent.getStringExtra("moveToOtp");
            mobile = intent.getStringExtra("mobile");

        } else {
            Toast.makeText(Verification.this, "Nulll", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.tvSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginWithMobile.class));
            }
        });
        tvnewlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, loginWithEmail.class));
            }
        });
        tvHeading.setText("We have sent a verification code to your registered mobile number.");
        findViewById(R.id.llSubmitNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = pinview.getText().toString();
                if (otp.length() < 4) {
                    Toast.makeText(Verification.this, "The otp field is required.", Toast.LENGTH_SHORT).show();
                    Sneaker.with(Verification.this)
                            .setTitle("The otp field is required.")
                            .setMessage("")
                            .sneakWarning();
                } else {

                    if (value.equalsIgnoreCase("login")) {
                        // PostloginApi(otp);
                        presenter.DoOTPVerify(Verification.this, otp);
                    } else if (value.equalsIgnoreCase("forget")) {
                        // PostGlobalApi(otp);

                        presenter.ForgetOTPVerify(Verification.this, otp);
                    } else {
                        // PostOTPRegistrationApi(otp);
                        presenter.PostOTPRegistrationApi(Verification.this, otp);
                    }





                /*    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {

                        AppUtils.showToastSort(mActivity, otp);


                            if (value.equalsIgnoreCase("login")) {
                               // PostloginApi(otp);
                                presenter.DoOTPVerify(Verification.this,otp);
                            } else if(value.equalsIgnoreCase("forget")) {
                                PostGlobalApi(otp);
                            }
                            else {
                                PostOTPRegistrationApi(otp);
                            }

                    } else {
                        AppUtils.showErrorMessage(etOtp1, getString(R.string.errorInternet), mActivity);
                    }*/

                }



/*
                if (etOtp1.getText().toString().isEmpty()) {
                    AppUtils.showToastSort(mActivity, "please enter here");
                } else if (etOtp2.getText().toString().isEmpty()) {
                    AppUtils.showToastSort(mActivity, "please enter  here");
                } else if (etOtp3.getText().toString().isEmpty()) {
                    AppUtils.showToastSort(mActivity, "please enter  here");
                } else if (etOtp4.getText().toString().isEmpty()) {
                    AppUtils.showToastSort(mActivity, "please enter  here");
                } else {
                    String data1 = etOtp1.getText().toString().trim();
                    String data2 = etOtp2.getText().toString().trim();
                    String data3 = etOtp3.getText().toString().trim();
                    String data4 = etOtp4.getText().toString().trim();
                    String otpdata = data1 + data2 + data3 + data4;
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        if (getIntent().hasExtra("moveToOtp")) {
                            String value = getIntent().getStringExtra("moveToOtp");
                            Log.v("MyValueCheck","value "+value );
                            if (value.equalsIgnoreCase("login")) {
                                PostloginApi(otpdata);
                            } else if(value.equalsIgnoreCase("forget")) {
                                PostGlobalApi(otpdata);
                            }
                            else {
                                PostOTPRegistrationApi(otpdata);
                            }
                        }
                    } else {
                        AppUtils.showErrorMessage(etOtp1, getString(R.string.errorInternet), mActivity);
                    }
                }*/
            }
        });
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
               /// tvCount.setText("Resend OTP!");

                tvCount.setVisibility(View.GONE);
                tvResend.setVisibility(View.VISIBLE);

            }

        }.start();

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    PostloginApii();

            }
        });
    }


    private void PostloginApii() {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.otpLogin;
        AndroidNetworking.post(url)
                .addBodyParameter("mobile_number", mobile)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseJsonResponsee(response);
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
                            Toast.makeText(Verification.this, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();



                        }
                    }
                });
    }

    private void parseJsonResponsee(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                pref.set("counter", "false").commit();
               /* Intent i = new Intent(mActivity, Verification.class);
                i.putExtra("moveToOtp", "login");
                i.putExtra("mobile", etmobilenumber.getText().toString().trim());
                startActivity(i);*/

                Toast.makeText(Verification.this, message+"", Toast.LENGTH_SHORT).show();

                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvResend.setVisibility(View.GONE);
                        tvCount.setVisibility(View.VISIBLE);

                        tvCount.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {

                        tvCount.setVisibility(View.GONE);
                        tvResend.setVisibility(View.VISIBLE);


                    }

                }.start();

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

    private void PostloginApi(String otpdata) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.otploginbyOTPuser;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otpdata)
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
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                Toast.makeText(this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONObject data = response.getJSONObject("data");
                String access_token = data.getString("access_token");
                pref.set("token", access_token).commit();
                Log.v("jh", access_token);
                startActivity(new Intent(mActivity, Container.class));
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                //AppUtils.showToastSort(mActivity, "" + error);
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }


    private void PostOTPRegistrationApi(String otpdata) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.otpNewuser;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otpdata)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseregistrationJsonResponse(response);
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

    private void parseregistrationJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                Toast.makeText(this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONObject data = response.getJSONObject("data");
             /*   String access_token = data.getString("access_token");
                pref.set("token", access_token).commit();*//*
                Log.v("jh",access_token);*/
                startActivity(new Intent(mActivity, personalInfo.class));
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    @Override
    public void onOTPVerifyError(String message) {

        Toast.makeText(Verification.this, message + "", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onDoOTPVerifySuccess(LoginWithOTPReop response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            if (response.getStatus() == true) {

                pref.set("token", response.getData().getAccessToken()).commit();
                startActivity(new Intent(mActivity, Container.class));

            } else {

                // AppUtils.showToastSort(mActivity, );
                Toast.makeText(this, "OTP may be expired or not exist.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void ForgetOTPVerifySuccess(ForgetOTPVerifyReop response, String message) {
        if (message.equalsIgnoreCase("ok")) {
            if (response.getStatus() == true) {

                String user_id = String.valueOf(response.getData().getVerifyContent().getUserId());
                String otp = response.getData().getVerifyContent().getOtp();
                pref.set("user_id", user_id).commit();
                pref.set("otp", otp).commit();
               /* JSONObject data = response.getJSONObject("data");
                String access_token = data.getString("access_token");
                pref.set("token", access_token).commit();
                Log.v("jh",access_token);*/
                startActivity(new Intent(mActivity, ForgetPassword.class));

                Toast.makeText(Verification.this, response.getMessage() + "", Toast.LENGTH_SHORT).show();

            } else {

                // AppUtils.showToastSort(mActivity, "OTP may be expired or not exist.");
                Toast.makeText(this, "OTP may be expired or not exist.", Toast.LENGTH_SHORT).show();


            }


        }
    }

    @Override
    public void PostOTPRegistrationSuccess(NewUserOTPVerifyReop response, String message) {


        if (message.equalsIgnoreCase("ok")) {
            if (response.getStatus() == true) {

                startActivity(new Intent(mActivity, personalInfo.class));

                Toast.makeText(Verification.this, response.getMessage() + "", Toast.LENGTH_SHORT).show();


            } else {

                // AppUtils.showToastSort(mActivity, "OTP may be expired or not exist.");
                Toast.makeText(this, "OTP may be expired or not exist.", Toast.LENGTH_SHORT).show();


            }


        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            AppUtils.showRequestDialog(mActivity);

        } else {
            AppUtils.hideDialog();

        }
    }


    @Override
    public void onOTPVerifyFailure(Throwable t) {
        //  Toast.makeText(Verification.this, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "OTP may be expired or not exist.", Toast.LENGTH_SHORT).show();
        Sneaker.with(this)
                .setTitle("OTP may be expired or not exist.")
                .setMessage("")
                .sneakError();
    }


    @Override
    public void onLoginOTPVerifyFailure(Throwable t) {

        //   Toast.makeText(this, "Your Account Is Blocked, Please Contact To Admin.", Toast.LENGTH_LONG).show();
      /*  Sneaker.with(this)
                .setTitle("Your Account Is Blocked, Please Contact To Admin.")
                .setMessage("")
                .sneakError();*/

        AlertDialogBox("Your Account Is Blocked, Please Contact To Admin.");
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


    public class PinTextWatcher implements TextWatcher {

        private final int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;
            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();

            Toast.makeText(mActivity, "" + editTexts[currentIndex].getText().toString().trim(), Toast.LENGTH_SHORT).show();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            Toast.makeText(Verification.this, "" + editTexts, Toast.LENGTH_SHORT).show();
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)

                    return false;

            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private final int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();

            }
            return false;
        }

    }

    private void PostGlobalApi(String otpdata) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorverifyotp;
        AndroidNetworking.post(url)
                .addBodyParameter("otp", otpdata)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseGlobalJsonResponse(response);
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

    private void parseGlobalJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject dataJsonObject = response.getJSONObject("data");
                JSONObject userJsonObject = dataJsonObject.getJSONObject("verify_content");//addons
                String user_id = userJsonObject.getString("user_id");
                String otp = userJsonObject.getString("otp");
                pref.set("user_id", user_id).commit();
                pref.set("otp", otp).commit();
                //  AppUtils.showToastSort(mActivity, "" + message);
                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

               /* JSONObject data = response.getJSONObject("data");
                String access_token = data.getString("access_token");
                pref.set("token", access_token).commit();
                Log.v("jh",access_token);*/
                startActivity(new Intent(mActivity, ForgetPassword.class));
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                AppUtils.showToastSort(mActivity, "" + error);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

}
