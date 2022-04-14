package com.venndor.venndor.ui.Presenter.Repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginWithOTPReop {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public class Data {

        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("access_token")
        @Expose
        private String accessToken;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public class User {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("permission_id")
            @Expose
            private Object permissionId;
            @SerializedName("referral_user_id")
            @Expose
            private Object referralUserId;
            @SerializedName("referral_master_id")
            @Expose
            private Object referralMasterId;
            @SerializedName("name")
            @Expose
            private Object name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("mobile_number")
            @Expose
            private String mobileNumber;
            @SerializedName("state_id")
            @Expose
            private Integer stateId;
            @SerializedName("city_id")
            @Expose
            private Integer cityId;
            @SerializedName("category_id")
            @Expose
            private String categoryId;
            @SerializedName("sub_category_id")
            @Expose
            private String subCategoryId;
            @SerializedName("service_id")
            @Expose
            private Object serviceId;
            @SerializedName("profile_pic")
            @Expose
            private Object profilePic;
            @SerializedName("role")
            @Expose
            private String role;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("pincode")
            @Expose
            private String pincode;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("last_name")
            @Expose
            private String lastName;
            @SerializedName("business_type")
            @Expose
            private Object businessType;
            @SerializedName("website")
            @Expose
            private Object website;
            @SerializedName("referral_id")
            @Expose
            private Object referralId;
            @SerializedName("full_address")
            @Expose
            private Object fullAddress;
            @SerializedName("pan_card_number")
            @Expose
            private Object panCardNumber;
            @SerializedName("aadhaar_card_number")
            @Expose
            private Object aadhaarCardNumber;
            @SerializedName("business_proof_number")
            @Expose
            private Object businessProofNumber;
            @SerializedName("business_address")
            @Expose
            private Object businessAddress;
            @SerializedName("account_type")
            @Expose
            private Object accountType;
            @SerializedName("account_name")
            @Expose
            private Object accountName;
            @SerializedName("account_number")
            @Expose
            private Object accountNumber;
            @SerializedName("ifsc_code")
            @Expose
            private Object ifscCode;
            @SerializedName("bank_name")
            @Expose
            private Object bankName;
            @SerializedName("bank_branch")
            @Expose
            private Object bankBranch;
            @SerializedName("pan_card_document")
            @Expose
            private Object panCardDocument;
            @SerializedName("aadhaar_card_front")
            @Expose
            private Object aadhaarCardFront;
            @SerializedName("aadhaar_card_back")
            @Expose
            private Object aadhaarCardBack;
            @SerializedName("business_proof_document")
            @Expose
            private Object businessProofDocument;
            @SerializedName("cancelled_cheque_img")
            @Expose
            private Object cancelledChequeImg;
            @SerializedName("photographs")
            @Expose
            private Object photographs;
            @SerializedName("other_documents")
            @Expose
            private Object otherDocuments;
            @SerializedName("country_id")
            @Expose
            private Object countryId;
            @SerializedName("landmark")
            @Expose
            private Object landmark;
            @SerializedName("status")
            @Expose
            private Integer status;
            @SerializedName("email_verified_at")
            @Expose
            private String emailVerifiedAt;
            @SerializedName("current_team_id")
            @Expose
            private Object currentTeamId;
            @SerializedName("profile_photo_path")
            @Expose
            private Object profilePhotoPath;
            @SerializedName("wallet_amount")
            @Expose
            private String walletAmount;
            @SerializedName("unique_id")
            @Expose
            private String uniqueId;
            @SerializedName("total_loan")
            @Expose
            private Integer totalLoan;
            @SerializedName("remaining_loan")
            @Expose
            private Integer remainingLoan;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("father_name")
            @Expose
            private Object fatherName;
            @SerializedName("sender_amount")
            @Expose
            private Integer senderAmount;
            @SerializedName("receiver_amount")
            @Expose
            private Integer receiverAmount;
            @SerializedName("sender_referral_status")
            @Expose
            private String senderReferralStatus;
            @SerializedName("receiver_referral_status")
            @Expose
            private String receiverReferralStatus;
            @SerializedName("serve_state_id")
            @Expose
            private Integer serveStateId;
            @SerializedName("serve_city_id")
            @Expose
            private Integer serveCityId;
            @SerializedName("serve_pincodes")
            @Expose
            private String servePincodes;
            @SerializedName("mobile_verified")
            @Expose
            private Object mobileVerified;
            @SerializedName("vaccinated")
            @Expose
            private String vaccinated;
            @SerializedName("vaccine_file")
            @Expose
            private String vaccineFile;
            @SerializedName("device_token")
            @Expose
            private Object deviceToken;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("deleted_at")
            @Expose
            private Object deletedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Object getPermissionId() {
                return permissionId;
            }

            public void setPermissionId(Object permissionId) {
                this.permissionId = permissionId;
            }

            public Object getReferralUserId() {
                return referralUserId;
            }

            public void setReferralUserId(Object referralUserId) {
                this.referralUserId = referralUserId;
            }

            public Object getReferralMasterId() {
                return referralMasterId;
            }

            public void setReferralMasterId(Object referralMasterId) {
                this.referralMasterId = referralMasterId;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
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

            public Integer getStateId() {
                return stateId;
            }

            public void setStateId(Integer stateId) {
                this.stateId = stateId;
            }

            public Integer getCityId() {
                return cityId;
            }

            public void setCityId(Integer cityId) {
                this.cityId = cityId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getSubCategoryId() {
                return subCategoryId;
            }

            public void setSubCategoryId(String subCategoryId) {
                this.subCategoryId = subCategoryId;
            }

            public Object getServiceId() {
                return serviceId;
            }

            public void setServiceId(Object serviceId) {
                this.serviceId = serviceId;
            }

            public Object getProfilePic() {
                return profilePic;
            }

            public void setProfilePic(Object profilePic) {
                this.profilePic = profilePic;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

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

            public Object getBusinessType() {
                return businessType;
            }

            public void setBusinessType(Object businessType) {
                this.businessType = businessType;
            }

            public Object getWebsite() {
                return website;
            }

            public void setWebsite(Object website) {
                this.website = website;
            }

            public Object getReferralId() {
                return referralId;
            }

            public void setReferralId(Object referralId) {
                this.referralId = referralId;
            }

            public Object getFullAddress() {
                return fullAddress;
            }

            public void setFullAddress(Object fullAddress) {
                this.fullAddress = fullAddress;
            }

            public Object getPanCardNumber() {
                return panCardNumber;
            }

            public void setPanCardNumber(Object panCardNumber) {
                this.panCardNumber = panCardNumber;
            }

            public Object getAadhaarCardNumber() {
                return aadhaarCardNumber;
            }

            public void setAadhaarCardNumber(Object aadhaarCardNumber) {
                this.aadhaarCardNumber = aadhaarCardNumber;
            }

            public Object getBusinessProofNumber() {
                return businessProofNumber;
            }

            public void setBusinessProofNumber(Object businessProofNumber) {
                this.businessProofNumber = businessProofNumber;
            }

            public Object getBusinessAddress() {
                return businessAddress;
            }

            public void setBusinessAddress(Object businessAddress) {
                this.businessAddress = businessAddress;
            }

            public Object getAccountType() {
                return accountType;
            }

            public void setAccountType(Object accountType) {
                this.accountType = accountType;
            }

            public Object getAccountName() {
                return accountName;
            }

            public void setAccountName(Object accountName) {
                this.accountName = accountName;
            }

            public Object getAccountNumber() {
                return accountNumber;
            }

            public void setAccountNumber(Object accountNumber) {
                this.accountNumber = accountNumber;
            }

            public Object getIfscCode() {
                return ifscCode;
            }

            public void setIfscCode(Object ifscCode) {
                this.ifscCode = ifscCode;
            }

            public Object getBankName() {
                return bankName;
            }

            public void setBankName(Object bankName) {
                this.bankName = bankName;
            }

            public Object getBankBranch() {
                return bankBranch;
            }

            public void setBankBranch(Object bankBranch) {
                this.bankBranch = bankBranch;
            }

            public Object getPanCardDocument() {
                return panCardDocument;
            }

            public void setPanCardDocument(Object panCardDocument) {
                this.panCardDocument = panCardDocument;
            }

            public Object getAadhaarCardFront() {
                return aadhaarCardFront;
            }

            public void setAadhaarCardFront(Object aadhaarCardFront) {
                this.aadhaarCardFront = aadhaarCardFront;
            }

            public Object getAadhaarCardBack() {
                return aadhaarCardBack;
            }

            public void setAadhaarCardBack(Object aadhaarCardBack) {
                this.aadhaarCardBack = aadhaarCardBack;
            }

            public Object getBusinessProofDocument() {
                return businessProofDocument;
            }

            public void setBusinessProofDocument(Object businessProofDocument) {
                this.businessProofDocument = businessProofDocument;
            }

            public Object getCancelledChequeImg() {
                return cancelledChequeImg;
            }

            public void setCancelledChequeImg(Object cancelledChequeImg) {
                this.cancelledChequeImg = cancelledChequeImg;
            }

            public Object getPhotographs() {
                return photographs;
            }

            public void setPhotographs(Object photographs) {
                this.photographs = photographs;
            }

            public Object getOtherDocuments() {
                return otherDocuments;
            }

            public void setOtherDocuments(Object otherDocuments) {
                this.otherDocuments = otherDocuments;
            }

            public Object getCountryId() {
                return countryId;
            }

            public void setCountryId(Object countryId) {
                this.countryId = countryId;
            }

            public Object getLandmark() {
                return landmark;
            }

            public void setLandmark(Object landmark) {
                this.landmark = landmark;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public String getEmailVerifiedAt() {
                return emailVerifiedAt;
            }

            public void setEmailVerifiedAt(String emailVerifiedAt) {
                this.emailVerifiedAt = emailVerifiedAt;
            }

            public Object getCurrentTeamId() {
                return currentTeamId;
            }

            public void setCurrentTeamId(Object currentTeamId) {
                this.currentTeamId = currentTeamId;
            }

            public Object getProfilePhotoPath() {
                return profilePhotoPath;
            }

            public void setProfilePhotoPath(Object profilePhotoPath) {
                this.profilePhotoPath = profilePhotoPath;
            }

            public String getWalletAmount() {
                return walletAmount;
            }

            public void setWalletAmount(String walletAmount) {
                this.walletAmount = walletAmount;
            }

            public String getUniqueId() {
                return uniqueId;
            }

            public void setUniqueId(String uniqueId) {
                this.uniqueId = uniqueId;
            }

            public Integer getTotalLoan() {
                return totalLoan;
            }

            public void setTotalLoan(Integer totalLoan) {
                this.totalLoan = totalLoan;
            }

            public Integer getRemainingLoan() {
                return remainingLoan;
            }

            public void setRemainingLoan(Integer remainingLoan) {
                this.remainingLoan = remainingLoan;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public Object getFatherName() {
                return fatherName;
            }

            public void setFatherName(Object fatherName) {
                this.fatherName = fatherName;
            }

            public Integer getSenderAmount() {
                return senderAmount;
            }

            public void setSenderAmount(Integer senderAmount) {
                this.senderAmount = senderAmount;
            }

            public Integer getReceiverAmount() {
                return receiverAmount;
            }

            public void setReceiverAmount(Integer receiverAmount) {
                this.receiverAmount = receiverAmount;
            }

            public String getSenderReferralStatus() {
                return senderReferralStatus;
            }

            public void setSenderReferralStatus(String senderReferralStatus) {
                this.senderReferralStatus = senderReferralStatus;
            }

            public String getReceiverReferralStatus() {
                return receiverReferralStatus;
            }

            public void setReceiverReferralStatus(String receiverReferralStatus) {
                this.receiverReferralStatus = receiverReferralStatus;
            }

            public Integer getServeStateId() {
                return serveStateId;
            }

            public void setServeStateId(Integer serveStateId) {
                this.serveStateId = serveStateId;
            }

            public Integer getServeCityId() {
                return serveCityId;
            }

            public void setServeCityId(Integer serveCityId) {
                this.serveCityId = serveCityId;
            }

            public String getServePincodes() {
                return servePincodes;
            }

            public void setServePincodes(String servePincodes) {
                this.servePincodes = servePincodes;
            }

            public Object getMobileVerified() {
                return mobileVerified;
            }

            public void setMobileVerified(Object mobileVerified) {
                this.mobileVerified = mobileVerified;
            }

            public String getVaccinated() {
                return vaccinated;
            }

            public void setVaccinated(String vaccinated) {
                this.vaccinated = vaccinated;
            }

            public String getVaccineFile() {
                return vaccineFile;
            }

            public void setVaccineFile(String vaccineFile) {
                this.vaccineFile = vaccineFile;
            }

            public Object getDeviceToken() {
                return deviceToken;
            }

            public void setDeviceToken(Object deviceToken) {
                this.deviceToken = deviceToken;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public Object getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(Object deletedAt) {
                this.deletedAt = deletedAt;
            }

        }


    }

}