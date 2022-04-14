package com.venndor.venndor.ui.Loan;

import android.os.Bundle;
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

import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Adapter.VendorLoanAdapter;
import com.venndor.venndor.ui.Adapter.VendorLoanHistoryAdapter;
import com.venndor.venndor.ui.Presenter.LoanHistoryPresenter;
import com.venndor.venndor.ui.Presenter.Repo.LoanHistoryRepo;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;

public class LoanPaymentHistory extends BaseFragment implements View.OnClickListener, LoanHistoryPresenter.LoanHistoryView {

    View rootView;
    RecyclerView recyloanPayhistory;
    LinearLayoutManager layoutManager;
    TextView tvAccNo, etDisbursed, tvAccNo1, tvAccountName1, tvAccountName, tvIFSCCODE1,
            tvCall;
    Preferences pref;
    LoanHistoryPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.loan_paymen_history, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);


        tvAccNo = rootView.findViewById(R.id.tvAccNo);
        tvAccNo1 = rootView.findViewById(R.id.tvAccNo1);

        etDisbursed = rootView.findViewById(R.id.etDisbursed);
        tvAccountName1 = rootView.findViewById(R.id.tvAccountName1);
        tvCall = rootView.findViewById(R.id.tvCall);
        tvAccountName = rootView.findViewById(R.id.tvAccountName);
        tvIFSCCODE1 = rootView.findViewById(R.id.tvIFSCCODE1);
        recyloanPayhistory = rootView.findViewById(R.id.recyloanPayhistory);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyloanPayhistory.setLayoutManager(layoutManager);
        presenter = new LoanHistoryPresenter(this);
        String loans_id = pref.get("loans_id");
        presenter.LoanHistory(mActivity, loans_id);
        //  presenter.LoanHistory(mActivity, "13");
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);

    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onOTPVerifyError(String message) {

        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakError();
    }

    @Override
    public void onSignUpSuccess(LoanHistoryRepo response, String message) {

        if (message.equalsIgnoreCase("ok")) {


            tvAccNo.setText(setDateTimeValues(response.getData().getMyLoan().getDateTime()));
            tvAccNo1.setText(response.getData().getMyLoan().getUnique_id());
            etDisbursed.setText(response.getData().getMyLoan().getLoanId());
            tvAccountName1.setText(" \u20B9 " + response.getData().getMyLoan().getAmount());
            tvIFSCCODE1.setText(response.getData().getMyLoan().getDeducation() + "%");
            tvAccountName.setText(" \u20B9 " + response.getData().getMyLoan().getAmountDeposite());


            if (response.getData().getHistories().size() > 0) {
                VendorLoanHistoryAdapter vendorLoanAdapter = new VendorLoanHistoryAdapter(response, getContext());
                recyloanPayhistory.setAdapter(vendorLoanAdapter);

                tvCall.setVisibility(View.GONE);
            } else {
                tvCall.setVisibility(View.VISIBLE);
            }


        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            AppUtils.showRequestDialog(mActivity);

        } else {
            AppUtils.hideDialog();

        }
    }
    private String setDateTimeValues(String date) {
        try {
            String pattern = "dd MMM yyyy hh:mm a";
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

    @Override
    public void onOTPVerifyFailure(Throwable t) {

        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakError();
    }



}
