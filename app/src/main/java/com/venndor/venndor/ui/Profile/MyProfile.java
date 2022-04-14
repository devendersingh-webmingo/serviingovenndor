package com.venndor.venndor.ui.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.irozon.sneaker.Sneaker;
import com.squareup.picasso.Picasso;
import com.thomashaertel.widget.MultiSpinner;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.ui.Presenter.Repo.UpdateprofileUserRepo;
import com.venndor.venndor.ui.Presenter.Request.UpdateProfile_request;
import com.venndor.venndor.ui.Presenter.UpdateprofilePresenter;
import com.venndor.venndor.ui.loginWithMobile;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.venndor.venndor.ui.Container.ivIamge;

public class MyProfile extends BaseFragment implements View.OnClickListener, UpdateprofilePresenter.updateProfileView {
    private static final int SELECT_PICTURE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Uniquedaam";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String picturePath = "", filename = "", ext = "", newDate, strJobId = "", strCityId = "";
    Bitmap rotatedBitmap;
    Uri picUri;
    String statuspic = "";
    String statusType = "";
    File imageFile;

    
    ArrayList<HashMap<String, String>> select_Categories = new ArrayList<>();
    ArrayList<HashMap<String, String>> select_SubCategories = new ArrayList<>();

    ArrayList<HashMap<String, String>> ServestateList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayGender = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayType = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayBtype = new ArrayList<>();
    ArrayList<HashMap<String, String>> stateList = new ArrayList<>();
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    ArrayList<HashMap<String, String>> ServecityList = new ArrayList<>();
    ArrayList<HashMap<String, String>> CategoryList = new ArrayList<>();
    ArrayList<HashMap<String, String>> SubCategoryList = new ArrayList<>();
    ArrayList<HashMap<String, String>> PincodeList = new ArrayList<>();
    ArrayList<HashMap<String, String>> pincodeList = new ArrayList<>();
    ArrayList<HashMap<String, String>> sub_categorieslist = new ArrayList<>();
    ArrayList<HashMap<String, String>> tempsubstorelist = new ArrayList<>();//temporary storing subcat list
    String encodedImage = "";
    String mydoc = "";
    String serve_cityname="";
    LinearLayout llchoosefile;
    LabeledSwitch labeledSwitch;
    View rootView;
    LinearLayout llUpdateSecurityDetails, llAcDetails, llPrsonalDetails;
    EditText etoldPassword, etnewPassword, etConfirmPassword1;
    TextView tvPersonal, tvAccNo1a, tabBank, profname, tvchooseProfile, sdd, calender, tvAccNo, tvCalender, etCity, tvPiincode, etDisbursed, tvAccountName1,
            etstate, tvaddress, tvAccountName, tvSecuritydetails, tvacc,
            tvPanNo, putGSTNO, editDetail, editGStDetal, editOtherDocs, editPhotographs, editcancelledcheque, editAdhar, editAdharBack;
    RelativeLayout Rlaccanddetails, relBusinessProof;
    Preferences pref;
    Calendar myCalendar;
    RelativeLayout rl_selectAllCategory,rl_selectAllSubCategory,rl_selectAllPincodes,relativeSubCat;
    Boolean isSelectAllCat=false,isSelectAllSubCat=false,isSelectAllPincodes=false;
    ImageView iv_checkCategorys,iv_checkSubCategorys,iv_checkPincodes;

    ImageView ivPAN, ivGST, ivcancelledcheque, ivPhotographs, ivOtherDocs, ivAdharFront, ivAdharBack;
    EditText etAccountName, etAccountnumber, etifscCode, etbank_name, etbank_branch;
    Spinner spGender, spState, spType, spCity, spServeState, spServeCity, spCategory, spBusinessType, spSubCategory;
    EditText etfName, etlName, etEmail, etContactNumber, etpermanentAddress, etwebsite,
            etPincode, etPassword, etConfirmPassword;
    CircleImageView ivCustomer;
    String account_type, ifsc_code, bank_name,
            bank_branch, email, mobile_number, dob, gender, BankkType, BusinessType, serve_state_id, serve_pinxcodes, serve_city_id, category_id, sub_category_id;
    String account_name, account_number, address, pincode, first_name, last_name, business_type, website, statemain_id, citymain_id, pan_card_number, aadhaar_card_number, business_proof_number, business_address;
    TextView putAdharcardNO, putAdharNO,uniqueId;
    LinearLayout llSecurity, llPersonalDetails, editPersonalDetails;
    String categoryId = "";
    TextView addGSTDetail,tvNoPincodes, addPhotographs, addOtherDocs, addcancelledcheque, addPANDetail, addADHARDetail, addAdharbackDetail;
    ImageView ivCamera;
    LinearLayout llGsteditable, llOtherDocseditable, llPhotographseditable, llcancelledchequeeditable, llBankUpdateDetails, llAdhareditable, llAdharBackeditable, llPaneditable, lldocumentDetails, llBankView;
    String ServestrJobId = "", ServestrCityId = "";
    String vaccineStatus = "";
    RecyclerView recySubCategories, recyPincode, recyCategories;
    LinearLayoutManager layoutManager, layoutManager2, layoutManager3, layoutManager0;
    OfferAdapter2 OfferAdapter;
    ImageView ivchooseGST, ivchoosePAN, ivchooseAdharBack, ivchooseAdhar, ivchooseCancelCheque, ivchoosePhotographs, ivchooseOtherDoc;
    EditText etGSTNumber, etGSTcertificate, etPANnumber, etPANImage, etAdharNumber, etAdharFrontImage, etAdharBackImage;
    ArrayList<Integer> pincodestatusList = new ArrayList<Integer>();
    ArrayList<Integer> SubcategoryarrayList = new ArrayList<Integer>();
    ArrayList<Integer> CategoryarrayList = new ArrayList<Integer>();
    //    ArrayList<Integer> previousSubCatarrayList = new ArrayList<Integer>();
    String[] previousSubCat;
    String[] previousCat;
    String[] previousPinCat;
    UpdateprofilePresenter updateprofilePresenter;
    String categoriesname;
    ViewImage viewImage;
    private MultiSpinner spinner;
    private Bitmap bitmap;
    MyProfile myProfile;

