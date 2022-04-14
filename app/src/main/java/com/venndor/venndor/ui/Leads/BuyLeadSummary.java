package com.venndor.venndor.ui.Leads;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.venndor.venndor.ui.Wallet.AddMoney;
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

public class BuyLeadSummary extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recyrecentBuyLeads;
    OfferAdapter OfferAdapter;
    LinearLayoutManager layoutManager;
    Preferences pref;
    TextView addfund, tvAccNo, tvAccNo1, etDisbursed, tvAccountName, tvAccHolderName, loan;
    String order_id;
    LinearLayout llBuyLead;
    float amount;
    ArrayList<HashMap<String, String>> addonslist = new ArrayList();
    ArrayList<HashMap<String, String>> Recentlist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.buy_lead_summry, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        order_id = pref.get("order_id");
        recyrecentBuyLeads = rootView.findViewById(R.id.recyrecentBuyLeads);
        llBuyLead = rootView.findViewById(R.id.llBuyLead);
        addfund = rootView.findViewById(R.id.addfund);
        loan = rootView.findViewById(R.id.loan);
        tvAccNo = rootView.findViewById(R.id.tvAccNo);
        tvAccNo1 = rootView.findViewById(R.id.tvAccNo1);
        etDisbursed = rootView.findViewById(R.id.etDisbursed);
        tvAccountName = rootView.findViewById(R.id.tvAccountName);
        tvAccHolderName = rootView.findViewById(R.id.tvAccHolderName);
        addfund.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyrecentBuyLeads.setLayoutManager(layoutManager);
        llBuyLead.setOnClickListener(this);
        addfund.setOnClickListener(this);
        GetvendormywalletHistory();
        Getvendor(order_id);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBuyLead:
                Postvendorbuylead(order_id);
                break;
            case R.id.addfund:
                AlertAmount();
                break;
        }
    }

    public void GetvendormywalletHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getRecentLeads;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegetvendormywalletJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsegetvendormywalletJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            Recentlist.clear();
            String message = response.getString("message");
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject jsonObjectt = response.getJSONObject("data");

            JSONArray histories = jsonObjectt.getJSONArray("histories");
            for (int i = 0; i < histories.length(); i++) {
                JSONObject loansObj = histories.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("order_number", loansObj.get("order_number").toString());
                map.put("buy_lead_amount", loansObj.get("buy_lead_amount").toString());
                map.put("serve_date", loansObj.get("serve_date").toString());
                map.put("serve_time", loansObj.get("serve_time").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("serving_datetime", loansObj.get("serving_datetime").toString());
                Recentlist.add(map);
            }
            OfferAdapter = new OfferAdapter(Recentlist);
            recyrecentBuyLeads.setAdapter(OfferAdapter);

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void AlertAmount() {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.inflate_amount_add);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final String[] SpinMonth = {""};
        final String[] SpinYear = {""};

        EditText etamount = dialog.findViewById(R.id.etamount);
        TextView tvSubmit = dialog.findViewById(R.id.tvSubmit);
        TextView cut = dialog.findViewById(R.id.cut);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etamount.getText().toString().trim().isEmpty()) {
                    Toast.makeText(mActivity, "Please enter amount here", Toast.LENGTH_SHORT).show();
                } else {
                    String money = etamount.getText().toString().trim();
                    pref.set("money", money).commit();
                  /*  tvHeading.setText("Add Money");
                    ivIamge.setImageResource(R.drawable.user);
                    ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);*/
                    /*  ((Container) mActivity).displayView(9);*/

                    Intent i = new Intent(mActivity, AddMoney.class);
                    i.putExtra("keyBuyLeadSummary", "addfundTowallet");
                    startActivity(i);

                    dialog.dismiss();
                }
            }
        });
    }

    private void Postvendorbuylead(String order_id) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorbuylead;
        String token = pref.get("token");
        Log.v("ddurl", token);
        AndroidNetworking.post(url)
                .addBodyParameter("order_id", order_id)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsevendorbuyleadJsonResponse(response);
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

    private void parsevendorbuyleadJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            addonslist.clear();

            Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            Log.v("responseApiCheck", "empty " + response);
            JSONObject dataJsonObject = response.getJSONObject("data");
            JSONObject userJsonObject = dataJsonObject.getJSONObject("order");//addons
            String id = userJsonObject.getString("id");
            String user_id = userJsonObject.getString("user_id");
            String vendor_id = userJsonObject.getString("vendor_id");
            String package_id = userJsonObject.getString("package_id");
            String pincode = userJsonObject.getString("pincode");
            String order_number = userJsonObject.getString("order_number");
            String cart_details = userJsonObject.getString("cart_details");
            String sub_amount = userJsonObject.getString("sub_amount");
            String discount_amount = userJsonObject.getString("discount_amount");
            String membership_discount = userJsonObject.getString("membership_discount");
            String total_amount = userJsonObject.getString("total_amount");
            String vendor_service_amount = userJsonObject.getString("vendor_service_amount");
            String amount_without_tax = userJsonObject.getString("amount_without_tax");
            String tax_name = userJsonObject.getString("tax_name");
            String tax_amount = userJsonObject.getString("tax_amount");
            String hygiene_fees = userJsonObject.getString("hygiene_fees");
            String order_status = userJsonObject.getString("order_status");
            String vendor_status = userJsonObject.getString("vendor_status");
            String name = userJsonObject.getString("name");
            String email = userJsonObject.getString("email");
            String mobile_number = userJsonObject.getString("mobile_number");
            String address = userJsonObject.getString("address");
            String landmark = userJsonObject.getString("landmark");
            String state_id = userJsonObject.getString("state_id");
            String city = userJsonObject.getString("city");
            String cancellation_reasons = userJsonObject.getString("cancellation_reasons");
            String support_question_id = userJsonObject.getString("support_question_id");
            String cancellation_remark = userJsonObject.getString("cancellation_remark");
            String cancelled_by = userJsonObject.getString("cancelled_by");
            String payment_method = userJsonObject.getString("payment_method");
            String payment_status = userJsonObject.getString("payment_status");
            String assigned_status = userJsonObject.getString("assigned_status");
            String payment_meethod = userJsonObject.getString("payment_method");
            String remark = userJsonObject.getString("remark");
            String membership_id = userJsonObject.getString("membership_id");
            String membership_cost = userJsonObject.getString("membership_cost");
            String cgst = userJsonObject.getString("cgst");
            String sgst = userJsonObject.getString("sgst");
            String igst = userJsonObject.getString("igst");
            String device_type = userJsonObject.getString("device_type");
            String device_info = userJsonObject.getString("device_info");
            String system_ip = userJsonObject.getString("system_ip");
            String serve_date = userJsonObject.getString("serve_date");
            String serve_time = userJsonObject.getString("serve_time");
            String serving_datetime = userJsonObject.getString("serving_datetime");
            String accept_by_vendor = userJsonObject.getString("accept_by_vendor");
            String reject_reason_by_vendor = userJsonObject.getString("reject_reason_by_vendor");
            String service_start = userJsonObject.getString("service_start");
            String rejection_accept = userJsonObject.getString("rejection_accept");
            String tax_percent = userJsonObject.getString("tax_percent");
            String v_buy_lead_amount = userJsonObject.getString("v_buy_lead_amount");
            String buy_lead_percent = userJsonObject.getString("buy_lead_percent");
            String order_device_type = userJsonObject.getString("order_device_type");
            String vendor_attachment = userJsonObject.getString("vendor_attachment");
            String created_at = userJsonObject.getString("created_at");
            String updated_at = userJsonObject.getString("updated_at");


            String action_check = userJsonObject.getString("action_check");
            JSONObject package_informationJsonObject = userJsonObject.getJSONObject("package_information");
            JSONObject packageobj = package_informationJsonObject.getJSONObject("package");
            String package_ids = packageobj.getString("package_id");
            String names = packageobj.getString("name");
            String quantity = packageobj.getString("quantity");
            String amountt = packageobj.getString("amount");
            String discount = packageobj.getString("discount");
            String discount_amountt = packageobj.getString("discount_amount");
            if (response.getString("status").equals("true")) {
                Intent i = new Intent(mActivity, ThankYou.class);
                i.putExtra("buyleadid", order_id);
                i.putExtra("order_number", order_number);
                i.putExtra("name", name);
                i.putExtra("serving_datetime", serving_datetime);

                startActivity(i);
            } else {

            }
            JSONArray addonsJsonObject = userJsonObject.getJSONArray("package_information");//
            for (int i = 0; i < addonsJsonObject.length(); i++) {
                JSONObject loansObj = addonsJsonObject.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", loansObj.get("id").toString());
                map.put("name", loansObj.get("name").toString());
                map.put("discount", loansObj.get("discount").toString());
                map.put("after_discount", loansObj.get("after_discount").toString());
                map.put("quantity", loansObj.get("quantity").toString());
                addonslist.add(map);
            }


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void Getvendor(String order_id) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");

        Log.v("ddurl", token);
        String url = AppUrls.getSummary + order_id;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseResponse(JSONObject response) {

        AppUtils.hideDialog();
        Log.v("responseApiCheck", "empty " + response);
        try {
            if (response.getString("status").equals("true")) {


                String message = response.getString("message");
                Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONObject userJsonObject = jsonObjectt.getJSONObject("order_info");//addons
                String order_number = userJsonObject.getString("order_numer");
                String lead_amount = userJsonObject.getString("lead_amount");
                String serving_datetime = userJsonObject.getString("serve_datetime");
                String service = userJsonObject.getString("service");


              String moneyvalue=userJsonObject.getString("buy_lead_amount").replace(",","");
                 float buy_lead_amount= Float.parseFloat(moneyvalue);
                   // Double  buy_lead_amount = Double.parseDouble(userJsonObject.getString("buy_lead_amount"));
                if(!pref.get("wallet_amount").isEmpty()) {
                    amount = Float.parseFloat(pref.get("wallet_amount"));
                    Log.v("hjdf", String.valueOf(amount));
                    loan.setText("Wallet" + " ( " + amount + " )");
                    if (buy_lead_amount < amount) {
                        addfund.setVisibility(View.GONE);
                        llBuyLead.setVisibility(View.VISIBLE);
                        loan.setTextColor(getResources().getColor(R.color.textColor));

                    } else {
                        addfund.setVisibility(View.VISIBLE);
                        llBuyLead.setVisibility(View.GONE);
                        loan.setTextColor(getResources().getColor(R.color.red));
                    }

                    String leadAmount = String.valueOf(buy_lead_amount);

                    if (!order_number.equalsIgnoreCase("null")) {
                        tvAccNo.setText("XXXXXXXXXX");
                    } else {
                        tvAccNo.setText("N/A");
                    }
                    if (!leadAmount.equalsIgnoreCase("null")) {
                        tvAccNo1.setText(leadAmount);
                    } else {
                        tvAccNo1.setText("N/A");
                    }
                    if (!serving_datetime.equalsIgnoreCase("null")) {
                        etDisbursed.setText(setTimeDateValues(serving_datetime));

                    } else {
                        etDisbursed.setText("N/A");
                    }

                    if (!service.equalsIgnoreCase("null")) {
                        tvAccountName.setText(service);
                    } else {
                        tvAccountName.setText("N/A");
                    }
                }else{

                }
            } else {
                JSONObject useJsonObject = response.getJSONObject("message");
                String ss = useJsonObject.getString("error");
                Toast.makeText(mActivity, "" + ss, Toast.LENGTH_SHORT).show();

                tvAccNo.setText("N/A");
                tvAccNo1.setText("N/A");
                etDisbursed.setText("N/A");
                tvAccountName.setText("N/A");

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> bookingsList) {
            this.data = bookingsList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_buy_leads_summary, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.etDisbursed.setText(setTimeDateValues(data.get(position).get("serving_datetime")));
            holder.tvAccount.setText(data.get(position).get("order_number"));
            holder.tvAccNo1.setText(" ₹ " + data.get(position).get("buy_lead_amount"));
            holder.tvAccNo.setText(data.get(position).get("name"));

            holder.movetoDetail.setOnClickListener(new View.OnClickListener() {
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

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccount, tvAccNo1, etDisbursed;
        RelativeLayout movetoDetail;

        public OfferHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            movetoDetail = itemView.findViewById(R.id.movetoDetail);
        }
    }
    /*private String setDateTimeValues(String date) {
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
    }*/
    private String setTimeDateValues(String date) {
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
