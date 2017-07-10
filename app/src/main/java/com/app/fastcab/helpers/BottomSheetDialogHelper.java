package com.app.fastcab.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.ui.views.AnyTextView;

import java.util.Date;
import java.util.List;


/**
 * Created on 6/30/2017.
 */

public class BottomSheetDialogHelper {
   // private BottomSheetDialog dialog;
    private LinearLayout dialog;
    private Context context;
    BottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout mainParent;
    public BottomSheetDialogHelper(Context context, CoordinatorLayout mainParent, int LayoutID) {
        this.context = context;
        this.mainParent = mainParent;
        LayoutInflater inflater = LayoutInflater.from(context);
        dialog = (LinearLayout) inflater.inflate(LayoutID, null, false);
        mainParent.addView(dialog);
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) dialog.getLayoutParams();
        params.setBehavior(new BottomSheetBehavior());
        dialog.requestLayout();
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // setupRideNowDialog();
// init the bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from(dialog);

// change the state of the bottom sheet
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
        bottomSheetBehavior.setPeekHeight((int)context.getResources().getDimension(R.dimen.x100));

// set hideable or not
        bottomSheetBehavior.setHideable(false);
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
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
