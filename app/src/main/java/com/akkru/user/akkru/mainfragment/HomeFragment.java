package com.akkru.user.akkru.mainfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akkru.user.akkru.CallbackListener;
import com.akkru.user.akkru.CatBarFragment;
import com.akkru.user.akkru.CatCafeFragment;
import com.akkru.user.akkru.CatRecommendationFragment;
import com.akkru.user.akkru.PromoSwipeAdapter;
import com.akkru.user.akkru.R;
import com.akkru.user.akkru.RecommendationAdapter;
import com.akkru.user.akkru.RecommendationModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    private RecyclerView recyclerView;
    private RecommendationAdapter recommendationAdapter;
    private List<RecommendationModel> recommendationModelList;
    private TextView seeAll;
    private ImageView ivBar, ivCafe, ivResto;
    private CallbackListener callbackListener;
    private SwipeRefreshLayout swipeHome;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeHome = view.findViewById(R.id.swipe_home);
        seeAll = view.findViewById(R.id.see_all);
        ivBar = view.findViewById(R.id.iv_category_bar);
        ivCafe = view.findViewById(R.id.iv_category_cafe);
        ivResto = view.findViewById(R.id.iv_category_resto);

        ViewPager viewPager = view.findViewById(R.id.view_pager_promo);
        viewPager.setAdapter(new PromoSwipeAdapter(getFragmentManager()));

        recyclerView = view.findViewById(R.id.rv_cafe_recommendation);
        recommendationModelList = new ArrayList<>();
        recommendationAdapter = new RecommendationAdapter(getContext(),recommendationModelList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recommendationAdapter);

        hideBackButton();
        refresh();
        prepareRecommendationCafeData();

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()){
                    case R.id.see_all:
                        fragment = new CatRecommendationFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack("HomeFragment");
                        transaction.commit();

                }
            }
        });

        ivCafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()){
                    case R.id.iv_category_cafe:
                        fragment = new CatCafeFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                }
            }
        });

        ivBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()){
                    case R.id.iv_category_bar:
                        fragment = new CatBarFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                }
            }
        });

        ivResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                switch (v.getId()){
                    case R.id.iv_category_resto:
                        fragment = new CatRecommendationFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof CallbackListener)
            callbackListener = (CallbackListener) getActivity();
        callbackListener.onCallBack("Home");
    }

    public void hideBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void refresh() {
        swipeHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeHome.setRefreshing(true);
                swipeHome.setRefreshing(false);
            }
        });
    }

    private void prepareRecommendationCafeData() {
        int[] cafeImages = new int[]{
                R.drawable.cafe_one,
                R.drawable.cafe_two,
                R.drawable.cafe_three,
                R.drawable.cafe_four,
                R.drawable.cafe_five
        };
        RecommendationModel a;

        a = new RecommendationModel(cafeImages[0], "Cafe Mahal", "Condong Catur","Open" );
        recommendationModelList.add(a);
        a = new RecommendationModel(cafeImages[1], "Cafe Mahal", "Condong Catur","Open" );
        recommendationModelList.add(a);
        a = new RecommendationModel(cafeImages[2], "Cafe Mahal", "Condong Catur","Open" );
        recommendationModelList.add(a);
        a = new RecommendationModel(cafeImages[3], "Cafe Mahal", "Condong Catur","Open" );
        recommendationModelList.add(a);
        a = new RecommendationModel(cafeImages[4], "Cafe Mahal", "Condong Catur","Open" );
        recommendationModelList.add(a);

        recommendationAdapter.notifyDataSetChanged();

    }

}
