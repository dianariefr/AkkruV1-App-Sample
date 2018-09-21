package com.akkru.user.akkru.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.akkru.user.akkru.CreditPagerLayout;
import com.akkru.user.akkru.R;

import java.util.ArrayList;
import java.util.List;

public class CreditPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    private List<Integer> imagesPromo = new ArrayList<Integer>() {{
        add(R.drawable.generali);
        add(R.drawable.promo_two);
        add(R.drawable.promo_three);
    }};

    public CreditPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = getFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int imagePromo : imagesPromo) {
            CreditPagerLayout creditPagerLayout = new CreditPagerLayout();
            creditPagerLayout.imagePromoId1 = imagePromo;
            fragmentList.add(creditPagerLayout);
        }
        return fragmentList;
    }
}
