package com.venndor.venndor.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Presenter.Repo.VendorMyloan;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VendorLoanAdapter extends RecyclerView.Adapter<VendorLoanAdapter.myViewHolder> {

    VendorMyloan modelArrayList;
    Context context;
    private Myloan itemClickListenerr;


    int quantity;


    public VendorLoanAdapter(VendorMyloan modelArrayList, Context context, Myloan itemClickListenerr) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.itemClickListenerr = itemClickListenerr;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_commission, parent, false);

        return new VendorLoanAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.tvAccNo.setText(setDateTimeValues(modelArrayList.getData().getLoans().get(position).getDateTime()));

        holder.tvAccNo1.setText(modelArrayList.getData().getLoans().get(position).getLoanId());
        holder.etDisbursed.setText(modelArrayList.getData().getLoans().get(position).getDeducation());



        holder.tvAccountName1.setText(modelArrayList.getData().getLoans().get(position).getStatus());


        holder.tvAccountName.setText(" \u20B9 "+modelArrayList.getData().getLoans().get(position).getLoan_repay());
        holder.tvIFSCCODE1.setText(" \u20B9 "+modelArrayList.getData().getLoans().get(position).getAmount());
        holder.tvAccHolderName.setText(modelArrayList.getData().getLoans().get(position).getLoanDetails());
        if (modelArrayList.getData().getLoans().get(position).getStatus().equalsIgnoreCase("Success"))
        {
            holder.llSendOTP.setVisibility(View.VISIBLE);
        }


       /* if (modelArrayList.getData().getAddonServices().size() > 0) {
            cartModel=new CartModel(String.valueOf(modelArrayList.getData().getAddonServices().get(position).getId()),modelArrayList.getData().getAddonServices().get(position).getQuantity());
            arrayList.add(cartModel);
            itemClickListenerr.Adonsedata(arrayList,position);
Date Format with MM/dd/yyyy : 07/08/2019

Date Format with dd-M-yyyy hh:mm:ss : 08-7-2019 08:51:58

Date Format with dd MMMM yyyy : 08 July 2019

Date Format with dd MMMM yyyy zzzz : 08 July 2019 Coordinated Universal Time

Date Format with E, dd MMM yyyy HH:mm:ss z : Mon, 08 Jul 2019 08:51:

        }*/






        holder.llSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListenerr.ViewRepaymentHistory( position, modelArrayList);


            }
        });


    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, modelArrayList.getData().getAddonServices().size()+"", Toast.LENGTH_SHORT).show();
        return modelArrayList.getData().getLoans().size();


    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo,tvAccNo1,etDisbursed,tvAccountName1,tvAccountName,tvIFSCCODE1,tvAccHolderName;
        LinearLayout llSendOTP;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccNo = itemView.findViewById(R.id.tvAccNo);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            etDisbursed = itemView.findViewById(R.id.etDisbursed);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            tvAccountName = itemView.findViewById(R.id.tvAccountName);
            tvIFSCCODE1 = itemView.findViewById(R.id.tvIFSCCODE1);
            tvAccHolderName = itemView.findViewById(R.id.tvAccHolderName);
            llSendOTP = itemView.findViewById(R.id.llSendOTP);
        }
    }
    private String setDateTimeValues(String date) {
        try {
            String pattern = "dd MMM yyyy hh:mm a";
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

    public interface Myloan {


        void ViewRepaymentHistory( int position, VendorMyloan VendorMyloan);

      /*  void IncreaseQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DecressQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DeleteAddonse(String Quentity, int position, VendorMyloan VendorMyloan);
*/

    }


}
