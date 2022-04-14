package com.venndor.venndor.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;

import java.util.ArrayList;


public class PreviousBookingHorizontalAdapter extends RecyclerView.Adapter<PreviousBookingHorizontalAdapter.myViewHolder> {

    ArrayList<String> modelArrayList;
    Context context;

    private AddPackageCategoriesSubListener itemClickListenerr;
    String selectedId = "Yestarday";


    public PreviousBookingHorizontalAdapter(ArrayList<String> modelArrayList, Context context, AddPackageCategoriesSubListener itemClickListenerr) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.itemClickListenerr = itemClickListenerr;
    }
    

   /* public package_categoriesAdapter(Subcategory_detailsRepo modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }*/

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_tab, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.data.setText(modelArrayList.get(position));

        holder.data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemClickListenerr.Click(modelArrayList, position);
                selectedId = modelArrayList.get(position);
                notifyDataSetChanged();

            }
        });

        if (selectedId.equalsIgnoreCase(modelArrayList.get(position))) {

            holder.view.setVisibility(View.VISIBLE);
            selectedId = modelArrayList.get(position);

        }else {
            holder.view.setVisibility(View.GONE);

        }


        ///

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView data;
        View view;
        //getLifecycle().addObserver(youTubePlayerView);

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.tvtab);
            view = itemView.findViewById(R.id.view);
            // ( context).addLifeCycleCallBack(youTubePlayerView);

            // context. getLifecycle().addObserver(youTubePlayerView);

        }
    }

    public interface AddPackageCategoriesSubListener {

        void Click(ArrayList<String> modelArrayList, int position);


    }

}
