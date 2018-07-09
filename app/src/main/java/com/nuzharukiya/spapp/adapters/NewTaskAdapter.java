package com.nuzharukiya.spapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class NewTaskAdapter extends RecyclerView.Adapter<NewTaskAdapter.ViewHolder> {

    private Context context;
    private List<AppointmentInfoEntity> newAppointmentsList = new ArrayList<>();

    public NewTaskAdapter(Context context, List<AppointmentInfoEntity> newAppointmentsList) {
        this.context = context;
        this.newAppointmentsList = newAppointmentsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_new_task_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentInfoEntity infoModel = newAppointmentsList.get(position);

        holder.tvCustomerName.setText(infoModel.getAppointmentFor());
        holder.tvTime.setText("Recently"); // Relative Time

        StringBuilder sbServices = new StringBuilder();
        List<String> serviceTypeList = infoModel.getServiceType();
        if (serviceTypeList != null && serviceTypeList.size() > 0) {
            for (String serviceType : serviceTypeList) {
                sbServices
                        .append(serviceType)
                        .append((serviceTypeList.indexOf(serviceType) == serviceTypeList.size() - 1) ? "" : ", ");
            }
            holder.tvServicesRequired.setText(sbServices.toString());
        }

        holder.tvPrice.setText("13 credits");
        holder.tvAppointmentDate.setText(TimeUtils.convert24To12(infoModel.getAppointmentTime()));
        holder.tvAppointmentDuration.setText(TimeUtils.convert24To12(infoModel.getAppointmentTime()));
    }

    @Override
    public int getItemCount() {
        return newAppointmentsList == null ? 0 : newAppointmentsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCustomerName, tvTime, tvServicesRequired, tvPrice, tvAppointmentDate, tvAppointmentDuration;

        ViewHolder(View itemView) {
            super(itemView);

            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvServicesRequired = itemView.findViewById(R.id.tvServicesRequired);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvAppointmentDate = itemView.findViewById(R.id.tvAppointmentDate);
            tvAppointmentDuration = itemView.findViewById(R.id.tvAppointmentDuration);
        }
    }
}
