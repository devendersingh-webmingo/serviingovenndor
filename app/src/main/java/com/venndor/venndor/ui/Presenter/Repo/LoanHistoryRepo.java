package com.venndor.venndor.ui.Presenter.Repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoanHistoryRepo {


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

        @SerializedName("my_loan")
        @Expose
        private MyLoan myLoan;
        @SerializedName("histories")
        @Expose
        private List<History> histories = null;

        public MyLoan getMyLoan() {
            return myLoan;
        }

        public void setMyLoan(MyLoan myLoan) {
            this.myLoan = myLoan;
        }

        public List<History> getHistories() {
            return histories;
        }

        public void setHistories(List<History> histories) {
            this.histories = histories;
        }

        public class History {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("loan_id")
            @Expose
            private String loanId;
            @SerializedName("booking_id")
            @Expose
            private Object bookingId;
            @SerializedName("payout_id")
            @Expose
            private String payoutId;
            @SerializedName("transaction_id")
            @Expose
            private Object transactionId;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("remaining")
            @Expose
            private String remaining;
            @SerializedName("created_by")
            @Expose
            private String createdBy;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("remark")
            @Expose
            private String remark;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;


            @SerializedName("date_time")
            @Expose
            private String date_time;

            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLoanId() {
                return loanId;
            }

            public void setLoanId(String loanId) {
                this.loanId = loanId;
            }

            public Object getBookingId() {
                return bookingId;
            }

            public void setBookingId(Object bookingId) {
                this.bookingId = bookingId;
            }

            public String getPayoutId() {
                return payoutId;
            }

            public void setPayoutId(String payoutId) {
                this.payoutId = payoutId;
            }

            public Object getTransactionId() {
                return transactionId;
            }

            public void setTransactionId(Object transactionId) {
                this.transactionId = transactionId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getRemaining() {
                return remaining;
            }

            public void setRemaining(String remaining) {
                this.remaining = remaining;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
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

        }

        public class MyLoan {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("vendor_id")
            @Expose
            private String vendorId;
            @SerializedName("booking_id")
            @Expose
            private Object bookingId;
            @SerializedName("loan_id")
            @Expose
            private String loanId;
            @SerializedName("transaction_id")
            @Expose
            private String transactionId;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("amount_deposite")
            @Expose
            private String amountDeposite;
            @SerializedName("deducation")
            @Expose
            private String deducation;
            @SerializedName("loan_details")
            @Expose
            private String loanDetails;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("created_by")
            @Expose
            private String createdBy;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("region")
            @Expose
            private Object region;
            @SerializedName("deposite_status")
            @Expose
            private String depositeStatus;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;
            @SerializedName("date_time")
            @Expose
            private String dateTime;
            @SerializedName("loan_repay")
            @Expose
            private String loanRepay;

            @SerializedName("unique_id")
            @Expose
            private String unique_id;

            public String getUnique_id() {
                return unique_id;
            }

            public void setUnique_id(String unique_id) {
                this.unique_id = unique_id;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVendorId() {
                return vendorId;
            }

            public void setVendorId(String vendorId) {
                this.vendorId = vendorId;
            }

            public Object getBookingId() {
                return bookingId;
            }

            public void setBookingId(Object bookingId) {
                this.bookingId = bookingId;
            }

            public String getLoanId() {
                return loanId;
            }

            public void setLoanId(String loanId) {
                this.loanId = loanId;
            }

            public String getTransactionId() {
                return transactionId;
            }

            public void setTransactionId(String transactionId) {
                this.transactionId = transactionId;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getAmountDeposite() {
                return amountDeposite;
            }

            public void setAmountDeposite(String amountDeposite) {
                this.amountDeposite = amountDeposite;
            }

            public String getDeducation() {
                return deducation;
            }

            public void setDeducation(String deducation) {
                this.deducation = deducation;
            }

            public String getLoanDetails() {
                return loanDetails;
            }

            public void setLoanDetails(String loanDetails) {
                this.loanDetails = loanDetails;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getRegion() {
                return region;
            }

            public void setRegion(Object region) {
                this.region = region;
            }

            public String getDepositeStatus() {
                return depositeStatus;
            }

            public void setDepositeStatus(String depositeStatus) {
                this.depositeStatus = depositeStatus;
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

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getLoanRepay() {
                return loanRepay;
            }

            public void setLoanRepay(String loanRepay) {
                this.loanRepay = loanRepay;
            }


        }


    }

}