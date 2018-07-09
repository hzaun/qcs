package com.nuzharukiya.spapp.views.frags;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPAppFragment;
import com.nuzharukiya.spapp.adapters.NewTaskAdapter;
import com.nuzharukiya.spapp.room.entities.AppointmentInfoEntity;
import com.nuzharukiya.spapp.views.StartingPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class NewTasksFragment extends SPAppFragment {

    private StartingPoint parentContext;
    private View rootView;

    // New Tasks List

    @BindView(R.id.rvNewTasks)
    RecyclerView rvNewTasks;

    private List<AppointmentInfoEntity> newAppointmentsList = new ArrayList<>();
    private NewTaskAdapter newTaskAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initApp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_new_tasks, container, false);

        initViews();
        initData();

        return rootView;
    }

    @Override
    public void initApp() {
        super.initApp();

        parentContext = (StartingPoint) getActivity();

        Objects.requireNonNull(parentContext).getUiComponents().setTitle("New Appointments");
    }

    @Override
    public void initViews() {
        super.initViews();

        initNewTasks();
    }

    private void initNewTasks() {
        newTaskAdapter = new NewTaskAdapter(parentContext, newAppointmentsList);
       /* RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(parentContext);
        rvNewTasks.setItemAnimator(new DefaultItemAnimator());
        rvNewTasks.setLayoutManager(mLayoutManager);
        rvNewTasks.setAdapter(newTaskAdapter);*/
    }

    @Override
    public void initData() {
        super.initData();

        insertNewAppointments();

    }

    private void insertNewAppointments() {

        List<String> serviceTypes = new ArrayList<String>();
        serviceTypes.add("1");
        serviceTypes.add("3");
        serviceTypes.add("5");
        serviceTypes.add("8");
        newAppointmentsList.add(new AppointmentInfoEntity(
                "111",
                "Nuzha Rukiya",
                "180620",
                "15:00",
                serviceTypes
        ));

        newAppointmentsList.add(new AppointmentInfoEntity(
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

        newAppointmentsList.add(new AppointmentInfoEntity(
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

        newAppointmentsList.add(new AppointmentInfoEntity(
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

        newAppointmentsList.add(new AppointmentInfoEntity(
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

        newAppointmentsList.add(new AppointmentInfoEntity(
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

        newAppointmentsList.add(new AppointmentInfoEntity(
                "181",
                "Nuzha R",
                "180621",
                "19:30",
                serviceTypes
        ));

        newAppointmentsList.add(new AppointmentInfoEntity(
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
                parentContext.getSpDatabase().appointmentInfoDao().insertAppointmentData(newAppointmentsList);
            }
        };
        t.start();
    }
}
