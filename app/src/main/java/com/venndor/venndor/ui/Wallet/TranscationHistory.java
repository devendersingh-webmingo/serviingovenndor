package com.venndor.venndor.ui.Wallet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.TimeZone;

import static com.venndor.venndor.ui.Container.ivIamge;

public class TranscationHistory extends BaseFragment implements View.OnClickListener {
    View rootView;
    RecyclerView recybooking;
    LinearLayoutManager layoutManager;
    TextView tvLeaddebit, tvBookingCommission, tvdebit, totalCreadit;
    Preferences pref;
    OfferAdapter offerAdapter;
    ComissionAdapter commissionAdapter;
    ArrayList<HashMap<String, String>> BookingsList = new ArrayList<>();
    ArrayList<HashMap<String, String>> CommissionList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.transcationhistory, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvLeaddebit = rootView.findViewById(R.id.tvleaddebit);
        tvBookingCommission = rootView.findViewById(R.id.tvbookingCommission);
        tvdebit = rootView.findViewById(R.id.tvdebit);
        totalCreadit = rootView.findViewById(R.id.totalCreadit);
        recybooking = rootView.findViewById(R.id.recyclerviewtranscation);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recybooking.setLayoutManager(layoutManager);
        String total_debit = pref.get("total_debit");
        String total_credit = pref.get("total_credit");
        tvdebit.setText(getString(R.string.debit) + "₹" + total_debit);
        totalCreadit.setText(getString(R.string.credits) + "₹" + total_credit);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        tvLeaddebit.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
        tvBookingCommission.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvLeaddebit.setOnClickListener(this);
        tvBookingCommission.setOnClickListener(this);
        getbuybookings();
        recybooking.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvleaddebit:
                getbuybookings();

                recybooking.setVisibility(View.VISIBLE);
                tvLeaddebit.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvBookingCommission.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvbookingCommission:
                getCommissions();

