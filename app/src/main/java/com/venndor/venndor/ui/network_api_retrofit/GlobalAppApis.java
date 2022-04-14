package com.venndor.venndor.ui.network_api_retrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class GlobalAppApis {
    Context context;

    public GlobalAppApis(Context context) {
        this.context = context;
    }

    public String set_loginData(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObject.put("deviceType", "1");
          /*  jsonObject.put("deviceToken", Sharedpre.getPrefrence(context, Constants.DEVICE_TOKEN));
            jsonObject.put("language", ResourceHelper.getLocale());
            jsonObject.put("timezone", DateTimeHelper.getZone());*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String set_SignupData(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", bundle.getString("email"));
            jsonObject.put("password", bundle.getString("password"));
            jsonObject.put("name", bundle.getString("name"));
            jsonObject.put("phone", bundle.getString("phone"));
            jsonObject.put("deviceType", "1");
           /* jsonObject.put("deviceToken", Sharedpre.getPrefrence(context, Constants.DEVICE_TOKEN));
            jsonObject.put("language", ResourceHelper.getLocale());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String set_forgot_data(String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String set_LogOutData(Bundle params) {
        JSONObject jsonObject = new JSONObject();
        try {
         //   jsonObject.put("authorization", getApi_Token());
            jsonObject.put("deviceToken", params.getString("deviceToken"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setTodoDetailData(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
            //jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setArchivetask(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
            jsonObject.put("isArchive", bundle.getInt("isArchive"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String DeleteTdo(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String Removemember(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
            jsonObject.put("perticipantID", bundle.getInt("perticipantID"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String DeleteTask(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
            //jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setupdateProfile(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isUpdate", bundle.getString("isUpdate"));
            if (bundle.getString("isUpdate").equalsIgnoreCase("1")) {

                jsonObject.put("isnotificationOn", bundle.getInt("isnotificationOn"));
                jsonObject.put("islocationOn", bundle.getInt("islocationOn"));
             //   jsonObject.put("language", ResourceHelper.getLocale());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setgeofenceData(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoListID", bundle.getString("todoListID"));
            jsonObject.put("isEnter", bundle.getString("isEnter"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String changeProfileFields(Bundle bundle, int value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isUpdate", bundle.getString("isUpdate"));
        //    jsonObject.put("language", ResourceHelper.getLocale());
            if (value == 1) {
                jsonObject.put("name", bundle.getString("name"));
            } else if (value == 2) {
                jsonObject.put("email", bundle.getString("email"));
            } else if (value == 4) {
                jsonObject.put("phone", bundle.getString("phone"));
            } else if (value == 7) {
                jsonObject.put("title", bundle.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String set_ChangePasswordData(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("oldPassword", bundle.getString("oldPassword"));
            jsonObject.put("newPassword", bundle.getString("newPassword"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setpulsedata(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNO", String.valueOf(bundle.getInt("pageNO")));
          //  jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setInvitedataForPulse(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("todoID", bundle.getString("todoID"));
            jsonObject.put("status", bundle.getInt("status"));
           // jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String ignoretaskinPulse(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskItemID", bundle.getString("taskItemID"));
            //jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String settaskCompletedData(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskItemID", bundle.getString("taskItemID"));
          //  jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String userList(String todoID, int type, int pageNO) {
        JSONObject jsonObject = new JSONObject();
        try {
          //  jsonObject.put("language", ResourceHelper.getLocale());
            if (type == 1) {
                jsonObject.put("noteID", todoID);
            } else if (type == 2) {
                jsonObject.put("crewID", todoID);
            } else {
                jsonObject.put("todoID", todoID);
            }
            jsonObject.put("pageNO", pageNO);

            //  jsonObject.put("todoID", todoID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String setNotesdata(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
          //  jsonObject.put("language", ResourceHelper.getLocale());
            jsonObject.put("title", bundle.getString("title"));
            jsonObject.put("description", bundle.getString("description"));
            jsonObject.put("noteDate", bundle.getString("noteDate"));
            jsonObject.put("isLocation", bundle.getString("isLocation"));
            jsonObject.put("location", bundle.getString("location"));
            jsonObject.put("latitude", bundle.getString("latitude"));
            jsonObject.put("longitude", bundle.getString("longitude"));
            jsonObject.put("radius", bundle.getString("radius"));
            jsonObject.put("type", bundle.getString("type"));


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String sendinvite(Bundle bundle) {

        JSONObject jsonObject = new JSONObject();
        int type = bundle.getInt("type");
        try {
           // jsonObject.put("language", ResourceHelper.getLocale());
            jsonObject.put("inviteTo", bundle.getString("inviteTo"));
            if (type == 1) {
                jsonObject.put("noteID", bundle.getString("noteID"));
            } else if (type == 2) {
                jsonObject.put("crewID", bundle.getString("crewID"));
            } else {
                jsonObject.put("todoID", bundle.getInt("todoID"));
            }
            jsonObject.put("type", bundle.getInt("type"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("AAAAAAAAAAAAA", jsonObject.toString());
        return jsonObject.toString();
    }

    public String postTodo(Bundle bundle, String loc, String todoID) {
        JSONObject jsonObject = new JSONObject();
        try {
          //  jsonObject.put("language", ResourceHelper.getLocale());
            jsonObject.put("templateID", bundle.getInt("templateID") + "");
            jsonObject.put("todoName", bundle.getString("todoName"));
            jsonObject.put("notes", bundle.getString("notes"));
            jsonObject.put("data", bundle.getString("data"));
            if (!loc.equalsIgnoreCase("")) {
                jsonObject.put("location", bundle.getString("location"));
                jsonObject.put("latitude", bundle.getString("latitude"));
                jsonObject.put("longitude", bundle.getString("longitude"));
            }
            if (bundle.getInt("templateID") == 3) {
                jsonObject.put("radius", bundle.getDouble("radius") + "");
            }
            if (!todoID.equalsIgnoreCase("")) {
                jsonObject.put("todoID", Integer.parseInt(todoID));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String createTodoJsonString(Bundle bundle) {
        JSONObject jsonObject = new JSONObject();
        try {
         //   jsonObject.put("language", ResourceHelper.getLocale());
            if (bundle.containsKey("templateID")) {
                jsonObject.put("templateID", bundle.getInt("templateID"));
            } else {
                jsonObject.put("templateID", "");
            }

            if (bundle.containsKey("todoID")) {
                jsonObject.put("todoID", bundle.getInt("todoID"));
            } else {
                // jsonObject.put("todoID","");
            }

            if (bundle.containsKey("todoName")) {
                jsonObject.put("todoName", bundle.getString("todoName"));
            } else {
                //jsonObject.put("todoName","");
            }

            if (bundle.containsKey("todoDate")) {
                jsonObject.put("todoDate", bundle.getString("todoDate"));
            } else {
                //jsonObject.put("todoDate","");
            }

            if (bundle.containsKey("notes")) {
                jsonObject.put("notes", bundle.getString("notes"));
            } else {
                // jsonObject.put("notes","");
            }

            if (bundle.containsKey("isLocation")) {
                jsonObject.put("isLocation", bundle.getInt("isLocation"));
            } else {
                //jsonObject.put("isLocation","0");
            }
            if (bundle.containsKey("location")) {
                jsonObject.put("location", bundle.getString("location"));
            } else {
                //jsonObject.put("location","");
            }

            if (bundle.containsKey("latitude")) {
                jsonObject.put("latitude", bundle.getString("latitude"));
            } else {
                //jsonObject.put("latitude","0.0");
            }

            if (bundle.containsKey("longitude")) {
                jsonObject.put("longitude", bundle.getString("longitude"));
            } else {
                //jsonObject.put("longitude","0.0");
            }

            if (bundle.containsKey("radius")) {
                try {
                    jsonObject.put("radius", Double.parseDouble(bundle.getString("radius")));
                } catch (Exception e) {
                    jsonObject.put("radius", bundle.getDouble("radius"));
                }
            } else {
                jsonObject.put("radius", "0.0");
            }

            if (bundle.containsKey("todoMedia")) {
                jsonObject.put("todoMedia", bundle.getString("todoMedia"));
            } else {
                // jsonObject.put("todoMedia",new JSONArray());
            }

            if (bundle.containsKey("data")) {
                jsonObject.put("data", bundle.getString("data"));
            } else {
                // jsonObject.put("data", "");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getchat(Bundle bundle, String isgroup) {
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("language", ResourceHelper.getLocale());
            if (!isgroup.equalsIgnoreCase("1")) {
                jsonObject.put("taskItemID", bundle.getString("taskItemID"));
                jsonObject.put("type", bundle.getString("type"));
            }
            jsonObject.put("todoListID", bundle.getString("todoListID"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String postChat(Bundle bundle, String isgroup) {
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("language", ResourceHelper.getLocale());
            if (!isgroup.equalsIgnoreCase("1")) {
                jsonObject.put("taskItemID", Integer.parseInt(bundle.getString("taskItemID")));
            }
            jsonObject.put("todoListID", Integer.parseInt(bundle.getString("todoListID")));
            jsonObject.put("messageType", Integer.parseInt(bundle.getString("messageType")));
            jsonObject.put("message", bundle.getString("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


   /* public String getApi_Token() {

        return Sharedpre.getPrefrence(context, Constants.API_KEY);
    }
*/
    public static RequestBody setTaskImage(String path) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
      //  builder.addFormDataPart("language",ResourceHelper.getLocale());
        if (path != null && !path.equalsIgnoreCase("")) {
            //RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
            RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), new File(path));
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", Calendar.getInstance().getTimeInMillis() + "." + path.substring(path.lastIndexOf('.') + 1), requestFile);
            builder.addPart(image);
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }

    public static RequestBody uploadMedia(String path, String type) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        Log.e("request File", "String path " + path);
        if (path != null && !path.equalsIgnoreCase("")) {
            RequestBody requestFile = null;


            if (type.equalsIgnoreCase("image")) {

                //requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(path));
                requestFile = RequestBody.create(MediaType.parse("image/*"), new File(path));
            } else if (type.equalsIgnoreCase("video")) {
                requestFile = RequestBody.create(MediaType.parse("video/mp4"), new File(path));
            } else if (type.equalsIgnoreCase("doc")) {
                requestFile = RequestBody.create(MediaType.parse("application/pdf"), new File(path));
            } else {
                requestFile = RequestBody.create(MediaType.parse("*/*"), new File(path));
            }

            Log.e("request File", "File path " + new File(path).getName());


            MultipartBody.Part image = MultipartBody.Part.createFormData("image", Calendar.getInstance().getTimeInMillis() + "." + path.substring(path.lastIndexOf('.') + 1), requestFile);
            builder.addPart(image);
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }
/*
    public static RequestBody updateProfilePicture(Bundle bundle) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("isUpdate", bundle.getString("isUpdate"));
        builder.addFormDataPart("language", ResourceHelper.getLocale());
        String path = bundle.getString("imageURL");

        if (path != null && !path.equalsIgnoreCase("")) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
            MultipartBody.Part image = MultipartBody.Part.createFormData("imageURL", "imageURL", requestFile);
            builder.addPart(image);
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }*/

    public static RequestBody setNotesdatawithImage(Bundle bundle) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("title", bundle.getString("title"));
        builder.addFormDataPart("description", bundle.getString("description"));
        builder.addFormDataPart("noteDate", bundle.getString("noteDate"));
        builder.addFormDataPart("type", bundle.getString("type"));
        builder.addFormDataPart("isLocation", bundle.getString("isLocation"));
        builder.addFormDataPart("location", bundle.getString("location"));
        builder.addFormDataPart("latitude", bundle.getString("latitude"));
        builder.addFormDataPart("longitude", bundle.getString("longitude"));
        builder.addFormDataPart("radius", bundle.getString("radius"));

        ArrayList<String> list = bundle.getStringArrayList("imageURL");
        Log.e("ImageUrl", "size " + list.size());
        for (int i = 1; i < list.size(); i++) {
            String path = list.get(i);

            if (path != null && !path.equalsIgnoreCase("")) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
                MultipartBody.Part image = MultipartBody.Part.createFormData("imageURL", "imageURL", requestFile);
                builder.addPart(image);
            }
        }

        RequestBody requestBody = builder.build();
        return requestBody;
    }

    public static RequestBody postchatimage(Bundle bundle, String isgroup) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("todoListID", bundle.getString("todoListID"));
        builder.addFormDataPart("messageType", bundle.getString("messageType"));
        builder.addFormDataPart("message", bundle.getString("message"));
        if (!isgroup.equalsIgnoreCase("")) {
            if (null != bundle.getString("taskItemID")) {
                builder.addFormDataPart("taskItemID", bundle.getString("taskItemID"));
            }
        }
        String path = bundle.getString("image");

        if (path != null && !path.equalsIgnoreCase("")) {
            File file = new File(path);
            int compressionRatio = 25; //1 == originalImage, 2 = 50% compression, 4=25% compress
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatio, new FileOutputStream(file));
            } catch (Throwable t) {
                Log.e("ERROR", "Error compressing file." + t.toString());
                t.printStackTrace();
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", "image", requestFile);
            builder.addPart(image);
        }
        RequestBody requestBody = builder.build();
        return requestBody;
    }

    public String syncData(Bundle params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("syncTime", params.getString("syncTime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

/*    public static RequestBody sendimages(Bundle bundle) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("language",ResourceHelper.getLocale());
       *//* builder.addFormDataPart("title", bundle.getString("title"));
        builder.addFormDataPart("description", bundle.getString("description"));*//*
        String tableName = bundle.getString("Table_name");

        ArrayList<TaskImage> list = (ArrayList<TaskImage>) bundle.getSerializable("ARRAYLIST");

        for (int i = 0; i < list.size(); i++) {
            String path = list.get(i).getAttributeValue();

            int locid = list.get(i).getTaskItemID_loc();

            String table = list.get(i).getTableName();

            Log.e("Upload Images", "from table " + table + " local id " + locid);

            if (path != null && !path.equalsIgnoreCase("")) {

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), new File(path));
                //MultipartBody.Part image = MultipartBody.Part.createFormData("imageURL", table+"_"+locid, requestFile);
                String someFilepath = new File(path).getName();
                String extension = someFilepath.substring(someFilepath.lastIndexOf("."));
                Log.e("Upload Images", "File Extension" + extension);
                MultipartBody.Part image
                        = MultipartBody.Part.createFormData("imageURL",
                        *//*Calendar.getInstance().getTimeInMillis()+"."+path.substring(path.lastIndexOf('.') + 1)*//*
                        table + "_" + locid + extension, requestFile);
                builder.addPart(image);
            }
        }

        RequestBody requestBody = builder.build();
        return requestBody;
    }*/

    public static RequestBody senddata(Bundle bundle) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("todoList", bundle.getString("todoList"));
        builder.addFormDataPart("todoListItemHeader", bundle.getString("todoListItemHeader"));
        builder.addFormDataPart("todoMedia", bundle.getString("todoMedia"));
        builder.addFormDataPart("taskItem", bundle.getString("taskItem"));
        builder.addFormDataPart("syncTime", bundle.getString("syncTime"));
        builder.addFormDataPart("notes", bundle.getString("notes"));
        builder.addFormDataPart("notesMedia", bundle.getString("notesMedia"));
        builder.addFormDataPart("listParticipant", bundle.getString("listParticipant"));
        builder.addFormDataPart("user", bundle.getString("user"));
        builder.addFormDataPart("chat", bundle.getString("chat"));
        builder.addFormDataPart("pendingTaskList", bundle.getString("pendingTaskList"));
        builder.addFormDataPart("pauseDailyTodo", bundle.getString("pauseDailyTodo"));
        builder.addFormDataPart("userArchiveTodo", bundle.getString("userArchiveTodo"));
        builder.addFormDataPart("timeSheet", bundle.getString("timeSheet"));
        builder.addFormDataPart("userChatStatus", bundle.getString("userChatStatus"));
        builder.addFormDataPart("userNoteStatus", bundle.getString("userNoteStatus"));
        builder.addFormDataPart("crew", bundle.getString("crew"));
        builder.addFormDataPart("crewMember", bundle.getString("crewMember"));
        builder.addFormDataPart("taskMedia", bundle.getString("taskMedia"));
        Log.e("SYNCDATE", bundle.getString("syncTime") + "**");
        RequestBody requestBody = builder.build();
        return requestBody;
    }

    public String setFilterData(String filterType, String postedby) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("filterType", filterType);
            jsonObject.put("postedBy", postedby);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
/*
    public static String getLocale_Json() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("language", ResourceHelper.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }*/
}
