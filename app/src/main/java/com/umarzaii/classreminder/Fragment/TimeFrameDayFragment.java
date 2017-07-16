package com.umarzaii.classreminder.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Handler.RVDividerItemHandler;
import com.umarzaii.classreminder.Model.TimeFrameModel;
import com.umarzaii.classreminder.R;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class TimeFrameDayFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private DatabaseReference dbClassPicker;
    private RecyclerView rvTimeFrameDay;

    private String classLocationID, userClassID, displayType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_timeframeday,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("TimeFrame Day");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        rvTimeFrameDay = (RecyclerView)v.findViewById(R.id.rvTimeFrameDay);

        displayType = getArguments().getString("displayType");
        if (displayType.equals(databaseHandler.tblUserClass)) {
            userClassID = getArguments().getString("userClassID");
            dbClassPicker = databaseHandler.getTblUniversityUserClassTimeFrameDay(PSMZAID,userClassID);
        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
            classLocationID = getArguments().getString("classLocationID");
            dbClassPicker = databaseHandler.getTblUniversityClassLocationTimeFrameDay(PSMZAID,classLocationID);
        }

        rvTimeFrameDay.setHasFixedSize(true);
        rvTimeFrameDay.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTimeFrameDay.addItemDecoration(new RVDividerItemHandler(getActivity()));
        rvTimeFrameDay.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<TimeFrameModel,TimeFrameDayViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TimeFrameModel, TimeFrameDayViewHolder>(

                TimeFrameModel.class,
                R.layout.rvitem_timeframedayrow,
                TimeFrameDayViewHolder.class,
                dbClassPicker

        ) {
            @Override
            protected void populateViewHolder(TimeFrameDayViewHolder viewHolder, TimeFrameModel model, int position) {

                final String dayID = model.getDayID();
                viewHolder.setDayID(dayID);

                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("dayID", dayID);

                        if (displayType.equals(databaseHandler.tblUserClass)) {
                            bundle.putString("userClassID", userClassID);
                            bundle.putString("displayType", databaseHandler.tblUserClass);
                        } else if (displayType.equals(databaseHandler.tblClassLocation)) {
                            bundle.putString("classLocationID", classLocationID);
                            bundle.putString("displayType", databaseHandler.tblClassLocation);
                        }

                        fragmentHandler.stackFragment(new TimeFrameHourFragment(),bundle,"TimeFrameHour");
                    }
                });

            }
        };

        rvTimeFrameDay.setAdapter(firebaseRecyclerAdapter);
    }

    public static class TimeFrameDayViewHolder extends RecyclerView.ViewHolder {
        View fView;

        public TimeFrameDayViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setDayID(String dayID) {
            TextView txtTimeFrameDayID = (TextView)fView.findViewById(R.id.txtTimeFrameDayID);
            txtTimeFrameDayID.setText(dayID);
        }
    }

}
