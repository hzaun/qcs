package com.nuzharukiya.spapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nuzharukiya.spapp.utils.BaseUtils;
import com.nuzharukiya.spapp.utils.ImageUtils;
import com.nuzharukiya.spapp.utils.UIBase;
import com.nuzharukiya.spapp.utils.UIComponents;

import butterknife.ButterKnife;

public class SPApp extends AppCompatActivity implements UIBase {

    private Context context;


    protected UIComponents uiComponents;
    public BaseUtils baseUtils;
    protected ImageUtils imageUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp();
        initViews();
        initData();
        runFactory();
    }

    protected void startNextActivity(Class<?> nextClass) {
        startNextActivity(nextClass, true);
    }

    protected void startNextActivity(Class<?> nextClass, boolean bAnim) {
        startActivity(new Intent(context, nextClass));
        if (bAnim) ((Activity) context).overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @Override
    public void initApp() {
        context = this;

        baseUtils = new BaseUtils(context);
        imageUtils = new ImageUtils(context);
    }

    @Override
    public void initViews() {
        ButterKnife.bind((Activity) context);
    }

    @Override
    public void initData() {
    }

    @Override
    public void runFactory() {
    }

    public UIComponents getUiComponents() {
        return uiComponents;
    }
}
