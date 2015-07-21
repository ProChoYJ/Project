package com.woowahan.intern.internproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.woowahan.intern.internproject.business.BusinessListFragment;
import com.woowahan.intern.internproject.main.MainListFragment;

/**
 * Created by user on 2015. 6. 7..
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter{

    private final String[] TITLES = {"홈", "치킨","중국집","피자", "한식/분식", "족발/보쌈", "야식", "찜/탕", "회/일식/돈까스", "도시락" ,"패스트푸드", "기타"};//{ "Widget", "SystemInfo", "File", "Peoples" };

    private SparseArray<BaseFragment> mFragments;
    private int mainpos;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new SparseArray<>();
        // ID set

    }

    public BaseFragment getFragment(int position){
        return mFragments.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
//        mainpos = position;
        switch (position){
            case 0:
                // newInstance(id)
                MainListFragment mlf = MainListFragment.newInstance();
                mlf.setCategoryNum(0);
                fragment = mlf;
                break;

            case 1:
                BusinessListFragment blf = BusinessListFragment.newInstance();
                blf.setBusinessId(1);
                blf.setCategoryName(TITLES[1]);
                fragment = blf;
                break;
            case 2:
                BusinessListFragment cblf = BusinessListFragment.newInstance();
                cblf.setBusinessId(2);
                fragment = cblf;
                break;
            case 3:
                BusinessListFragment pblf = BusinessListFragment.newInstance();
                pblf.setBusinessId(3);
                fragment = pblf;
                break;
            case 4:
                BusinessListFragment hblf = BusinessListFragment.newInstance();
                hblf.setBusinessId(4);
                fragment = hblf;
                break;
            case 5:
                BusinessListFragment jblf = BusinessListFragment.newInstance();
                jblf.setBusinessId(5);
                fragment = jblf;
                break;
            case 6:
                BusinessListFragment yblf = BusinessListFragment.newInstance();
                yblf.setBusinessId(6);
                fragment = yblf;
                break;
            case 7:
                BusinessListFragment zblf = BusinessListFragment.newInstance();
                zblf.setBusinessId(7);
                fragment = zblf;
                break;
            case 8:
                BusinessListFragment wblf = BusinessListFragment.newInstance();
                wblf.setBusinessId(8);
                fragment = wblf;
                break;
            case 9:
                BusinessListFragment dblf = BusinessListFragment.newInstance();
                dblf.setBusinessId(9);
                fragment = dblf;
                break;
            case 10:
                BusinessListFragment fblf = BusinessListFragment.newInstance();
                fblf.setBusinessId(10);
                fragment = fblf;
                break;
            case 11:
                BusinessListFragment gblf = BusinessListFragment.newInstance();
                gblf.setBusinessId(11);
                fragment = gblf;
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

    //// pos getter setter

    public int getMainpos() {
        return mainpos;
    }

    public void setMainpos(int mainpos) {
        this.mainpos = mainpos;
    }
}
