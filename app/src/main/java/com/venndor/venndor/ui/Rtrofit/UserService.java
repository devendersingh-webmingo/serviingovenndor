package com.venndor.venndor.ui.Rtrofit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;
import com.venndor.venndor.ui.Presenter.Repo.ForgetOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Repo.LoanHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.LoginWithOTPReop;
import com.venndor.venndor.ui.Presenter.Repo.NewUserOTPVerifyReop;
import com.venndor.venndor.ui.Presenter.Repo.PaymentInfoPopup;
import com.venndor.venndor.ui.Presenter.Repo.SignupUserRepo;
import com.venndor.venndor.ui.Presenter.Repo.UpdateprofileUserRepo;
import com.venndor.venndor.ui.Presenter.Repo.VendorMyloan;
import com.venndor.venndor.ui.Presenter.Request.SignUpUser_request;
import com.venndor.venndor.ui.Presenter.Request.UpdateProfile_request;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {


    /*  */

    /**
     * File Name: UserService.java
     * Description: This file contains classes and functions for the Api.
     *
     * @author Devender
     * Date Created:
     * Date Released:
     * Created by Devender Singh
     */


    @FormUrlEncoded
    @POST("vendor/login/by-otp")
    Call<LoginWithOTPReop> DootploginbyOTPuser(
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("vendor/forgot-password/verify-otp")
    Call<ForgetOTPVerifyReop> DOForgetOTPVerifyReop(
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("vendor/verify/otp/new-user")
    Call<NewUserOTPVerifyReop> PostOTPRegistrationApi(
            @Field("otp") String otp
    );


    @POST("vendor/signup")
    Call<SignupUserRepo> SignupUserRepo(@Body SignUpUser_request credential);


    @Multipart
    @POST("vendor/upload/vaccine")
    Call<ResponseBody> SignupUserRepoWithImage(
            @Part("user_id") RequestBody user_id,
            @Part MultipartBody.Part file);


    @POST("vendor/update/profile")
    Call<UpdateprofileUserRepo> UpdateprofileUserRepo(@Body UpdateProfile_request credential);


    @GET("vendor/my-loan")
    Call<VendorMyloan> VendorMyloan();


    @FormUrlEncoded
    @POST("vendor/update/password")
    Call<ResponseBody> UpdatePassword(
            @Field("_method") String _method,
            @Field("old_passowrd") String old_passowrd,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation


    );


    @GET("vendor/loan/history/{loan_id}")
    Call<LoanHistoryRepo> LoanHistory(
            @Path("loan_id") String sub_category_id
    );


    @GET("vendor/my/bookings/Active")
    Call<BookingActiveHistoryRepo> BookingActiveHistory();


    @GET("vendor/my/bookings/Completed")
    Call<BookingActiveHistoryRepo> BookingCompletedHistory();

    @GET("vendor/my/bookings/Cancelled")
    Call<BookingActiveHistoryRepo> BookingCancelledHistory();


    @GET("vendor/upcoming/bookings/Pending-Approval")
    Call<BookingActiveHistoryRepo> PendingApprovals();

    @GET("vendor/upcoming/bookings/Today")
    Call<BookingActiveHistoryRepo> ForToday();

    @GET("vendor/upcoming/bookings/Tomorrow")
    Call<BookingActiveHistoryRepo> ForTomorrow();

    @GET("vendor/upcoming/bookings/Week")
    Call<BookingActiveHistoryRepo> ForWeek();

    @GET("vendor/upcoming/bookings/Month")
    Call<BookingActiveHistoryRepo> ForMonth();


    @GET("vendor/my-booking/history/Yesterday")
    Call<BookingActiveHistoryRepo> Yestarday();


    @GET("vendor/my-booking/history/Seven")
    Call<BookingActiveHistoryRepo> ForLast7days();

    @GET("vendor/my-booking/history/15-days")
    Call<BookingActiveHistoryRepo> ForLast15days();

    @GET("vendor/my-booking/history/30-days")
    Call<BookingActiveHistoryRepo> ForLast30days();


    @GET("vendor/accept/booking/{booking_id}")
    Call<ResponseBody> acceptBooking(
            @Path("booking_id") String booking_id
    );

    @GET("vendor/reject/booking/{booking_id}")
    Call<ResponseBody> rejectBooking(
            @Path("booking_id") String booking_id
    );

    @GET("vendor/start-service/send-otp/{order_id}")
    Call<ResponseBody> Startservice(
            @Path("order_id") String booking_id
    );


    @FormUrlEncoded
    @POST("vendor/verify-otp/start-service")
    Call<ResponseBody> VerifyStartserviceootp(
            @Field("otp") String otp


    );


    @Multipart
    @POST("vendor/order/change-status")
    Call<ResponseBody> Changeorder(
            @Part("order_id") RequestBody order_id,
            @Part("status") RequestBody status,
            @Part("payment_collected") RequestBody payment_collected,
            @Part("remark") RequestBody remark,


            @Part MultipartBody.Part file);


    @Multipart
    @POST("vendor/order/change-status")
    Call<ResponseBody> ChangeWithoutCODorder(
            @Part("order_id") RequestBody order_id,
            @Part("status") RequestBody status,
            @Part("remark") RequestBody remark,
            @Part MultipartBody.Part file);


    @FormUrlEncoded
    @POST("vendor/veirfy-otp/change-status")
    Call<ResponseBody> VerifyWithoutCODCompleteserviceotp(
            @Field("order_id") String order_id,
            @Field("status") String status,
            @Field("remark") String remark,

            @Field("otp") String otp


    );


    @FormUrlEncoded
    @POST("vendor/veirfy-otp/change-status")
    Call<ResponseBody> VerifyCODCompleteserviceotp(
            @Field("order_id") String order_id,
            @Field("status") String status,
            @Field("remark") String remark,
            @Field("otp") String otp,
            @Field("payment_collected") String payment_collected

    );

    @GET("vendor/order/detail/{order_id}")
    Call<BookingHistoryDetails> BookingHistoryDetails(
            @Path("order_id") String order_id
    );


    @GET("vendor/view-payout/payment-info/{payout_id}")
    Call<PaymentInfoPopup> PaymentInfoPopupDetails(
            @Path("payout_id") String order_id
    );



}
