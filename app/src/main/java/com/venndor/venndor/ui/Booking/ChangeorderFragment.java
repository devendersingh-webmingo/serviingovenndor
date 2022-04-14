package com.venndor.venndor.ui.Booking;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.chaos.view.PinView;
import com.irozon.sneaker.Sneaker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.ui.ImagePath;
import com.venndor.venndor.ui.Presenter.ChangeorderPresenter;
import com.venndor.venndor.ui.personalInfo;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;
import static com.venndor.venndor.ui.Container.ivIamge;

public class ChangeorderFragment extends BaseFragment implements View.OnClickListener, ChangeorderPresenter.ChangeorderView {
    View rootView;
    Spinner ordersatus, cod;
    LinearLayout llchoosefile, llSubmitNow, codLL;
    EditText reason;
    TextView codTv, profname, tvchooseProfile;

    Preferences pref;
    String payment_method, orderid, orderstaus, reasonn;

    String[] gender = {"Completed"};//array of strings used to populate the spinner


    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    File file;
    ChangeorderPresenter presenter;
    private Uri uriFilePath;

    final int REQUEST_TAKE_PHOTO = 1;

    private String getUriSelfie = "";
    private File fileUserProfilePic;

    RadioButton yes, no;

    String paymentcollected;


    View view, view1;
    AlertDialog alertDialog, alertDialog1;

    Bitmap bitmap;

    private static final int PICK_IMAGE = 100;
    String currentPhotoPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_changeorder, container, false);
        findViewById();
        return rootView;
    }

    @Override
    public void onClick(View v) {

    }

    private void findViewById() {
        presenter = new ChangeorderPresenter(this);
        pref = new Preferences(mActivity);
        yes = rootView.findViewById(R.id.yes);
        no = rootView.findViewById(R.id.no);
        ordersatus = rootView.findViewById(R.id.ordersatus);
        llchoosefile = rootView.findViewById(R.id.llchoosefile);
        tvchooseProfile = rootView.findViewById(R.id.tvchooseProfile);
        reason = rootView.findViewById(R.id.reason);
        llSubmitNow = rootView.findViewById(R.id.llSubmitNow);
        codLL = rootView.findViewById(R.id.codLL);
        profname = rootView.findViewById(R.id.profname);
        Picasso.get().load(AppUrls.profileImageUrl + pref.get("imageVendor")).into(ivIamge);

        payment_method = pref.get("payment_method");
        orderid = pref.get("order_id");

      /*  payment_method="cod";
        orderid="1";
*/
        if (!payment_method.equals("cod")) {
            codLL.setVisibility(View.GONE);
        }
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        ordersatus.setAdapter(aa);
        ordersatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                orderstaus = parent.getItemAtPosition(position).toString().trim();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


        tvchooseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   dispatchTakePictureIntent();


  /*              Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //  cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

                //dispatchTakePictureIntent();
                ChoicCamera(getContext());

                /*
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }*/
            }

        });

        llSubmitNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonn = reason.getText().toString().trim();
                String profnamee = profname.getText().toString().trim();
                if (yes.isChecked()) {
                    paymentcollected = "Yes";
                } else if (no.isChecked()) {
                    paymentcollected = "No";
                }
                if (reasonn.isEmpty()) {
                    Sneaker.with(mActivity)
                            .setTitle("Enter Remark or Reason")
                            .setMessage("")
                            .sneakWarning();
                } else if (profnamee.isEmpty()) {
                    Sneaker.with(mActivity)
                            .setTitle("Take your Photo")
                            .setMessage("")
                            .sneakWarning();
                } else {


                    if (!payment_method.equals("cod")) {

                        ChangeWithoutCODorder(getContext(), orderid, orderstaus, reasonn, file);
                    } else {
                        ChangeCODorder(getContext(), orderid, orderstaus, paymentcollected, reasonn, file);

                    }


                }

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == getActivity().RESULT_OK) {
                file = new File(currentPhotoPath);
                profname.setText(file.getAbsolutePath());
            }
            //  new uploadFileToServerTask().execute(destination.getAbsolutePath());
        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data == null)
                return;

            Uri uri = data.getData();
            System.out.println("urii  " + uri + " " + uri.getPath());
            String path = ImagePath.getPath(getContext().getApplicationContext(), uri);
            System.out.println("urii path " + path);

            //imageUri = data.getData();
            //circleImageView.setImageURI(imageUri);
            if (path != null && !path.equals("")) {
                file = new File(path);
                profname.setText(path);

            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.venndor.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }

