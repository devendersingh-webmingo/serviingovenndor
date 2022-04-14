package com.venndor.venndor.ui.Support;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.tvHeading;

public class OrdersandBooking extends BaseFragment implements View.OnClickListener {

    private static final int SELECT_PICTURE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Uniquedaam";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    View rootView;
    Spinner spPriority;
    AppCompatEditText etappcompat;
    Preferences pref;
    String picturePath = "", filename = "", ext = "", newDate, strJobId = "", strCityId = "";
    Bitmap rotatedBitmap;
    Uri picUri;
    File imageFile;
    ImageView ivPlus,ivupload;
    EditText otherHeading;
    TextView textupload;
    String encodedImage = "";
    String key = "";
    ArrayList<HashMap<String, String>> data = new ArrayList();
    private Bitmap bitmap;

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
        rootView = inflater.inflate(R.layout.ordersbooking, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        String value1 = pref.get("models");
        tvHeading.setText(value1);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);


        spPriority = rootView.findViewById(R.id.spPriority);
        populateSpinnerFrom(spPriority);
        etappcompat = rootView.findViewById(R.id.etappcompat);
        otherHeading = rootView.findViewById(R.id.otherHeading);
        ivPlus = rootView.findViewById(R.id.ivPlus);
        ivupload   = rootView.findViewById(R.id.ivupload);
        textupload = rootView.findViewById(R.id.textupload);
        otherHeading.setVisibility(View.GONE);
        if(value1.equalsIgnoreCase("other")){
            otherHeading.setVisibility(View.VISIBLE);
        }

        ivPlus.setVisibility(View.GONE);
        ivupload .setVisibility(View.VISIBLE);
        textupload.setVisibility(View.VISIBLE);

        rootView.findViewById(R.id.ivupload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
            }
        });
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                key = data.get(position).get("name");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        rootView.findViewById(R.id.llContactUs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1 = pref.get("models");
                String value2 = pref.get("id");
                String value3 =  etappcompat.getText().toString().trim();
                String value4 = otherHeading.getText().toString().trim();
                
                if (value3.isEmpty()){
                    Toast.makeText(mActivity, "The query statement field is required", Toast.LENGTH_SHORT).show();
                }else if (key.isEmpty()){
                    Toast.makeText(mActivity, "The query statement field is required", Toast.LENGTH_SHORT).show();
                }else{
                    Postvendorverifyotpstartservice(value1, value2, value3,key,value4, imageFile);
                }

            }
        });
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

                    Cursor cursor = mActivity.getContentResolver().query(contentURI, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    picturePath = cursor.getString(columnIndex);
                    System.out.println("Image Path : " + picturePath);
                    cursor.close();
                    filename = picturePath.substring(picturePath.lastIndexOf("/") + 1);
                    // profname.setText(filename);
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
                    ivPlus.setVisibility(View.VISIBLE);
                    ivupload .setVisibility(View.GONE);
                    textupload.setVisibility(View.GONE);
                ivPlus.setImageBitmap(rotatedBitmap);

                    Toast.makeText(mActivity, "File Uploaded successfully !!", Toast.LENGTH_SHORT).show();
                    /*   ivPAN.setImageBitmap(rotatedBitmap);*/
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
    public void populateSpinnerFrom(Spinner spinner) {
        data.clear();
        HashMap<String, String> operators = new HashMap<String, String>();
               operators.put("name", "Normal");
        operators.put("Id", "");
        data.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "Medium");
        operators.put("Id", "");
        data.add(operators);
        operators = new HashMap<String, String>();
        operators.put("name", "High");
        operators.put("Id", "");
        data.add(operators);
        spinner.setAdapter(new adapter_spinner1(mActivity, R.layout.spinner_textview_rel, data));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    public void Postvendorverifyotpstartservice(String value1, String value2, String value3, String key,String value4, File imageFile) {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.Postvendorgenerateticket;
        Log.v("data_url", url);

        String token = pref.get("token");
        Log.v("ddurl", token);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("query_module", value1);
        hashMap.put("subject_type_id", value2);
        hashMap.put("query_statement", value3);
        hashMap.put("priority", key);
        hashMap.put("other_subject", value4);
        Log.v("generate", hashMap + " good");
        AndroidNetworking.upload(url)
                .addMultipartFile("file", imageFile)
                .addMultipartParameter(hashMap)
                .addHeaders("Authorization", "Bearer " + token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsevendorticketJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });

    }

    private void parsevendorticketJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            String message = response.getString("message");
            /*AppUtils.showToastSort(mActivity, "" + message);*/
            Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
            ((Container) mActivity).displayView(18);
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
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
}
