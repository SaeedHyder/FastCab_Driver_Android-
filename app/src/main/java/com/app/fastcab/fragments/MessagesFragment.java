package com.app.fastcab.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.fastcab.R;
import com.app.fastcab.entities.MessagesEnt;
import com.app.fastcab.fragments.abstracts.BaseFragment;
import com.app.fastcab.ui.adapters.ArrayListAdapter;
import com.app.fastcab.ui.viewbinder.MessagesBinder;
import com.app.fastcab.ui.views.AnyTextView;
import com.app.fastcab.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 6/22/2017.
 */

public class MessagesFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_Messages)
    ListView lvMessages;

    private ArrayListAdapter<MessagesEnt> adapter;
    private ArrayList<MessagesEnt> userCollection;

    public static MessagesFragment newInstance() {
        return new MessagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<MessagesEnt>(getDockActivity(), new MessagesBinder());
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

        setMessages();
    }

    private void setMessages() {

        userCollection =new ArrayList<>();

        userCollection.add(new MessagesEnt("Please Wait","03362912002"));
        userCollection.add(new MessagesEnt("Please Wait","03362912002"));
        userCollection.add(new MessagesEnt("Please Wait","03362912002"));


        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvMessages.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvMessages.setVisibility(View.VISIBLE);
        }

        bindData(userCollection);

    }

    private void bindData(ArrayList<MessagesEnt> userCollection) {
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
        titleBar.setSubHeading(getString(R.string.Messages));
    }


}
