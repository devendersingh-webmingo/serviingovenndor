package com.venndor.venndor.ui.Wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.venndor.venndor.R;
import com.venndor.venndor.view.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MyWalletPendingCreadits  extends BaseFragment implements View.OnClickListener{
    View rootView;
    RecyclerView recyclerview;
    LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.walletpendingcreadits, container, false);
        findViewById();
        return rootView;
    }
    private void findViewById() {
        recyclerview=rootView.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        OfferAdapter OfferAdapter =new OfferAdapter();
        recyclerview.setAdapter(OfferAdapter);
    }

    @Override
    public void onClick(View v) {

    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();

     /*   public OfferAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }*/

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_creadtis, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {


        }

        public int getItemCount() {
            return 2;
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {

        public OfferHolder(View itemView) {
            super(itemView);

        }
    }
}
