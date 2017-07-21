package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 6/29/2017.
 */

public class RateUserFragment extends BaseFragment {


    @BindView(R.id.txtDriverName)
    AnyTextView txtDriverName;
    @BindView(R.id.txtCarNo)
    AnyTextView txtCarNo;
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
    @BindView(R.id.txtRateDriver)
    AnyTextView txtRateDriver;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;
    @BindView(R.id.ll_rateDriver)
    LinearLayout llRateDriver;
    @BindView(R.id.ll_submitRating)
    LinearLayout llSubmitRating;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.bottom_sheet)
    NestedScrollView bottomSheet;

    public static RateUserFragment newInstance() {
        return new RateUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_user, container, false);

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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.rate));
    }



}
