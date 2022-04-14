package com.venndor.venndor.ui.Leads;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThankYou extends BaseActivity {
    TextView tvHeading, tvGST, tvreason, tvorederamount, tvComminssionEarned, tvLoanDebit;
    ImageView ivAdd;
    TextView cusName, bookingId, tvDate;
    LinearLayout llBooking;
    Intent i = getIntent();
    Preferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thnkyou_screen);
        init();
    }

    private void init() {
        pref = new Preferences(mActivity);
        tvHeading = findViewById(R.id.tvHeading);
        tvGST = findViewById(R.id.tvGST);
        llBooking = findViewById(R.id.llBooking);
        ivAdd = findViewById(R.id.ivAdd);
        tvreason = findViewById(R.id.tvreason);
        cusName = findViewById(R.id.cusName);
        bookingId = findViewById(R.id.bookingId);
        tvDate = findViewById(R.id.tvDate);
        tvorederamount = findViewById(R.id.tvorederamount);
        tvComminssionEarned = findViewById(R.id.tvComminssionEarned);
        tvLoanDebit = findViewById(R.id.tvLoanDebit);
        tvHeading.setText("Thank You for Purchasing Lead ");
        tvGST.setText("Congratullations ! Lead has been added in your My Booking");

        tvorederamount.setText("Customer Name :");
        tvComminssionEarned.setText("Booking ID :");
        tvLoanDebit.setText("Service Date  Time :");
        llBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThankYou.this, Container.class);
                i.putExtra("keybooking", "startService");
                startActivity(i);
            }
        });
        if (getIntent().hasExtra("order_number")) {
            String value = getIntent().getStringExtra("order_number");
            Log.v("MyValueCheck", "value " + value);
            pref.set("MyOrderId", value).commit();
            tvreason.setText("Your Purchase Lead ID : "+  value);
        }
        if (getIntent().hasExtra("order_number")) {
            String value = getIntent().getStringExtra("order_number");
            Log.v("MyValueCheck", "value " + value);

            bookingId.setText(value);
        }
        if (getIntent().hasExtra("name")) {
            String value = getIntent().getStringExtra("name");
            Log.v("MyValueCheck", "value " + value);

            cusName.setText(value);
        }
        if (getIntent().hasExtra("serving_datetime")) {
            String value1 = getIntent().getStringExtra("serving_datetime");

            Log.v("MyValueCcheck1", "value " + value1);


            tvDate.setText(setTimeValues(value1));
        }
    }

    private String setTimeValues(String date) {
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