    public static int getImageOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static String getFileType(String path) {
        String fileType = null;
        fileType = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return fileType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.myprofile_activity, container, false);
        findViewById();
        myProfile = new MyProfile();
        return rootView;
    }

    private void findViewById() {
        viewImage = new ViewImage();
        pref = new Preferences(mActivity);
        tvPersonal = rootView.findViewById(R.id.tvPersonal);
        tabBank = rootView.findViewById(R.id.tabBank);
        tvAccNo1a = rootView.findViewById(R.id.tvAccNo1a);
        tvchooseProfile = rootView.findViewById(R.id.tvchooseProfile);
        tvSecuritydetails = rootView.findViewById(R.id.tvSecuritydetails);
        tvacc = rootView.findViewById(R.id.tvacc);
        etfName = rootView.findViewById(R.id.etfName);
        etlName = rootView.findViewById(R.id.etlName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etContactNumber = rootView.findViewById(R.id.etContactNumber);
        etpermanentAddress = rootView.findViewById(R.id.etpermanentAddress);
        etwebsite = rootView.findViewById(R.id.etwebsite);
        etPincode = rootView.findViewById(R.id.etPincode);
        etPassword = rootView.findViewById(R.id.etPassword);
        profname = rootView.findViewById(R.id.profname);
        iv_checkPincodes = rootView.findViewById(R.id.iv_checkPincodes);
        rl_selectAllCategory = rootView.findViewById(R.id.rl_selectAllCategory);
        relativeSubCat = rootView.findViewById(R.id.relativeSubCat);
        rl_selectAllSubCategory = rootView.findViewById(R.id.rl_selectAllSubCategory);
        rl_selectAllPincodes = rootView.findViewById(R.id.rl_selectAllPincodes);
        iv_checkCategorys = rootView.findViewById(R.id.iv_checkCategorys);
        iv_checkSubCategorys = rootView.findViewById(R.id.iv_checkSubCategorys);
        lldocumentDetails = rootView.findViewById(R.id.lldocumentDetails);
        llBankView = rootView.findViewById(R.id.llBankView);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        recySubCategories = rootView.findViewById(R.id.recySubCategories);
        recyCategories = rootView.findViewById(R.id.recyCategories);
        recyPincode = rootView.findViewById(R.id.recyPincode);
        updateprofilePresenter = new UpdateprofilePresenter(this);

        etAccountName = rootView.findViewById(R.id.etAccountName);
        etAccountnumber = rootView.findViewById(R.id.etAccountnumber);
        etifscCode = rootView.findViewById(R.id.etifscCode);
        etbank_name = rootView.findViewById(R.id.etbank_name);
        etbank_branch = rootView.findViewById(R.id.etbank_branch);


        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyPincode.setLayoutManager(layoutManager);
        layoutManager2 = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recySubCategories.setLayoutManager(layoutManager2);

        layoutManager0 = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyCategories.setLayoutManager(layoutManager0);


        spServeState = rootView.findViewById(R.id.spServeState);
        spServeCity = rootView.findViewById(R.id.spServeCity);
        spCategory = rootView.findViewById(R.id.spCategory);
        spSubCategory = rootView.findViewById(R.id.spSubCategory);
        spBusinessType = rootView.findViewById(R.id.spBusinessType);

        etConfirmPassword = rootView.findViewById(R.id.etConfirmPassword);
        ImageView imagepass11 = rootView.findViewById(R.id.eye1);
        ImageView imagepass12 = rootView.findViewById(R.id.eye2);

        etGSTNumber = rootView.findViewById(R.id.etGSTNumber);

        etGSTcertificate = rootView.findViewById(R.id.etGSTcertificate);
        etPANnumber = rootView.findViewById(R.id.etPANnumber);
        etPANImage = rootView.findViewById(R.id.etPANImage);
        etAdharNumber = rootView.findViewById(R.id.etAdharNumber);
        etAdharFrontImage = rootView.findViewById(R.id.etAdharFrontImage);
        etAdharBackImage = rootView.findViewById(R.id.etAdharBackImage);
        llBankUpdateDetails = rootView.findViewById(R.id.llBankUpdateDetails);


        ivchooseGST = rootView.findViewById(R.id.ivchooseGST);
        ivchoosePAN = rootView.findViewById(R.id.ivchoosePAN);
        ivchooseAdhar = rootView.findViewById(R.id.ivchooseAdhar);
        ivchooseAdharBack = rootView.findViewById(R.id.ivchooseAdharBack);
        ivchooseCancelCheque = rootView.findViewById(R.id.ivchooseCancelCheque);
        ivchoosePhotographs = rootView.findViewById(R.id.ivchoosePhotographs);
        ivchooseOtherDoc = rootView.findViewById(R.id.ivchooseOtherDoc);


        llGsteditable = rootView.findViewById(R.id.llGsteditable);
        llOtherDocseditable = rootView.findViewById(R.id.llOtherDocseditable);
        llPhotographseditable = rootView.findViewById(R.id.llPhotographseditable);
        llcancelledchequeeditable = rootView.findViewById(R.id.llcancelledchequeeditable);
        llAdhareditable = rootView.findViewById(R.id.llAdhareditable);
        llAdharBackeditable = rootView.findViewById(R.id.llAdharBackeditable);
        llPaneditable = rootView.findViewById(R.id.llPaneditable);
        tvNoPincodes = rootView.findViewById(R.id.tvNoPincodes);
        addGSTDetail = rootView.findViewById(R.id.addGSTDetail);
        addOtherDocs = rootView.findViewById(R.id.addOtherDocs);
        addPhotographs = rootView.findViewById(R.id.addPhotographs);
        addcancelledcheque = rootView.findViewById(R.id.addcancelledcheque);
        addPANDetail = rootView.findViewById(R.id.addPANDetail);
        addADHARDetail = rootView.findViewById(R.id.addADHARDetail);
        addAdharbackDetail = rootView.findViewById(R.id.addAdharbackDetail);
        tvAccNo = rootView.findViewById(R.id.tvAccNoa);
        etDisbursed = rootView.findViewById(R.id.etDisburseda);
        tvAccountName1 = rootView.findViewById(R.id.tvAccountName1a);
        tvAccountName = rootView.findViewById(R.id.tvAccountNamea);
        tvaddress = rootView.findViewById(R.id.tvaddressa);
        ivCustomer = rootView.findViewById(R.id.ivCustomer);
        etstate = rootView.findViewById(R.id.etstatea);

        llAcDetails = rootView.findViewById(R.id.llAcDetails);
        tvPiincode = rootView.findViewById(R.id.tvPiincodea);
        calender = rootView.findViewById(R.id.calender);
        etCity = rootView.findViewById(R.id.etCitya);

        etoldPassword = rootView.findViewById(R.id.etoldPassword);

        etnewPassword = rootView.findViewById(R.id.etnewPassword);
        etConfirmPassword1 = rootView.findViewById(R.id.etConfirmPassword1);
        llUpdateSecurityDetails = rootView.findViewById(R.id.llUpdateSecurityDetails);
        editGStDetal = rootView.findViewById(R.id.editGStDetal);
        editOtherDocs = rootView.findViewById(R.id.editOtherDocs);
        editPhotographs = rootView.findViewById(R.id.editPhotographs);
        editcancelledcheque = rootView.findViewById(R.id.editcancelledcheque);
        editAdhar = rootView.findViewById(R.id.editAdhar);
        editAdharBack = rootView.findViewById(R.id.editAdharBack);
        sdd = rootView.findViewById(R.id.sdd);
        editDetail = rootView.findViewById(R.id.editDetail);
        putGSTNO = rootView.findViewById(R.id.putGSTNO);
        relBusinessProof = rootView.findViewById(R.id.relBusinessProof);
        tvPanNo = rootView.findViewById(R.id.tvPanNo);

        ivPAN = rootView.findViewById(R.id.ivPAN);
        ivGST = rootView.findViewById(R.id.ivGST);
        ivcancelledcheque = rootView.findViewById(R.id.ivcancelledcheque);
        ivPhotographs = rootView.findViewById(R.id.ivPhotographs);
        ivOtherDocs = rootView.findViewById(R.id.ivOtherDocs);
        ivAdharFront = rootView.findViewById(R.id.ivAdharFront);
        ivAdharBack = rootView.findViewById(R.id.ivAdharBack);


        Rlaccanddetails = rootView.findViewById(R.id.Rlaccanddetails);
        ivCamera = rootView.findViewById(R.id.ivCamera);
        llSecurity = rootView.findViewById(R.id.llSecurity);
        llPrsonalDetails = rootView.findViewById(R.id.llPrsonalDetails);
        llPersonalDetails = rootView.findViewById(R.id.llPersonalDetails);


        putAdharcardNO = rootView.findViewById(R.id.putAdharcardNO);
        putAdharNO = rootView.findViewById(R.id.putAdharNO);
        uniqueId= rootView.findViewById(R.id.uniqueId);
        Rlaccanddetails = rootView.findViewById(R.id.Rlaccanddetails);
        editPersonalDetails = rootView.findViewById(R.id.editPersonalDetails);

        spGender = rootView.findViewById(R.id.spGender);
        spType = rootView.findViewById(R.id.spType);
        spState = rootView.findViewById(R.id.spState);
        spCity = rootView.findViewById(R.id.spCity);
        // editPersonalDetails.setVisibility(View.VISIBLE);
        llSecurity.setVisibility(View.GONE);
        llBankView.setVisibility(View.GONE);
        Rlaccanddetails.setVisibility(View.GONE);
        tvPersonal.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
        tabBank.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvacc.setBackground(getResources().getDrawable(R.drawable.white_border));
        tvPersonal.setOnClickListener(this);
        tvSecuritydetails.setOnClickListener(this);
        tvacc.setOnClickListener(this);
        llAcDetails.setOnClickListener(this);
        llUpdateSecurityDetails.setOnClickListener(this);
        editDetail.setOnClickListener(this);
        editGStDetal.setOnClickListener(this);
        editOtherDocs.setOnClickListener(this);
        editPhotographs.setOnClickListener(this);
        rl_selectAllCategory.setOnClickListener(this);
        rl_selectAllSubCategory.setOnClickListener(this);
        rl_selectAllPincodes.setOnClickListener(this);


        tabBank.setOnClickListener(this);


        editGStDetal.setOnClickListener(this);
        editcancelledcheque.setOnClickListener(this);
        editPhotographs.setOnClickListener(this);
        sdd.setOnClickListener(this);
        editAdhar.setOnClickListener(this);
        editAdharBack.setOnClickListener(this);
        editOtherDocs.setOnClickListener(this);
        llPersonalDetails.setVisibility(View.VISIBLE);
        llPrsonalDetails.setOnClickListener(this);
        ivPAN.setOnClickListener(this);

        //for zoom
        /*  ivPAN.setOnTouchListener(new ImageMatrixTouchHandler(rootView.getContext()));*/
        ivGST.setOnClickListener(this);
        tvchooseProfile.setOnClickListener(this);
        ivchooseGST.setOnClickListener(this);
        ivchoosePAN.setOnClickListener(this);
        ivchooseAdhar.setOnClickListener(this);

        addGSTDetail.setOnClickListener(this);
        addOtherDocs.setOnClickListener(this);
        addPhotographs.setOnClickListener(this);
        addcancelledcheque.setOnClickListener(this);
        addPANDetail.setOnClickListener(this);
        addADHARDetail.setOnClickListener(this);
        addAdharbackDetail.setOnClickListener(this);


        llBankUpdateDetails.setOnClickListener(this);
        ivchooseAdharBack.setOnClickListener(this);
        ivchooseCancelCheque.setOnClickListener(this);
        ivchoosePhotographs.setOnClickListener(this);
        ivchooseOtherDoc.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

        if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
            getProfile();
        } else {

            Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
        }

    /*    ImageView imagepass = rootView.findViewById(R.id.eye11);
        ImageView imagepass2 = rootView.findViewById(R.id.eye12);
        ImageView imagepass3 = rootView.findViewById(R.id.eye3);*/
       /* imagepass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etoldPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etoldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        imagepass2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etnewPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etnewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        imagepass3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etConfirmPassword1.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etConfirmPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });*/
        tvCalender = rootView.findViewById(R.id.calender);
        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                calender.setText(sdf.format(myCalendar.getTime()));
                tvCalender.setText(sdf.format(myCalendar.getTime()));
                newDate = tvCalender.getText().toString().trim();
            }
        };
        tvCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {

            populateSpinnerFrom(spGender);
            populateSpinnerBankType(spType);
            populateSpinnerTo(spBusinessType);
        } else {
            Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();

        }
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MYState", stateList.get(position).get("name") + "00");
                /* if (position != 0) {*/
                strJobId = stateList.get(position).get("id");
                if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    GetCity(strJobId);
                } else {
                    Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /* if (position != 0) {*/
                gender = arrayGender.get(position).get("name");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /* if (position != 0) {*/
                BankkType = arrayType.get(position).get("name");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /* if (position != 0) {*/
                BusinessType = arrayBtype.get(position).get("Id");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // strcityId = cityList.get(position).get("id");
                strCityId = cityList.get(position).get("id");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Personal Info


        imagepass11.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        imagepass12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });


        spServeState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("MYState", ServestateList.get(position).get("name") + "00");
                /* if (position != 0) {*/
                ServestrJobId = ServestateList.get(position).get("id");
                if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    GetServeCity(ServestrJobId);
                } else {
                    Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                }

              /*  } else {
                    strJobId = "";
                    cityList.clear();
                    spCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, cityList));

                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spServeCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*Log.v("MYState", ServestateList.get(position).get("name") + "00");*/
                /* if (position != 0) {*/
                ServestrCityId = ServecityList.get(position).get("id");

                getCategory(ServestrCityId);

                getPincode(ServestrJobId, ServestrCityId);
              /*  } else {
                    strJobId = "";
                    cityList.clear();
                    spCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, cityList));

                }*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner = rootView.findViewById(R.id.spinnerMulti);
        /*spinner.setAdapter(adapter, false, onSelectedListener);*/
        spinner.setOnItemsSelectedListener(new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
      /*  categoryId = CategoryList.get(position).get("id");
        if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
            getSubCategory(categoryId);
        } else {
            Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
        } */
            }
        });
       /* spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                categoryId = CategoryList.get(position).get("id");
                if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    getSubCategory(categoryId);
                } else {
                    Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        labeledSwitch = rootView.findViewById(R.id.simpleswitch);
        llchoosefile = rootView.findViewById(R.id.llchoosefile);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true) {
                    vaccineStatus = "yes";
                    llchoosefile.setVisibility(View.GONE);
                } else {
                    vaccineStatus = "no";
                    llchoosefile.setVisibility(View.GONE);
                }
            }


        });

    }

    private void populateSpinnerTo(Spinner spBusinessType) {
        arrayBtype.clear();
        HashMap<String, String> operators = new HashMap<String, String>();
        operators.put("name", "Select Type");
        operators.put("Id", "");
        arrayBtype.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Proprietor");
        operators.put("Id", "1");
        arrayBtype.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Private Limited");
        operators.put("Id", "2");
        arrayBtype.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "LLP/LLC");
        operators.put("Id", "3");
        arrayBtype.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Partnership");
        operators.put("Id", "4");
        arrayBtype.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Others");
        operators.put("Id", "5");
        arrayBtype.add(operators);
        spBusinessType.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, arrayBtype));
    }

    public void populateSpinnerFrom(Spinner spinner) {
        arrayGender.clear();
        HashMap<String, String> operators = new HashMap<String, String>();
        operators.put("name", "Select Gender");
        operators.put("Id", "");
        arrayGender.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Male");
        operators.put("Id", "");
        arrayGender.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Female");
        operators.put("Id", "");
        arrayGender.add(operators);
        spinner.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, arrayGender));
    }

    public void populateSpinnerBankType(Spinner spinner) {
        arrayType.clear();
        HashMap<String, String> operators = new HashMap<String, String>();
        operators.put("name", "Select Type");
        operators.put("Id", "");
        arrayType.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "saving");
        operators.put("Id", "");
        arrayType.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "current");
        operators.put("Id", "");
        arrayType.add(operators);
        spinner.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, arrayType));
    }

    public void getPincode(String servestrJobId, String ServestrCityId) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getPincode + servestrJobId + "/" + ServestrCityId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePincodeJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsePincodeJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            pincodeList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //   Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();

                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray serve_pincodes = jsonObjectt.getJSONArray("pincodes");
                for (int i = 0; i < serve_pincodes.length(); i++) {
                    JSONObject block_object = serve_pincodes.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("area_id", block_object.get("area_id").toString());
                    map.put("pincode", block_object.get("pincode").toString());
                    map.put("remark", block_object.get("remark").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    String isSelected = "false";
                    for (int j = 0; j < previousPinCat.length; j++) {
                        if (previousPinCat[j].equalsIgnoreCase(block_object.get("id").toString())) {
                            isSelected = "true";
                            break;
                        }
                    }
                    map.put("checkStatus", isSelected);

                    //map.put("checkStatus", "false");
                    pincodeList.add(map);
                }
                if(pincodeList.size() ==0){
                    rl_selectAllPincodes.setVisibility(View.GONE);
                    recyPincode.setVisibility(View.GONE);
                    tvNoPincodes.setVisibility(View.VISIBLE);
                }else {
                    rl_selectAllPincodes.setVisibility(View.VISIBLE);
                    recyPincode.setVisibility(View.VISIBLE);
                    tvNoPincodes.setVisibility(View.GONE);
                }
                OfferAdapter OfferAdapter = new OfferAdapter(pincodeList);
                recyPincode.setAdapter(OfferAdapter);
                OfferAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }

    public void getState() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getstate;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsestateJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsestateJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);

            String message = response.getString("message");
            //   Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject jsonObjectt = response.getJSONObject("data");
            JSONArray jsonArray = jsonObjectt.getJSONArray("states");
            stateList.clear();

            HashMap<String, String> mapmap = new HashMap<String, String>();
            mapmap.put("id", "00");
            mapmap.put("country_id", "00");
            mapmap.put("name", "Select City");
            mapmap.put("created_at", "00");
            mapmap.put("updated_at", "00");
            stateList.add(mapmap);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject block_object = jsonArray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", block_object.get("id").toString());
                map.put("country_id", block_object.get("country_id").toString());
                map.put("name", block_object.get("name").toString());
                map.put("created_at", block_object.get("created_at").toString());
                map.put("updated_at", block_object.get("updated_at").toString());
                stateList.add(map);
            }
            spState.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, stateList));

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    //CityApi
    private void GetCity(String strJobId) {
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getCity + strJobId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseCityJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseCityJsonResponse(JSONObject response) {

        try {


            cityList.clear();
            String message = response.getString("message");
            // Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

            JSONObject jsonObjectt = response.getJSONObject("data");
            JSONArray jsonArray = jsonObjectt.getJSONArray("cities");

            HashMap<String, String> mapmap = new HashMap<String, String>();
            mapmap.put("id", "00");
            mapmap.put("state_id", "00");
            mapmap.put("name", "Select City");
            mapmap.put("created_at", "00");
            mapmap.put("updated_at", "00");
            cityList.add(mapmap);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject block_object = jsonArray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", block_object.get("id").toString());
                map.put("state_id", block_object.get("state_id").toString());
                map.put("name", block_object.get("name").toString());
                map.put("created_at", block_object.get("created_at").toString());
                map.put("updated_at", block_object.get("updated_at").toString());
                cityList.add(map);
            }

            spCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, cityList));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBankUpdateDetails:
                if (etAccountName.getText().toString().isEmpty()) {

                    Toast.makeText(mActivity, "please enter  Account Name", Toast.LENGTH_SHORT).show();

                } else if (etAccountnumber.getText().toString().isEmpty()) {

                    Toast.makeText(mActivity, "please enter  Account Number", Toast.LENGTH_SHORT).show();
                } else if (etifscCode.getText().toString().isEmpty()) {

                    Toast.makeText(mActivity, "please enter IFSC Code", Toast.LENGTH_SHORT).show();

                } else if (etbank_name.getText().toString().isEmpty()) {

                    Toast.makeText(mActivity, "please enter Bank Name", Toast.LENGTH_SHORT).show();

                } else if (etbank_branch.getText().toString().isEmpty()) {

                    Toast.makeText(mActivity, "please enter Bank Branch", Toast.LENGTH_SHORT).show();

                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        UpdatePostvendorupdatebankdetailsApi(BankkType, etAccountName.getText().toString(), etAccountnumber.getText().toString(), etifscCode.getText().toString(), etbank_name.getText().toString(), etbank_branch.getText().toString());
                    } else {
                        Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.tvPersonal:

                //llPersonalDetails
                // editPersonalDetails.setVisibility(View.VISIBLE);
                llSecurity.setVisibility(View.GONE);
                llBankView.setVisibility(View.GONE);
                Rlaccanddetails.setVisibility(View.GONE);

                lldocumentDetails.setVisibility(View.GONE);
                llPersonalDetails.setVisibility(View.VISIBLE);
                tabBank.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvPersonal.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvacc.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvSecuritydetails:

                llSecurity.setVisibility(View.VISIBLE);
                editPersonalDetails.setVisibility(View.GONE);
                llPersonalDetails.setVisibility(View.GONE);
                llBankView.setVisibility(View.GONE);
                lldocumentDetails.setVisibility(View.GONE);
                Rlaccanddetails.setVisibility(View.GONE);
                tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvacc.setBackground(getResources().getDrawable(R.drawable.white_border));
                tabBank.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvPersonal.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tvacc:

                Rlaccanddetails.setVisibility(View.VISIBLE);
                llBankView.setVisibility(View.GONE);
                llPersonalDetails.setVisibility(View.GONE);
                lldocumentDetails.setVisibility(View.GONE);
                llSecurity.setVisibility(View.GONE);
                editPersonalDetails.setVisibility(View.GONE);
                tabBank.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvacc.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvPersonal.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.tabBank:
                llBankView.setVisibility(View.VISIBLE);
                llSecurity.setVisibility(View.GONE);
                editPersonalDetails.setVisibility(View.GONE);
                llPersonalDetails.setVisibility(View.GONE);
                lldocumentDetails.setVisibility(View.GONE);
                Rlaccanddetails.setVisibility(View.GONE);
                tabBank.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvacc.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvPersonal.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;

            case R.id.editDetail:
                llPersonalDetails.setVisibility(View.VISIBLE);
                llSecurity.setVisibility(View.GONE);
                lldocumentDetails.setVisibility(View.GONE);
                editPersonalDetails.setVisibility(View.GONE);
                llBankView.setVisibility(View.GONE);
                tvPersonal.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
                tvSecuritydetails.setBackground(getResources().getDrawable(R.drawable.white_border));
                tvacc.setBackground(getResources().getDrawable(R.drawable.white_border));
                break;
            case R.id.rl_selectAllCategory:
                makeSelection();
                selectAllCategories();
                break;
                case R.id.rl_selectAllSubCategory:
                makeSubSelection();
                selectAllSubCategories();
                break;
                case R.id.rl_selectAllPincodes:
                makePincodeSelection();
                selectAllPincode();
                break;
                case R.id.addGSTDetail:
                mydoc = "1";
                gallery();
                break;
            case R.id.addPANDetail:
                mydoc = "2";
                statuspic = "doc";
                statusType = "pandcard";
                gallery();
                break;
            case R.id.addADHARDetail:
                mydoc = "3";
                statuspic = "doc";
                statusType = "aadhaar_front";
                gallery();
                break;
            case R.id.addAdharbackDetail:
                mydoc = "4";
                statuspic = "doc";
                statusType = "aadhaar_back";
                gallery();
                break;

            case R.id.addcancelledcheque:
                mydoc = "5";
                statuspic = "doc";
                statusType = "cheque";
                gallery();
                break;

            case R.id.addPhotographs:
                mydoc = "6";
                statuspic = "doc";
                statusType = "photograph";
                gallery();
                break;


            case R.id.addOtherDocs:
                mydoc = "7";
                statuspic = "doc";
                statusType = "other";
                gallery();
                break;


            case R.id.tvchooseProfile:

                if (checkAndRequestPermissions()) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                } else {

                    Toast.makeText(mActivity, getString(R.string.give_permission), Toast.LENGTH_SHORT).show();

                }
                break;


            case R.id.editGStDetal:
                statuspic = "doc";
                statusType = "business_proof";
                gallery();
                break;
            case R.id.sdd:
                statuspic = "doc";
                statusType = "pandcard";
                gallery();
                break;
            case R.id.editAdhar:
                statuspic = "doc";
                statusType = "aadhaar_front";
                gallery();
                break;
            case R.id.editAdharBack:
                statuspic = "doc";
                statusType = "aadhaar_back";
                gallery();
                break;
            case R.id.editcancelledcheque:
                statuspic = "doc";
                statusType = "cheque";
                gallery();
                break;
            case R.id.editPhotographs:
                statuspic = "doc";
                statusType = "photograph";
                gallery();
                break;
            case R.id.editOtherDocs:
                statuspic = "doc";
                statusType = "other";
                gallery();
                break;


            case R.id.llUpdateSecurityDetails:
                if (etoldPassword.getText().toString().isEmpty()) {

                    Sneaker.with(mActivity)
                            .setTitle("please enter  Old Password")
                            .setMessage("")
                            .sneakWarning();

                    Toast.makeText(mActivity, "please enter  Old Password", Toast.LENGTH_LONG).show();

                } else if (etnewPassword.getText().toString().length() < 5) {

                    Sneaker.with(mActivity)
                            .setTitle("The password must be at least 5 characters!")
                            .setMessage("")
                            .sneakWarning();
                    Toast.makeText(mActivity, "The password must be at least 5 characters!", Toast.LENGTH_LONG).show();


                } else if (!etConfirmPassword1.getText().toString().equals(etnewPassword.getText().toString())) {
                    Sneaker.with(mActivity)
                            .setTitle("Confirm password not match")
                            .setMessage("")
                            .sneakWarning();
                    Toast.makeText(mActivity, "Confirm password not match", Toast.LENGTH_LONG).show();

                } else {
                    if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                        updateprofilePresenter.Updatepassword(mActivity, etoldPassword.getText().toString().trim(), etnewPassword.getText().toString().trim(), etConfirmPassword1.getText().toString().trim());

                        //  Postvendorupdatepassword(etoldPassword.getText().toString(), etnewPassword.getText().toString(), etConfirmPassword1.getText().toString());
                        //sdfs
                    } else {
                        Toast.makeText(mActivity, getString(R.string.errorInternet), Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.ivchooseGST:
            case R.id.ivchoosePAN:
            case R.id.ivchooseAdhar:
            case R.id.ivchooseAdharBack:
            case R.id.ivchooseCancelCheque:
            case R.id.ivchoosePhotographs:
            case R.id.ivchooseOtherDoc:
                statuspic = "doc";
                if (checkAndRequestPermissions()) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                } else {
                    //AppUtils.showToastSort(mActivity, getString(R.string.give_permission));
                    Toast.makeText(mActivity, getString(R.string.give_permission), Toast.LENGTH_SHORT).show();


                }
                break;


            case R.id.ivCamera:
                statuspic = "camera";
                if (checkAndRequestPermissions()) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                } else {
                    //  AppUtils.showToastSort(mActivity, getString(R.string.give_permission));
                    Toast.makeText(mActivity, getString(R.string.give_permission), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.llPrsonalDetails:


                String data1 = etfName.getText().toString().trim();
                String data2 = etlName.getText().toString().trim();
                String data3 = etEmail.getText().toString().trim();
                String data4 = etContactNumber.getText().toString().trim();
                String data5 = etPincode.getText().toString().trim();
                String data6 = etpermanentAddress.getText().toString().trim();
                String data7 = etPassword.getText().toString().trim();
                String data8 = etConfirmPassword.getText().toString().trim();
                String data9 = etwebsite.getText().toString().trim();
                String data10 = etGSTNumber.getText().toString().trim();
                String data11 = etPANnumber.getText().toString().trim();
                String data12 = etAdharNumber.getText().toString().trim();


                pincodestatusList.clear();
                //   pincodes[];
                //  Array sub_categories[];

                for (int i = 0; i < pincodeList.size(); i++) {
                    if (pincodeList.get(i).get("checkStatus").equalsIgnoreCase("true"))
                        pincodestatusList.add(Integer.valueOf(pincodeList.get(i).get("id")));

                    // pincodes.add();
                }





                CategoryarrayList.clear();
                for (int i = 0; i < select_Categories.size(); i++) {
                    CategoryarrayList.add(Integer.valueOf(select_Categories.get(i).get("id")));
                }

/*
                for (int i = 0; i < CategoryList.size(); i++) {
                    if (CategoryList.get(i).get("checkCatStatus").equalsIgnoreCase("true"))
                        CategoryarrayList.add(Integer.valueOf(CategoryList.get(i).get("id")));
//check data here
                }
*/

                SubcategoryarrayList.clear();

             /*   ArrayList<HashMap<String,String>> newSelectedSubCategories=new ArrayList<>();

                for(int j=0; j<SubcategoryarrayList.size();j++){
                    if(select_SubCategories.get(j).get("id").equalsIgnoreCase()){
                        newSelectedSubCategories.add(select_Categories.get(j));
                    }
                }
                select_Categories=newSelectedSubCategories;
                Log.v("MySelectedCsubategories",select_Categories.toString());
*/
                for (int i = 0; i < select_SubCategories.size(); i++) {
                    SubcategoryarrayList.add(Integer.valueOf(select_SubCategories.get(i).get("id")));
                }


                if (data1.isEmpty()) {

                    //Toast.makeText(mActivity, "please enter first name here", Toast.LENGTH_SHORT).show();

                    Sneaker.with(mActivity)
                            .setTitle("please enter first name here")
                            .setMessage("")
                            .sneakWarning();

                } else if (data2.isEmpty()) {


                    Sneaker.with(mActivity)
                            .setTitle("please enter last name here")
                            .setMessage("")
                            .sneakWarning();


                } else if (data3.isEmpty()) {


                    Sneaker.with(mActivity)
                            .setTitle("please enter email here")
                            .setMessage("")
                            .sneakWarning();

                } else if (data4.isEmpty()) {
                    Sneaker.with(mActivity)
                            .setTitle("please enter Contact Number here")
                            .setMessage("")
                            .sneakWarning();

                } else if (pincodestatusList.size() == 0) {
                    Sneaker.with(mActivity)
                            .setTitle("Please Select Pincode!")
                            .setMessage("")
                            .sneakWarning();

                }  else if (CategoryarrayList.size() == 0) {
                    Sneaker.with(mActivity)
                            .setTitle("Please Select  Category!")
                            .setMessage("")
                            .sneakWarning();

                }else if (SubcategoryarrayList.size() == 0) {
                    Sneaker.with(mActivity)
                            .setTitle("Please Select Sub Categories!")
                            .setMessage("")
                            .sneakWarning();

                } else if (strJobId.equalsIgnoreCase("00")) {
                    Sneaker.with(mActivity)
                            .setTitle("Please Select State!")
                            .setMessage("")
                            .sneakWarning();
                } else if (strCityId.equalsIgnoreCase("00")) {
                    Sneaker.with(mActivity)
                            .setTitle("Please Select City!")
                            .setMessage("")
                            .sneakWarning();
                } else {

                   /* ArrayList<Integer> categoryIdyarrayList = new ArrayList<Integer>();
                    categoryIdyarrayList.clear();
                    categoryIdyarrayList.add(Integer.valueOf(categoryId));*/
                    //    PostvendorupdateprofileApi(data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11, data12, gender, newDate, strJobId, strCityId, ServestrJobId, ServestrCityId, vaccineStatus, imageFile, BusinessType);

                    UpdateProfile_request updateProfile_request = new UpdateProfile_request("PATCH", data1, data2, data3, data4, gender,
                            calender.getText().toString().trim(), strJobId, strCityId, data6, data5, ServestrJobId, ServestrCityId, pincodestatusList,CategoryarrayList,
                            SubcategoryarrayList, data11, data12, data10, "");

                    updateprofilePresenter.UpdateProfileUseer(getContext(), updateProfile_request,myProfile);

                    Log.v("checkStatusArray", SubcategoryarrayList.toString());
                    Log.v("checkcatStatusArray", CategoryarrayList.toString());


                /*    UpdateProfile_request updateProfileRequest = new UpdateProfile_request(data1, data2, BusinessType, data3,
                            data4, data9, gender, newDate, data6,data5,ServestrJobId, ServestrCityId,categoryId,"2",
                            strJobId, strCityId, pincodestatusList , SubcategoryarrayList,
                            data11,data12, data7, "Yyyysedrftgyhujyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy","PATCH");

*/

                    /*   presenter.UpdateProfileUseer(mActivity, updateProfileRequest);*/

                    //   Log.d("signuprequest", updateProfileRequest.toString());

                    break;

                }
        }
    }


    private void makePincodeSelection() {
        if(isSelectAllPincodes){
            iv_checkPincodes.setBackground(getResources().getDrawable(R.drawable.checked));
            isSelectAllPincodes=false;
        }else {
            iv_checkPincodes.setBackground(getResources().getDrawable(R.drawable.uncheck));
            isSelectAllPincodes=true;
        }

    }

    private void selectAllPincode() {
        if(isSelectAllPincodes){
            for(int i=0;i<pincodeList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pincodeList.get(i).replace("checkStatus", "false", "true");
                } else {
                    pincodeList.get(i).remove("checkStatus");
                    pincodeList.get(i).put("checkStatus", "true");
                }


            }
        }else {

            for(int i=0;i<pincodeList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    pincodeList.get(i).replace("checkStatus", "true", "false");
                } else {
                    pincodeList.get(i).remove("checkStatus");
                    pincodeList.get(i).put("checkStatus", "false");
                }
            }
        }



        OfferAdapter OfferAdapter = new OfferAdapter(pincodeList);
        recyPincode.setAdapter(OfferAdapter);
        OfferAdapter.notifyDataSetChanged();
    }



    private void makeSubSelection() {
        if(isSelectAllSubCat){
            iv_checkSubCategorys.setBackground(getResources().getDrawable(R.drawable.checked));
            isSelectAllSubCat=false;
        }else {
            iv_checkSubCategorys.setBackground(getResources().getDrawable(R.drawable.uncheck));
            isSelectAllSubCat=true;
        }

    }
    private void selectAllSubCategories() {
        if(isSelectAllSubCat){
            for(int i=0;i<SubCategoryList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SubCategoryList.get(i).replace("checkSubStatus", "false", "true");
                } else {
                    SubCategoryList.get(i).remove("checkSubStatus");
                    SubCategoryList.get(i).put("checkSubStatus", "true");
                }
                select_SubCategories.add(SubCategoryList.get(i));

            }
        }else {
            select_SubCategories.clear();
            for(int i=0;i<SubCategoryList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SubCategoryList.get(i).replace("checkSubStatus", "true", "false");
                } else {
                    SubCategoryList.get(i).remove("checkSubStatus");
                    SubCategoryList.get(i).put("checkSubStatus", "false");
                }
            }
        }



        OfferAdapter2 offerAdapter2 = new OfferAdapter2(SubCategoryList);
        recySubCategories.setAdapter(offerAdapter2);
        offerAdapter2.notifyDataSetChanged();

    }

    private void selectAllCategories() {
        if(isSelectAllCat){
            for(int i=0;i<CategoryList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        CategoryList.get(i).replace("checkCatStatus", "false", "true");
                } else {
                        CategoryList.get(i).remove("checkCatStatus");
                        CategoryList.get(i).put("checkCatStatus", "true");
                    }
                select_Categories.add(CategoryList.get(i));

            }
            }else {
            select_SubCategories.clear();
            select_Categories.clear();
            isSelectAllSubCat=true;
            makeSubSelection();
            for(int i=0;i<CategoryList.size();i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    CategoryList.get(i).replace("checkCatStatus", "true", "false");
                } else {
                    CategoryList.get(i).remove("checkCatStatus");
                    CategoryList.get(i).put("checkCatStatus", "false");
                }
//                filterCategoryData(CategoryList.get(i).get("id"));
            }
        }



            OfferAdapter3 offerAdapter3 = new OfferAdapter3(CategoryList);
            recyCategories.setAdapter(offerAdapter3);
            offerAdapter3.notifyDataSetChanged();
            getMultiSubcategory();
        }

    private void makeSelection() {
        if(isSelectAllCat){
            iv_checkCategorys.setBackground(getResources().getDrawable(R.drawable.checked));
            isSelectAllCat=false;
        }else {
            iv_checkCategorys.setBackground(getResources().getDrawable(R.drawable.uncheck));
            isSelectAllCat=true;
        }
    }

    private void UpdatePostvendorupdatebankdetailsApi(String bankkType, String s, String toString, String string, String s1, String toString1) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorupdatebankdetails;
        String token = pref.get("token");
        Log.v("ddurl", token);
        AndroidNetworking.put(url)
                .addBodyParameter("account_type", bankkType)
                .addBodyParameter("account_name", s)
                .addBodyParameter("account_number", toString)
                .addBodyParameter("ifsc_code", string)
                .addBodyParameter("bank_name", s1)
                .addBodyParameter("bank_branch", toString1)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseupdateBankResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            Toast.makeText(mActivity, "Unauthenticated ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mActivity, loginWithMobile.class));
                        } else {
                            //Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseupdateBankResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            // Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void gallery() {
        if (checkAndRequestPermissions()) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PICTURE);
        } else {
            //  AppUtils.showToastSort(mActivity, getString(R.string.give_permission));
            Toast.makeText(mActivity, getString(R.string.give_permission), Toast.LENGTH_SHORT).show();

        }
    }

    private boolean checkAndRequestPermissions() {
        int writeStorage = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int openCamera = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (openCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writeStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                try {
                    picUri = data.getData();
                    Uri contentURI = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    //file for sending
                    String imagepath = getRealPathFromURI(contentURI, mActivity);
                    imageFile = new File(imagepath);
                    if (statuspic.equalsIgnoreCase("camera")) {
                        UpdateProfilePicture(imageFile);
                    } else {
                        if (statusType.equalsIgnoreCase("business_proof")) {
                            PostMultipledocument("business_proof", imageFile);
                        } else if (statusType.equalsIgnoreCase("pandcard")) {
                            PostMultipledocument("pandcard", imageFile);
                        } else if (statusType.equalsIgnoreCase("aadhaar_front")) {
                            PostMultipledocument("aadhaar_front", imageFile);
                        } else if (statusType.equalsIgnoreCase("aadhaar_back")) {
                            PostMultipledocument("aadhaar_back", imageFile);
                        } else if (statusType.equalsIgnoreCase("cheque")) {
                            PostMultipledocument("cheque", imageFile);
                        } else if (statusType.equalsIgnoreCase("photograph")) {
                            PostMultipledocument("photograph", imageFile);
                        } else if (statusType.equalsIgnoreCase("other")) {
                            PostMultipledocument("other", imageFile);
                        } else {
                            Postvendorupdatedocuments(imageFile);
                        }
                    }
                    Cursor cursor = mActivity.getContentResolver().query(contentURI, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    System.out.println("Image Path : " + picturePath);
                    cursor.close();
                    filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    profname.setText(filename);
                    ext = getFileType(picturePath);
                    String selectedImagePath = picturePath;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(selectedImagePath, options);
                    final int REQUIRED_SIZE = 500;
                    int scale = 1;
                    while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                        scale *= 2;
                    options.inSampleSize = scale;
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(getImageOrientation(picturePath));
                    rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte[] ba = bao.toByteArray();

                    encodedImage = getEncoded64ImageStringFromBitmap(rotatedBitmap);
                    //  ivuser.setPadding(0, 0, 0, 0);
               /*     ivGST.setImageBitmap(rotatedBitmap);
                    ivPAN.setImageBitmap(rotatedBitmap);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mActivity, getString(R.string.unabletoselectimage), Toast.LENGTH_SHORT).show();
                Log.v("imaaage", String.valueOf(SELECT_PICTURE));
            }

        }

    }

    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    public Uri getOutputMediaFileUri(int type) {
        return FileProvider.getUriForFile(mActivity, mActivity.getPackageName() + ".provider", getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the com.kkmeds.kkmeds.base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        /* UpdateProfilePicture(imgString);*/
        Log.v("str", imgString);
        return imgString;
    }

    public void getProfile() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getProfile;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsegetResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsegetResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            select_Categories.clear();
            //  Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            JSONObject dataJsonObject = response.getJSONObject("data");
            String profile_image_url = dataJsonObject.getString("profile_image_url");
            String documents_image_url = dataJsonObject.getString("documents_image_url");


            JSONArray serve_pincodes = dataJsonObject.getJSONArray("serve_pincodes");
            for (int i = 0; i < serve_pincodes.length(); i++) {
                JSONObject block_object = serve_pincodes.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", block_object.get("id").toString());
                map.put("area_id", block_object.get("area_id").toString());
                map.put("pincode", block_object.get("pincode").toString());
                map.put("remark", block_object.get("remark").toString());
                map.put("created_at", block_object.get("created_at").toString());
                map.put("updated_at", block_object.get("updated_at").toString());
                pincodeList.add(map);
            }

            JSONArray categories = dataJsonObject.getJSONArray("categories");
            for (int i = 0; i < categories.length(); i++) {
                JSONObject block_object = categories.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", block_object.get("id").toString());
                map.put("state_id", block_object.get("state_id").toString());
                map.put("city_id", block_object.get("city_id").toString());
                map.put("name", block_object.get("name").toString());
                map.put("checkCatStatus", "true");
                select_Categories.add(map);


                categoriesname = block_object.get("name").toString();

                Log.d("JSONArrayJSONArray", block_object.get("name").toString());

                JSONObject jsonObject = block_object.getJSONObject("get_state");
                String id = jsonObject.getString("id");
                String country_id = jsonObject.getString("country_id");
                String name = jsonObject.getString("name");
                String created_at = jsonObject.getString("created_at");
                String updated_at = jsonObject.getString("updated_at");

                JSONObject cityjsonObject = block_object.getJSONObject("get_city");
                String idd = cityjsonObject.getString("id");
                String state_id = cityjsonObject.getString("state_id");
                String namee = cityjsonObject.getString("name");
                String created_att = cityjsonObject.getString("created_at");
                String updated_att = cityjsonObject.getString("updated_at");
                String updated_atet = cityjsonObject.getString("updated_at");

            }
            JSONArray sub_categories = dataJsonObject.getJSONArray("sub_categories");
            for (int i = 0; i < sub_categories.length(); i++) {
                JSONObject block_object = sub_categories.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", block_object.get("id").toString());
                map.put("state_id", block_object.get("state_id").toString());
                map.put("city_id", block_object.get("city_id").toString());
                map.put("category_id", block_object.get("category_id").toString());
                map.put("sub_category_icon", block_object.get("sub_category_icon").toString());
                map.put("background_image", block_object.get("background_image").toString());
                map.put("sub_category_name", block_object.get("sub_category_name").toString());
                map.put("sub_category_slug", block_object.get("sub_category_slug").toString());
                map.put("short_description", block_object.get("short_description").toString());
                map.put("features", block_object.get("features").toString());
                map.put("meta_title", block_object.get("meta_title").toString());
                map.put("meta_description", block_object.get("meta_description").toString());
                map.put("meta_keyword", block_object.get("meta_keyword").toString());
                map.put("status", block_object.get("status").toString());
                map.put("eligiable_for_all", block_object.get("eligiable_for_all").toString());
                map.put("created_at", block_object.get("created_at").toString());
                map.put("updated_at", block_object.get("updated_at").toString());
                map.put("servicable_pincode", block_object.get("servicable_pincode").toString());
                map.put("position", block_object.get("position").toString());
                map.put("deleted_at", block_object.get("deleted_at").toString());
                select_SubCategories.add(map);
            }


            JSONObject userJsonObject = dataJsonObject.getJSONObject("profile");
            String id = userJsonObject.getString("id");
            String referral_user_id = userJsonObject.getString("referral_user_id");
            String referral_master_id = userJsonObject.getString("referral_master_id");
            String permission_id = userJsonObject.getString("permission_id");
            String name = userJsonObject.getString("name");

            email = userJsonObject.getString("email");


            if (!userJsonObject.getString("email").equalsIgnoreCase("null")) {
                etDisbursed.setText(email);
                etEmail.setText(email);
            } else {
                etDisbursed.setText("N/A");
                etEmail.setText("");
            }
            mobile_number = userJsonObject.getString("mobile_number");

            if (!userJsonObject.getString("mobile_number").equalsIgnoreCase("null")) {
                etContactNumber.setText(mobile_number);
                tvAccNo1a.setText(mobile_number);
            } else {
                etContactNumber.setText("");
            }
            statemain_id = userJsonObject.getString("state_id");
            citymain_id = userJsonObject.getString("city_id");

            category_id = userJsonObject.getString("category_id");
            previousCat = category_id.split(",");
            sub_category_id = userJsonObject.getString("sub_category_id");
            previousSubCat = sub_category_id.split(",");

            String service_id = userJsonObject.getString("service_id");
            String profile_pic = userJsonObject.getString("profile_pic");

            String role = userJsonObject.getString("role");
            gender = userJsonObject.getString("gender");
            for (int j = 0; j < arrayGender.size(); j++) {
                if (gender.equalsIgnoreCase(arrayGender.get(j).get("name"))) {
                    spGender.setSelection(j);
                    break;
                }
            }
            if (!userJsonObject.getString("gender").equalsIgnoreCase("null")) {
                tvAccountName.setText(gender);
            } else {
                tvAccountName.setText("N/A");
            }
            address = userJsonObject.getString("address");

            if (!userJsonObject.getString("address").equalsIgnoreCase("null")) {
                tvaddress.setText(address);
                etpermanentAddress.setText(address);
            } else {
                tvaddress.setText("N/A");
                etpermanentAddress.setText("");
            }
            pincode = userJsonObject.getString("pincode");

            if (!userJsonObject.getString("pincode").equalsIgnoreCase("null")) {
                tvPiincode.setText(pincode);
                etPincode.setText(pincode);
            } else {
                tvPiincode.setText("N/A");
                etPincode.setText("");
            }
            first_name = userJsonObject.getString("first_name");

            if (!userJsonObject.getString("first_name").equalsIgnoreCase("null")) {
                tvAccNo.setText(first_name);
                etfName.setText(first_name);
            } else {
                tvAccNo.setText("N/A");

            }

            last_name = userJsonObject.getString("last_name");
            if (!userJsonObject.getString("last_name").equalsIgnoreCase("null")) {
                etlName.setText(last_name);
            } else {
                etlName.setText("");
            }

            business_type = userJsonObject.getString("business_type");
            website = userJsonObject.getString("website");
            if (!userJsonObject.getString("website").equalsIgnoreCase("null")) {
                etwebsite.setText(website);
            } else {
                etwebsite.setText("");
            }


            String referral_id = userJsonObject.getString("referral_id");
            String full_address = userJsonObject.getString("full_address");
            String pan_card_number = userJsonObject.getString("pan_card_number");


            if (!userJsonObject.getString("pan_card_number").equalsIgnoreCase("null")) {
                tvPanNo.setText(pan_card_number);
                etPANnumber.setText(pan_card_number);
            } else {
                tvPanNo.setText("N/A");
                etPANnumber.setText("");
            }
            String aadhaar_card_number = userJsonObject.getString("aadhaar_card_number");
            if (!userJsonObject.getString("aadhaar_card_number").equalsIgnoreCase("null")) {
                putAdharcardNO.setText(aadhaar_card_number);
                putAdharNO.setText(aadhaar_card_number);
                etAdharNumber.setText(aadhaar_card_number);
            } else {
                putAdharcardNO.setText("N/A");
                putAdharNO.setText("N/A");
                etAdharNumber.setText("");

            }

            String business_proof_number = userJsonObject.getString("business_proof_number");

            if (!userJsonObject.getString("business_proof_number").equalsIgnoreCase("null")) {
                putGSTNO.setText(business_proof_number);
                etGSTNumber.setText(business_proof_number);
                relBusinessProof.setVisibility(View.VISIBLE);
            } else {
                relBusinessProof.setVisibility(View.GONE);
                etGSTNumber.setText("");
            }

            String business_address = userJsonObject.getString("business_address");
            account_type = userJsonObject.getString("account_type");
            for (int j = 0; j < arrayType.size(); j++) {
                if (account_type.equalsIgnoreCase(arrayType.get(j).get("name"))) {
                    spType.setSelection(j);
                    break;
                }
            }
            account_name = userJsonObject.getString("account_name");
            if (!account_name.equalsIgnoreCase("null")) {
                etAccountName.setText(account_name);
            } else {
                etAccountName.setText("");
            }
            account_number = userJsonObject.getString("account_number");
            if (!account_number.equalsIgnoreCase("null")) {
                etAccountnumber.setText(account_number);
            } else {
                etAccountnumber.setText("");
            }
            ifsc_code = userJsonObject.getString("ifsc_code");
            if (!ifsc_code.equalsIgnoreCase("null")) {
                etifscCode.setText(ifsc_code);
            } else {
                etifscCode.setText("");
            }

            bank_name = userJsonObject.getString("bank_name");

            if (!bank_name.equalsIgnoreCase("null")) {
                etbank_name.setText(bank_name);
            } else {
                etbank_name.setText("");
            }
            bank_branch = userJsonObject.getString("bank_branch");

            if (!bank_branch.equalsIgnoreCase("null")) {
                etbank_branch.setText(bank_branch);
            } else {
                etbank_branch.setText("");
            }
            String pan_card_document = userJsonObject.getString("pan_card_document");
            String aadhaar_card_front = userJsonObject.getString("aadhaar_card_front");
            String aadhaar_card_back = userJsonObject.getString("aadhaar_card_back");
            String business_proof_document = userJsonObject.getString("business_proof_document");
            String cancelled_cheque_img = userJsonObject.getString("cancelled_cheque_img");
            String photographs = userJsonObject.getString("photographs");
            String other_documents = userJsonObject.getString("other_documents");
            String country_id = userJsonObject.getString("country_id");
            String landmark = userJsonObject.getString("landmark");
            String status = userJsonObject.getString("status");
            String email_verified_at = userJsonObject.getString("email_verified_at");
            String current_team_id = userJsonObject.getString("current_team_id");
            String profile_photo_path = userJsonObject.getString("profile_photo_path");

            if (profile_photo_path != null) {

                Picasso.get().load(AppUrls.profileImageUrl + profile_photo_path).into(ivCustomer);

            }
            String wallet_amount = userJsonObject.getString("wallet_amount");
            String unique_id = userJsonObject.getString("unique_id");
            uniqueId.setText(unique_id);
            String total_loan = userJsonObject.getString("total_loan");
            String remaining_loan = userJsonObject.getString("remaining_loan");
            dob = userJsonObject.getString("dob");

            if (!userJsonObject.getString("dob").equalsIgnoreCase("null")) {
                tvAccountName1.setText(dob);
                calender.setText(dob);
            } else {
                tvAccountName1.setText("N/A");
                calender.setText("N/A");
            }


            String father_name = userJsonObject.getString("father_name");
            String sender_amount = userJsonObject.getString("sender_amount");
            String receiver_amount = userJsonObject.getString("receiver_amount");
            String sender_referral_status = userJsonObject.getString("sender_referral_status");
            String receiver_referral_status = userJsonObject.getString("receiver_referral_status");
            serve_state_id = userJsonObject.getString("serve_state_id");
            JSONObject get_serve_city = userJsonObject.getJSONObject("get_serve_city");
            String serve_cityid = get_serve_city.getString("id");
            String serve_citystate_id = get_serve_city.getString("state_id");
            serve_cityname = get_serve_city.getString("name");
            Log.v("MySelectedValue","servercitydata 1 "+serve_cityname);
            GetServeCity(serve_state_id);
            getPincode(serve_citystate_id, serve_cityid);
            getCategory(serve_cityid);
            for (int j = 0; j < ServestateList.size(); j++) {
                if (serve_state_id.equalsIgnoreCase(ServestateList.get(j).get("Id"))) {
                    spServeState.setSelection(j);
                    break;
                }
            }
          /*  serve_city_id = userJsonObject.getString("serve_city_id");
            for (int j = 0; j < ServecityList.size(); j++) {
                if (serve_city_id.equalsIgnoreCase(ServecityList.get(j).get("name"))) {
                    spServeCity.setSelection(j);
                    break;
                }
            }*/
            serve_pinxcodes = userJsonObject.getString("serve_pincodes");
            previousPinCat = serve_pinxcodes.split(",");
            String mobile_verified = userJsonObject.getString("mobile_verified");
            String vaccinated = userJsonObject.getString("vaccinated");
            String vaccine_file = userJsonObject.getString("vaccine_file");
            String created_at = userJsonObject.getString("created_at");
            String updated_at = userJsonObject.getString("updated_at");
            String deleted_at = userJsonObject.getString("deleted_at");

            JSONObject get_state = userJsonObject.getJSONObject("get_state");
            String stateid = get_state.getString("id");
            String statecountry_id = get_state.getString("country_id");
            String statename = get_state.getString("name");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    for (int j = 0; j < stateList.size(); j++) {
                        if (statename.equalsIgnoreCase(stateList.get(j).get("name"))) {
                            spState.setSelection(j);
                            //  Toast.makeText(mActivity,stateid+ "", Toast.LENGTH_SHORT).show();
                            GetCity(stateid);
                            break;
                        }
                    }
                }
            }, 2000);

            if (!get_state.getString("name").equalsIgnoreCase("null")) {
                etstate.setText(get_state.getString("name"));
            } else {
                etCity.setText("N/A");

            }
            String statecreated_at = get_state.getString("created_at");
            String stateupdated_at = get_state.getString("updated_at");
            JSONObject get_city = userJsonObject.getJSONObject("get_city");
            String cityid = get_city.getString("id");
            String citystate_id = get_city.getString("state_id");
            String cityname = get_city.getString("name");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    for (int j = 0; j < cityList.size(); j++) {
                        if (cityname.equalsIgnoreCase(cityList.get(j).get("name"))) {
                            Log.e("citynamecityname", cityname);
                            Log.e("cityListcityList", cityList.get(j).get("name"));
                            spCity.setSelection(j);
                            break;
                        }
                    }

                }
            }, 4000);


            if (!get_city.getString("name").equalsIgnoreCase("null")) {
                etCity.setText(get_city.getString("name"));
            } else {
                etCity.setText("N/A");

            }
            JSONObject get_serve_state = userJsonObject.getJSONObject("get_serve_state");
            String serve_stateid = get_serve_state.getString("id");
            String serve_statecountry_id = get_serve_state.getString("country_id");
            String serve_statename = get_serve_state.getString("name");
            String serve_statecreated_at = get_serve_state.getString("created_at");
            String serve_stateupdated_at = get_serve_state.getString("updated_at");


