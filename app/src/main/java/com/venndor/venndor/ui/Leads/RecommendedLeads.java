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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.tvHeading;

public class RecommendedLeads extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recybooking;
    Preferences pref;
    LinearLayoutManager layoutManager;
    ArrayList<HashMap<String, String>> BookingsList = new ArrayList<>();
    RelativeLayout Rl_noLeads, Rl_leads;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recomended_leads, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvHeading.setText("Recommended Leads");
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
        recybooking = rootView.findViewById(R.id.recyclerleads);
        Rl_noLeads = rootView.findViewById(R.id.Rl_noLeads);
        Rl_leads = rootView.findViewById(R.id.Rl_leads);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recybooking.setLayoutManager(layoutManager);
        getbuybookings();
    }

    @Override
    public void onClick(View v) {

    }

    public void getbuybookings() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.Getvendorbuybookings;
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
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                BookingsList.clear();
                String message = response.getString("message");
                Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                if (response.getString("message").equalsIgnoreCase("No Any Booking Found.")) {
                    Rl_noLeads.setVisibility(View.VISIBLE);
                     Rl_leads.setVisibility(View.GONE);
                } else {
                    Rl_noLeads.setVisibility(View.GONE);
                    Rl_leads.setVisibility(View.VISIBLE);
                }
                JSONObject jsonObjectt = response.getJSONObject("data");
                String wallet_amount = jsonObjectt.getString("wallet_amount");
                pref.set("wallet_amount", wallet_amount).commit();
                JSONArray jsonArray = jsonObjectt.getJSONArray("orders");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("user_id", block_object.get("user_id").toString());
                    map.put("vendor_id", block_object.get("vendor_id").toString());
                    map.put("package_id", block_object.get("package_id").toString());
                    map.put("order_number", block_object.get("order_number").toString());
                    map.put("cart_details", block_object.get("cart_details").toString());
                    map.put("total_amount", block_object.get("total_amount").toString());
                    map.put("vendor_service_amount", block_object.get("vendor_service_amount").toString());
                    map.put("order_status", block_object.get("order_status").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("email", block_object.get("email").toString());
                    map.put("mobile_number", block_object.get("mobile_number").toString());
                    map.put("address", block_object.get("address").toString());
                    map.put("landmark", block_object.get("landmark").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("city", block_object.get("city").toString());
                    map.put("pincode", block_object.get("pincode").toString());
                    map.put("payment_method", block_object.get("payment_method").toString());
                    map.put("serve_date", block_object.get("serve_date").toString());
                    map.put("serve_time", block_object.get("serve_time").toString());
                    map.put("v_buy_lead_amount", block_object.get("v_buy_lead_amount").toString());
                    map.put("buy_lead_percent", block_object.get("buy_lead_percent").toString());
                    map.put("buy_lead_amount", block_object.get("buy_lead_amount").toString());
                    map.put("action_check", block_object.get("action_check").toString());
                    map.put("buy_action_check", block_object.get("buy_action_check").toString());
                    map.put("package_information", block_object.get("package_information").toString());
                    map.put("serving_datetime", block_object.get("serving_datetime").toString());
                    map.put("new_lead_amount", block_object.get("new_lead_amount").toString());


                    BookingsList.add(map);
                }
                OfferAdapter OfferAdapter = new OfferAdapter(BookingsList);
                recybooking.setAdapter(OfferAdapter);
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_leads, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.tvLead.setText(" ₹ " +data.get(position).get("new_lead_amount"));

            holder.tvAccNo1.setText(setDateTimeValues(data.get(position).get("serving_datetime")));
            holder.tvAccNo.setText(data.get(position).get("name"));
            holder.etDisbursed.setText(data.get(position).get("address"));
            holder.tvAccountName1.setText(data.get(position).get("location"));

            holder.loan.setText(" ₹ " + data.get(position).get("buy_lead_amount"));
            holder.tvAccHolderName.setText(" ₹ " +data.get(position).get("total_amount"));
            holder.llrecommendedleadbuy.setVisibility(View.GONE);
            holder.tvAccountName.setText("XXXXXXXXXX");
            if(data.get(position).get("buy_action_check").equalsIgnoreCase("button-show")){
                holder.llrecommendedleadbuy.setVisibility(View.VISIBLE);
            }else {
                holder.llrecommendedleadbuy.setVisibility(View.GONE);
            }

            holder.llrecommendedleadbuy.setOnClickListener(new View.OnClickListener() {
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
                holder.tvAccountName1.setText(name);
                Log.v("dddddsd", id);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccNo1, etDisbursed,tvLead,
                tvAccountName1, tvAccountName, loan, tvAccHolderName;
        LinearLayout llrecommendedleadbuy;

        public OfferHolder(View itemView) {
            super(itemView);
            tvLead = itemView.findViewById(R.id.tvLead);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            loan = itemView.findViewById(R.id.loan);
            tvAccHolderName = itemView.findViewById(R.id.tvAccHolderName);
            llrecommendedleadbuy = itemView.findViewById(R.id.llrecommendedleadbuy);
        }
    }

    private String setDateTimeValues(String date) {
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
}
