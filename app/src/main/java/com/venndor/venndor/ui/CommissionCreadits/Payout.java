package com.venndor.venndor.ui.CommissionCreadits;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Payout extends BaseFragment implements View.OnClickListener ,  PaymentInfoPopupPresenter.PaymentInfoPopupView {

    View rootView;
    RecyclerView recy;
    Preferences pref;
    LinearLayoutManager layoutManager;
    ArrayList<HashMap<String, String>> invoicelist = new ArrayList<>();
    String unique_id;
    TextView pname, paddress, pContactNo, pEmailId, pServeCommperod, pMethod, pId,
            tvaddress,tvContact,tvGSTNumber,tvEmailId;
    TextView tvtotallPrice,priceDiscountt,tvGSsT,toBePaid;

    TextView tvNamet,tvch_date,tvchaquedate,tvbankbranch,tvbankbran,tvbName,tvbankname,tvpayMethod;
    TextView tvAccNot, tvAccNo1, tvUTRNumber, UtrNo,cut;
    ImageView ivName;


    PaymentInfoPopupPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_payout, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recy = rootView.findViewById(R.id.recy);
        pname = rootView.findViewById(R.id.pname);
        paddress = rootView.findViewById(R.id.paddress);
        pContactNo = rootView.findViewById(R.id.pContactNo);
        pEmailId = rootView.findViewById(R.id.pEmailId);
        pServeCommperod = rootView.findViewById(R.id.pServeCommperod);
        pMethod = rootView.findViewById(R.id.pMethod);

        tvtotallPrice  = rootView.findViewById(R.id.tvtotallPrice);
        priceDiscountt = rootView.findViewById(R.id.priceDiscountt);
        tvGSsT         = rootView.findViewById(R.id.tvGSsT);
        toBePaid       = rootView.findViewById(R.id.toBePaid);

        pId = rootView.findViewById(R.id.pId);
        tvaddress=rootView.findViewById(R.id.tvName);
        tvContact=rootView.findViewById(R.id.tvContact);
        tvEmailId=rootView.findViewById(R.id.tvEmailId);
        tvGSTNumber=rootView.findViewById(R.id.tvGSTNumber);

        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recy.setLayoutManager(layoutManager);
        presenter=new PaymentInfoPopupPresenter(this);
        String id = pref.get("payoutId");
        presenter.PaymentInfoPopup(getContext(),id);
       Toast.makeText(getContext(),id+"", Toast.LENGTH_SHORT).show();
        Getvendorinvoice(id);
    }

    @Override
    public void onClick(View v) {
    }

    public void Getvendorinvoice(String id) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = "https://serviingo.com/api/vendor/payout/invoice/" + id;
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
                invoicelist.clear();
                String message = response.getString("message");
                JSONObject userJsonObject = response.getJSONObject("data");//addons
                String payout_range = userJsonObject.getString("payout_range");
                String payment_mode = userJsonObject.getString("payment_mode");


                String total_order_amount = userJsonObject.getString("total_order_amount");
                String comission_amount = userJsonObject.getString("comission_amount");
                String loan_adjustment = userJsonObject.getString("loan_adjustment");
                String release_amount = userJsonObject.getString("release_amount");

                JSONObject payoutObject = userJsonObject.getJSONObject("payout");//addons
                String payoutunique_id = payoutObject.getString("unique_id");
                pId.setText(payoutunique_id);
                JSONObject vendor_infoObject = userJsonObject.getJSONObject("vendor_info");//addons

                String first_name = vendor_infoObject.getString("first_name");
                String last_name = vendor_infoObject.getString("last_name");
                String address = vendor_infoObject.getString("address");

                String email = vendor_infoObject.getString("email");
                String mobile_number = vendor_infoObject.getString("mobile_number");

                 unique_id = vendor_infoObject.getString("unique_id");


                /*JSONObject payment_info = vendor_infoObject.getJSONObject("vendor_info");
                String unique_id = vendor_infoObject.getString("unique_id");
                String id = vendor_infoObject.getString("id");*/

                pname.setText(first_name+""+last_name);
                paddress.setText(address);
                pContactNo.setText(mobile_number);
                pEmailId.setText(email);
                pServeCommperod.setText(payout_range);
                pMethod.setText(payment_mode);

                tvtotallPrice.setText("\u20B9"+total_order_amount);
                        priceDiscountt.setText("\u20B9"+comission_amount);
                tvGSsT.setText("\u20B9"+loan_adjustment);
                        toBePaid.setText("\u20B9"+release_amount);

                JSONArray payouts = userJsonObject.getJSONArray("comissions");
                for (int i = 0; i < payouts.length(); i++) {
                    JSONObject loansObj = payouts.getJSONObject(i);
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
                    map.put("get_some_order_info", loansObj.get("get_some_order_info").toString());
                    invoicelist.add(map);
                }
                Adapter commissionAdapter = new Adapter(invoicelist);
                recy.setAdapter(commissionAdapter);




                JSONObject company_details = userJsonObject.getJSONObject("company_details");
                tvaddress.setText(company_details.getString("company_address"));
                tvContact.setText("Contact No:"+company_details.getString("company_mobile_number"));
                tvGSTNumber.setText("GST: "+company_details.getString("company_gst_number"));
                tvEmailId.setText("Email Id:"+company_details.getString("company_email"));


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
    public void onPaymentInfoPopupSuccess(PaymentInfoPopup paymentInfoPopup, String message) {
        Log.e("onPaymentSuccess", paymentInfoPopup.getData().toString());
        Log.e("onPaymentInfoPopup", paymentInfoPopup.getData().getPaymentInfo().toString());
        //  Toast.makeText(getContext(), response.getMessage() + "", Toast.LENGTH_SHORT).show();

        if (message.equalsIgnoreCase("ok")) {



           cut = rootView.findViewById(R.id.cut);
            tvAccNot = rootView.findViewById(R.id.tvAccNot);
            tvAccNo1 = rootView.findViewById(R.id.tvAccNo1);
            tvUTRNumber = rootView.findViewById(R.id.tvUTRNumber);
            UtrNo = rootView.findViewById(R.id.UtrNo);
            tvch_date= rootView.findViewById(R.id.tvch_date);
            ivName = rootView.findViewById(R.id.ivName);
            tvNamet= rootView.findViewById(R.id.tvNamet);
            tvpayMethod= rootView.findViewById(R.id.tvpayMethod);
            tvchaquedate= rootView.findViewById(R.id.tvchaquedate);
            tvbankbranch= rootView.findViewById(R.id.tvbankbranch);
            tvbankbran= rootView.findViewById(R.id.tvbankbran);
            tvbName= rootView.findViewById(R.id.tvbName);
            tvbankname= rootView.findViewById(R.id.tvbankname);


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
            

            //AlertBankDetails(response);

        }
    }

    @Override
    public void showHideProgress(boolean isShow) {

    }

    @Override
    public void onPaymentInfoPopupFailure(Throwable t) {


        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakSuccess();


    }

    public class Adapter extends RecyclerView.Adapter<Holder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public Adapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payouts_inflate, parent, false);
            return new Holder(itemView);
        }

        public void onBindViewHolder(Holder holder, final int position) {
            holder.tv2AccNo.setText(unique_id);
            holder.tvAccNo1.setText(data.get(position).get("order_id"));
            holder.etDisbursed.setText(" \u20B9"+data.get(position).get("total_amount"));
            holder.tvAccountName1.setText(" \u20B9"+data.get(position).get("vendor_comession")+"( "+data.get(position).get("comission_percent")+" %) ");

            try {
                JSONObject orderInfo = new JSONObject(data.get(position).get("get_some_order_info"));

                String orderno = orderInfo.getString("order_number");

                holder.tvAccNo1.setText(orderno);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        TextView tv2AccNo, tvAccNo1, etDisbursed, tvAccountName1;

        public Holder(View itemView) {
            super(itemView);
            tv2AccNo = itemView.findViewById(R.id.tv2AccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
        }
    }
}
