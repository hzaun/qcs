package com.nuzharukiya.spapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

/**
 * Created by Nuzha Rukiya on 18/06/07.
 */
public class SPAppPreferences {

    public static final String EMPTY_STRING_DEFAULT_VALUE = "null";
    public static final boolean BOOLEAN_DEFAULT_VALUE = false;
    private static final String PREFERENCE_NAME = "SP_APP_PREFS";

    private static final String USER_ID = "USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_LOGGED_IN = "USER_LOGGED_IN";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private static final String DISPLAY_NAME = "DISPLAY_NAME";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String IMAGE_URL = "IMAGE_URL";

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

    // User id
    public static String getUserId() {
        return _sharedPreferences.getString(USER_ID, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setUserId(String userId) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(USER_ID, userId);
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

    // Access token
    public static String getAccessToken() {
        return _sharedPreferences.getString(ACCESS_TOKEN, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(ACCESS_TOKEN, accessToken);
        _editor.commit();
        _editor = null;
    }

    /**
     * User details
     *
     * @return
     */

    // Full name
    public static String getDisplayName() {
        return _sharedPreferences.getString(DISPLAY_NAME, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setDisplayName(String displayName) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(DISPLAY_NAME, displayName);
        _editor.commit();
        _editor = null;
    }

    // First name
    public static String getFirstName() {
        return _sharedPreferences.getString(FIRST_NAME, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setFirstName(String firstName) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(FIRST_NAME, firstName);
        _editor.commit();
        _editor = null;
    }

    // Last name
    public static String getLastName() {
        return _sharedPreferences.getString(LAST_NAME, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setLastName(String lastName) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(LAST_NAME, lastName);
        _editor.commit();
        _editor = null;
    }

    // Last name
    public static String getImageUrl() {
        return _sharedPreferences.getString(IMAGE_URL, EMPTY_STRING_DEFAULT_VALUE);
    }

    public static void setImageUrl(Uri imageUri) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(IMAGE_URL, imageUri != null? imageUri.toString() : "");
        _editor.commit();
        _editor = null;
    }
}
