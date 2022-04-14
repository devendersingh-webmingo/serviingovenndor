package com.venndor.venndor.ui.Presenter.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UpdateProfile_request {

    @SerializedName("_method")
    @Expose
    private String _method;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("state_id")
    @Expose
    private String state_id;
    @SerializedName("city_id")
    @Expose
    private String city_id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("serve_state")
    @Expose
    private String serve_state;
    @SerializedName("serve_city")
    @Expose
    private String serve_city;
    @SerializedName("serve_pincodes")
    @Expose
    private ArrayList<Integer> serve_pincodes = null;
    @SerializedName("categories")
    @Expose
    private ArrayList<Integer> categories = null;
    @SerializedName("sub_categories")
    @Expose
    private ArrayList<Integer> sub_categories = null;
    @SerializedName("pan_card_number")
    @Expose
    private String pan_card_number;
    @SerializedName("aadhaar_card_number")
    @Expose
    private String aadhaar_card_number;
    @SerializedName("business_proof_number")
    @Expose
    private String business_proof_number;
    @SerializedName("business_address")
    @Expose
    private String business_address;


    public UpdateProfile_request(String _method, String first_name, String last_name, String email, String mobile_number,  String gender, String dob, String state_id, String city_id, String address, String pincode, String serve_state, String serve_city, ArrayList<Integer> serve_pincodes, ArrayList<Integer> categories, ArrayList<Integer> sub_categories, String pan_card_number, String aadhaar_card_number, String business_proof_number, String business_address) {
        this._method = _method;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_number = mobile_number;

        this.gender = gender;
        this.dob = dob;
        this.state_id = state_id;
        this.city_id = city_id;
        this.address = address;
        this.pincode = pincode;
        this.serve_state = serve_state;
        this.serve_city = serve_city;
        this.serve_pincodes = serve_pincodes;
        this.categories = categories;
        this.sub_categories = sub_categories;
        this.pan_card_number = pan_card_number;
        this.aadhaar_card_number = aadhaar_card_number;
        this.business_proof_number = business_proof_number;
        this.business_address = business_address;
    }
}