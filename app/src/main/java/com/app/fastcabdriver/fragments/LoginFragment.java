package com.app.fastcabdriver.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.ClickableSpanHelper;
import com.app.fastcabdriver.helpers.InternetHelper;
import com.app.fastcabdriver.helpers.TokenUpdater;
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


public class LoginFragment extends BaseFragment  {

    @BindView(R.id.iv_LogoIcon)
    ImageView ivLogoIcon;

    @BindView(R.id.iv_passwordIcon)
    ImageView ivPasswordIcon;

    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.txtResetPass)
    AnyTextView txtResetPass;
    @BindView(R.id.txtSignup)
    AnyTextView txtSignup;
    @BindView(R.id.iv_facebookIcon)
    ImageView ivFacebookIcon;
    @BindView(R.id.txtFacebookLogin)
    AnyTextView txtFacebookLogin;
    @BindView(R.id.ll_loginfacebook)
    RelativeLayout llLoginfacebook;
    @BindView(R.id.scrollview_bigdaddy)
    RelativeLayout scrollviewBigdaddy;
    @BindView(R.id.iv_EmailIcon)
    ImageView ivEmailIcon;

    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.edtEmail)
    AnyEditTextView edtEmail;
    @BindView(R.id.edtpassword)
    AnyEditTextView edtpassword;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setSignupSpan(getResources().getString(R.string.new_user_create_account), getString(R.string.create_account), txtSignup);
        return view;

    }

    private void setSignupSpan(String text, String spanText, AnyTextView txtview) {
        SpannableStringBuilder stringBuilder = ClickableSpanHelper.initSpan(text);
        ClickableSpanHelper.setSpan(stringBuilder, text, spanText, new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));    // you can use custom color
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
                 getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(), SignUpFragment.class.getSimpleName());
            }
        });
        ClickableSpanHelper.setColor(stringBuilder, text, getResources().getString(R.string.new_user_text), "#e6ffffff");

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }


    @OnClick({R.id.loginButton, R.id.txtResetPass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                    if (isvalidated()) {
                        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())){
                            MakeDriverLogin();}

                    }
                    break;
            case R.id.txtResetPass:
                  getDockActivity().replaceDockableFragment(ForgotPassowordFragment.newInstance(), "ForgotPassowordFragment");
                break;

        }
    }

    private void MakeDriverLogin() {
        loadingStarted();
        Call<ResponseWrapper<DriverEnt>> call = webService.loginDriver(edtEmail.getText().toString(),edtpassword.getText().toString());
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
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());

                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(LoginFragment.class.getSimpleName(), t.toString());
            }
        });

    }

    private boolean isvalidated() {
        if (edtpassword.getText().toString().isEmpty()) {
            edtpassword.setError(getString(R.string.enter_password));
            return false;
        } else if (edtpassword.getText().toString().length() < 6) {
            edtpassword.setError(getString(R.string.enter_valid_password));
            return false;
        } else if (edtEmail.getText() == null || (edtEmail.getText().toString().isEmpty()) ||
                (!Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches())) {
            edtEmail.setError(getString(R.string.valid_email));
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }





}
