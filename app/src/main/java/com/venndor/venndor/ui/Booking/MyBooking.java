package com.venndor.venndor.ui.Booking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chaos.view.PinView;
import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Adapter.Bookinghistory.ActiveBookingHistoryAdapter;
import com.venndor.venndor.ui.Adapter.Bookinghistory.BookingDeatilsAddonseAdapter;
import com.venndor.venndor.ui.Adapter.DayBookingHorizontalAdapter;
import com.venndor.venndor.ui.Adapter.VendorLoanAdapter;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Presenter.BookingHistoryPresenter;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.tvHeading;

import okhttp3.ResponseBody;

public class MyBooking extends BaseFragment implements View.OnClickListener, DayBookingHorizontalAdapter.AddPackageCategoriesSubListener, BookingHistoryPresenter.BookingHistoryView, ActiveBookingHistoryAdapter.Myloan {
    View rootView;
    RecyclerView recyloanPayhistory, recyclerviewtranscation;
    LinearLayoutManager layoutManager, layoutrecytranscationManager;
    ArrayList<HashMap<String, String>> linklist = new ArrayList<>();
    Preferences pref;
    DayBookingHorizontalAdapter dayBookingHorizontalAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    BookingHistoryPresenter presenter;
    View view, bookingdetailsview;

    AlertDialog alertDialog, alertDialogBooking;

    String key, Orderid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_bookng, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recyloanPayhistory = rootView.findViewById(R.id.recyclerviewtab);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyloanPayhistory.setLayoutManager(layoutManager);
        Picasso.get().load(AppUrls.profileImageUrl + pref.get("imageVendor")).into(ivIamge);
        recyclerviewtranscation = rootView.findViewById(R.id.recyclerviewtranscation);
        layoutrecytranscationManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerviewtranscation.setLayoutManager(layoutrecytranscationManager);
        presenter = new BookingHistoryPresenter(this);
        presenter.BookingHistory(mActivity);
        key = "Active Booking";
        arrayList.add("Active Booking");
        arrayList.add("Completed Booking");
        arrayList.add("Cancelled Booking");


        dayBookingHorizontalAdapter = new DayBookingHorizontalAdapter(arrayList, mActivity, this);
        recyloanPayhistory.setAdapter(dayBookingHorizontalAdapter);


    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void Click(ArrayList<String> modelArrayList, int position) {

        //  Toast.makeText(mActivity, modelArrayList.get(position) + "", Toast.LENGTH_SHORT).show();
        if (position == 0) {
            presenter.BookingHistory(getContext());

        } else if (position == 1) {
            presenter.BookingCompletedHistory(getContext());


        } else if (position == 2) {

            presenter.BookingCancelledHistory(getContext());


        }
    }

    @Override
    public void onBookingHistoryError(String message) {
        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakError();
    }

    @Override
    public void onBookingHistoryDetailsSuccess(BookingHistoryDetails response, String message) {
       /* Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakSuccess();
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
*/

        if (message.equalsIgnoreCase("ok")) {

            showBookingDetails(getContext(), response);
        }


    }

    @Override
    public void onBookingHistorySuccess(BookingActiveHistoryRepo response, String message) {


        if (message.equalsIgnoreCase("ok")) {


            if (response.getData().getOrder().size() > 0) {
                recyclerviewtranscation.setVisibility(View.VISIBLE);

               /* ActiveBookingHistoryAdapter vendorLoanAdapter = new ActiveBookingHistoryAdapter(response, getContext(), this);
                recyclerviewtranscation.setAdapter(vendorLoanAdapter);*/
                ActiveBookingHistoryAdapter vendorLoanAdapter = new ActiveBookingHistoryAdapter("no","ActiveBooking", response, getContext(), this);
                recyclerviewtranscation.setAdapter(vendorLoanAdapter);

            } else {
                recyclerviewtranscation.setVisibility(View.GONE);

            }


        }
    }

