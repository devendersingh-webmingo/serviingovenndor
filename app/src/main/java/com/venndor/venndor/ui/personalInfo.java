package com.venndor.venndor.ui;

import android.Manifest;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Common.SimpleHTTPConnection;
import com.venndor.venndor.ui.Presenter.Repo.LoginWithOTPReop;
import com.venndor.venndor.ui.Presenter.Repo.SignupUserRepo;
import com.venndor.venndor.ui.Presenter.Request.SignUpUser_request;
import com.venndor.venndor.ui.Presenter.SignUpUseerPresenter;
import com.venndor.venndor.ui.Profile.MyProfile;
import com.venndor.venndor.ui.Rtrofit.ApiManager;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class personalInfo extends BaseActivity implements SignUpUseerPresenter.SignUpUseerView,View.OnClickListener {
    private static final int SELECT_PICTURE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Uniquedaam";
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    LabeledSwitch labeledSwitch;
    String vaccineStatus = "";
    Calendar myCalendar;
    TextView tvCalender, tvchooseProfile;
    Spinner spGender, spState, spCity, spServeState, spServeCity, spCategory, spSubCategory;
    EditText etfName, etlName, etEmail, etContactNumber, etpermanentAddress,
            etPincode, etPassword, etConfirmPassword;
    String strJobId = "";
    String strCityId = "";
    String ServestrJobId = "";
    String ServestrCityId = "";
    String newDate = "";
    String gender;
    String categoryId = "";
    Preferences pref;
    RecyclerView recyPincode, recySubcat;
    boolean pinStatus = false;
    LinearLayoutManager layoutManager, sublayoutManager;
    String picturePath = "", filename = "", ext = "";
    Bitmap rotatedBitmap;
    Uri picUri;

    RelativeLayout rl_selectAllSubCategory,rl_selectAllPincodes;
    Boolean isSelectAllSubCat=false,isSelectAllPincodes=false;
    ImageView iv_checkSubCategorys,iv_checkPincodes;



    TextView profname;
    String encodedImage = "";
    File imageFile;
    ArrayList<HashMap<String, String>> pincodeList = new ArrayList<>();
    ArrayList<Integer> pincodestatusList = new ArrayList<Integer>();
    ArrayList<Integer> SubcategoryarrayList = new ArrayList<Integer>();
    LinearLayout llchoosefile;
    ArrayList<HashMap<String, String>> ServestateList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayGender = new ArrayList<>();
    ArrayList<HashMap<String, String>> stateList = new ArrayList<>();
    ArrayList<HashMap<String, String>> cityList = new ArrayList<>();
    ArrayList<HashMap<String, String>> ServecityList = new ArrayList<>();
    ArrayList<HashMap<String, String>> CategoryList = new ArrayList<>();
    ArrayList<HashMap<String, String>> SubCategoryList = new ArrayList<>();
    private Bitmap bitmap;
    SignUpUseerPresenter presenter;
    private static final int PICK_IMAGE = 100;

    Uri imageUri;
    String pincodes[];
    String sub_categories[];
    File file;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
        init();
    }

    private void init() {
        pref = new Preferences(mActivity);
        presenter = new SignUpUseerPresenter(this);

        etfName = findViewById(R.id.etfName);
        etlName = findViewById(R.id.etlName);
        etEmail = findViewById(R.id.etEmail);
        etContactNumber = findViewById(R.id.etContactNumber);
        etContactNumber.setText(pref.get("mobilenumber"));
        etpermanentAddress = findViewById(R.id.etpermanentAddress);
        etPincode = findViewById(R.id.etPincode);
        etPassword = findViewById(R.id.etPassword);
        profname = findViewById(R.id.profname);
        recyPincode = findViewById(R.id.recyPincode);
        recySubcat = findViewById(R.id.recySubcat);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyPincode.setLayoutManager(layoutManager);
        sublayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recySubcat.setLayoutManager(sublayoutManager);

        spServeState = findViewById(R.id.spServeState);
        spServeCity = findViewById(R.id.spServeCity);

        rl_selectAllSubCategory = findViewById(R.id.rl_selectAllSubCategory);
        iv_checkSubCategorys = findViewById(R.id.iv_checkSubCategorys);
        rl_selectAllPincodes = findViewById(R.id.rl_selectAllPincodes);
        iv_checkPincodes = findViewById(R.id.iv_checkPincodes);

        spCategory = findViewById(R.id.spCategory);
        spSubCategory = findViewById(R.id.spSubCategory);
        spGender = findViewById(R.id.spGender);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        tvCalender = findViewById(R.id.calender);
        tvchooseProfile = findViewById(R.id.tvchooseProfile);


        tvchooseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissions()) {


                    On_click_OpenGallery();
                    /*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);*/
                } else {
                    //AppUtils.showToastSort(mActivity, getString(R.string.give_permission));
                    Toast.makeText(mActivity, getString(R.string.give_permission), Toast.LENGTH_SHORT).show();


                }
            }
        });
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
                //  String myFormat = "dd-MM-yyyy"; //In which you need put here

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
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

        // ImageView imagepass = findViewById(R.id.eye1);
