package com.venndor.venndor.ui.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.loginUsingOTP;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class VerificationOTP extends BaseActivity {
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        init();
    }
    private void init() {
        pref=new Preferences(mActivity);
        TextView tvPrivacyPolicy=findViewById(R.id.tvPrivacyPolicy);
        tvPrivacyPolicy.setVisibility(View.GONE);
        TextView tvHeading=findViewById(R.id.tvHeading);
        tvHeading.setText("Please enter the verificaton code sent to Customer Registered Mobile Number to start the services.");
        getOTP();
    }
    public void getOTP() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.GetvendorstartserviceSendOTP+"123";
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseGetvendorstartserviceSendOTPJsonResponse(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }
    private void parseGetvendorstartserviceSendOTPJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();

            JSONObject orderJsonObject = response.getJSONObject("data");
            String id = orderJsonObject.getString("id");
            String user_id = orderJsonObject.getString("user_id");
            String vendor_id = orderJsonObject.getString("vendor_id");
            String package_id = orderJsonObject.getString("package_id");
            String addons = orderJsonObject.getString("addons");
            String pincode = orderJsonObject.getString("pincode");
            String order_number = orderJsonObject.getString("order_number");
            String cart_details = orderJsonObject.getString("cart_details");
            String sub_amount = orderJsonObject.getString("sub_amount");
            String discount_amount = orderJsonObject.getString("discount_amount");
            String membership_discount = orderJsonObject.getString("membership_discount");
            String total_amount = orderJsonObject.getString("total_amount");
            String vendor_service_amount = orderJsonObject.getString("vendor_service_amount");
            String amount_without_tax = orderJsonObject.getString("amount_without_tax");
            String tax_name = orderJsonObject.getString("tax_name");
            String hygiene_fees = orderJsonObject.getString("hygiene_fees");
            String order_status = orderJsonObject.getString("order_status");
            String vendor_status = orderJsonObject.getString("vendor_status");
            String name = orderJsonObject.getString("name");
            String email = orderJsonObject.getString("email");
            String mobile_number = orderJsonObject.getString("mobile_number");
            String address = orderJsonObject.getString("address");
            String landmark = orderJsonObject.getString("landmark");
            String state_id = orderJsonObject.getString("state_id");
            String city = orderJsonObject.getString("city");
            String cancellation_reasons = orderJsonObject.getString("cancellation_reasons");
            String support_question_id = orderJsonObject.getString("support_question_id");
            String cancellation_remark = orderJsonObject.getString("cancellation_remark");
            String cancelled_by = orderJsonObject.getString("cancelled_by");
            String payment_status = orderJsonObject.getString("payment_status");
            String assigned_status = orderJsonObject.getString("assigned_status");
            String remark = orderJsonObject.getString("remark");
            String membership_id = orderJsonObject.getString("membership_id");
            String membership_cost = orderJsonObject.getString("membership_cost");
            String cgst = orderJsonObject.getString("cgst");
            String sgst = orderJsonObject.getString("sgst");
            String igst = orderJsonObject.getString("igst");
            String device_type = orderJsonObject.getString("device_type");
            String system_ip = orderJsonObject.getString("system_ip");
            String serve_date = orderJsonObject.getString("serve_date");
            String serve_time = orderJsonObject.getString("serve_time");
            String accept_by_vendor = orderJsonObject.getString("accept_by_vendor");
            String reject_reason_by_vendor = orderJsonObject.getString("reject_reason_by_vendor");
            String service_start = orderJsonObject.getString("service_start");
            String rejection_accept = orderJsonObject.getString("rejection_accept");
            String tax_percent = orderJsonObject.getString("tax_percent");
            String v_buy_lead_amount = orderJsonObject.getString("v_buy_lead_amount");
            String buy_lead_percent = orderJsonObject.getString("buy_lead_percent");
            String order_device_type = orderJsonObject.getString("order_device_type");
            String vendor_attachment = orderJsonObject.getString("vendor_attachment");
            String created_at = orderJsonObject.getString("created_at");
            String updated_at = orderJsonObject.getString("updated_at");
            String buy_lead_amount = orderJsonObject.getString("buy_lead_amount");
            String action_check = orderJsonObject.getString("action_check");

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }
}