    @Override
    public void onBookingCompletedHistorySuccess(BookingActiveHistoryRepo response, String message) {

        if (message.equalsIgnoreCase("ok")) {


            if (response.getData().getOrder().size() > 0) {

                recyclerviewtranscation.setVisibility(View.VISIBLE);

                ActiveBookingHistoryAdapter vendorLoanAdapter = new ActiveBookingHistoryAdapter(response, getContext(), this);

                recyclerviewtranscation.setAdapter(vendorLoanAdapter);

            } else {
                recyclerviewtranscation.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void onBookingCancelledHistorySuccess(BookingActiveHistoryRepo response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            if (response.getData().getOrder().size() > 0) {

                recyclerviewtranscation.setVisibility(View.VISIBLE);

                ActiveBookingHistoryAdapter vendorLoanAdapter = new ActiveBookingHistoryAdapter(response, getContext(), this);
                recyclerviewtranscation.setAdapter(vendorLoanAdapter);
            } else {
                recyclerviewtranscation.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onStartserviceBookingSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {

            Sneaker.with(mActivity)
                    .setTitle("OTP Successfully Send On User Order Email And Mobile Number.")
                    .setMessage("")
                    .sneakSuccess();

            Toast.makeText(mActivity, "OTP Successfully Send On User Order Email And Mobile Number.", Toast.LENGTH_LONG).show();

            showDialog(getContext());


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
    public void onStartVerifyStartserviceotpSuccess(ResponseBody response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            alertDialog.dismiss();
            ;
            Sneaker.with(mActivity)
                    .setTitle("OTP verify  Successfully.")
                    .setMessage("")
                    .sneakSuccess();
            Toast.makeText(mActivity, "OTP verify Successfully .", Toast.LENGTH_LONG).show();
            presenter.BookingHistory(mActivity);
            key = "Active Booking";
            // presenter.ForToday(getContext());


        }

    }

    @Override
    public void onBookingHistoryFailure(Throwable t) {
        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakError();
    }

    @Override
    public void ViewBookingHistory(int position, BookingActiveHistoryRepo VendorMyloan) {
        presenter.BookingHistoryDetails(getContext(), String.valueOf(VendorMyloan.getData().getOrder().get(position).getId()));


    }

    @Override
    public void ViewAcceptOrder(int position, BookingActiveHistoryRepo VendorMyloan) {

    }

    @Override
    public void ViewRejectOrder(int position, BookingActiveHistoryRepo VendorMyloan) {

    }

    @Override
    public void Viewstartservicce(int position, BookingActiveHistoryRepo VendorMyloan) {

    }

    @Override
    public void ViewCompleteBooking(int position, BookingActiveHistoryRepo VendorMyloan) {

    }


    /* public void showBookingDetails(Context context, BookingHistoryDetails bookingHistoryDetails) {

     *//* Dialog dialog;*//*
        TextView exit, name, address, tvOrderid, tvrs, quantity, jobsum;

        ImageView ivCall,ivHelp;

        RecyclerView rvAddons;


        TextView tvAccount1, tvAccNo1, tvName1, tvAccountName1, tvIFSC1, loan, tvdataaa, tvdaaaa, tvaaa, note;

        TextView tvAccNo, etMembership, tvkey, tvAccountName,tvLoanRepaid;
        LinearLayoutManager layoutManager;


        LayoutInflater inflater = LayoutInflater.from(context);
        bookingdetailsview = inflater.inflate(R.layout.bookingdetaildialogbox, null);
        alertDialogBooking = new AlertDialog.Builder(context)
                .setView(bookingdetailsview)
                .setCancelable(false)
                .create();


        exit = bookingdetailsview.findViewById(R.id.exit);
        ivCall = bookingdetailsview.findViewById(R.id.ivCall);
        name = bookingdetailsview.findViewById(R.id.name);
        tvOrderid = bookingdetailsview.findViewById(R.id.tvOrderid);
        address = bookingdetailsview.findViewById(R.id.tvAccount);

        tvrs = bookingdetailsview.findViewById(R.id.tvrs);
        quantity = bookingdetailsview.findViewById(R.id.quantity);
        jobsum = bookingdetailsview.findViewById(R.id.jobsum);
        rvAddons = bookingdetailsview.findViewById(R.id.rvAddons);

        tvAccount1 = bookingdetailsview.findViewById(R.id.tvAccount1);
        tvAccNo1 = bookingdetailsview.findViewById(R.id.tvAccNo1);
        tvkey = bookingdetailsview.findViewById(R.id.tvkey);
        tvAccountName = bookingdetailsview.findViewById(R.id.tvAccountName);
        tvLoanRepaid= bookingdetailsview.findViewById(R.id.tvLoanRepaid);
        tvName1 = bookingdetailsview.findViewById(R.id.tvName1);
        tvAccountName1 = bookingdetailsview.findViewById(R.id.tvAccountName1);
        tvIFSC1 = bookingdetailsview.findViewById(R.id.tvIFSC1);
        loan = bookingdetailsview.findViewById(R.id.loan);
        tvdataaa = bookingdetailsview.findViewById(R.id.tvdataaa);
        tvdaaaa = bookingdetailsview.findViewById(R.id.tvdaaaa);
        tvaaa = bookingdetailsview.findViewById(R.id.tvaaa);
        note = bookingdetailsview.findViewById(R.id.note);
        ivHelp = bookingdetailsview.findViewById(R.id.ivHelp);
        tvAccNo = bookingdetailsview.findViewById(R.id.tvAccNo);
        etMembership = bookingdetailsview.findViewById(R.id.etMembership);

        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvAddons.setLayoutManager(layoutManager);

        name.setText(bookingHistoryDetails.getData().getOrderInfo().getName());
        address.setText(bookingHistoryDetails.getData().getOrderInfo().getAddress());
        tvOrderid.setText(bookingHistoryDetails.getData().getOrderInfo().getOrderNumber());

        jobsum.setText(bookingHistoryDetails.getData().getPackage().getName());
        quantity.setText("("+bookingHistoryDetails.getData().getPackage().getQuantity()+")");
        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());


        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());
        tvAccount1.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceAmount());


        if (bookingHistoryDetails.getData().getOrderInfo().getAddons().size() > 0) {

            BookingDeatilsAddonseAdapter vendorLoanAdapter = new BookingDeatilsAddonseAdapter(bookingHistoryDetails, getContext());
            rvAddons.setAdapter(vendorLoanAdapter);
        }


        if (bookingHistoryDetails.getData().getAddonAmount() > 0) {
            tvAccNo1.setText(" \u20B9 " + bookingHistoryDetails.getData().getAddonAmount());

        } else {
            tvAccNo.setVisibility(View.GONE);
            tvAccNo1.setVisibility(View.GONE);

        }
        tvName1.setText(" \u20B9 " + bookingHistoryDetails.getData().getSubTotal());
        tvAccountName1.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceAmount());

        if (bookingHistoryDetails.getData().getMembershipDiscount() > 0) {
            tvIFSC1.setText(" \u20B9 " + bookingHistoryDetails.getData().getMembershipDiscount());

        } else {

            etMembership.setVisibility(View.GONE);
            tvIFSC1.setVisibility(View.GONE);

        }


        if (bookingHistoryDetails.getData().getTotalDiscount() > 0) {
            loan.setText(" \u20B9 " + bookingHistoryDetails.getData().getTotalDiscount());

        } else {


            loan.setVisibility(View.GONE);
            tvLoanRepaid.setVisibility(View.GONE);
        }


        if (bookingHistoryDetails.getData().getMembershipFees() > 0) {
            tvdataaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getMembershipFees());

        } else {


            tvdataaa.setVisibility(View.GONE);
            tvkey.setVisibility(View.GONE);
        }
        if (bookingHistoryDetails.getData().getHygieneFees() > 0) {
            tvdaaaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getHygieneFees());

        } else {


            tvAccountName.setVisibility(View.GONE);
            tvdaaaa.setVisibility(View.GONE);
        }
        tvaaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getBillingAmount());

        note.setText("Note : Both Partner Commission & Buy Lead Amount are Calculated on Total Lead Amount ( ₹ " + bookingHistoryDetails.getData().getVendorBillingAmount() + ")");

        // presenter.TimeSlotRepo(getContext(),"1","2021-08-24");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBooking.dismiss();
            }
        });
        ivHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container) mActivity).displayView(18);
                alertDialogBooking.dismiss();
            }
        });
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "call", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(bookingHistoryDetails.getData().getOrderInfo().getMobileNumber())));

                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);

            }
        });
        alertDialogBooking.show();
    }
*/


    public void showDialog(Context context) {

        /* Dialog dialog;*/
        TextView exit, tvCount;

        LinearLayout llSubmitNow;

        PinView pinview;


        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.otpverificationdialog, null);
        alertDialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .create();

/*
        dialog = new Dialog(getContext());
       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.otpverificationdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    */ //   WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
//            wmlp.x = 30;   //x position
        //       wmlp.y = 300;   //y position

        // wmlp.gravity = Gravity.TOP;

        exit = view.findViewById(R.id.exit);
        tvCount = view.findViewById(R.id.tvCount);
        pinview = view.findViewById(R.id.pinview);
        llSubmitNow = view.findViewById(R.id.llSubmitNow);

        // presenter.TimeSlotRepo(getContext(),"1","2021-08-24");


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        llSubmitNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp;
                otp = pinview.getText().toString();


                if (otp.length() < 4) {
                    Toast.makeText(getContext(), "The otp field is required.", Toast.LENGTH_SHORT).show();
                    Sneaker.with(getActivity())
                            .setTitle("The otp field is required.")
                            .setMessage("")
                            .sneakWarning();
                } else {
                    presenter.VerifyStartserviceotp(context, otp);

                }
            }
        });
        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.Startservice(getContext(),Orderid);

            }
        });

        alertDialog.show();
    }

    public void showBookingDetails(Context context, BookingHistoryDetails bookingHistoryDetails) {

        /* Dialog dialog;*/
        TextView exit, name, address, tvOrderid, tvrs, quantity, jobsum;

        ImageView ivCall,ivHelp;

        RecyclerView rvAddons;


        TextView tvAccount1, tvAccNo1, tvName1, tvAccountName1, tvIFSC1, loan, tvdataaa, tvdaaaa, tvaaa, note,
                startservicce, CompleteBooking;

        TextView tvAccNo, etMembership, tvkey, tvAccountName, tvLoanRepaid,tvCouponDiscount,tvcoupon;
        LinearLayoutManager layoutManager;
        TextView TotalPartnercommissionAmountTV, TotalBuyLeadAmountTV, TotalLeadAmountTV;


        LayoutInflater inflater = LayoutInflater.from(context);
        bookingdetailsview = inflater.inflate(R.layout.bookingdetaildialogbox, null);
        alertDialogBooking = new AlertDialog.Builder(context)
                .setView(bookingdetailsview)
                .setCancelable(false)
                .create();
        TotalPartnercommissionAmountTV = bookingdetailsview.findViewById(R.id.TotalPartnercommissionAmountTV);
        TotalBuyLeadAmountTV = bookingdetailsview.findViewById(R.id.TotalBuyLeadAmountTV);
        TotalLeadAmountTV = bookingdetailsview.findViewById(R.id.TotalLeadAmountTV);


        exit = bookingdetailsview.findViewById(R.id.exit);
        ivCall = bookingdetailsview.findViewById(R.id.ivCall);
        name = bookingdetailsview.findViewById(R.id.name);
        tvOrderid = bookingdetailsview.findViewById(R.id.tvOrderid);
        address = bookingdetailsview.findViewById(R.id.tvAccount);
        ivHelp= bookingdetailsview.findViewById(R.id.ivHelp);
        tvrs = bookingdetailsview.findViewById(R.id.tvrs);
        quantity = bookingdetailsview.findViewById(R.id.quantity);
        jobsum = bookingdetailsview.findViewById(R.id.jobsum);
        rvAddons = bookingdetailsview.findViewById(R.id.rvAddons);
        tvCouponDiscount = bookingdetailsview.findViewById(R.id.tvCouponDiscount);
        tvcoupon = bookingdetailsview.findViewById(R.id.tvcoupon);
        tvAccount1 = bookingdetailsview.findViewById(R.id.tvAccount1);
        tvAccNo1 = bookingdetailsview.findViewById(R.id.tvAccNo1);
        tvkey = bookingdetailsview.findViewById(R.id.tvkey);
        tvAccountName = bookingdetailsview.findViewById(R.id.tvAccountName);
        tvName1 = bookingdetailsview.findViewById(R.id.tvName1);
        tvAccountName1 = bookingdetailsview.findViewById(R.id.tvAccountName1);
        tvIFSC1 = bookingdetailsview.findViewById(R.id.tvIFSC1);
        loan = bookingdetailsview.findViewById(R.id.loan);
        tvdataaa = bookingdetailsview.findViewById(R.id.tvdataaa);
        tvdaaaa = bookingdetailsview.findViewById(R.id.tvdaaaa);
        tvaaa = bookingdetailsview.findViewById(R.id.tvaaa);
        note = bookingdetailsview.findViewById(R.id.note);
        tvAccNo = bookingdetailsview.findViewById(R.id.tvAccNo);
        etMembership = bookingdetailsview.findViewById(R.id.etMembership);

        tvLoanRepaid = bookingdetailsview.findViewById(R.id.tvLoanRepaid);
        startservicce = bookingdetailsview.findViewById(R.id.startservicce);
        CompleteBooking = bookingdetailsview.findViewById(R.id.CompleteBooking);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvAddons.setLayoutManager(layoutManager);

        name.setText(bookingHistoryDetails.getData().getOrderInfo().getName());
        address.setText(bookingHistoryDetails.getData().getOrderInfo().getAddress());
        tvOrderid.setText("Lead id  :- "+bookingHistoryDetails.getData().getOrderInfo().getOrderNumber());
        if(bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount()!=null) {
            tvCouponDiscount.setVisibility(View.VISIBLE);
            tvcoupon.setVisibility(View.VISIBLE);
            tvCouponDiscount.setText(" \u20B9 " + String.valueOf(bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount()));
        }else {
            tvCouponDiscount.setVisibility(View.GONE);
            tvcoupon.setVisibility(View.GONE);
        }
jobsum.setText(bookingHistoryDetails.getData().getPackage().getName());
        quantity.setText("(" + bookingHistoryDetails.getData().getPackage().getQuantity() + ")");
        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());


        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());
        tvAccount1.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceAmount());


        if (bookingHistoryDetails.getData().getOrderInfo().getAddons().size() > 0) {

            BookingDeatilsAddonseAdapter vendorLoanAdapter = new BookingDeatilsAddonseAdapter(bookingHistoryDetails, getContext());
            rvAddons.setAdapter(vendorLoanAdapter);
        }


        if (bookingHistoryDetails.getData().getAddonAmount() > 0) {
            tvAccNo1.setText(" \u20B9 " + bookingHistoryDetails.getData().getAddonAmount());

        } else {
            tvAccNo.setVisibility(View.GONE);
            tvAccNo1.setVisibility(View.GONE);

        }
        tvName1.setText(" \u20B9 " + bookingHistoryDetails.getData().getSubTotal());
        tvAccountName1.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceDiscount());

        TotalPartnercommissionAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getVendor_comission() + "( " + bookingHistoryDetails.getData().getVendor_comission_percent() + "%)");

        TotalBuyLeadAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getOrderInfo().getBuyLeadAmount()+"( " + bookingHistoryDetails.getData().getOrderInfo().getBuyLeadPercent() + "%)");

       // TotalBuyLeadAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getOrderInfo().getBuyLeadAmount());
        TotalLeadAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getVendorBillingAmount());

        if (bookingHistoryDetails.getData().getMembershipDiscount() > 0) {
            tvIFSC1.setText(" \u20B9 " + bookingHistoryDetails.getData().getMembershipDiscount());

        } else {

            etMembership.setVisibility(View.GONE);
            tvIFSC1.setVisibility(View.GONE);

        }






        if (bookingHistoryDetails.getData().getTotalDiscount() > 0) {
            if(bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount()!=null) {

                float amounntloan=bookingHistoryDetails.getData().getServiceDiscount()+bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount();

                loan.setText(" \u20B9 " + amounntloan);
            }else {
                loan.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceDiscount());
            }

        } else {
            loan.setVisibility(View.GONE);
            tvLoanRepaid.setVisibility(View.GONE);
        }

        if (bookingHistoryDetails.getData().getMembershipFees() > 0) {
            tvdataaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getMembershipFees());

        } else {


            tvdataaa.setVisibility(View.GONE);
            tvkey.setVisibility(View.GONE);
        }
        if (bookingHistoryDetails.getData().getHygieneFees() > 0) {
            tvdaaaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getHygieneFees());

        } else {


            tvAccountName.setVisibility(View.GONE);
            tvdaaaa.setVisibility(View.GONE);
        }


        if (key.equalsIgnoreCase("Active Booking")) {
            if (bookingHistoryDetails.getData().getButton_show().equalsIgnoreCase("Yes")) {


                if (bookingHistoryDetails.getData().getOrderInfo().getActionCheck().equalsIgnoreCase("start-service")) {
                    if (bookingHistoryDetails.getData().getOrderInfo().getOrderStatus().equalsIgnoreCase("Under-Cancellation")) {
                        startservicce.setVisibility(View.GONE);

                    } else {
                        startservicce.setVisibility(View.VISIBLE);

                    }

                } else if (bookingHistoryDetails.getData().getOrderInfo().getActionCheck().equalsIgnoreCase("complete-order")) {
                    CompleteBooking.setVisibility(View.VISIBLE);


                } else {
                    CompleteBooking.setVisibility(View.GONE);
                    startservicce.setVisibility(View.GONE);

                }
            }
        }

        tvaaa.setText(" \u20B9 " + bookingHistoryDetails.getData().getBillingAmount());

        note.setText("Note : Both Partner Commission & Buy Lead Amount are Calculated on Total Lead Amount ( ₹ "+ bookingHistoryDetails.getData().getVendorBillingAmount() + ") .");

        // presenter.TimeSlotRepo(getContext(),"1","2021-08-24");


        startservicce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Orderid = String.valueOf(bookingHistoryDetails.getData().getOrderInfo().getId());
                presenter.Startservice(getContext(), Orderid);
                alertDialogBooking.dismiss();
            }
        });

        CompleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                pref.set("payment_method", bookingHistoryDetails.getData().getOrderInfo().getPaymentMethod()).commit();
                pref.set("order_id", String.valueOf(bookingHistoryDetails.getData().getOrderInfo().getId())).commit();
                alertDialogBooking.dismiss();

                ((Container) mActivity).displayView(28);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBooking.dismiss();
            }
        });
        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "call", Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(bookingHistoryDetails.getData().getOrderInfo().getMobileNumber())));

                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(callIntent);

            }
        });


        ivHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Container) mActivity).displayView(18);
                alertDialogBooking.dismiss();
            }
        });
        alertDialogBooking.show();
    }
}
