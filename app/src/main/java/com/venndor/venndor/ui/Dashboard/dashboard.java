package com.venndor.venndor.ui.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;

import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.navuser;
import static com.venndor.venndor.ui.Container.navusername;
import static com.venndor.venndor.ui.Container.tvHeading;

public class dashboard extends BaseFragment implements View.OnClickListener {

    LinearLayoutManager layoutrecytranscationManager, layoutranscationManager, layourecyRecytodayManager, layoutRecentPayout, layoutPendingPayout, LayoutwalletSummary;
    View rootView;
    Preferences pref;
    RecyclerView recyclerCategories, recyRecyLeads, recyRecytoday, rvRecentPayout, rvPendingPayout, rvwalletSummary;
    TextView tvLeads, tvnodata, recentPayout, tvPendingPayout, tvUserName, uniqueId, tvAccount, tvsubtitle, allBooking, tvRecommendedLeads;
    ImageView ivCustomer;

    TodayAdapter todayAdapter;
    RecentAdapter recentAdapter;
    View bookingdetailsview;
    AlertDialog alertDialog, alertDialogBooking;

    String key, Orderid;
    ArrayList<HashMap<String, String>> bookinglist = new ArrayList<>();
    ArrayList<HashMap<String, String>> recomendedlist = new ArrayList<>();
    ArrayList<HashMap<String, String>> today_activitieslist = new ArrayList<>();
    ArrayList<HashMap<String, String>> recentList = new ArrayList<>();
    ArrayList<HashMap<String, String>> pendingList = new ArrayList<>();
    ArrayList<HashMap<String, String>> walletList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_dashboard, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvHeading.setText("DashBoard");

        ivBack.setVisibility(View.GONE);
        recyclerCategories = rootView.findViewById(R.id.recyclerCategories);
        recyRecyLeads = rootView.findViewById(R.id.recyRecyLeads);
        recyRecytoday = rootView.findViewById(R.id.recyRecytoday);
        tvnodata = rootView.findViewById(R.id.tvnodata);
        tvnodata.setVisibility(View.GONE);
        rvRecentPayout = rootView.findViewById(R.id.rvRecentPayout);
        tvPendingPayout = rootView.findViewById(R.id.tvPendingPayout);
        rvPendingPayout = rootView.findViewById(R.id.rvPendingPayout);
        rvwalletSummary = rootView.findViewById(R.id.rvwalletSummary);
        tvLeads = rootView.findViewById(R.id.tvLeads);
        recentPayout = rootView.findViewById(R.id.recentPayout);
        tvUserName = rootView.findViewById(R.id.tvUserName);
        allBooking = rootView.findViewById(R.id.allBooking);
        tvRecommendedLeads = rootView.findViewById(R.id.tvRecommendedLeads);
      /*  navuser = rootView.findViewById(R.id.navuser);
          navusername = rootView.findViewById(R.id.navusername);*/
        // lltodaypayout = rootView.findViewById(R.id.lltodaypayout);
        tvAccount = rootView.findViewById(R.id.tvAccount);
        tvsubtitle = rootView.findViewById(R.id.tvsubtitle);
        uniqueId = rootView.findViewById(R.id.uniqueId);
        ivCustomer = rootView.findViewById(R.id.ivCustomer);
        layoutrecytranscationManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        layoutranscationManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        layourecyRecytodayManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        layoutRecentPayout = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        layoutPendingPayout = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        LayoutwalletSummary = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerCategories.setLayoutManager(layoutrecytranscationManager);
        recyRecyLeads.setLayoutManager(layoutranscationManager);
        recyRecytoday.setLayoutManager(layourecyRecytodayManager);
        rvRecentPayout.setLayoutManager(layoutRecentPayout);
        rvPendingPayout.setLayoutManager(layoutPendingPayout);
        rvwalletSummary.setLayoutManager(LayoutwalletSummary);

