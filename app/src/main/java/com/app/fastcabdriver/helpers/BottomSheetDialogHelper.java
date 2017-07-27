package com.app.fastcabdriver.helpers;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.DockActivity;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.DriverFeedBackEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.entities.UserRideDetailRatingEnt;
import com.app.fastcabdriver.fragments.HomeFragment;
import com.app.fastcabdriver.fragments.SettingFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.retrofit.WebService;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.ExpandedBottomSheetBehavior;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
    ExpandedBottomSheetBehavior bottomSheetBehavior;
    BasePreferenceHelper prefHelper;
    int RideId;
    // private BottomSheetDialog dialog;
    private NestedScrollView dialog;
    private DockActivity context;
    private WebService webService;
    private CoordinatorLayout mainParent;

    public BottomSheetDialogHelper(DockActivity context, CoordinatorLayout mainParent, int LayoutID, WebService webService, BasePreferenceHelper prefHelper, int RideId) {
        this.context = context;
        this.mainParent = mainParent;
        this.webService = webService;
        this.prefHelper = prefHelper;
        this.RideId = RideId;
        LayoutInflater inflater = LayoutInflater.from(context);
        dialog = (NestedScrollView) inflater.inflate(LayoutID, null, false);
        mainParent.addView(dialog);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dialog.getLayoutParams();
        params.setBehavior(new ExpandedBottomSheetBehavior());
        dialog.requestLayout();
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bottomSheetBehavior = (ExpandedBottomSheetBehavior) ExpandedBottomSheetBehavior.from(dialog);
        //  bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setAllowUserDragging(false);
        bottomSheetBehavior.setPeekHeight(0);
    }

    public void initRatingDialog(View.OnClickListener onClickListener, AssignRideEnt result) {
        bottomSheetBehavior.setPeekHeight((int) context.getResources().getDimension(R.dimen.x150));

        CircleImageView CircularImageSharePop = (CircleImageView) dialog.findViewById(R.id.CircularImageSharePop);
        AnyTextView txtDriverName = (AnyTextView) dialog.findViewById(R.id.txtDriverName);
        AnyTextView txtPickText = (AnyTextView) dialog.findViewById(R.id.txt_pick_text);
        AnyTextView txtDestinationText = (AnyTextView) dialog.findViewById(R.id.txt_destination_text);
        AnyTextView txtFareAmount = (AnyTextView) dialog.findViewById(R.id.txtFareAmount);
        RelativeLayout mainFrame = (RelativeLayout) dialog.findViewById(R.id.mainFrame);
        mainFrame.setVisibility(View.GONE);
        Button submit = (Button) dialog.findViewById(R.id.SubmitButton);
        submit.setOnClickListener(onClickListener);
        if (result.getRideDetail().getUserDetail() != null) {
            Picasso.with(context).load(result.getRideDetail().getUserDetail().getProfileImage()).into(CircularImageSharePop);
            txtDriverName.setText(result.getRideDetail().getUserDetail().getFullName() + "");
        }
        txtPickText.setText(result.getRideDetail().getPickupAddress() + "");
        txtDestinationText.setText(result.getRideDetail().getDestinationAddress() + "");
        txtFareAmount.setText(result.getRideDetail().getTotalAmount() + "");
        //UserDetail(RideId, CircularImageSharePop, txtDriverName, txtPickText, txtDestinationText, txtFareAmount, mainFrame);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private int getRating() {
        CustomRatingBar customRatingBar = (CustomRatingBar) dialog.findViewById(R.id.rbAddRating);
        return (int) customRatingBar.getScore();

    }

    public void RateUser(String UserId, final String rideId) {
        context.onLoadingStarted();
        Call<ResponseWrapper<DriverFeedBackEnt>> call = webService.DriverFeedBack(UserId, prefHelper.getDriverId(), rideId, getRating(), AppConstants.USER);

        call.enqueue(new Callback<ResponseWrapper<DriverFeedBackEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverFeedBackEnt>> call, Response<ResponseWrapper<DriverFeedBackEnt>> response) {
                context.onLoadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    context.popBackStackTillEntry(0);
                    context.replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getSimpleName());
                } else {
                    UIHelper.showShortToastInCenter(context, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverFeedBackEnt>> call, Throwable t) {
                context.onLoadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());

            }
        });
    }

    public void UserDetail(int rideId, final CircleImageView circularImageSharePop, final AnyTextView txtDriverName, final AnyTextView txtPickText, final AnyTextView txtDestinationText, final AnyTextView txtFareAmount, final RelativeLayout mainFrame) {
        context.onLoadingStarted();
        Call<ResponseWrapper<UserRideDetailRatingEnt>> call = webService.UserDetailForRating(rideId);

        call.enqueue(new Callback<ResponseWrapper<UserRideDetailRatingEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserRideDetailRatingEnt>> call, Response<ResponseWrapper<UserRideDetailRatingEnt>> response) {
                context.onLoadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    mainFrame.setVisibility(View.VISIBLE);
                    txtDriverName.setText(response.body().getResult().getUserDetail().getFullName() + "");
                    txtPickText.setText(response.body().getResult().getPickupPlace() + " " + response.body().getResult().getPickupAddress());
                    txtDestinationText.setText(response.body().getResult().getDestinationPlace() + " " + response.body().getResult().getDestinationAddress());

                    Picasso.with(context).load(response.body().getResult().getUserDetail().getProfileImage()).into(circularImageSharePop);
                    if (!response.body().getResult().getTotalAmount().equals("")) {
                        txtFareAmount.setText("AED " + response.body().getResult().getTotalAmount());
                    } else {
                        txtFareAmount.setText("AED 0");
                    }

                } else {
                    UIHelper.showShortToastInCenter(context, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserRideDetailRatingEnt>> call, Throwable t) {
                context.onLoadingFinished();
                Log.e(SettingFragment.class.getSimpleName(), t.toString());
            }
        });

    }

    public void showDialog() {
        // setupRideNowDialog();
// init the bottom sheet behavior


// change the state of the bottom sheet
        //  bottomSheetBehavior.setAllowUserDragging(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

// set the peek height
        bottomSheetBehavior.setPeekHeight((int) context.getResources().getDimension(R.dimen.x100));

// set hideable or not
        bottomSheetBehavior.setHideable(false);

        //dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        //dialog.setCancelable(isCancelable);
        //  dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        RemoveDialog();
        // dialog.dismiss();
    }

    private void RemoveDialog() {
        mainParent.removeView(dialog);
        // dialog.dismiss();
    }


}
