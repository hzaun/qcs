package com.nuzharukiya.spapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.room.entities.ServicesOfferedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuzha Rukiya on 18/06/15.
 */
public class ServicesOfferedAdapter extends RecyclerView.Adapter<ServicesOfferedAdapter.ViewHolder> {

    private Context context;

    private List<ServicesOfferedEntity> servicesOfferedList;

    private boolean bDisableCb = false;

    public ServicesOfferedAdapter(Context context, List<ServicesOfferedEntity> servicesOfferedList) {
        this.context = context;
        this.servicesOfferedList = servicesOfferedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_services_offfered_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicesOfferedEntity servicesOfferedEntity = servicesOfferedList.get(position);

        holder.cbServiceName.setChecked(servicesOfferedEntity.isbServiceAvailable());
        holder.cbServiceName.setText(servicesOfferedEntity.getServiceName());
        holder.tvServicePrice.setText(servicesOfferedEntity.getServicePrice());

        holder.cbServiceName.setEnabled(!bDisableCb);
    }

    @Override
    public int getItemCount() {
        return servicesOfferedList == null ? 0 : servicesOfferedList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox cbServiceName;
        TextView tvServicePrice;

        ViewHolder(View itemView) {
            super(itemView);

            cbServiceName = itemView.findViewById(R.id.cbServiceName);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
        }
    }

    public void disableCb(boolean bDisableCb) {
        this.bDisableCb = bDisableCb;
        notifyDataSetChanged();
    }
}
