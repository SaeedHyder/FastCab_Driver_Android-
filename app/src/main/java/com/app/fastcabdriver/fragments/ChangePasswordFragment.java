package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyEditTextView;
import com.app.fastcabdriver.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_LogoIcon)
    ImageView ivLogoIcon;
    @BindView(R.id.iv_currentPasswordIcon)
    ImageView ivCurrentPasswordIcon;
    @BindView(R.id.edtcurrentPassword)
    AnyEditTextView edtcurrentPassword;
    @BindView(R.id.ll_currentPassword)
    LinearLayout llCurrentPassword;
    @BindView(R.id.iv_NewPasswordIcon)
    ImageView ivNewPasswordIcon;
    @BindView(R.id.editNewPassword)
    AnyEditTextView editNewPassword;
    @BindView(R.id.ll_NewPassword)
    LinearLayout llNewPassword;
    @BindView(R.id.iv_ConfirmPasswordIcon)
    ImageView ivConfirmPasswordIcon;
    @BindView(R.id.editConfirmPassword)
    AnyEditTextView editConfirmPassword;
    @BindView(R.id.ll_ConfirmPassword)
    LinearLayout llConfirmPassword;
    @BindView(R.id.UpdateButton)
    Button UpdateButton;
    @BindView(R.id.togglePassword)
    ToggleButton togglePassword;
    @BindView(R.id.togglePassword1)
    ToggleButton togglePassword1;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {
        UpdateButton.setOnClickListener(this);
        togglePassword.setOnClickListener(this);
        togglePassword1.setOnClickListener(this);

    }

    void changePassword(String currentPassword, String NewPassword, String ConfirmPassword){

        loadingStarted();
        Call<ResponseWrapper<DriverEnt>> call = webService.ChangePassword(currentPassword,NewPassword,ConfirmPassword,prefHelper.getDriverId());

        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(EditProfileFragment.class.getSimpleName(), t.toString());
            }
        });


    }

    private boolean isvalidate() {
        if (edtcurrentPassword.getText() == null || (edtcurrentPassword.getText().toString().isEmpty())) {
            edtcurrentPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (editNewPassword.getText() == null || (editNewPassword.getText().toString().isEmpty())) {
            editNewPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (editNewPassword.getText().toString().length() < 8) {
            editNewPassword.setError(getString(R.string.passwordLength));
            return false;
        } else if (editConfirmPassword.getText() == null || (editConfirmPassword.getText().toString().isEmpty()) || editConfirmPassword.getText().toString().length() < 6) {
            editConfirmPassword.setError(getString(R.string.enter_password));
            return false;
        } else if (!editConfirmPassword.getText().toString().equals(editNewPassword.getText().toString())) {
            editConfirmPassword.setError("confirm password does not match");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.UpdateButton:
                if (isvalidate()) {
                    changePassword(edtcurrentPassword.getText().toString(),editNewPassword.getText().toString(),editConfirmPassword.getText().toString());
                }
                break;
            case R.id.togglePassword:

                boolean isCheck = togglePassword.isChecked();

                if (isCheck) {
                    editNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editNewPassword.setSelection(editNewPassword.getText().length());
                } else {
                    editNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editNewPassword.setSelection(editNewPassword.getText().length());
                }


                break;

            case R.id.togglePassword1:

                boolean isCheck1 = togglePassword1.isChecked();

                if (isCheck1) {
                    editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editConfirmPassword.setSelection(editConfirmPassword.getText().length());
                } else {
                    editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editConfirmPassword.setSelection(editConfirmPassword.getText().length());
                }


                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        titleBar.setSubHeading(getString(R.string.Change_Password));
    }



}
