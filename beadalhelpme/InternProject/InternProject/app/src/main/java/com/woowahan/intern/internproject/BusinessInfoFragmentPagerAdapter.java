package com.woowahan.intern.internproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.widget.BaseAdapter;

import com.woowahan.intern.internproject.business.BusinessListFragment;
import com.woowahan.intern.internproject.imformation.BusinessInfoFragment;
import com.woowahan.intern.internproject.main.MainListFragment;
import com.woowahan.intern.internproject.menu.MenuListFragment;
import com.woowahan.intern.internproject.review.ReviewListAdapter;
import com.woowahan.intern.internproject.review.ReviewListFragment;

/**
 * Created by user on 2015. 6. 7..
 */
public class BusinessInfoFragmentPagerAdapter extends FragmentPagerAdapter{

    private final String[] TITLES = {"메뉴","정보","리뷰"};

    private SparseArray<BaseFragment> mFragments;
    private int businessId;

    public BusinessInfoFragmentPagerAdapter(FragmentManager fm, int businessId) {
        super(fm);
        mFragments = new SparseArray<>();
        this.businessId = businessId;
        // ID set

    }

    public BaseFragment getFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
        switch (position){
            case 0:
                // newInstance(id)
                MenuListFragment mlf = MenuListFragment.newInstance();
                mlf.setBusinessId(businessId);
                fragment = mlf;
                break;

            case 1:
                BusinessInfoFragment blf = BusinessInfoFragment.newInstance();
                blf.setBusinessId(businessId);
                fragment = blf;
                break;
            case 2:
                ReviewListFragment rlf = ReviewListFragment.newInstance();
                rlf.setBusinessId(businessId);
                fragment = rlf;
                break;



        }
        if(fragment != null) {
            mFragments.put(position, fragment);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
