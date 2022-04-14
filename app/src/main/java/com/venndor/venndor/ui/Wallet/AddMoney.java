package com.venndor.venndor.ui.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;


public class AddMoney extends BaseActivity implements View.OnClickListener, PaymentResultWithDataListener {
    RecyclerView recyCheck, recyclerlist;
    LinearLayoutManager layoutManager, layoutManager2;

    String selection = "1";
    String dselection;
    Preferences pref;
    String razorpay_order_id;
    String Payamount;
    TextView tvAmount;
    ArrayList<HashMap<String, String>> arrayFrom = new ArrayList<>();
    ImageView ivchek1, ivchek2;
    TextView textView1;
    TextView textView2;
    String money = "";
    LinearLayout ProceedtoPay;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    Intent i=getIntent();
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmoney_activity);
        Checkout.preload(mActivity);
        findViewById();

        //keyBuyLeadSummary
      //  intent.putExtra("keyWallet","addmoneytowallet");
        //  i.putExtra("keyBuyLeadSummary","addfundTowallet");
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        if (getIntent().hasExtra("keyWallet")){
             value = getIntent().getStringExtra("keyWallet");
            Log.v("MyValueCheck", "value " + value);
        }
        if (getIntent().hasExtra("keyBuyLeadSummary")){
             value = getIntent().getStringExtra("keyBuyLeadSummary");
            Log.v("MyValueCheck", "value " + value);
        }
        TextView tvHeading = findViewById(R.id.tvHeading);
        ImageView ivBack = findViewById(R.id.ivBack);
        ImageView ivIamge = findViewById(R.id.ivIamge);
        tvHeading.setText("Add Money");
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
        ivchek1 = findViewById(R.id.ivchek1);
        ivchek2 = findViewById(R.id.ivchek2);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        ProceedtoPay = findViewById(R.id.ProceedtoPay);
        relativeLayout1 = findViewById(R.id.relative1);
        tvAmount = findViewById(R.id.tvAmount);
        money = pref.get("money");
        tvAmount.setText("Amount :" + pref.get("money"));
        PostrezorPayOrdergenerate(money);
        ivchek1.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
        ivchek2.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivchek1.setImageResource(R.drawable.ic_baseline_radio_button_checked_24);
                selection = "1";
                ivchek2.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
                ProceedtoPay.setVisibility(View.VISIBLE);
            }
        });
        ProceedtoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float paisa = Float.parseFloat(money);
                String razorpayemail = pref.get("razorpayemail");
                String razorpaymobno = pref.get("razorpaymobno");
                startPayment(paisa, razorpaymobno, razorpayemail);
                /* PostaddAmountWallet(money);*/
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onClick(View v) {
    }
    /*"api_key": "rzp_live_yJxf7kgNbo065e",
          "secret_key": "Tlpi1zpOGfr6WfSflGBS734y",*/

    public void startPayment(float paisa, String razorpaymobno, String razorpayemail) {
        /*
          You need to pass Checkout.preload in oncreate() in order to let Razorpay create CheckoutActivity
          and also declare ApiKey in manifest
         */
        final Checkout co = new Checkout();
        co.setKeyID("rzp_live_yJxf7kgNbo065e");
        int image = R.mipmap.partnerlogo; // Can be any drawable
        co.setImage(image);
        try {
            /*  Container.valueInRupees = 1;*/
            JSONObject options = new JSONObject();
            options.put("name", R.string.app_name);
            options.put("description", "Your payable Amount");
            options.put("order_id", razorpay_order_id);
            //You can omit the image option to fetch the image from dashboard
            /*options.put("image", );*/
            options.put("currency", "INR");
            options.put("amount", 100 * paisa);
            JSONObject preFill = new JSONObject();
            preFill.put("email", razorpayemail);
            preFill.put("contact", razorpaymobno);
            options.put("prefill", preFill);
            co.open(AddMoney.this, options);
        } catch (Exception e) {
            //Toast.makeText(mActivity, "Error in payment:", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

            Log.v("jshd", "" + e);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void PostrezorPayOrdergenerate(String amount) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostrezorPayOrder;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.post(url)
                .addBodyParameter("amount", amount)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePostrezorPayOrderResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsePostrezorPayOrderResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            String message = response.getString("message");
           // Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

            JSONObject orderJsonObject = response.getJSONObject("data");
            razorpay_order_id = orderJsonObject.getString("razorpay_order_id");
            pref.set("orderID", razorpay_order_id).commit();

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        // orderID = paymentData.getOrderId();
        String Payment_id = paymentData.getPaymentId();
        // status ="Success";
            /*
        if (paymentData != null) {*/


        Log.e("Payment_data", String.valueOf(paymentData));
        Log.e("getPaymentId", String.valueOf(paymentData.getPaymentId()));
        Log.e("getOrderId", String.valueOf(paymentData.getOrderId()));
        Log.e("getSignature", String.valueOf(paymentData.getSignature()));


        Log.v("RazorCHeck", "Success  " + s);
        // PostaddAmountWallet( money,s);
        PostaddAmountWallet(money, "razorpay", paymentData.getPaymentId(), paymentData.getOrderId(), paymentData.getSignature());
        Toast.makeText(this, "Thank You for making payment, your wallet is credited with Rs." + money, Toast.LENGTH_SHORT).show();
        /*  }*/
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        // Log.v("RazrCHeck","Failure  "+s);
        startActivity(new Intent(mActivity, PaymentFailed.class));
        Toast.makeText(this, "Oops, it seems you payment failed, please contact your financial institution / bank", Toast.LENGTH_SHORT).show();

    }
/*pay_It7lF1fA9ZD0Bq
* order_It7jrI5WQAf0cM
* 366a31e9f6bcceae36533120c29325cbf6afd8afc95df9c82d6b71776ebd7536*/

    public void PostaddAmountWallet(String money, String razor, String paymentId, String orderId, String signature) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostaddAmountWallet;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.post(url)
                .addBodyParameter("amount", money)
                .addBodyParameter("payment_mode", razor)
                .addBodyParameter("razorpay_payment_id", paymentId)
                .addBodyParameter("razorpay_order_id", orderId)
                .addBodyParameter("razorpay_signature", signature)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePostaddAmountWalletResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsePostaddAmountWalletResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            if (!response.getString("status").equalsIgnoreCase("false")) {
                String message = response.getString("message");
                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
                JSONObject dataJsonObject = response.getJSONObject("data");
                JSONObject wallet_infoJsonObject = dataJsonObject.getJSONObject("wallet_info");
                String user_id = wallet_infoJsonObject.getString("user_id");
                String transaction_id = wallet_infoJsonObject.getString("transaction_id");
                String amount = wallet_infoJsonObject.getString("amount");
                String avail_balance = wallet_infoJsonObject.getString("avail_balance");
                String remark = wallet_infoJsonObject.getString("remark");
                String payment_mode = wallet_infoJsonObject.getString("payment_mode");
                String mode_of_payment = wallet_infoJsonObject.getString("mode_of_payment");
                String created_by = wallet_infoJsonObject.getString("created_by");
                String approval = wallet_infoJsonObject.getString("approval");
                String online_payment_status = wallet_infoJsonObject.getString("online_payment_status");
                String updated_at = wallet_infoJsonObject.getString("updated_at");
                String created_at = wallet_infoJsonObject.getString("created_at");
                String id = wallet_infoJsonObject.getString("id");

                if (approval.equalsIgnoreCase("Success")) {
                  Intent i= new Intent(mActivity, PaymentWorking.class);
                  i.putExtra("PaydateTime", created_at);
                  i.putExtra("credited_amount", amount);
                  i.putExtra("transaction_id", transaction_id);
                  i.putExtra("key", value);
                  startActivity(i);
                } else {
                    startActivity(new Intent(mActivity, PaymentFailed.class));
                }


            } else {
                JSONObject message = response.getJSONObject("message");
                String Error = message.getString("message");
                //AppUtils.showToastSort(mActivity, "" + message);
                Toast.makeText(this, "" + Error, Toast.LENGTH_SHORT).show();

            }


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void PostFailWallet(String money, String razorpay) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.PostaddAmountWallet;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.post(url)
                .addBodyParameter("amount", money)
                .addBodyParameter("payment_mode", razorpay)
                .addBodyParameter("razorpay_payment_id", "")
                .addBodyParameter("razorpay_order_id", "")
                .addBodyParameter("razorpay_signature", "")
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseFailResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parseFailResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            if (!response.getString("status").equalsIgnoreCase("false")) {


            } else {
                JSONObject message = response.getJSONObject("message");
                //AppUtils.showToastSort(mActivity, "" + message);
                Toast.makeText(this, "" +message.getString("error"), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity, PaymentFailed.class));
                Toast.makeText(this, "Oops, it seems you payment failed, please contact your financial institution / bank", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }
}


