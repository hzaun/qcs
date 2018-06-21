package com.nuzharukiya.spapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;
import com.nuzharukiya.spapp.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Nuzha Rukiya on 18/06/20.
 */
public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> {

    private Context context;
    private List<AppointmentInfoEntity> appointmentsList = new ArrayList<>();

    public AppointmentsAdapter(Context context, List<AppointmentInfoEntity> appointmentsList) {
        this.context = context;
        this.appointmentsList = appointmentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_timeline_item, parent, false);
        return new ViewHolder(rootView);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentInfoEntity appointmentInfoEntity = appointmentsList.get(position);

        // Date
        if (position == 0 || !appointmentInfoEntity.getAppointmentDate().equals(appointmentsList.get(position - 1).getAppointmentDate())) {
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(TimeUtils.prettyDate(appointmentInfoEntity.getAppointmentDate()));
        } else {
            holder.tvDate.setVisibility(View.GONE);
        }

        holder.acivInfo.setSupportImageTintList(ColorStateList.valueOf(context.getResources().getColor(getSunsetColor(appointmentInfoEntity.getAppointmentTime()))));
//        holder.civImage.setImageResource(R.color.blue);
        holder.tvName.setText(appointmentInfoEntity.getAppointmentFor());
        holder.tvAppointmentTime.setText(TimeUtils.convert24To12(appointmentInfoEntity.getAppointmentTime()));

        StringBuilder sbServices = new StringBuilder();
        List<String> serviceTypeList = appointmentInfoEntity.getServiceType();
        if (serviceTypeList != null && serviceTypeList.size() > 0) {
            for (String serviceType : serviceTypeList) {
                sbServices
                        .append(serviceType)
                        .append((serviceTypeList.indexOf(serviceType) == serviceTypeList.size() - 1) ? "" : ", ");
            }
            holder.tvAppointmentType.setText(sbServices.toString());
        }
    }

    private int getSunsetColor(String appointmentTime) {

        long currentTimeInMillis = TimeUtils.convert24ToMillis(appointmentTime);

        if (currentTimeInMillis <= 16200000) {
            return R.color.time_10_00;
        } else if (currentTimeInMillis <= 23400000) {
            return R.color.time_12_00;
        } else if (currentTimeInMillis <= 34200000) {
            return R.color.time_15_00;
        } else if (currentTimeInMillis <= 39600000) {
            return R.color.time_16_30;
        } else if (currentTimeInMillis <= 43200000) {
            return R.color.time_17_30;
        } else if (currentTimeInMillis <= 46800000) {
            return R.color.time_18_30;
        } else if (currentTimeInMillis <= 48600000) {
            return R.color.time_19_00;
        } else if (currentTimeInMillis <= 50400000) {
            return R.color.time_19_30;
        } else if (currentTimeInMillis <= 59400000) {
            return R.color.time_22_00;
        }

        return R.color.time_10_00;
    }

    @Override
    public int getItemCount() {
        return appointmentsList == null ? 0 : appointmentsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView acivInfo;
        CircleImageView civImage;
        TextView tvDate, tvName, tvAppointmentTime, tvAppointmentType;

        ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            acivInfo = itemView.findViewById(R.id.acivInfo);
            civImage = itemView.findViewById(R.id.civImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvAppointmentTime = itemView.findViewById(R.id.tvAppointmentTime);
            tvAppointmentType = itemView.findViewById(R.id.tvAppointmentType);
        }
    }
}
