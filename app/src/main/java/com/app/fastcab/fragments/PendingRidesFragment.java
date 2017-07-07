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
import com.app.fastcab.entities.PendingTripsEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.CompletedTripBinder;
import com.app.fastcab.ui.viewbinder.PendingTripBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingRidesFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.PendingRides_ListView)
    ListView PendingRidesListView;

    private ArrayListAdapter<PendingTripsEnt> adapter;

    private ArrayList<PendingTripsEnt> userCollection = new ArrayList<>();

    public static PendingRidesFragment newInstance() {
        return new PendingRidesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<PendingTripsEnt>(getDockActivity(), new PendingTripBinder(getDockActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_rides, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getPendingTripsData();
    }

    private void getPendingTripsData() {

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
        userCollection.add(new PendingTripsEnt("eat 09:10 PM","Dunkin Donut","Publix Super Marker"));
        bindData(userCollection);

    }

    private void bindData(ArrayList<PendingTripsEnt> userCollection) {

        adapter.clearList();
        if (PendingRidesListView!=null)
            PendingRidesListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {

        PendingRidesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //getDockActivity().replaceDockableFragment(PendingRidesDetailFragment.newInstance(),PendingRidesDetailFragment.class.getSimpleName());

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.btn_submit:
                getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(),HomeFragment.class.getSimpleName());
                break;*/
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.Pending_Rides));
    }


}
