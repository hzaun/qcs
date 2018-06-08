package com.nuzharukiya.spapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Nuzha Rukiya on 18/06/07.
 */
public class SPAppPreferences {

    public static final String EMPTY_STRING_DEFAULT_VALUE = "null";
    public static final boolean BOOLEAN_DEFAULT_VALUE = false;
    private static final String PREFERENCE_NAME = "SP_APP_PREFS";

    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";

    private static SharedPreferences _sharedPreferences;

    /**
     * Get the shared preferences object for SP APP.
     */
    public static void init(Context context) {
        if (_sharedPreferences == null) {
            _sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    // User's credentials
    public static String getUserEmail() {
        return _sharedPreferences.getString(USER_EMAIL, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setUserEmail(String userEmail) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(USER_EMAIL, userEmail);
        _editor.commit();
        _editor = null;
    }

    // Is the user logged in

    public static boolean getUserLoggedIn() {
        return _sharedPreferences.getBoolean(USER_LOGGED_IN, BOOLEAN_DEFAULT_VALUE);
    }

    public static void setUserLoggedIn(boolean bUserLoggedIn) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putBoolean(USER_LOGGED_IN, bUserLoggedIn);
        _editor.commit();
        _editor = null;
    }
}