/*
        ImageView imagepass2 = findViewById(R.id.eye2);
*/
     /*   imagepass.setOnTouchListener(new View.OnTouchListener() {
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
        });*/
       /* imagepass2.setOnTouchListener(new View.OnTouchListener() {
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
        });*/



        if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
            getState();
            getServeState();
            populateSpinnerFrom(spGender);
        } else {
            //  AppUtils.showErrorMessage(etEmail, getString(R.string.errorInternet), mActivity);
            Toast.makeText(this, getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
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

                if (position == 0) {

                    //  Toast.makeText(personalInfo.this, position + "", Toast.LENGTH_SHORT).show();
                } else {

                    gender = arrayGender.get(position).get("name");

                }

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
                Log.v("MYState", ServecityList.get(position).get("name") + "00");
                /* if (position != 0) {*/
                ServestrCityId = ServecityList.get(position).get("id");
                if (SimpleHTTPConnection.isNetworkAvailable(mActivity)) {
                    getCategory(ServestrCityId);
                    getPincode(ServestrJobId, ServestrCityId);
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
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        });

        labeledSwitch = findViewById(R.id.simpleswitch);
        llchoosefile = findViewById(R.id.llchoosefile);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true) {
                    vaccineStatus = "yes";
                    llchoosefile.setVisibility(View.VISIBLE);
                } else {
                    vaccineStatus = "no";
                    llchoosefile.setVisibility(View.GONE);
                }
            }


        });
        rl_selectAllSubCategory.setOnClickListener(this);
        rl_selectAllPincodes.setOnClickListener(this);

        findViewById(R.id.llSendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data1 = etfName.getText().toString().trim();
                String data2 = etlName.getText().toString().trim();
                String data3 = etEmail.getText().toString().trim();
                String data4 = etContactNumber.getText().toString().trim();
                String data6 = etpermanentAddress.getText().toString().trim();

                String data5 = etPincode.getText().toString().trim();
                String data7 = etPassword.getText().toString().trim();
                String data8 = etConfirmPassword.getText().toString().trim();

                pincodestatusList.clear();
                //   pincodes[];
                Array sub_categories[];

                for (int i = 0; i < pincodeList.size(); i++) {
                    if (pincodeList.get(i).get("checkStatus").equalsIgnoreCase("true"))
                        pincodestatusList.add(Integer.valueOf(pincodeList.get(i).get("id")));

                    // pincodes.add();
                }

                Log.v("checkStatusArray", pincodestatusList.toString());

                SubcategoryarrayList.clear();
                for (int i = 0; i < SubCategoryList.size(); i++) {
                    if (SubCategoryList.get(i).get("checkSubStatus").equalsIgnoreCase("true"))
                        SubcategoryarrayList.add(Integer.valueOf(SubCategoryList.get(i).get("id")));


                }

                Log.v("checkStatusArray", SubcategoryarrayList.toString());


                if (data1.isEmpty()) {

                    Sneaker.with(personalInfo.this)
                            .setTitle("please enter first name here")
                            .setMessage("")
                            .sneakError();


                } else if (data2.isEmpty()) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("please enter last name here")
                            .setMessage("")
                            .sneakError();

                    // Toast.makeText(personalInfo.this, "please enter last name here", Toast.LENGTH_SHORT).show();

                } else if (data3.isEmpty()) {

                    Sneaker.with(personalInfo.this)
                            .setTitle("please enter email here")
                            .setMessage("")
                            .sneakError();

                    // Toast.makeText(personalInfo.this, "please enter  here", Toast.LENGTH_SHORT).show();

                } else if (data4.isEmpty()) {

                    Sneaker.with(personalInfo.this)
                            .setTitle("please enter Contact Number here")
                            .setMessage("")
                            .sneakError();

                    //Toast.makeText(mActivity, "please enter Contact Number here", Toast.LENGTH_SHORT).show();

                } else if (gender == null) {

                    Sneaker.with(personalInfo.this)
                            .setTitle("please Select gender")
                            .setMessage("")
                            .sneakError();

                    //Toast.makeText(mActivity, "please enter Contact Number here", Toast.LENGTH_SHORT).show();

                } else if (newDate.isEmpty()) {

                    Sneaker.with(personalInfo.this)
                            .setTitle("please Select DOB")
                            .setMessage("")
                            .sneakError();
                } else if (data6.isEmpty()) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("Enter address")
                            .setMessage("")
                            .sneakError();
                } else if (data5.isEmpty()) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("Enter pincode")
                            .setMessage("")
                            .sneakError();
                } else if (data7.isEmpty()) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("Enter your password!")
                            .setMessage("")
                            .sneakError();
                } else if (data7.length() < 6) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("The password must be at least 6 characters!")
                            .setMessage("")
                            .sneakError();
                } else if (!data8.equals(data7)) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("confirm password not match!")
                            .setMessage("")
                            .sneakError();

                } else if (pincodestatusList.size() == 0) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("Please Select Pincode!")
                            .setMessage("")
                            .sneakError();

                } else if (SubcategoryarrayList.size() == 0) {
                    Sneaker.with(personalInfo.this)
                            .setTitle("Please Select Sub Categories!")
                            .setMessage("")
                            .sneakError();

                } else if (profname.getText().toString().trim().isEmpty()) {
                    Sneaker.with(personalInfo.this)
                            .setTitle(" Vaccination Dose Image is requried!")
                            .setMessage("")
                            .sneakError();
                } else {


                    SignUpUser_request upUser_request = new SignUpUser_request(data1, data2, data3, data4, gender, newDate,
                            strJobId, strCityId, data6, data5, ServestrJobId, ServestrCityId, pincodestatusList,
                            categoryId, SubcategoryarrayList, data7, data8, "Yes");


                    presenter.SignUpUseer(personalInfo.this, upUser_request);

                    Log.d("signuprequest", upUser_request.toString());


                    //   PostRegisterApi(data1, data2, data3, data4, data5, data6, data7, data8, vaccineStatus, imageFile);
                }
            }
        });
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

    //StateApi
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
            stateList.clear();
            if (response.getString("status").equals("true")) {
                //   HashMap<String, String> map = new HashMap<String, String>();

                String message = response.getString("message");
                //      Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
               /* HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", "00");
                map.put("country_id", "00");
                map.put("name", "Select State");
                map.put("created_at", "00");
                map.put("updated_at","00");
                stateList.add(map);*/


                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("states");

                HashMap<String, String> mapp = new HashMap<String, String>();
                mapp.put("id", "00");
                mapp.put("country_id", "00");
                mapp.put("name", "Select State");
                mapp.put("created_at", "00");
                mapp.put("updated_at", "00");
                stateList.add(mapp);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();

                    JSONObject block_object = jsonArray.getJSONObject(i);
                    map.put("id", block_object.get("id").toString());
                    map.put("country_id", block_object.get("country_id").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    stateList.add(map);
                }
                spState.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, stateList));
            } else {
            }
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
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //    Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("cities");
                HashMap<String, String> mapp = new HashMap<String, String>();
                mapp.put("id", "00");
                mapp.put("state_id", "00");
                mapp.put("name", "Select City");
                mapp.put("created_at", "00");
                mapp.put("updated_at", "00");
                cityList.add(mapp);

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
            }
            spCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, cityList));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getServeState() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getservestate;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
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
            ServestateList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //    Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

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
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getserveCity + ServestrJobId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseServeCityJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parseServeCityJsonResponse(JSONObject response) {

        try {
            ServecityList.clear();
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //   Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

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
                }
            }
            spServeCity.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, ServecityList));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getCategory(String strCityId) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getCategory + strCityId;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
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
            AppUtils.hideDialog();
            CategoryList.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //    Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
                HashMap<String, String> mapmap = new HashMap<String, String>();

                mapmap.put("id", "00");
                mapmap.put("state_id", "00");
                mapmap.put("city_id","00");
                mapmap.put("name", "Select Category");

                CategoryList.add(mapmap);

                JSONObject jsonObjectt = response.getJSONObject("data");
                JSONArray jsonArray = jsonObjectt.getJSONArray("categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", block_object.get("id").toString());
                    map.put("state_id", block_object.get("state_id").toString());
                    map.put("city_id", block_object.get("city_id").toString());
                    map.put("name", block_object.get("name").toString());
                    map.put("icon", block_object.get("icon").toString());
                    map.put("background_image", block_object.get("background_image").toString());
                    map.put("slug", block_object.get("slug").toString());
                    map.put("reviews_heading", block_object.get("reviews_heading").toString());
                    map.put("reviews_title", block_object.get("reviews_title").toString());
                    map.put("faq_title", block_object.get("faq_title").toString());
                    map.put("short_description", block_object.get("short_description").toString());
                    map.put("about_category", block_object.get("about_category").toString());
                    map.put("status", block_object.get("status").toString());
                    map.put("meta_title", block_object.get("meta_title").toString());
                    map.put("meta_keywords", block_object.get("meta_keywords").toString());
                    map.put("meta_description", block_object.get("meta_description").toString());
                    map.put("avg_rating", block_object.get("avg_rating").toString());
                    map.put("banner_title", block_object.get("banner_title").toString());
                    map.put("banner_description", block_object.get("banner_description").toString());
                    map.put("deleted_at", block_object.get("deleted_at").toString());
                    map.put("created_at", block_object.get("created_at").toString());
                    map.put("updated_at", block_object.get("updated_at").toString());
                    CategoryList.add(map);
                }
                spCategory.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, CategoryList));
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }

    public void getSubCategory(String categoryId) {
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
                //
                //   Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

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
                    map.put("checkSubStatus", "false");
                    SubCategoryList.add(map);
                }
                OfferAdapter2 offerAdapter2 = new OfferAdapter2(SubCategoryList);
                recySubcat.setAdapter(offerAdapter2);
