package com.venndor.venndor.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Booking.BookingDetail;
import com.venndor.venndor.ui.Booking.ChangeorderFragment;
import com.venndor.venndor.ui.Booking.HistoryBooking;
import com.venndor.venndor.ui.Booking.MyBooking;
import com.venndor.venndor.ui.Booking.PreviousBookingFragment;
import com.venndor.venndor.ui.CommissionCreadits.Payout;
import com.venndor.venndor.ui.CommissionCreadits.VendorCommissions;
import com.venndor.venndor.ui.CommissionCreadits.VendorPayouts;
import com.venndor.venndor.ui.Dashboard.dashboard;
import com.venndor.venndor.ui.Fragments.DeactivateProfile;
import com.venndor.venndor.ui.Fragments.Home;
import com.venndor.venndor.ui.Fragments.NewLead;
import com.venndor.venndor.ui.Fragments.PPEproblems;
import com.venndor.venndor.ui.Leads.BuyLeadSummary;
import com.venndor.venndor.ui.Leads.LeadHistory;
import com.venndor.venndor.ui.Leads.LeadbuyDetail;
import com.venndor.venndor.ui.Leads.RecommendedLeads;
import com.venndor.venndor.ui.Loan.Applyloan;
import com.venndor.venndor.ui.Loan.LoanPaymentHistory;
import com.venndor.venndor.ui.Loan.Vender_loan;
import com.venndor.venndor.ui.Offer.ManagePartnerOffer;
import com.venndor.venndor.ui.Profile.MyProfile;
import com.venndor.venndor.ui.Support.Chat;
import com.venndor.venndor.ui.Support.HelpAndSupport;
import com.venndor.venndor.ui.Support.OrdersandBooking;
import com.venndor.venndor.ui.Support.Query;
import com.venndor.venndor.ui.Support.ViewPostComplaints;
import com.venndor.venndor.ui.Traning.ActivityTraining;
import com.venndor.venndor.ui.Wallet.MyWalletPendingCreadits;
import com.venndor.venndor.ui.Wallet.TranscationHistory;
import com.venndor.venndor.ui.Wallet.Wallet;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class Container extends BaseActivity implements View.OnClickListener {

    public static ImageView ivBack;
    public static CircleImageView ivIamge;
    public static TextView tvHeading;
    public static int valueInRupees = 0;
    FragmentManager fragmentManager;
    View ViewInclude;
    FrameLayout frameLayout;
    boolean counter = false;
    RelativeLayout rr_logout, rr_Profile,rr_UpcomingBooking,rr_PreviousBooking, rr_mywallet, rr_leads, rr_leads_History,rr_Payout_Summary,rr_Payout_History,rr_My_Bookings, rr_LoanRe_Payment, rr_Help_Support;
    LinearLayout llHome, llMyTask, llInfo, llMore;
    ImageView ivHomeBottom, ivMyTask, ivInfo, ivMore,iv_Submenu,iv_Subpayoutmenu;
    TextView tvHomeBottom,tv_PartnerPayout, tvMyTask, tvInfo, tvMore,tv_leads;
    boolean doubleBackToExitPressedOnce = false;
    Preferences pref;
    Intent i = getIntent();
    private DrawerLayout mDrawerLayout;
    private ScrollView svSideMenu;
    private Fragment fragment;
    public  static CircleImageView navuser;
    public  static TextView navusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        init();
    }

    private void init() {
        pref = new Preferences(mActivity);
        pref.set("counter", "true").commit();
        frameLayout = findViewById(R.id.framelayout);
        tvHeading = findViewById(R.id.tvHeading);
        ivIamge = findViewById(R.id.ivIamge);
        ivBack = findViewById(R.id.ivBack);
        navuser = findViewById(R.id.navuser);
        navusername = findViewById(R.id.navusername);
        llHome = findViewById(R.id.llHome);
        llMyTask = findViewById(R.id.llMyTask);
        llInfo = findViewById(R.id.llInfo);
        llMore = findViewById(R.id.llMore);
        rr_UpcomingBooking= findViewById(R.id.rr_UpcomingBooking);
        rr_PreviousBooking= findViewById(R.id.rr_PreviousBooking);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        svSideMenu = findViewById(R.id.svSideMenu);
        ViewInclude = findViewById(R.id.ViewInclude);
        iv_Submenu = findViewById(R.id.iv_Submenu);
        iv_Subpayoutmenu = findViewById(R.id.iv_Subpayoutmenu);
        mDrawerLayout.closeDrawer(svSideMenu);
        ViewInclude.setVisibility(View.GONE);

        ivHomeBottom = findViewById(R.id.ivHomeBottom);

        rr_logout = findViewById(R.id.rr_logout);
        tv_PartnerPayout = findViewById(R.id.tv_PartnerPayout);
        rr_Profile = findViewById(R.id.rr_Profile);
        rr_LoanRe_Payment = findViewById(R.id.rr_LoanRe_Payment);
        rr_mywallet = findViewById(R.id.rr_mywallet);
        rr_leads = findViewById(R.id.rr_leads);
        rr_leads_History = findViewById(R.id.rr_leads_History);
        rr_Payout_Summary = findViewById(R.id.rr_Payout_Summary);
        rr_Payout_History = findViewById(R.id.rr_Payout_History);
        tv_leads = findViewById(R.id.tv_leads);
        rr_My_Bookings = findViewById(R.id.rr_My_Bookings);
        rr_Help_Support = findViewById(R.id.rr_Help_Support);

        ivMyTask = findViewById(R.id.ivMyTask);
        ivInfo = findViewById(R.id.ivInfo);
        ivMore = findViewById(R.id.ivMore);

        tvHomeBottom = findViewById(R.id.tvHomeBottom);
        tvMyTask = findViewById(R.id.tvMyTask);
        tvInfo = findViewById(R.id.tvInfo);
        tvMore = findViewById(R.id.tvMore);
        rr_leads_History.setVisibility(View.GONE);

        displayView(0);
        tvHeading.setText("DashBoard");
        ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
        ivBack.setVisibility(View.GONE);
        onclickListener();
        if (getIntent().hasExtra("key")) {
            String value = getIntent().getStringExtra("key");
            Log.v("MyValueCheck", "value " + value);
            if (value.equalsIgnoreCase("1")) {
                displayView(13);
                tvHeading.setText("Recommended Leads");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
            } else {
                displayView(24);
                tvHeading.setText("Buy Lead Summary");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
            }
        }
        if(getIntent().hasExtra("keyPayment")){
            String value = getIntent().getStringExtra("keyPayment");
            if (value.equalsIgnoreCase("fail")) {
                displayView(7);
                tvHeading.setText("My Wallet");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
            } else {
                displayView(0);
                tvHeading.setText("DashBoard");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setVisibility(View.GONE);
            }
        }
        if(getIntent().hasExtra("keybooking")){
            String value = getIntent().getStringExtra("keybooking");
            if (value.equalsIgnoreCase("startService")) {
                tvHeading.setText("Booking Detail");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(14);
            } else {
                tvHeading.setText("DashBoard");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setVisibility(View.GONE);
                displayView(0);
            }
        }

    }

    private void onclickListener() {
        ivBack.setOnClickListener(this);
        llHome.setOnClickListener(this);
        llMyTask.setOnClickListener(this);
        llInfo.setOnClickListener(this);
        rr_logout.setOnClickListener(this);
        tv_PartnerPayout.setOnClickListener(this);
        rr_Profile.setOnClickListener(this);
        rr_LoanRe_Payment.setOnClickListener(this);
        rr_mywallet.setOnClickListener(this);
        rr_leads.setOnClickListener(this);
        rr_leads_History.setOnClickListener(this);
        rr_Payout_Summary.setOnClickListener(this);
        rr_Payout_History.setOnClickListener(this);
        tv_leads.setOnClickListener(this);

        rr_My_Bookings.setOnClickListener(this);
        rr_Help_Support.setOnClickListener(this);

        iv_Submenu.setOnClickListener(this);
        iv_Subpayoutmenu.setOnClickListener(this);
        llMore.setOnClickListener(this);
        ivIamge.setOnClickListener(this);

        tvHomeBottom.setTextColor(Color.parseColor("#0468AE"));
        tvMyTask.setTextColor(Color.parseColor("#B9000000"));
        tvInfo.setTextColor(Color.parseColor("#B9000000"));
        tvMore.setTextColor(Color.parseColor("#B9000000"));
        ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
        rr_UpcomingBooking.setOnClickListener(this);
        rr_PreviousBooking.setOnClickListener(this);
        ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
        ivInfo.setImageResource(R.drawable.ic_wallet);
        ivMore.setImageResource(R.drawable.ic_baseline_notes_24);
        ivHomeBottom.setColorFilter(getResources().getColor(R.color.textDark));
        ivInfo.setColorFilter(getResources().getColor(R.color.buttongrey));
        ivMyTask.setColorFilter(getResources().getColor(R.color.buttongrey));
        ivMore.setColorFilter(getResources().getColor(R.color.buttongrey));
    }

    public void displayView(int position) {
        fragmentManager = getSupportFragmentManager();
        mDrawerLayout.closeDrawer(svSideMenu);
        ViewInclude.setVisibility(View.GONE);
        switch (position) {
            case 0:
                fragment = new dashboard();
                tvHeading.setText("DashBoard");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setVisibility(View.GONE);


                break;
            case 1:
                fragment = new NewLead();
                tvHeading.setText("New Leads");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setImageResource(R.drawable.featuremenu);
                break;
            case 2:
                fragment = new DeactivateProfile();
                tvHeading.setText("I want to deactivate my profle");
                break;
            case 3:
                fragment = new PPEproblems();
                tvHeading.setText("PPE related Problems");
                break;
            case 4:
                fragment = new Home();
                tvHeading.setText("Account Details");
                break;
            case 5:
                fragment = new MyBooking();
                tvHeading.setText("My Booking History");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 6:
                fragment = new Payout();
                tvHeading.setText("Partner Invoice");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 7:
                fragment = new Wallet();
                tvHeading.setText("My Wallet");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 8:
                fragment = new MyProfile();
                tvHeading.setText("My Profile");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 9:
                fragment = new VendorPayouts();
                tvHeading.setText("Partner Payout");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 10:
                fragment = new TranscationHistory();
                tvHeading.setText("Transcation History");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 11:
                fragment = new Vender_loan();
                tvHeading.setText("Vendor loan");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 12:
                fragment = new LoanPaymentHistory();
                tvHeading.setText("Loan Payment History");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 13:
                fragment = new RecommendedLeads();
                tvHeading.setText("Recommended Leads");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 14:
                fragment = new BookingDetail();
                tvHeading.setText("Booking Detail");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 15:
                fragment = new HelpAndSupport();
                tvHeading.setText("Help And Support");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 16:
                fragment = new OrdersandBooking();
                tvHeading.setText("Orders and Booking");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);

                break;
            case 17:
               /* fragment = new Query();
                tvHeading.setText("My Query/Complaint");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);*/
                break;
            case 18:
                fragment = new ViewPostComplaints();
                tvHeading.setText("View Complaints");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 19:
                fragment = new Vender_loan();
                tvHeading.setText(" Loan");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 20:
                fragment = new LeadbuyDetail();
                tvHeading.setText("Lead By Detail");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 21:
                fragment = new LeadHistory();
                tvHeading.setText("Lead History");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 22:
                fragment = new MyWalletPendingCreadits();
                tvHeading.setText("My Wallet Pending creadits");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 23:
                fragment = new VendorCommissions();
                tvHeading.setText("Vendor Comissions");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 24:
                fragment = new BuyLeadSummary();
                tvHeading.setText(" Buy Lead Summary");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 25:
                fragment = new Applyloan();
                tvHeading.setText("Apply Loan");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 26:
                fragment = new HistoryBooking();
                tvHeading.setText(" Upcoming Booking");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case 27:
                fragment = new PreviousBookingFragment();
                tvHeading.setText(" Previous Booking");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;

            case 28:
                fragment = new ChangeorderFragment();
                tvHeading.setText(" Change Order Status");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;

            case 29:
                fragment = new Chat();
                tvHeading.setText("Chat");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
                case 30:
                fragment = new ManagePartnerOffer();
                tvHeading.setText("Manage Partner Offer");
                ivIamge.setImageResource(R.drawable.user);
               // ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
                case 31:
                fragment = new ActivityTraining();
                tvHeading.setText("Training And Development");
                ivIamge.setImageResource(R.drawable.user);
                //ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.framelayout, fragment)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivIamge:
                tvHeading.setText("View Complaints");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(18);
                break;
            case R.id.llHome:
                displayView(0);
                tvHomeBottom.setTextColor(Color.parseColor("#0468AE"));
                tvMyTask.setTextColor(Color.parseColor("#B9000000"));
                tvInfo.setTextColor(Color.parseColor("#B9000000"));
                tvMore.setTextColor(Color.parseColor("#B9000000"));
                ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
                ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
                ivInfo.setImageResource(R.drawable.ic_wallet);
                ivMore.setImageResource(R.drawable.ic_baseline_notes_24);
                ivHomeBottom.setColorFilter(getResources().getColor(R.color.textDark));
                ivInfo.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivMyTask.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivMore.setColorFilter(getResources().getColor(R.color.buttongrey));
                break;
            case R.id.llMyTask:
                tvMyTask.setTextColor(Color.parseColor("#0468AE"));
                tvHomeBottom.setTextColor(Color.parseColor("#B9000000"));
                tvInfo.setTextColor(Color.parseColor("#B9000000"));
                tvMore.setTextColor(Color.parseColor("#B9000000"));
                ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
                ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
                ivInfo.setImageResource(R.drawable.ic_wallet);
                ivMore.setImageResource(R.drawable.ic_baseline_notes_24);
                ivMyTask.setColorFilter(getResources().getColor(R.color.textDark));
                ivInfo.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivHomeBottom.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivMore.setColorFilter(getResources().getColor(R.color.buttongrey));
                displayView(5);
                break;
            case R.id.llInfo:
                tvInfo.setTextColor(Color.parseColor("#0468AE"));
                tvMyTask.setTextColor(Color.parseColor("#B9000000"));
                tvHomeBottom.setTextColor(Color.parseColor("#B9000000"));
                tvMore.setTextColor(Color.parseColor("#B9000000"));
                ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
                ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
                ivInfo.setImageResource(R.drawable.ic_wallet);
                ivMore.setImageResource(R.drawable.ic_baseline_notes_24);

                ivInfo.setColorFilter(getResources().getColor(R.color.textDark));
                ivMyTask.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivHomeBottom.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivMore.setColorFilter(getResources().getColor(R.color.buttongrey));
                displayView(7);
                break;

            case R.id.llMore:
                if (counter == false) {
                    counter = true;
                    ViewInclude.setVisibility(View.GONE);
                    mDrawerLayout.closeDrawer(svSideMenu);
                } else {
                    counter = false;
                    ViewInclude.setVisibility(View.VISIBLE);
                    mDrawerLayout.openDrawer(svSideMenu);
                }
                tvMore.setTextColor(Color.parseColor("#0468AE"));
                tvMyTask.setTextColor(Color.parseColor("#B9000000"));
                tvInfo.setTextColor(Color.parseColor("#B9000000"));
                tvHomeBottom.setTextColor(Color.parseColor("#B9000000"));
                ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
                ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
                ivInfo.setImageResource(R.drawable.ic_wallet);
                ivMore.setImageResource(R.drawable.ic_baseline_notes_24);

                ivMore.setColorFilter(getResources().getColor(R.color.textDark));

                ivMyTask.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivHomeBottom.setColorFilter(getResources().getColor(R.color.buttongrey));
                ivInfo.setColorFilter(getResources().getColor(R.color.buttongrey));
                // displayView(8);
                break;
            case R.id.rr_logout:
                getLogout();

                break;
            case R.id.tv_PartnerPayout:

                displayView(9);
                break;
            case R.id.rr_Profile:
                tvHeading.setText("My Profile");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(8);
                break;
            case R.id.rr_mywallet:
                tvHeading.setText("My Wallet");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(7);
                break;
            case R.id.tv_leads:
                tvHeading.setText("Recommended Leads");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(13);
                break;
            case R.id.rr_My_Bookings:
                tvHeading.setText("My Booking History");
                ivIamge.setImageResource(R.drawable.ic_icon_feather_help_circle);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(5);
                break;
            case R.id.rr_LoanRe_Payment:
                tvHeading.setText(" Loan");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(19);
                break;
            case R.id.rr_Help_Support:
                tvHeading.setText("View Complaints");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(18);
                break;
            case R.id.rr_leads_History:
                tvHeading.setText("Lead History");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                displayView(21);
                break;

            case R.id.iv_Submenu:
                if (counter == false) {
                    counter = true;
                    rr_leads_History.setVisibility(View.GONE);
                } else {
                    counter = false;
                    rr_leads_History.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.iv_Subpayoutmenu:
                /*if (counter == false) {
                    counter = true;
                     rr_Payout_Summary.setVisibility(View.GONE);
                     rr_Payout_History.setVisibility(View.GONE);
                } else {
                    counter = false;
                     rr_Payout_Summary.setVisibility(View.VISIBLE);
                     rr_Payout_History.setVisibility(View.VISIBLE);
                }*/
                break;
            case R.id.rr_Payout_Summary:
                displayView(30);
                tvHeading.setText("Manage Partner Offer");

                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case R.id.rr_Payout_History:
                displayView(31);
                tvHeading.setText("Training And Development");

                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case R.id.rr_UpcomingBooking:
                displayView(26);
                tvHeading.setText(" Upcoming Booking");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                break;
            case R.id.rr_PreviousBooking:
                displayView(27);
                break;

        }
    }

    public void getLogout() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getLogout;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseCategoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parseCategoryJsonResponse(JSONObject response) {

        try {
            String message = response.getString("message");
            startActivity(new Intent(mActivity, loginWithMobile.class));
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            startActivity(new Intent(mActivity, loginWithMobile.class));
            pref.clear();
            Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawer(svSideMenu);
        ViewInclude.setVisibility(View.GONE);
        onback();
    }

   /* private void onback() {
        if (doubleBackToExitPressedOnce) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        //AppUtils.showErrorMessage(tvHomeBottom, "Please click BACK again to exit", mActivity);
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
*/

    private void onback() {

        Fragment fr = getSupportFragmentManager().findFragmentById(R.id.framelayout);
        String fragmentName = fr.getClass().getSimpleName();
        if (doubleBackToExitPressedOnce) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            return;
        }else  if (!fragmentName.equalsIgnoreCase("dashboard")) {
            ViewInclude.setVisibility(View.INVISIBLE);

            tvHomeBottom.setTextColor(Color.parseColor("#0468AE"));
            tvMyTask.setTextColor(Color.parseColor("#B9000000"));
            tvInfo.setTextColor(Color.parseColor("#B9000000"));
            tvMore.setTextColor(Color.parseColor("#B9000000"));
            ivHomeBottom.setImageResource(R.drawable.ic_baseline_home_24);
            ivMyTask.setImageResource(R.drawable.ic_tasks_solid);
            ivInfo.setImageResource(R.drawable.ic_wallet);
            ivMore.setImageResource(R.drawable.ic_baseline_notes_24);
            ivHomeBottom.setColorFilter(getResources().getColor(R.color.textDark));
            ivInfo.setColorFilter(getResources().getColor(R.color.buttongrey));
            ivMyTask.setColorFilter(getResources().getColor(R.color.buttongrey));
            ivMore.setColorFilter(getResources().getColor(R.color.buttongrey));

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("dashboard")
                    .replace(R.id.framelayout, new dashboard(), "dashboard")
                    .commit();

            // Finish activity, if only one fragment left, to prevent leaving empty screen
            //  finish();
        }else {

            this.doubleBackToExitPressedOnce = true;
            //AppUtils.showErrorMessage(tvHomeBottom, "Please click BACK again to exit", mActivity);
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }


    }

        /*else if (fragmentName.equalsIgnoreCase("AppliencesCareServicesFragment")) {

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("Addones")
                    .replace(R.id.fragment_Container, new AddonesFragment(), "Addones")
                    .commit();

            // Finish activity, if only one fragment left, to prevent leaving empty screen
            //  finish();
        } else if (fragmentName.equalsIgnoreCase("AddonesFragment")) {

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("HomeFragment")
                    .replace(R.id.fragment_Container, new HomeFragment(), "HomeFragment")
                    .commit();

            // Finish activity, if only one fragment left, to prevent leaving empty screen
            //  finish();
        }*/





}
