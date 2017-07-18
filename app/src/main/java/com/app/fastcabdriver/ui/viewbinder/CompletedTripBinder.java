package com.app.fastcabdriver.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.CompletedTripsEnt;
import com.app.fastcabdriver.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class CompletedTripBinder extends ViewBinder<AssignRideEnt> {

    ImageLoader imageLoader;

    public CompletedTripBinder() {
        super(R.layout.completed_trips_item);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(AssignRideEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtRideNo.setText(entity.getRideId().toString());
        viewHolder.txtFare.setText("AED "+entity.getRideDetail().getTotalAmount());
        viewHolder.txtTimeDate.setText(entity.getRideDetail().getDate()+" "+entity.getRideDetail().getTime());
        viewHolder.txtTip.setText("AED "+entity.getRideDetail().getEstimateFare());
        imageLoader.displayImage("drawable://"+R.drawable.trip, viewHolder.ivCompletedTrips);


    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_completedTrips)
        ImageView ivCompletedTrips;
        @BindView(R.id.txt_RideNo)
        AnyTextView txtRideNo;
        @BindView(R.id.txt_fare)
        AnyTextView txtFare;
        @BindView(R.id.txt_time_date)
        AnyTextView txtTimeDate;
        @BindView(R.id.txt_tip)
        AnyTextView txtTip;
        @BindView(R.id.ll_completeTripDetail)
        LinearLayout llCompleteTripDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
