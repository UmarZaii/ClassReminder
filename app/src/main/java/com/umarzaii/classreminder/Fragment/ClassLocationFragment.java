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
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.umarzaii.classreminder.Handler.DatabaseHandler;
import com.umarzaii.classreminder.Handler.FragmentHandler;
import com.umarzaii.classreminder.Handler.RVDividerItemHandler;
import com.umarzaii.classreminder.Model.ClassLocationModel;
import com.umarzaii.classreminder.R;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class ClassLocationFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private RecyclerView rvClassLocation;
    private Button btnGoToAddClassLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_classlocation,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Class Location");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        btnGoToAddClassLocation = (Button)v.findViewById(R.id.btnGoToAddClassLocation);
        rvClassLocation = (RecyclerView)v.findViewById(R.id.rvClassLocation);

        rvClassLocation.setHasFixedSize(true);
        rvClassLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvClassLocation.addItemDecoration(new RVDividerItemHandler(getActivity()));
        rvClassLocation.setItemAnimator(new DefaultItemAnimator());

        btnGoToAddClassLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new AddClassLocationFragment(),"AddClassLocation");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<ClassLocationModel,ClassLocationViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ClassLocationModel, ClassLocationViewHolder>(

                ClassLocationModel.class,
                R.layout.rvitem_classlocationrow,
                ClassLocationViewHolder.class,
                databaseHandler.getTblUniversityClassLocation(PSMZAID)

        ) {
            @Override
            protected void populateViewHolder(ClassLocationViewHolder viewHolder, ClassLocationModel model, int position) {

                viewHolder.setClassLocationID(model.getClassLocationID());

                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        };

        rvClassLocation.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ClassLocationViewHolder extends RecyclerView.ViewHolder {
        View fView;

        public ClassLocationViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setClassLocationID(String classLocationID) {
            TextView txtClassLocationID = (TextView)fView.findViewById(R.id.txtClassLocationID);
            txtClassLocationID.setText(classLocationID);
        }
    }

}
