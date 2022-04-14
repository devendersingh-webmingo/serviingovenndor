package com.venndor.venndor.ui.CommissionCreadits;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Presenter.PaymentInfoPopupPresenter;
import com.venndor.venndor.ui.Presenter.Repo.PaymentInfoPopup;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VendorPayouts extends BaseFragment implements View.OnClickListener, PaymentInfoPopupPresenter.PaymentInfoPopupView {
    View rootView;
    TextView tvongoing, tvPending, tvReleased;
    RecyclerView recyPayout;
    LinearLayoutManager layoutManager;
    Preferences pref;
    Boolean click = false;
    ArrayList<HashMap<String, String>> payoutlist = new ArrayList<>();
    RelativeLayout Rl_noLeads, Rl_leads;
    TextView tvAccNot, tvAccNo1, tvUTRNumber, UtrNo;
    ImageView ivName;
    PaymentInfoPopupPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payout_released, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvongoing = rootView.findViewById(R.id.tvongoing);
        tvPending = rootView.findViewById(R.id.tvPending);
        tvReleased = rootView.findViewById(R.id.tvReleased);
        recyPayout = rootView.findViewById(R.id.recyrecent);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyPayout.setLayoutManager(layoutManager);
        Rl_noLeads = rootView.findViewById(R.id.Rl_noLeads);
        Rl_leads = rootView.findViewById(R.id.Rl_leads);
        presenter = new PaymentInfoPopupPresenter(this);

        GetvendorPayout();
        tvongoing.setOnClickListener(this);
        tvPending.setOnClickListener(this);
        tvReleased.setOnClickListener(this);
        tvongoing.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
        tvPending.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvReleased.setBackground(getResources().getDrawable(R.drawable.white_border));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvongoing:
                click = false;
                GetvendorPayout();
                tvongoing.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvPending.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvReleased.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvPending:
                click = false;
                GetvendorPendingPayout();
                tvPending.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvongoing.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvReleased.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvReleased:
                click = true;
                GetvendorCompletedPayout();

                tvReleased.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvPending.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvongoing.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
        }
    }


    public void GetvendorPayout() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = "https://serviingo.com/api/vendor/payouts/Ongoing";
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

    public void GetvendorPendingPayout() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = "https://serviingo.com/api/vendor/payouts/Pending";
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

    public void GetvendorCompletedPayout() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = "https://serviingo.com/api/vendor/payouts/Released";
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
            payoutlist.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject jsonObjectt = response.getJSONObject("data");

                JSONArray payouts = jsonObjectt.getJSONArray("payouts");
                for (int i = 0; i < payouts.length(); i++) {
                    JSONObject loansObj = payouts.getJSONObject(i);
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
                    map.put("order_id", loansObj.get("booking_ids").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("vendor_unique_number", loansObj.get("vendor_unique_number").toString());
                    map.put("created_datetime", loansObj.get("created_datetime").toString());
                    map.put("total_booking_count", loansObj.get("total_booking_count").toString());
                    map.put("payout_range", loansObj.get("payout_range").toString());
                    payoutlist.add(map);
                }
                if (payoutlist.isEmpty()) {
                    Rl_noLeads.setVisibility(View.VISIBLE);
                    Rl_leads.setVisibility(View.GONE);
                } else {
                    Rl_noLeads.setVisibility(View.GONE);
                    Rl_leads.setVisibility(View.VISIBLE);
                }
                OfferAdapter OfferAdapter = new OfferAdapter(payoutlist);
                recyPayout.setAdapter(OfferAdapter);
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void AlertBankDetails(PaymentInfoPopup paymentInfoPopup) {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_bank_details);
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

        TextView tvNamet,tvch_date,tvchaquedate,tvbankbranch,tvbankbran,tvbName,tvbankname,tvpayMethod;


        TextView cut = dialog.findViewById(R.id.cut);
        tvAccNot = dialog.findViewById(R.id.tvAccNot);
        tvAccNo1 = dialog.findViewById(R.id.tvAccNo1);
        tvUTRNumber = dialog.findViewById(R.id.tvUTRNumber);
        UtrNo = dialog.findViewById(R.id.UtrNo);
        tvch_date= dialog.findViewById(R.id.tvch_date);
        ivName = dialog.findViewById(R.id.ivName);
        tvNamet= dialog.findViewById(R.id.tvNamet);
        tvpayMethod= dialog.findViewById(R.id.tvpayMethod);
        tvchaquedate= dialog.findViewById(R.id.tvchaquedate);
        tvbankbranch= dialog.findViewById(R.id.tvbankbranch);
        tvbankbran= dialog.findViewById(R.id.tvbankbran);
        tvbName= dialog.findViewById(R.id.tvbName);
        tvbankname= dialog.findViewById(R.id.tvbankname);
        tvAccNo1.setText(paymentInfoPopup.getData().getModeOfPayment());
        UtrNo.setText(paymentInfoPopup.getData().getPaymentInfo().getRemark());


        if (paymentInfoPopup.getData().getCheckType().equalsIgnoreCase("cash")) {



        } else if (paymentInfoPopup.getData().getCheckType().equalsIgnoreCase("cheque")) {
//            tvNamet.setVisibility(View.VISIBLE);
           // tvUTRNumber.setVisibility(View.VISIBLE);

            tvch_date.setVisibility(View.VISIBLE);
            tvchaquedate.setVisibility(View.VISIBLE);
            tvbankbranch.setVisibility(View.VISIBLE);
            tvbankbran.setVisibility(View.VISIBLE);
            tvbankname.setVisibility(View.VISIBLE);
            tvbName.setVisibility(View.VISIBLE);


            tvch_date.setText("Cheque date");
            tvNamet.setText("Cheque number");
            tvbankbran.setText("Bank branch");
            tvbankname.setText("Bank name");

            tvUTRNumber.setText(paymentInfoPopup.getData().getPaymentInfo().getChequeNumber());

            tvchaquedate.setText(paymentInfoPopup.getData().getPaymentInfo().getChequeDate());
            tvbankbranch.setText(paymentInfoPopup.getData().getPaymentInfo().getBankBranch());
            tvbName.setText(paymentInfoPopup.getData().getPaymentInfo().getBankName());

        } else if (paymentInfoPopup.getData().getCheckType().equalsIgnoreCase("net-banking")) {
            tvpayMethod.setVisibility(View.VISIBLE);
            ivName.setVisibility(View.VISIBLE);
            tvNamet.setText("UTR Number ");
            tvAccNot.setText("Online ");

            tvUTRNumber.setText(paymentInfoPopup.getData().getPaymentInfo().getUtrNumber());
            Picasso.get().load(paymentInfoPopup.getData().getPaymentInfo().getPaymentScreenshot()).into(ivName);



        } else if (paymentInfoPopup.getData().getCheckType().equalsIgnoreCase("upi")) {
            tvAccNot.setText("Online ");
            tvNamet.setText("Refrence id ");
            ivName.setVisibility(View.VISIBLE);
            tvpayMethod.setVisibility(View.VISIBLE);

            tvUTRNumber.setText(paymentInfoPopup.getData().getPaymentInfo().getRefId());
            Picasso.get().load(paymentInfoPopup.getData().getPaymentInfo().getPaymentScreenshot()).into(ivName);


        } else if (paymentInfoPopup.getData().getCheckType().equalsIgnoreCase("paytm")) {
            tvAccNot.setText("Online ");
            tvNamet.setText("Order id ");
            ivName.setVisibility(View.VISIBLE);
            tvpayMethod.setVisibility(View.VISIBLE);

            tvUTRNumber.setText(paymentInfoPopup.getData().getPaymentInfo().getOrderId());
            Picasso.get().load(paymentInfoPopup.getData().getPaymentInfo().getPaymentScreenshot()).into(ivName);

        }

        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    public void Getvendorpaymentinfo(String id) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = "https://serviingo.com/api/vendor/view-payout/payment-info/" + id;
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
                JSONObject userJsonObject = response.getJSONObject("data");//addons

                String check_type = userJsonObject.getString("check_type");
                String mode_of_payment = userJsonObject.getString("mode_of_payment");
                JSONObject payment_info = userJsonObject.getJSONObject("payment_info");
                String remark = payment_info.getString("remark");
                String order_id = payment_info.getString("order_id");
                String payment_screenshot = payment_info.getString("payment_screenshot");

                tvAccNot.setText(check_type);
                tvAccNo1.setText(mode_of_payment);
                tvUTRNumber.setText(order_id);
                UtrNo.setText(remark);
                Picasso.get().load(payment_screenshot).into(ivName);


            } else {

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    @Override
    public void onPaymentInfoPopupError(String message) {
        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakSuccess();
    }

    @Override
    public void onPaymentInfoPopupSuccess(PaymentInfoPopup response, String message) {
        Log.e("onPaymentSuccess", response.getData().toString());
        Log.e("onPaymentInfoPopup", response.getData().getPaymentInfo().toString());
      //  Toast.makeText(getContext(), response.getMessage() + "", Toast.LENGTH_SHORT).show();

        if (message.equalsIgnoreCase("ok")) {

            AlertBankDetails(response);

        }
    }

    @Override
    public void showHideProgress(boolean isShow) {


        if (isShow) {
            AppUtils.hideSoftKeyboard(mActivity);
            AppUtils.showRequestDialog(mActivity);

        } else {
            AppUtils.hideDialog();

        }


    }

    @Override
    public void onPaymentInfoPopupFailure(Throwable t) {


        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakSuccess();


    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_vendor_commissions, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.tvAccNo.setText(data.get(position).get("created_datetime"));
            holder.tvAccNo1.setText(data.get(position).get("payout_range"));
            holder.etDisbursed.setText(data.get(position).get("total_booking_count"));
            holder.tvAccountName1.setText(data.get(position).get("vendor_unique_number"));
            holder.tvAccountName.setText("\u20B9"+data.get(position).get("total_amount"));
            holder.tvIFSCCODE1.setText("\u20B9"+data.get(position).get("vendor_comession"));
            holder.tvloanadjustment.setText("\u20B9"+data.get(position).get("loan_adjustment"));

            holder.tvPayableAmount.setText("\u20B9"+data.get(position).get("release_amount"));
            holder.tvtvOrderNoans.setText(data.get(position).get("order_id"));

            holder.status.setText(data.get(position).get("status"));


            holder.llpayoutPaymentdetails.setVisibility(View.GONE);
            holder.llpayoutinvoice.setVisibility(View.GONE);
            if (click) {
                holder.llpayoutPaymentdetails.setVisibility(View.VISIBLE);
                holder.llpayoutinvoice.setVisibility(View.VISIBLE);
                holder.llpayoutPaymentdetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Getvendorpaymentinfo(data.get(position).get("id"));

                     Toast.makeText(getContext(), data.get(position).get("id") + "", Toast.LENGTH_SHORT).show();

                        presenter.PaymentInfoPopup(getContext(), data.get(position).get("id"));

                    }
                });
                holder.llpayoutinvoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pref.set("payoutId", data.get(position).get("id")).commit();
                        ((Container) mActivity).displayView(6);
                    }
                });
            } else {
                holder.llpayoutPaymentdetails.setVisibility(View.GONE);
                holder.llpayoutinvoice.setVisibility(View.GONE);
            }

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, tvAccNo1, status, etDisbursed, tvAccountName1, tvAccountName, tvIFSCCODE1, tvloanadjustment, tvPayableAmount, tvtvOrderNoans;
        LinearLayout llpayoutPaymentdetails, llpayoutinvoice;

        public OfferHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvIFSCCODE1 = itemView.findViewById(R.id.tvIFSCCODE1);
            tvloanadjustment = itemView.findViewById(R.id.tvloanadjustment);
            tvPayableAmount = itemView.findViewById(R.id.tvPayableAmount);
            tvtvOrderNoans = itemView.findViewById(R.id.tvtvOrderNoans);
            status = itemView.findViewById(R.id.status);
            llpayoutPaymentdetails = itemView.findViewById(R.id.llpayoutPaymentdetails);
            llpayoutinvoice = itemView.findViewById(R.id.llpayoutinvoice);
        }
    }
}
