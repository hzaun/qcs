package com.nuzharukiya.spapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nuzharukiya.spapp.utils.UIBase;
import com.nuzharukiya.spapp.views.StartingPoint;

import butterknife.ButterKnife;

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class SPAppFragment extends Fragment implements UIBase {

    protected StartingPoint parentContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initData();
        runFactory();
    }

    @Override
    public void initApp() {
        parentContext = (StartingPoint) getActivity();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(parentContext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void runFactory() {

    }
}
