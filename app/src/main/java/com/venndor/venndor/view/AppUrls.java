package com.venndor.venndor.view;

public class AppUrls {
    public static final String baseUrl = "https://serviingo.com/api/";
    //baseimagepath
    public static final String baseImagePath = "http://themetoaster.co.in/kkmedical/public/storage/";
    public static final String profileImageUrl = "https://serviingo.com/public/images/user_images/";
    public static final String vendorImageUrl = "https://serviingo.com/public/images/vendor_images";
    public static final String documents_image_url = "https://serviingo.com/public/images/vendor_images/";

    //get  6|jM5Xa95kNaonAmJeh8mleXFkKSartPZ9szofV0u6.83|pWdoNdvIwHKaWxBwK8HIwkpoFSEgwTkwe5UWS9qQ
    public static final String login     = baseUrl + "vendor/login";//email,phonno,password
    public static final String otpLogin   = baseUrl + "vendor/send/otp/login";//PhoneNo
    public static final String sendOTPNewSignUp   = baseUrl + "vendor/send/otp/new-signup";
    public static final String otpNewuser = baseUrl + "vendor/verify/otp/new-user";
    public static final String otploginbyOTPuser = baseUrl + "vendor/login/by-otp";
    public static final String registration = baseUrl + "vendor/signup";
    public static final String getstate = baseUrl + "vendor/states";
    public static final String getCity = baseUrl + "vendor/cities/";
    public static final String getLogout = baseUrl + "vendor/logout";
    public static final String getservestate = baseUrl + "vendor/serve/states";
    public static final String getserveCity = baseUrl + "vendor/serve/cities/";
    public static final String getProfile = baseUrl + "vendor/my-profile";
    public static final String getdashboard = baseUrl + "vendor/dashboard";
    public static final String getPincode = baseUrl + "vendor/area/pincodes/";//stateid+cityid
    public static final String getCategory = baseUrl + "vendor/categories/";//cityid
    public static final String getSubCategory = baseUrl + "vendor/sub-categories/";//CategoryId
    public static final String Postupdateprofilephoto = baseUrl + "vendor/update/profile-photo";
    public static final String Postvendorupdateprofile = baseUrl + "vendor/update/profile";
    public static final String Postvendorupdatedocuments = baseUrl + "vendor/update/documents";
    public static final String Postvendorupdatebankdetails = baseUrl + "vendor/update/bank-details";
    public static final String Postvendorupdatepassword = baseUrl + "vendor/update/password";
    public static final String GettvendormyLoan = baseUrl + "vendor/my-loan";
    public static final String GettvendormyLoanHistory = baseUrl + "vendor/loan/history/2";
    public static final String PostvendorapplyLoan = baseUrl + "vendor/apply/loan";

    public static final String Getvendorbooking = baseUrl + "vendor/my/bookings/Active";
    public static final String Getvendororderdetail= baseUrl + "vendor/order/detail/";//id
    public static final String GetvendororderUserInfo= baseUrl + "vendor/booking/user-info/";//id
    public static final String Getvendoractivebooking= baseUrl + "vendor/accept/booking/";//id
    public static final String Getvendorrejectbooking= baseUrl + "vendor/reject/booking/";//id
    public static final String GetvendorstartserviceSendOTP= baseUrl + "vendor/start-service/send-otp/";//id
    public static final String Postvendorverifyotpstartservice= baseUrl + "vendor/verify-otp/start-service";//id


    public static final String Getvendorcomissions = baseUrl + "vendor/comissions";
    public static final String GetvendorpayoutsPending = baseUrl + "vendor/payouts/Pending";
    public static final String Getvendorviewpayoutpaymentinfo = baseUrl + "vendor/view-payout/payment-info/2";
    public static final String Getvendorpayoutbookings = baseUrl + "vendor/payout/bookings/";//id
    public static final String Getvendorpayoutinvoice = baseUrl + "vendor/payout/invoice/";//id


    public static final String Getvendorbuybookings = baseUrl + "vendor/buy/bookings";//id
    public static final String Postvendorbuylead = baseUrl + "vendor/buy-lead";//id

    public static final String Getvendormywallet = baseUrl + "vendor/my-wallet/Credit";//id
    public static final String GetvendormywalletDebit = baseUrl + "vendor/my-wallet/Debit";//id
    public static final String GetvendorComissions = baseUrl + "vendor/comissions";//id
    public static final String PostrezorPayOrder = baseUrl + "vendor/generate-razorpay/order";//id
    public static final String PostaddAmountWallet = baseUrl + "vendor/add-amount/wallet";//id

    public static final String PostForgetAPI = baseUrl + "vendor/forgot-password/send-otp";//id
    public static final String Postvendorverifyotp = baseUrl + "vendor/forgot-password/verify-otp";//id
    public static final String PostvendornewPassWord = baseUrl + "vendor/create/new-password";//id

    public static final String getRecentLeads = baseUrl + "vendor/buy-lead/history/Recent";//id
    public static final String getTodayLeads = baseUrl + "vendor/buy-lead/history/Today";//id
    public static final String getYesterdayLeads = baseUrl + "vendor/buy-lead/history/Yesterday";//id
    public static final String getWeekLeads = baseUrl + "vendor/buy-lead/history/Week";//id
    public static final String getMonthLeads = baseUrl + "vendor/buy-lead/history/Month";//id
    public static final String getLeadsDetail = baseUrl + "vendor/order/detail/";//id

    public static final String getbookingsMonth = baseUrl + "vendor/upcoming/bookings/Month";//id
    public static final String getbookingsYesterday = baseUrl + "vendor/my-booking/history/Yesterday";//id
    public static final String getSummary = baseUrl + "vendor/buy-lead/summery/";//id

    public static final String getvendorsubcats = baseUrl + "vendor/trainings/sub-cats";//id
    public static final String getvendortraning = baseUrl + "vendor/trainings/";//id
    public static final String getvendormyoffers = baseUrl + "vendor/offers";//id
    public static final String getvendormytickets = baseUrl + "vendor/my/tickets";//id
    public static final String Postvendorgenerateticket = baseUrl + "vendor/generate/ticket";//id
    public static final String getvendortabs = baseUrl + "vendor/support/tabs";//id
    public static final String getvendorchat= baseUrl + "vendor/my-ticket-chat/";//id
    public static final String PostvendorSendMessage= baseUrl + "vendor/send/message";//id
    public static final String PostvendorsupportQuestions= baseUrl + "vendor/support/questions";//id
    public static final String Postvendormultiplesubcategories= baseUrl + "vendor/multiple/sub-categories";//id

}

