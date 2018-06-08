package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.network.ConnectorInterface;
import com.nuzharukiya.spapp.utils.BaseUtils;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIBase;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nuzharukiya.spapp.utils.Constants.REQUEST_REGISTER;
import static com.nuzharukiya.spapp.utils.Constants.RESULT_MESSAGE;

public class LoginActivity extends SPApp implements
        UIBase,
        ConnectorInterface {

    private Context context;

    @BindView(R.id.actvUsername)
    AutoCompleteTextView actvUsername;
    @BindView(R.id.actvPassword)
    AutoCompleteTextView actvPassword;
    @BindView(R.id.bLogin)
    Button bLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;

        uiComponents = new UIComponents(context, false);
        uiComponents.makeStatusBarTransparent();
        uiComponents.hideNavigationBarImmersive();
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

    @OnClick(R.id.tvRegister)
    void registerUser() {
        startActivityForResult(new Intent(context, RegisterActivity.class), REQUEST_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_REGISTER: {
                    baseUtils.makeToast(data.getIntExtra(RESULT_MESSAGE, 0));
                }
                break;
            }
        }
    }

    @Override
    public void onProcessFinish(String path, Object response) {
        if (path.contains("")) {
            // Enter app
            SPAppPreferences.setUserEmail(actvUsername.getText().toString().trim());
            SPAppPreferences.setUserLoggedIn(true);
            startNextActivity(StartingPoint.class);
        }
    }
}
