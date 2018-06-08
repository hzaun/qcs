package com.nuzharukiya.spapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nuzha Rukiya on 18/01/10.
 */

public class BaseUtils {

    private Context context;
    private ProgressDialog progressDialog;

    public BaseUtils(Context context) {
        this.context = context;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Creates a short toast given a string resource
     *
     * @param message
     */
    public void makeToast(int message) {
        if (message > 0) makeToast(context.getString(message));
    }

    /**
     * Creates a short toast given a string
     *
     * @param message
     */
    public void makeToast(final String message) {
        if (message != null && !message.isEmpty()) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String capitalizeWords(String str) {

        if (str == null || str.isEmpty()) {
            return "";
        }
        StringBuilder b = new StringBuilder(str);
        int i = 0;
        do {
            if (b.length() > i + 1)
                b.replace(i, i + 1, b.substring(i, i + 1).toUpperCase());
            else {
                b.replace(i, i + 1, String.valueOf(Character.toUpperCase(b.charAt(i))));
            }
            i = b.indexOf(" ", i) + 1;
        } while (i > 0 && i < b.length());

        return b.toString();
    }

    /**
     * Requests permissions to be granted to the application
     * Takes care of pre & post marshmallow devies
     *
     * @param fragment
     * @param permissions
     * @param requestCode
     */
    public void requestPerms(Fragment fragment, @NonNull String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fragment.requestPermissions(permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(fragment.getActivity(), permissions, requestCode);
        }
    }

    public void showSnackbar(View coordinatorLayout, int stringRes) {
        ((SPApp) context).getUiComponents().forceHideKeyboard();
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, stringRes, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void getLoader() {
        progressDialog = new ProgressDialog(context, R.style.AppDialogTheme);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.show();
    }

    public void dismissLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