/*
                spSubCategory.setAdapter(new adapter_spinner2(mActivity, R.layout.spinner_textview_rel, SubCategoryList));
*/
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
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

    /*@Override
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

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mActivity, getString(R.string.unabletoselectimage), Toast.LENGTH_SHORT).show();
                Log.v("imaaage", String.valueOf(SELECT_PICTURE));
            }

        }

    }*/

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

            return s;
        }

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

    private void PostRegisterApi(String data1, String data2, String data3, String data4, String data5, String data6, String data7, String data8, String vaccineStatus, File imaeFile) {
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.registration;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("first_name", data1);
        hashMap.put("last_name", data2);
        hashMap.put("email", data3);
        hashMap.put("mobile_number", data4);
        hashMap.put("gender", gender);
        hashMap.put("dob", newDate);
        hashMap.put("state", strJobId);
        hashMap.put("city", strCityId);
        hashMap.put("address", data6);
        hashMap.put("pincode", data5);
        hashMap.put("serve_state", ServestrJobId);
        hashMap.put("serve_city", ServestrCityId);
        hashMap.put("pincodes", pincodestatusList.toString());
        hashMap.put("pincodes", pincodestatusList.toString());
        hashMap.put("pincodes", pincodestatusList.toString());
        hashMap.put("category", categoryId);
        hashMap.put("sub_categories", SubcategoryarrayList.toString());
        hashMap.put("sub_categories", SubcategoryarrayList.toString());
        hashMap.put("sub_categories", SubcategoryarrayList.toString());
        hashMap.put("vaccinated", vaccineStatus);
        hashMap.put("password", data7);
        hashMap.put("password_confirmation", data8);
        Log.v("EditAdreessHash", hashMap + " good");

        /*.addMultipartParameter("", data1)
                .addMultipartParameter("last_name", data2)
                .addMultipartParameter("email", data3)
                .addMultipartParameter("mobile_number", data4)
                .addMultipartParameter("gender", gender)
                .addMultipartParameter("dob", newDate)
                .addMultipartParameter("state", strJobId)
                .addMultipartParameter("city", strCityId)
                .addMultipartParameter("address", data6)
                .addMultipartParameter("pincode",data5)
                .addMultipartParameter("serve_state", ServestrJobId)
                .addMultipartParameter("serve_city", ServestrJobId)
                .addMultipartParameter("pincodes",pincodestatusList.toString())
                .addMultipartParameter("pincodes", pincodestatusList.toString())
                .addMultipartParameter("pincodes", pincodestatusList.toString())
                .addMultipartParameter("category", categoryId)
                .addMultipartParameter("sub_categories", SubcategoryarrayList.toString())
                .addMultipartParameter("sub_categories",SubcategoryarrayList.toString())
                .addMultipartParameter("sub_categories", SubcategoryarrayList.toString())
                .addMultipartParameter("vaccinated", vaccineStatus)
                .addMultipartFile("vaccine", imageFile)//make it choose file
                .addMultipartParameter("password",data7 )
                .addMultipartParameter("password_confirmation",data8)*/
        AndroidNetworking.upload(url)
                .addMultipartFile("vaccine", imaeFile)
                .addMultipartParameter(hashMap)
                .setPriority(Priority.HIGH)
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
                        if (error.getErrorCode() == 200) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        } else if (error.getErrorCode() == 401) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        } else if (error.getErrorCode() == 500) {
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //  Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mActivity, Container.class));
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                JSONObject jsonObject1 = messageObjectt.getJSONObject("error");
                JSONArray jsonArray = jsonObject1.getJSONArray("email");
                //  Toast.makeText(mActivity, "" + jsonArray.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray1 = jsonObject1.getJSONArray("mobile_number");
                //   Toast.makeText(mActivity, "" + jsonArray1.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray2 = jsonObject1.getJSONArray("pincode");
                //  Toast.makeText(mActivity, "" + jsonArray2.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray3 = jsonObject1.getJSONArray("first_name");
                // Toast.makeText(mActivity, "" + jsonArray3.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray4 = jsonObject1.getJSONArray("last_name");
                //  Toast.makeText(mActivity, "" + jsonArray4.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray6 = jsonObject1.getJSONArray("gender");
                //  Toast.makeText(mActivity, "" + jsonArray6.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray7 = jsonObject1.getJSONArray("dob");
                //  Toast.makeText(mActivity, "The dob must be a date before 13 years", Toast.LENGTH_SHORT).show();
                JSONArray jsonArray8 = jsonObject1.getJSONArray("state");
                //  Toast.makeText(mActivity, "" + jsonArray8.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray9 = jsonObject1.getJSONArray("city");
                //  Toast.makeText(mActivity, "" + jsonArray9.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray19 = jsonObject1.getJSONArray("password");
                //  Toast.makeText(mActivity, "" + jsonArray19.toString(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray18 = jsonObject1.getJSONArray("vaccinated");
                // Toast.makeText(mActivity, "" + jsonArray18.toString(), Toast.LENGTH_SHORT).show();


            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
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
                //  Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();

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
                    map.put("checkStatus", "false");
                    pincodeList.add(map);
                }
                OfferAdapter OfferAdapter = new OfferAdapter(pincodeList);
                recyPincode.setAdapter(OfferAdapter);
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }

    }

    @Override
    public void onOTPVerifyError(String message) {
        Sneaker.with(personalInfo.this)
                .setTitle(message)
                .setMessage("")
                .sneakError();

        Log.d("onOTPVerifyError", message);

    }

    @Override
    public void onSignUpSuccess(SignupUserRepo response, String message) {

        if (response.getStatus() == true) {


            GenerateTicketWithImage(personalInfo.this, file, String.valueOf(response.getData().getUser().getId()));
            //    Toast.makeText(personalInfo.this, response.getMessage()+"", Toast.LENGTH_SHORT).show();

/*

            //GenerateTicketWithImage(personalInfo.this,String.valueOf(response.getData().getUser().getId()),profname.getText().toString());
           // GenerateTicketWithImage(personalInfo.this,"1",profname.getText().toString());
*/

        } else {

        }

        Log.d("onSignUpSuccess", message);
    }

    @Override
    public void onUploadSuccess(ResponseBody response, String message) {


        if (message.equalsIgnoreCase("ok")) {
            startActivity(new Intent(personalInfo.this, loginWithMobile.class));
            Toast.makeText(personalInfo.this, "Registration Successfully ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
        if (isShow) {
            AppUtils.showRequestDialog(this);


        } else {
            AppUtils.hideDialog();

        }
    }

    @Override
    public void onOTPVerifyFailure(Throwable t) {
        Sneaker.with(personalInfo.this)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakError();

        Log.d("onFailure", t.getLocalizedMessage());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_selectAllSubCategory:
                makeSubSelection();
                selectAllSubCategories();
                break;
            case R.id.rl_selectAllPincodes:
                makePincodeSelection();
                selectAllPincode();
                break;
        }
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
               // select_SubCategories.add(SubCategoryList.get(i));

            }
        }else {
           // select_SubCategories.clear();
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
        recySubcat.setAdapter(offerAdapter2);
        offerAdapter2.notifyDataSetChanged();

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

    public class adapter_spinner2 extends ArrayAdapter<HashMap<String, String>> {
        ArrayList<HashMap<String, String>> data;

        public adapter_spinner2(Context context, int textViewResourceId, ArrayList<HashMap<String, String>> arraySpinner_time) {
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
            label.setText(data.get(position).get("sub_category_name"));
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
                    if (data.get(position).get("checkStatus").equalsIgnoreCase("false"))
                        data.get(position).replace("checkStatus", "false", "true");
                    else {
                        isSelectAllPincodes = true;
                        makePincodeSelection();
                        data.get(position).replace("checkStatus", "true", "false");
                    }
                } else {
                    if (data.get(position).get("checkStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkStatus");
                        data.get(position).put("checkStatus", "true");
                    } else {
                        isSelectAllPincodes = true;
                        makePincodeSelection();
                        data.get(position).remove("checkStatus");
                        data.get(position).put("checkStatus", "false");
                    }
                }
                notifyDataSetChanged();
            });
