package com.app.fastcabdriver.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.app.fastcabdriver.R;
import com.app.fastcabdriver.entities.DriverEnt;
import com.app.fastcabdriver.entities.NavigationEnt;
import com.app.fastcabdriver.entities.ResponseWrapper;
import com.app.fastcabdriver.fragments.abstracts.BaseFragment;
import com.app.fastcabdriver.global.WebServiceConstants;
import com.app.fastcabdriver.helpers.ClickableSpanHelper;
import com.app.fastcabdriver.helpers.DialogHelper;
import com.app.fastcabdriver.helpers.UIHelper;
import com.app.fastcabdriver.ui.adapters.ArrayListAdapter;
import com.app.fastcabdriver.ui.viewbinders.abstracts.NavigationItemBinder;
import com.app.fastcabdriver.ui.views.AnyTextView;
import com.app.fastcabdriver.ui.views.TitleBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.fastcabdriver.R.id.CircularImageSharePop;
import static com.app.fastcabdriver.R.id.img_driver;

public class SideMenuFragment extends BaseFragment {

    @BindView(img_driver)
    CircleImageView imgDriver;
    @BindView(R.id.txt_drivername)
    AnyTextView txtDrivername;
    @BindView(R.id.ll_driver)
    LinearLayout llDriver;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.sideoptions)
    ListView sideoptions;
    @BindView(R.id.line_bottom)
    View lineBottom;
    @BindView(R.id.txt_driveoption)
    AnyTextView txtDriveoption;
    private ArrayList<NavigationEnt> navigationEnts;
    private ArrayListAdapter<NavigationEnt> adapter;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayListAdapter<NavigationEnt>(getDockActivity(), new NavigationItemBinder(getDockActivity()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(prefHelper.getDriver()!=null){
        txtDrivername.setText(prefHelper.getDriver().getFullName()+"");
        Picasso.with(getDockActivity()).load(prefHelper.getDriver().getProfileImage()).into(imgDriver);}

        BindData();
        setGooglePlayShortcut(getResources().getString(R.string.ride_with_fastcab),
                getResources().getString(R.string.fastcab), txtDriveoption);


    }

    private void setGooglePlayShortcut(String text, String spanText, AnyTextView txtview) {
        SpannableStringBuilder stringBuilder = ClickableSpanHelper.initSpan(text);
        ClickableSpanHelper.setSpan(stringBuilder, text, spanText, new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.white));    // you can use custom color
                ds.setTypeface(Typeface.DEFAULT_BOLD);
                ds.setUnderlineText(false);    // this remove the underline
            }

            @Override
            public void onClick(View widget) {
                openAppORPlaystore(getResources().getString(R.string.user_app_package_name));
            }
        });

        ClickableSpanHelper.setClickableSpan(txtview, stringBuilder);
    }
    private void openAppORPlaystore(String packageName){
        Intent i;
        PackageManager manager = getDockActivity().getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage(packageName);
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {

//if not found in device then will come here
             // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            }
        }
    }

    private void bindview() {
        adapter.clearList();
        sideoptions.setAdapter(adapter);
        adapter.addAll(navigationEnts);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(sideoptions);
        sideoptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationEnts.get(position).getTitle().equals(getString(R.string.home))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(),HomeFragment.class.getSimpleName());

                }
                else if (navigationEnts.get(position).getTitle().equals(getString(R.string.Pending_Requests))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(PendingRidesFragment.newInstance(), PendingRidesFragment.class.getSimpleName());
                }
                else if (navigationEnts.get(position).getTitle().equals(getString(R.string.Completed_Rides))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(CompletedRidesFragment.newInstance(), CompletedRidesFragment.class.getSimpleName());

                }else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.profile))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(DriverProfileFragment.newInstance(), DriverProfileFragment.class.getSimpleName());
                }
                else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.settings))) {
                    getMainActivity().getResideMenu().closeMenu();
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), SettingFragment.class.getSimpleName());
                }
                else if (navigationEnts.get(position).getTitle().equals(getResources().getString(R.string.arabic_english))) {
                    UIHelper.showShortToastInCenter(getDockActivity(),"Will be Implemented in Beta Version");
                }

                else if (navigationEnts.get(position).getTitle().equals(getString(R.string.logoout))) {
                    final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                    logoutdialog.initlogout(R.layout.logout_dialog, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logoutdialog.hideDialog();
                           logoutDriver();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMainActivity().getResideMenu().closeMenu();
                            logoutdialog.hideDialog();
                        }
                    });
                    logoutdialog.setCancelable(false);
                    logoutdialog.showDialog();
                }
            }
        });
    }

    private void logoutDriver() {

        loadingStarted();
        Call<ResponseWrapper> call = webService.LogoutDriver(Integer.parseInt(prefHelper.getDriverId()));

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    getMainActivity().getResideMenu().closeMenu();
                    prefHelper.setLoginStatus(false);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), LoginFragment.class.getSimpleName());
                }
                else {
                    UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                }
                }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                Log.e(DriverProfileFragment.class.getSimpleName(), t.toString());
            }
        });


    }

    private void BindData() {
        navigationEnts = new ArrayList<>();
        navigationEnts.add(new NavigationEnt(R.drawable.home, getResources().getString(R.string.home)));
        navigationEnts.add(new NavigationEnt(R.drawable.pending, getString(R.string.Pending_Requests)));
        navigationEnts.add(new NavigationEnt(R.drawable.completedride,getString(R.string.Completed_Rides)));
        navigationEnts.add(new NavigationEnt(R.drawable.profile,getResources().getString(R.string.profile)));
        navigationEnts.add(new NavigationEnt(R.drawable.setting, getResources().getString(R.string.settings)));
        navigationEnts.add(new NavigationEnt(R.drawable.language, getResources().getString(R.string.arabic_english)));
        navigationEnts.add(new NavigationEnt(R.drawable.logout, getResources().getString(R.string.logoout)));
        bindview();

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


}
