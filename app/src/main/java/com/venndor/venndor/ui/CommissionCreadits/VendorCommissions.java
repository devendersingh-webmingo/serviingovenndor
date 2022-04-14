package com.venndor.venndor.ui.CommissionCreadits;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Booking.MyBooking;
import com.venndor.venndor.ui.Dashboard.dashboard;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class VendorCommissions extends BaseFragment implements View.OnClickListener{
    RecyclerView recyloanPayhistory, recyclerviewtranscation;
    LinearLayoutManager layoutManager, layoutrecytranscationManager;

    OfferAdapter OfferAdapter;
    View rootView;
    Preferences pref;
    ArrayList<HashMap<String, String>> commissionList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inflate_vendor_commissions, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref=new Preferences(mActivity);
        recyloanPayhistory = rootView.findViewById(R.id.recyclerviewtab);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyloanPayhistory.setLayoutManager(layoutManager);
        OfferAdapter = new OfferAdapter();
        recyloanPayhistory.setAdapter(OfferAdapter);
    }

    @Override
    public void onClick(View v) {

    }
    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();
        /*   public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
               data = favList;
           }*/
        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_tab, parent, false);
            return new OfferHolder(itemView);
        }
        public void onBindViewHolder(OfferHolder holder, final int position) {
            if (position == 0) {
                holder.tvtab.setText("Today");

            } else if (position == 1) {
                holder.tvtab.setText("Yesterday");

            } else if (position == 2) {
                holder.tvtab.setText("Week");

            } else if (position == 3) {
                holder.tvtab.setText("Month");

            }
            holder.tvtab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvtab.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));

                }
            });

        }

        public int getItemCount() {
            return 3;
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvtab;

        public OfferHolder(View itemView) {
            super(itemView);
            tvtab = itemView.findViewById(R.id.tvtab);
        }
    }
    public void Getvendorcomissions(){
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Getvendorcomissions;
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
                        parseGetvendorcomissionsJsonResponse(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }
    private void parseGetvendorcomissionsJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject dataJsonObject = response.getJSONObject("data");
            commissionList.clear();
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
