package com.app.fastcabdriver.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.SignUpFormConstant;
import com.app.fastcabdriver.helpers.CameraHelper;
import com.app.fastcabdriver.helpers.DatePickerHelper;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.interfaces.ImageSetter;
import com.app.fastcabdriver.ui.views.AnyEditTextView;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by saeedhyder on 7/3/2017.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener,ImageSetter {

    @BindView(R.id.CircularImageSharePop)
    CircleImageView CircularImageSharePop;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.ll_ProfileImage)
    RelativeLayout llProfileImage;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.edtUserName)
    AnyEditTextView edtUserName;
    @BindView(R.id.ll_userName)
    LinearLayout llUserName;
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
    @BindView(R.id.iv_LicenseNumber)
    ImageView ivLicenseNumber;
    @BindView(R.id.edtLicenseeNumber)
    AnyEditTextView edtLicenseeNumber;
    @BindView(R.id.ll_LicenseNumber)
    LinearLayout llLicenseNumber;
    @BindView(R.id.btn_submuit)
    Button btnSubmuit;
    @BindView(R.id.ll_SignUpFields)
    LinearLayout llSignUpFields;
    @BindView(R.id.sv_signup)
    ScrollView svSignup;

    Calendar calendar;
    int Year, Month, Day;
    private Date DateSelected;

    File profilePic;
    String profilePath;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDatePickerVariables();
        setListners();
        getMainActivity().setImageSetter(this);

    }

    private void setListners() {
        edtDateOfBirth.setOnClickListener(this);
        btnSubmuit.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

    }

    private boolean isvalidate() {

        if (edtUserName.getText() == null || (edtUserName.getText().toString().isEmpty())) {
            if (edtUserName.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtUserName.setError(getString(R.string.enter_FullName));
            return false;
        }
        else if (edtDateOfBirth.getText() == null || (edtDateOfBirth.getText().toString().isEmpty())) {
            edtDateOfBirth.setError(getString(R.string.enter_dob));
            return false;
        }
        else if (edtMobileNumber.getText().toString().isEmpty()) {
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
        }else if (edtllCurrentAddress.getText() == null || (edtllCurrentAddress.getText().toString().isEmpty())) {
            if (edtllCurrentAddress.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtllCurrentAddress.setError(getString(R.string.enter_HomeAddress));
            return false;
        }
        else if (edtLicenseeNumber.getText().toString().isEmpty()) {
            if (edtLicenseeNumber.requestFocus()) {
                getMainActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            edtLicenseeNumber.setError(getString(R.string.enter_License));
            return false;
        }else {
            return true;
        }

    }



    private void setDatePickerVariables() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
    }





    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        titleBar.setSubHeading(getResources().getString(R.string.sign_up));

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtDateOfBirth:
                initDatePicker(edtDateOfBirth);
                break;

            case R.id.btn_submuit:
                if (isvalidate()) {

                    if (profilePic == null) {
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.profile_pic_error));
                    } else {

                        SignUpFormConstant signupFormConstants = new SignUpFormConstant();
                        signupFormConstants.setFullname(edtUserName.getText().toString());
                        signupFormConstants.setDob(edtDateOfBirth.getText().toString());
                        signupFormConstants.setMobileNo(edtMobileNumber.getText().toString());
                        signupFormConstants.setHomeAddress(edtllCurrentAddress.getText().toString());
                        signupFormConstants.setLicenseNo(edtLicenseeNumber.getText().toString());
                        signupFormConstants.setProfileImage(profilePic);

                        getDockActivity().replaceDockableFragment(SignUp2Fragment.newInstance(signupFormConstants), "SignUp2Fragment");
                    }
                }
                break;

            case R.id.iv_camera:
                CameraHelper.uploadPhotoDialog(getMainActivity());
                break;

        }

    }


    @Override
    public void setImage(String imagePath) {
        if (imagePath != null) {
            //profilePic = new File(imagePath);
            profilePic = new File(imagePath);
            profilePath=imagePath;
            Picasso.with(getDockActivity())
                    .load("file:///" +imagePath)
                    .into(CircularImageSharePop);
    }
    }

    @Override
    public void setFilePath(String filePath) {

    }

    @Override
    public void setVideo(String videoPath, String VideoThumbail) {

    }
}
