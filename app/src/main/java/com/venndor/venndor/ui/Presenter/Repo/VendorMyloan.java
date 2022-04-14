package com.venndor.venndor.ui.Presenter.Repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorMyloan {



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

        @SerializedName("total_loan")
        @Expose
        private Integer totalLoan;
        @SerializedName("remaining_loan")
        @Expose
        private Integer remainingLoan;
        @SerializedName("loans")
        @Expose
        private List<Loan> loans = null;

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

        public List<Loan> getLoans() {
            return loans;
        }

        public void setLoans(List<Loan> loans) {
            this.loans = loans;
        }


        public class Loan {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("vendor_id")
            @Expose
            private Integer vendorId;
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
            private Integer amountDeposite;
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
            @SerializedName("related_order")
            @Expose
            private Object relatedOrder;


            @SerializedName("loan_repay")
            @Expose
            private String loan_repay;

            public String getLoan_repay() {
                return loan_repay;
            }

            public void setLoan_repay(String loan_repay) {
                this.loan_repay = loan_repay;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getVendorId() {
                return vendorId;
            }

            public void setVendorId(Integer vendorId) {
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

            public Integer getAmountDeposite() {
                return amountDeposite;
            }

            public void setAmountDeposite(Integer amountDeposite) {
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

            public Object getRelatedOrder() {
                return relatedOrder;
            }

            public void setRelatedOrder(Object relatedOrder) {
                this.relatedOrder = relatedOrder;
            }

        }

    }
}