                recybooking.setVisibility(View.VISIBLE);
                tvBookingCommission.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvLeaddebit.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
        }
    }

    private void getCommissions() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.GetvendorComissions;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseCommissionResponse(response);
                    }

                    private void parseCommissionResponse(JSONObject response) {
                        try {
                            AppUtils.hideDialog();
                            CommissionList.clear();
                            Log.v("responseApiCheck", "empty " + response);
                            if (response.getString("status").equals("true")) {
                                String message = response.getString("message");
                                JSONObject jsonObjectt = response.getJSONObject("data");
                                JSONArray comissions = jsonObjectt.getJSONArray("comissions");
                                for (int i = 0; i < comissions.length(); i++) {
                                    JSONObject loansObj = comissions.getJSONObject(i);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("id", loansObj.get("id").toString());
                                    map.put("vendor_id", loansObj.get("vendor_id").toString());
                                    map.put("order_id", loansObj.get("order_id").toString());
                                    map.put("name", loansObj.get("name").toString());
                                    map.put("mobile_number", loansObj.get("mobile_number").toString());
                                    map.put("total_amount", loansObj.get("total_amount").toString());
                                    map.put("amount_without_gst", loansObj.get("amount_without_gst").toString());
                                    map.put("gst", loansObj.get("gst").toString());
                                    map.put("vendor_comession", loansObj.get("vendor_comession").toString());
                                    map.put("comission_percent", loansObj.get("comission_percent").toString());
                                    map.put("added_in_wallet", loansObj.get("added_in_wallet").toString());
                                    map.put("payout", loansObj.get("payout").toString());
                                    map.put("created_at", loansObj.get("created_at").toString());
                                    map.put("updated_at", loansObj.get("updated_at").toString());
                                    map.put("unique_id", loansObj.get("unique_id").toString());
                                    map.put("order_number", loansObj.get("order_number").toString());
/*
                                    map.put("new_lead_amount", loansObj.get("new_lead_amount").toString());
*/

                                    CommissionList.add(map);
                                }
                                commissionAdapter = new ComissionAdapter(CommissionList);
                                recybooking.setAdapter(commissionAdapter);
                                commissionAdapter.notifyDataSetChanged();
                            } else {
                            }
                        } catch (Exception e) {
                            Log.v("error", String.valueOf(e));
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }


    public void getbuybookings() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.GetvendormywalletDebit;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsebuybookingsJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsebuybookingsJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            BookingsList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject jsonObjectt = response.getJSONObject("data");
                String total_debit = jsonObjectt.getString("total_debit");
                String total_credit = jsonObjectt.getString("total_credit");
                String available_wallet_amount = jsonObjectt.getString("available_wallet_amount");


                JSONArray histories = jsonObjectt.getJSONArray("histories");
                for (int i = 0; i < histories.length(); i++) {
                    JSONObject loansObj = histories.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("title", loansObj.get("title").toString());
                    map.put("user_id", loansObj.get("user_id").toString());
                    map.put("booking_id", loansObj.get("booking_id").toString());
                    map.put("transaction_id", loansObj.get("transaction_id").toString());
                    map.put("withdrawal", loansObj.get("withdrawal").toString());
                    map.put("deposit", loansObj.get("deposit").toString());
                    map.put("amount", loansObj.get("amount").toString());
                    map.put("order_info", loansObj.get("order_info").toString());
                    map.put("avail_balance", loansObj.get("avail_balance").toString());
                    map.put("remark", loansObj.get("remark").toString());
                    map.put("payment_mode", loansObj.get("payment_mode").toString());
                    map.put("mode_of_payment", loansObj.get("mode_of_payment").toString());
                    map.put("payment_remark", loansObj.get("payment_remark").toString());
                    map.put("created_by", loansObj.get("created_by").toString());
                    map.put("approval", loansObj.get("approval").toString());
                    map.put("payment_status", loansObj.get("payment_status").toString());
                    map.put("type", loansObj.get("type").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("updated_at", loansObj.get("updated_at").toString());
                    BookingsList.add(map);
                }
                offerAdapter = new OfferAdapter(BookingsList);
                recybooking.setAdapter(offerAdapter);
                offerAdapter.notifyDataSetChanged();
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

   /* private String setTimeValues(String date) {
        try {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            Log.v("date",formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }*/
    private String setTimeValues(String date) {
        try {

            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat indianFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            indianFormat .setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

            Date timestamp = utcFormat.parse(date);
            String output = indianFormat.format(timestamp);
            Log.v("ii",output);
            return output.toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_lead_debit, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            if (!data.get(position).get("created_at").equalsIgnoreCase("null")) {
                holder.tvAccNo.setText(setTimeValues(data.get(position).get("created_at")));
            } else {
                holder.tvAccNo.setText("N/A");
            }

            if (!data.get(position).get("remark").equalsIgnoreCase("null")) {
                holder.title.setText(data.get(position).get("remark"));
            } else {
                holder.title.setText("N/A");
            }

            if (!data.get(position).get("amount").equalsIgnoreCase("null")) {
                holder.tvAccountName.setText(" ₹ " + data.get(position).get("amount"));
            } else {
                holder.tvAccountName.setText("N/A");
            }
            try {
                JSONObject orderInfo = new JSONObject(data.get(position).get("order_info"));
                String id = orderInfo.getString("id");
                Log.v("dddddd", id);
                pref.set("order_id", id).commit();
                String orderno = orderInfo.getString("order_number");
                String name = orderInfo.getString("name");
                String payment_method = orderInfo.getString("payment_method");
                holder.tvAccNo1.setText(name);
                holder.etDisbursed.setText(payment_method);
                holder.tvIDLEAD.setText(orderno);
                holder.ViewLeadDetail.setVisibility(View.GONE);
                if(orderInfo.getString("id").equalsIgnoreCase("null") || orderInfo.getString("id").isEmpty()){
                    holder.ViewLeadDetail.setVisibility(View.GONE);
                }else{
                    holder.ViewLeadDetail.setVisibility(View.VISIBLE);
                    holder.ViewLeadDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            pref.set("MyOrderId",id).commit();
                            pref.set("order_id",id).commit();
                            ((Container) mActivity).displayView(20);
                        }
                    });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (data.get(position).get("approval") == null || data.get(position).get("approval").equalsIgnoreCase("")) {
                holder.llstatus.setVisibility(View.GONE);
            } else {
                if (data.get(position).get("approval").equalsIgnoreCase("Failed")) {
                    holder.llstatus.setVisibility(View.VISIBLE);
                    holder.llstatus.setBackgroundResource(R.drawable.red_button);
                    holder.tvStatus.setText("Failed");
                } else if (data.get(position).get("approval").equalsIgnoreCase("Success")) {
                    holder.llstatus.setVisibility(View.VISIBLE);
                    holder.llstatus.setBackgroundResource(R.drawable.green_bg);
                    holder.tvStatus.setText("Success");
                }

            }
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccNo1, etDisbursed, tvIDLEAD,
                tvAccountName, tvStatus, title;
        LinearLayout ViewLeadDetail, llstatus;

        public OfferHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvIDLEAD = itemView.findViewById(R.id.tvIDLEAD);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            ViewLeadDetail = itemView.findViewById(R.id.ViewLeadDetail);
            llstatus = itemView.findViewById(R.id.llstatus);
            title = itemView.findViewById(R.id.title);
        }
    }


    public class ComissionAdapter extends RecyclerView.Adapter<CommissionHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public ComissionAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public CommissionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_commission_screen, parent, false);
            return new CommissionHolder(itemView);
        }

        public void onBindViewHolder(CommissionHolder holder, final int position) {
            if(data.get(position).get("order_id").equalsIgnoreCase("null") || data.get(position).get("order_id").isEmpty()){
                holder.ViewLeadDetail.setVisibility(View.GONE);
            }else{
                holder.ViewLeadDetail.setVisibility(View.VISIBLE);
            }

            holder.ViewLeadDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pref.set("MyOrderId",data.get(position).get("order_id")).commit();
                    pref.set("order_id",data.get(position).get("order_id")).commit();
                    ((Container) mActivity).displayView(20);
                }
            });
