package com.venndor.venndor.ui.Adapter.Bookinghistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Presenter.Repo.BookingHistory.BookingHistoryDetails;


public class BookingDeatilsAddonseAdapter extends RecyclerView.Adapter<BookingDeatilsAddonseAdapter.myViewHolder> {

    BookingHistoryDetails modelArrayList;
    Context context;


    public BookingDeatilsAddonseAdapter(BookingHistoryDetails modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_addonsdetails, parent, false);

        return new BookingDeatilsAddonseAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        holder.tvSecurityAmount.setText(" \u20B9 " + modelArrayList.getData().getOrderInfo().getAddons().get(position).getAfterDiscount());
       // holder.tvLead.setText(" \u20B9 " + modelArrayList.getData().getOrderInfo().getAddons().get(position).());


        holder.tvSecurityFee.setText(modelArrayList.getData().getOrderInfo().getAddons().get(position).getName());
        holder.tvSecurityquantity.setText("("+modelArrayList.getData().getOrderInfo().getAddons().get(position).getQuantity()+")");


    }

    @Override
    public int getItemCount() {
//        Toast.makeText(context, modelArrayList.getData().getAddonServices().size()+"", Toast.LENGTH_SHORT).show();
        return modelArrayList.getData().getOrderInfo().getAddons().size();


    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvSecurityFee, tvSecurityquantity, tvSecurityAmount;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSecurityFee = itemView.findViewById(R.id.tvSecurityFee);
            tvSecurityquantity = itemView.findViewById(R.id.tvSecurityquantity);
            tvSecurityAmount = itemView.findViewById(R.id.tvSecurityAmount);

        }
    }


}