//sir code

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

            data.get(position).get("checkSubStatus");
            if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                holder.ivPinCheck.setImageResource(R.drawable.checked);
            } else {
                holder.ivPinCheck.setImageResource(R.drawable.uncheck);
            }
            holder.ivPinCheck.setOnClickListener(v -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false"))
                        data.get(position).replace("checkSubStatus", "false", "true");
                    else {
                        isSelectAllSubCat=true;
                        makeSubSelection();
                        data.get(position).replace("checkSubStatus", "true", "false");
                    }
                } else {
                    if (data.get(position).get("checkSubStatus").equalsIgnoreCase("false")) {
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "true");
                    } else {
                        isSelectAllSubCat=true;
                        makeSubSelection();
                        data.get(position).remove("checkSubStatus");
                        data.get(position).put("checkSubStatus", "false");
                    }
                }
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

    private void GenerateTicketWithImage(Context context, File file, String id) {


        //  @Part MultipartBody.Part payment_screenshot);

        Log.e("filefile", String.valueOf(file));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("vaccine_file", file.getName(), requestFile);
        RequestBody Idd = RequestBody.create(MediaType.parse("multipart/form-data"), id);

        presenter.uploadImage(image, personalInfo.this, Idd);




    /*    Call<ResponseBody> userlist = ApiManager.getApi(context).SignupUserRepoWithImage(Idd, image);

        userlist.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(personalInfo.this, response.code()+"", Toast.LENGTH_SHORT).show();

                // Toast.makeText(context, response.code() + "", Toast.LENGTH_LONG).show();
                AppUtils.hideDialog();

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (jsonObject.getString("status").equals("true")) {
                            startActivity(new Intent(personalInfo.this, loginWithMobile.class));
                        }


                        //     Toast.makeText(context, response.body().getMessage() + "", Toast.LENGTH_LONG).show();


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        String errorMsg = jsonObject.getString("status");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AppUtils.hideDialog();
                Toast.makeText(personalInfo.this, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();

                Log.e("onFailurebbb", t.getLocalizedMessage());
            }
        });*/
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("requestCode" + requestCode);
        switch (requestCode) {
            case ImagePath.REquestPermissionCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(personalInfo.this, "Permissin Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(personalInfo.this, "Permissin Deined", Toast.LENGTH_SHORT).show();
                }


        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data == null)
                return;

            Uri uri = data.getData();
            System.out.println("urii  " + uri + " " + uri.getPath());
            String path = ImagePath.getPath(personalInfo.this.getApplicationContext(), uri);
            System.out.println("urii path " + path);

            imageUri = data.getData();
            //circleImageView.setImageURI(imageUri);
            if (path != null && !path.equals("")) {
                file = new File(path);
                profname.setText(path);

            }
        }
    }

    private void uploadImage(File file, String id) {
        //  AppTools.showGifDialog(getActivity(), globalloader);

        Log.e("filefile", String.valueOf(file));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        RequestBody Idd = RequestBody.create(MediaType.parse("multipart/form-data"), id);

    }

    public void On_click_OpenGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
}
