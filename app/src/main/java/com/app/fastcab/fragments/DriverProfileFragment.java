package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;

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
        Picasso.with(getDockActivity()).load(driver.getProfile_image()).into(CircularImageSharePop);
        txtProfileName.setText(driver.getFull_name() + "");
        rbAddRating.setScore(Float.parseFloat((driver.getAverage_rate())));
        txtRides.setText(driver.getTotal_ride());
        txtdob.setText(driver.getDob());
        txtphone.setText(driver.getPhone_no() + "");
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
