package com.app.fastcabdriver.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.ui.views.ExpandedBottomSheetBehavior;


/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
   // private BottomSheetDialog dialog;
    private NestedScrollView dialog;
    private Context context;
    ExpandedBottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout mainParent;
    public BottomSheetDialogHelper(Context context, CoordinatorLayout mainParent, int LayoutID) {
        this.context = context;
        this.mainParent = mainParent;
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
