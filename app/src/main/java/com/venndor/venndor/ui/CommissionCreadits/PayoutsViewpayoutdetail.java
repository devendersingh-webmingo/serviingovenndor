package com.venndor.venndor.ui.CommissionCreadits;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PayoutsViewpayoutdetail extends BaseFragment implements View.OnClickListener {

    View rootView;
    Preferences pref;
    ArrayList<HashMap<String, String>> commissionList = new ArrayList<>();
    ArrayList<HashMap<String, String>> packageList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_payout_detail, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
    }

    @Override
    public void onClick(View v) {
    }

    public void Getvendorviewpayoutpaymentinfo() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Getvendorviewpayoutpaymentinfo;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseGetvendorviewpayoutpaymentinfoJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }
    private void parseGetvendorviewpayoutpaymentinfoJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject dataJsonObject = response.getJSONObject("data");
            String check_type = dataJsonObject.getString("check_type");
            String mode_of_payment = dataJsonObject.getString("mode_of_payment");
            String payment_info = dataJsonObject.getString("payment_info");
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void Getvendorpayoutbookings() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Getvendorpayoutbookings;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        Getvendorpayoutbookings(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }
    private void Getvendorpayoutbookings(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject dataJsonObject = response.getJSONObject("data");
            JSONArray bookings = dataJsonObject.getJSONArray("bookings");
            for (int i = 0; i < bookings.length(); i++) {
                JSONObject payoutsObj = bookings.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("user_id", payoutsObj.get("user_id").toString());
                map.put("vendor_id", payoutsObj.get("vendor_id").toString());
                map.put("package_id", payoutsObj.get("package_id").toString());
                map.put("order_number", payoutsObj.get("order_number").toString());
                map.put("cart_details", payoutsObj.get("cart_details").toString());
                map.put("total_amount", payoutsObj.get("total_amount").toString());
                map.put("vendor_service_amount", payoutsObj.get("vendor_service_amount").toString());
                map.put("amount_without_tax", payoutsObj.get("amount_without_tax").toString());
                map.put("order_status", payoutsObj.get("order_status").toString());
                map.put("name", payoutsObj.get("name").toString());
                map.put("email", payoutsObj.get("email").toString());
                map.put("mobile_number", payoutsObj.get("name").toString());
                map.put("payment_method", payoutsObj.get("payment_method").toString());
                map.put("assigned_status", payoutsObj.get("assigned_status").toString());
                map.put("v_buy_lead_amount", payoutsObj.get("v_buy_lead_amount").toString());
                map.put("buy_lead_percent", payoutsObj.get("buy_lead_percent").toString());
                map.put("buy_lead_amount", payoutsObj.get("buy_lead_amount").toString());
                map.put("action_check", payoutsObj.get("action_check").toString());
                packageList.add(map);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void Getgetvendorpayoutinvoice() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Getvendorpayoutinvoice+"1";
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseGetvendorpayoutinvoiceresponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }
    private void parseGetvendorpayoutinvoiceresponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject dataJsonObject = response.getJSONObject("data");
            String payout_range = dataJsonObject.getString("payout_range");
            String total_order_amount = dataJsonObject.getString("total_order_amount");
            String comission_amount = dataJsonObject.getString("comission_amount");
            String loan_adjustment = dataJsonObject.getString("loan_adjustment");
            String release_amount = dataJsonObject.getString("release_amount");
            JSONObject vendor_info = dataJsonObject.getJSONObject("vendor_info");
            String id = vendor_info.getString("id");
            String unique_id = vendor_info.getString("unique_id");
            JSONArray payouts = dataJsonObject.getJSONArray("comissions");
            for (int i = 0; i < payouts.length(); i++) {
                JSONObject payoutsObj = payouts.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", payoutsObj.get("id").toString());
                map.put("vendor_id", payoutsObj.get("vendor_id").toString());
                map.put("order_id", payoutsObj.get("order_id").toString());
                map.put("name", payoutsObj.get("name").toString());
                map.put("mobile_number", payoutsObj.get("mobile_number").toString());
                map.put("total_amount", payoutsObj.get("total_amount").toString());
                map.put("amount_without_gst", payoutsObj.get("amount_without_gst").toString());
                map.put("gst", payoutsObj.get("gst").toString());
                map.put("vendor_comession", payoutsObj.get("vendor_comession").toString());
                map.put("comission_percent", payoutsObj.get("comission_percent").toString());
                map.put("added_in_wallet", payoutsObj.get("added_in_wallet").toString());
                map.put("payout", payoutsObj.get("payout").toString());
                map.put("created_at", payoutsObj.get("created_at").toString());
                map.put("updated_at", payoutsObj.get("updated_at").toString());
                map.put("unique_id", payoutsObj.get("unique_id").toString());
                map.put("order_number", payoutsObj.get("order_number").toString());
                commissionList.add(map);
            }


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }
}
