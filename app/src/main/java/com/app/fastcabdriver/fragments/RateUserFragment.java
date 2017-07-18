package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.DriverFeedBackEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.global.AppConstants.DRIVER;

/**
 * Created by saeedhyder on 6/29/2017.
 */

public class RateUserFragment extends BaseFragment {

    @BindView(R.id.txtDriverName)
    AnyTextView txtDriverName;
    @BindView(R.id.txtCarNo)
    AnyTextView txtCarNo;
    @BindView(R.id.txt_pick_text)
    AnyTextView txtPickText;
    @BindView(R.id.txt_destination_text)
    AnyTextView txtDestinationText;
    @BindView(R.id.txtFareAmount)
    AnyTextView txtFareAmount;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.SubmitButton)
    Button SubmitButton;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;

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

    void RateUser(int UserId, int rideId)
    {
        loadingStarted();
        Call<ResponseWrapper<DriverFeedBackEnt>> call = webService.DriverFeedBack(UserId,12,rideId,(int)rbAddRating.getScore(),AppConstants.DRIVER);

        call.enqueue(new Callback<ResponseWrapper<DriverFeedBackEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverFeedBackEnt>> call, Response<ResponseWrapper<DriverFeedBackEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getMainActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
                }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverFeedBackEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());

            }
        });

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.rate));
    }



    @OnClick(R.id.SubmitButton)
    public void onViewClicked() {
        getDockActivity().popBackStackTillEntry(0);
        RateUser(12,16);

    }
}
