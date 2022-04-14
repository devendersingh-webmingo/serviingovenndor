package com.venndor.venndor.ui.Presenter.Repo.BookingHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingHistoryDetails {

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

        @SerializedName("package")
        @Expose
        private Package _package;
        @SerializedName("included_services")
        @Expose
        private List<IncludedService> includedServices = null;

        @SerializedName("support_questions")
        @Expose
        private List<SupportQuestion> supportQuestions = null;
        @SerializedName("order_info")
        @Expose
        private OrderInfo orderInfo;
        @SerializedName("service_amount")
        @Expose
        private Integer serviceAmount;
        @SerializedName("addon_amount")
        @Expose
        private Integer addonAmount;
        @SerializedName("sub_total")
        @Expose
        private Integer subTotal;
        @SerializedName("service_discount")
        @Expose
        private Integer serviceDiscount;
        @SerializedName("membership_discount")
        @Expose
        private Integer membershipDiscount;
        @SerializedName("total_discount")
        @Expose
        private Integer totalDiscount;
        @SerializedName("membership_fees")
        @Expose
        private Integer membershipFees;
        @SerializedName("hygiene_fees")
        @Expose
        private Integer hygieneFees;
        @SerializedName("billing_amount")
        @Expose
        private String billingAmount;
        @SerializedName("vendor_billing_amount")
        @Expose
        private Integer vendorBillingAmount;
        @SerializedName("invoice_url")
        @Expose
        private String invoiceUrl;

        @SerializedName("button_show")
        @Expose
        private String button_show;



        @SerializedName("vendor_comission")
        @Expose
        private String vendor_comission;


        @SerializedName("vendor_comission_percent")
        @Expose
        private String vendor_comission_percent;

        public String getVendor_comission() {
            return vendor_comission;
        }

        public void setVendor_comission(String vendor_comission) {
            this.vendor_comission = vendor_comission;
        }

        public String getVendor_comission_percent() {
            return vendor_comission_percent;
        }

        public void setVendor_comission_percent(String vendor_comission_percent) {
            this.vendor_comission_percent = vendor_comission_percent;
        }

        public Package get_package() {
            return _package;
        }

        public void set_package(Package _package) {
            this._package = _package;
        }

        public String getButton_show() {
            return button_show;
        }

        public void setButton_show(String button_show) {
            this.button_show = button_show;
        }

        public Package getPackage() {
            return _package;
        }

        public void setPackage(Package _package) {
            this._package = _package;
        }

        public List<IncludedService> getIncludedServices() {
            return includedServices;
        }

        public void setIncludedServices(List<IncludedService> includedServices) {
            this.includedServices = includedServices;
        }


        public List<SupportQuestion> getSupportQuestions() {
            return supportQuestions;
        }

        public void setSupportQuestions(List<SupportQuestion> supportQuestions) {
            this.supportQuestions = supportQuestions;
        }

        public OrderInfo getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfo orderInfo) {
            this.orderInfo = orderInfo;
        }

        public Integer getServiceAmount() {
            return serviceAmount;
        }

        public void setServiceAmount(Integer serviceAmount) {
            this.serviceAmount = serviceAmount;
        }

        public Integer getAddonAmount() {
            return addonAmount;
        }

        public void setAddonAmount(Integer addonAmount) {
            this.addonAmount = addonAmount;
        }

        public Integer getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(Integer subTotal) {
            this.subTotal = subTotal;
        }

        public Integer getServiceDiscount() {
            return serviceDiscount;
        }

        public void setServiceDiscount(Integer serviceDiscount) {
            this.serviceDiscount = serviceDiscount;
        }

        public Integer getMembershipDiscount() {
            return membershipDiscount;
        }

        public void setMembershipDiscount(Integer membershipDiscount) {
            this.membershipDiscount = membershipDiscount;
        }

        public Integer getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(Integer totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public Integer getMembershipFees() {
            return membershipFees;
        }

        public void setMembershipFees(Integer membershipFees) {
            this.membershipFees = membershipFees;
        }

        public Integer getHygieneFees() {
            return hygieneFees;
        }

        public void setHygieneFees(Integer hygieneFees) {
            this.hygieneFees = hygieneFees;
        }

        public String getBillingAmount() {
            return billingAmount;
        }

        public void setBillingAmount(String billingAmount) {
            this.billingAmount = billingAmount;
        }

        public Integer getVendorBillingAmount() {
            return vendorBillingAmount;
        }

        public void setVendorBillingAmount(Integer vendorBillingAmount) {
            this.vendorBillingAmount = vendorBillingAmount;
        }

        public String getInvoiceUrl() {
            return invoiceUrl;
        }

        public void setInvoiceUrl(String invoiceUrl) {
            this.invoiceUrl = invoiceUrl;
        }


        public class Package {

            @SerializedName("package_id")
            @Expose
            private Integer packageId;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("quantity")
            @Expose
            private String quantity;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("discount")
            @Expose
            private String discount;
            @SerializedName("discount_amount")
            @Expose
            private String discountAmount;

            public Integer getPackageId() {
                return packageId;
            }

            public void setPackageId(Integer packageId) {
                this.packageId = packageId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(String discountAmount) {
                this.discountAmount = discountAmount;
            }

        }

        public class IncludedService {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("discount")
            @Expose
            private String discount;
            @SerializedName("after_discount")
            @Expose
            private String afterDiscount;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDiscount() {
                return discount;
            }

            public void setDiscount(String discount) {
                this.discount = discount;
            }

            public String getAfterDiscount() {
                return afterDiscount;
            }

            public void setAfterDiscount(String afterDiscount) {
                this.afterDiscount = afterDiscount;
            }

        }

        public class SupportQuestion {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("user_type")
            @Expose
            private String userType;
            @SerializedName("models")
            @Expose
            private String models;
            @SerializedName("queries")
            @Expose
            private String queries;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getModels() {
                return models;
            }

            public void setModels(String models) {
                this.models = models;
            }

            public String getQueries() {
                return queries;
            }

            public void setQueries(String queries) {
                this.queries = queries;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

        public class OrderInfo {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("user_id")
            @Expose
            private Integer userId;
            @SerializedName("vendor_id")
            @Expose
            private Integer vendorId;
            @SerializedName("package_id")
            @Expose
            private Integer packageId;
            @SerializedName("addons")
            @Expose
            private List<Addon> addons = null;
            @SerializedName("pincode")
            @Expose
            private String pincode;
            @SerializedName("order_number")
            @Expose
            private String orderNumber;
            @SerializedName("cart_details")
            @Expose
            private String cartDetails;
            @SerializedName("sub_amount")
            @Expose
            private Integer subAmount;
            @SerializedName("discount_amount")
            @Expose
            private Integer discountAmount;
            @SerializedName("membership_discount")
            @Expose
            private Integer membershipDiscount;
            @SerializedName("total_amount")
            @Expose
            private String totalAmount;
            @SerializedName("vendor_service_amount")
            @Expose
            private Integer vendorServiceAmount;
            @SerializedName("amount_without_tax")
            @Expose
            private Integer amountWithoutTax;
            @SerializedName("tax_name")
            @Expose
            private Object taxName;
            @SerializedName("tax_amount")
            @Expose
            private Object taxAmount;
            @SerializedName("hygiene_fees")
            @Expose
            private Integer hygieneFees;
            @SerializedName("order_status")
            @Expose
            private String orderStatus;
            @SerializedName("vendor_status")
            @Expose
            private String vendorStatus;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("mobile_number")
            @Expose
            private String mobileNumber;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("landmark")
            @Expose
            private String landmark;
            @SerializedName("state_id")
            @Expose
            private Integer stateId;
            @SerializedName("city")
            @Expose
            private String city;
            @SerializedName("cancellation_reasons")
            @Expose
            private Object cancellationReasons;
            @SerializedName("support_question_id")
            @Expose
            private Object supportQuestionId;
            @SerializedName("cancellation_remark")
            @Expose
            private Object cancellationRemark;
            @SerializedName("cancelled_by")
            @Expose
            private Object cancelledBy;

            @SerializedName("payment_method")
            @Expose
            private String paymentMethod;



            @SerializedName("coupon_discount_amount")
            @Expose
            private Integer coupon_discount_amount;

            public Integer getCoupon_discount_amount() {
                return coupon_discount_amount;
            }

            public void setCoupon_discount_amount(Integer coupon_discount_amount) {
                this.coupon_discount_amount = coupon_discount_amount;
            }

            @SerializedName("payment_status")
            @Expose
            private String paymentStatus;
            @SerializedName("assigned_status")
            @Expose
            private String assignedStatus;
            @SerializedName("remark")
            @Expose
            private Object remark;
            @SerializedName("membership_id")
            @Expose
            private Object membershipId;
            @SerializedName("membership_cost")
            @Expose
            private Object membershipCost;
            @SerializedName("cgst")
            @Expose
            private Object cgst;
            @SerializedName("sgst")
            @Expose
            private Object sgst;
            @SerializedName("igst")
            @Expose
            private String igst;
            @SerializedName("device_type")
            @Expose
            private String deviceType;
            @SerializedName("device_info")
            @Expose
            private String deviceInfo;
            @SerializedName("system_ip")
            @Expose
            private String systemIp;
            @SerializedName("serve_date")
            @Expose
            private String serveDate;
            @SerializedName("serve_time")
            @Expose
            private String serveTime;
            @SerializedName("accept_by_vendor")
            @Expose
            private String acceptByVendor;
            @SerializedName("reject_reason_by_vendor")
            @Expose
            private Object rejectReasonByVendor;
            @SerializedName("service_start")
            @Expose
            private String serviceStart;
            @SerializedName("rejection_accept")
            @Expose
            private String rejectionAccept;
            @SerializedName("tax_percent")
            @Expose
            private Integer taxPercent;
            @SerializedName("v_buy_lead_amount")
            @Expose
            private Float vBuyLeadAmount;
            @SerializedName("buy_lead_percent")
            @Expose
            private Integer buyLeadPercent;
            @SerializedName("order_device_type")
            @Expose
            private String orderDeviceType;
            @SerializedName("vendor_attachment")
            @Expose
            private Object vendorAttachment;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;


            @SerializedName("buy_lead_amount")
            @Expose
            private String buyLeadAmount;
            @SerializedName("action_check")
            @Expose
            private String actionCheck;
            @SerializedName("buy_action_check")
            @Expose
            private String buyActionCheck;

            @SerializedName("get_state")
            @Expose
            private GetState getState;
            @SerializedName("get_city")
            @Expose
            private GetCity getCity;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getVendorId() {
                return vendorId;
            }

            public void setVendorId(Integer vendorId) {
                this.vendorId = vendorId;
            }

            public Integer getPackageId() {
                return packageId;
            }

            public void setPackageId(Integer packageId) {
                this.packageId = packageId;
            }

            public List<Addon> getAddons() {
                return addons;
            }

            public void setAddons(List<Addon> addons) {
                this.addons = addons;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getCartDetails() {
                return cartDetails;
            }

            public void setCartDetails(String cartDetails) {
                this.cartDetails = cartDetails;
            }

            public Integer getSubAmount() {
                return subAmount;
            }

            public void setSubAmount(Integer subAmount) {
                this.subAmount = subAmount;
            }

            public Integer getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(Integer discountAmount) {
                this.discountAmount = discountAmount;
            }

            public Integer getMembershipDiscount() {
                return membershipDiscount;
            }

            public void setMembershipDiscount(Integer membershipDiscount) {
                this.membershipDiscount = membershipDiscount;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public Integer getVendorServiceAmount() {
                return vendorServiceAmount;
            }

            public void setVendorServiceAmount(Integer vendorServiceAmount) {
                this.vendorServiceAmount = vendorServiceAmount;
            }

            public Integer getAmountWithoutTax() {
                return amountWithoutTax;
            }

            public void setAmountWithoutTax(Integer amountWithoutTax) {
                this.amountWithoutTax = amountWithoutTax;
            }

            public Object getTaxName() {
                return taxName;
            }

            public void setTaxName(Object taxName) {
                this.taxName = taxName;
            }

            public Object getTaxAmount() {
                return taxAmount;
            }

            public void setTaxAmount(Object taxAmount) {
                this.taxAmount = taxAmount;
            }

            public Integer getHygieneFees() {
                return hygieneFees;
            }

            public void setHygieneFees(Integer hygieneFees) {
                this.hygieneFees = hygieneFees;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getVendorStatus() {
                return vendorStatus;
            }

            public void setVendorStatus(String vendorStatus) {
                this.vendorStatus = vendorStatus;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLandmark() {
                return landmark;
            }

            public void setLandmark(String landmark) {
                this.landmark = landmark;
            }

            public Integer getStateId() {
                return stateId;
            }

            public void setStateId(Integer stateId) {
                this.stateId = stateId;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public Object getCancellationReasons() {
                return cancellationReasons;
            }

            public void setCancellationReasons(Object cancellationReasons) {
                this.cancellationReasons = cancellationReasons;
            }

            public Object getSupportQuestionId() {
                return supportQuestionId;
            }

            public void setSupportQuestionId(Object supportQuestionId) {
                this.supportQuestionId = supportQuestionId;
            }

            public Object getCancellationRemark() {
                return cancellationRemark;
            }

            public void setCancellationRemark(Object cancellationRemark) {
                this.cancellationRemark = cancellationRemark;
            }

            public Object getCancelledBy() {
                return cancelledBy;
            }

            public void setCancelledBy(Object cancelledBy) {
                this.cancelledBy = cancelledBy;
            }

            public String getPaymentMethod() {
                return paymentMethod;
            }

            public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
            }

            public String getPaymentStatus() {
                return paymentStatus;
            }

            public void setPaymentStatus(String paymentStatus) {
                this.paymentStatus = paymentStatus;
            }

            public String getAssignedStatus() {
                return assignedStatus;
            }

            public void setAssignedStatus(String assignedStatus) {
                this.assignedStatus = assignedStatus;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getMembershipId() {
                return membershipId;
            }

            public void setMembershipId(Object membershipId) {
                this.membershipId = membershipId;
            }

            public Object getMembershipCost() {
                return membershipCost;
            }

            public void setMembershipCost(Object membershipCost) {
                this.membershipCost = membershipCost;
            }

            public Object getCgst() {
                return cgst;
            }

            public void setCgst(Object cgst) {
                this.cgst = cgst;
            }

            public Object getSgst() {
                return sgst;
            }

            public void setSgst(Object sgst) {
                this.sgst = sgst;
            }

            public String getIgst() {
                return igst;
            }

            public void setIgst(String igst) {
                this.igst = igst;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getDeviceInfo() {
                return deviceInfo;
            }

            public void setDeviceInfo(String deviceInfo) {
                this.deviceInfo = deviceInfo;
            }

            public String getSystemIp() {
                return systemIp;
            }

            public void setSystemIp(String systemIp) {
                this.systemIp = systemIp;
            }

            public String getServeDate() {
                return serveDate;
            }

            public void setServeDate(String serveDate) {
                this.serveDate = serveDate;
            }

            public String getServeTime() {
                return serveTime;
            }

            public void setServeTime(String serveTime) {
                this.serveTime = serveTime;
            }

            public String getAcceptByVendor() {
                return acceptByVendor;
            }

            public void setAcceptByVendor(String acceptByVendor) {
                this.acceptByVendor = acceptByVendor;
            }

            public Object getRejectReasonByVendor() {
                return rejectReasonByVendor;
            }

            public void setRejectReasonByVendor(Object rejectReasonByVendor) {
                this.rejectReasonByVendor = rejectReasonByVendor;
            }

            public String getServiceStart() {
                return serviceStart;
            }

            public void setServiceStart(String serviceStart) {
                this.serviceStart = serviceStart;
            }

            public String getRejectionAccept() {
                return rejectionAccept;
            }

            public void setRejectionAccept(String rejectionAccept) {
                this.rejectionAccept = rejectionAccept;
            }

            public Integer getTaxPercent() {
                return taxPercent;
            }

            public void setTaxPercent(Integer taxPercent) {
                this.taxPercent = taxPercent;
            }

            public Float getvBuyLeadAmount() {
                return vBuyLeadAmount;
            }

            public void setvBuyLeadAmount(Float vBuyLeadAmount) {
                this.vBuyLeadAmount = vBuyLeadAmount;
            }

            public Integer getBuyLeadPercent() {
                return buyLeadPercent;
            }

            public void setBuyLeadPercent(Integer buyLeadPercent) {
                this.buyLeadPercent = buyLeadPercent;
            }

            public String getOrderDeviceType() {
                return orderDeviceType;
            }

            public void setOrderDeviceType(String orderDeviceType) {
                this.orderDeviceType = orderDeviceType;
            }

            public Object getVendorAttachment() {
                return vendorAttachment;
            }

            public void setVendorAttachment(Object vendorAttachment) {
                this.vendorAttachment = vendorAttachment;
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



            public String getBuyLeadAmount() {
                return buyLeadAmount;
            }

            public void setBuyLeadAmount(String buyLeadAmount) {
                this.buyLeadAmount = buyLeadAmount;
            }

            public String getActionCheck() {
                return actionCheck;
            }

            public void setActionCheck(String actionCheck) {
                this.actionCheck = actionCheck;
            }

            public String getBuyActionCheck() {
                return buyActionCheck;
            }

            public void setBuyActionCheck(String buyActionCheck) {
                this.buyActionCheck = buyActionCheck;
            }


            public GetState getGetState() {
                return getState;
            }

            public void setGetState(GetState getState) {
                this.getState = getState;
            }

            public GetCity getGetCity() {
                return getCity;
            }

            public void setGetCity(GetCity getCity) {
                this.getCity = getCity;
            }




            public class GetCity {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("state_id")
                @Expose
                private Integer stateId;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("created_at")
                @Expose
                private Object createdAt;
                @SerializedName("updated_at")
                @Expose
                private Object updatedAt;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public Integer getStateId() {
                    return stateId;
                }

                public void setStateId(Integer stateId) {
                    this.stateId = stateId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(Object createdAt) {
                    this.createdAt = createdAt;
                }

                public Object getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(Object updatedAt) {
                    this.updatedAt = updatedAt;
                }

            }
            public class GetState {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("country_id")
                @Expose
                private Integer countryId;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("created_at")
                @Expose
                private Object createdAt;
                @SerializedName("updated_at")
                @Expose
                private Object updatedAt;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public Integer getCountryId() {
                    return countryId;
                }

                public void setCountryId(Integer countryId) {
                    this.countryId = countryId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(Object createdAt) {
                    this.createdAt = createdAt;
                }

                public Object getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(Object updatedAt) {
                    this.updatedAt = updatedAt;
                }

            }
            public class Addon {

                @SerializedName("id")
                @Expose
                private Integer id;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("amount")
                @Expose
                private String amount;
                @SerializedName("discount")
                @Expose
                private String discount;
                @SerializedName("after_discount")
                @Expose
                private String afterDiscount;
                @SerializedName("quantity")
                @Expose
                private String quantity;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public String getAfterDiscount() {
                    return afterDiscount;
                }

                public void setAfterDiscount(String afterDiscount) {
                    this.afterDiscount = afterDiscount;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

            }

            public class Package {

                @SerializedName("package_id")
                @Expose
                private Integer packageId;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("quantity")
                @Expose
                private String quantity;
                @SerializedName("amount")
                @Expose
                private String amount;
                @SerializedName("discount")
                @Expose
                private String discount;
                @SerializedName("discount_amount")
                @Expose
                private String discountAmount;

                public Integer getPackageId() {
                    return packageId;
                }

                public void setPackageId(Integer packageId) {
                    this.packageId = packageId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getQuantity() {
                    return quantity;
                }

                public void setQuantity(String quantity) {
                    this.quantity = quantity;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public String getDiscountAmount() {
                    return discountAmount;
                }

                public void setDiscountAmount(String discountAmount) {
                    this.discountAmount = discountAmount;
                }

            }

        }

    }



}