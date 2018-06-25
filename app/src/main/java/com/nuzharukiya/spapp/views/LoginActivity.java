package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.helpers.LoginHelper;
import com.nuzharukiya.spapp.network.ConnectorInterface;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIBase;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nuzharukiya.spapp.utils.Constants.REQUEST_GOOGLE_SIGN_IN;
import static com.nuzharukiya.spapp.utils.Constants.REQUEST_REGISTER;
import static com.nuzharukiya.spapp.utils.Constants.RESULT_MESSAGE;

public class LoginActivity extends SPApp implements
        UIBase,
        ConnectorInterface {

    private Context context;
    private static final String TAG = "LoginA";
    private LoginHelper loginHelper;

    @BindView(R.id.actvUsername)
    AutoCompleteTextView actvUsername;
    @BindView(R.id.actvPassword)
    AutoCompleteTextView actvPassword;
    @BindView(R.id.bLogin)
    Button bLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    // Facebook Login
    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    @BindView(R.id.blFacebook)
    LoginButton blFacebook;

    // Google Login
    GoogleSignInClient mGoogleSignInClient;
    @BindView(R.id.sibGoogle)
    SignInButton sibGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;

        loginHelper = new LoginHelper(context);
        uiComponents = new UIComponents(context, false);
        uiComponents.makeStatusBarTransparent();
        uiComponents.hideNavigationBarImmersive();

        // Facebook login
        callbackManager = loginHelper.createFacebookCallbackManager();
        // Google login
        mGoogleSignInClient = loginHelper.retrieveGoogleSignInClient();
    }

    @Override
    public void initViews() {
        super.initViews();
        actvPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    handled = true;
                }
                return handled;
            }
        });

        initFacebookLogin();
    }

    private void initFacebookLogin() {
        blFacebook.setReadPermissions(Arrays.asList(EMAIL));
        // Callback registration
        blFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginHelper.storeUserDetails(
                        loginResult.getAccessToken().getToken(),
                        loginResult.getAccessToken().getUserId(),
                        Profile.getCurrentProfile().getName(),
                        Profile.getCurrentProfile().getName(),
                        Profile.getCurrentProfile().getFirstName(),
                        Profile.getCurrentProfile().getLastName(),
                        Profile.getCurrentProfile().getProfilePictureUri(500, 500));
                enterApp();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Facebook login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, "Facebook login exception : " + exception.toString());
                baseUtils.makeToast("Error, please try again!");
            }
        });
    }

    @OnClick(R.id.bLogin)
    void login() {
        String sUsername = actvUsername.getText().toString().trim();
        String sPassword = actvPassword.getText().toString().trim();

        if (sPassword.length() < 8) {
            baseUtils.makeToast(R.string.info_password_length);
        } else {
            // TODO: Call login API
            onProcessFinish("", null);
        }
    }

    @OnClick(R.id.sibGoogle)
    void loginWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_IN);
    }

    @OnClick(R.id.tvRegister)
    void registerUser() {
        startActivityForResult(new Intent(context, RegisterActivity.class), REQUEST_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data); // Facebook login callback
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GOOGLE_SIGN_IN: {
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                }
                break;
                case REQUEST_REGISTER: {
                    baseUtils.makeToast(data.getIntExtra(RESULT_MESSAGE, 0));
                }
                break;
            }
        } else if (requestCode == REQUEST_GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            loginHelper.storeUserDetails(
                    account.getIdToken(),
                    account.getId(),
                    account.getEmail(),
                    account.getDisplayName(),
                    account.getGivenName(),
                    account.getFamilyName(),
                    account.getPhotoUrl());

            // Signed in successfully, show authenticated UI.
            enterApp();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            baseUtils.makeToast("Login failed, please try again!");
        }
    }

    @Override
    public void onProcessFinish(String path, Object response) {
        if (path.contains("")) {
            SPAppPreferences.setUserEmail(actvUsername.getText().toString().trim());
            SPAppPreferences.setUserLoggedIn(true);
            enterApp();
        }
    }

    private void enterApp() {
        // Enter app
        SPAppPreferences.setUserSetPassword(true);
        startNextActivity(StartingPoint.class);
        LoginActivity.this.finish();
    }
}
