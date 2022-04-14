package com.venndor.venndor.ui.Presenter.Repo.BookingHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingActiveHistoryRepo {


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

        @SerializedName("order")
        @Expose
        private List<Order> order = null;

        public List<Order> getOrder() {
            return order;
        }

        public void setOrder(List<Order> order) {
            this.order = order;
        }




        public class Order {

            @SerializedName("id")
            @Expose
            private Integer id;
            @SerializedName("user_id")
            @Expose
            private Integer userId;
            @SerializedName("vendor_id")
            @Expose
            private Integer vendorId;
            @SerializedName("cart_details")
            @Expose
            private String cartDetails;
            @SerializedName("order_number")
            @Expose
            private String orderNumber;
            @SerializedName("total_amount")
            @Expose
            private String totalAmount;
            @SerializedName("order_status")
            @Expose
            private String orderStatus;
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
            @SerializedName("pincode")
            @Expose
            private String pincode;
            @SerializedName("payment_method")
            @Expose
            private String paymentMethod;
            @SerializedName("serve_date")
            @Expose
            private String serveDate;
            @SerializedName("serve_time")
            @Expose
            private String serveTime;





            @SerializedName("new_lead_amount")
            @Expose
            private String new_lead_amount;

            public String getNew_lead_amount() {
                return new_lead_amount;
            }

            public void setNew_lead_amount(String new_lead_amount) {
                this.new_lead_amount = new_lead_amount;
            }

            @SerializedName("assigned_status")
            @Expose
            private String assigned_status;

            public String getAssigned_status() {
                return assigned_status;
            }

            public void setAssigned_status(String assigned_status) {
                this.assigned_status = assigned_status;
            }

            @SerializedName("accept_by_vendor")
            @Expose
            private String acceptByVendor;
            @SerializedName("package_information")
            @Expose
            private PackageInformation packageInformation;
            @SerializedName("buy_lead_amount")
            @Expose
            private String buyLeadAmount;
            @SerializedName("action_check")
            @Expose
            private String actionCheck;



            @SerializedName("payment_status")
            @Expose
            private String payment_status;



            @SerializedName("serving_datetime")
            @Expose
            private String serving_datetime;

            public String getServing_datetime() {
                return serving_datetime;
            }

            public void setServing_datetime(String serving_datetime) {
                this.serving_datetime = serving_datetime;
            }

            @SerializedName("package")
            @Expose
            private Object _package;
            @SerializedName("get_state")
            @Expose
            private GetState getState;
            @SerializedName("get_city")
            @Expose
            private GetCity getCity;

            public String getPayment_status() {
                return payment_status;
            }

            public void setPayment_status(String payment_status) {
                this.payment_status = payment_status;
            }

            public Object get_package() {
                return _package;
            }

            public void set_package(Object _package) {
                this._package = _package;
            }

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

            public String getCartDetails() {
                return cartDetails;
            }

            public void setCartDetails(String cartDetails) {
                this.cartDetails = cartDetails;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(String totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
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

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

            public String getPaymentMethod() {
                return paymentMethod;
            }

            public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
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

            public PackageInformation getPackageInformation() {
                return packageInformation;
            }

            public void setPackageInformation(PackageInformation packageInformation) {
                this.packageInformation = packageInformation;
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

            public Object getPackage() {
                return _package;
            }

            public void setPackage(Object _package) {
                this._package = _package;
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



            public class PackageInformation {

                @SerializedName("package")
                @Expose
                private Package _package;

                public Package getPackage() {
                    return _package;
                }

                public void setPackage(Package _package) {
                    this._package = _package;
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

        }

    }



}