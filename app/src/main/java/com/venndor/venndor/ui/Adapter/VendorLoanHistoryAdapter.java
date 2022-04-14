package com.venndor.venndor.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Presenter.Repo.LoanHistoryRepo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VendorLoanHistoryAdapter extends RecyclerView.Adapter<VendorLoanHistoryAdapter.myViewHolder> {

    LoanHistoryRepo modelArrayList;
    Context context;


    public VendorLoanHistoryAdapter(LoanHistoryRepo modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loanhistoryadaptterlayout, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        holder.tvAccNo1.setText(modelArrayList.getData().getHistories().get(position).getDate_time());


        holder.tvName1.setText(modelArrayList.getData().getHistories().get(position).getType());


        holder.tvAccountName1.setText(" \u20B9 " + modelArrayList.getData().getHistories().get(position).getAmount());
        holder.tvIFSC1.setText(" \u20B9 " + modelArrayList.getData().getHistories().get(position).getRemaining());

        holder.tvIFSCCODE1.setText(modelArrayList.getData().getHistories().get(position).getRemark());


    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, modelArrayList.getData().getAddonServices().size()+"", Toast.LENGTH_SHORT).show();
        return modelArrayList.getData().getHistories().size();


    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccNo1, tvName1, tvAccountName1, tvIFSC1, tvIFSCCODE1;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccNo1 = itemView.findViewById(R.id.tvAccNo1);
            tvAccountName1 = itemView.findViewById(R.id.tvAccountName1);
            tvIFSCCODE1 = itemView.findViewById(R.id.tvIFSCCODE1);
            tvName1 = itemView.findViewById(R.id.tvName1);
            tvIFSC1 = itemView.findViewById(R.id.tvIFSC1);

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

}
