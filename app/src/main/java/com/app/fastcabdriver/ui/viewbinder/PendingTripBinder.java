package com.app.fastcabdriver.ui.viewbinder;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.DockActivity;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.HomeFragment;
import com.app.fastcabdriver.fragments.PendingRidesDetailFragment;
import com.app.fastcabdriver.fragments.SettingFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.retrofit.WebService;
import com.app.fastcabdriver.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class PendingTripBinder extends ViewBinder<AssignRideEnt> {

   private DockActivity context;
    private WebService webService;

    public PendingTripBinder(DockActivity context, WebService webService) {
        super(R.layout.pending_rides_item);
        this.context=context;
        this.webService = webService;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final AssignRideEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtPickpTime.setText(entity.getRideDetail().getTime());
        viewHolder.txtPickup.setText(entity.getRideDetail().getPickupPlace());
        viewHolder.txtDropOff.setText(entity.getRideDetail().getDestinationPlace());

        viewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(PendingRidesDetailFragment.newInstance(entity),PendingRidesDetailFragment.class.getSimpleName());


            }
        });

        viewHolder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AssignRideService(entity.getDriverDetail().getId(),entity.getRideDetail().getId(),
                       AppConstants.REJECT,AppConstants.DEFUALT,entity.getRideDetail().getUserId());
            }
        });

    }
    private void AssignRideService(Integer driverID, Integer rideID, int REJECT, int DEFUALT, Integer userId) {
        Call<ResponseWrapper<AssignRideEnt>> call = webService.AssignStatus(driverID+"", rideID+"", REJECT, DEFUALT, userId+"");

        call.enqueue(new Callback<ResponseWrapper<AssignRideEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<AssignRideEnt>> call, Response<ResponseWrapper<AssignRideEnt>> response) {

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                        context.popBackStackTillEntry(0);
                        context.replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());
                } else {
                    UIHelper.showShortToastInCenter(context, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<AssignRideEnt>> call, Throwable t) {

                Log.e(PendingTripBinder.class.getSimpleName(), t.toString());
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
