package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.CompletedTripsEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.adapters.ArrayListAdapter;
import com.app.fastcabdriver.ui.viewbinder.CompletedTripBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.R.drawable.call;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class CompletedRidesFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.CompletedTrips_ListView)
    ListView CompletedTripsListView;
    private ArrayListAdapter<AssignRideEnt> adapter;

    private ArrayList<AssignRideEnt> userCollection = new ArrayList<>();

    public static CompletedRidesFragment newInstance() {
        return new CompletedRidesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<AssignRideEnt>(getDockActivity(), new CompletedTripBinder(getDockActivity()));
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
        getCompleteRidesData();


    }

    private void getCompleteRidesData() {

        loadingStarted();
        Call<ResponseWrapper<ArrayList<AssignRideEnt>>> call = webService.CompleteRides(Integer.parseInt(prefHelper.getDriverId()));

        call.enqueue(new Callback<ResponseWrapper<ArrayList<AssignRideEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<AssignRideEnt>>> call, Response<ResponseWrapper<ArrayList<AssignRideEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    setCompleteRidesData(response.body().getResult());
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<AssignRideEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());

            }
        });



    }

    private void setCompleteRidesData(ArrayList<AssignRideEnt> result) {

      if(result.size()<=0)
        {
            txtNoData.setVisibility(View.VISIBLE);
            CompletedTripsListView.setVisibility(View.GONE);
        }
        else{
            txtNoData.setVisibility(View.GONE);
          CompletedTripsListView.setVisibility(View.VISIBLE);
        }

        userCollection = new ArrayList<>();

        for(AssignRideEnt item: result){
            userCollection.add(item);
        }

        bindData(userCollection);


    }

    private void bindData(ArrayList<AssignRideEnt> userCollection) {

        adapter.clearList();
        if (CompletedTripsListView!=null)
            CompletedTripsListView.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {

      /*  CompletedTripsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             }
        });*/

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
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.Completed_Rides));
    }



}
