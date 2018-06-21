package com.nuzharukiya.spapp.helpers;

import android.content.Context;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.nuzharukiya.spapp.utils.SPAppPreferences;

/**
 * Created by Nuzha Rukiya on 18/06/11.
 */
public class LoginHelper {

    private Context context;

    public LoginHelper(Context context) {
        this.context = context;
    }

    public CallbackManager createFacebookCallbackManager() {
        return CallbackManager.Factory.create();
    }

    public GoogleSignInClient retrieveGoogleSignInClient() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(context, gso);
    }

    public void storeUserDetails(String sAccessToken, String sUserId, String sUserEmail,
                                 String sDisplayName, String sFirstName, String sLastName,
                                 Uri uriImage) {
        SPAppPreferences.setAccessToken(sAccessToken);
        SPAppPreferences.setUserId(sUserId);
        SPAppPreferences.setUserEmail(sUserEmail);
        SPAppPreferences.setDisplayName(sDisplayName);
        SPAppPreferences.setFirstName(sFirstName);
        SPAppPreferences.setLastName(sLastName);
        SPAppPreferences.setImageUrl(uriImage);
    }
}
