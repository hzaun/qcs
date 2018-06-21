package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.utils.BaseUtils;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIBase;
import com.nuzharukiya.spapp.utils.UIComponents;

public class SplashActivity extends SPApp {

    // Splash time in seconds
    private static final int SPLASH_TIME = 3;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isUserLoggedIn()) {
            // Registered user logged in
            startNextActivity(StartingPoint.class);
        } else {
            startNextActivity(LoginActivity.class);
        }

        // Activity should not be available on back press
        SplashActivity.this.finish();
    }

    @Override
    public void initApp() {
        super.initApp();

        context = SplashActivity.this;
        SPAppPreferences.init(context);

//        uiComponents = new UIComponents(context, false);
//        uiComponents.makeStatusBarTransparent();
//        uiComponents.hideNavigationBarImmersive();
    }

    //
//    @Override
//    public void runFactory() {
////        new IntentLauncher().start();
//    }
//
//    private class IntentLauncher extends Thread {
//
//        /**
//         * Sleep for some time and then start new activity.
//         */
//        @Override
//        public void run() {
//            try {
//                // Sleeping
//                Thread.sleep(SPLASH_TIME * 1000);
//            } catch (Exception e) {
//                Log.e("Exception", e.getMessage());
//            }
//
//            if (isUserLoggedIn()) {
//                // Registered user logged in
//                startNextActivity(StartingPoint.class);
//            } else {
//                startNextActivity(LoginActivity.class);
//            }
//
//            // Activity should not be available on back press
//            SplashActivity.this.finish();
//        }
//    }
//
    private boolean isUserLoggedIn() {

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null) {
            // Gmail user logged in
            return true;
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            // Facebook user logged in
            return true;
        }

        if (SPAppPreferences.getUserLoggedIn()) return true;

        return false;
    }
}
