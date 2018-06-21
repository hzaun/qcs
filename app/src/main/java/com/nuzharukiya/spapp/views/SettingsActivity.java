package com.nuzharukiya.spapp.views;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPApp;
import com.nuzharukiya.spapp.adapters.ServicesOfferedAdapter;
import com.nuzharukiya.spapp.helpers.SettingsHelper;
import com.nuzharukiya.spapp.room.ServiceProviderDatabase;
import com.nuzharukiya.spapp.room.entities.ServiceInfoEntity;
import com.nuzharukiya.spapp.room.entities.ServicesOfferedEntity;
import com.nuzharukiya.spapp.utils.ButterknifeUtils;
import com.nuzharukiya.spapp.utils.UIComponents;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.nuzharukiya.spapp.room.Collection.DATABASE_SERVICE_PROVIDER;

public class SettingsActivity extends SPApp {

    private Context context;
    private SettingsHelper settingsHelper;
    private ServiceProviderDatabase spDatabase;

    private boolean bEditMode = false;

    // User Details
    @BindView(R.id.acivUserDetails)
    AppCompatImageView acivUserDetails;
    @BindView(R.id.cvUserDetails)
    CardView cvUserDetails;
    @BindView(R.id.tietFullName)
    TextInputEditText tietFullName;

    // Working Days
    @BindView(R.id.acivWorkingDays)
    AppCompatImageView acivWorkingDays;
    @BindView(R.id.cvWorkingDays)
    CardView cvWorkingDays;

    @BindViews({R.id.fabMon, R.id.fabTue, R.id.fabWed, R.id.fabThu, R.id.fabFri, R.id.fabSat, R.id.fabSun})
    List<FloatingActionButton> fabDaysList;

    // Services Offered
    @BindView(R.id.tvServices)
    TextView tvServices;
    @BindView(R.id.acivServices)
    AppCompatImageView acivServices;
    @BindView(R.id.acivAddService)
    ImageView acivAddService;
    @BindView(R.id.cvServicesOffered)
    CardView cvServicesOffered;
    @BindView(R.id.etServiceName)
    EditText etServiceName;
    @BindView(R.id.etServicePrice)
    EditText etServicePrice;
    @BindView(R.id.rvServices)
    RecyclerView rvServices;
    @BindView(R.id.tvNoServices)
    TextView tvNoServices;

    // Salon Photos
    @BindView(R.id.acivPhotos)
    AppCompatImageView acivPhotos;
    @BindView(R.id.cvSalonPhotos)
    CardView cvSalonPhotos;

    private List<ServicesOfferedEntity> servicesOfferedList = new ArrayList<>();
    private ServicesOfferedAdapter servicesOfferedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = this;
        settingsHelper = new SettingsHelper(context);


        spDatabase = ServiceProviderDatabase.getInstance(context);

