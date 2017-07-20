package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingRidesDetailFragment extends BaseFragment implements View.OnClickListener, DirectionFinderListener {

    @BindView(R.id.iv_pendingRides)
    ImageView ivPendingRides;
    @BindView(R.id.txt_pickpTime)
    AnyTextView txtPickpTime;
    @BindView(R.id.ll_pickupTime)
    LinearLayout llPickupTime;
    @BindView(R.id.txt_pickup)
    AnyTextView txtPickup;
    @BindView(R.id.ll_pickup)
    LinearLayout llPickup;
    @BindView(R.id.anyTextView)
    AnyTextView anyTextView;
    @BindView(R.id.txt_dropOff)
    AnyTextView txtDropOff;
    @BindView(R.id.ll_DropOff)
    LinearLayout llDropOff;
    @BindView(R.id.txt_estimatedFare)
    AnyTextView txtEstimatedFare;
    @BindView(R.id.ll_EstimatedFare)
    LinearLayout llEstimatedFare;
    @BindView(R.id.ll_pendingRidesDetail)
    LinearLayout llPendingRidesDetail;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;

    public static String PENDING_RIDES_DETAIL = "pending_rides_detail";
    public AssignRideEnt entity;
    @BindView(R.id.mainframe)
    LinearLayout mainframe;

    public static PendingRidesDetailFragment newInstance(AssignRideEnt entity) {

        Bundle args = new Bundle();

        args.putString(PENDING_RIDES_DETAIL, new Gson().toJson(entity));
        PendingRidesDetailFragment fragment = new PendingRidesDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonString = getArguments().getString(PENDING_RIDES_DETAIL);
        if (jsonString != null)
            entity = new Gson().fromJson(jsonString, AssignRideEnt.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_rides_detail, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainframe.setVisibility(View.GONE);
        setListners();
        setStaticMapData(view, entity);

    }

    private void setPendingRideDetailData() {
        mainframe.setVisibility(View.VISIBLE);
        txtPickpTime.setText(entity.getRideDetail().getDate() + " " + entity.getRideDetail().getTime());
        txtPickup.setText(entity.getRideDetail().getPickupPlace()+"");
        txtDropOff.setText(entity.getRideDetail().getDestinationPlace()+"");
        txtEstimatedFare.setText("AED " + entity.getRideDetail().getEstimateFare()+"");
    }

    private void setStaticMapData(View view, AssignRideEnt entity) {
        // String origin = "24.839611,67.082231";
        // String destination = "24.829428,67.073822";
        String origin = entity.getRideDetail().getPickupLatitude() + "," + entity.getRideDetail().getPickupLongitude();
        String destination = entity.getRideDetail().getDestinationLatitude() + "," + entity.getRideDetail().getDestinationLongitude();

        String CustomMarkerOrigin = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/pickup.png";
        String CustomMarkerDestination = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/destination.png";

        try {
            new DirectionFinder(this, origin, destination, view, entity, CustomMarkerOrigin, CustomMarkerDestination).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, View view, String origin, String destination, Object object, String customMarkerOrigin, String customMarkerDestination) {

        StringBuilder stringBuilder = new StringBuilder();

        for (Route routesingle : route) {
            for (int i = 0; i < routesingle.points.size(); i++) {
                stringBuilder.append(routesingle.points.get(i) + "|");
            }
        }
        AssignRideEnt entity = (AssignRideEnt) object;
        String routesList = stringBuilder.toString();
        routesList = routesList.replaceAll("[^\\d.|,]", "");
        routesList = routesList.substring(0, routesList.length() - 1);

        Picasso.with(view.getContext()).load(getStaticMapURL(origin, destination, routesList, customMarkerOrigin, customMarkerDestination, getResources().getString(R.string.API_KEY)))
                .fit().into(ivPendingRides);
        setPendingRideDetailData();


    }

    private String getStaticMapURL(String origin, String destination, String routelist, String customMarkerOrigin, String customMarkerDestination, String APIKEY) {
        return "https://maps.googleapis.com/maps/api/staticmap?visible=" + routelist + "&scale=2&size=300x150&maptype=roadmap" +
                "&markers=icon:" + customMarkerOrigin + "|" + origin + "&markers=icon:" + customMarkerDestination + "|" + destination +
                "&path=color:0x070707FF|weight:5|" + routelist + "&key=" + APIKEY;
    }

    private void setListners() {

        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_accept:
                getDockActivity().replaceDockableFragment(CompletedRidesFragment.newInstance(), CompletedRidesFragment.class.getSimpleName());
                break;

            case R.id.btn_reject:
                getDockActivity().replaceDockableFragment(PendingRidesFragment.newInstance(), PendingRidesFragment.class.getSimpleName());
                break;
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


    @Override
    public void onDirectionFinderStart() {

    }



}
