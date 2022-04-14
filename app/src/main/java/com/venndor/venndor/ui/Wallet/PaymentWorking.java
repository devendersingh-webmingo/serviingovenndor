package com.venndor.venndor.ui.Wallet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentWorking  extends BaseActivity {
    TextView tvHeading, tvGST, tvreason, tvorederamount, tvComminssionEarned, tvLoanDebit,tvname,tvdata,tvValue;
    ImageView ivAdd;
    LinearLayout llGotoWallet,llBuyLeads;
    Preferences pref;
    String razorpay_order_id;
    Intent i=getIntent();
    String moveTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_success_screen);
        init();
    }

    private void init() {
        pref=new Preferences(mActivity);
        tvHeading = findViewById(R.id.tvHeading);
        tvGST = findViewById(R.id.tvGST);
        ivAdd = findViewById(R.id.ivAdd);
        tvreason = findViewById(R.id.tvreason);
        tvorederamount = findViewById(R.id.tvorederamount);
        tvComminssionEarned = findViewById(R.id.tvComminssionEarned);
        tvLoanDebit = findViewById(R.id.tvLoanDebit);
        tvname  = findViewById(R.id.tvname);
        tvdata  = findViewById(R.id.tvdata);
        tvValue = findViewById(R.id.tvValue);
        llGotoWallet = findViewById(R.id.llGotoWallet);
        llBuyLeads = findViewById(R.id.llBuyLeads);

        if (getIntent().hasExtra("key")) {
             moveTo = getIntent().getStringExtra("key");
            Log.v("MyValueCheck", "value " + moveTo);
            if(moveTo.equalsIgnoreCase("addmoneytowallet")){
                llGotoWallet.setVisibility(View.VISIBLE);
                llGotoWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(PaymentWorking.this,Container.class);
                        i.putExtra("keyPayment","fail");
                        startActivity(i);
                    }
                });
                llBuyLeads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(mActivity,Container.class);
                        i.putExtra("key","1");
                        startActivity(i);
                    }


                });
            }else {
                llGotoWallet.setVisibility(View.GONE);
                llBuyLeads.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(mActivity,Container.class);
                        i.putExtra("key",moveTo);
                        startActivity(i);
                    }


                });
            }


        }

        tvHeading.setText("Thank You for the Payment ");
        tvGST.setText("Congratulations! for adding fund to your wallet, Amount Rs.XXXX has been credited to your wallet");
        tvreason.setText("Fund has been Credited");
        tvorederamount.setText("Payment Date & Time :");
        tvComminssionEarned.setText(" Credited Amount :");
        tvLoanDebit.setText("Transaction ID :");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            tvname.setText(dtf.format(now));
        }
        if (getIntent().hasExtra("transaction_id")){
            String value = getIntent().getStringExtra("transaction_id");
            Log.v("MyValueCheck", "value " + value);
            tvValue.setText(value);
        }
        if (getIntent().hasExtra("credited_amount")){
            String value = getIntent().getStringExtra("credited_amount");
            Log.v("MyValueCheck", "value " + value);
            tvdata.setText(" ₹ "+value);
        }

/*
        if (getIntent().hasExtra("PaydateTime")) {
            String value = getIntent().getStringExtra("PaydateTime");
            Log.v("MyValueCheck", "value " + value);
//TOdo..
        }*/

      /*

*/
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
