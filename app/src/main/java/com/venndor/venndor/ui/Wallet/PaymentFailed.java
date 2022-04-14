package com.venndor.venndor.ui.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.BaseActivity;

public class PaymentFailed  extends BaseActivity {
    TextView tvHeading, tvGST, tvreason, tvorederamount, tvComminssionEarned, tvLoanDebit;
    ImageView ivAdd;
    LinearLayout llGotoWallet,llBuyLeads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_failed_screen);
        init();
    }

    private void init() {
        tvHeading = findViewById(R.id.tvHeading);
        tvGST = findViewById(R.id.tvGST);
        ivAdd = findViewById(R.id.ivAdd);
        tvreason = findViewById(R.id.tvreason);
        tvorederamount = findViewById(R.id.tvorederamount);
        llGotoWallet = findViewById(R.id.llGotoWallet);
        llBuyLeads = findViewById(R.id.llBuyLeads);
        llGotoWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(PaymentFailed.this,Container.class);
                i.putExtra("keyPayment","fail");
                startActivity(i);
            }
        });
        llBuyLeads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mActivity,AddMoney.class);

                startActivity(i);
            }
        });
        tvComminssionEarned = findViewById(R.id.tvComminssionEarned);
        tvLoanDebit = findViewById(R.id.tvLoanDebit);
        tvHeading.setText("Transaction Failed ");
        tvGST.setText("Oops, Your payment failed, Please contact your financial institution for the same.");
        tvreason.setText("Fund could not be credited to your wallet, as your transaction is Failed");
        tvorederamount.setText("Customer Name :");
        tvComminssionEarned.setText("Booking ID :");
        tvLoanDebit.setText("Service Date &amp; Time :");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
