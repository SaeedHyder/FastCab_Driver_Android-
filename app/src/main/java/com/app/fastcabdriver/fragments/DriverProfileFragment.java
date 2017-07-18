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
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.InternetHelper;
import com.app.fastcabdriver.helpers.TokenUpdater;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/23/2017.
 */

public class DriverProfileFragment extends BaseFragment {

    @BindView(R.id.txtProfileName)
    AnyTextView txtProfileName;
    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.txt_ratingName)
    AnyTextView txtRatingName;
    @BindView(R.id.ll_rating)
    LinearLayout llRating;
    @BindView(R.id.txt_line)
    View txtLine;
    @BindView(R.id.txt_rides)
    AnyTextView txtRides;
    @BindView(R.id.txt_ridesName)
    AnyTextView txtRidesName;
    @BindView(R.id.ll_rides)
    LinearLayout llRides;
    @BindView(R.id.ll_rect)
    LinearLayout llRect;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.txtphone)
    AnyTextView txtphone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.iv_address)
    ImageView ivAddress;
    @BindView(R.id.txtAddress)
    AnyTextView txtAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.iv_dob)
    ImageView ivDob;
    @BindView(R.id.txtdob)
    AnyTextView txtdob;
    @BindView(R.id.ll_dob)
    LinearLayout llDob;
    @BindView(R.id.mainFrame)
    RelativeLayout mainFrame;

    public static DriverProfileFragment newInstance() {
        return new DriverProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainFrame.setVisibility(View.GONE);
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
            getDriverProfile();
        else {
            BindData(prefHelper.getDriver());
        }
    }

    private void getDriverProfile() {
        loadingStarted();
        Call<ResponseWrapper<DriverEnt>> call = webService.getProfile(Integer.parseInt(prefHelper.getDriverId()));

        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putDriver(response.body().getResult());
                    prefHelper.setDriverId(response.body().getResult().getId() + "");
                    prefHelper.setLoginStatus(true);
                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getDriverId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());
                    BindData(prefHelper.getDriver());

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(DriverProfileFragment.class.getSimpleName(), t.toString());

            }
        });
    }

    private void BindData(DriverEnt driver) {
        mainFrame.setVisibility(View.VISIBLE);
        Picasso.with(getDockActivity()).load(driver.getProfileImage()).into(CircularImageSharePop);
        txtProfileName.setText(driver.getFullName() + "");

        if (driver.getAverageRate()==null) {
            rbAddRating.setScore(0);

        } else {rbAddRating.setScore((float) driver.getAverageRate());}

        if(driver.getTotalRide()==null)
        {
            txtRides.setText("0");
        }
        else
        {
            txtRides.setText(driver.getTotalRide().toString());
        }

        txtdob.setText(driver.getDob());
        txtphone.setText(driver.getPhoneNo() + "");
        txtAddress.setText(driver.getAddress() + "");

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.showEditButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(EditProfileFragment.newInstance(), EditProfileFragment.class.getSimpleName());
            }
        });
        titleBar.showMenuButton();
        titleBar.setSubHeading(getResources().getString(R.string.profile));
    }



}
