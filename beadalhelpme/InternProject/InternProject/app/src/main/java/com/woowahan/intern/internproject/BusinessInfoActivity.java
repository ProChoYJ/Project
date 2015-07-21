package com.woowahan.intern.internproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by user on 2015. 6. 7..
 */
public class BusinessInfoActivity extends AppCompatActivity{

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private BusinessInfoFragmentPagerAdapter mPagerAdapter;
    private Toast toast;
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);
//        setTitle("맛있는 가게");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        payBtn = (Button) findViewById(R.id.payment_btn);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/nanum.ttf");

        Intent it = getIntent();
        Log.d("status : " , "" + it.getExtras().getInt("Status"));
        ///// status check
        if(it.getExtras().getInt("Status") == 3) {
            toast = Toast.makeText(this.getApplicationContext(), "사장님이 잠시 외출중입니다.",
                    Toast.LENGTH_SHORT);

            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(25);

            toast.show();

//            finish();

        }


        Log.d("test", "intent data:" + it.getExtras().getInt("Id"));
        setTitle(it.getExtras().getString("Name"));
        // construcor set intent data -> ID
        mPagerAdapter = new BusinessInfoFragmentPagerAdapter(getSupportFragmentManager(),it.getExtras().getInt("Id"));

        mViewPager = (ViewPager) findViewById(R.id.info_viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.info_tabs);

        // tab full
        mPagerSlidingTabStrip.setShouldExpand(true);
        mPagerSlidingTabStrip.setTextSize(40);
        mPagerSlidingTabStrip.setTypeface(typeFace, R.id.tabs);
        mPagerSlidingTabStrip.setTextColor(Color.BLACK);
        mPagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mPagerSlidingTabStrip.setIndicatorColor(Color.parseColor("#E91E63"));
        mPagerSlidingTabStrip.setViewPager(mViewPager);


    }


    ////// font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /////// menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ///////// back event
    @Override
    public void onBackPressed() {

        BaseFragment curFragment = mPagerAdapter.getFragment(mViewPager.getCurrentItem());
        if(curFragment != null && curFragment.onBackPressed()){
            return ;
        }

        super.onBackPressed();
    }

}
