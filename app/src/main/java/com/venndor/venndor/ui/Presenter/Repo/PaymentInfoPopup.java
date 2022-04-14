package com.venndor.venndor.ui.Presenter.Repo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentInfoPopup {



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

        @SerializedName("check_type")
        @Expose
        private String checkType;
        @SerializedName("mode_of_payment")
        @Expose
        private String modeOfPayment;
        @SerializedName("payment_info")
        @Expose
        private PaymentInfo paymentInfo;

        public String getCheckType() {
            return checkType;
        }

        public void setCheckType(String checkType) {
            this.checkType = checkType;
        }

        public String getModeOfPayment() {
            return modeOfPayment;
        }

        public void setModeOfPayment(String modeOfPayment) {
            this.modeOfPayment = modeOfPayment;
        }

        public PaymentInfo getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
        }



        public class PaymentInfo {

            @SerializedName("cheque_number")
            @Expose
            private String chequeNumber;
            @SerializedName("cheque_date")
            @Expose
            private String chequeDate;
            @SerializedName("bank_name")
            @Expose
            private String bankName;
            @SerializedName("bank_branch")
            @Expose
            private String bankBranch;
            @SerializedName("utr_number")
            @Expose
            private String utrNumber;
            @SerializedName("ref_id")
            @Expose
            private String refId;
            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("payment_screenshot")
            @Expose
            private String paymentScreenshot;
            @SerializedName("remark")
            @Expose
            private String remark;

            public String getChequeNumber() {
                return chequeNumber;
            }

            public void setChequeNumber(String chequeNumber) {
                this.chequeNumber = chequeNumber;
            }

            public String getChequeDate() {
                return chequeDate;
            }

            public void setChequeDate(String chequeDate) {
                this.chequeDate = chequeDate;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankBranch() {
                return bankBranch;
            }

            public void setBankBranch(String bankBranch) {
                this.bankBranch = bankBranch;
            }

            public String getUtrNumber() {
                return utrNumber;
            }

            public void setUtrNumber(String utrNumber) {
                this.utrNumber = utrNumber;
            }

            public String getRefId() {
                return refId;
            }

            public void setRefId(String refId) {
                this.refId = refId;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getPaymentScreenshot() {
                return paymentScreenshot;
            }

            public void setPaymentScreenshot(String paymentScreenshot) {
                this.paymentScreenshot = paymentScreenshot;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            @Override
            public String toString() {
                return "PaymentInfo{" +
                        "chequeNumber='" + chequeNumber + '\'' +
                        ", chequeDate='" + chequeDate + '\'' +
                        ", bankName='" + bankName + '\'' +
                        ", bankBranch='" + bankBranch + '\'' +
                        ", utrNumber='" + utrNumber + '\'' +
                        ", refId='" + refId + '\'' +
                        ", orderId='" + orderId + '\'' +
                        ", paymentScreenshot='" + paymentScreenshot + '\'' +
                        ", remark='" + remark + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Data{" +
                    "checkType='" + checkType + '\'' +
                    ", modeOfPayment='" + modeOfPayment + '\'' +
                    ", paymentInfo=" + paymentInfo +
                    '}';
        }
    }
}
