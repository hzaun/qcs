package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.utils.SPAppPreferences;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends SPApp {

    private Context context;

    @BindView(R.id.tvLogout)
    TextView tvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @OnClick(R.id.tvLogout)
    void logout(){
        SPAppPreferences.setUserLoggedIn(false);
        Intent iGoToLogin = new Intent(context, LoginActivity.class);
        // Set the new task and clear flags
        iGoToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iGoToLogin);
    }
}
