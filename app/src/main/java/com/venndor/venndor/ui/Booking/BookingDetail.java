package com.venndor.venndor.ui.Booking;

import android.content.Intent;
import android.net.Uri;
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

public class BookingDetail extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recybooking,rvAddons;
    LinearLayoutManager layoutManager,AddonslayoutManager;
    String action_check;
    Preferences pref;
    String mobile_number="";
    TextView tvDate,tvcoupon,CouponDisc,tvcity,quantity,tvname,tvAccount,jobsum,tvOrderid,tvrs;
    ImageView ivCall,ivHelp;
    ArrayList<HashMap<String, String>> querylist = new ArrayList<>();
    ArrayList<HashMap<String, String>> addonslist = new ArrayList<>();

    TextView tvAccount1,tvAccNo1,tvAccNo,tvName1,tvAccountName1,etMembership,tvIFSC1,loan,tvkey,tvdataaa,tvdaaaa,tvaaa,note;
   TextView tv,ds,tvcommissionAmount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bookingdetailmonday, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);

        recybooking = rootView.findViewById(R.id.recybooking);
        rvAddons = rootView.findViewById(R.id.rvAddons);
        tvDate = rootView.findViewById(R.id.tvDate);
        ivCall = rootView.findViewById(R.id.ivCall);
        tvAccNo = rootView.findViewById(R.id.tvAccNo);
        quantity = rootView.findViewById(R.id.quantity);
        tvcity = rootView.findViewById(R.id.tvcity);
        ivHelp = rootView.findViewById(R.id.ivHelp);
        CouponDisc = rootView.findViewById(R.id.CouponDisc);
        tvcoupon = rootView.findViewById(R.id.tvcoupon);
        tvname = rootView.findViewById(R.id.name);
        tvOrderid = rootView.findViewById(R.id.tvOrderid);
        tvrs = rootView.findViewById(R.id.tvrs);
        tvAccount = rootView.findViewById(R.id.tvaAccount);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        tvAccount1     = rootView.findViewById(R.id.tvAccount1);
        tvAccNo1       = rootView.findViewById(R.id.tvAccNo1);
        tvName1        = rootView.findViewById(R.id.tvName1);
        tvAccountName1 = rootView.findViewById(R.id.tvAccountName1);
        tvIFSC1        = rootView.findViewById(R.id.tvIFSC1);
        loan           = rootView.findViewById(R.id.loan);
        etMembership           = rootView.findViewById(R.id.etMembership);
        tvkey           = rootView.findViewById(R.id.tvkey);
        tvdataaa       = rootView.findViewById(R.id.tvdataaa);
        tvdaaaa        = rootView.findViewById(R.id.tvdaaaa);
        tvaaa          = rootView.findViewById(R.id.tvaaa);

        note          = rootView.findViewById(R.id.note);
        tv                        = rootView.findViewById(R.id.tv);
        ds                       = rootView.findViewById(R.id.ds);
        tvcommissionAmount          = rootView.findViewById(R.id.tvcommissionAmount);


        jobsum = rootView.findViewById(R.id.jobsum);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recybooking.setLayoutManager(layoutManager);
        AddonslayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvAddons.setLayoutManager(AddonslayoutManager);
        getBookingdetail();
        PostSupportQuestions("order");
        ivHelp.setOnClickListener(this);
        ivCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.ivHelp:
                ((Container) mActivity).displayView(18);
                break;
            case R.id.ivCall:
                call();
                break;
        }
    }

    public void getBookingdetail() {



        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        String MyOrderId = pref.get("order_id");
        Log.v("ddurl", token);
        String url = AppUrls.Getvendororderdetail + MyOrderId;
        Log.v("data_url", url);

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegetBookingdetailJsonResponse(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsegetBookingdetailJsonResponse(JSONObject response) {
        try {
            Log.v("resp", response.toString());
            AppUtils.hideDialog();
            addonslist.clear();
            JSONObject dataJsonObject = response.getJSONObject("data");
            String service_amount = dataJsonObject.getString("service_amount");
            int addon_amount = Integer.parseInt(dataJsonObject.getString("addon_amount"));
            String sub_total = dataJsonObject.getString("sub_total");
            String service_discount = dataJsonObject.getString("service_discount");
            String membership_discount = dataJsonObject.getString("membership_discount");
            String total_discount = dataJsonObject.getString("total_discount");
            String membership_fees = dataJsonObject.getString("membership_fees");
            String hygiene_fees = dataJsonObject.getString("hygiene_fees");
            String billing_amount = dataJsonObject.getString("billing_amount");
            String vendor_billing_amount = dataJsonObject.getString("vendor_billing_amount");
            String vendor_comission = dataJsonObject.getString("vendor_comission");
            String vendor_comission_percent = dataJsonObject.getString("vendor_comission_percent");
            String invoice_url = dataJsonObject.getString("invoice_url");

            tv.setText(" ₹ "+vendor_billing_amount);

            tvcommissionAmount.setText(" ₹ "+vendor_comission+" ( "+vendor_comission_percent+" % ) ");
            tvAccount1.setText(" ₹ "+service_amount);

            if (addon_amount > 0) {
                tvAccNo1.setText(" \u20B9 " +addon_amount);

            } else {
                tvAccNo.setVisibility(View.GONE);
                tvAccNo1.setVisibility(View.GONE);

            }
            tvAccNo1.setText(" ₹ "+addon_amount);
            tvName1.setText(" ₹ "+sub_total);


            tvIFSC1.setVisibility(View.GONE);
            etMembership.setVisibility(View.GONE);
            if (membership_discount.equalsIgnoreCase("0")) {
                tvIFSC1.setVisibility(View.GONE);
                etMembership.setVisibility(View.GONE);
            }else {
                tvIFSC1.setVisibility(View.VISIBLE);
                etMembership.setVisibility(View.VISIBLE);
                tvIFSC1.setText(" ₹ "+membership_discount);
            }

            tvdataaa.setVisibility(View.GONE);
            tvkey.setVisibility(View.GONE);
            if (membership_fees.equalsIgnoreCase("0")) {
                tvdataaa.setVisibility(View.GONE);
                tvkey.setVisibility(View.GONE);
            }else {

                tvkey.setVisibility(View.VISIBLE);
                tvdataaa.setVisibility(View.VISIBLE);
                tvdataaa.setText(" ₹ "+membership_fees);
            }
            tvdaaaa.setText(" ₹ "+hygiene_fees);
            tvaaa.setText(" ₹ "+billing_amount);
            note.setText("Note : Both Partner Commission & Buy Lead Amount are Calculated on Total Lead Amount ( ₹ "+vendor_billing_amount+")");


            JSONObject order_infoObject = dataJsonObject.getJSONObject("order_info");
            String package_idd = order_infoObject.getString("package_id");
            String user_id = order_infoObject.getString("user_id");
            String vendor_id = order_infoObject.getString("vendor_id");
            String id = order_infoObject.getString("id");
            String addonss = order_infoObject.getString("addons");

            String buy_lead_amount = order_infoObject.getString("buy_lead_amount");
            String buy_lead_amountpercnatge = order_infoObject.getString("buy_lead_percent");

            Log.v("hd",buy_lead_amount);
            ds.setText(" ₹ "+buy_lead_amount+"( "+ buy_lead_amountpercnatge+ "%)");
            JSONArray jsonArray=order_infoObject.getJSONArray("addons");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jObj = jsonArray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", jObj.get("id").toString());
                map.put("name", jObj.get("name").toString());
                map.put("amount", jObj.get("amount").toString());
                map.put("discount", jObj.get("discount").toString());
                map.put("quantity", jObj.get("quantity").toString());
                map.put("after_discount", jObj.get("after_discount").toString());
                addonslist.add(map);
            }

            if (addonslist.size() > 0) {
                AddonsAdapter addonsAdapter=new  AddonsAdapter(addonslist);
                rvAddons.setAdapter(addonsAdapter);
            }


            String pincode = order_infoObject.getString("pincode");
            String order_number = order_infoObject.getString("order_number");
            tvOrderid.setText(order_number);
            String cart_details = order_infoObject.getString("cart_details");
            String sub_amount = order_infoObject.getString("sub_amount");
            String discount_amountt = order_infoObject.getString("discount_amount");
            String membership_discouunt = order_infoObject.getString("membership_discount");
            String total_amount = order_infoObject.getString("total_amount");
            String vendor_service_amount = order_infoObject.getString("vendor_service_amount");
            String amount_without_tax = order_infoObject.getString("amount_without_tax");
            String tax_name = order_infoObject.getString("tax_name");
            String tax_amount = order_infoObject.getString("tax_amount");
            String hygiene_feess = order_infoObject.getString("hygiene_fees");
            String order_status = order_infoObject.getString("order_status");
            String vendor_status = order_infoObject.getString("vendor_status");
            String namee = order_infoObject.getString("name");
            tvname.setText(namee);
            String address = order_infoObject.getString("address");
            tvAccount.setText(address);
            String landmark = order_infoObject.getString("landmark");
            mobile_number = order_infoObject.getString("mobile_number");
            String state_id = order_infoObject.getString("state_id");

            String cancellation_reasons = order_infoObject.getString("cancellation_reasons");
            String support_question_id = order_infoObject.getString("support_question_id");
            String cancellation_remark = order_infoObject.getString("cancellation_remark");
            String cancelled_by = order_infoObject.getString("cancelled_by");
            String payment_method = order_infoObject.getString("payment_method");
            String payment_status = order_infoObject.getString("payment_status");
            String assigned_status = order_infoObject.getString("assigned_status");
            String remark = order_infoObject.getString("remark");
            String membership_id = order_infoObject.getString("membership_id");
            String membership_cost = order_infoObject.getString("membership_cost");
            String cgst = order_infoObject.getString("cgst");
            String sgst = order_infoObject.getString("sgst");
            String igst = order_infoObject.getString("igst");
            String device_type = order_infoObject.getString("device_type");
            String system_ip = order_infoObject.getString("system_ip");
            String device_info = order_infoObject.getString("device_info");
            String serve_date = order_infoObject.getString("serve_date");
            tvDate.setText(setDateTimeValues(serve_date));

            String serve_time = order_infoObject.getString("serve_time");
            String accept_by_vendor = order_infoObject.getString("accept_by_vendor");
            String reject_reason_by_vendor = order_infoObject.getString("reject_reason_by_vendor");
            String service_start = order_infoObject.getString("service_start");
            String rejection_accept = order_infoObject.getString("rejection_accept");
            String tax_percent = order_infoObject.getString("tax_percent");
            String v_buy_lead_amount = order_infoObject.getString("v_buy_lead_amount");
            String buy_lead_percent = order_infoObject.getString("buy_lead_percent");
            String order_device_type = order_infoObject.getString("order_device_type");
            String vendor_attachment = order_infoObject.getString("vendor_attachment");
            String coupon_discount_amount = order_infoObject.getString("coupon_discount_amount");
            String created_at = order_infoObject.getString("created_at");
            String updated_at = order_infoObject.getString("updated_at");




            JSONObject get_cityobject = order_infoObject.getJSONObject("get_city");
            String cityname = get_cityobject.getString("name");
            tvcity.setText(cityname);

            JSONObject package_information_obj = order_infoObject.getJSONObject("package_information");
            JSONObject packageobject = package_information_obj.getJSONObject("package");
            String package_id = packageobject.getString("package_id");
            String quantityy = packageobject.getString("quantity");
            //  quantity.setText();
            String amount = packageobject.getString("amount");
            String nameee = packageobject.getString("name");
            jobsum.setText(nameee+"("+quantityy+")");
            String discounnt = packageobject.getString("discount");
            String discount_amount = packageobject.getString("discount_amount");
            tvrs.setText(" ₹ "+discount_amount);





            String coupon_discount= String.valueOf(coupon_discount_amount);

            loan.setText(" ₹ "+total_discount);

            tvAccountName1.setText(" ₹ "+service_discount);

            CouponDisc.setVisibility(View.VISIBLE);
            tvcoupon.setVisibility(View.VISIBLE);
            if(!coupon_discount_amount.equalsIgnoreCase("null")){
                CouponDisc.setText(" ₹ "+ coupon_discount_amount);
                int servdiscount= Integer.parseInt(service_discount);
                int coupon_amount= Integer.parseInt(coupon_discount_amount);
                if(servdiscount>0 || coupon_amount>0) {

                    float amounntloan = servdiscount + coupon_amount;
                    loan.setText(" ₹ " + amounntloan);
                }

            } else {
                CouponDisc.setVisibility(View.GONE);
                tvcoupon.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+mobile_number));
        startActivity(intent);

    }

    public void PostSupportQuestions(String slug) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostvendorsupportQuestions;
        Log.v("data_url", url);

        String token = pref.get("token");
        Log.v("ddurl", token);
        AndroidNetworking.post(url)
                .addBodyParameter("model", slug)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseqyeryResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parseqyeryResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            querylist.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");

                JSONArray histories = response.getJSONArray("data");
                for (int i = 0; i < histories.length(); i++) {
                    JSONObject loansObj = histories.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("user_type", loansObj.get("user_type").toString());
                    map.put("models", loansObj.get("models").toString());
                    map.put("queries", loansObj.get("queries").toString());
                    map.put("status", loansObj.get("status").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("updated_at", loansObj.get("updated_at").toString());

                    querylist.add(map);
                }
                OfferAdapter offerAdapter = new OfferAdapter(querylist);
                recybooking.setAdapter(offerAdapter);
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_detail_booking, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.tvHeading.setText(data.get(position).get("queries"));
            holder.ivIamge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pref.set("id", data.get(position).get("id")).commit();
                    pref.set("models", data.get(position).get("models")).commit();
                    pref.set("queries", data.get(position).get("queries")).commit();
                    ((Container) mActivity).displayView(16);
                }
            });

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvHeading;
        ImageView ivIamge;

        public OfferHolder(View itemView) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.tvHeading);
            ivIamge = itemView.findViewById(R.id.ivIamge);
        }
    }

    public class AddonsAdapter extends RecyclerView.Adapter<addonsHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public AddonsAdapter (ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public addonsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_addons, parent, false);
            return new addonsHolder(itemView);
        }

        public void onBindViewHolder(addonsHolder holder, final int position) {

            holder.tvHeading.setText(data.get(position).get("name")+"("+data.get(position).get("quantity")+")");

            holder.tvSecurityAmount.setText(" ₹ "+data.get(position).get("after_discount"));

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class addonsHolder extends RecyclerView.ViewHolder {
        TextView tvHeading,tvSecurityquantity,tvSecurityAmount;


        public addonsHolder(View itemView) {
            super(itemView);
            tvHeading = itemView.findViewById(R.id.tvSecurityFee);
            tvSecurityquantity = itemView.findViewById(R.id.tvSecurityquantity);
            tvSecurityAmount = itemView.findViewById(R.id.tvSecurityAmount);
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
