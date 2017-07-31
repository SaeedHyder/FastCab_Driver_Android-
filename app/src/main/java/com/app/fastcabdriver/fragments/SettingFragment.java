package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/21/2017.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.txt_notification)
    AnyTextView txtNotification;
    @BindView(R.id.ll_notificationToggel)
    LinearLayout llNotificationToggel;
    @BindView(R.id.txt_changePassword)
    AnyTextView txtChangePassword;
    @BindView(R.id.ll_ChangePassword)
    LinearLayout llChangePassword;
    @BindView(R.id.toggle_notifications)
    ToggleButton toggleNotifications;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefHelper.setRideStatus(false);
        prefHelper.removeRideSessionPreferences();
        setListners();
    }

    private void setListners() {
        toggleNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TurnNotificationStatus(isChecked);
            }
        });
        llChangePassword.setOnClickListener(this);
    }

    public void TurnNotificationStatus(boolean isChecked) {
        loadingStarted();
        Call<ResponseWrapper<DriverEnt>> call = webService.ChangeNotifiationStatus(prefHelper.getDriverId(), isChecked ? 1 : 0);
        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putDriver(response.body().getResult());
                    prefHelper.setDriverId(response.body().getResult().getId() + "");

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ChangePassword:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), ChangePasswordFragment.class.getSimpleName());

                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading("Settings");
    }



}
