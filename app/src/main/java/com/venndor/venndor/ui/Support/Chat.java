package com.venndor.venndor.ui.Support;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.venndor.venndor.R;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.venndor.venndor.ui.Container.ivIamge;

public class Chat extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    View rootView;

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rvChat;
    EditText editText;
    ImageView ivSend,ivattachmnet;
    Preferences pref;
    String ticket_id;
    LinearLayoutManager layoutManager;
    ChatAdapter chatAdapter;
    ArrayList<HashMap<String, String>> chatlist = new ArrayList<>();


    private static final int SELECT_PICTURE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Uniquedaam";
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;



    String picturePath = "", filename = "", ext = "", newDate, strJobId = "", strCityId = "";
    Bitmap rotatedBitmap;
    Uri picUri;
    File imageFile;

    EditText otherHeading;
    TextView textupload;
    String encodedImage = "";


    private Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inflate_chat, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref=new Preferences(mActivity);
         ticket_id=pref.get("ticket_id");
        rvChat = rootView.findViewById(R.id.rvChat);
        editText = rootView.findViewById(R.id.editText);
        ivSend = rootView.findViewById(R.id.ivSend);
        Picasso.get().load(AppUrls.profileImageUrl +  pref.get("imageVendor")).into(ivIamge);
        ivattachmnet = rootView.findViewById(R.id.ivattachmnet);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                GetchatApi(ticket_id);
            }
        });
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostSendMessageApi(ticket_id,editText.getText().toString().trim());
            }
        });
        ivattachmnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
            }
        });
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvChat.setLayoutManager(layoutManager);


    }
    @Override
    public void onRefresh() {

        GetchatApi(ticket_id);
    }

    private void GetchatApi(String ticket_id) {
        mSwipeRefreshLayout.setRefreshing(true);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getvendorchat+ticket_id;
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        parseGlobalJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        mSwipeRefreshLayout.setRefreshing(false);
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

    private void parseGlobalJsonResponse(JSONObject response) {
        try {
           chatlist.clear();
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                JSONObject dataJsonObject = response.getJSONObject("data");
                JSONObject userJsonObject = dataJsonObject.getJSONObject("ticket_info");
                String id = userJsonObject.getString("id");
                String ticket_id = userJsonObject.getString("ticket_id");
                String user_id = userJsonObject.getString("user_id");
                String team_id = userJsonObject.getString("team_id");
                String question_id = userJsonObject.getString("question_id");
                String subject_type = userJsonObject.getString("subject_type");
                String subject = userJsonObject.getString("subject");
                String booking_id = userJsonObject.getString("booking_id");
                String wallet_id = userJsonObject.getString("wallet_id");
                String query = userJsonObject.getString("query");
                String file = userJsonObject.getString("file");
                String priority = userJsonObject.getString("priority");
                String status = userJsonObject.getString("status");
                String created_at = userJsonObject.getString("created_at");
                String updated_at = userJsonObject.getString("updated_at");
                JSONObject jsonObject = userJsonObject.getJSONObject("query_statement");
                String model = jsonObject.getString("model");
                String question = jsonObject.getString("question");
                String querry = jsonObject.getString("query");
                JSONArray jsonArray = dataJsonObject.getJSONArray("messages");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject loansObj = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("ticket_id", loansObj.get("ticket_id").toString());
                    map.put("user_id", loansObj.get("user_id").toString());
                    map.put("message", loansObj.get("message").toString());
                    map.put("file", loansObj.get("file").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("updated_at", loansObj.get("updated_at").toString());
                    map.put("user_role", loansObj.get("user_role").toString());
                    map.put("date_time", loansObj.get("date_time").toString());
                    chatlist.add(map);
                }
                 chatAdapter = new ChatAdapter(chatlist);
                rvChat.setAdapter(chatAdapter);
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                AppUtils.showToastSort(mActivity, "" + error);
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private void PostSendMessageApi(String ticket_id,String editText) {
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.PostvendorSendMessage;
        AndroidNetworking.upload(url)
                .addMultipartParameter("ticket_id", ticket_id)
                .addMultipartParameter("message", editText)
                .addMultipartFile("file", imageFile)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer "+token)
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

                            // AppUtils.showErrorMessage(etmobilenumber, String.valueOf(error.getErrorBody()), mActivity);

                        } else {
                            // AppUtils.showErrorMessage(etmobilenumber, String.valueOf(error.getErrorDetail()), mActivity);
                            Toast.makeText(mActivity, String.valueOf(error.getErrorDetail()), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void parseJsonResponse(JSONObject response) {
        try {
            if (response.getString("status").equalsIgnoreCase("true")) {
                String message = response.getString("message");
                Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
                editText.setText("");
                GetchatApi(ticket_id);
            } else {
                JSONObject messageObjectt = response.getJSONObject("message");
                String error = messageObjectt.getString("error");
                //AppUtils.showToastSort(mActivity, "" + error);

            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    private String setTimeValues(String date) {
        try {
            String pattern = "dd-MM-yyyy hh:mm a";
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



    public class ChatAdapter extends RecyclerView.Adapter<chatHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

        public ChatAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public chatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatsupport, parent, false);
            return new chatHolder(itemView);
        }

        public void onBindViewHolder(chatHolder holder, final int position) {

            holder.rl_receiver.setVisibility(View.GONE);
            holder.rl_sender.setVisibility(View.GONE);

            if(data.get(position).get("user_role").equalsIgnoreCase("vendor")){
                holder.rl_sender.setVisibility(View.VISIBLE);
                holder.tv_senderDate.setText(data.get(position).get("message"));
                holder.tvSenderTime.setText(setTimeValues(data.get(position).get("date_time")));
                Picasso.get().load(AppUrls.profileImageUrl + pref.get("imageVendor")).into(holder.iv_sender);
            }else{
                holder.rl_receiver.setVisibility(View.VISIBLE);
                holder.tv_receiverdate.setText(data.get(position).get("message"));
                holder.tvreceiverTime.setText(setTimeValues(data.get(position).get("date_time")));

            }
            holder.tvSenderAttachment.setVisibility(View.GONE);
            holder.tv_receiver.setVisibility(View.GONE);
            if(data.get(position).get("file").equalsIgnoreCase("null") || data.get(position).get("file").isEmpty()){
                holder.tvSenderAttachment.setVisibility(View.GONE);
                holder.tv_receiver.setVisibility(View.GONE);
            }else{
                holder.tvSenderAttachment.setVisibility(View.VISIBLE);
                holder.tv_receiver.setVisibility(View.VISIBLE);
                holder.tvSenderAttachment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertView(data.get(position).get("file"));
                    }
                });
                holder.tv_receiver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertView(data.get(position).get("file"));
                    }
                });
            }

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class chatHolder extends RecyclerView.ViewHolder {
        TextView tv_senderDate,tv_receiverdate, tvSenderTime,tvreceiverTime,tvSenderAttachment,tv_receiver;
          RelativeLayout rl_sender,rl_receiver;
          CircleImageView iv_sender,iv_receiver;
        public chatHolder(View itemView) {
            super(itemView);
            tv_senderDate = itemView.findViewById(R.id.tv_senderDate);
            tvSenderTime = itemView.findViewById(R.id.tvSenderTime);
            rl_sender = itemView.findViewById(R.id.rl_sender);
            iv_sender = itemView.findViewById(R.id.iv_sender);
            tvSenderAttachment = itemView.findViewById(R.id.tvSenderAttachment);

            //rr_receiver
            rl_receiver = itemView.findViewById(R.id.rl_receiver);
            tv_receiverdate = itemView.findViewById(R.id.tv_receiverdate);
            tvreceiverTime = itemView.findViewById(R.id.tvreceiverTime);
            tv_receiver = itemView.findViewById(R.id.tv_receiver);
            iv_receiver = itemView.findViewById(R.id.iv_receiver);
        }
    }


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
                   // ivPlus.setVisibility(View.VISIBLE);
                   // ivupload .setVisibility(View.GONE);
                    textupload.setVisibility(View.GONE);
                   // ivPlus.setImageBitmap(rotatedBitmap);

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
    private void AlertView(String file) {
        final Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.viewimagemessage);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        ImageView close = dialog.findViewById(R.id.close);
        ImageView imageviewMesg = dialog.findViewById(R.id.messageview);
        Picasso.get().load(file).into(imageviewMesg);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });




    }
}