        uiComponents = new UIComponents(context, true);
        uiComponents.setToolbarItems(R.drawable.ic_left, R.string.activity_settings, R.drawable.ic_edit);
    }

    @Override
    public void initViews() {
        super.initViews();

        try {
            initWorkingDays();
            initServicesOfferedList();
            // Text mode
            switchMode(true);

            uiComponents.onClickEndIcon(SettingsActivity.class.getMethod("switchEditing"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {
        super.initData();
        LiveData<List<ServiceInfoEntity>> serviceInfoLiveData = spDatabase.serviceInfoDao().fetchServiceProviderInfo();
        serviceInfoLiveData.observe(this, new Observer<List<ServiceInfoEntity>>() {
            @Override
            public void onChanged(@Nullable List<ServiceInfoEntity> serviceInfoEntities) {
                if (serviceInfoEntities.size() > 0) {
                    tietFullName.setText(serviceInfoEntities.get(0).getFullName());
                    servicesOfferedList.clear();
                    servicesOfferedList.addAll(serviceInfoEntities.get(0).getServicesOfferedList());
                    servicesOfferedAdapter.notifyDataSetChanged();
                    resizeList();
                }
            }
        });
    }

    private void initServicesOfferedList() {
        servicesOfferedAdapter = new ServicesOfferedAdapter(context, servicesOfferedList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        rvServices.setLayoutManager(mLayoutManager);
        rvServices.setItemAnimator(new DefaultItemAnimator());
        rvServices.setAdapter(servicesOfferedAdapter);

        resizeList();
    }

    private void resizeList() {
        if (servicesOfferedList.size() < 1) {
            rvServices.setVisibility(View.GONE);
            tvNoServices.setVisibility(View.VISIBLE);
        } else if (rvServices.getVisibility() == View.GONE) {
            // If there is an item or more, and the view is hidden, show it
            rvServices.setVisibility(View.VISIBLE);
            tvNoServices.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.acivUserDetails)
    public void switchUserDetails() {
        settingsHelper.switchView(cvUserDetails, acivUserDetails);
    }

    @OnClick(R.id.acivWorkingDays)
    public void switchWorkingDaysView() {
        settingsHelper.switchView(cvWorkingDays, acivWorkingDays);
    }

    @OnClick(R.id.acivServices)
    public void switchServicesOfferedView() {
        settingsHelper.switchView(cvServicesOffered, acivServices);
    }

    @OnClick(R.id.acivPhotos)
    public void switchSalonPhotosView() {
        settingsHelper.switchView(cvSalonPhotos, acivPhotos);
    }

    @OnClick(R.id.acivAddService)
    public void onServiceAdded() {
        String serviceName = etServiceName.getText().toString().trim();
        String servicePrice = etServicePrice.getText().toString().trim();

        if (serviceName.isEmpty()) {
            ((SPApp) context).baseUtils.makeToast("Please enter a valid service name");
        } else if (servicePrice.isEmpty()) {
            ((SPApp) context).baseUtils.makeToast("Please enter a valid service price");
        } else {
            etServiceName.setText("");
            etServicePrice.setText("");

            servicesOfferedList.add(new ServicesOfferedEntity(serviceName, servicePrice));
            servicesOfferedAdapter.notifyDataSetChanged();

            resizeList();
        }
    }

    private void initWorkingDays() {
        for (final FloatingActionButton fab : fabDaysList) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickFab(fab);
                }
            });
        }
    }

    public void switchEditing() {
        if (bEditMode) {
            // Store data, change icon and switch to non-edit mode
            getData();
            uiComponents.setIvEndIcon(R.drawable.ic_edit);
            switchMode(true);
            baseUtils.makeToast("Changes saved!");
        } else {
            // Change icon and switch to edit mode
            uiComponents.setIvEndIcon(R.drawable.ic_done);
            switchMode(false);
            ButterKnife.apply(fabDaysList, ButterknifeUtils.ENABLED, true);
        }
        bEditMode = !bEditMode;
    }

    private void switchMode(boolean bTextMode) {
        if (bTextMode) {
            tietFullName.setEnabled(false);
            ButterKnife.apply(fabDaysList, ButterknifeUtils.DISABLE);

            // Services offered
            acivAddService.setVisibility(View.GONE);
            etServiceName.setVisibility(View.GONE);
            etServicePrice.setVisibility(View.GONE);
            if (servicesOfferedList.size() < 1) {
                tvNoServices.setVisibility(View.VISIBLE);
            }
            servicesOfferedAdapter.disableCb(true);
        } else {
            tietFullName.setEnabled(true);

            // Services Offered
            acivAddService.setVisibility(View.VISIBLE);
            etServiceName.setVisibility(View.VISIBLE);
            etServicePrice.setVisibility(View.VISIBLE);
            tvNoServices.setVisibility(View.GONE);
            servicesOfferedAdapter.disableCb(false);
        }
    }

    private void onClickFab(FloatingActionButton fab) {
        uiComponents.forceHideKeyboard();
        if (settingsHelper.isFabDeselected(fab)) {
            // Select Date
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        } else {
            // Deselect Date
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.darkGray)));
        }
    }

    private void getData() {
        ServiceInfoEntity serviceInfoEntity = new ServiceInfoEntity();
        serviceInfoEntity.setPhoneNo("9845141074");
        serviceInfoEntity.setFullName(tietFullName.getText().toString());
        serviceInfoEntity.setWorkingDays(settingsHelper.createWorkingDaysMap(fabDaysList));
        serviceInfoEntity.setServicesOfferedList(servicesOfferedList);

        settingsHelper.storeData(spDatabase, serviceInfoEntity);
    }
}
