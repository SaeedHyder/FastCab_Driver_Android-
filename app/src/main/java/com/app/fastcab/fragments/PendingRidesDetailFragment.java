package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public static PendingRidesDetailFragment newInstance() {
        return new PendingRidesDetailFragment();
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
