package com.venndor.venndor.ui.Booking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Adapter.Bookinghistory.ActiveBookingHistoryAdapter;
import com.venndor.venndor.ui.Adapter.Bookinghistory.BookingDeatilsAddonseAdapter;
import com.venndor.venndor.ui.Adapter.DayBookingHorizontalAdapter;
import com.venndor.venndor.ui.Adapter.PreviousBookingHorizontalAdapter;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.Presenter.BookingHistoryPresenter;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;
import com.venndor.venndor.ui.Presenter.Request.PreviousBookingHistoryPresenter;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import java.util.ArrayList;
import java.util.HashMap;

import static com.venndor.venndor.ui.Container.ivIamge;


public class PreviousBookingFragment extends BaseFragment implements View.OnClickListener,
        PreviousBookingHistoryPresenter.BookingHistoryView,
        ActiveBookingHistoryAdapter.Myloan
        , PreviousBookingHorizontalAdapter.AddPackageCategoriesSubListener {

    View rootView;
    RecyclerView recyloanPayhistory, recyclerviewtranscation;
    LinearLayoutManager layoutManager, layoutrecytranscationManager;
    ArrayList<HashMap<String, String>> linklist = new ArrayList<>();
    Preferences pref;
    PreviousBookingHorizontalAdapter dayBookingHorizontalAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    PreviousBookingHistoryPresenter presenter;


    View  bookingdetailsview;
    AlertDialog  alertDialogBooking;
    public PreviousBookingFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_previous_booking, container, false);
        findViewById();
        return rootView;
    }

    @Override
    public void onClick(View v) {
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recyloanPayhistory = rootView.findViewById(R.id.recyclerviewtab);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyloanPayhistory.setLayoutManager(layoutManager);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        recyclerviewtranscation = rootView.findViewById(R.id.recyclerviewtranscation);
        layoutrecytranscationManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerviewtranscation.setLayoutManager(layoutrecytranscationManager);
        presenter = new PreviousBookingHistoryPresenter(this);
        presenter.YestardayHistory(mActivity);
        arrayList.add("Yestarday");
        arrayList.add("Last 7 days");
        arrayList.add("Last 15 days");
        arrayList.add("Last 30 days");


        dayBookingHorizontalAdapter = new PreviousBookingHorizontalAdapter(arrayList, mActivity, this);
        recyloanPayhistory.setAdapter(dayBookingHorizontalAdapter);

    }

    @Override
    public void onBookingHistoryError(String message) {
        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakError();
    }

    @Override
    public void onYestardaySuccess(BookingActiveHistoryRepo response, String message) {
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
    public void onForLast7daysHistorySuccess(BookingActiveHistoryRepo response, String message) {
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
    public void onForLast15daysHistorySuccess(BookingActiveHistoryRepo response, String message) {
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
    public void onForLast30daysHistorySuccess(BookingActiveHistoryRepo response, String message) {
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
    public void onBookingHistoryDetailsSuccess(BookingHistoryDetails response, String message) {
      /*  Sneaker.with(mActivity)
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
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            AppUtils.showRequestDialog(mActivity);

        } else {
            AppUtils.hideDialog();

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

    @Override
    public void Click(ArrayList<String> modelArrayList, int position) {


        //  Toast.makeText(mActivity, modelArrayList.get(position) + "", Toast.LENGTH_SHORT).show();
        if (position == 0) {
            presenter.YestardayHistory(getContext());

        } else if (position == 1) {

            presenter.ForLast7daysHistory(getContext());


        } else if (position == 2) {
            presenter.ForLast15daysHistory(getContext());


        } else if (position == 3) {
            presenter.ForLast30daysHistory(getContext());
        }
    }

    public void showBookingDetails(Context context, BookingHistoryDetails bookingHistoryDetails) {

        /* Dialog dialog;*/
        TextView exit, name, address, tvOrderid, tvrs, quantity, jobsum,tvCouponDiscount,tvcoupon;

        ImageView ivCall,ivHelp;

        RecyclerView rvAddons;


        TextView tvAccount1, tvAccNo1, tvName1, tvAccountName1, tvIFSC1, loan, tvdataaa, tvdaaaa, tvaaa, note;
        TextView TotalPartnercommissionAmountTV, TotalBuyLeadAmountTV, TotalLeadAmountTV;

        TextView tvAccNo, etMembership, tvkey, tvAccountName,tvLoanRepaid;
        LinearLayoutManager layoutManager;


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
        ivHelp= bookingdetailsview.findViewById(R.id.ivHelp);
        name = bookingdetailsview.findViewById(R.id.name);
        tvOrderid = bookingdetailsview.findViewById(R.id.tvOrderid);
        address = bookingdetailsview.findViewById(R.id.tvAccount);
        tvCouponDiscount = bookingdetailsview.findViewById(R.id.tvCouponDiscount);
        tvcoupon = bookingdetailsview.findViewById(R.id.tvcoupon);
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
        tvAccNo = bookingdetailsview.findViewById(R.id.tvAccNo);
        etMembership = bookingdetailsview.findViewById(R.id.etMembership);

        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvAddons.setLayoutManager(layoutManager);
        if(bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount()!=null) {
            tvCouponDiscount.setVisibility(View.VISIBLE);
            tvcoupon.setVisibility(View.VISIBLE);
            tvCouponDiscount.setText(" \u20B9 " + String.valueOf(bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount()));

        }else {
            tvCouponDiscount.setVisibility(View.GONE);
            tvcoupon.setVisibility(View.GONE);
        }
        name.setText(bookingHistoryDetails.getData().getOrderInfo().getName());
        address.setText(bookingHistoryDetails.getData().getOrderInfo().getAddress());
        tvOrderid.setText(bookingHistoryDetails.getData().getOrderInfo().getOrderNumber());

        jobsum.setText(bookingHistoryDetails.getData().getPackage().getName());
        quantity.setText("("+bookingHistoryDetails.getData().getPackage().getQuantity()+")");
        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());


        tvrs.setText(" \u20B9 " + bookingHistoryDetails.getData().getPackage().getDiscountAmount());
        tvAccount1.setText(" \u20B9 " + bookingHistoryDetails.getData().getServiceAmount());

        TotalPartnercommissionAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getVendor_comission() + "( " + bookingHistoryDetails.getData().getVendor_comission_percent() + "%)");
        TotalBuyLeadAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getOrderInfo().getBuyLeadAmount());
        TotalLeadAmountTV.setText(" \u20B9 " + bookingHistoryDetails.getData().getVendorBillingAmount());

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

        if (bookingHistoryDetails.getData().getMembershipDiscount() > 0) {
            tvIFSC1.setText(" \u20B9 " + bookingHistoryDetails.getData().getMembershipDiscount());

        } else {

            etMembership.setVisibility(View.GONE);
            tvIFSC1.setVisibility(View.GONE);

        }


        float amounntloan=bookingHistoryDetails.getData().getServiceDiscount()+bookingHistoryDetails.getData().getOrderInfo().getCoupon_discount_amount();

        if (bookingHistoryDetails.getData().getTotalDiscount() > 0) {
            loan.setText(" \u20B9 " + amounntloan);

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
        note.setText("Note : Both Partner Commission & Buy Lead Amount are Calculated on Total Lead Amount ( ₹ " + bookingHistoryDetails.getData().getVendorBillingAmount() + ") .");


        // presenter.TimeSlotRepo(getContext(),"1","2021-08-24");

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