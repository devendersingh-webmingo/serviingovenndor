package com.venndor.venndor.ui.Support;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;

public class ViewPostComplaints extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    OfferAdapter offerAdapter;
    Preferences pref;
    ArrayList<HashMap<String, String>> ticketList = new ArrayList();
RelativeLayout    Rl_norecord;
    LinearLayout lldetails  ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_post_details, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recyclerView = rootView.findViewById(R.id.recyclerview);
       Rl_norecord = rootView.findViewById(R.id.Rl_norecord);
       lldetails   = rootView.findViewById(R.id.lldetails  );
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        rootView.findViewById(R.id.movetoHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container) mActivity).displayView(15);

            }
        });
        getTickets();
    }

    @Override
    public void onClick(View v) {

    }

    public void getTickets() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getvendormytickets;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
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
                ticketList.clear();
                JSONObject jsonObjectt = response.getJSONObject("data");


                JSONArray jsonArray = jsonObjectt.getJSONArray("tickets");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("user_id", block_object.get("user_id").toString());
                    map.put("ticket_id", block_object.get("ticket_id").toString());
                    map.put("team_id", block_object.get("team_id").toString());
                    map.put("question_id", block_object.get("question_id").toString());
                    map.put("subject_type", block_object.get("subject_type").toString());
                    map.put("subject", block_object.get("subject").toString());
                    map.put("booking_id", block_object.get("booking_id").toString());
                    map.put("wallet_id", block_object.get("wallet_id").toString());
                    map.put("query", block_object.get("query").toString());
                    map.put("file", block_object.get("file").toString());
                    map.put("priority", block_object.get("priority").toString());
                    map.put("status", block_object.get("status").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    map.put("date_time", block_object.get("date_time").toString());
                    map.put("get_support_question", block_object.get("get_support_question").toString());
                    map.put("query_statement", block_object.get("query_statement").toString());
                    map.put("last_reply", block_object.get("last_reply").toString());
                    ticketList.add(map);
                }
                offerAdapter = new OfferAdapter(ticketList);
                recyclerView.setAdapter(offerAdapter);
                if (ticketList.isEmpty()) {
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
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_inflaate, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            try {
                JSONObject orderInfo = new JSONObject(data.get(position).get("query_statement"));
                String question = orderInfo.getString("question");

                String query = orderInfo.getString("query");
                String model = orderInfo.getString("model");

                holder.heading.setText(question);
                holder.headingtop.setText(model);
                holder.tvModel.setText(query);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            holder.tvDate.setText(setDateTimeValues(data.get(position).get("date_time")));
            holder.tvView.setText(data.get(position).get("status"));
            holder.tvView.setBackgroundResource(R.drawable.green_bg);
            holder.ivChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pref.set("ticket_id",data.get(position).get("id")).commit();
                    ((Container) mActivity).displayView(29);
                }
            });
            if(data.get(position).get("file").equalsIgnoreCase("null") || data.get(position).get("file").isEmpty()){
                holder.icon.setVisibility(View.GONE);
            }else{
                holder.icon.setVisibility(View.VISIBLE);
                holder.icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertView(data.get(position).get("file"));
                    }
                });
            }
        }

        public int getItemCount() {
            return data.size();
        }
    }
    /*https://youtu.be/lvj9Ny0jv6o*/

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvDate, headingtop, heading, tvModel, tvView;
        ImageView ivChat,icon;

        public OfferHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivChat = itemView.findViewById(R.id.ivChat);
            icon = itemView.findViewById(R.id.icon);
            headingtop = itemView.findViewById(R.id.headingtop);
            heading = itemView.findViewById(R.id.heading);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvView = itemView.findViewById(R.id.tvView);
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

    private void AlertView(String file) {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.viewimagemessage);
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
        ImageView close = dialog.findViewById(R.id.close);
        ImageView imageviewMesg = dialog.findViewById(R.id.messageview);
        Picasso.get().load(file).into(imageviewMesg);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }

}
