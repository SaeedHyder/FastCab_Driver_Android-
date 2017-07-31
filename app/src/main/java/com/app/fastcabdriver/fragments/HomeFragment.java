package com.app.fastcabdriver.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.DriverSessionEnt;
import com.app.fastcabdriver.entities.LocationEnt;
import com.app.fastcabdriver.entities.RequestRideEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.AppConstants;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.BottomSheetDialogHelper;
import com.app.fastcabdriver.helpers.DialogHelper;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.interfaces.OnSettingActivateListener;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.CustomRatingBar;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.global.AppConstants.KEY_RIDE_CANCEL;


public class HomeFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        OnSettingActivateListener,
        DirectionFinderListener {

    public static String PENDING_RIDES_DETAIL = "pending_rides_detail";
    public static String Notification_USERID = "Notification_USERID";
    public static String Notfication_RIDEID = "Notfication_RIDEID";
    public static String IS_FROM_NOTIFICATION = "pending_rides_detail";
    private static String isPendingRideKey = "isPendingRideKey";

    @BindView(R.id.rbAddRating)
    CustomRatingBar rbAddRating;
    @BindView(R.id.rbAddRating_trip)
    CustomRatingBar rbAddRating_trip;
    @BindView(R.id.txt_driver_name_trip)
    AnyTextView txt_driver_name_trip;


    @BindView(R.id.btn_online_status)
    Button btnOnlineStatus;
    @BindView(R.id.txt_driver_name)
    AnyTextView txtDriverName;
    @BindView(R.id.txt_driver_car)
    AnyTextView txtDriverCar;
    @BindView(R.id.txt_driver_car_plate)
    AnyTextView txtDriverCarPlate;
    @BindView(R.id.txt_pick_text)
    AnyTextView PickupText;
    @BindView(R.id.txt_destination_text)
    AnyTextView DestinationText;


    @BindView(R.id.Main_frame)
    CoordinatorLayout MainFrame;
    SupportMapFragment map;
    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    @BindView(R.id.btn_location)
    CircleImageView btnLocation;

    @BindView(R.id._online_btn)
    ImageView OnlineBtn;
    @BindView(R.id.online_container)
    RelativeLayout onlineContainer;
    @BindView(R.id.rl_detail)
    RelativeLayout DetailContainer;
    @BindView(R.id.rl_trip)
    RelativeLayout TripContainer;
    @BindView(R.id.btn_trip_status)
    Button btnTripStatus;
    @BindView(R.id.ll_locationdetail)
    LinearLayout LocationDetail;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.circledriverView)
    CircleImageView circledriverView;
    Marker destinationMarker;
    Marker pickupMarker;
    String jsonString;
    private View viewParent;
    private LocationEnt origin;
    private LocationEnt destination;
    private LocationEnt pickup;
    private double longitude;
    private double latitude;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private boolean isRideinSession = false;
    private TitleBar titlebar;
    private boolean isTitleBarChange;
    private Location Mylocation;
    private LocationListener listener;
    private Marker carMarker;
    private String rideID;
    private String userID;
    private BroadcastReceiver broadcastReceiver;
    private RequestRideEnt requestRideEnt;
    private AssignRideEnt pendingRideEnt;
    private AssignRideEnt currentRideEnt;
    private String sessionState = AppConstants.KEY_SESSION_DEFAULT;
    private boolean isPendingRide = false;
    private boolean isFromNotification = false;
    private int TripStatus = R.string.start_trip;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static HomeFragment newInstance(AssignRideEnt entity, boolean isPendingRide) {

        Bundle args = new Bundle();

        args.putString(PENDING_RIDES_DETAIL, new Gson().toJson(entity));
        args.putBoolean(isPendingRideKey, isPendingRide);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static BaseFragment newInstance(String userID, String rideID, boolean isFromNotification) {
        Bundle args = new Bundle();

        args.putString(Notfication_RIDEID, rideID);
        args.putString(Notification_USERID, userID);
        args.putBoolean(IS_FROM_NOTIFICATION, isFromNotification);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonString = getArguments().getString(PENDING_RIDES_DETAIL);
            isPendingRide = getArguments().getBoolean(isPendingRideKey);
            rideID = getArguments().getString(Notfication_RIDEID);
            userID = getArguments().getString(Notification_USERID);
            isFromNotification = getArguments().getBoolean(IS_FROM_NOTIFICATION);
            if (jsonString != null)
                pendingRideEnt = new Gson().fromJson(jsonString, AssignRideEnt.class);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_home, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        /* map is already there, just return view as it is */
        }
        getMainActivity().setSettingActivateListener(this);
        if (viewParent != null)
            ButterKnife.bind(this, viewParent);

        return viewParent;
    }

    @Override
    public void onViewCreated(View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.isOnline() && !isRideinSession) {
            btnOnlineStatus.setText(R.string.go_offline);
            btnLocation.setVisibility(View.VISIBLE);
        } else {
            btnOnlineStatus.setText(R.string.go_online);
            btnLocation.setVisibility(View.GONE);
        }
        setDriverData(prefHelper.getDriver());

        getMainActivity().refreshSideMenu();

        setlistner();
        onNotificationReceived();
        if (map == null)
            initMap();

    }

    private void setlistner() {
        onlineContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void onNotificationReceived() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(AppConstants.REGISTRATION_COMPLETE)) {
                    System.out.println("registration complete");
                    System.out.println(prefHelper.getFirebase_TOKEN());

                } else if (intent.getAction().equals(AppConstants.PUSH_NOTIFICATION)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String sender_id = bundle.getString("sender_id");
                        String Type =bundle.getString("pushtype");
                        userID = bundle.getString("sender_id");
                        rideID = bundle.getString("ride_id");
                        if (Type!=null&&!Type.equals(KEY_RIDE_CANCEL)) {
                            getRequestRideDetail(rideID);
                        }
                        else if (Type!=null&&Type.equals(KEY_RIDE_CANCEL)){
                            prefHelper.setRideStatus(false);
                            prefHelper.removeRideSessionPreferences();
                            getDockActivity().popBackStackTillEntry(0);
                            getMainActivity().initFragment();
                        }
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Notification Data is Empty");
                    }
                } else if (intent.getAction().equals(AppConstants.LOCATION_RECIEVED)) {
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        String lat = bundle.getString("lat");
                        String lon = bundle.getString("lon");
                        LatLng latLng = new LatLng(Double.parseDouble(lat + ""), Double.parseDouble(lon + ""));
                        if (origin != null && !origin.getLatlng().equals(new LatLng(0, 0))) {
                            animateMarker(origin.getLatlng(), latLng, false);
                            origin.setLatlng(latLng);
                        }
                    } else {
                        //UIHelper.showShortToastInCenter(getDockActivity(), "Notification Data is Empty");
                    }
                }
            }
        };
    }

    private void getRequestRideDetail(String rideID) {
        loadingStarted();
        Call<ResponseWrapper<RequestRideEnt>> call = webService.getRequestRideDetail(rideID + "");
        call.enqueue(new Callback<ResponseWrapper<RequestRideEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<RequestRideEnt>> call, Response<ResponseWrapper<RequestRideEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    requestRideEnt = response.body().getResult();
                    setupNewRideDialog(requestRideEnt);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<RequestRideEnt>> call, Throwable t) {
                loadingFinished();
                Log.e("HomeFragment", t.toString());
            }
        });
    }

    private void initMap() {
        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        map.getMapAsync(this);


        googleApiClient = new GoogleApiClient.Builder(getMainActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)

                .addApi(LocationServices.API)
                .build();


    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        this.titlebar = titleBar;
        if (isTitleBarChange) {
            adjustTitleBar();
        } else {
            titleBar.hideButtons();
            titleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            titleBar.showMenuButton();
            titleBar.setSubHeading(getResources().getString(R.string.home));
        }
    }

    @Override
    public void onMapReady(GoogleMap googlemap) {
        googleMap = googlemap;

        //Check if From Pending Ride show Route
        if (isPendingRide) {
            userID = pendingRideEnt.getRideDetail().getUserId() + "";
            rideID = pendingRideEnt.getRideDetail().getId() + "";
            setupRideScreens(pendingRideEnt);
        }
        //Check if Application is open from Notification
        if (!isPendingRide && isFromNotification) {
            if (!prefHelper.isInSession())
                getRequestRideDetail(rideID);
        }
        //Check if Ride was in session them Resume Current Ride
        if (prefHelper.isInSession()) {
            RestoreState(prefHelper.getDriverSession());
        }

    }

    private String getCurrentAddress(double lat, double lng) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext());
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String country = addresses.get(0).getCountryName();
                return address + ", " + country;
            } else {

                return "Address Not Available";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onResume() {
        super.onResume();

        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity()
                .getWindow().getDecorView());
        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0))) {
            getMainActivity().statusCheck();
            //getCurrentLocation();
        }
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.PUSH_NOTIFICATION));
        LocalBroadcastManager.getInstance(getDockActivity()).registerReceiver(broadcastReceiver,
                new IntentFilter(AppConstants.LOCATION_RECIEVED));


    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getDockActivity()).unregisterReceiver(broadcastReceiver);
        SaveCurrentState();
        super.onPause();
        UIHelper.hideSoftKeyboard(getDockActivity(), getMainActivity()
                .getWindow().getDecorView());
    }

    private void getCurrentLocation() {

        if (googleMap != null && !isRideinSession)
            googleMap.clear();
        if (ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMainActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (Mylocation == null) {
            locationRequest.setInterval(1000);
        }
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location mlocation) {
                if (mlocation != null) {
                    Mylocation = mlocation;

                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, listener);
                    //listener = null;
                    if (Mylocation != null) {
                        //Getting longitude and latitude
                        longitude = Mylocation.getLongitude();
                        latitude = Mylocation.getLatitude();
                        // origin = new LatLng(latitude, longitude);
                        String Address = getCurrentAddress(latitude, longitude);
                        if (Address != null) {
                            //  UIHelper.showShortToastInCenter(getDockActivity(),"Seems Like a problem Try Again");
                            if (googleMap != null && !isRideinSession) {
                                googleMap.clear();
                            }
                            origin = new LocationEnt(Address, new LatLng(latitude, longitude));
                            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);
                            carMarker = googleMap.addMarker(new MarkerOptions().position(origin.getLatlng()).icon(icon));

                        } else {
                            origin = new LocationEnt("Un Named Street", new LatLng(latitude, longitude));

                        }

                        movemap(origin.getLatlng());
                        locationRequest.setNumUpdates(1);
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Can't get your Location Try getting using Location Button");
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            {
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, listener);
            }
        }

    }

    @Override
    public void onConnected(@android.support.annotation.Nullable Bundle bundle) {

        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0)))
            getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void movemap(LatLng latlng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latlng.latitude, latlng.longitude))
                .zoom(15)
                .bearing(0)
                .tilt(50)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setupNewRideDialog(final RequestRideEnt result) {

        final DialogHelper newRide = new DialogHelper(getDockActivity());
        newRide.initNewRide(R.layout.new_ride_request_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRide.hideDialog();
                AssignRideService(AppConstants.ACCEPT, AppConstants.DEFUALT);

            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRide.hideDialog();
                AssignRideService(AppConstants.REJECT, AppConstants.DEFUALT);

            }
        }, result);
        newRide.setCancelable(false);
        newRide.showDialog();
    }

    private void AssignRideService(final int rideStatus, final int tripStatus) {
        loadingStarted();
        Call<ResponseWrapper<AssignRideEnt>> call = webService.AssignStatus(prefHelper.getDriverId(), rideID, rideStatus, tripStatus, userID);

        call.enqueue(new Callback<ResponseWrapper<AssignRideEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<AssignRideEnt>> call, Response<ResponseWrapper<AssignRideEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    currentRideEnt = response.body().getResult();
                    if (rideStatus == AppConstants.ACCEPT) {
                        prefHelper.setRideStatus(true);
                        setupRideScreens(response.body().getResult());
                    } else if (tripStatus == AppConstants.START) {
                        if (pickupMarker!=null) {
                            pickupMarker.setVisible(false);
                            pickupMarker.remove();
                        }
                        TripStatus = R.string.End_Trip;
                        btnTripStatus.setText(R.string.end_trip);
                    } else if (tripStatus == AppConstants.END) {
                        setupEndtripScreens(response.body().getResult());
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<AssignRideEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(HomeFragment.class.getSimpleName(), t.toString());
            }
        });


    }

    private void setupEndtripScreens(AssignRideEnt result) {
        isTitleBarChange = false;
        destination = null;
        googleMap.clear();
        getCurrentLocation();
        isRideinSession = false;
        LocationDetail.setVisibility(View.GONE);
        TripContainer.setVisibility(View.GONE);
        DetailContainer.setVisibility(View.VISIBLE);
        TripStatus = R.string.start_trip;
        btnTripStatus.setText(getResources().getString(R.string.start_trip));
        sessionState = AppConstants.KEY_RIDE_COMPLETE_RATE;
        showRatingDialog(result);
    }

    private void setupRideScreens(AssignRideEnt rideEnt) {
        sessionState = AppConstants.KEY_SESSION_RIDE_INPROGRESS;
        if (origin == null)
            origin = new LocationEnt("Unnamed Address", new LatLng(0, 0));

        LocationDetail.setVisibility(View.VISIBLE);
        DetailContainer.setVisibility(View.GONE);
        TripContainer.setVisibility(View.VISIBLE);
        btnTripStatus.setText(TripStatus);
        isRideinSession = true;
        PickupText.setText(rideEnt.getRideDetail().getPickupAddress() + "");
        DestinationText.setText(rideEnt.getRideDetail().getDestinationAddress() + "");
        pickup = new LocationEnt(rideEnt.getRideDetail().getDestinationAddress() + "", new LatLng(Double.parseDouble(rideEnt.getRideDetail().getPickupLatitude()),
                Double.parseDouble(rideEnt.getRideDetail().getPickupLongitude())));
        destination = new LocationEnt(rideEnt.getRideDetail().getDestinationAddress() + "", new LatLng(Double.parseDouble(rideEnt.getRideDetail().getDestinationLatitude()),
                Double.parseDouble(rideEnt.getRideDetail().getDestinationLongitude())));
        setRoute();

        isTitleBarChange = true;
        adjustTitleBar();

    }

    private void adjustTitleBar() {
        titlebar.hideButtons();
        titlebar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        titlebar.showMenuButton();
        titlebar.setSubHeading(getResources().getString(R.string.home));
        titlebar.showMessageButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(MessagesFragment.newInstance(), MessagesFragment.class.getSimpleName());
            }
        });
        titlebar.showCallButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 999888555222"));
                startActivity(intent);
            }
        });
    }

    public LatLng translateCoordinates(final double distance, final LatLng origpoint, final double angle) {
        final double distanceNorth = Math.sin(angle) * distance;
        final double distanceEast = Math.cos(angle) * distance;

        final double earthRadius = 6371000;

        final double newLat = origpoint.latitude + (distanceNorth / earthRadius) * 180 / Math.PI;
        final double newLon = origpoint.longitude + (distanceEast / (earthRadius * Math.cos(newLat * 180 / Math.PI))) * 180 / Math.PI;

        return new LatLng(newLat, newLon);
    }

    private void sendRequest(String origin, String destination) {
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setRoute() {
        if (pickup != null && destination != null) {
            LatLng pick = pickup.getLatlng();
            LatLng destinat = destination.getLatlng();
            String origin_string = String.valueOf(pick.latitude) + "," + String.valueOf(pick.longitude);
            String destination_string = String.valueOf(destinat.latitude) + "," + String.valueOf(destinat.longitude);
            sendRequest(origin_string, destination_string);
        }
    }

    @Override
    public void onLocationActivateListener() {
        if (origin == null || origin.getLatlng().equals(new LatLng(0, 0)))
            getCurrentLocation();
    }

    @Override
    public void onNetworkActivateListener() {
      /*  if (origin == null || origin.getLatlng().equals(new LatLng(0, 0)))
            getCurrentLocation();*/
    }

    @OnClick({R.id.btn_online_status, R.id._online_btn, R.id.btn_location, R.id.btn_trip_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_online_status:
                if (btnOnlineStatus.getText().toString().equals(getResources().getString(R.string.go_online))) {
                    onlineContainer.setVisibility(View.VISIBLE);

                    titlebar.hideTitleBar();
                    ((ViewGroup) viewParent.getParent()).setPadding(0, (int) getResources().getDimension(R.dimen.x50_), 0, 0);
                } else {
                    btnOnlineStatus.setText(R.string.go_online);
                    DriverStatus(AppConstants.OFFLINE);
                    btnLocation.setVisibility(View.VISIBLE);
                    prefHelper.setUserStatus(false);
                }
                break;
            case R.id._online_btn:
                onlineContainer.setVisibility(View.GONE);
                btnOnlineStatus.setText(R.string.go_offline);
                DriverStatus(AppConstants.ONLINE);
                btnLocation.setVisibility(View.VISIBLE);
                prefHelper.setUserStatus(true);
                ((ViewGroup) viewParent.getParent()).setPadding(0, (int) getResources().getDimension(R.dimen.x50), 0, 0);
                titlebar.showTitleBar();
                break;
            case R.id.btn_location:
                if (getMainActivity().statusCheck())
                    getCurrentLocation();
                break;
            case R.id.btn_trip_status:
                if (btnTripStatus.getText().toString().equals(getResources().getString(R.string.start_trip))) {
                    AssignRideService(AppConstants.DEFUALT, AppConstants.START);
                    // btnTripStatus.setText(R.string.end_trip);
                    LocationDetail.setVisibility(View.GONE);
                } else {
                    //AssignRideService(AppConstants.DEFUALT, AppConstants.END);
                    setupEndRideDialog();
                }
                break;
        }
    }

    void DriverStatus(int status) {

        loadingStarted();

        Call<ResponseWrapper<DriverEnt>> call = webService.GoOnline(Integer.parseInt(prefHelper.getDriverId()), String.valueOf(status), String.valueOf(origin.getLatlng().latitude), String.valueOf(origin.getLatlng().longitude));

        call.enqueue(new Callback<ResponseWrapper<DriverEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<DriverEnt>> call, Response<ResponseWrapper<DriverEnt>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    setDriverData(response.body().getResult());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<DriverEnt>> call, Throwable t) {
                loadingFinished();
                Log.e(EditProfileFragment.class.getSimpleName(), t.toString());
            }
        });


    }

    private void setDriverData(DriverEnt result) {
        Picasso.with(getDockActivity()).load(result.getProfileImage()).into(circleImageView);
        txtDriverName.setText(result.getFullName() + "");

        Picasso.with(getDockActivity()).load(result.getProfileImage()).into(circledriverView);
        txt_driver_name_trip.setText(result.getFullName() + "");

        if (result.getVehicleDetail() != null) {
            txtDriverCar.setText(result.getVehicleDetail().getVehicleName() + "");
            txtDriverCarPlate.setText(result.getVehicleDetail().getVehicleNumber() + "");
        }


        if (result.getAverageRate() != null) {
            rbAddRating.setScore((int)(Float.parseFloat( prefHelper.getDriver().getAverageRate())));;
        } else {
            rbAddRating.setScore(0);
        }

        if (result.getAverageRate() != null) {
            rbAddRating_trip.setScore((int)(Float.parseFloat( prefHelper.getDriver().getAverageRate())));
        } else {
            rbAddRating_trip.setScore(0);
        }

    }

    private void setupEndRideDialog() {
        final DialogHelper endride = new DialogHelper(getDockActivity());
        endride.initendtrip(R.layout.endtrip_dialog, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endride.hideDialog();
                AssignRideService(AppConstants.DEFUALT, AppConstants.END);

               /* isTitleBarChange = false;
                destination = null;
                googleMap.clear();
                getCurrentLocation();
                isRideinSession = false;
                LocationDetail.setVisibility(View.GONE);
                TripContainer.setVisibility(View.GONE);
                endride.hideDialog();
                DetailContainer.setVisibility(View.VISIBLE);
                btnTripStatus.setText(getResources().getString(R.string.start_trip));
                showRatingDialog();*/
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endride.hideDialog();
            }
        });
        endride.setCancelable(false);
        endride.showDialog();
    }

    private void showRatingDialog(AssignRideEnt result) {
        titlebar.hideButtons();
        titlebar.showMenuButton();
        titlebar.setSubHeading(getResources().getString(R.string.rate));

        final BottomSheetDialogHelper ratingDialog =
                new BottomSheetDialogHelper(getDockActivity(), MainFrame, R.layout.fragment_rate_user, webService, prefHelper, 10);
        ratingDialog.initRatingDialog(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.RateUser(userID, rideID);
                ratingDialog.hideDialog();
                titlebar.hideButtons();
                titlebar.setSubHeading(getResources().getString(R.string.home));
                titlebar.showMenuButton();
                prefHelper.setRideStatus(false);
                prefHelper.removeRideSessionPreferences();
            }
        }, result);
        ratingDialog.showDialog();
    }

    @Override
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> route, View view, String origin, String destination, Object entity, String customMarkerOrigin, String customMarkerDestination) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for (Route routesingle : route) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(15);

            destinationMarker = googleMap.addMarker(new MarkerOptions().position(this.destination.getLatlng())
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.set_pickup_location_trip,
                            routesingle.duration.text, R.color.black))));
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.destination_icon_);
            pickupMarker = googleMap.addMarker(new MarkerOptions().position(this.pickup.getLatlng()).icon(icon));

            for (int i = 0; i < routesingle.points.size(); i++)
                polylineOptions.add(routesingle.points.get(i));
            //moveMap(null);
            moveRouteMap();
            polylinePaths.add(googleMap.addPolyline(polylineOptions));

        }
    }

    private void moveRouteMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin.getLatlng());
        builder.include(destination.getLatlng());
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        googleMap.animateCamera(cu, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                CameraUpdate zout = CameraUpdateFactory.zoomBy(-0.5f);
                googleMap.animateCamera(zout);
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId, String title, int ColorID) {

        View customMarkerView = ((LayoutInflater) getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.img_icon);
        TextView textView = (TextView) customMarkerView.findViewById(R.id.txt_pick_text);
        textView.setText(title);
        ImageView userImageView = (ImageView) customMarkerView.findViewById(R.id.img_userProfile);
        String userImage = "thisisdummyitwillchangewhenuserobjectisnotnull";
        if (requestRideEnt != null && requestRideEnt.getUserDetail() != null)
            userImage = requestRideEnt.getUserDetail().getProfileImage();
        else if (pendingRideEnt != null && pendingRideEnt.getRideDetail().getUserDetail() != null)
            userImage = pendingRideEnt.getRideDetail().getUserDetail().getProfileImage();
        else if (currentRideEnt != null && currentRideEnt.getRideDetail().getUserDetail() != null)
            userImage = currentRideEnt.getRideDetail().getUserDetail().getProfileImage();

        Picasso.with(getDockActivity()).load(userImage).placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                .into(userImageView);
        //textView.setTextColor(getResources().getColor(R.color.black));
        // textView.setBackgroundColor(getResources().getColor(R.color.white));
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    //This methos is used to move the marker of each car smoothly when there are any updates of their position
    public void animateMarker(final LatLng startPosition, final LatLng toPosition,
                              final boolean hideMarker) {


        /*final Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));*/


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();

        final long duration = 2500;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;


                double dLon = (toPosition.longitude - startPosition.longitude);
                double y = Math.sin(dLon) * Math.cos(toPosition.latitude);
                double x = Math.cos(startPosition.latitude) * Math.sin(toPosition.latitude) -
                        Math.sin(startPosition.latitude) * Math.cos(toPosition.latitude) * Math.cos(dLon);
                double brng = Math.toDegrees((Math.atan2(y, x)));
                brng = (360 - ((brng + 360) % 360));
                carMarker.setPosition(new LatLng(lat, lng));
                // carMarker.setRotation((float) brng);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        carMarker.setVisible(false);
                    } else {
                        carMarker.setVisible(true);
                    }
                }
            }
        });
        movemap(toPosition);
    }

    public void SaveCurrentState() {
        if (prefHelper.isInSession()) {
            DriverSessionEnt session = new DriverSessionEnt();
            session.setRequestRideEnt(requestRideEnt);
            session.setPendingRide(isPendingRide);
            session.setDestination(destination);
            session.setFromNotification(isFromNotification);
            session.setLongitude(longitude);
            session.setLatitude(latitude);
            session.setOrigin(origin);
            session.setPickup(pickup);
            session.setRideID(rideID);
            session.setUserID(userID);
            session.setRideinSession(isRideinSession);
            session.setTitleBarChange(isTitleBarChange);
            session.setPendingRideEnt(pendingRideEnt);
            session.setSessionState(sessionState);
            session.setCurrentRideEnt(currentRideEnt);
            session.setTripStatus(TripStatus);
            prefHelper.putDriverSession(session);
        }
    }

    private void RestoreState(DriverSessionEnt session) {
        if (session != null && prefHelper.isInSession()) {
            requestRideEnt = session.getRequestRideEnt();
            isPendingRide = session.isPendingRide();
            destination = session.getDestination();
            isFromNotification = session.isFromNotification();
            longitude = session.getLongitude();
            latitude = session.getLatitude();
            // session.setOrigin(origin);
            pickup = session.getPickup();
            rideID = session.getRideID();
            userID = session.getUserID();
            isRideinSession = session.isRideinSession();
            isTitleBarChange = session.isTitleBarChange();
            pendingRideEnt = session.getPendingRideEnt();
            sessionState = session.getSessionState();
            currentRideEnt = session.getCurrentRideEnt();
            TripStatus = session.getTripStatus();
            if (sessionState.equals(AppConstants.KEY_SESSION_RIDE_INPROGRESS)) {
                //Check if From Pending Ride show Route
                setupRideScreens(currentRideEnt);
            } else if (sessionState.equals(AppConstants.KEY_RIDE_COMPLETE_RATE)) {
                showRatingDialog(currentRideEnt);
            }
        }
    }


}
