package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.CompleteRideDataEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.R.drawable.call;
import static com.app.fastcabdriver.R.id.txt_Name;


/**
 * Created by saeedhyder on 7/4/2017.
 */

public class CompletedRIdesDetailFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txtUserName)
    AnyTextView txtUserName;
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

    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.ll_submitRating)
    LinearLayout llSubmitRating;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;

    public static String COMPLETE_RIDES_DETAIL = "complete_rides_detail";
    public static String ENTITY = "entity";
    public AssignRideEnt entity;
    @BindView(R.id.iv_staticmap)
    ImageView iv_staticmap;

    public String StaticMapString;
    @BindView(R.id.txt_Name)
    AnyTextView txtName;
    @BindView(R.id.mainFrame)
    RelativeLayout mainFrame;

    public static CompletedRIdesDetailFragment newInstance(String staticMap, AssignRideEnt entity) {
        Bundle args = new Bundle();

        args.putString(ENTITY, new Gson().toJson(entity));
        args.putString(COMPLETE_RIDES_DETAIL, staticMap);
        CompletedRIdesDetailFragment fragment = new CompletedRIdesDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StaticMapString = getArguments().getString(COMPLETE_RIDES_DETAIL);
        String jsonString = getArguments().getString(ENTITY);
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

        mainFrame.setVisibility(View.GONE);
        getCompleteRideData();
        setListners();
    }

    private void getCompleteRideData() {
        loadingStarted();
        Call<ResponseWrapper<CompleteRideDataEnt>> call = webService.CompleteRideUserDetail(entity.getRideId(),entity.getRideDetail().getUserId(), Integer.parseInt(prefHelper.getDriverId()));

        call.enqueue(new Callback<ResponseWrapper<CompleteRideDataEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<CompleteRideDataEnt>> call, Response<ResponseWrapper<CompleteRideDataEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    setCompleteRideData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<CompleteRideDataEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());

            }
        });

    }

    private void setCompleteRideData(CompleteRideDataEnt result) {

        mainFrame.setVisibility(View.VISIBLE);
        Picasso.with(getDockActivity()).load(StaticMapString).fit().into(iv_staticmap);
        Picasso.with(getDockActivity()).load(result.getUserDetail().getProfileImage()).into(CircularImageSharePop);
        txtUserName.setText(result.getUserDetail().getFullName() + "");
        txtDateTime.setText(result.getDate() + " " + result.getTime());
        txtPickText.setText(result.getPickupPlace() + " " + result.getPickupAddress());
        txtDestinationText.setText(result.getDestinationPlace() + " " + result.getDestinationAddress());
        txtName.setText(result.getUserDetail().getFullName() + "");
        if(result.getRateUser()!=null) {
            rbAddRating.setScore(result.getRateUser());
        }else{
            rbAddRating.setScore(0);
        }
        if(!result.getTotalAmount().equals("")){
            txtFareAmount.setText("AED " + result.getTotalAmount());}
        else{
            txtFareAmount.setText("AED 0");
        }


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
        titleBar.setSubHeading("Ride "+entity.getRideId());
    }



}
