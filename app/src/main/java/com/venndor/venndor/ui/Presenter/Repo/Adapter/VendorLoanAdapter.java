package com.venndor.venndor.ui.Presenter.Repo.Adapter;

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

        holder.tvAccNo.setText(modelArrayList.getData().getLoans().get(position).getDateTime());

        holder.tvAccNo1.setText(modelArrayList.getData().getLoans().get(position).getLoanId());
        holder.etDisbursed.setText(modelArrayList.getData().getLoans().get(position).getDeducation());



        holder.tvAccountName1.setText(modelArrayList.getData().getLoans().get(position).getStatus());


        holder.tvAccountName.setText(" \u20B9 "+modelArrayList.getData().getLoans().get(position).getAmount());
        holder.tvIFSCCODE1.setText(" \u20B9 "+modelArrayList.getData().getLoans().get(position).getAmount());

        holder.tvAccHolderName.setText(modelArrayList.getData().getLoans().get(position).getLoan_repay());


       /* if (modelArrayList.getData().getAddonServices().size() > 0) {
            cartModel=new CartModel(String.valueOf(modelArrayList.getData().getAddonServices().get(position).getId()),modelArrayList.getData().getAddonServices().get(position).getQuantity());
            arrayList.add(cartModel);
            itemClickListenerr.Adonsedata(arrayList,position);


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


    public interface Myloan {


        void ViewRepaymentHistory( int position, VendorMyloan VendorMyloan);

      /*  void IncreaseQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DecressQuentity(String Quentity, int position, VendorMyloan VendorMyloan);

        void DeleteAddonse(String Quentity, int position, VendorMyloan VendorMyloan);
*/

    }


}
