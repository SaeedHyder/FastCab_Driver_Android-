package com.app.fastcab.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.helpers.ClickableSpanHelper;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment implements OnClickListener {

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


    @OnClick({R.id.loginButton, R.id.txtResetPass, R.id.ll_loginfacebook})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                if (isvalidated()) {
                    prefHelper.setLoginStatus(true);
                    //Intent intent=new Intent(getMainActivity(), MapsActivity.class);
                    //startActivity(intent);

                    getDockActivity().popBackStackTillEntry(0);
                    // getDockActivity().replaceDockableFragment(HomeMapFragment.newInstance(), HomeMapFragment.class.getSimpleName());
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());


                }
                break;
            case R.id.txtResetPass:
                  getDockActivity().replaceDockableFragment(ForgotPassowordFragment.newInstance(), "ForgotPassowordFragment");
                break;
            case R.id.ll_loginfacebook:
                UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in Beta");
                break;
        }
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


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.loginButton:
                prefHelper.setLoginStatus(true);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragmnet");
                break;
        }
    }


}
