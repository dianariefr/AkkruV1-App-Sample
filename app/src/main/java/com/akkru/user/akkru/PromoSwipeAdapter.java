package com.akkru.user.akkru;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromoSwipeAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    private List<Integer> imagesPromo = new ArrayList<Integer>() {{
        add(R.drawable.promo_one);
        add(R.drawable.promo_two);
        add(R.drawable.promo_three);
    }};

    public PromoSwipeAdapter(FragmentManager fm) {
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
        for (int imagePromo: imagesPromo) {
            PromoSwipeLayoutFragment promoSwipeLayoutFragment = new PromoSwipeLayoutFragment();
            promoSwipeLayoutFragment.imagePromoId = imagePromo;
            fragmentList.add(promoSwipeLayoutFragment);
        }
        return fragmentList;
    }

}
