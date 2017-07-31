package com.app.fastcabdriver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverMsgesEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.adapters.ArrayListAdapter;
import com.app.fastcabdriver.ui.viewbinder.MessagesBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.fragments.SignUp2Fragment.SIGNUP_MODEL;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class MessagesFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_Messages)
    ListView lvMessages;

    private ArrayListAdapter<DriverMsgesEnt> adapter;
    private ArrayList<DriverMsgesEnt> userCollection;
    public static String MOBILENO = "mobile_no";
    String MobileNo;

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    public static MessagesFragment newInstance(String userPhone) {

        Bundle args = new Bundle();
        args.putString(MOBILENO, userPhone);
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);

        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileNo = getArguments().getString(MOBILENO);
        adapter = new ArrayListAdapter<DriverMsgesEnt>(getDockActivity(), new MessagesBinder(prefHelper,MobileNo));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GetMessages();
    }

    private void GetMessages() {

        loadingStarted();

        Call<ResponseWrapper<ArrayList<DriverMsgesEnt>>> call = webService.DriverMsges();

        call.enqueue(new Callback<ResponseWrapper<ArrayList<DriverMsgesEnt>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<ArrayList<DriverMsgesEnt>>> call, Response<ResponseWrapper<ArrayList<DriverMsgesEnt>>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    setMesseges(response.body().getResult());
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
                }

            @Override
            public void onFailure(Call<ResponseWrapper<ArrayList<DriverMsgesEnt>>> call, Throwable t) {
                loadingFinished();
                Log.e(EditProfileFragment.class.getSimpleName(), t.toString());
            }
        });







    }

    private void setMesseges(ArrayList<DriverMsgesEnt> result) {

        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMessages.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMessages.setVisibility(View.VISIBLE);
        }
        userCollection =new ArrayList<>();
        for(DriverMsgesEnt item : result)
        {
        userCollection.add(item);
        }

        bindData(userCollection);
    }

    private void bindData(ArrayList<DriverMsgesEnt> userCollection) {
        adapter.clearList();
        lvMessages.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titleBar.setSubHeading(getString(R.string.Messages));
    }


}
