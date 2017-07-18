package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.DialogHelper;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyEditTextView;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created on 6/20/2017.
 */

public class ForgotPassowordFragment extends BaseFragment {

    @BindView(R.id.iv_LogoIcon)
    ImageView ivLogoIcon;
    @BindView(R.id.txtResetPass)
    AnyTextView txtResetPass;
    @BindView(R.id.iv_emailIcon)
    ImageView ivEmailIcon;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;

    public static ForgotPassowordFragment newInstance() {
        return new ForgotPassowordFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        titleBar.showBackButton();
        titleBar.setSubHeading(getResources().getString(R.string.forgot_password_heading));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validated()) {
            forgetPassword(edtEmail.getText().toString());
        }
    }

    private void forgetPassword(String email) {

        loadingStarted();
        Call<ResponseWrapper> call = webService.ForgotPassword(email);

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    final DialogHelper dialogHelper = new DialogHelper(getDockActivity());
                    dialogHelper.initForgotPasswordDialog(R.layout.dialog_reset_password, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getDockActivity().popBackStackTillEntry(0);
                            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                            dialogHelper.hideDialog();
                        }
                    }, getResources().getString(R.string.reset), getResources().getString(R.string.reset_password_message));
                    dialogHelper.setCancelable(true);
                    dialogHelper.showDialog();
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
                }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e(EditProfileFragment.class.getSimpleName(), t.toString());

            }
        });




    }

    private boolean validated() {
        if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        } else {
            return true;
        }

    }

}
