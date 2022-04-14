package com.venndor.venndor.ui.Leads;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;

public class LeadHistory extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    Preferences pref;
    LinearLayout llhistory;
    TextView tvToday, tvYesterday, tvWeek, tvMonth;
    ArrayList<HashMap<String, String>> historylist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.leadmainhistory, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recyclerview = rootView.findViewById(R.id.recyclerview);
        tvToday = rootView.findViewById(R.id.tvToday);
        tvYesterday = rootView.findViewById(R.id.tvYesterday);
        tvWeek = rootView.findViewById(R.id.tvWeek);
        llhistory = rootView.findViewById(R.id.llhistory);
        tvMonth = rootView.findViewById(R.id.tvMonth);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linearLayoutManager);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        tvToday.setOnClickListener(this);
        tvYesterday.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
        tvMonth.setOnClickListener(this);
        tvToday.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
        tvYesterday.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvWeek.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvMonth.setBackground(getResources().getDrawable(R.drawable.white_border));
        GetTodayHistory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvToday:

                GetTodayHistory();
                tvToday.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvYesterday.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvWeek.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvMonth.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvYesterday:
                GetYesterdayHistory();
                tvYesterday.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvToday.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvWeek.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvMonth.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvWeek:
                GetWeekHistory();
                tvWeek.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvYesterday.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvToday.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvMonth.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvMonth:

                GetMonthHistory();
                tvMonth.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvYesterday.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvWeek.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvToday.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
        }
    }

    public void GetTodayHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getTodayLeads;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegettodayLeadHistoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    public void GetYesterdayHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getYesterdayLeads;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegettodayLeadHistoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    public void GetWeekHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getWeekLeads;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegettodayLeadHistoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    public void GetMonthHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getMonthLeads;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegettodayLeadHistoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsegettodayLeadHistoryJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            historylist.clear();
            String message = response.getString("message");
            Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
            JSONObject jsonObjectt = response.getJSONObject("data");

            JSONArray histories = jsonObjectt.getJSONArray("histories");
            if(histories.length()==0){
                llhistory.setVisibility(View.GONE);
            }else {
                llhistory.setVisibility(View.VISIBLE);
            }
            for (int i = 0; i < histories.length(); i++) {
                JSONObject loansObj = histories.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("user_id", loansObj.get("user_id").toString());
                map.put("vendor_id", loansObj.get("vendor_id").toString());
                map.put("cart_details", loansObj.get("cart_details").toString());
                map.put("order_number", loansObj.get("order_number").toString());
                map.put("total_amount", loansObj.get("total_amount").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("payment_method", loansObj.get("payment_method").toString());
                map.put("serve_date", loansObj.get("serve_date").toString());
                map.put("serve_time", loansObj.get("serve_time").toString());
                map.put("new_lead_amount", loansObj.get("new_lead_amount").toString());
                map.put("buy_lead_amount", loansObj.get("buy_lead_amount").toString());
                map.put("action_check", loansObj.get("action_check").toString());
                historylist.add(map);
            }
            HistoryAdapter historyAdapter = new HistoryAdapter(historylist);
            recyclerview.setAdapter(historyAdapter);
            historyAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }


    public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();
        ArrayList<String> color = new ArrayList<>();

        public HistoryAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;

        }

        @Override
        public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lead_history, parent, false);
            return new HistoryHolder(itemView);

        }

        public void onBindViewHolder(HistoryHolder holder, final int position) {
            holder.tvAccount.setText(data.get(position).get("order_number"));

            holder.tvLead.setText(" ₹ " +data.get(position).get("new_lead_amount"));
            holder.tvAccNo.setText(data.get(position).get("name"));
            holder.tvAccNo1.setText(" ₹ " +data.get(position).get("total_amount"));
            holder.etDisbursed.setText(setDateTimeValues(data.get(position).get("serve_date")) + " | " + data.get(position).get("serve_time"));
            holder.tvAccountName1.setText(data.get(position).get("payment_method"));
            holder.rrdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pref.set("MyOrderId",data.get(position).get("id")).commit();
                    pref.set("order_id",data.get(position).get("id")).commit();

                    ((Container) mActivity).displayView(20);
                }
            });
        }
        public int getItemCount() {
            return data.size();
        }
    }

    private class HistoryHolder extends RecyclerView.ViewHolder {
        TextView tvAccount, tvAccNo, tvAccNo1, etDisbursed,tvLead, tvAccountName1;
        RelativeLayout rrdetail;

        public HistoryHolder(View itemView) {
            super(itemView);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvLead = itemView.findViewById(R.id.tvLead);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            rrdetail = itemView.findViewById(R.id.rrdetail);

        }
    }
    private String setDateTimeValues(String date) {
        try {
            String pattern = "yyyy-MM-dd";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