//            getPincode(serve_citystate_id, serve_cityname);
//            getCategory(serve_cityname);`
            /* spinState.setSelection(Integer.parseInt(state_name));
        for(int j=0;j<cityArrayList.size();j++){
            if(city_name.equalsIgnoreCase(cityArrayList.get(j).get("id"))){
                spinCity.setSelection(j);
                break;
            }
        }*/
            /*for(int j=0;j<ServecityList.size();j++){
                if(serve_cityname.equalsIgnoreCase(ServecityList.get(j).get("name"))){
                    spServeCity.setSelection(j);
                    break;
                }
            }*/
            String serve_citycreated_at = get_serve_city.getString("created_at");
            String serve_cityupdated_at = get_serve_city.getString("updated_at");

            if (!business_proof_document.equalsIgnoreCase("null")) {
                addGSTDetail.setVisibility(View.GONE);
                editGStDetal.setVisibility(View.VISIBLE);
                llGsteditable.setVisibility(View.GONE);

                Picasso.get().load(AppUrls.documents_image_url + business_proof_document).into(ivGST);


            } else {
                addGSTDetail.setVisibility(View.VISIBLE);
                editGStDetal.setVisibility(View.GONE);
                llGsteditable.setVisibility(View.GONE);
            }

            if (!cancelled_cheque_img.equalsIgnoreCase("null")) {

                addcancelledcheque.setVisibility(View.GONE);
                llcancelledchequeeditable.setVisibility(View.VISIBLE);
                editcancelledcheque.setVisibility(View.VISIBLE);
                Picasso.get().load(AppUrls.documents_image_url + cancelled_cheque_img).into(ivcancelledcheque);
                ivcancelledcheque.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + cancelled_cheque_img);
                    }
                });
            } else {
                addcancelledcheque.setVisibility(View.VISIBLE);
                llcancelledchequeeditable.setVisibility(View.GONE);
                editcancelledcheque.setVisibility(View.GONE);
            }

            if (!photographs.equalsIgnoreCase("null")) {
                addPhotographs.setVisibility(View.GONE);
                llPhotographseditable.setVisibility(View.VISIBLE);
                editPhotographs.setVisibility(View.VISIBLE);
                Picasso.get().load(AppUrls.documents_image_url + photographs).into(ivPhotographs);
                ivPhotographs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + photographs);
                    }
                });
            } else {
                addPhotographs.setVisibility(View.VISIBLE);
                llPhotographseditable.setVisibility(View.GONE);
                editPhotographs.setVisibility(View.GONE);
            }
            if (!other_documents.equalsIgnoreCase("null")) {
                addOtherDocs.setVisibility(View.GONE);
                llOtherDocseditable.setVisibility(View.VISIBLE);
                editOtherDocs.setVisibility(View.VISIBLE);
                Picasso.get().load(AppUrls.documents_image_url + other_documents).into(ivOtherDocs);
                ivOtherDocs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + other_documents);
                    }
                });
            } else {
                addOtherDocs.setVisibility(View.VISIBLE);
                llOtherDocseditable.setVisibility(View.GONE);
                editOtherDocs.setVisibility(View.GONE);
            }
            if (!pan_card_document.equalsIgnoreCase("null")) {
                addPANDetail.setVisibility(View.GONE);
                sdd.setVisibility(View.VISIBLE);
                llPaneditable.setVisibility(View.VISIBLE);
                Picasso.get().load(AppUrls.documents_image_url + pan_card_document).into(ivPAN);

                ivPAN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + pan_card_document);
                    }
                });
            } else {
                addPANDetail.setVisibility(View.VISIBLE);
                sdd.setVisibility(View.GONE);
                llPaneditable.setVisibility(View.GONE);
            }
            if (!aadhaar_card_front.equalsIgnoreCase("null")) {
                addADHARDetail.setVisibility(View.GONE);
                llAdhareditable.setVisibility(View.VISIBLE);
                editAdhar.setVisibility(View.VISIBLE);
                Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_front).into(ivAdharFront);


                ivAdharFront.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + aadhaar_card_front);
                    }
                });
            } else {
                addADHARDetail.setVisibility(View.VISIBLE);
                llAdhareditable.setVisibility(View.GONE);
                editAdhar.setVisibility(View.GONE);
            }
            if (!aadhaar_card_back.equalsIgnoreCase("null")) {
                editAdharBack.setVisibility(View.VISIBLE);
                llAdharBackeditable.setVisibility(View.VISIBLE);
                addAdharbackDetail.setVisibility(View.GONE);
                Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_back).into(ivAdharBack);

                ivAdharBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewImage.showDialog(mActivity, AppUrls.documents_image_url + aadhaar_card_back);
                    }
                });
            } else {
                editAdharBack.setVisibility(View.GONE);
                llAdharBackeditable.setVisibility(View.GONE);
                addAdharbackDetail.setVisibility(View.VISIBLE);
            }
            getState();
            getServeState();
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void PostvendorupdateprofileApi(String data1,
                                            String data2,
                                            String data3,
                                            String data4,
                                            String data5,
                                            String data6, String data7, String data8, String data9, String data10, String data11, String data12, String gender, String newDate, String strJobId, String strCityId, String ServestrJobId, String ServestrCityId, String vaccineStatus, File imageFile, String businessType) {
        AppUtils.showRequestDialog(mActivity);
        String url = "http://serviingo.com/api/vendor/update/profile";
        String token = pref.get("token");
        Log.v("ddurl", token);
        AndroidNetworking.post(url)
                .addBodyParameter("first_name", data1)
                .addBodyParameter("last_name", data2)
                .addBodyParameter("business_type", businessType)
                .addBodyParameter("email", data3)
                .addBodyParameter("mobile_number", data4)
                .addBodyParameter("website", data9)
                .addBodyParameter("gender", gender)
                .addBodyParameter("dob", newDate)
                .addBodyParameter("state_id", strJobId)
                .addBodyParameter("city_id", strCityId)
                .addBodyParameter("address", data6)
                .addBodyParameter("pincode", data5)
                .addBodyParameter("serve_state", ServestrJobId)
                .addBodyParameter("serve_city", ServestrCityId)
                .addBodyParameter("serve_pincodes", pincodestatusList.toString())
                .addBodyParameter("categories", category_id)
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("pan_card_number", data11)
                .addBodyParameter("aadhaar_card_number", data12)
                .addBodyParameter("business_proof_number", data10)
                .addBodyParameter("business_address", "1")
                .addBodyParameter("categories", category_id)
                .addBodyParameter("categories", category_id)
                .addBodyParameter("categories", category_id)
                .addBodyParameter("categories", category_id)
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("sub_categories", SubcategoryarrayList.toString())
                .addBodyParameter("serve_pincodes", pincodestatusList.toString())
                .addBodyParameter("serve_pincodes", pincodestatusList.toString())
                .addBodyParameter("serve_pincodes", pincodestatusList.toString())
                .addBodyParameter("serve_pincodes", pincodestatusList.toString())
                .addBodyParameter("_method", "PATCH")
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMGI3MzZjZTMxZjk5MDJmMGRjMTEyYTg5YjZjNjNjM2RkMDllMWUwNGU1MDBjNWYzMzE0YzE5MDZkMDg4ZDFmZGUyZTM0YjIzOGIwNjA4NDMiLCJpYXQiOjE2Mzc2NjYyNzAsIm5iZiI6MTYzNzY2NjI3MCwiZXhwIjoxNjY5MjAyMjcwLCJzdWIiOiIzMyIsInNjb3BlcyI6W119.QCT5PYJcFWSGKPAXYXHKhxAQOSf1bqUDxoF2XhpWMT4rfjIR-RQTLrNmJ2GYz1lHypSAl1GnyLXkbH3SYUTYOV_UW8-opBBHDNAneOv6sBijm0soXXmxnZZt7QhYzSKcm6PuCOXs36KOg_EY6i0Tm1EwMqqVnuLDHMtnqohY51pbtt79yWZzvpRx13ARKiQijRzttCLgglJPwDpbbEMUayxd_QmP5gAs5MlOPgHA_4AUzBfmasb1cXab64mitdRAhfjBctEHuylQspNSwdiwzbVUZqEWlrp7Sq3vUtTXqlVAGLB6Vu1-pR9p1rx5jsMM34P6g82HgfLEBfhpew1NwjKXeTGmF2I--NR6yJ_l1b1UHjI1u2tI_Ebsnl7GacA-Q_o0gES2-X87-eNtKOvBRCfCZitHpaqHtbBeNWA17fIEogGUrNn6-Nb3lY_FF_B3ciY1WKLTQUx6ng2S_HAhGJF_9twlrL5Gn2qNgA7LCoxkCWWFQkryOxXLxuuJBI35OvQ1gFvDg6lcbD89AeFTaabPvg0V0tm-vzI71oLSVKbaMtdD0fjPrKTxWkcoiA7YvLFPMVpt-3CS7mKQOPox239BsutsFd4Fv503BJuUgPNzaU6bu3KSZc9T_8nPjixBtWC0qf3NbNulQ4E1MzfwT5LH1PShp49rfNocrr4c6xs")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePostvendorupdateprofileJsonResponse(response);
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

    private void parsePostvendorupdateprofileJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            // Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private String setTimeValues(String date) {
        try {
            String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
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

    private void UpdateProfilePicture(File file) {
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("statuspicstatuspic", statuspic);

        String url = "https://serviingo.com/api/vendor/update/profile-photo";
        AndroidNetworking.upload(url)
                .addMultipartFile("image", file)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePostupdateprofilephotoJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        if (error.getErrorCode() == 200) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        } else if (error.getErrorCode() == 401) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        } else if (error.getErrorCode() == 500) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void parsePostupdateprofilephotoJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            Log.v("statuspicstatuspic", "empty " + response);


            //  Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            JSONObject dataJsonObject = response.getJSONObject("data");
            JSONObject userJsonObject = dataJsonObject.getJSONObject("user");

            String profile_pic = userJsonObject.getString("profile_photo_path");
            Picasso.get().load(AppUrls.profileImageUrl + profile_pic).into(ivCustomer);


        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void Postvendorupdatedocuments(File filedata) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorupdatedocuments;
        String token = pref.get("token");
        AndroidNetworking.upload(url)
                .addMultipartFile("file", filedata)
                .addMultipartParameter("file_type", "other")
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .addHeaders("Accept", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsePostvendorupdatedocumentsJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            Toast.makeText(mActivity, "Unauthenticated ", Toast.LENGTH_SHORT).show();
                            pref.clear();
                            startActivity(new Intent(getActivity(), loginWithMobile.class));
                        } else {
                            //  Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parsePostvendorupdatedocumentsJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                AppUtils.hideDialog();
                Log.v("responseApiCheck", "empty " + response);
                // Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
                JSONObject JsonObject = response.getJSONObject("data");
                JSONObject dataJsonObject = JsonObject.getJSONObject("user");
                String id = dataJsonObject.getString("id");
                String permission_id = dataJsonObject.getString("permission_id");
                String referral_user_id = dataJsonObject.getString("referral_user_id");
                String referral_master_id = dataJsonObject.getString("referral_master_id");
                String name = dataJsonObject.getString("name");
                String email = dataJsonObject.getString("email");
                String mobile_number = dataJsonObject.getString("mobile_number");
                String state_id = dataJsonObject.getString("state_id");
                String city_id = dataJsonObject.getString("city_id");
                String category_id = dataJsonObject.getString("category_id");


                String aadhaar_card_front = dataJsonObject.getString("aadhaar_card_front");
                String aadhaar_card_back = dataJsonObject.getString("aadhaar_card_back");
                String business_proof_document = dataJsonObject.getString("business_proof_document");
                String cancelled_cheque_img = dataJsonObject.getString("cancelled_cheque_img");
                String photographs = dataJsonObject.getString("photographs");
                String other_documents = dataJsonObject.getString("other_documents");
                String pan_card_document = dataJsonObject.getString("pan_card_document");


                if (!business_proof_document.equalsIgnoreCase("null")) {
                    //  Picasso.get().load(AppUrls.documents_image_url + business_proof_document).placeholder(R.drawable.avtar).into(ivGST);
                    Picasso.get().load(AppUrls.documents_image_url + business_proof_document).into(ivGST);


                }

                if (!cancelled_cheque_img.equalsIgnoreCase("null")) {
                    Picasso.get().load(AppUrls.documents_image_url + cancelled_cheque_img).into(ivcancelledcheque);

                }

                if (!photographs.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + photographs).into(ivPhotographs);

                }
                if (!other_documents.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + other_documents).into(ivOtherDocs);

                }
                if (!pan_card_document.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + pan_card_document).into(ivPAN);

                }
                if (!aadhaar_card_front.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_front).into(ivAdharFront);

                }
                if (!aadhaar_card_back.equalsIgnoreCase("null")) {
                    Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_back).into(ivAdharBack);
                }


                if (mydoc.equalsIgnoreCase("1")) {
                    addGSTDetail.setVisibility(View.GONE);
                    editGStDetal.setVisibility(View.VISIBLE);
                    llGsteditable.setVisibility(View.GONE);
                } else if (mydoc.equalsIgnoreCase("2")) {
                    addPANDetail.setVisibility(View.GONE);
                    sdd.setVisibility(View.VISIBLE);
                    llPaneditable.setVisibility(View.VISIBLE);
                } else if (mydoc.equalsIgnoreCase("3")) {
                    addADHARDetail.setVisibility(View.GONE);
                    llAdhareditable.setVisibility(View.VISIBLE);
                    editAdhar.setVisibility(View.VISIBLE);
                } else if (mydoc.equalsIgnoreCase("4")) {
                    editAdharBack.setVisibility(View.VISIBLE);
                    llAdharBackeditable.setVisibility(View.VISIBLE);
                    addAdharbackDetail.setVisibility(View.GONE);
                } else if (mydoc.equalsIgnoreCase("5")) {
                    addcancelledcheque.setVisibility(View.GONE);
                    llcancelledchequeeditable.setVisibility(View.VISIBLE);
                    editcancelledcheque.setVisibility(View.VISIBLE);

                } else if (mydoc.equalsIgnoreCase("6")) {
                    addPhotographs.setVisibility(View.GONE);
                    llPhotographseditable.setVisibility(View.VISIBLE);
                    editPhotographs.setVisibility(View.VISIBLE);
                } else if (mydoc.equalsIgnoreCase("7")) {
                    addOtherDocs.setVisibility(View.GONE);
                    llOtherDocseditable.setVisibility(View.VISIBLE);
                    editOtherDocs.setVisibility(View.VISIBLE);
                }
                getProfile();

            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                JSONObject errorobject = messageObjectt.getJSONObject("error");
                JSONArray file = errorobject.getJSONArray("file");
                Toast.makeText(mActivity, "" + file.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void Postvendorupdatepassword(String oldpassword, String password, String confirmPassword) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorupdatepassword;
        String token = pref.get("token");
        AndroidNetworking.put(url)
                .addBodyParameter("old_passowrd", oldpassword)
                .addBodyParameter("password", password)
                .addBodyParameter("password_confirmation", confirmPassword)
                .addHeaders("Authorization", "Bearer " + token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        AppUtils.hideDialog();
                        parsePostvendorupdatepasswordJsonResponse(response);
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

    private void parsePostvendorupdatepasswordJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            //  Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
            etoldPassword.setText("");
            etnewPassword.setText("");
            etConfirmPassword1.setText("");

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }

    public void getServeState() {
        AppUtils.hideSoftKeyboard(mActivity);

        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getservestate;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parsegetservestateeJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsegetservestateeJsonResponse(JSONObject response) {
        try {

            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //   Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();

                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("states");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("country_id", block_object.get("country_id").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    ServestateList.add(map);
                }
                spServeState.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, ServestateList));
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }

    private void GetServeCity(String ServestrJobId) {
/* 8528360316*/
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getserveCity + ServestrJobId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parseServeCityJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseServeCityJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                ServecityList.clear();
                //  Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                int selectedservecity=0;
                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("cities");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    ServecityList.add(map);
                    if (serve_cityname.equalsIgnoreCase(block_object.get("name").toString())) {
                        selectedservecity=i;
                    }
                }
                Log.v("MySelectedValue","servercitydata "+selectedservecity);
                Log.v("MySelectedValue","servercitydata "+ServecityList);
                Log.v("MySelectedValue","servercitydata "+serve_cityname);
                spServeCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, ServecityList));
                spServeCity.setSelection(selectedservecity);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getCategory(String ServestrCityId) {
        ArrayList<HashMap<String,String>> newSelectedCategories=new ArrayList<>();

        for(int j=0; j<select_Categories.size();j++){
            if(select_Categories.get(j).get("city_id").equalsIgnoreCase(ServestrCityId)){
                newSelectedCategories.add(select_Categories.get(j));
            }
        }
        select_Categories=newSelectedCategories;
        Log.v("MySelectedCategories",select_Categories.toString());
        AppUtils.hideSoftKeyboard(mActivity);

        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getCategory + ServestrCityId;
        Log.v("dta_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        parseCategoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseCategoryJsonResponse(JSONObject response) {
        try {

            CategoryList.clear();
            Log.v("responseApi_Category", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("city_id", block_object.get("city_id").toString());
                    map.put("name", block_object.get("name").toString());

                    String isSelected = "false";
                    for(int j=0; j<select_Categories.size();j++){
                        if(select_Categories.get(j).get("id").equalsIgnoreCase(block_object.get("id").toString())){
                            isSelected="true";
                        }
                    }
                    map.put("checkCatStatus", isSelected);
                    CategoryList.add(map);
                }
                OfferAdapter3 offerAdapter3 = new OfferAdapter3(CategoryList);
                recyCategories.setAdapter(offerAdapter3);
                offerAdapter3.notifyDataSetChanged();
                getMultiSubcategory();

            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

   /* public void getSubCategory(String categoryId) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getSubCategory + categoryId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseSubcategoryJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseSubcategoryJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            SubCategoryList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //  Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("sub_categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("city_id", block_object.get("city_id").toString());
                    map.put("category_id", block_object.get("category_id").toString());
                    map.put("sub_category_icon", block_object.get("sub_category_icon").toString());
                    map.put("background_image", block_object.get("background_image").toString());
                    map.put("sub_category_name", block_object.get("sub_category_name").toString());
                    map.put("sub_category_slug", block_object.get("sub_category_slug").toString());
                    map.put("short_description", block_object.get("short_description").toString());
                    map.put("features", block_object.get("features").toString());
                    map.put("meta_title", block_object.get("meta_title").toString());
                    map.put("meta_keyword", block_object.get("meta_keyword").toString());
                    map.put("meta_description", block_object.get("meta_description").toString());
                    map.put("status", block_object.get("status").toString());
                    map.put("eligiable_for_all", block_object.get("eligiable_for_all").toString());
                    map.put("servicable_pincode", block_object.get("servicable_pincode").toString());
                    map.put("deleted_at", block_object.get("deleted_at").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    String isSelected = "false";
                    for (int j = 0; j < previousSubCat.length; j++) {
                        if (previousSubCat[j].equalsIgnoreCase(block_object.get("id").toString())) {
                            isSelected = "true";
                            break;
                        }
                    }
                    map.put("checkSubStatus", isSelected);
                    SubCategoryList.add(map);
                }
                OfferAdapter2 offerAdapter2 = new OfferAdapter2(SubCategoryList);
                recySubCategories.setAdapter(offerAdapter2);
                offerAdapter2.notifyDataSetChanged();
*//*
                spSubCategory.setAdapter(new adapter_spinner2(mActivity, R.layout.spinner_textview_rel, SubCategoryList));
*//*
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }*/

    private void PostMultipledocument(String Typedata, File imageFile) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorupdatedocuments;
        String token = pref.get("token");
        AndroidNetworking.upload(url)
                .addMultipartFile("file", imageFile)
                .addMultipartParameter("file_type", Typedata)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .addHeaders("Accept", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseMultipledocumentJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        AppUtils.hideDialog();
                        // handle error
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                            Toast.makeText(mActivity, "Unauthenticated ", Toast.LENGTH_SHORT).show();
                            pref.clear();
                            startActivity(new Intent(getActivity(), loginWithMobile.class));
                        } else {
                            //  Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseMultipledocumentJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                AppUtils.hideDialog();
                Log.v("responseApiCheck", "empty " + response);
                //  Toast.makeText(mActivity, "" + response.getString("message"), Toast.LENGTH_LONG).show();
                JSONObject JsonObject = response.getJSONObject("data");
                JSONObject dataJsonObject = JsonObject.getJSONObject("user");
                String id = dataJsonObject.getString("id");
                String permission_id = dataJsonObject.getString("permission_id");
                String referral_user_id = dataJsonObject.getString("referral_user_id");
                String referral_master_id = dataJsonObject.getString("referral_master_id");
                String name = dataJsonObject.getString("name");
                String email = dataJsonObject.getString("email");
                String mobile_number = dataJsonObject.getString("mobile_number");
                String state_id = dataJsonObject.getString("state_id");
                String city_id = dataJsonObject.getString("city_id");
                String category_id = dataJsonObject.getString("category_id");

                String aadhaar_card_front = dataJsonObject.getString("aadhaar_card_front");
                String aadhaar_card_back = dataJsonObject.getString("aadhaar_card_back");
                String business_proof_document = dataJsonObject.getString("business_proof_document");
                String cancelled_cheque_img = dataJsonObject.getString("cancelled_cheque_img");
                String photographs = dataJsonObject.getString("photographs");
                String other_documents = dataJsonObject.getString("other_documents");
                String pan_card_document = dataJsonObject.getString("pan_card_document");


                if (!business_proof_document.equalsIgnoreCase("null")) {
                    Picasso.get().load(AppUrls.documents_image_url + business_proof_document).into(ivGST);

                }

                if (!cancelled_cheque_img.equalsIgnoreCase("null")) {
                    Picasso.get().load(AppUrls.documents_image_url + cancelled_cheque_img).into(ivcancelledcheque);
                }

                if (!photographs.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + photographs).into(ivPhotographs);

                }
                if (!other_documents.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + other_documents).into(ivOtherDocs);

                }
                if (!pan_card_document.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + pan_card_document).into(ivPAN);

                }
                if (!aadhaar_card_front.equalsIgnoreCase("null")) {

                    Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_front).into(ivAdharFront);

                }
                if (!aadhaar_card_back.equalsIgnoreCase("null")) {
                    Picasso.get().load(AppUrls.documents_image_url + aadhaar_card_back).into(ivAdharBack);
                }
                getProfile();


            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                JSONObject errorobject = messageObjectt.getJSONObject("error");
                JSONArray file = errorobject.getJSONArray("file");
                Toast.makeText(mActivity, "" + file.toString(), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    @Override
    public void onOTPVerifyError(String message) {
        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakWarning();
    }

    @Override
    public void onupdateProfileSuccess(UpdateprofileUserRepo response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(mActivity)
                    .setTitle(response.getMessage())
                    .setMessage("")
                    .sneakWarning();

            Toast.makeText(getContext(), response.getMessage() + "", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onupdatPaswordSuccess(ResponseBody response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Sneaker.with(mActivity)
                    .setTitle("Password Updated Sucessfully")
                    .setMessage("")
                    .sneakWarning();

            Toast.makeText(mActivity, "Password Updated Sucessfully", Toast.LENGTH_LONG).show();
            etoldPassword.setText("");
            etnewPassword.setText("");
            etConfirmPassword1.setText("");

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
    public void onOTPVerifyFailure(Throwable t) {
        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakWarning();
    }

    private void getMultiSubcategory() {
        isSelectAllSubCat=true;
        makeSubSelection();

        ArrayList<HashMap<String,String>> newSavedCategories=new ArrayList<>();

        HashMap<String ,String> params=new HashMap<String, String>();
        params.put("categories[0]","");
        for (int i = 0; i < select_Categories.size(); i++) {
            params.put("categories["+i+"]",select_Categories.get(i).get("id"));
            for(int j=0; j<select_SubCategories.size();j++){
                if(select_SubCategories.get(j).get("category_id").equalsIgnoreCase(select_Categories.get(i).get("id"))){
                    newSavedCategories.add(select_SubCategories.get(j));
                }
            }
        }
        select_SubCategories=newSavedCategories;
        Log.v("MyCategoriesSelected","fetch "+params);
        Log.v("MyCategoriesSelected","selected "+params);

        String token = pref.get("token");
        Log.v("ddurl", token);

        String url = AppUrls.Postvendormultiplesubcategories;
        AndroidNetworking.post(url)
                .addBodyParameter(params)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

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
            SubCategoryList.clear();
            Log.v("resseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("sub_categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("city_id", block_object.get("city_id").toString());
                    map.put("category_id", block_object.get("category_id").toString());
                    map.put("sub_category_icon", block_object.get("sub_category_icon").toString());
                    map.put("background_image", block_object.get("background_image").toString());
                    map.put("sub_category_name", block_object.get("sub_category_name").toString());
                    map.put("sub_category_slug", block_object.get("sub_category_slug").toString());
                    map.put("short_description", block_object.get("short_description").toString());
                    map.put("features", block_object.get("features").toString());
                    map.put("meta_title", block_object.get("meta_title").toString());
                    map.put("meta_keyword", block_object.get("meta_keyword").toString());
                    map.put("meta_description", block_object.get("meta_description").toString());
                    map.put("status", block_object.get("status").toString());
                    map.put("eligiable_for_all", block_object.get("eligiable_for_all").toString());
                    map.put("servicable_pincode", block_object.get("servicable_pincode").toString());
                    map.put("position", block_object.get("position").toString());
                    map.put("deleted_at", block_object.get("deleted_at").toString());


                    String isSelected = "false";
                    for(int j=0; j<select_SubCategories.size();j++){
                        if(select_SubCategories.get(j).get("id").equalsIgnoreCase(block_object.get("id").toString())){
                            isSelected="true";
                            break;
                        }
                    }
                    map.put("checkSubStatus", isSelected);


/*
                    String isSelected = "false";
                    for (int j = 0; j < previousSubCat.length; j++) {
                        if (previousSubCat[j].equalsIgnoreCase(block_object.get("id").toString())) {
                            isSelected = "true";
                            break;
                        }
                    }
                    for (int j = 0; j < tempsubstorelist.size(); j++) {
                        if (tempsubstorelist.get(j).get("id").equalsIgnoreCase(block_object.get("id").toString())) {
                            isSelected = "true";
                            break;
                        }
                    }
       */           //  printLogData();
                    SubCategoryList.add(map);
                }
              /*  Collections.sort(SubCategoryList, new Comparator<HashMap<String, String>>() {
                    public int compare(HashMap<String, String> mapping1, HashMap<String, String> mapping2) {
                        return mapping2.get("checkSubStatus").compareTo(mapping1.get("checkSubStatus"));

                    }
                });*/

                OfferAdapter2 offerAdapter2 = new OfferAdapter2(SubCategoryList);
                recySubCategories.setAdapter(offerAdapter2);
                offerAdapter2.notifyDataSetChanged();


            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void printLogData() {
        Log.v("MyArrayCatCe","prevSubCat "+sub_category_id);
        Log.v("MyArrayCatCe","prevCat "+category_id);

        Log.v("MyArrayCatCe","tempSubCat "+tempsubstorelist);

        CategoryarrayList.clear();
        for (int i = 0; i < CategoryList.size(); i++) {
            if (CategoryList.get(i).get("checkCatStatus").equalsIgnoreCase("true"))
                CategoryarrayList.add(Integer.valueOf(CategoryList.get(i).get("id")));
        }
        SubcategoryarrayList.clear();
        for (int i = 0; i < SubCategoryList.size(); i++) {
            if (SubCategoryList.get(i).get("checkSubStatus").equalsIgnoreCase("true"))
                SubcategoryarrayList.add(Integer.valueOf(SubCategoryList.get(i).get("id")));
        }
        Log.v("MyArrayCatCe","secondSubCat "+SubcategoryarrayList);
        Log.v("MyArrayCatCe","secondCat "+CategoryarrayList);


    }


    public class adapter_spinner1 extends ArrayAdapter<HashMap<String, String>> {
        ArrayList<HashMap<String, String>> data;

        public adapter_spinner1(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> arraySpinner_time) {
            super(context, textViewResourceId, arraySpinner_time);
            this.data = arraySpinner_time;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View row = inflater.inflate(R.layout.spinner_textview_rel, parent, false);
            TextView label = row.findViewById(R.id.tv_spinner_name);
            label.setText(data.get(position).get("name"));
            return row;
        }
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_subcategories, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.textcheck.setText(data.get(position).get("pincode"));


            data.get(position).get("checkStatus");
            if (data.get(position).get("checkStatus").equalsIgnoreCase("false")) {
                holder.ivPinCheck.setImageResource(R.drawable.checked);
            } else {
                holder.ivPinCheck.setImageResource(R.drawable.uncheck);
            }
            holder.ivPinCheck.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (data.get(position).get("checkStatus").equalsIgnoreCase("false")) {
                        data.get(position).replace("checkStatus", "false", "true");
                    }  else {
                        isSelectAllPincodes = true;
                        makePincodeSelection();
                        data.get(position).replace("checkStatus", "true", "false");
                    }
                } else {

                    if (data.get(position).get("checkStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkStatus");
                        data.get(position).put("checkStatus", "true");
                    } else {
                        isSelectAllPincodes=true;
                        makePincodeSelection();
                        data.get(position).remove("checkStatus");
                        data.get(position).put("checkStatus", "false");
                    }
                }
                notifyDataSetChanged();
            });
        }

        public int getItemCount() {
            return data.size();
        }
    }
    private class OfferHolder extends RecyclerView.ViewHolder {
        TextView textcheck;
        ImageView ivPinCheck;

        public OfferHolder(View itemView) {
            super(itemView);
            textcheck = itemView.findViewById(R.id.textcheck);
            ivPinCheck = itemView.findViewById(R.id.ivPinCheck);
        }
    }

    public class OfferAdapter2 extends RecyclerView.Adapter<OfferHolder2> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter2(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_subcategories, parent, false);
            return new OfferHolder2(itemView);
        }

        public void onBindViewHolder(OfferHolder2 holder, final int position) {
            holder.textcheck.setText(data.get(position).get("sub_category_name"));


            if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                holder.ivPinCheck.setImageResource(R.drawable.checked);
            } else {
                holder.ivPinCheck.setImageResource(R.drawable.uncheck);
            }
            holder.ivPinCheck.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                        data.get(position).replace("checkSubStatus", "false", "true");
                        select_SubCategories.add(data.get(position));
                    }
                    else {
                        isSelectAllSubCat=true;
                        makeSubSelection();
                        data.get(position).replace("checkSubStatus", "true", "false");
                        for(int k=0;k<select_SubCategories.size();k++){
                            if(select_SubCategories.get(k).get("id").equalsIgnoreCase(data.get(position).get("id"))){
                                select_SubCategories.remove(k);
                                break;
                            }
                        }
                    }
                } else {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "true");
                        select_SubCategories.add(data.get(position));
                    } else {
                        isSelectAllSubCat=true;
                        makeSubSelection();
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "false");
                        for(int k=0;k<select_SubCategories.size();k++){
                            if(select_SubCategories.get(k).get("id").equalsIgnoreCase(data.get(position).get("id"))){
                                select_SubCategories.remove(k);
                                break;
                            }
                        }
                    }
                }
                Log.v("MyCategoriesSelected","subCat "+select_SubCategories);
 /*               tempsubstorelist.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).get("checkSubStatus").equalsIgnoreCase("true")) {
                        tempsubstorelist.add(data.get(position));
                    }
                }
 */               Log.v("FinalSelectedSub"," "+tempsubstorelist);
                notifyDataSetChanged();
            });
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder2 extends RecyclerView.ViewHolder {
        TextView textcheck;
        ImageView ivPinCheck;

        public OfferHolder2(View itemView) {
            super(itemView);
            textcheck = itemView.findViewById(R.id.textcheck);
            ivPinCheck = itemView.findViewById(R.id.ivPinCheck);
        }
    }
    public class OfferAdapter3 extends RecyclerView.Adapter<OfferHolder3> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter3(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_subcategories, parent, false);
            return new OfferHolder3(itemView);
        }

        public void onBindViewHolder(OfferHolder3 holder, final int position) {
            holder.textcheck.setText(data.get(position).get("name"));
            if (data.get(position).get("checkCatStatus").equalsIgnoreCase("false")) {
                holder.ivPinCheck.setImageResource(R.drawable.checked);
            } else {
                holder.ivPinCheck.setImageResource(R.drawable.uncheck);
            }
            holder.ivPinCheck.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (data.get(position).get("checkCatStatus").equalsIgnoreCase("false")) {
                        data.get(position).replace("checkCatStatus", "false", "true");
                        select_Categories.add(data.get(position));
                        Log.v("MyCategoriesSelected","add "+select_Categories);
                        getMultiSubcategory();
                    }
                    else {
                        isSelectAllCat=true;
                        makeSelection();
                        data.get(position).replace("checkCatStatus", "true", "false");
                        filterCategoryData(data.get(position).get("id"));
                    }
                } else {
                    if (data.get(position).get("checkCatStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkCatStatus");
                        data.get(position).put("checkCatStatus", "true");
                        select_Categories.add(data.get(position));
                        Log.v("MyCategoriesSelected","add "+select_Categories);
                        getMultiSubcategory();

                    } else {
                        isSelectAllCat=true;
                        makeSelection();
                        data.get(position).remove("checkCatStatus");
                        data.get(position).put("checkCatStatus", "false");
                        filterCategoryData(data.get(position).get("id"));
                    }

                }

                notifyDataSetChanged();
            });
        }


        public int getItemCount() {
            return data.size();
        }
    }

    private void filterCategoryData(String id) {
        for(int i=0;i<select_Categories.size();i++){
            if(select_Categories.get(i).get("id").equalsIgnoreCase(id)){
                select_Categories.remove(i);
            }
        }
        for(int i=0;i<select_SubCategories.size();i++){
            if(select_SubCategories.get(i).get("category_id").equalsIgnoreCase(id)){
                select_SubCategories.remove(i);
            }
        }
        Log.v("MyCategoriesSelected","remove "+select_Categories);
        Log.v("MyCategoriesSelected","removeSub "+select_SubCategories);

        getMultiSubcategory();
    }

    private class OfferHolder3 extends RecyclerView.ViewHolder {
        TextView textcheck;
        ImageView ivPinCheck;

        public OfferHolder3(View itemView) {
            super(itemView);
            textcheck = itemView.findViewById(R.id.textcheck);
            ivPinCheck = itemView.findViewById(R.id.ivPinCheck);
        }
    }
  /*  public class OfferAdapter3 extends RecyclerView.Adapter<OfferHolder3> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter3(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public OfferHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_subcategories, parent, false);
            return new OfferHolder3(itemView);
        }

        public void onBindViewHolder(OfferHolder3 holder, final int position) {
            holder.textcheck.setText(data.get(position).get("name"));


            //String twoSection = modelArrayList.getData().getTwoSection().getCategoriesIds();

            ArrayList<String> twoSectionList = new ArrayList(Arrays.asList(category_idd.split(",")));
            for (int i = 0; i < twoSectionList.size(); i++) {

                if (data.get(position).get("id").equalsIgnoreCase(twoSectionList.get(i))) {

                    holder.ivPinCheck.setImageResource(R.drawable.checked);

                } else {
                    holder.ivPinCheck.setImageResource(R.drawable.uncheck);
                }
            }




         *//*   if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                holder.ivPinCheck.setImageResource(R.drawable.checked);
            } else {
                holder.ivPinCheck.setImageResource(R.drawable.uncheck);
            }
            holder.ivPinCheck.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false"))
                        data.get(position).replace("checkSubStatus", "false", "true");
                    else
                        data.get(position).replace("checkSubStatus", "true", "false");
                } else {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "true");
                    } else {
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "false");
                    }
                }
                notifyDataSetChanged();
            });*//*
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder3 extends RecyclerView.ViewHolder {
        TextView textcheck;
        ImageView ivPinCheck;

        public OfferHolder3(View itemView) {
            super(itemView);
            textcheck = itemView.findViewById(R.id.textcheck);
            ivPinCheck = itemView.findViewById(R.id.ivPinCheck);
        }
    }*/

    public class ViewImage {

        AlertDialog alertDialog;
        ImageView imageviewMesg;


        public void showDialog(Activity activity, String image) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            View view = inflater.inflate(R.layout.viewimagemessage, null);
            alertDialog = new AlertDialog.Builder(activity)
                    .setView(view)
                    .setCancelable(false)
                    .create();


            ImageView close = view.findViewById(R.id.close);
            imageviewMesg = view.findViewById(R.id.messageview);
            Picasso.get().load(image).into(imageviewMesg);

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


            alertDialog.show();
        }


    }

   /* public void documentclick(String url)
    {


        ivPAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);
            }
        });
        ivAdharFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);

            }
        });
        ivAdharBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);

            }
        });

        ivcancelledcheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);

            }
        });
        ivPhotographs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);

            }
        });

        ivOtherDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage.showDialog(mActivity,url);

            }
        });


    }*/
}
