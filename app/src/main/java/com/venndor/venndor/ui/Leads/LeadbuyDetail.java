package com.venndor.venndor.ui.Leads;

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

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;

public class LeadbuyDetail extends BaseFragment  {

    View rootView;
    Preferences pref;
    TextView tvAccNo, tvAccNo1, tvAccountName, tvAccountName1, tvsuccess;
    TextView tvAccounta, tvAccountNamea, tvAccNo1a,tvLead, tvAccNoa, tvAccountName1a, tvAcc, tvPincode, tvorder, tvIFSC1;
    LinearLayout llPaystatus, llLeadStatus;

    ArrayList<HashMap<String, String>> includeServiceList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.leadbuydetail, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvAccNo = rootView.findViewById(R.id.tvAccNo);
        tvAccNo1 = rootView.findViewById(R.id.tvAccNo1);
        tvorder = rootView.findViewById(R.id.tvorder);
        tvAccountName = rootView.findViewById(R.id.tvAccountName);
        tvAccountName1 = rootView.findViewById(R.id.tvAccountName1);
        tvIFSC1 = rootView.findViewById(R.id.tvIFSC1);
        // tvsuccess=rootView.findViewById(R.id.tvsuccess);
        llLeadStatus = rootView.findViewById(R.id.llLeadStatus);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        tvAccounta = rootView.findViewById(R.id.tvAccounta);
        tvAccountNamea = rootView.findViewById(R.id.tvAccountNamea);
        tvAccNo1a = rootView.findViewById(R.id.tvAccNo1a);
        tvAccNoa = rootView.findViewById(R.id.tvAccNoa);
        tvLead = rootView.findViewById(R.id.tvLead);
        tvAccountName1a = rootView.findViewById(R.id.tvAccountName1a);
        tvPincode = rootView.findViewById(R.id.tvPincode);
        tvAcc = rootView.findViewById(R.id.tvAcc);
        /*  llPaystatus=rootView.findViewById(R.id.llPaystatus);*/
        getDetail();
        tvAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String OrderId = pref.get("order_id");
                pref.set("MyOrderId",OrderId).commit();*/
                ((Container) mActivity).displayView(14);
            }
        });
    }



    public void getDetail() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        String OrderId = pref.get("order_id");
        Log.v("ddurl", token);
        String url = AppUrls.getLeadsDetail + OrderId;
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
            includeServiceList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");


                JSONObject jsonObject = response.getJSONObject("data");
                JSONObject jsonObjectt = jsonObject.getJSONObject("package");
                String package_id = jsonObjectt.getString("package_id");
                String name = jsonObjectt.getString("name");
                String quantity = jsonObjectt.getString("quantity");
                String amount = jsonObjectt.getString("amount");
                String discount = jsonObjectt.getString("discount");
                String discount_amount = jsonObjectt.getString("discount_amount");

                JSONObject jsonObject1 = jsonObject.getJSONObject("order_info");
                String id = jsonObject1.getString("id");
                String user_id = jsonObject1.getString("user_id");
                String vendor_id = jsonObject1.getString("vendor_id");
                String order_number = jsonObject1.getString("order_number");
                String new_lead_amount = jsonObject1.getString("new_lead_amount");
                tvLead.setText(" ₹ " +new_lead_amount);
                if (!order_number.equalsIgnoreCase("null")) {
                    tvAccNo.setText(order_number);
                } else {
                    tvAccNo.setText("N/A");
                }
                String sub_amount = jsonObject1.getString("sub_amount");
                String discount_amount1 = jsonObject1.getString("discount_amount");
                String membership_discount = jsonObject1.getString("membership_discount");
                String vendor_service_amount = jsonObject1.getString("vendor_service_amount");
                String pincode = jsonObject1.getString("pincode");
                String order_status = jsonObject1.getString("order_status");

                if (!order_status.equalsIgnoreCase("null")) {
                    tvorder.setText(order_status);
                } else {
                    tvorder.setText("N/A");
                }
                if (order_status.equalsIgnoreCase("Confirmed")) {
                    llLeadStatus.setBackgroundResource(R.drawable.green_bg);
                } else if (order_status.equalsIgnoreCase("Processing")) {
                    llLeadStatus.setBackgroundResource(R.drawable.green_bg);
                } else if (order_status.equalsIgnoreCase("Completed")) {
                    llLeadStatus.setBackgroundResource(R.drawable.green_bg);
                } else {
                    llLeadStatus.setBackgroundResource(R.drawable.red_button);
                }
                String name1 = jsonObject1.getString("name");
                String total_amount = jsonObject1.getString("total_amount");


                if (!name1.equalsIgnoreCase("null")) {
                    tvAccounta.setText(name1);
                } else {
                    tvAccounta.setText("N/A");
                }
                if (!total_amount.equalsIgnoreCase("null")) {
                    tvAccNo1.setText(" ₹ " + total_amount);
                } else {
                    tvAccNo1.setText("N/A");
                }

                String email = jsonObject1.getString("email");
                String mobile_number = jsonObject1.getString("mobile_number");
                String address = jsonObject1.getString("address");
                String landmark = jsonObject1.getString("landmark");

                if (!address.equalsIgnoreCase("null")) {
                    tvAccNoa.setText(address);
                } else {
                    tvAccNoa.setText("N/A");
                }
                String payment_method = jsonObject1.getString("payment_method");

                if (!payment_method.equalsIgnoreCase("null")) {
                    tvAccountName1.setText(payment_method);
                } else {
                    tvAccountName1.setText("N/A");
                }
                String payment_status = jsonObject1.getString("payment_status");

                String serve_date = jsonObject1.getString("serve_date");
                String serve_time = jsonObject1.getString("serve_time");
                String serving_datetime = jsonObject1.getString("serving_datetime");


                JSONObject jsonObject2 = jsonObject1.getJSONObject("package_information");
                JSONObject packagejsonObj = jsonObject2.getJSONObject("package");
                String packagename = packagejsonObj.getString("name");
                if (!packagename.equalsIgnoreCase("null")) {
                    tvIFSC1.setText(packagename);
                } else {
                    tvIFSC1.setText("N/A");
                }
                if (!serve_date.equalsIgnoreCase("null")) {
                    tvAccountName.setText(setDateTimeValues(serving_datetime) );
                } else {
                    tvAccountName.setText("N/A");
                }
                if (!email.equalsIgnoreCase("null")) {
                    tvAccNo1a.setText(email);
                } else {
                    tvAccNo1a.setText("N/A");
                }
                if (!mobile_number.equalsIgnoreCase("null")) {
                    tvAccountNamea.setText(mobile_number);
                } else {
                    tvAccountNamea.setText("N/A");
                }

                if (!landmark.equalsIgnoreCase("null")) {
                    tvAccountName1a.setText(landmark);
                } else {
                    tvAccountName1a.setText("N/A");
                }

                if (!pincode.equalsIgnoreCase("null")) {
                    tvPincode.setText(pincode);
                } else {
                    tvPincode.setText("N/A");
                }


            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
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
