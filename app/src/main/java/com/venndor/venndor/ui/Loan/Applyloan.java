package com.venndor.venndor.ui.Loan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.ForgetPassword;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

import static com.venndor.venndor.ui.Container.ivIamge;

public class Applyloan extends BaseFragment {
    View rootView;
    EditText tvdetail, Amount, dedAmount;
    Preferences pref;
    LinearLayout ApplyNowLL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.apply_loan, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        tvdetail = rootView.findViewById(R.id.tvdetail);
        Amount = rootView.findViewById(R.id.Amount);
        dedAmount = rootView.findViewById(R.id.dedAmount);
        ApplyNowLL = rootView.findViewById(R.id.ApplyNowLL);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        rootView.findViewById(R.id.ApplyNowLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = Amount.getText().toString().trim();
                String dedAmountt = dedAmount.getText().toString().trim();
              /*  Sneaker.with(mActivity)
                        .setTitle(dedAmountt)
                        .setMessage("")
                        .sneakWarning();*/

                if (amount.isEmpty()) {
                    Sneaker.with(mActivity)
                            .setTitle("Enter Loan Amount")
                            .setMessage("")
                            .sneakWarning();

                } else if (dedAmountt.isEmpty() ||  dedAmountt.length()>=3) {

                    Sneaker.with(mActivity)
                            .setTitle("Deduction Field Must Be Required in percentage")
                            .setMessage("")
                            .sneakWarning();
                } else {
                    PostvendorapplyLoan(amount, String.valueOf(dedAmountt), tvdetail.getText().toString().trim());

                }
            }
        });
    }

    private void PostvendorapplyLoan(String trim, String s, String trim1) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostvendorapplyLoan;
        String token = pref.get("token");
        Log.v("ddurl", token);
        AndroidNetworking.post(url)
                .addBodyParameter("amount", trim)
                .addBodyParameter("deduction", s)
                .addBodyParameter("details", trim1)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            ((Container) mActivity).displayView(19);

            JSONObject dataJsonObject = response.getJSONObject("data");
            JSONObject userJsonObject = dataJsonObject.getJSONObject("loan");
            String id = userJsonObject.getString("id");
            String vendor_id = userJsonObject.getString("vendor_id");
            String loan_id = userJsonObject.getString("loan_id");
            String transaction_id = userJsonObject.getString("transaction_id");
            String amount = userJsonObject.getString("amount");
            String deducation = userJsonObject.getString("deducation");
            String loan_details = userJsonObject.getString("loan_details");
            String status = userJsonObject.getString("status");
            String created_by = userJsonObject.getString("created_by");
            String type = userJsonObject.getString("type");
            String updated_at = userJsonObject.getString("updated_at");
            String created_at = userJsonObject.getString("created_at");

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }


}
