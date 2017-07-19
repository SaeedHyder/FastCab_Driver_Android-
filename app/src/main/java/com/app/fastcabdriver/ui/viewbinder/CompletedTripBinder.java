package com.app.fastcabdriver.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.DockActivity;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.ui.viewbinders.abstracts.ViewBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.util.List;

import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class CompletedTripBinder extends ViewBinder<AssignRideEnt> implements DirectionFinderListener  {

    ImageLoader imageLoader;
    String routes;
    DockActivity context;


    public CompletedTripBinder(DockActivity context) {
        super(R.layout.completed_trips_item);
        imageLoader = ImageLoader.getInstance();
        this.context=context;

    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }



    @Override
    public void bindView(AssignRideEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.mainFrame.setVisibility(View.GONE);


        String origin="24.839611,67.082231";
        String destination="24.829428,67.073822";


        String markerOrigin="http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/location.png";
        String markerDestination="http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/destination_icon.png";

        try {
            new DirectionFinder(this, origin, destination, viewHolder, view,entity).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }



    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, String data, View view, ViewHolder viewHolder, String origin, String destination, AssignRideEnt entity) {
        StringBuilder stringBuilder=new StringBuilder();
            routes=data;

        for (Route routesingle : route) {
             for (int i = 0; i < routesingle.points.size(); i++){
                 stringBuilder.append(routesingle.points.get(i)+"|");}


        }
        String routesList=stringBuilder.toString();
        routesList=routesList.replaceAll("[^\\d.|,]", "");
        routesList = routesList.substring(0, routesList.length() - 1);

        Picasso.with(view.getContext()).load("https://maps.googleapis.com/maps/api/staticmap?zoom=13&scale=2&size=360x160&maptype=roadmap" +
                "&markers=color:0x0E8593|"+origin+"&markers=color:0x0c3644|"+ destination+
                "&path=color:0x000000|weight:5|"+routesList+"&key="+context.getResources().getString(R.string.API_KEY))
                .fit().into(viewHolder.ivCompletedTrips);

        viewHolder.mainFrame.setVisibility(View.VISIBLE);
        viewHolder.txtRideNo.setText(entity.getRideId().toString());
        viewHolder.txtFare.setText("AED "+entity.getRideDetail().getTotalAmount());
        viewHolder.txtTimeDate.setText(entity.getRideDetail().getDate()+" "+entity.getRideDetail().getTime());
        viewHolder.txtTip.setText("AED "+entity.getRideDetail().getEstimateFare());
    }





    public static class ViewHolder extends BaseViewHolder {
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
        @BindView(R.id.mainFrame)
        LinearLayout mainFrame;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
