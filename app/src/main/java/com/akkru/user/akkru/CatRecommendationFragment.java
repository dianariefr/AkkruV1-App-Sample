package com.akkru.user.akkru;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akkru.user.akkru.adapter.CatCafeAdapter;
import com.akkru.user.akkru.adapter.CategoryAdapter;
import com.akkru.user.akkru.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatRecommendationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CatRecommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatRecommendationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeCatCafe;
    private RecyclerView recyclerCatCafe;

    //Inisialisasi
    private CategoryAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private List<CategoryModel> cafeList;

    protected Handler handler;
    private CallbackListener callbackListener;

    public CatRecommendationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatRecommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatRecommendationFragment newInstance(String param1, String param2) {
        CatRecommendationFragment fragment = new CatRecommendationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cat_recommendation, container, false);
        //Binding

        swipeCatCafe = rootView.findViewById(R.id.Swipe_CatRec);
        recyclerCatCafe = rootView.findViewById(R.id.Recycler_CatRec);

        cafeList = new ArrayList<CategoryModel>();
        handler = new Handler();
        recyclerCatCafe.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerCatCafe.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryAdapter(cafeList, recyclerCatCafe);
        recyclerCatCafe.setAdapter(mAdapter);
        showBackButton();

        //Load Initial Data
        loadData();


        //LOAD MORE
        loadmore();

        //Refresh
        refresh();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof CallbackListener)
           callbackListener = (CallbackListener) getActivity();
        callbackListener.onCallBack("ssss");
    }

    public void showBackButton() {
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // load initial data
    private void loadData() {
        for (int i = 1; i <= 20; i++) {
            cafeList.add(new CategoryModel(R.drawable.dummybar, "Aufa Recom", "Condong Catur, Yogyakarta", "Open Now"));

        }
        swipeCatCafe.setRefreshing(false);
    }

    private void loadmore(){
        mAdapter.setOnLoadMoreListener(new onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                cafeList.add(null);
                mAdapter.notifyItemInserted(cafeList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        cafeList.remove(cafeList.size() - 1);
                        mAdapter.notifyItemRemoved(cafeList.size());
                        //add items one by one
                        int start = cafeList.size();
                        int end = start + 20;

                        for (int i = start + 1; i <= end; i++) {
                            cafeList.add(new CategoryModel(R.drawable.dummybar, "Fatih Recom", "Mlati, Yogyakarta", "Closed Now"));
                            mAdapter.notifyItemInserted(cafeList.size());
                        }
                        mAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 5000);

            }
        }); swipeCatCafe.setRefreshing(false);
    }

    //Refresh Page
    private void refresh() {
        swipeCatCafe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeCatCafe.setRefreshing(true);
                swipeCatCafe.setRefreshing(false);
                //mAdapter.clear();
                //loadData();
                //loadmore();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
