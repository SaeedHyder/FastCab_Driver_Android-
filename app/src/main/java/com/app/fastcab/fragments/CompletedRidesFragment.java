package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.CompletedTripsEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.CompletedTripBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class CompletedRidesFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.CompletedTrips_ListView)
    ListView CompletedTripsListView;
    private ArrayListAdapter<CompletedTripsEnt> adapter;

    private ArrayList<CompletedTripsEnt> userCollection = new ArrayList<>();

    public static CompletedRidesFragment newInstance() {
        return new CompletedRidesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<CompletedTripsEnt>(getDockActivity(), new CompletedTripBinder());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_trips, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getPastTripsData();


    }

    private void getPastTripsData() {

       /* if(result.size()<0)
        {
            txtNoData.setVisibility(View.VISIBLE);
            PastTripsListView.setVisibility(View.GONE);
        }
        else{
            txtNoData.setVisibility(View.GONE);
            PastTripsListView.setVisibility(View.GONE);
        }*/

        userCollection = new ArrayList<>();
        userCollection.add(new CompletedTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));
        userCollection.add(new CompletedTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));
        userCollection.add(new CompletedTripsEnt("055082595","AED 15.00","13-6-17 1:36PM","AED 2.00","drawable://" + R.drawable.trip));

        bindData(userCollection);

    }

    private void bindData(ArrayList<CompletedTripsEnt> userCollection) {

        adapter.clearList();
        if (CompletedTripsListView!=null)
            CompletedTripsListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {

        CompletedTripsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(CompletedRIdesDetailFragment.newInstance(),CompletedRIdesDetailFragment.class.getSimpleName());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.Completed_Trips));
    }



}
