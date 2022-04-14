package com.venndor.venndor.ui.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.venndor.venndor.R;
import com.venndor.venndor.view.BaseFragment;

public class DeactivateProfile extends BaseFragment implements View.OnClickListener{

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_deactivate, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {

    }

    @Override
    public void onClick(View v) {

    }
}
