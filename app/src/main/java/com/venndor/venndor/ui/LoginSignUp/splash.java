package com.venndor.venndor.ui.LoginSignUp;


import android.content.Intent;
import android.os.Bundle;




import com.venndor.venndor.R;
import com.venndor.venndor.ui.Container;

import com.venndor.venndor.ui.loginWithMobile;
import com.venndor.venndor.ui.personalInfo;
import com.venndor.venndor.view.BaseActivity;
import com.venndor.venndor.view.Preferences;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class splash extends BaseActivity {

    Timer timer = new Timer();
    Preferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        init();
    }
    private void init() {
        pref     =new Preferences(mActivity);
     /*   Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
*/


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if ( pref.get("counter").equals("true")) {
                    moveToDashBoard();
                }else{
                    moveToLogin();
                }
            }
            private void moveToLogin() {
                startActivity(new Intent(mActivity, loginWithMobile.class));
            }

            private void moveToDashBoard() {

                startActivity(new Intent(splash.this, Container.class));
            }
        }, 3000);

    }
}
