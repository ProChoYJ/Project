package com.woowahan.intern.internproject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private MainFragmentPagerAdapter mPagerAdapter;
    private Toast toast;
    private long backKeyPressedTime = 0;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, SplashActivity.class));

        setTitle("배달 헬미");

        // construcor set intent data -> ID
        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/nanum.ttf");
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);


        // tab full
        //mPagerSlidingTabStrip.setShouldExpand(true);
        mPagerSlidingTabStrip.setTextSize(40);
        mPagerSlidingTabStrip.setTextColor(Color.BLACK);
        mPagerSlidingTabStrip.setIndicatorColor(Color.parseColor("#E91E63"));
        mPagerSlidingTabStrip.setDividerColor(Color.parseColor("#FFFFFF"));
        mPagerSlidingTabStrip.setTypeface(typeFace, R.id.tabs);
        mPagerSlidingTabStrip.setUnderlineColor(Color.parseColor("#FFFFFF"));
        mPagerSlidingTabStrip.setViewPager(mViewPager);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.dojang2);

        String context = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(context);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            alterCheckGPS();
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


///////// menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//

        return super.onOptionsItemSelected(item);
    }

    ///////// back event
    @Override
    public void onBackPressed() {

        BaseFragment curFragment = mPagerAdapter.getFragment(mViewPager.getCurrentItem());
        if(curFragment != null && cbackKeyPressedTimeurFragment.onBackPressed()){
            return ;
        }

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed();
            toast.cancel();
        }

        super.onBackPressed();
    }


    ///////// setfragment
    public void setCurrentItem(int pagerIndex) {
        mViewPager.setCurrentItem(pagerIndex);
    }



    ////////////// backpress toast

    private void showGuide() {
        toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);


        LinearLayout linearLayout = (LinearLayout) toast.getView();
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView messageTextView = (TextView) linearLayout.getChildAt(0);
        messageTextView.setTextSize(20);

        toast.show();
    }

    /////// gps state

    private void alterCheckGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("GPS가 꺼져 있습니가. GPS를 사용하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("사용", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveConfigGPS();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void moveConfigGPS(){
        Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(it);
    }

}
