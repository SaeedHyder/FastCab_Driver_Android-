package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.fastcab.R.id.edtDateOfBirth;
import static com.app.fastcab.R.id.edtLicenseeNumber;
import static com.app.fastcab.R.id.edtMobileNumber;
import static com.app.fastcab.R.id.edtUserName;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class SignUp2Fragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.edtBriefIntro)
    AnyEditTextView edtBriefIntro;
    @BindView(R.id.edtPrevWorkIntro)
    AnyEditTextView edtPrevWorkIntro;
    @BindView(R.id.btn_submuit)
    Button btnSubmuit;

    public static SignUp2Fragment newInstance() {
        return new SignUp2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup2, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

        btnSubmuit.setOnClickListener(this);

        edtBriefIntro.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        edtPrevWorkIntro.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_submuit:
                if(isvalidate()) {
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                }
        }
    }

    private boolean isvalidate() {

        if (edtBriefIntro.getText() == null || (edtBriefIntro.getText().toString().isEmpty())) {
            if (edtBriefIntro.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtBriefIntro.setError(getString(R.string.enter_Brief_Introduction));
            return false;
        }
       else if (edtPrevWorkIntro.getText() == null || (edtPrevWorkIntro.getText().toString().isEmpty())) {
            if (edtPrevWorkIntro.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtPrevWorkIntro.setError(getString(R.string.enter_Previous_word));
            return false;
        }
        else {
            return true;
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.sign_up));
    }


}
