package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.fastcab.R;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.views.TitleBar;

import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class GoOnlineFragment extends BaseFragment implements View.OnClickListener {

    public static GoOnlineFragment newInstance() {
        return new GoOnlineFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_online, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
    }

    private void setListners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();


    }
}
