package com.venndor.venndor.ui.Adapter.Bookinghistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingActiveHistoryRepo;
import com.venndor.venndor.ui.Presenter.Repo.VendorMyloan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActiveBookingHistoryAdapter extends RecyclerView.Adapter<ActiveBookingHistoryAdapter.myViewHolder> {

    BookingActiveHistoryRepo modelArrayList;
    Context context;
    private Myloan itemClickListenerr;
    String key = "no";
    SimpleDateFormat code12Hours = new SimpleDateFormat("hh:mm");


    String keyy = " ";

    public ActiveBookingHistoryAdapter(BookingActiveHistoryRepo modelArrayList, Context context, Myloan itemClickListenerr) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.itemClickListenerr = itemClickListenerr;
    }


    public ActiveBookingHistoryAdapter(String key, BookingActiveHistoryRepo modelArrayList, Context context, Myloan itemClickListenerr) {

        this.key = key;

        this.modelArrayList = modelArrayList;
        this.context = context;
        this.itemClickListenerr = itemClickListenerr;
    }

    public ActiveBookingHistoryAdapter(String key, String keyy, BookingActiveHistoryRepo modelArrayList, Context context, Myloan itemClickListenerr) {

        this.key = key;
        this.keyy = keyy;

        this.modelArrayList = modelArrayList;
        this.context = context;
        this.itemClickListenerr = itemClickListenerr;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_ui, parent, false);

        return new ActiveBookingHistoryAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        holder.tvLead.setText(" \u20B9 "+modelArrayList.getData().getOrder().get(position).getNew_lead_amount());





        if (keyy.equalsIgnoreCase("ActiveBooking")) {


            if(modelArrayList.getData().getOrder().get(position).getAssigned_status().equalsIgnoreCase("admin")){
                if(modelArrayList.getData().getOrder().get(position).getAcceptByVendor().equalsIgnoreCase("yes") ){




                   // Toast.makeText(context, keyy + "", Toast.LENGTH_SHORT).show();
                    if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Confirmed")) {
                        // holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.button));
                        holder.status.setTextColor(context.getResources().getColor(R.color.textColor));

                    } else if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Cancelled")) {

                        //  holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.red_button));
                        holder.status.setTextColor(context.getResources().getColor(R.color.red));

                    }
                    if (key.equalsIgnoreCase("Pending-Approval")) {

                        holder.tvAccountName.setText("XXXXXXXX");


                        if (modelArrayList.getData().getOrder().get(position).getActionCheck().equalsIgnoreCase("accept-order")) {
                            holder.OrderLL.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.tvAccountName.setText(modelArrayList.getData().getOrder().get(position).getOrderNumber());
                    }

                    holder.tvAccount.setText(modelArrayList.getData().getOrder().get(position).getName());

                    holder.servicenametv.setText(modelArrayList.getData().getOrder().get(position).getPackageInformation().getPackage().getName());

                    holder.tvAccNo1.setText(" \u20B9 " + modelArrayList.getData().getOrder().get(position).getTotalAmount());
                    holder.tvAccNo.setText(modelArrayList.getData().getOrder().get(position).getAddress());
                    holder.status.setText(modelArrayList.getData().getOrder().get(position).getOrderStatus());
                    holder.paymentstatusTV.setText(modelArrayList.getData().getOrder().get(position).getPayment_status());


                    holder.dateandtime.setText(setDateTimeValues(modelArrayList.getData().getOrder().get(position).getServing_datetime()));



                    holder.imagenext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListenerr.ViewBookingHistory(position, modelArrayList);


                        }
                    });

                    holder.CompleteBooking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListenerr.ViewCompleteBooking(position, modelArrayList);


                        }
                    });


                    holder.AcceptOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListenerr.ViewAcceptOrder(position, modelArrayList);


                        }
                    });
                    holder.RejectOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListenerr.ViewRejectOrder(position, modelArrayList);


                        }
                    });


                    holder.startservicce.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            itemClickListenerr.Viewstartservicce(position, modelArrayList);

                        }
                    });



                }else {

                    holder.LLorderlayout.setVisibility(View.GONE);
                }

            }
            if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Confirmed")) {
                // holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.button));
                holder.status.setTextColor(context.getResources().getColor(R.color.textColor));


            } else if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Cancelled")) {

                //  holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.red_button));
                holder.status.setTextColor(context.getResources().getColor(R.color.red));

            }

            if (key.equalsIgnoreCase("Pending-Approval")) {

                holder.tvAccountName.setText("XXXXXXXX");

                if (modelArrayList.getData().getOrder().get(position).getActionCheck().equalsIgnoreCase("accept-order")) {
                    holder.OrderLL.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvAccountName.setText(modelArrayList.getData().getOrder().get(position).getOrderNumber());

            }


            holder.tvAccount.setText(modelArrayList.getData().getOrder().get(position).getName());
            holder.servicenametv.setText(modelArrayList.getData().getOrder().get(position).getPackageInformation().getPackage().getName());
            holder.tvAccNo1.setText(" \u20B9 " + modelArrayList.getData().getOrder().get(position).getTotalAmount());
            holder.tvAccNo.setText(modelArrayList.getData().getOrder().get(position).getAddress());
            holder.status.setText(modelArrayList.getData().getOrder().get(position).getOrderStatus());
            holder.paymentstatusTV.setText(modelArrayList.getData().getOrder().get(position).getPayment_status());
            holder.dateandtime.setText(setDateTimeValues(modelArrayList.getData().getOrder().get(position).getServing_datetime()));
            holder.imagenext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewBookingHistory(position, modelArrayList);


                }
            });

            holder.CompleteBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewCompleteBooking(position, modelArrayList);


                }
            });


            holder.AcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewAcceptOrder(position, modelArrayList);


                }
            });
            holder.RejectOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewRejectOrder(position, modelArrayList);


                }
            });


            holder.startservicce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemClickListenerr.Viewstartservicce(position, modelArrayList);

                }
            });

            // if (modelArrayList.getData().getOrder().get(position).())

        } else {


            if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Confirmed")) {
                // holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.button));
                holder.status.setTextColor(context.getResources().getColor(R.color.textColor));


            } else if (modelArrayList.getData().getOrder().get(position).getOrderStatus().equalsIgnoreCase("Cancelled")) {

                //  holder.llstatus.setBackground(context.getResources().getDrawable(R.drawable.red_button));
                holder.status.setTextColor(context.getResources().getColor(R.color.red));

            }

            if (key.equalsIgnoreCase("Pending-Approval")) {

                holder.tvAccountName.setText("XXXXXXXX");


                if (modelArrayList.getData().getOrder().get(position).getActionCheck().equalsIgnoreCase("accept-order")) {
                    holder.OrderLL.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvAccountName.setText(modelArrayList.getData().getOrder().get(position).getOrderNumber());

            }

            holder.imagenext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewBookingHistory(position, modelArrayList);


                }
            });

            holder.CompleteBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewCompleteBooking(position, modelArrayList);


                }
            });


            holder.AcceptOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewAcceptOrder(position, modelArrayList);


                }
            });
            holder.RejectOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListenerr.ViewRejectOrder(position, modelArrayList);


                }
            });


            holder.startservicce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemClickListenerr.Viewstartservicce(position, modelArrayList);

                }
            });



        /*else if (key.equalsIgnoreCase("ForToday")) {
            if (modelArrayList.getData().getOrder().get(position).getActionCheck().equalsIgnoreCase("start-service")) {
                holder.startservicce.setVisibility(View.VISIBLE);
            }else if (modelArrayList.getData().getOrder().get(position).getActionCheck().equalsIgnoreCase("complete-order")) {

                holder.CompleteBooking.setVisibility(View.VISIBLE);

            }
        }*/


            holder.tvAccount.setText(modelArrayList.getData().getOrder().get(position).getName());

            holder.servicenametv.setText(modelArrayList.getData().getOrder().get(position).getPackageInformation().getPackage().getName());

            holder.tvAccNo1.setText(" \u20B9 " + modelArrayList.getData().getOrder().get(position).getTotalAmount());
            holder.tvAccNo.setText(modelArrayList.getData().getOrder().get(position).getAddress());
            holder.status.setText(modelArrayList.getData().getOrder().get(position).getOrderStatus());
            holder.paymentstatusTV.setText(modelArrayList.getData().getOrder().get(position).getPayment_status());


            holder.dateandtime.setText(setDateTimeValues(modelArrayList.getData().getOrder().get(position).getServing_datetime()));


        }


        /*holder.imagenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListenerr.ViewBookingHistory(position, modelArrayList);


            }
        });

        holder.CompleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListenerr.ViewCompleteBooking(position, modelArrayList);


            }
        });


        holder.AcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListenerr.ViewAcceptOrder(position, modelArrayList);


            }
        });
        holder.RejectOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListenerr.ViewRejectOrder(position, modelArrayList);


            }
        });


        holder.startservicce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListenerr.Viewstartservicce(position, modelArrayList);

            }
        });
*/
       /* if (modelArrayList.getData().getAddonServices().size() > 0) {
            cartModel=new CartModel(String.valueOf(modelArrayList.getData().getAddonServices().get(position).getId()),modelArrayList.getData().getAddonServices().get(position).getQuantity());
            arrayList.add(cartModel);
            itemClickListenerr.Adonsedata(arrayList,position);


        }*/
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, modelArrayList.getData().getAddonServices().size()+"", Toast.LENGTH_SHORT).show();
        return modelArrayList.getData().getOrder().size();


    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccount, tvAccount1, tvAccNo, tvAccNo1, tvAccountName, tvName1, status, dateandtime, AcceptOrder, RejectOrder, startservicce, CompleteBooking,
                servicenametv, paymentstatusTV,LeadAccount,tvLead;
        LinearLayout llstatus, OrderLL, LLorderlayout;
        ImageView imagenext;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccount = itemView.findViewById(R.id.tvAccount);
            tvAccount1 = itemView.findViewById(R.id.tvAccount1);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvName1 = itemView.findViewById(R.id.tvName1);
            status = itemView.findViewById(R.id.status);
            dateandtime = itemView.findViewById(R.id.dateandtime);
            imagenext = itemView.findViewById(R.id.imagenext);
            llstatus = itemView.findViewById(R.id.llstatus);
            OrderLL = itemView.findViewById(R.id.OrderLL);
            LLorderlayout = itemView.findViewById(R.id.LLorderlayout);
            AcceptOrder = itemView.findViewById(R.id.AcceptOrder);
            servicenametv = itemView.findViewById(R.id.servicenametv);
            RejectOrder = itemView.findViewById(R.id.RejectOrder);
            paymentstatusTV = itemView.findViewById(R.id.paymentstatusTV);
            startservicce = itemView.findViewById(R.id.startservicce);
            CompleteBooking = itemView.findViewById(R.id.CompleteBooking);
            LeadAccount= itemView.findViewById(R.id.LeadAccount);
            tvLead= itemView.findViewById(R.id.tvLead);
        }
    }


    public interface Myloan {


        void ViewBookingHistory(int position, BookingActiveHistoryRepo VendorMyloan);

        void ViewAcceptOrder(int position, BookingActiveHistoryRepo VendorMyloan);

        void ViewRejectOrder(int position, BookingActiveHistoryRepo VendorMyloan);

        void Viewstartservicce(int position, BookingActiveHistoryRepo VendorMyloan);

        void ViewCompleteBooking(int position, BookingActiveHistoryRepo VendorMyloan);


      /*  void IncreaseQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DecressQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DeleteAddonse(String Quentity, int position, VendorMyloan VendorMyloan);
*/

    }

    private String setDateTimeValues(String date) {
        try {
            String pattern = "dd-MM-yyyy hh:mm a";
            DateFormat df = new SimpleDateFormat(pattern);
            Date myDate = df.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            //   setLog("MyNewDate", date + "  " + formatter.format(myDate));
            return formatter.format(myDate).toUpperCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public String Convert24to12(String time) {


        String convertedTime = "";
        try {

            String pattern = "hh:mm:ss a";

            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");

            //  SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            Date date = displayFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }


}
