package com.venndor.venndor.ui.Presenter.Request;


import android.content.Context;

import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;
import com.venndor.venndor.ui.Rtrofit.ApiManager;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousBookingHistoryPresenter {

    private BookingHistoryView view;

    public PreviousBookingHistoryPresenter(BookingHistoryView view) {
        this.view = view;
    }


    public void YestardayHistory(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).Yestarday();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onYestardaySuccess(response.body(), response.message());
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
    public void ForLast7daysHistory(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForLast7days();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForLast7daysHistorySuccess(response.body(), response.message());
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

    public void ForLast15daysHistory(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForLast15days();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForLast15daysHistorySuccess(response.body(), response.message());
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




    public void ForLast30daysHistory(Context context) {
        Call<BookingActiveHistoryRepo> loginCall = ApiManager.getApi(context).ForLast30days();
        view.showHideProgress(true);
        loginCall.enqueue(new Callback<BookingActiveHistoryRepo>() {
            @Override
            public void onResponse(Call<BookingActiveHistoryRepo> call, Response<BookingActiveHistoryRepo> response) {
                view.showHideProgress(false);

                if (response.isSuccessful() && response.body() != null && response.code() == 200) {
                    try {
                        view.onForLast30daysHistorySuccess(response.body(), response.message());
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


        void onYestardaySuccess(BookingActiveHistoryRepo response, String message);
        void onForLast7daysHistorySuccess(BookingActiveHistoryRepo response, String message);

        void onForLast15daysHistorySuccess(BookingActiveHistoryRepo response, String message);
        void onForLast30daysHistorySuccess(BookingActiveHistoryRepo response, String message);

        void onBookingHistoryDetailsSuccess( BookingHistoryDetails response, String message);

        void showHideProgress(boolean isShow);

        void onBookingHistoryFailure(Throwable t);

    }
}
