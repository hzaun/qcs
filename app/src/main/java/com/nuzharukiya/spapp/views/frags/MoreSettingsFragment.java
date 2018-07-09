package com.nuzharukiya.spapp.views.frags;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPAppFragment;

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class MoreSettingsFragment extends SPAppFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_more_settings, container, false);

        return rootView;
    }
}
