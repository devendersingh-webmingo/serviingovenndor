package com.venndor.venndor.ui.Presenter.Repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateprofileUserRepo {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private SignupUserRepo.Data data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignupUserRepo.Data getData() {
        return data;
    }

    public void setData(SignupUserRepo.Data data) {
        this.data = data;
    }


    public class Data {

        @SerializedName("user")
        @Expose
        private SignupUserRepo.Data.User user;

        public SignupUserRepo.Data.User getUser() {
            return user;
        }

        public void setUser(SignupUserRepo.Data.User user) {
            this.user = user;
        }


        public class User {

            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("mobile_number")
            @Expose
            private String mobileNumber;
            @SerializedName("state_id")
            @Expose
            private String stateId;
            @SerializedName("city_id")
            @Expose
            private String cityId;
            @SerializedName("serve_state_id")
            @Expose
            private String serveStateId;
            @SerializedName("serve_city_id")
            @Expose
            private String serveCityId;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("pincode")
            @Expose
            private String pincode;
            @SerializedName("category_id")
            @Expose
            private String categoryId;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("sub_category_id")
            @Expose
            private String subCategoryId;
            @SerializedName("role")
            @Expose
            private String role;
            @SerializedName("status")
            @Expose
            private Integer status;
            @SerializedName("unique_id")
            @Expose
            private String uniqueId;
            @SerializedName("serve_pincodes")
            @Expose
            private String servePincodes;
            @SerializedName("vaccinated")
            @Expose
            private String vaccinated;
            @SerializedName("vaccine_file")
            @Expose
            private Object vaccineFile;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("id")
            @Expose
            private Integer id;

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobileNumber() {
                return mobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                this.mobileNumber = mobileNumber;
            }

            public String getStateId() {
                return stateId;
            }

            public void setStateId(String stateId) {
                this.stateId = stateId;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getServeStateId() {
                return serveStateId;
            }

            public void setServeStateId(String serveStateId) {
                this.serveStateId = serveStateId;
            }

            public String getServeCityId() {
                return serveCityId;
            }

            public void setServeCityId(String serveCityId) {
                this.serveCityId = serveCityId;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(String subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getUniqueId() {
                return uniqueId;
            }

            public void setUniqueId(String uniqueId) {
                this.uniqueId = uniqueId;
            }

            public String getServePincodes() {
                return servePincodes;
            }

            public void setServePincodes(String servePincodes) {
                this.servePincodes = servePincodes;
            }

            public String getVaccinated() {
                return vaccinated;
            }

            public void setVaccinated(String vaccinated) {
                this.vaccinated = vaccinated;
            }

            public Object getVaccineFile() {
                return vaccineFile;
            }

            public void setVaccineFile(Object vaccineFile) {
                this.vaccineFile = vaccineFile;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

        }

    }
}
