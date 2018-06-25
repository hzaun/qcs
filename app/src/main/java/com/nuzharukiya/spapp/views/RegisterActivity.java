package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.SPApplication;
import com.nuzharukiya.spapp.helpers.RegisterHelper;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIComponents;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nuzharukiya.spapp.utils.Constants.RESULT_MESSAGE;
import static com.nuzharukiya.spapp.utils.SPUrls.GET_REGISTER_USER;
import static com.nuzharukiya.spapp.utils.SPUrls.GET_VALIDATE_OTP;

public class RegisterActivity extends SPApp {

    private Context context;
    private RegisterHelper registerHelper;
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.coordLayout)
    CoordinatorLayout coordLayout;
    @BindView(R.id.actvPhoneNo)
    AutoCompleteTextView actvPhoneNo;
    @BindView(R.id.actvEmailId)
    AutoCompleteTextView actvEmailId;
    @BindView(R.id.actvFullName)
    AutoCompleteTextView actvFullName;
    @BindView(R.id.actvRegisterOtp)
    AutoCompleteTextView actvRegisterOtp;
    @BindView(R.id.bRegister)
    Button bRegister;

    private boolean bOTPView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;

        registerHelper = new RegisterHelper();
        uiComponents = new UIComponents(context, false);
        uiComponents.makeStatusBarTransparent();
        uiComponents.hideNavigationBarImmersive();
    }

    @Override
    public void initViews() {
        super.initViews();

        actvFullName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    register();
                    handled = true;
                }
                return handled;
            }
        });
    }

    @OnClick(R.id.bRegister)
    void register() {
        if (bOTPView) {
            String sOTP = actvRegisterOtp.getText().toString().trim();

            try {
                validateOTP(sOTP);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String sPhoneNo = actvPhoneNo.getText().toString().trim();
            String sEmailId = actvEmailId.getText().toString().trim();
            String sFullName = actvFullName.getText().toString().trim();

            int ERROR_CODE = registerHelper.getErrorCode(sPhoneNo, sEmailId, sFullName);
            if (ERROR_CODE > 0) {
                baseUtils.showSnackbar(coordLayout, ERROR_CODE);
            } else {
                try {
                    registerUser(sEmailId, sPhoneNo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void validateOTP(String sOTP) throws JSONException {
        baseUtils.getLoader();

        String URL_VALIDATE_OTP = GET_VALIDATE_OTP + "?Type=1&Email=" + SPAppPreferences.getUserEmail() + "&Otp=" + sOTP;

        StringRequest jorValidateOTP = new StringRequest(
                Request.Method.GET,
                URL_VALIDATE_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        baseUtils.makeToast("OTP validated successfully!");
                        onOTPValidated();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        baseUtils.makeToast(error.getMessage());
                        // hide the progress dialog
                        baseUtils.dismissLoader();
                    }
                });
        // Adding request to request queue
        SPApplication.getInstance().addToRequestQueue(jorValidateOTP);

//        // TODO: Remove
//        onOTPValidated();
    }

    private void onOTPValidated() {
        baseUtils.dismissLoader();

        goToDashboard();
//        goToLoginA();
    }

    private void goToDashboard() {
        startNextActivity(StartingPoint.class);
    }

    private void goToLoginA() {
        // Go back to login
        Intent resIntent = getIntent();
        resIntent.putExtra(RESULT_MESSAGE, R.string.register_success);
        setResult(RESULT_OK, resIntent);
        finish();
    }

    private void registerUser(final String sEmail, String sName) throws JSONException {
        // Call API to register user
        baseUtils.getLoader();

        String URL_REGISTER_USER = GET_REGISTER_USER + "?Type=1&Email=" + sEmail + "&Name=" + sName;

        StringRequest jorRegisterUser = new StringRequest(
                Request.Method.GET,
                URL_REGISTER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        baseUtils.makeToast("OTP sent!");

                        SPAppPreferences.setUserEmail(sEmail);
                        onRegistrationValidated();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        baseUtils.makeToast(error.getMessage());
                        // hide the progress dialog
                        baseUtils.dismissLoader();
                    }
                });
        // Adding request to request queue
        SPApplication.getInstance().addToRequestQueue(jorRegisterUser);

//        // TODO: Remove
//        baseUtils.makeToast("OTP sent!");
//
//        SPAppPreferences.setUserEmail(sEmail);
//        onRegistrationValidated();
    }

    private void onRegistrationValidated() {
        // Verify user with OTP
        changeToOTPView();

        bOTPView = true;
        baseUtils.dismissLoader();
    }

    private void changeToOTPView() {
        actvPhoneNo.setVisibility(View.INVISIBLE);
        actvEmailId.setVisibility(View.INVISIBLE);
        actvFullName.setVisibility(View.INVISIBLE);

        actvRegisterOtp.setVisibility(View.VISIBLE);

        bRegister.setText("Validate OTP");
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}