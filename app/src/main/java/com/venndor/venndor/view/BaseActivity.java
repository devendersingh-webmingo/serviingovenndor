package com.venndor.venndor.view;


import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    protected static Activity mActivity;
    //  public static String developer ="Shailaja";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //  AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        this.mActivity = this;
       /* OSettings oSettings = new OSettings(this.mActivity);
        SimpleHTTPConnection simpleHTTPConnection = new SimpleHTTPConnection(this.mActivity);*/
    }
}
