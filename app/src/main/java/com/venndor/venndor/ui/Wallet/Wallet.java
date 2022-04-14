package com.venndor.venndor.ui.Wallet;

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
import java.util.TimeZone;

import static com.venndor.venndor.ui.Container.ivIamge;

public class Wallet extends BaseFragment implements View.OnClickListener {

    View rootView;
    RecyclerView recybooking;
    TextView tvBankingMethod, walletCreditHistory, allTranscationHistory, pendingCredits, tvAccNot;
    LinearLayoutManager layoutManager;
    Preferences pref;
    ArrayList<HashMap<String, String>> Historylist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_activity, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        walletCreditHistory = rootView.findViewById(R.id.walletCreditHistory);
        allTranscationHistory = rootView.findViewById(R.id.allTranscationHistory);
        pendingCredits = rootView.findViewById(R.id.pendingCredits);
        tvAccNot = rootView.findViewById(R.id.tvAccNot);

        recybooking = rootView.findViewById(R.id.recyclerview);
        tvBankingMethod = rootView.findViewById(R.id.tvBankingMethod);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recybooking.setLayoutManager(layoutManager);

        tvBankingMethod.setOnClickListener(this);
        walletCreditHistory.setOnClickListener(this);
        allTranscationHistory.setOnClickListener(this);
        pendingCredits.setOnClickListener(this);

        GetvendormywalletHistory();
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        walletCreditHistory.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
        allTranscationHistory.setBackground(getResources().getDrawable(R.drawable.white_border));
        pendingCredits.setBackground(getResources().getDrawable(R.drawable.white_border));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBankingMethod:
                AlertAmount();
                break;
            case R.id.walletCreditHistory:
                walletCreditHistory.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                allTranscationHistory.setBackground(getResources().getDrawable(R.drawable.white_border));
                pendingCredits.setBackground(getResources().getDrawable(R.drawable.white_border));

                break;
            case R.id.allTranscationHistory:
                allTranscationHistory.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                walletCreditHistory.setBackground(getResources().getDrawable(R.drawable.white_border));
                pendingCredits.setBackground(getResources().getDrawable(R.drawable.white_border));
                ((Container) mActivity).displayView(10);
                break;
            case R.id.pendingCredits:
                pendingCredits.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                allTranscationHistory.setBackground(getResources().getDrawable(R.drawable.white_border));
                walletCreditHistory.setBackground(getResources().getDrawable(R.drawable.white_border));

                break;
        }

    }

    public void GetvendormywalletHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.Getvendormywallet;
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
            Historylist.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");

                JSONObject jsonObjectt = response.getJSONObject("data");
                String total_debit = jsonObjectt.getString("total_debit");
                String total_credit = jsonObjectt.getString("total_credit");
                pref.set("total_debit", total_debit).commit();
                pref.set("total_credit", total_credit).commit();
                String available_wallet_amount = jsonObjectt.getString("available_wallet_amount");
                if (available_wallet_amount != null) {
                    tvAccNot.setText("₹ " + available_wallet_amount);
                } else {
                    tvAccNot.setText("0");
                }

                JSONArray histories = jsonObjectt.getJSONArray("histories");
                for (int i = 0; i < histories.length(); i++) {
                    JSONObject loansObj = histories.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("title", loansObj.get("title").toString());
                    map.put("user_id", loansObj.get("user_id").toString());
                    map.put("booking_id", loansObj.get("booking_id").toString());
                    map.put("transaction_id", loansObj.get("transaction_id").toString());
                    map.put("withdrawal", loansObj.get("withdrawal").toString());
                    map.put("deposit", loansObj.get("deposit").toString());
                    map.put("amount", loansObj.get("amount").toString());
                    map.put("avail_balance", loansObj.get("avail_balance").toString());
                    map.put("remark", loansObj.get("remark").toString());
                    map.put("payment_mode", loansObj.get("payment_mode").toString());
                    map.put("mode_of_payment", loansObj.get("mode_of_payment").toString());
                    map.put("payment_remark", loansObj.get("payment_remark").toString());
                    map.put("created_by", loansObj.get("created_by").toString());
                    map.put("approval", loansObj.get("approval").toString());
                    map.put("type", loansObj.get("type").toString());
                    map.put("payment_status", loansObj.get("payment_status").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("updated_at", loansObj.get("updated_at").toString());
                    Historylist.add(map);
                }
                OfferAdapter OfferAdapter = new OfferAdapter(Historylist);
                recybooking.setAdapter(OfferAdapter);
            } else {
            }
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

                    Intent intent = new Intent(getActivity(), AddMoney.class);
                    intent.putExtra("keyWallet","addmoneytowallet");
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });
    }

  /*  private String setTimeValues(String date) {
        try {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }*/

    private String setTimeValues(String date) {
        try {
            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat indianFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            indianFormat .setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date timestamp = utcFormat.parse(date);
            String output = indianFormat.format(timestamp);
            Log.v("ii",output);
            return output.toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_creadit, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            if (!data.get(position).get("created_at").equalsIgnoreCase("null")) {
                holder.tvAccNo.setText(setTimeValues(data.get(position).get("created_at")));
            } else {
                holder.tvAccNo.setText("N/A");
            }
            if (!data.get(position).get("mode_of_payment").equalsIgnoreCase("null")) {
                holder.etDisbursed.setText(data.get(position).get("mode_of_payment"));
            } else {
                holder.etDisbursed.setText("N/A");
            }
            if (!data.get(position).get("avail_balance").equalsIgnoreCase("null")) {
                holder.tvAccountName.setText("₹ " + data.get(position).get("avail_balance"));
            } else {
                holder.tvAccountName.setText("N/A");
            }
            if (!data.get(position).get("amount").equalsIgnoreCase("null")) {
                holder.tvAccNo1.setText(" ₹ " + data.get(position).get("amount"));
            } else {
                holder.tvAccNo1.setText("N/A");
            }
            if (!data.get(position).get("transaction_id").equalsIgnoreCase("null")) {
                holder.tvAccountName1.setText(data.get(position).get("transaction_id"));
            } else {
                holder.tvAccountName1.setText("N/A");
            }
            if (!data.get(position).get("title").equalsIgnoreCase("null")) {
                holder.title.setText(data.get(position).get("title"));
            } else {
                holder.title.setText("N/A");
            }
            if (!data.get(position).get("payment_mode").equalsIgnoreCase("null")) {
                holder.tvAccHolderName.setText(data.get(position).get("payment_mode"));
            } else {
                holder.tvAccHolderName.setText("N/A");
            }
                if (data.get(position).get("payment_mode").equalsIgnoreCase("online")) {
                if (!data.get(position).get("payment_status").equalsIgnoreCase("null")) {
                    holder.tvStatus.setText(data.get(position).get("payment_status"));
                } else {
                    holder.tvStatus.setText("N/A");
                }
                }else{
                    if (!data.get(position).get("approval").equalsIgnoreCase("null")) {
                        holder.tvStatus.setText(data.get(position).get("approval"));
                    } else {
                        holder.tvStatus.setText("N/A");
                    }
                }



        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo, etDisbursed, tvAccountName, tvAccNo1, tvAccountName1, title, tvAccHolderName, tvStatus;

        public OfferHolder(View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            tvAccHolderName = itemView.findViewById(R.id.tvAccHolderName);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            title = itemView.findViewById(R.id.title);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
