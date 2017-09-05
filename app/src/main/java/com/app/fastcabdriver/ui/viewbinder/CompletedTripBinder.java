package com.app.fastcabdriver.ui.viewbinder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.activities.DockActivity;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.fragments.CompletedRIdesDetailFragment;
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

import static com.app.fastcabdriver.R.dimen.x160;

/**
 * Created by saeedhyder on 7/4/2017.
 */

public class CompletedTripBinder extends ViewBinder<AssignRideEnt> implements DirectionFinderListener {

    ImageLoader imageLoader;
    String routes;
    DockActivity context;


    public CompletedTripBinder(DockActivity context) {
        super(R.layout.completed_trips_item);
        imageLoader = ImageLoader.getInstance();
        this.context = context;

    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    public void bindView(final AssignRideEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        final String image_map = entity.getRideDetail().getMap_image();
        viewHolder.mainFrame.setVisibility(View.GONE);
        Picasso.with(view.getContext()).load(image_map==null||image_map.trim().equals("")
                ?"asd":image_map)
                .fit().into(viewHolder.ivCompletedTrips);

        //setCompleteRideData(viewHolder,entity);


        //final String finalRoutesList = routesList;
        viewHolder.llCompleteTripDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(CompletedRIdesDetailFragment.newInstance(image_map==null||image_map.trim().equals("")
                        ?"asd":image_map,entity),CompletedRIdesDetailFragment.class.getSimpleName());

            }
        });
        viewHolder.mainFrame.setVisibility(View.VISIBLE);
        viewHolder.txtRideNo.setText(entity.getRideId().toString()+"");
        if(!entity.getRideDetail().getTotalAmount().equals("")){
            viewHolder.txtFare.setText("AED " + entity.getRideDetail().getTotalAmount());}
        else{
            viewHolder.txtFare.setText("AED 0");
        }
        viewHolder.txtTimeDate.setText(entity.getRideDetail().getDate() + " " + entity.getRideDetail().getTime());
        //setStaticMapData(view,entity);

    }

    private void setStaticMapData(View view, AssignRideEnt entity) {
        String origin = "24.839611,67.082231";
        String destination = "24.829428,67.073822";

      //  String origin = entity.getRideDetail().getPickupLatitude() + "," + entity.getRideDetail().getPickupLongitude();
       // String destination = entity.getRideDetail().getDestinationLatitude() + "," + entity.getRideDetail().getDestinationLongitude();


        String CustomMarkerOrigin = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/pickup.png";
        String CustomMarkerDestination = "http://35.160.175.165/portfolio/fast_cab/public/images/profile_images/destination.png";

        try {
            new DirectionFinder(this, origin, destination, view, entity,CustomMarkerOrigin,CustomMarkerDestination).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, View view, final String origin, final String destination, Object object, final String customMarkerOrigin, final String customMarkerDestination) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Route routesingle : route) {
            for (int i = 0; i < routesingle.points.size(); i++) {
                stringBuilder.append(routesingle.points.get(i) + "|");
            }
        }
        final AssignRideEnt entity = (AssignRideEnt)object;
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        String routesList = stringBuilder.toString();
        routesList = routesList.replaceAll("[^\\d.|,]", "");
        routesList = routesList.substring(0, routesList.length() - 1);

        Picasso.with(view.getContext()).load(getStaticMapURL(origin, destination, routesList,customMarkerOrigin,customMarkerDestination, context.getResources().getString(R.string.API_KEY)))
                .fit().into(viewHolder.ivCompletedTrips);

        setCompleteRideData(viewHolder,entity);


        final String finalRoutesList = routesList;
        viewHolder.llCompleteTripDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.replaceDockableFragment(CompletedRIdesDetailFragment.newInstance(getStaticMapURL(origin, destination, finalRoutesList,customMarkerOrigin,customMarkerDestination, context.getResources().getString(R.string.API_KEY)),entity),CompletedRIdesDetailFragment.class.getSimpleName());

            }
        });
    }

    private void setCompleteRideData(ViewHolder viewHolder, AssignRideEnt entity) {

        viewHolder.mainFrame.setVisibility(View.VISIBLE);
        viewHolder.txtRideNo.setText(entity.getRideId().toString()+"");
        if(!entity.getRideDetail().getTotalAmount().equals("")){
        viewHolder.txtFare.setText("AED " + entity.getRideDetail().getTotalAmount());}
        else{
            viewHolder.txtFare.setText("AED 0");
        }
        viewHolder.txtTimeDate.setText(entity.getRideDetail().getDate() + " " + entity.getRideDetail().getTime());
    }

    private String getStaticMapURL(String origin, String destination, String routelist,String customMarkerOrigin,String customMarkerDestination, String APIKEY) {
        return "https://maps.googleapis.com/maps/api/staticmap?visible="+routelist+"&scale=2&size=360x160&maptype=roadmap" +
                "&markers=icon:"+customMarkerOrigin+"|anchor:center|" + origin + "&markers=icon:"+customMarkerDestination+"|" + destination +
                "&path=color:0x070707FF|weight:5|" + routelist + "&key=" + APIKEY;
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
        @BindView(R.id.ll_completeTripDetail)
        LinearLayout llCompleteTripDetail;
        @BindView(R.id.mainFrame)
        LinearLayout mainFrame;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