/*holder.tvAccHolderName.setText(data.get(position).get("new_lead_amount"));*/
            if (!data.get(position).get("created_at").equalsIgnoreCase("null")) {
                holder.tvAccNo.setText(setTimeValues(data.get(position).get("created_at")));
            } else {
                holder.tvAccNo.setText("N/A");
            }
            if (!data.get(position).get("unique_id").equalsIgnoreCase("null")) {
                holder.tvAccNo1.setText(data.get(position).get("unique_id"));
            } else {
                holder.tvAccNo1.setText("N/A");
            }
            if (!data.get(position).get("order_number").equalsIgnoreCase("null")) {
                holder.etDisbursed.setText(data.get(position).get("order_number"));
            } else {
                holder.etDisbursed.setText("N/A");
            }
            if (!data.get(position).get("amount_without_gst").equalsIgnoreCase("null")) {
                holder.tvAccountName1.setText(" ₹ " + data.get(position).get("amount_without_gst"));
            } else {
                holder.tvAccountName1.setText("N/A");
            }
            if (!data.get(position).get("vendor_comession").equalsIgnoreCase("null")) {
                holder.tvAccountName.setText(" ₹ " + data.get(position).get("vendor_comession") + " ( " + data.get(position).get("comission_percent") + "% " + ")");
            } else {
                holder.tvAccountName.setText("N/A");
            }
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class CommissionHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccNo1, etDisbursed, tvAccountName1,tvAccHolderName,
                tvAccountName;
        LinearLayout ViewLeadDetail;

        public CommissionHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
           /* tvAccHolderName = itemView.findViewById(R.id.tvAccHolderName);*/
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            ViewLeadDetail = itemView.findViewById(R.id.ViewLeadDetail);

        }
    }
}
