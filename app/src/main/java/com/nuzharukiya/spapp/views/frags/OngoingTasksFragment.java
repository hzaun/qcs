package com.nuzharukiya.spapp.views.frags;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPAppFragment;
import com.nuzharukiya.spapp.adapters.AppointmentsAdapter;
import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;
import com.nuzharukiya.spapp.views.StartingPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class OngoingTasksFragment extends SPAppFragment implements
        SwipeRefreshLayout.OnRefreshListener {

    private StartingPoint context;
    private View rootView;

    // Timeline

    private SwipeRefreshLayout srlTimeline;
    private RecyclerView rvTimeline;
    private AppointmentsAdapter appointmentsAdapter;
    private List<AppointmentInfoEntity> appointmentsList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_ongoing_tasks, container, false);

        initViews();
        initData();

        return rootView;
    }

    private void initTimeline() {
        appointmentsAdapter = new AppointmentsAdapter(context, appointmentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvTimeline.setLayoutManager(layoutManager);
        rvTimeline.setItemAnimator(new DefaultItemAnimator());
        rvTimeline.setAdapter(appointmentsAdapter);
    }


    private void fetchTimelineData() {
        LiveData<List<AppointmentInfoEntity>> appointmentInfoLiveData = context.getSpDatabase().appointmentInfoDao().getAllAppointmentInfo();
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
                context.getSpDatabase().appointmentInfoDao().insertAppointmentData(appointmentsList);
            }
        };
        t.start();
    }

    @Override
    public void onRefresh() {
        insertTimelineData();
        srlTimeline.setRefreshing(false);
    }

    @Override
    public void initApp() {
        super.initApp();

        context = (StartingPoint) getActivity();
    }

    @Override
    public void initViews() {
        super.initViews();

        rvTimeline = rootView.findViewById(R.id.rvTimeline);
        srlTimeline = rootView.findViewById(R.id.srlTimeline);

        srlTimeline.setOnRefreshListener(this);

        initTimeline();
    }

    @Override
    public void initData() {
        super.initData();

        insertTimelineData();
        fetchTimelineData();
    }
}
