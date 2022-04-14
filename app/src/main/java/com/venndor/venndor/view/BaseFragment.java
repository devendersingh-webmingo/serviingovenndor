package com.venndor.venndor.view;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * File Name: BaseFragment.java
 * Description: This file contains classes and functions for the Products Management.
 *
 * @author Shailaja
 * Date Created: 20/07/2021
 * Date Released:
 * Created by Shailaja Tripathi
 */
public class BaseFragment extends Fragment {
    protected Activity mActivity;
    // public static ArrayList<HashMap<String, String>> basearraylist = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
    }
}
