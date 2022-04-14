package com.venndor.venndor.ui.network_api_retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    String TAG = "ApiService";

    //public static String BASE_URL = "http://18.209.105.125:9000/api/";   // CERT prod server
    public static String BASE_URL = "https://serviingo.com/api/"; // prod server new

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getLocationTodo")
    Call<String> getLOCATIONlist(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "vendor/login")
    Call<String> login(@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "forgotPassword")
    Call<String> forgot_password(@Body String body);


    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "register")
    Call<String> register(@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getAllTaskList")
    Call<String> getPulsedata(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getTodayTaskList1")
    Call<String> getTodayData(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getUpcommingTaskList")
    Call<String> getUpcomingData(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getArchiveList")
    Call<String> getArchiveddata(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getAnyTimeTaskList")
    Call<String> AnytimeData(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getcompletedTaskList")
    Call<String> getCompletedData(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getLocationTaskList")
    Call<String> getLocationData(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "todoDetail")
    Call<String> getDetails(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "addArchiveOnTodo")
    Call<String> setArchivetask(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "deleteTodo")
    Call<String> DeleteTdo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "removeUserFromTodo")
    Call<String> RemoveMember(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "deleteTodo")
    Call<String> DeleteTask(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "duplicateTodo")
    Call<String> DeplicateTodo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "completeTodo")
    Call<String> CompleteTodo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "pauseTodo")
    Call<String> PauseTodo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "leaveTodoList")
    Call<String> LeaveList(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "acceptRejectInvitaion")
    Call<String> getinvitedataforpulse(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "taskIgnore")
    Call<String> ignoretaskinPulse(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "completeTask")
    Call<String> getTaskComplete(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateProfile")
    Call<String> updateProfile(@Header("authorization") String header, @Body String body);


    @POST(BASE_URL + "updateTodoMedia")
    Call<String> updateTodoMedia(@Header("authorization") String header, @Body RequestBody body);

    //    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateProfile")
    Call<String> updateProfilePicture(@Header("authorization") String header, @Body RequestBody file);

    @POST(BASE_URL + "fileUpload")
    Call<String> settaskImage(@Header("authorization") String header, @Body RequestBody file);

    @POST(BASE_URL + "fileMulipleUpload")
    Call<String> fileMulipleUpload(@Header("authorization") String header, @Body RequestBody file);

    @POST(BASE_URL + "uploadMultipleImageSync")
    Call<String> setmultitaskImage(@Header("authorization") String header, @Body RequestBody file);

    @POST(BASE_URL + "createNote")
    Call<String> setNotesimages(@Header("authorization") String header, @Body RequestBody file);

    @POST(BASE_URL + "createCrew")
    Call<String> createCrew(@Header("authorization") String header, @Body RequestBody file);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "createNote")
    Call<String> setNotes(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "resetPassword")
    Call<String> ChangePassword(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "logout")
    Call<String> logOut(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "userList")
    Call<String> getuserList(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "postInvite")
    Call<String> sendinvite(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "inviteOnCrew")
    Call<String> inviteOnCrew(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "postTask")
    Call<String> postTodo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "postTaskVersion1")
    Call<String> postTaskVersion1(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateTask")
    Call<String> editTodo(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateTaskVersion1")
    Call<String> updateTaskVersion1(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getchatTodo")
    Call<String> gettodoChatList(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "chatTodo")
    Call<String> postchat(@Header("authorization") String header, @Body String body);

    @POST(BASE_URL + "updateDriverProfile")
    Call<String> getPostCreateBodyResponse(@Header("authorization") String authorization, @Body RequestBody file);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateOwnGeoFance")
    Call<String> updateOwnGeoFance(@Header("authorization") String header, @Body String body);

    @POST(BASE_URL + "updateOwnGeoFance")
    Call<String> updateOwnGeoFance(@Header("authorization") String header, @Body RequestBody body);

    @POST(BASE_URL + "chatTodo")
    Call<String> postchatimage(@Header("authorization") String header, @Body RequestBody file);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "tripDetail")
    Call<String> tripDetail(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "syncData")
    Call<String> sync(@Header("authorization") String header, @Body String body);

    //@Headers("Content-Type: application/json")
    @POST(BASE_URL + "uploadSyncData")
    Call<String> uploadsyncData(@Header("authorization") String header, @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "AddTimeSheet")
    Call<String> add_Edit_Timesheet(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getTimeSheetList")
    Call<String> getTimeSheetList(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "checkInCheckOutTimesheet")
    Call<String> checkInCheckOutTimesheet(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "updateTimeSheet")
    Call<String> updateTimeSheet(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "overDueTaskList")
    Call<String> overDueTaskList(@Header("authorization") String header,@Body String body);


    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "pulseList")
    Call<String> getpulseList(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "pulseListVersion1")
    Call<String> pulseListVersion1(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "perticipatedUserList")
    Call<String> perticipatedUserList(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "filterOnPulse")
    Call<String> filterOnPulse(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "ticketDetail")
    Call<String> ticketDetail(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getChatNotes")
    Call<String> getChatNotes(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getchatCrew")
    Call<String> getchatCrew(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "removeTimeSheet")
    Call<String> removeTimeSheet(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "closeTicket")
    Call<String> closeTicket(@Header("authorization") String header, @Body String body);


    @POST(BASE_URL + "chatNotes")
    Call<String> chatNotes(@Header("authorization") String header, @Body RequestBody body);

    @POST(BASE_URL + "chatCrew")
    Call<String> chatCrew(@Header("authorization") String header, @Body RequestBody body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "chatNotes")
    Call<String> chatNotes(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getNotificationLog")
    Call<String> getNotificationLog(@Header("authorization") String header);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "openTicketList")
    Call<String> openTicketList(@Header("authorization") String header);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "getAllNoteList")
    Call<String> getAllNoteList(@Header("authorization") String header);


    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "checkAppVersion")
    Call<String> checkAppVersion(@Header("authorization") String header, @Body String body);


    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "userSearch")
    Call<String> userSearch(@Header("authorization") String header, @Body String body);

    @POST(BASE_URL + "uploadCrewMedia")
    Call<String> uploadCrewMedia(@Header("authorization") String header, @Body RequestBody file);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "inviteByEmail")
    Call<String> inviteByEmail(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "inviteByEmailForCrew")
    Call<String> inviteByEmailForCrew(@Header("authorization") String header, @Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "deleteAccount")
    Call<String> deleteAccount(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "inventoryMarketPrice")
    Call<String> inventoryMarketPrice(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "marcopoloTransactionHistory")
    Call<String> marcopoloTransactionHistory(@Header("authorization") String header,@Body String body);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "marcopoloCustomer")
    Call<String> marcopoloCustomer(@Header("authorization") String header);

    @Headers("Content-Type: application/json")
    @POST(BASE_URL + "soldInventory")
    Call<String> soldInventory(@Header("authorization") String header,@Body String body);
}