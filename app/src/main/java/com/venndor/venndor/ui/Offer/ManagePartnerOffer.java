package com.venndor.venndor.ui.Offer;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Support.ViewPostComplaints;
import com.venndor.venndor.ui.Wallet.AddMoney;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;

public class ManagePartnerOffer extends BaseFragment{
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    OfferAdapter offerAdapter;
    View rootView;
    RelativeLayout Rl_norecord;
    LinearLayout lldetails  ;
    Preferences pref;
    ArrayList<HashMap<String, String>> offerList = new ArrayList();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.offer_activity, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref=new Preferences(mActivity);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        Rl_norecord = rootView.findViewById(R.id.Rl_norecord);
        lldetails   = rootView.findViewById(R.id.lldetails  );
        gridLayoutManager = new GridLayoutManager(mActivity,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        getOffers();
    }

    public void getOffers() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getvendormyoffers;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsemyTicketsJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsemyTicketsJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                offerList.clear();
                JSONObject jsonObjectt = response.getJSONObject("data");


                JSONArray jsonArray = jsonObjectt.getJSONArray("offers");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("image", block_object.get("image").toString());
                    map.put("status", block_object.get("status").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());

                    offerList.add(map);
                }
                offerAdapter = new OfferAdapter(offerList);
                recyclerView.setAdapter(offerAdapter);
                if (offerList.isEmpty()) {
                    Rl_norecord.setVisibility(View.VISIBLE);
                    lldetails   .setVisibility(View.GONE);
                } else {
                    Rl_norecord.setVisibility(View.GONE);
                    lldetails.setVisibility(View.VISIBLE);
                }
            } else {
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_offer, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            Picasso.get().load(data.get(position).get("image")).into(holder.ivOffer);
            holder.tvid.setText(data.get(position).get("name"));
            holder.ivOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertImage(data.get(position).get("image"),data.get(position).get("name"));
                }
            });
        }

        public int getItemCount() {
            return data.size();
        }
    }


    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvid, headingtop, heading, tvModel, tvView;
        ImageView ivOffer,icon;

        public OfferHolder(View itemView) {
            super(itemView);

            ivOffer = itemView.findViewById(R.id.ivOffer);
            tvid = itemView.findViewById(R.id.tvid);

        }
    }

    private void AlertImage(String image, String s) {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.inflate_offer_image);
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


        ImageView imageview = dialog.findViewById(R.id.image);
        TextView tvclose = dialog.findViewById(R.id.tvclose);
        Picasso.get().load(image).into(imageview);
        tvclose.setText(s);
        tvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }
}
