package com.app.fastcabdriver.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.DockActivity;
import com.app.fastcabdriver.entities.DriverFeedBackEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.HomeFragment;
import com.app.fastcabdriver.fragments.SettingFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.retrofit.WebService;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.ExpandedBottomSheetBehavior;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static com.app.fastcabdriver.R.id.rbAddRating;


/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
   // private BottomSheetDialog dialog;
    private NestedScrollView dialog;
    private DockActivity context;
    ExpandedBottomSheetBehavior bottomSheetBehavior;
    private WebService webService;
    BasePreferenceHelper prefHelper;
    private CoordinatorLayout mainParent;
    public BottomSheetDialogHelper(DockActivity context, CoordinatorLayout mainParent, int LayoutID,WebService webService,BasePreferenceHelper prefHelper) {
        this.context = context;
        this.mainParent = mainParent;
        this.webService=webService;
        this.prefHelper=prefHelper;
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
    public void initRatingDialog(View.OnClickListener onClickListener){
        bottomSheetBehavior.setPeekHeight((int)context.getResources().getDimension(R.dimen.x150));
        Button submit = (Button)dialog.findViewById(R.id.SubmitButton);
        submit.setOnClickListener(onClickListener);

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

    private int getRating(){
        CustomRatingBar customRatingBar = (CustomRatingBar)dialog.findViewById(R.id.rbAddRating);
        return (int)customRatingBar.getScore();

    }

    public void RateUser(int UserId, int rideId)
    {
        context.onLoadingStarted();
        Call<ResponseWrapper<DriverFeedBackEnt>> call = webService.DriverFeedBack(UserId,Integer.parseInt(prefHelper.getDriverId()),rideId,getRating(), AppConstants.USER);

        call.enqueue(new Callback<ResponseWrapper<DriverFeedBackEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverFeedBackEnt>> call, Response<ResponseWrapper<DriverFeedBackEnt>> response) {
                context.onLoadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    context.replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
                }
                else {
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

    public void showDialog(){
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
    public void setCancelable(boolean isCancelable){
        //dialog.setCancelable(isCancelable);
      //  dialog.setCanceledOnTouchOutside(isCancelable);
    }
    public void hideDialog(){
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        RemoveDialog();
       // dialog.dismiss();
    }
    private void RemoveDialog(){
        mainParent.removeView(dialog);
        // dialog.dismiss();
    }




}
