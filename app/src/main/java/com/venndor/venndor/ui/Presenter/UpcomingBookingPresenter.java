package com.venndor.venndor.ui.Presenter;


import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingBookingPresenter {

    private BookingHistoryView view;

    public UpcomingBookingPresenter(BookingHistoryView view) {
        this.view = view;
    }

    public void PendingApprovals(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).PendingApprovals();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onPendingApprovalsSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingActiveHistoryRepo> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }
    public void ForToday(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForToday();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForTodaySuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingActiveHistoryRepo> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public void ForTomorrow(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForTomorrow();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForTomorrowSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingActiveHistoryRepo> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public void ForWeek(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForWeek();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForWeekSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingActiveHistoryRepo> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }




    public void ForMonth(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForMonth();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForMonthSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingActiveHistoryRepo> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }


    public void acceptBooking(Context context,String id) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).acceptBooking(id);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onacceptBookingSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }
    public void rejectBooking(Context context,String id) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).rejectBooking(id);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onrejectBookingSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }
    public void Startservice(Context context,String id) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).Startservice(id);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onStartserviceBookingSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }
    public void VerifyStartserviceotp(Context context,String otp) {
        Call<ResponseBody> loginCall = ApiManager.getApi(context).VerifyStartserviceootp(otp);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onStartVerifyStartserviceotpSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }




    public void BookingHistoryDetails(Context context,String id) {
        Call<BookingHistoryDetails> loginCall = ApiManager.getApi(context).BookingHistoryDetails(id);
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingHistoryDetails>() {
            @Override
            public void onResponse(Call<BookingHistoryDetails> call, Response<BookingHistoryDetails> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onBookingHistoryDetailsSuccess(response.body(), response.message());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");

                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }

                } else if (response.code() == 401) {
                    try {
                        String errorStr = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorStr);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                        view.onBookingHistoryError(jsonObject1.getString("error"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        view.onBookingHistoryError(String.valueOf(response.code()));
                    }
                }
            }
            @Override
            public void onFailure(Call<BookingHistoryDetails> call, Throwable t) {
                view.onBookingHistoryFailure(t);
                view.showHideProgress(false);

            }
        });

    }

    public interface BookingHistoryView {

        void onBookingHistoryError(String message);


        void onPendingApprovalsSuccess(BookingActiveHistoryRepo response, String message);
        void onForTodaySuccess(BookingActiveHistoryRepo response, String message);
        void onForTomorrowSuccess(BookingActiveHistoryRepo response, String message);
        void onForWeekSuccess(BookingActiveHistoryRepo response, String message);
        void onForMonthSuccess(BookingActiveHistoryRepo response, String message);
        void onacceptBookingSuccess(ResponseBody response, String message);
        void onrejectBookingSuccess(ResponseBody response, String message);
        void onStartserviceBookingSuccess(ResponseBody response, String message);
        void onStartVerifyStartserviceotpSuccess(ResponseBody response, String message);
        void onBookingHistoryDetailsSuccess(BookingHistoryDetails response, String message);




        void showHideProgress(boolean isShow);

        void onBookingHistoryFailure(Throwable t);

    }
}
