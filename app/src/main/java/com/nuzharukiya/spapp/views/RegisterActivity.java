package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.helpers.RegisterHelper;
import com.nuzharukiya.spapp.network.ConnectorInterface;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

import static com.nuzharukiya.spapp.utils.Constants.RESULT_MESSAGE;

public class RegisterActivity extends SPApp implements
        ConnectorInterface {

    private Context context;
    private RegisterHelper registerHelper;

    @BindView(R.id.coordLayout)
    CoordinatorLayout coordLayout;
    @BindView(R.id.actvPhoneNo)
    AutoCompleteTextView actvPhoneNo;
    @BindView(R.id.actvEmailId)
    AutoCompleteTextView actvEmailId;
    @BindView(R.id.actvPassword)
    AutoCompleteTextView actvPassword;
    @BindView(R.id.bRegister)
    Button bRegister;

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
    }

    @Override
    public void initViews() {
        super.initViews();

        actvPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        String sPhoneNo = actvPhoneNo.getText().toString().trim();
        String sEmailId = actvEmailId.getText().toString().trim();
        String sPassword = actvPassword.getText().toString().trim();

        int ERROR_CODE = registerHelper.getErrorCode(sPhoneNo, sEmailId, sPassword);
        if (ERROR_CODE > 0) {
            baseUtils.showSnackbar(coordLayout, ERROR_CODE);
        } else {
            registerUser();
        }
    }

    private void registerUser() {
        // Call API to register user
        onProcessFinish("", null);
    }

    @Override
    public void onProcessFinish(String path, Object response) {
        if(path.contains("")){
            // Go back to login
            Intent resIntent = getIntent();
            resIntent.putExtra(RESULT_MESSAGE, R.string.register_success);
            setResult(RESULT_OK, resIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }
}