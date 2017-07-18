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
import com.app.fastcabdriver.global.SignUpFormConstant;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fastcabdriver.fragments.SignUp2Fragment.SIGNUP_MODEL;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingRidesDetailFragment extends BaseFragment implements View.OnClickListener {

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

        setListners();
        setPendingRideDetailData();
    }

    private void setPendingRideDetailData() {
        txtPickpTime.setText(entity.getRideDetail().getDate()+" "+entity.getRideDetail().getTime());
        txtPickup.setText(entity.getRideDetail().getPickupPlace());
        txtDropOff.setText(entity.getRideDetail().getDestinationPlace());
        txtEstimatedFare.setText("AED "+entity.getRideDetail().getEstimateFare());
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


}
