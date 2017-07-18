package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.fastcabdriver.fragments.PendingRidesDetailFragment.PENDING_RIDES_DETAIL;
import static com.app.fastcabdriver.fragments.SignUp2Fragment.SIGNUP_MODEL;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class CompletedRIdesDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txtDriverName)
    AnyTextView txtDriverName;
    @BindView(R.id.txt_date_time)
    AnyTextView txtDateTime;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.txt_pickup)
    AnyTextView txtPickup;
    @BindView(R.id.txt_pick_text)
    AnyTextView txtPickText;
    @BindView(R.id.imageView_destination)
    ImageView imageViewDestination;
    @BindView(R.id.txt_destination)
    AnyTextView txtDestination;
    @BindView(R.id.txt_destination_text)
    AnyTextView txtDestinationText;
    @BindView(R.id.txtFare)
    AnyTextView txtFare;
    @BindView(R.id.txtFareAmount)
    AnyTextView txtFareAmount;
    @BindView(R.id.ll_fare)
    LinearLayout llFare;
    @BindView(R.id.txt_Name)
    AnyTextView txtName;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.ll_submitRating)
    LinearLayout llSubmitRating;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;

    public static String COMPLETE_RIDES_DETAIL = "complete_rides_detail";
    public AssignRideEnt entity;

    public static CompletedRIdesDetailFragment newInstance(AssignRideEnt entity) {
        Bundle args = new Bundle();

        args.putString(COMPLETE_RIDES_DETAIL, new Gson().toJson(entity));
        CompletedRIdesDetailFragment fragment = new CompletedRIdesDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonString = getArguments().getString(COMPLETE_RIDES_DETAIL);
        if (jsonString != null)
            entity = new Gson().fromJson(jsonString, AssignRideEnt.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_trips_detail, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

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
        titleBar.showBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading("Ride 055052");
    }


}
