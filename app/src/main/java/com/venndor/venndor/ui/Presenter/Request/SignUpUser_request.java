package com.venndor.venndor.ui.Presenter.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.Query;

public class SignUpUser_request {


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
    @SerializedName("state")

    @Expose
    private String state;
    @SerializedName("city")

    @Expose
    private String city;
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


    @SerializedName("pincodes")
    @Expose
    private ArrayList<Integer> pincodes = null;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("sub_categories")
    @Expose
    private ArrayList<Integer> sub_categories = null;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("password_confirmation")
    @Expose
    private String password_confirmation;

    @SerializedName("vaccinated")
    @Expose
    private String vaccinated;

    public SignUpUser_request(String first_name, String last_name, String email, String mobile_number, String gender, String dob, String state, String city, String address, String pincode, String serve_state, String serve_city, ArrayList<Integer> pincodes, String category, ArrayList<Integer> sub_categories, String password, String password_confirmation, String vaccinated) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.gender = gender;
        this.dob = dob;
        this.state = state;
        this.city = city;
        this.address = address;
        this.pincode = pincode;
        this.serve_state = serve_state;
        this.serve_city = serve_city;
        this.pincodes = pincodes;
        this.category = category;
        this.sub_categories = sub_categories;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.vaccinated = vaccinated;
    }

    @Override
    public String toString() {
        return "SignUpUser_request{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", serve_state='" + serve_state + '\'' +
                ", serve_city='" + serve_city + '\'' +
                ", pincodes=" + pincodes +
                ", category='" + category + '\'' +
                ", sub_categories=" + sub_categories +
                ", password='" + password + '\'' +
                ", password_confirmation='" + password_confirmation + '\'' +
                ", vaccinated='" + vaccinated + '\'' +
                '}';
    }
}


