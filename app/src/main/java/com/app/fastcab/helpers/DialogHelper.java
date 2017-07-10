package com.app.fastcab.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.app.fastcab.R;
import com.app.fastcab.ui.views.AnyTextView;


/**
 * Created on 5/24/2017.
 */

public class DialogHelper {
    private Dialog dialog;
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }
    public Dialog initForgotPasswordDialog(int layoutID ,View.OnClickListener onclicklistener,String Title,String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        TextView closeButton =(TextView) dialog.findViewById(R.id.btn_ok);
        closeButton.setOnClickListener(onclicklistener);
        AnyTextView title = (AnyTextView) dialog.findViewById(R.id.txt_title);
        title.setText(Title);
        AnyTextView Message = (AnyTextView) dialog.findViewById(R.id.txt_message);
        Message.setText(message);
        return this.dialog;
    }
    public void setTextViewText(int ID,String Text){
        AnyTextView textView = (AnyTextView) dialog.findViewById(ID);
        textView.setText(Text);
    }

    /*public Dialog initJobRefusalDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }

    public Dialog initCancelQuotationDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }
*/
    public Dialog initlogout(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initNewRide(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_accept);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btnReject);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog initendtrip(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }
 /*  public Dialog promoCode(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_cancel);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_submit);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }

    public Dialog requestForCancellation(int layoutID, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_No);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_yes);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }



    public Dialog cancelRide(int layoutID, View.OnClickListener linkclicklistener, View.OnClickListener onokclicklistener, View.OnClickListener oncancelclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        AnyTextView link = (AnyTextView)dialog.findViewById(R.id.txt_link) ;
        link.setPaintFlags(link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        link.setOnClickListener(linkclicklistener);
        Button okbutton = (Button) dialog.findViewById(R.id.btn_yes);
        okbutton.setOnClickListener(onokclicklistener);
        Button cancelbutton = (Button) dialog.findViewById(R.id.btn_No);
        cancelbutton.setOnClickListener(oncancelclicklistener);
        return this.dialog;
    }*/

/*
    public Dialog initRequestSendDialog(int layoutID, View.OnClickListener onclicklistener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        return this.dialog;
    }


    public Dialog initJobDetailDialog(int layoutID, View.OnClickListener onclicklistener, String title, String person_name,
                                      View.OnClickListener arriveclickListener, View.OnClickListener completeclickListener) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_submit);
        closeButton.setOnClickListener(onclicklistener);
        AnyTextView txttitle = (AnyTextView) dialog.findViewById(R.id.txt_problem_name);
        txttitle.setText(title);
        AnyTextView txtperson = (AnyTextView) dialog.findViewById(R.id.txt_personname);
        txtperson.setText(person_name);
        AnyTextView arrive = (AnyTextView) dialog.findViewById(R.id.txt_arrval_time);
        arrive.setOnClickListener(arriveclickListener);
        AnyTextView complete = (AnyTextView) dialog.findViewById(R.id.txt_complete_time);
        complete.setOnClickListener(completeclickListener);
        return this.dialog;
    }

    public AnyTextView getTimeTextview(int ID) {
        return (AnyTextView) dialog.findViewById(ID);

    }

    public Dialog initRatingDialog(int layoutID, View.OnClickListener onclicklistener, String title, String message) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(layoutID);
        Button closeButton = (Button) dialog.findViewById(R.id.btn_close);
        closeButton.setOnClickListener(onclicklistener);
        AnyTextView txttitle = (AnyTextView) dialog.findViewById(R.id.txtHeader);
        txttitle.setText(title);
        AnyTextView txtmessage = (AnyTextView) dialog.findViewById(R.id.notwell_tv);
        txtmessage.setText(message);
        return this.dialog;
    }

    public float getRating(int ratingBarID) {
        CustomRatingBar ratingBar = (CustomRatingBar) dialog.findViewById(ratingBarID);
        return ratingBar.getScore();
    }

    public String getEditText(int editTextID) {
        AnyEditTextView editTextView = (AnyEditTextView) dialog.findViewById(editTextID);
        KeyboardHide.hideSoftKeyboard(dialog.getContext(), editTextView);
        return editTextView.getText().toString();
    }*/

    public void showDialog(){

        dialog.show();
    }
    public void setCancelable(boolean isCancelable){
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }
    public void hideDialog(){
        dialog.dismiss();
    }
}
