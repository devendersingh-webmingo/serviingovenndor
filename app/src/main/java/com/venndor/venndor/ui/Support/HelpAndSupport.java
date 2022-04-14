package com.venndor.venndor.ui.Support;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HelpAndSupport extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RelativeLayout ordersandBooking, walletsandcredits, PayoutandPayments, leadsRelated, Others;
    OfferAdapter offerAdapter;
    Preferences pref;
    ArrayList<HashMap<String, String>> Tablist = new ArrayList<>();
    ArrayList<HashMap<String, String>> querylist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.helpsupport, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        /*ordersandBooking = rootView.findViewById(R.id.ordersandBooking);
        walletsandcredits = rootView.findViewById(R.id.walletsandcredits);
        PayoutandPayments = rootView.findViewById(R.id.PayoutandPayments);
        leadsRelated = rootView.findViewById(R.id.leadsRelated);
        Others = rootView.findViewById(R.id.Others);*/

        recyclerView = rootView.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);

       /* ordersandBooking.setOnClickListener(this);
        walletsandcredits.setOnClickListener(this);
        PayoutandPayments.setOnClickListener(this);
        leadsRelated.setOnClickListener(this);
        Others.setOnClickListener(this);*/
        GetvendorSupport();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.ordersandBooking:
                tvHeading.setText("Orders and Booking");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(16);
                break;
            case R.id.walletsandcredits:
                tvHeading.setText("My Wallet");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(7);
                break;
            case R.id.PayoutandPayments:
                tvHeading.setText("My Payout");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);

                ((Container) mActivity).displayView(6);
                break;
            case R.id.leadsRelated:
                tvHeading.setText("Recommended Leads");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(13);
                break;
            case R.id.Others:
                tvHeading.setText("View Post Complaints");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(18);
                break;*/
        }
    }

    public void GetvendorSupport() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getvendortabs;
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
            Tablist.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
               // Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray histories = jsonObjectt.getJSONArray("tabs");
                for (int i = 0; i < histories.length(); i++) {
                    JSONObject loansObj = histories.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", loansObj.get("name").toString());
                    map.put("slug", loansObj.get("slug").toString());
                    map.put("questions", loansObj.get("questions").toString());

                    Tablist.add(map);
                }
                offerAdapter = new OfferAdapter(Tablist);
                recyclerView.setAdapter(offerAdapter);
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    /*  public void PostSupportQuestions(String slug) {
          AppUtils.hideSoftKeyboard(mActivity);
          AppUtils.showRequestDialog(mActivity);
          String url = AppUrls.PostvendorsupportQuestions;
          String token = pref.get("token");
          Log.v("ddurl", token);
          Log.v("data_url", url);
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
                  Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();

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
                  offerAdapter = new OfferAdapter(querylist);
                  recyclerView.setAdapter(offerAdapter);
              } else {
              }
          } catch (Exception e) {
              Log.v("error", String.valueOf(e));
          }
      }*/
    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> bookingsList) {
            this.data = bookingsList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_help, parent, false);
            return new OfferHolder(itemView);
        }
        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.tvtab.setText(data.get(position).get("name"));
            Log.v("jgh",data.get(position).get("name"));
           holder.relativelayout.setVisibility(View.GONE);

            holder.tvtab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.relativelayout.setVisibility(View.VISIBLE);
                    querylist.clear();
                    try {

                        JSONArray questions = new JSONArray(data.get(position).get("questions"));
                        for (int i = 0; i < questions.length(); i++) {
                            JSONObject loansObj = questions.getJSONObject(i);
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
                        QuesAdapter quesAdapter = new QuesAdapter(querylist);
                        holder.rvQuestion.setAdapter(quesAdapter);
                        if(querylist.isEmpty()){
                            pref.set("models","other").commit();
                            ((Container) mActivity).displayView(16);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {

        LinearLayout llorder;
        TextView tvtab;
        RecyclerView rvQuestion;
        RelativeLayout relativelayout;

        public OfferHolder(View itemView) {
            super(itemView);
            llorder = itemView.findViewById(R.id.llorder);
            tvtab = itemView.findViewById(R.id.tvtab);
            relativelayout = itemView.findViewById(R.id.relativelayout);
            rvQuestion = itemView.findViewById(R.id.rvQuestion);
            LinearLayoutManager layoutQManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
            rvQuestion.setLayoutManager(layoutQManager);

        }
    }


    public class QuesAdapter extends RecyclerView.Adapter<CommissionHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public QuesAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public CommissionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_questions, parent, false);
            return new CommissionHolder(itemView);
        }

        public void onBindViewHolder(CommissionHolder holder, final int position) {
            holder.tvquestion.setText(data.get(position).get("queries"));
            holder.imview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pref.set("id",data.get(position).get("id")).commit();
                    pref.set("models",data.get(position).get("models")).commit();
                    pref.set("queries",data.get(position).get("queries")).commit();
                    ((Container) mActivity).displayView(16);
                }
            });
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class CommissionHolder extends RecyclerView.ViewHolder {
        TextView tvquestion;
        ImageView imview;
        LinearLayout ViewLeadDetail;

        public CommissionHolder(View itemView) {
            super(itemView);
            tvquestion = itemView.findViewById(R.id.tvquestion);
            imview = itemView.findViewById(R.id.ivnext);

        }
    }

}
