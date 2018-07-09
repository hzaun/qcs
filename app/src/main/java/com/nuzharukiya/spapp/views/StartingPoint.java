package com.nuzharukiya.spapp.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.room.ServiceProviderDatabase;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIComponents;
import com.nuzharukiya.spapp.views.frags.MoreSettingsFragment;
import com.nuzharukiya.spapp.views.frags.NewTasksFragment;
import com.nuzharukiya.spapp.views.frags.OngoingTasksFragment;
import com.nuzharukiya.spapp.views.frags.ProfileFragment;

import java.util.Objects;

import butterknife.BindView;

public class StartingPoint extends SPApp implements
        View.OnClickListener {

    private Context context;
    private ServiceProviderDatabase spDatabase;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private ConstraintLayout clNavHeader;
    private ImageView ivUserDisplay;
    private TextView tvUserName, tvEmailId;

    // Menu - Tabs

    private static int TAB_ICON_COLOR_SELECTED;
    private static int TAB_ICON_COLOR_UNSELECTED;

    @BindView(R.id.tlMenu)
    TabLayout tlMenu;

    @BindView(R.id.flContainer)
    FrameLayout flContainer;

    Fragment fragment5 = new MoreSettingsFragment();
    Fragment fragment4 = new ProfileFragment();
    Fragment fragment3 = new MoreSettingsFragment();
    Fragment fragment2 = new OngoingTasksFragment();
    Fragment fragment1 = new NewTasksFragment();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_starting_point);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;
        spDatabase = ServiceProviderDatabase.getInstance(context);

        uiComponents = new UIComponents(context, true);
        uiComponents.setToolbarItems(R.drawable.ic_menu, R.string.app_name);

        TAB_ICON_COLOR_SELECTED = ContextCompat.getColor(context, R.color.colorAccent);
        TAB_ICON_COLOR_UNSELECTED = ContextCompat.getColor(context, R.color.darkGray);

        SPAppPreferences.setUserLoggedIn(true);

        if (!SPAppPreferences.getUserSetPassword()) {
            showSetPasswordDialog();
        }
    }

    private void showSetPasswordDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_set_password);

        final EditText etPassword = dialog.findViewById(R.id.etPassword);
        final EditText etConfirmPassword = dialog.findViewById(R.id.etConfirmPassword);

        TextView tvApply = dialog.findViewById(R.id.tvApply);
        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPassword = etPassword.getText().toString();
                String sConfirmPassword = etConfirmPassword.getText().toString();
                if (sPassword.length() < 8) {
                    baseUtils.makeToast("Password must have atleast 8 characters!");
                } else if (!sConfirmPassword.equals(sPassword)) {
                    baseUtils.makeToast("Passwords don't match!");
                } else {
                    baseUtils.makeToast("Password set!");
                    SPAppPreferences.setUserSetPassword(true);
                    dialog.dismiss();
                }
            }
        });

        Objects.requireNonNull(dialog.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.show();
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
        initTabs();
    }

    private void initTabs() {
        final FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.flContainer, fragment5, "5").commit();
        fm.beginTransaction().add(R.id.flContainer, fragment4, "4").commit();
        fm.beginTransaction().add(R.id.flContainer, fragment3, "3").commit();
        fm.beginTransaction().add(R.id.flContainer, fragment2, "2").commit();
        fm.beginTransaction().add(R.id.flContainer, fragment1, "1").commit();


        fm.beginTransaction().hide(active).show(fragment1).commit();
        fm.beginTransaction().hide(fragment2).commit();
        fm.beginTransaction().hide(fragment3).commit();
        fm.beginTransaction().hide(fragment4).commit();
        fm.beginTransaction().hide(fragment5).commit();

        Objects.requireNonNull(tlMenu.getTabAt(0).getIcon()).setColorFilter(TAB_ICON_COLOR_SELECTED, PorterDuff.Mode.SRC_IN);
        active = fragment1;

        tlMenu.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Objects.requireNonNull(tab.getIcon()).setColorFilter(TAB_ICON_COLOR_SELECTED, PorterDuff.Mode.SRC_IN);

                if (tab.getPosition() == 0) {
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                }
                if (tab.getPosition() == 1) {
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                }
                if (tab.getPosition() == 2) {
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                }
                if (tab.getPosition() == 3) {
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                }
                if (tab.getPosition() == 4) {
                    fm.beginTransaction().hide(active).show(fragment5).commit();
                    active = fragment5;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).setColorFilter(TAB_ICON_COLOR_UNSELECTED, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        imageUtils.loadImage(SPAppPreferences.getImageUrl().isEmpty() ? "https://i.imgur.com/80uMcZx.jpg" : SPAppPreferences.getImageUrl(), ivUserDisplay);
        tvUserName.setText(SPAppPreferences.getDisplayName());
        Objects.requireNonNull(tvEmailId).setText(SPAppPreferences.getUserEmail());
    }

    @Override
    public void runFactory() {
        super.runFactory();

        // Call services that will be used to populate this screen
    }

    private void initNavigation() {
        clNavHeader.findViewById(R.id.tvSettings).setOnClickListener(this);
        navigationView.findViewById(R.id.tvLogout).setOnClickListener(this);
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
                startNextActivity(SettingsActivity.class, false);
                drawer.closeDrawer(GravityCompat.START);
            }
            break;
            case R.id.tvLogout: {
                logout();
            }
            break;
        }
    }

    void logout() {
        SPAppPreferences.setUserLoggedIn(false);
        Intent iGoToLogin = new Intent(context, LoginActivity.class);
        // Set the new task and clear flags
        iGoToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iGoToLogin);
    }

    public ServiceProviderDatabase getSpDatabase() {
        return spDatabase;
    }
}