/*    private void encodebitmap(Bitmap bitmap) {


        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] byteofimage=byteArrayOutputStream.toByteArray();
        String encodeImage= Base64.encodeToString(byteofimage, Base64.DEFAULT);

        profname.setText(encodeImage);



    }*/


    @Override
    public void onOTPVerifyError(String message) {
        Log.e("onOTPVerifyError", message);
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

        Sneaker.with(mActivity)
                .setTitle(message)
                .setMessage("")
                .sneakError();

    }

    @Override
    public void onCODChangeorderSuccess(ResponseBody response, String message) {

        if (message.equalsIgnoreCase("ok")) {
            Toast.makeText(mActivity, "OTP Successfully Send On User Order Email And Mobile Number.", Toast.LENGTH_LONG).show();
            showDialog(getContext());
        }
    }

    @Override
    public void onVerifyCompleteserviceotpSuccess(ResponseBody response, String message) {
        if (message.equalsIgnoreCase("ok")) {


            ((Container) mActivity).displayView(5);
            alertDialog.dismiss();
            Toast.makeText(mActivity, "OTP verify Successfully .", Toast.LENGTH_LONG).show();


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
        Log.e("onOTPVerifyError", t.getLocalizedMessage());

        Sneaker.with(mActivity)
                .setTitle(t.getLocalizedMessage())
                .setMessage("")
                .sneakError();
        Toast.makeText(mActivity, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

    }


    private void ChangeWithoutCODorder(Context context, String orderid, String status, String remark, File file) {


        //  @Part MultipartBody.Part payment_screenshot);

        Log.e("filefile", String.valueOf(file));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        RequestBody orderidd = RequestBody.create(MediaType.parse("multipart/form-data"), orderid);
        RequestBody statuss = RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody remarkk = RequestBody.create(MediaType.parse("multipart/form-data"), remark);


        presenter.ChangeWithoutCODorder(getContext(), orderidd, statuss, remarkk, image);


    }


    private void ChangeCODorder(Context context, String orderid, String status, String payment_collected, String remark, File file) {


        //  @Part MultipartBody.Part payment_screenshot);

        Log.e("filefile", String.valueOf(file));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


        RequestBody orderidd = RequestBody.create(MediaType.parse("multipart/form-data"), orderid);
        RequestBody statuss = RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody remarkk = RequestBody.create(MediaType.parse("multipart/form-data"), remark);
        RequestBody payment_collectedd = RequestBody.create(MediaType.parse("multipart/form-data"), payment_collected);


        presenter.ChangeCODorder(getContext(), orderidd, statuss, payment_collectedd, remarkk, image);


    }


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


        exit = view.findViewById(R.id.exit);
        tvCount = view.findViewById(R.id.tvCount);
        pinview = view.findViewById(R.id.pinview);
        llSubmitNow = view.findViewById(R.id.llSubmitNow);
        exit.setText("Confirm OTP For Complete Order");
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

                    //   String payment_method, orderid, orderstaus, reasonn;


                    if (!payment_method.equals("cod")) {

                        presenter.VerifyWithoutCODCompleteser(context, orderid, orderstaus, reasonn, otp);

                    } else {

                        presenter.VerifyCODCompleteser(context, orderid, orderstaus, reasonn, otp, paymentcollected);


                    }


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
   /* public void On_click_OpenGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }*/


    public void On_click_OpenGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void ChoicCamera(Context context) {

        /* Dialog dialog;*/
        TextView CancelTv;

        ImageButton cammeraLL, GalleryLL;


        LayoutInflater inflater = LayoutInflater.from(context);
        view1 = inflater.inflate(R.layout.choiceimage, null);
        alertDialog1 = new AlertDialog.Builder(context)
                .setView(view1)
                .setCancelable(false)
                .create();


        CancelTv = view1.findViewById(R.id.CancelTv);
        cammeraLL = view1.findViewById(R.id.cammeraLL);
        GalleryLL = view1.findViewById(R.id.GalleryLL);


        CancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

        cammeraLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                alertDialog1.dismiss();
                dispatchTakePictureIntent();
            }
        });
        GalleryLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
                On_click_OpenGallery();

            }
        });

        alertDialog1.show();
    }
}