        rootView.findViewById(R.id.allBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHeading.setText("My Booking");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(5);
            }
        });
        tvLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHeading.setText("Recommended Leads");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(13);
            }
        });
        GetDashboardApi();
        getProfile();
    }

    public void GetDashboardApi() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getdashboard;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseDashboardJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseDashboardJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            bookinglist.clear();
            recomendedlist.clear();
            today_activitieslist.clear();
            recentList.clear();
            pendingList.clear();
            walletList.clear();
            Log.v("responseApiCheck", "empty " + response);
            String message = response.getString("message");
            // Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject jsonObjectt = response.getJSONObject("data");

            JSONObject userObject = jsonObjectt.getJSONObject("user");
            String id = userObject.getString("id");
            String first_name = userObject.getString("first_name");
            String last_name = userObject.getString("last_name");
            String unique_id = userObject.getString("unique_id");
            String profile_photo_path = userObject.getString("profile_photo_path");

            Log.d("profile_photo_path", profile_photo_path);

            pref.set("imageVendor", profile_photo_path).commit();
            tvUserName.setText(first_name);
            navusername.setText(first_name);
            uniqueId.setText(unique_id);

            // Picasso.get().load(modelArrayList.getData().getCategories().get(position).getImage()).into(holder.ivimageone);

            if (!profile_photo_path.equalsIgnoreCase("null")) {

                Picasso.get().load(AppUrls.profileImageUrl + profile_photo_path).into(ivCustomer);
                Picasso.get().load(AppUrls.profileImageUrl + profile_photo_path).into(navuser);

                Picasso.get().load(AppUrls.profileImageUrl + profile_photo_path).into(ivIamge);
            }
            JSONArray histories = jsonObjectt.getJSONArray("today_bookings");
            for (int i = 0; i < histories.length(); i++) {
                JSONObject loansObj = histories.getJSONObject(i);
                String assignStatus = loansObj.getString("assigned_status");
                String accept_by_vendor = loansObj.getString("accept_by_vendor");
                if (!assignStatus.equalsIgnoreCase("Admin") || accept_by_vendor.equalsIgnoreCase("Yes")) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("user_id", loansObj.get("user_id").toString());
                    map.put("cart_details", loansObj.get("cart_details").toString());
                    map.put("order_number", loansObj.get("order_number").toString());
                    map.put("total_amount", loansObj.get("total_amount").toString());
                    map.put("order_status", loansObj.get("order_status").toString());
                    map.put("name", loansObj.get("name").toString());
                    map.put("email", loansObj.get("email").toString());
                    map.put("mobile_number", loansObj.get("mobile_number").toString());
                    map.put("address", loansObj.get("address").toString());
                    map.put("landmark", loansObj.get("landmark").toString());
                    map.put("state_id", loansObj.get("state_id").toString());
                    map.put("city", loansObj.get("city").toString());
                    map.put("pincode", loansObj.get("pincode").toString());
                    map.put("payment_method", loansObj.get("payment_method").toString());
                    map.put("serve_date", loansObj.get("serve_date").toString());
                    map.put("serve_time", loansObj.get("serve_time").toString());
                    map.put("accept_by_vendor", loansObj.get("accept_by_vendor").toString());
                    map.put("assigned_status", loansObj.get("assigned_status").toString());
                    map.put("buy_lead_amount", loansObj.get("buy_lead_amount").toString());
                    map.put("action_check", loansObj.get("action_check").toString());
                    map.put("package", loansObj.get("package").toString());
                    map.put("new_lead_amount", loansObj.get("new_lead_amount").toString());
                    map.put("package_information", loansObj.get("package_information").toString());
                    bookinglist.add(map);
                }
            }
            TransAdapter transdapter = new TransAdapter(bookinglist);
            recyclerCategories.setAdapter(transdapter);

            if (histories.length() == 0) {
                tvsubtitle.setVisibility(View.GONE);
                allBooking.setVisibility(View.GONE);
            } else {
                tvsubtitle.setVisibility(View.VISIBLE);
                allBooking.setVisibility(View.VISIBLE);
            }
            JSONArray recommended_bookingsarray = jsonObjectt.getJSONArray("recommended_bookings");
            for (int j = 0; j < recommended_bookingsarray.length(); j++) {
                JSONObject loansObj = recommended_bookingsarray.getJSONObject(j);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("user_id", loansObj.get("user_id").toString());
                map.put("vendor_id", loansObj.get("vendor_id").toString());
                map.put("package_id", loansObj.get("package_id").toString());
                map.put("order_number", loansObj.get("order_number").toString());
                map.put("cart_details", loansObj.get("cart_details").toString());
                map.put("total_amount", loansObj.get("total_amount").toString());
                map.put("vendor_service_amount", loansObj.get("vendor_service_amount").toString());
                map.put("order_status", loansObj.get("order_status").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("email", loansObj.get("email").toString());
                map.put("mobile_number", loansObj.get("mobile_number").toString());
                map.put("address", loansObj.get("address").toString());
                map.put("landmark", loansObj.get("landmark").toString());
                map.put("state_id", loansObj.get("state_id").toString());
                map.put("pincode", loansObj.get("pincode").toString());
                map.put("payment_method", loansObj.get("payment_method").toString());
                map.put("serve_date", loansObj.get("serve_date").toString());
                map.put("serve_time", loansObj.get("serve_time").toString());
                map.put("v_buy_lead_amount", loansObj.get("v_buy_lead_amount").toString());
                map.put("buy_lead_percent", loansObj.get("buy_lead_percent").toString());
                map.put("buy_lead_amount", loansObj.get("buy_lead_amount").toString());
                map.put("action_check", loansObj.get("action_check").toString());
                map.put("buy_action_check", loansObj.get("buy_action_check").toString());
                map.put("package_information", loansObj.get("package_information").toString());
                map.put("serving_datetime", loansObj.get("serving_datetime").toString());
                map.put("new_lead_amount", loansObj.get("new_lead_amount").toString());
                recomendedlist.add(map);
            }
            TransactionAdapter transactiondapter = new TransactionAdapter(recomendedlist);
            recyRecyLeads.setAdapter(transactiondapter);

            if (recommended_bookingsarray.length() == 0) {
                tvRecommendedLeads.setVisibility(View.GONE);
                tvLeads.setVisibility(View.GONE);
            } else {
                tvRecommendedLeads.setVisibility(View.VISIBLE);
                tvLeads.setVisibility(View.VISIBLE);
            }
            JSONArray today_activitiesarray = jsonObjectt.getJSONArray("today_activities");
            for (int j = 0; j < today_activitiesarray.length(); j++) {
                JSONObject loansObj = today_activitiesarray.getJSONObject(j);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("time", loansObj.get("time").toString());
                map.put("message", loansObj.get("message").toString());
                today_activitieslist.add(map);
            }
            todayAdapter = new TodayAdapter(today_activitieslist);
            recyRecytoday.setAdapter(todayAdapter);
            if (today_activitiesarray.length() == 0) {
                tvAccount.setVisibility(View.GONE);
            } else {
                tvAccount.setVisibility(View.VISIBLE);
            }

            JSONArray recent_payoutsarray = jsonObjectt.getJSONArray("recent_payouts");
            for (int j = 0; j < recent_payoutsarray.length(); j++) {
                JSONObject loansObj = recent_payoutsarray.getJSONObject(j);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("unique_id", loansObj.get("unique_id").toString());
                map.put("week_range", loansObj.get("week_range").toString());
                map.put("vendor_id", loansObj.get("vendor_id").toString());
                map.put("order_ids", loansObj.get("order_ids").toString());
                map.put("commission_ids", loansObj.get("commission_ids").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("mobile_number", loansObj.get("mobile_number").toString());
                map.put("total_amount", loansObj.get("total_amount").toString());
                map.put("amount_without_gst", loansObj.get("amount_without_gst").toString());
                map.put("gst", loansObj.get("gst").toString());
                map.put("vendor_comession", loansObj.get("vendor_comession").toString());
                map.put("vendor_deducation_percent", loansObj.get("vendor_deducation_percent").toString());
                map.put("loan_adjustment", loansObj.get("loan_adjustment").toString());
                map.put("release_amount", loansObj.get("release_amount").toString());
                map.put("status", loansObj.get("status").toString());
                map.put("total_orders_count", loansObj.get("total_orders_count").toString());
                map.put("payment_mode", loansObj.get("payment_mode").toString());
                map.put("mode_of_payment", loansObj.get("mode_of_payment").toString());
                map.put("cash_collected_by", loansObj.get("cash_collected_by").toString());
                map.put("cheque_number", loansObj.get("cheque_number").toString());
                map.put("cheque_date", loansObj.get("cheque_date").toString());
                map.put("bank_name", loansObj.get("bank_name").toString());
                map.put("bank_branch", loansObj.get("bank_branch").toString());
                map.put("utr_number", loansObj.get("utr_number").toString());
                map.put("payment_screenshots", loansObj.get("payment_screenshots").toString());
                map.put("ref_id", loansObj.get("ref_id").toString());
                map.put("order_id", loansObj.get("order_id").toString());
                map.put("payment_remark", loansObj.get("payment_remark").toString());
                map.put("created_at", loansObj.get("created_at").toString());
                map.put("updated_at", loansObj.get("updated_at").toString());
                map.put("total_booking_count", loansObj.get("total_booking_count").toString());
                map.put("payout_range", loansObj.get("payout_range").toString());
                recentList.add(map);
            }
          /*  if (recent_payoutsarray.length() == 0) {
                recentPayout.setVisibility(View.GONE);
            } else {
                recentPayout.setVisibility(View.VISIBLE);
            }*/
            /*recentAdapter = new RecentAdapter(recentList);
            rvRecentPayout.setAdapter(recentAdapter);*/

            JSONArray pending_payoutsarray = jsonObjectt.getJSONArray("pending_payouts");
            for (int j = 0; j < pending_payoutsarray.length(); j++) {
                JSONObject loansObj = pending_payoutsarray.getJSONObject(j);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("unique_id", loansObj.get("unique_id").toString());
                map.put("week_range", loansObj.get("week_range").toString());
                map.put("vendor_id", loansObj.get("vendor_id").toString());
                map.put("order_ids", loansObj.get("order_ids").toString());
                map.put("commission_ids", loansObj.get("commission_ids").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("mobile_number", loansObj.get("mobile_number").toString());
                map.put("total_amount", loansObj.get("total_amount").toString());
                map.put("amount_without_gst", loansObj.get("amount_without_gst").toString());
                map.put("gst", loansObj.get("gst").toString());
                map.put("vendor_comession", loansObj.get("vendor_comession").toString());
                map.put("vendor_deducation_percent", loansObj.get("vendor_deducation_percent").toString());
                map.put("loan_adjustment", loansObj.get("loan_adjustment").toString());
                map.put("release_amount", loansObj.get("release_amount").toString());
                map.put("status", loansObj.get("status").toString());
                map.put("total_orders_count", loansObj.get("total_orders_count").toString());
                map.put("payment_mode", loansObj.get("payment_mode").toString());
                map.put("mode_of_payment", loansObj.get("mode_of_payment").toString());
                map.put("cash_collected_by", loansObj.get("cash_collected_by").toString());
                map.put("cheque_number", loansObj.get("cheque_number").toString());
                map.put("cheque_date", loansObj.get("cheque_date").toString());
                map.put("bank_name", loansObj.get("bank_name").toString());
                map.put("bank_branch", loansObj.get("bank_branch").toString());
                map.put("utr_number", loansObj.get("utr_number").toString());
                map.put("payment_screenshots", loansObj.get("payment_screenshots").toString());
                map.put("ref_id", loansObj.get("ref_id").toString());
                map.put("order_id", loansObj.get("order_id").toString());
                map.put("payment_remark", loansObj.get("payment_remark").toString());
                map.put("created_at", loansObj.get("created_at").toString());
                map.put("updated_at", loansObj.get("updated_at").toString());
                map.put("total_booking_count", loansObj.get("total_booking_count").toString());
                map.put("payout_range", loansObj.get("payout_range").toString());
                pendingList.add(map);
            }
           /* if (pending_payoutsarray.length() == 0) {
                tvPendingPayout.setVisibility(View.GONE);
            } else {
                tvPendingPayout.setVisibility(View.VISIBLE);
            }
            PendingPayoutAdapter pendingPayoutAdapter = new PendingPayoutAdapter(pendingList);
            rvPendingPayout.setAdapter(pendingPayoutAdapter);*/
            JSONArray walletarray = jsonObjectt.getJSONArray("wallet");
            for (int j = 0; j < walletarray.length(); j++) {
                JSONObject loansObj = walletarray.getJSONObject(j);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("referral_master_id", loansObj.get("referral_master_id").toString());
                map.put("user_id", loansObj.get("user_id").toString());
                map.put("booking_id", loansObj.get("booking_id").toString());
                map.put("transaction_id", loansObj.get("transaction_id").toString());
                map.put("amount", loansObj.get("amount").toString());
                map.put("avail_balance", loansObj.get("avail_balance").toString());
                map.put("remark", loansObj.get("remark").toString());
                map.put("payment_mode", loansObj.get("payment_mode").toString());
                map.put("mode_of_payment", loansObj.get("mode_of_payment").toString());
                map.put("cash_collected_by", loansObj.get("cash_collected_by").toString());
                map.put("cheque_number", loansObj.get("cheque_number").toString());
                map.put("cheque_date", loansObj.get("cheque_date").toString());
                map.put("bank_name", loansObj.get("bank_name").toString());
                map.put("bank_branch", loansObj.get("bank_branch").toString());
                map.put("utr_number", loansObj.get("utr_number").toString());
                map.put("payment_screenshots", loansObj.get("payment_screenshots").toString());
                map.put("ref_id", loansObj.get("ref_id").toString());
                map.put("order_id", loansObj.get("order_id").toString());
                map.put("payment_remark", loansObj.get("payment_remark").toString());
                map.put("created_by", loansObj.get("created_by").toString());
                map.put("status", loansObj.get("status").toString());
                map.put("approval", loansObj.get("approval").toString());
                map.put("type", loansObj.get("type").toString());
                map.put("razorpay_order_id", loansObj.get("razorpay_order_id").toString());
                map.put("razorpay_payment_id", loansObj.get("razorpay_payment_id").toString());
                map.put("razorpay_signature", loansObj.get("razorpay_signature").toString());
                map.put("created_at", loansObj.get("created_at").toString());
                map.put("updated_at", loansObj.get("updated_at").toString());
                map.put("created_datetime", loansObj.get("created_datetime").toString());
                walletList.add(map);
            }
            if (walletList.isEmpty()) {
                tvnodata.setVisibility(View.VISIBLE);
            } else {
                tvnodata.setVisibility(View.GONE);
                WalletAdapter walletAdapter = new WalletAdapter(walletList);
                rvwalletSummary.setAdapter(walletAdapter);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void getProfile() {

        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getProfile;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parsegetservestateJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsegetservestateJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            JSONObject dataJsonObject = response.getJSONObject("data");
            String profile_image_url = dataJsonObject.getString("profile_image_url");
            String documents_image_url = dataJsonObject.getString("documents_image_url");

            JSONObject userJsonObject = dataJsonObject.getJSONObject("profile");
            String id = userJsonObject.getString("id");
            String referral_user_id = userJsonObject.getString("referral_user_id");
            String referral_master_id = userJsonObject.getString("referral_master_id");
            String permission_id = userJsonObject.getString("permission_id");
            String name = userJsonObject.getString("name");

            String email = userJsonObject.getString("email");
            pref.set("razorpayemail", email).commit();
            String mobile_number = userJsonObject.getString("mobile_number");
            pref.set("razorpaymobno", mobile_number).commit();

            String role = userJsonObject.getString("role");
            String wallet_amount = userJsonObject.getString("wallet_amount");
            pref.set("wallet_amount", wallet_amount).commit();
            String first_name = userJsonObject.getString("first_name");
            String last_name = userJsonObject.getString("last_name");
            String business_type = userJsonObject.getString("business_type");
            String website = userJsonObject.getString("website");
            String referral_id = userJsonObject.getString("referral_id");
            String full_address = userJsonObject.getString("full_address");
            String pan_card_number = userJsonObject.getString("pan_card_number");
            String aadhaar_card_number = userJsonObject.getString("aadhaar_card_number");


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    @Override
    public void onClick(View v) {

    }

    private String setTimeValues(String date) {
        try {
            String pattern = "dd-MM-yyyy hh:mm a";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String setDateTimeValues(String date) {
        try {
            String pattern = "dd-MM-yyyy hh:mm aa";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public class TransAdapter extends RecyclerView.Adapter<TransHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();
        ArrayList<String> color = new ArrayList<>();

        public TransAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public TransHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_dshbooking, parent, false);
            return new TransHolder(itemView);

        }

        public void onBindViewHolder(TransHolder holder, final int position) {

                holder.tvAccNo.setText(data.get(position).get("name"));
                holder.tvAccNo1.setText(data.get(position).get("order_number"));
                holder.etDisbursed.setText(data.get(position).get("address"));
                holder.tvAccountName.setText(" ₹ " + data.get(position).get("buy_lead_amount"));

                holder.detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pref.set("order_id", data.get(position).get("id")).commit();
                        ((Container) mActivity).displayView(14);

                    }
                });

                holder.money.setText(" ₹ " + data.get(position).get("total_amount"));
                holder.tvlead.setText(" ₹ " + data.get(position).get("new_lead_amount"));
                holder.tvServicestatus.setText(data.get(position).get("order_status"));

                try {

                    JSONObject jsonObject = new JSONObject(data.get(position).get("package_information"));
                    JSONObject packageObject = jsonObject.getJSONObject("package");

                    String package_id = packageObject.getString("package_id");
                    Log.v("package_id", package_id);
                    String name = packageObject.getString("name");
                    String quantity = packageObject.getString("quantity");
                    String amount = packageObject.getString("amount");
                    String discount = packageObject.getString("discount");
                    String discount_amount = packageObject.getString("discount_amount");


                    holder.tvAccountName.setText(name);

                    //  holder.tvAccountName.setText(quantity);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        public int getItemCount() {
            return data.size();
        }
    }

    private class TransHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccNo1, detail, etDisbursed, tvlead,tvServicestatus, tvAccountName, money, SecurityFee, securitymoney;

        public TransHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            money = itemView.findViewById(R.id.money);
            tvlead = itemView.findViewById(R.id.tvlead);
            tvServicestatus = itemView.findViewById(R.id.tvServicestatus);

            detail = itemView.findViewById(R.id.detail);
            SecurityFee = itemView.findViewById(R.id.SecurityFee);
            securitymoney = itemView.findViewById(R.id.securitymoney);
        }
    }

    public class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public TransactionAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_dshrecommendedbooking, parent, false);
            return new TransactionHolder(itemView);
        }

        public void onBindViewHolder(TransactionHolder holder, final int position) {
            holder.tvLead.setText(" ₹ " + data.get(position).get("new_lead_amount"));
            holder.tvAccNo.setText(data.get(position).get("name"));
            holder.tvAccNo1.setText(setDateTimeValues(data.get(position).get("serving_datetime")));
            holder.etDisbursed.setText(data.get(position).get("address"));
            holder.tvAccountName.setText("XXXXXXXXXX");
            holder.tvLeadCost.setText(" ₹ " + data.get(position).get("total_amount"));
            holder.tvOrderValue.setText(" ₹ " + data.get(position).get("buy_lead_amount"));
            holder.dashBuyNow.setVisibility(View.GONE);
            if (data.get(position).get("buy_action_check").equalsIgnoreCase("button-show")) {
                holder.dashBuyNow.setVisibility(View.VISIBLE);
            } else {
                holder.dashBuyNow.setVisibility(View.GONE);
            }
            holder.leadDetail.setVisibility(View.GONE);
           /* holder.leadDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = data.get(position).get("id");
                    Log.v("dddddd", id);
                    pref.set("order_id", id).commit();
                    pref.set("MyOrderId",id).commit();

                    ((Container) mActivity).displayView(20);
                }
            });*/
            holder.dashBuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = data.get(position).get("id");
                    Log.v("dddddd", id);
                    pref.set("order_id", id).commit();
                    ((Container) mActivity).displayView(24);
                }
            });

            try {
                JSONObject package_information = new JSONObject(data.get(position).get("package_information"));
                JSONObject packagejOB = package_information.getJSONObject("package");
                String id = packagejOB.getString("package_id");
                String name = packagejOB.getString("name");
                String quantity = packagejOB.getString("quantity");
                String amount = packagejOB.getString("amount");
                String discount = packagejOB.getString("discount");
                String discount_amount = packagejOB.getString("discount_amount");
                holder.tvAcNo1.setText(name);
                Log.v("dddddsd", id);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class TransactionHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvOrderValue, leadDetail, tvLead, tvAccNo1, etDisbursed, tvAcNo1, tvLeadCost, tvAccountName;
        LinearLayout dashBuyNow;

        public TransactionHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            tvOrderValue = itemView.findViewById(R.id.tvOrderValue);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvLeadCost = itemView.findViewById(R.id.tvLeadCost);
            leadDetail = itemView.findViewById(R.id.leadDetail);
            dashBuyNow = itemView.findViewById(R.id.dashBuyNow);
            dashBuyNow = itemView.findViewById(R.id.dashBuyNow);

            tvLead = itemView.findViewById(R.id.tvLead);
            tvAcNo1 = itemView.findViewById(R.id.tvAcNo1);

        }
    }

    public class TodayAdapter extends RecyclerView.Adapter<TodayHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public TodayAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;

        }

        @Override
        public TodayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_dashboard_todays, parent, false);
            return new TodayHolder(itemView);
        }

        public void onBindViewHolder(TodayHolder holder, final int position) {
            holder.tvTime.setText(data.get(position).get("time"));
            holder.tvmessage.setText(data.get(position).get("message"));

            // Finding the view
            holder.mSeekBar.setProgress(holder.mSeekBar.getProgress() + 1);
            holder.mSeekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // True doesn't propagate the event
                    // the user cannot move the indicator
                    return true;
                }
            });
            // Setting up and initializing the form

        }


        public int getItemCount() {
            return data.size();
        }

    /*    @Override
        public View createStepContentView(int stepNumber) {
            View view = null;
            switch (stepNumber) {
                case 0:
                    view = createNameStep();
                    break;
                case 1:
                    view = createEmailStep();
                    break;
                case 2:
                    view = createPhoneNumberStep();
                    break;
            }
            return view;
        }

        @Override
        public void onStepOpening(int stepNumber) {
            switch (stepNumber) {
                case 0:
                    //checkName();
                    break;
                case 1:
                    // checkEmail();
                    break;
                case 2:
                    // As soon as the phone number step is open, we mark it as completed in order to show the "Continue"
                    // button (We do it because this field is optional, so the user can skip it without giving any info)

                  // holder.verticalStepperForm.setStepAsCompleted(2);
                    // In this case, the instruction above is equivalent to:
                    // verticalStepperForm.setActiveStepAsCompleted();
                    break;
            }
        }

        @Override
        public void sendData() {

        }
    }*/
    }

    private class TodayHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvmessage;
        SeekBar mSeekBar;
        VerticalStepperFormLayout verticalStepperForm;

        public TodayHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvmessage = itemView.findViewById(R.id.tvmessage);
            // verticalStepperForm = itemView.findViewById(R.id.vertical_stepper_form);
            mSeekBar = itemView.findViewById(R.id.seekbar);
        }
    }

    public class RecentAdapter extends RecyclerView.Adapter<RecentHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public RecentAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;

        }

        @Override
        public RecentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_recent_payout, parent, false);
            return new RecentHolder(itemView);
        }

        public void onBindViewHolder(RecentHolder holder, final int position) {
            holder.tvAccountr.setText(" ₹ " + data.get(position).get("total_amount"));
            holder.tvAccNo1.setText(data.get(position).get("vendor_comession"));
            holder.tvUTRNumber.setText(setTimeValues(data.get(position).get("created_at")));
            /*try {
                JSONObject orderInfo=new JSONObject(data.get(position).get("order_info"));
                String id=orderInfo.getString("id");
                String orderno=orderInfo.getString("order_number");
                String name=orderInfo.getString("name");
                String payment_method=orderInfo.getString("payment_method");
                holder.tvAccNo1.setText(name);
                holder.etDisbursed.setText(payment_method);
                holder.tvIDLEAD.setText(orderno);
            } catch (JSONException e) {
                e.printStackTrace();
            }
*/

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class RecentHolder extends RecyclerView.ViewHolder {
        TextView tvAccountr, tvAccNo1, tvUTRNumber;


        public RecentHolder(View itemView) {
            super(itemView);
            tvAccountr = itemView.findViewById(R.id.tvAccountr);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            tvUTRNumber = itemView.findViewById(R.id.tvUTRNumber);
        }
    }

    public class PendingPayoutAdapter extends RecyclerView.Adapter<payoutHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public PendingPayoutAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;

        }

        @Override
        public payoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_pending_payout, parent, false);
            return new payoutHolder(itemView);
        }

        public void onBindViewHolder(payoutHolder holder, final int position) {
            holder.tvAccountrs.setText(" ₹ " + data.get(position).get("total_amount"));
            holder.tvAccNo1s.setText(data.get(position).get("vendor_comession"));
            holder.tvUTRNumbers.setText(setTimeValues(data.get(position).get("created_at")));
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class payoutHolder extends RecyclerView.ViewHolder {
        TextView tvAccountrs, tvAccNo1s, tvUTRNumbers;

        public payoutHolder(View itemView) {
            super(itemView);
            tvAccountrs = itemView.findViewById(R.id.tvAccountrs);
            tvAccNo1s = itemView.findViewById(R.id.tvAccNo1s);
            tvUTRNumbers = itemView.findViewById(R.id.tvUTRNumbers);
        }
    }

    public class WalletAdapter extends RecyclerView.Adapter<WalletHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public WalletAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;

        }

        @Override
        public WalletHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_wallet_amount, parent, false);
            return new WalletHolder(itemView);
        }

        public void onBindViewHolder(WalletHolder holder, final int position) {
            /*  indianFormat .setTimeZone(TimeZone.getTimeZone("Asia/Kolkata")); */
            if (data.get(position).get("type").equalsIgnoreCase("Debited")) {
                holder.tvTime.setText(setTimeValues(data.get(position).get("created_datetime")));
                holder.tvmessage.setText("Your Wallet has Total Rs." + data.get(position).get("amount") + " Debited.");
            } else {
                holder.tvTime.setText(setTimeValues(data.get(position).get("created_datetime")));
                holder.tvmessage.setText("Your Wallet has Total Rs." + data.get(position).get("amount") + " Credited.");

            }
            holder.mSeekBar.setProgress(holder.mSeekBar.getProgress() + 1);
            holder.mSeekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // True doesn't propagate the event
                    // the user cannot move the indicator
                    return true;
                }
            });
        }

        public int getItemCount() {
            return data.size();
        }

    }

    private class WalletHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvmessage;
        SeekBar mSeekBar;
        VerticalStepperFormLayout verticalStepperForm;

        public WalletHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvmessage = itemView.findViewById(R.id.tvmessage);
            // verticalStepperForm = itemView.findViewById(R.id.vertical_stepper_form);
            mSeekBar = itemView.findViewById(R.id.seekbar);
        }
    }

}
