package com.nuzharukiya.spapp.views;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.adapters.AppointmentsAdapter;
import com.nuzharukiya.spapp.room.ServiceProviderDatabase;
import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;
import com.nuzharukiya.spapp.room.entities.ServiceInfoEntity;
import com.nuzharukiya.spapp.utils.SPAppPreferences;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class StartingPoint extends SPApp implements
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private ServiceProviderDatabase spDatabase;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    // Timeline

    private SwipeRefreshLayout srlTimeline;
    private RecyclerView rvTimeline;
    private AppointmentsAdapter appointmentsAdapter;
    private List<AppointmentInfoEntity> appointmentsList = new ArrayList<>();

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
        spDatabase = ServiceProviderDatabase.getInstance(context);

        uiComponents = new UIComponents(context, true);
        uiComponents.setToolbarItems(R.drawable.ic_menu, R.string.app_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getBoolean("SHOW_DIALOG")) {
                showSetPasswordDialog();
            }
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
                if (sPassword.length() > 8) {
                    baseUtils.makeToast("Password must have atleast 8 characters!");
                } else if (!sConfirmPassword.equals(sPassword)) {
                    baseUtils.makeToast("Passwords don't match!");
                } else {
                    baseUtils.makeToast("Password set!");
                    dialog.dismiss();
                }
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
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
        rvTimeline = findViewById(R.id.rvTimeline);
        srlTimeline = findViewById(R.id.srlTimeline);

        uiComponents.initDrawerToggle(drawer);
        srlTimeline.setOnRefreshListener(this);

        initNavigation();
        initTimeline();
    }

    private void initTimeline() {
        appointmentsAdapter = new AppointmentsAdapter(context, appointmentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setItemAnimator(new DefaultItemAnimator());
        rvTimeline.setAdapter(appointmentsAdapter);
    }

    @Override
    public void initData() {
        super.initData();

        imageUtils.loadImage(SPAppPreferences.getImageUrl().isEmpty() ? "https://i.imgur.com/80uMcZx.jpg" : SPAppPreferences.getImageUrl(), ivUserDisplay);
        tvUserName.setText(SPAppPreferences.getDisplayName());
        Objects.requireNonNull(tvEmailId).setText(SPAppPreferences.getUserEmail());

        insertTimelineData();
        fetchTimelineData();
    }

    private void fetchTimelineData() {
        LiveData<List<AppointmentInfoEntity>> appointmentInfoLiveData = spDatabase.appointmentInfoDao().getAllAppointmentInfo();
        appointmentInfoLiveData.observe(this, new Observer<List<AppointmentInfoEntity>>() {
            @Override
            public void onChanged(@Nullable List<AppointmentInfoEntity> appointmentInfoEntities) {
                if (appointmentInfoEntities.size() > 0) {
                    appointmentsList.clear();
                    appointmentsList.addAll(appointmentInfoEntities);
                    appointmentsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void insertTimelineData() {

        List<String> serviceTypes = new ArrayList<String>();
        serviceTypes.add("1");
        serviceTypes.add("3");
        serviceTypes.add("5");
        serviceTypes.add("8");
        appointmentsList.add(new AppointmentInfoEntity(
                "111",
                "Nuzha Rukiya",
                "180620",
                "15:00",
                serviceTypes
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "121",
                "Manogna Kamineni",
                "180620",
                "16:00",
                new ArrayList<String>() {
                    {
                        add("1");
                        add("4");
                        add("7");
                        add("8");
                    }
                }
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "141",
                "Manogna Kamineni",
                "180620",
                "19:00",
                new ArrayList<String>() {
                    {
                        add("1");
                        add("3");
                        add("5");
                    }
                }
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "151",
                "Nuzha R",
                "180620",
                "19:30",
                new ArrayList<String>() {
                    {
                        add("1");
                        add("2");
                        add("5");
                    }
                }
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "161",
                "Nuzha R",
                "180620",
                "20:30",
                new ArrayList<String>() {
                    {
                        add("2");
                        add("3");
                        add("5");
                    }
                }
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "171",
                "Manogna Kamineni",
                "180621",
                "19:00",
                new ArrayList<String>() {
                    {
                        add("1");
                        add("3");
                        add("5");
                    }
                }
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "181",
                "Nuzha R",
                "180621",
                "19:30",
                serviceTypes
        ));

        appointmentsList.add(new AppointmentInfoEntity(
                "191",
                "Nuzha R",
                "180621",
                "20:30",
                new ArrayList<String>() {
                    {
                        add("2");
                        add("3");
                        add("5");
                    }
                }
        ));

//        appointmentsAdapter.notifyDataSetChanged();

        Thread t = new Thread() {
            public void run() {
                spDatabase.appointmentInfoDao().insertAppointmentData(appointmentsList);
            }
        };
        t.start();
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

    @Override
    public void onRefresh() {
        insertTimelineData();
        srlTimeline.setRefreshing(false);
    }
}
