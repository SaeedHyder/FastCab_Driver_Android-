package com.app.fastcabdriver.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.DatePickerHelper;
import com.app.fastcabdriver.helpers.InternetHelper;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyEditTextView;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
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
    @BindView(R.id.iv_DateOfBirth)
    ImageView ivDateOfBirth;
    @BindView(R.id.edtDateOfBirth)
    AnyTextView edtDateOfBirth;
    @BindView(R.id.ll_DateOfBirth)
    LinearLayout llDateOfBirth;
    @BindView(R.id.iv_MobileNumber)
    ImageView ivMobileNumber;
    @BindView(R.id.edtMobileNumber)
    AnyEditTextView edtMobileNumber;
    @BindView(R.id.ll_MobileNumber)
    LinearLayout llMobileNumber;
    @BindView(R.id.iv_ll_currentAddress)
    ImageView ivLlCurrentAddress;
    @BindView(R.id.edtll_currentAddress)
    AnyEditTextView edtllCurrentAddress;
    @BindView(R.id.ll_currentAddress)
    LinearLayout llCurrentAddress;
    @BindView(R.id.ll_SignUpFields)
    LinearLayout llSignUpFields;
    @BindView(R.id.sv_signup)
    ScrollView svSignup;

    Calendar calendar;
    int Year, Month, Day;
    private Date DateSelected;

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShowDriverData();
        setDatePickerVariables();
        setListners();

    }


    private void ShowDriverData() {
        edtDateOfBirth.setText(prefHelper.getDriver().getDob());
        edtMobileNumber.setText(prefHelper.getDriver().getPhoneNo());
        edtllCurrentAddress.setText(prefHelper.getDriver().getAddress());

        if (prefHelper.getDriver().getAverageRate()==null) {
            rbAddRating.setScore(0);

        } else {rbAddRating.setScore((Float.parseFloat( prefHelper.getDriver().getAverageRate())));}


        txtRides.setText(prefHelper.getDriver().getTotalRide().toString());
        Picasso.with(getDockActivity()).load(prefHelper.getDriver().getProfileImage()).into(CircularImageSharePop);
    }

    private void UpdateDriverProfile() {

        loadingStarted();

        Call<ResponseWrapper<DriverEnt>> call = webService.UpdateDriver(Integer.parseInt(prefHelper.getDriverId()),edtMobileNumber.getText().toString(),edtllCurrentAddress.getText().toString(),edtDateOfBirth.getText().toString());
        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putDriver(response.body().getResult());
                    getDockActivity().replaceDockableFragment(DriverProfileFragment.newInstance(), DriverProfileFragment.class.getSimpleName());

                }else {
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

    private void setListners() {
        edtDateOfBirth.setOnClickListener(this);

    }

    private void setDatePickerVariables() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initDatePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerHelper datePickerHelper = new DatePickerHelper();
        datePickerHelper.initDateDialog(
                getDockActivity(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
                , new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new Date();
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
                        Date dateSpecified = c.getTime();
                        if (dateSpecified.after(date)) {
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.date_after_error));
                        } else {
                            DateSelected = dateSpecified;
                            String predate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

                            textView.setText(predate);
                            textView.setPaintFlags(Typeface.BOLD);
                        }

                    }
                }, "PreferredDate",1);

        datePickerHelper.showDate();
    }



    private boolean isvalidate() {

        if (edtDateOfBirth.getText() == null || (edtDateOfBirth.getText().toString().isEmpty())) {
            edtDateOfBirth.setError(getString(R.string.enter_dob));
            return false;
        } else if (edtMobileNumber.getText().toString().isEmpty()) {
            if (edtMobileNumber.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtMobileNumber.setError(getString(R.string.enter_phone));
            return false;
        } else if (edtMobileNumber.getText().toString().length() < 11) {
            if (edtMobileNumber.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtMobileNumber.setError(getString(R.string.numberLength));
            return false;
        } else if (edtllCurrentAddress.getText() == null || (edtllCurrentAddress.getText().toString().isEmpty())) {
            if (edtllCurrentAddress.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtllCurrentAddress.setError(getString(R.string.enter_HomeAddress));
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())){
                if (isvalidate()) {
                    UpdateDriverProfile();
                    }}
            }
        });
        titleBar.setSubHeading(getString(R.string.profile));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.edtDateOfBirth:
                initDatePicker(edtDateOfBirth);
                break;

        }
    }



}