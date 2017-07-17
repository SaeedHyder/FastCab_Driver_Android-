package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.DriverEnt;
import com.app.fastcab.entities.DriverMsgesEnt;
import com.app.fastcab.entities.MessagesEnt;
import com.app.fastcab.entities.ResponseWrapper;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.global.WebServiceConstants;
import com.app.fastcab.helpers.UIHelper;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.MessagesBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcab.R.id.edtDateOfBirth;
import static com.app.fastcab.R.id.edtMobileNumber;

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

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<DriverMsgesEnt>(getDockActivity(), new MessagesBinder(prefHelper));
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
