package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.DriverFeedBackEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.R.id.rbAddRating;
import static com.app.fastcabdriver.global.AppConstants.DRIVER;

/**
 * Created by saeedhyder on 6/29/2017.
 */

public class RateUserFragment extends BaseFragment {



    public static RateUserFragment newInstance() {
        return new RateUserFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate_user, container, false);

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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.rate));
    }


}
