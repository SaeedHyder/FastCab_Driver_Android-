package com.app.fastcab.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fastcab.R;

public class TitleBar extends RelativeLayout {

    private TextView txtTitle;
    private ImageView btnLeft;
    private ImageView btnRight;
    private RelativeLayout headerLayout;
    private ImageView btnright1;

    private OnClickListener menuButtonListener;
    private OnClickListener backButtonListener;

    private Context context;


    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    private void bindViews() {

        txtTitle = (TextView) this.findViewById(R.id.txt_subHead);
        btnRight = (ImageView) this.findViewById(R.id.btnRight);
        btnLeft = (ImageView) this.findViewById(R.id.btnLeft);
        btnright1 = (ImageView) this.findViewById(R.id.btnRight1);
        headerLayout = (RelativeLayout) this.findViewById(R.id.header_layout);

    }

    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_main, this);
        bindViews();
    }

    public void hideButtons() {
        txtTitle.setVisibility(View.GONE);
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        btnright1.setVisibility(GONE);

    }

    public void showBackButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.back_arrow);
        btnLeft.setOnClickListener(backButtonListener);

    }

    public void showBackButton(OnClickListener onClickListener) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.back_arrow);
        btnLeft.setOnClickListener(onClickListener);

    }

    public void showEditButton(OnClickListener addBtnListener) {
        btnRight.setImageResource(R.drawable.edit);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(addBtnListener);


    }

    public void showCallButton(OnClickListener callbtnlistener) {
        btnright1.setImageResource(R.drawable.tick);
        btnright1.setVisibility(View.VISIBLE);
        btnright1.setOnClickListener(callbtnlistener);
    }

    public void showMessageButton(OnClickListener addBtnListener) {
        btnRight.setImageResource(R.drawable.tick);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(addBtnListener);


    }

    public void showTickButton(OnClickListener addBtnListener) {
        btnRight.setImageResource(R.drawable.tick);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setOnClickListener(addBtnListener);


    }

    public void showMenuButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(menuButtonListener);
        btnLeft.setImageResource(R.drawable.dropdown);
    }

    public void setSubHeading(String heading) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(heading);

    }

    public void setBackgroundColor(int Color) {
        headerLayout.setBackgroundColor(Color);
    }

    public void showTitleBar() {
        this.setVisibility(View.VISIBLE);
    }

    public void hideTitleBar() {
        this.setVisibility(View.GONE);
    }

    public void setMenuButtonListener(OnClickListener listener) {
        menuButtonListener = listener;
    }

    public void setBackButtonListener(OnClickListener listener) {
        backButtonListener = listener;
    }


}
