package com.venndor.venndor.ui.Support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;
import com.venndor.venndor.view.BaseFragment;

import static com.venndor.venndor.ui.Container.ivBack;
import static com.venndor.venndor.ui.Container.ivIamge;
import static com.venndor.venndor.ui.Container.tvHeading;

public class Query  extends BaseFragment implements View.OnClickListener{

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.query, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        rootView.findViewById(R.id.markitclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHeading.setText("View Post Complaints");
                ivIamge.setImageResource(R.drawable.user);
                ivBack.setImageResource(R.drawable.ic_icon_ionic_ios_arrow_round_back);
                ((Container) mActivity).displayView(18);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
