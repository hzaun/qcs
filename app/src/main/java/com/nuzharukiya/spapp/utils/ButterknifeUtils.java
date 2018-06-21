package com.nuzharukiya.spapp.utils;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Nuzha Rukiya on 18/06/19.
 */
public interface ButterknifeUtils {

    ButterKnife.Action<View> DISABLE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setEnabled(false);
        }
    };

    ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };
}
