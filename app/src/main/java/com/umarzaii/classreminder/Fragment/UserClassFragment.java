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
import com.umarzaii.classreminder.Model.UserClassModel;
import com.umarzaii.classreminder.R;

import static com.umarzaii.classreminder.Handler.DatabaseHandler.PSMZAID;

public class UserClassFragment extends Fragment {

    private DatabaseHandler databaseHandler;
    private FragmentHandler fragmentHandler;

    private RecyclerView rvUserClass;
    private Button btnGoToAddUserClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragm_userclass,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("User Class Fragment");
        View v = getView();

        databaseHandler = new DatabaseHandler();
        fragmentHandler = new FragmentHandler(getActivity().getSupportFragmentManager());

        btnGoToAddUserClass = (Button)v.findViewById(R.id.btnGoToAddUserClass);
        rvUserClass = (RecyclerView)v.findViewById(R.id.rvUserClass);

        rvUserClass.setHasFixedSize(true);
        rvUserClass.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUserClass.addItemDecoration(new RVDividerItemHandler(getActivity()));
        rvUserClass.setItemAnimator(new DefaultItemAnimator());

        btnGoToAddUserClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHandler.stackFragment(new AddUserClassFragment(),"AddUserClass");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<UserClassModel,UserClassViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserClassModel, UserClassViewHolder>(

                UserClassModel.class,
                R.layout.rvitem_userclassrow,
                UserClassViewHolder.class,
                databaseHandler.getTblUniversityUserClass(PSMZAID)

        ) {
            @Override
            protected void populateViewHolder(UserClassViewHolder viewHolder, UserClassModel model, int position) {

                viewHolder.setUserClassID(model.getUserClassID());

                viewHolder.fView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

            }
        };
    }

    public static class UserClassViewHolder extends RecyclerView.ViewHolder {
        View fView;

        public UserClassViewHolder(View itemView) {
            super(itemView);
            fView = itemView;
        }

        public void setUserClassID(String userClassID) {
            TextView txtUserClassID = (TextView)fView.findViewById(R.id.txtUserClassID);
            txtUserClassID.setText(userClassID);
        }
    }

}
