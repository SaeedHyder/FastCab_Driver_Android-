package com.app.fastcab.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.fastcab.R;
import com.app.fastcab.activities.DockActivity;
import com.app.fastcab.entities.PendingTripsEnt;
import com.app.fastcab.fragments.HomeFragment;
import com.app.fastcab.fragments.PendingRidesDetailFragment;
import com.app.fastcab.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcab.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingTripBinder extends ViewBinder<PendingTripsEnt> {

    DockActivity context;

    public PendingTripBinder(DockActivity context) {
        super(R.layout.pending_rides_item);
        this.context=context;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(PendingTripsEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtPickpTime.setText(entity.getPickupTime());
        viewHolder.txtPickup.setText(entity.getPickup());
        viewHolder.txtDropOff.setText(entity.getDropOff());

        viewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(PendingRidesDetailFragment.newInstance(),PendingRidesDetailFragment.class.getSimpleName());


            }
        });

        viewHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
            }
        });

    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.txt_pickpTime)
        AnyTextView txtPickpTime;
        @BindView(R.id.ll_pickupTime)
        LinearLayout llPickupTime;
        @BindView(R.id.txt_pickup)
        AnyTextView txtPickup;
        @BindView(R.id.ll_pickup)
        LinearLayout llPickup;
        @BindView(R.id.anyTextView)
        AnyTextView anyTextView;
        @BindView(R.id.txt_dropOff)
        AnyTextView txtDropOff;
        @BindView(R.id.ll_DropOff)
        LinearLayout llDropOff;
        @BindView(R.id.btn_accept)
        Button btnAccept;
        @BindView(R.id.btn_reject)
        Button btnReject;
        @BindView(R.id.ll_pendingRidesDetail)
        LinearLayout llPendingRidesDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
