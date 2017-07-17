package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.app.fastcab.R;
import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.AppConstants;
import com.app.fastcab.global.SignUpFormConstant;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.InternetHelper;
import com.app.fastcab.helpers.TokenUpdater;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.TitleBar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public static String SIGNUP_MODEL = "signup_model";
    public SignUpFormConstant signupFormConstants;

    public static SignUp2Fragment newInstance(SignUpFormConstant signupFormConstants) {
        Bundle args = new Bundle();

        args.putString(SIGNUP_MODEL, new Gson().toJson(signupFormConstants));
        SignUp2Fragment fragment = new SignUp2Fragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jsonString = getArguments().getString(SIGNUP_MODEL);
        if (jsonString != null)
            signupFormConstants = new Gson().fromJson(jsonString, SignUpFormConstant.class);

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
                    if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                        DriverSignup();
                    }
                   // getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                }
        }
    }

    private void DriverSignup() {

        loadingStarted();

        MultipartBody.Part filePart;
        if (signupFormConstants.getProfileImage() != null) {
            filePart = MultipartBody.Part.createFormData("profile_picture",
                    signupFormConstants.getProfileImage().getName(), RequestBody.create(MediaType.parse("image/*"), signupFormConstants.getProfileImage()));
        } else {
            filePart = MultipartBody.Part.createFormData("profile_picture", "",
                    RequestBody.create(MediaType.parse("*/*"), ""));
        }
        Call<ResponseWrapper<DriverEnt>> call = webService.registerUser(
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getFullname()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getDob()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getMobileNo()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getHomeAddress()),
                RequestBody.create(MediaType.parse("text/plain"), signupFormConstants.getLicenseNo()),
                RequestBody.create(MediaType.parse("text/plain"), edtPrevWorkIntro.getText().toString()),
                RequestBody.create(MediaType.parse("text/plain"), edtBriefIntro.getText().toString()),
                filePart,
                RequestBody.create(MediaType.parse("text/plain"),"0.00"),
                RequestBody.create(MediaType.parse("text/plain"),"0.00")
        );

        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {

                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putDriver(response.body().getResult());
                    prefHelper.setDriverId(String.valueOf(response.body().getResult().getId()));

                    TokenUpdater.getInstance().UpdateToken(getDockActivity(),
                            prefHelper.getDriverId(),
                            AppConstants.Device_Type,
                            prefHelper.getFirebase_TOKEN());

                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(SignUp2Fragment.class.getSimpleName(), t.toString());
            }
        });


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
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        titleBar.setSubHeading(getString(R.string.sign_up));
    }


}
