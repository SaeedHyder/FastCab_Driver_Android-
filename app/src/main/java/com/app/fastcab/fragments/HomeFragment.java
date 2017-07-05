package com.app.fastcab.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.DialogHelper;
import com.app.fastcab.ui.views.TitleBar;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.Message)
    Button Message;
    @BindView(R.id.goOnline)
    Button goOnline;
    @BindView(R.id.endTip)
    Button endTip;
    @BindView(R.id.driverProfile)
    Button driverProfile;
    @BindView(R.id.EditdriverProfile)
    Button EditdriverProfile;
    @BindView(R.id.changePassword)
    Button changePassword;
    @BindView(R.id.completedTrips)
    Button completedTrips;
    @BindView(R.id.completedDetailTrips)
    Button completedDetailTrips;
    @BindView(R.id.pendingTrips)
    Button pendingTrips;
    @BindView(R.id.pendingDetailTrips)
    Button pendingDetailTrips;
    @BindView(R.id.newRideRequest)
    Button newRideRequest;
    Unbinder unbinder;
    @BindView(R.id.rateUsRequest)
    Button rateUsRequest;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

        Message.setOnClickListener(this);
        goOnline.setOnClickListener(this);
        endTip.setOnClickListener(this);
        driverProfile.setOnClickListener(this);
        EditdriverProfile.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        completedTrips.setOnClickListener(this);
        completedDetailTrips.setOnClickListener(this);
        pendingTrips.setOnClickListener(this);
        pendingDetailTrips.setOnClickListener(this);
        newRideRequest.setOnClickListener(this);
        rateUsRequest.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading("Home");

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Message:
                getDockActivity().replaceDockableFragment(MessagesFragment.newInstance(), "MessagesFragment");
                break;
            case R.id.goOnline:
                getDockActivity().replaceDockableFragment(GoOnlineFragment.newInstance(), "GoOnlineFragment");
                break;
            case R.id.endTip:
                final DialogHelper EndTrip = new DialogHelper(getDockActivity());
                EndTrip.endtrip(R.layout.endtrip_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.setLoginStatus(false);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EndTrip.hideDialog();
                    }
                });
                EndTrip.setCancelable(false);
                EndTrip.showDialog();
                break;
            case R.id.driverProfile:
                getDockActivity().replaceDockableFragment(DriverProfileFragment.newInstance(), "DriverProfileFragment");
                break;
            case R.id.EditdriverProfile:
                getDockActivity().replaceDockableFragment(EditProfileFragment.newInstance(), "EditProfileFragment");
                break;
            case R.id.changePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
            case R.id.completedTrips:
                getDockActivity().replaceDockableFragment(CompletedRidesFragment.newInstance(), "CompletedRidesFragment");
                break;
            case R.id.completedDetailTrips:
                getDockActivity().replaceDockableFragment(CompletedRIdesDetailFragment.newInstance(), "CompletedRIdesDetailFragment");
                break;
            case R.id.pendingTrips:
                getDockActivity().replaceDockableFragment(PendingRidesFragment.newInstance(), "PendingRidesFragment");
                break;
            case R.id.pendingDetailTrips:
                getDockActivity().replaceDockableFragment(PendingRidesDetailFragment.newInstance(), "PendingRidesDetailFragment");
                break;
            case R.id.newRideRequest:
                final DialogHelper newRide = new DialogHelper(getDockActivity());
                newRide.NewRide(R.layout.new_ride_request_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prefHelper.setLoginStatus(false);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newRide.hideDialog();
                    }
                });
                newRide.setCancelable(false);
                newRide.showDialog();

                break;
            case R.id.rateUsRequest:
                getDockActivity().replaceDockableFragment(RateUserFragment.newInstance(), "RateUserFragment");
                break;

        }
    }
}
