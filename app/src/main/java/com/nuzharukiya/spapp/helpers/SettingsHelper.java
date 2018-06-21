package com.nuzharukiya.spapp.helpers;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.View;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.room.Collection;
import com.nuzharukiya.spapp.room.ServiceProviderDatabase;
import com.nuzharukiya.spapp.room.entities.ServiceInfoEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nuzha Rukiya on 18/06/19.
 */
public class SettingsHelper {

    private Context context;

    public SettingsHelper(Context context) {
        this.context = context;
    }

    public void switchView(CardView cardView, AppCompatImageView acImageView) {
        if (cardView.getVisibility() == View.VISIBLE) {
            acImageView.setImageResource(R.drawable.ic_down);
            cardView.setVisibility(View.GONE);
        } else {
            acImageView.setImageResource(R.drawable.ic_up);
            cardView.setVisibility(View.VISIBLE);
        }
    }

    public HashMap<String, Boolean> createWorkingDaysMap(List<FloatingActionButton> fabDaysList) {
        HashMap<String, Boolean> wdMap = new HashMap<>();
        for (int fx = 0; fx < fabDaysList.size(); fx++) {
            wdMap.put(Collection.DAY_LIST.get(fx), isFabSelected(fabDaysList.get(fx)));
        }
        return wdMap;
    }

    private boolean isFabSelected(FloatingActionButton fabMon) {
        return !isFabDeselected(fabMon);
    }

    public boolean isFabDeselected(FloatingActionButton fab) {
        return fab.getBackgroundTintList().getDefaultColor() == context.getResources().getColor(R.color.darkGray);
    }

    public void storeData(final ServiceProviderDatabase spDatabase, final ServiceInfoEntity serviceInfoEntity) {
        Thread t = new Thread() {
            public void run() {
                spDatabase.serviceInfoDao().insertServiceProviderInfo(serviceInfoEntity);
            }
        };
        t.start();
    }
}
