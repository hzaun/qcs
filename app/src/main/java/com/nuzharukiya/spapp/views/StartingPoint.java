package com.nuzharukiya.spapp.views;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.Objects;

import butterknife.BindView;

public class StartingPoint extends SPApp
        implements View.OnClickListener {

    private Context context;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ConstraintLayout clNavHeader;
    private ImageView ivUserDisplay;
    private TextView tvUserName, tvEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_starting_point);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;

        uiComponents = new UIComponents(context, true);
        uiComponents.setToolbarItems(R.drawable.ic_menu, R.string.app_name);
    }

    @Override
    public void initViews() {
        super.initViews();

        clNavHeader = (ConstraintLayout) navigationView.getHeaderView(0);
        ivUserDisplay = clNavHeader.findViewById(R.id.ivUserDisplay);
        tvUserName = clNavHeader.findViewById(R.id.tvUserName);
        tvEmailId = clNavHeader.findViewById(R.id.tvEmailId);

        uiComponents.initDrawerToggle(drawer);
        initNavigation();
    }

    @Override
    public void initData() {
        super.initData();

        imageUtils.loadImage("https://i.imgur.com/80uMcZx.jpg", ivUserDisplay);
        Objects.requireNonNull(tvEmailId).setText(SPAppPreferences.getUserEmail());
    }

    @Override
    public void runFactory() {
        super.runFactory();

        // Call services that will be used to populate this screen
    }

    private void initNavigation() {
        clNavHeader.findViewById(R.id.tvSettings).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSettings: {
                startNextActivity(SettingsActivity.class);
                drawer.closeDrawer(GravityCompat.START);
            }
            break;
        }
    }
}
