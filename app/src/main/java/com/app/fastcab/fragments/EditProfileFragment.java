package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.AnyEditTextView;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.CustomRatingBar;
import com.app.fastcab.ui.views.TitleBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

        setDatePickerVariables();
        setListners();

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


    void ShowDateDialog(final AnyTextView txtView) {


        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date todayDate = null;
                        try {
                            todayDate = sdf.parse(sdf.format(new Date()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        txtView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, Year, Month, Day
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");


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
        titleBar.showTickButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isvalidate()) {
                    getDockActivity().replaceDockableFragment(DriverProfileFragment.newInstance(), DriverProfileFragment.class.getSimpleName());
                }
            }
        });
        titleBar.setSubHeading(getString(R.string.profile));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.edtDateOfBirth:
                ShowDateDialog(edtDateOfBirth);
                break;

        }
    }



}