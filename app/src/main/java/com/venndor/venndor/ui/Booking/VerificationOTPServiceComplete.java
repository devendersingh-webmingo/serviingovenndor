package com.venndor.venndor.ui.Booking;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Verification;
import com.venndor.venndor.ui.loginWithMobile;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class VerificationOTPServiceComplete extends BaseActivity {
    TextView textView;
    TextView tvCount;
    EditText  etOtp1,etOtp2,etOtp3,etOtp4;
    private EditText[] editTexts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        init();
    }
    private void init() {
        tvCount=findViewById(R.id.tvCount);
        etOtp1=findViewById(R.id.etOtp1);
        etOtp2=findViewById(R.id.etOtp2);
        etOtp3=findViewById(R.id.etOtp3);
        etOtp4=findViewById(R.id.etOtp4);
        editTexts = new EditText[]{etOtp1, etOtp2, etOtp3, etOtp4};
        etOtp1.addTextChangedListener(new PinTextWatcher(0));
        etOtp2.addTextChangedListener(new PinTextWatcher(1));
        etOtp3.addTextChangedListener(new PinTextWatcher(2));
        etOtp4.addTextChangedListener(new PinTextWatcher(3));

        etOtp1.setOnKeyListener(new PinOnKeyListener(0));
        etOtp2.setOnKeyListener(new PinOnKeyListener(1));
        etOtp3.setOnKeyListener(new PinOnKeyListener(2));
        etOtp4.setOnKeyListener(new PinOnKeyListener(3));

        findViewById(R.id.llSubmitNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etOtp1.getText().toString().isEmpty()) {
                    Toast.makeText(mActivity, "please enter here", Toast.LENGTH_SHORT).show();

                    //AppUtils.showToastSort(mActivity, "please enter here");
                } else if (etOtp2.getText().toString().isEmpty()) {
                  //  AppUtils.showToastSort(mActivity, "please enter  here");
                    Toast.makeText(mActivity, "please enter here", Toast.LENGTH_SHORT).show();

                } else if (etOtp3.getText().toString().isEmpty()) {
                   // AppUtils.showToastSort(mActivity, "please enter  here");
                    Toast.makeText(mActivity, "please enter here", Toast.LENGTH_SHORT).show();

                } else if (etOtp4.getText().toString().isEmpty()) {
                    //AppUtils.showToastSort(mActivity, "please enter  here");
                    Toast.makeText(mActivity, "please enter here", Toast.LENGTH_SHORT).show();

                } else {
                    String data1=etOtp1.getText().toString().trim();
                    String data2=etOtp2.getText().toString().trim();
                    String data3=etOtp3.getText().toString().trim();
                    String data4=etOtp4.getText().toString().trim();
                    String otpdata=data1+data2+data3+data4;
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        Postvendorverifyotpstartservice(otpdata);
                    } else {
                       // AppUtils.showErrorMessage(etOtp1, getString(R.string.errorInternet), mActivity);
                        Toast.makeText(mActivity,  getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

        TextView tvPrivacyPolicy=findViewById(R.id.tvPrivacyPolicy);
        tvPrivacyPolicy.setVisibility(View.GONE);
        TextView tvHeading=findViewById(R.id.tvHeading);
        tvHeading.setText("Please enter the verificaton code sent to Customer Registered Mobile Number to mark the service as complete.");
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCount.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvCount.setText("Resend OTP!");

            }

        }.start();
        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tvCount.setText("seconds remaining: " + millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        tvCount.setText("Resend OTP");
                    }

                }.start();
            }
        });
    }

    public class PinTextWatcher implements TextWatcher {
        private int currentIndex;
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

            Toast.makeText(mActivity, ""+ editTexts[currentIndex].getText().toString().trim(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mActivity, ""+editTexts, Toast.LENGTH_SHORT).show();
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

        private int currentIndex;

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

    public void Postvendorverifyotpstartservice(String otpdata) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorverifyotpstartservice;
        Log.v("data_url", url);
        AndroidNetworking.post(url)
                .addBodyParameter("otp",otpdata)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsevendorverifyotpstartserviceJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }
    private void parsevendorverifyotpstartserviceJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            String message = response.getString("message");
            /*AppUtils.showToastSort(mActivity, "" + message);*/
            Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mActivity, Container.class));
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }
}
