package com.venndor.venndor.ui.Loan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.tvHeading;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.irozon.sneaker.Sneaker;
import com.shuhart.stepview.StepView;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Adapter.VendorLoanAdapter;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Presenter.Repo.VendorMyloan;
import com.venndor.venndor.ui.Presenter.VendorMyloanPresenter;
import com.venndor.venndor.ui.Verification;
import com.venndor.venndor.ui.personalInfo;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Vender_loan extends BaseFragment implements VendorMyloanPresenter.VendorMyloanView, VendorLoanAdapter.Myloan {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    StepView mStepsView;
    OfferAdapter offerAdapter;
    View rootView;
    Preferences pref;
    VendorMyloanPresenter presenter;
    TextView tvAccNot, tvAccountt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.vender_loan, container, false);
        init();
        return rootView;
    }

    private void init() {
        pref = new Preferences(mActivity);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        tvAccountt = rootView.findViewById(R.id.tvAccountt);
        tvAccNot = rootView.findViewById(R.id.tvAccNot);
        mStepsView = rootView.findViewById(R.id.step_view);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        rootView.findViewById(R.id.tvBankingMethod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container) mActivity).displayView(25);
            }
        });
        // getLoan();
      /*  offerAdapter = new OfferAdapter();
        recyclerView.setAdapter(offerAdapter);*/
        /*.setLabels(steps)*/
        mStepsView.getState()
                .selectedTextColor(ContextCompat.getColor(mActivity, R.color.white))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(mActivity, R.color.teal_200))
                .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .selectedStepNumberColor(ContextCompat.getColor(mActivity, R.color.white))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("Apply");
                    add("Review ");
                    add("Approval");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(getResources().getDimensionPixelSize(R.dimen._1sdp))
                .textSize(getResources().getDimensionPixelSize(R.dimen._14sdp))
                .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen._16sdp))
                .typeface(ResourcesCompat.getFont(mActivity, R.font.roboto))
                // other state methods are equal to the corresponding xml attributes
                .commit();


        presenter = new VendorMyloanPresenter(this);
        presenter.VendorMyloan(mActivity);


    }

    @Override
    public void onVendorMyloanError(String message) {

        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakError();
    }

    @Override
    public void onVendorMyloanSuccess(VendorMyloan response, String message) {

     /*   Sneaker.with(mActivity)
                .setTitle(String.valueOf(response.getData().getLoans().size()))
                .setMessage("")
                .sneakError();
*/
        if (message.equalsIgnoreCase("ok")) {


            tvAccNot.setText("Loan Remaining\t  " + "\u20B9" + response.getData().getRemainingLoan());
            tvAccountt.setText("Loan Amount\t   " + "\u20B9" + response.getData().getTotalLoan());

            if (response.getData().getLoans().size() > 0) {
                VendorLoanAdapter vendorLoanAdapter = new VendorLoanAdapter(response, getContext(), this);

                recyclerView.setAdapter(vendorLoanAdapter);


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

    @Override
    public void onVendorMyloanFailure(Throwable t) {

        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakError();
    }

    @Override
    public void ViewRepaymentHistory(int position, VendorMyloan VendorMyloan) {
        ((Container) mActivity).displayView(12);
        pref.set("loans_id", String.valueOf(VendorMyloan.getData().getLoans().get(position).getId())).commit();


    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();


        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_commission, parent, false);
            return new OfferHolder(itemView);

        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.viewPaymentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvHeading.setText("Loan Payment History");
                    ivIamge.setImageResource(R.drawable.user);
                    ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                    ((Container) mActivity).displayView(12);
                }
            });
        }

        public int getItemCount() {
            return 1;
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {

        TextView viewPaymentHistory;

        public OfferHolder(View itemView) {
            super(itemView);
            viewPaymentHistory = itemView.findViewById(R.id.viewPaymentHistory);
        }
    }


    public void getLoan() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.GettvendormyLoan;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegetservestateJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsegetservestateJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            JSONObject dataJsonObject = response.getJSONObject("data");
            String total_loan = dataJsonObject.getString("total_loan");
            String remaining_loan = dataJsonObject.getString("remaining_loan");
            JSONArray loans = dataJsonObject.getJSONArray("loans");
            for (int i = 0; i < loans.length(); i++) {
                JSONObject loansObj = loans.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
               /* map.put("id", loansObj.get("id").toString());
                map.put("area_id", loansObj.get("area_id").toString());
                PincodeList.add(map);*/
            }

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void getLoanHistory() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.GettvendormyLoanHistory;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegetLoanHistoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsegetLoanHistoryJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            JSONObject dataJsonObject = response.getJSONObject("data");

            JSONArray histories = dataJsonObject.getJSONArray("histories");
            for (int i = 0; i < histories.length(); i++) {
                JSONObject historiesObj = histories.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
               /* map.put("id", historiesObj.get("id").toString());
                map.put("area_id", historiesObj.get("area_id").toString());

                PincodeList.add(map);*/
            }


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }


}
