package com.akkru.user.akkru;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akkru.user.akkru.adapter.CatBarAdapter;
import com.akkru.user.akkru.model.CatBarModel;
import com.akkru.user.akkru.model.Outlet;
import com.akkru.user.akkru.model.Response2;
import com.akkru.user.akkru.network.ApiClient;
import com.akkru.user.akkru.network.ApiInterface;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dar9617 on 8/25/18.
 *
 * @Author [Dian Arief]
 * @Email sg8.dian@gmail.com
 * @Github https://github.com/dar9617
 * @TIM Akkrue
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CatBarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CatBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatBarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private SwipeRefreshLayout swipeCatBar;
    private RecyclerView recyclerCatBar;

    //Inisialisasi
    private CatBarAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private List<CatBarModel> cafeList;
    private List<CatBarModel> outlets;
    protected Handler handler;
    private CallbackListener callbackListener;


    public CatBarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatBarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatBarFragment newInstance(String param1, String param2) {
        CatBarFragment fragment = new CatBarFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_cat_bar, container, false);
        //Binding
        //Load Initial Data
//        loadData();


        //LOAD MORE
//        loadmore();

        //Refresh
//        refresh();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof CallbackListener)
            callbackListener = (CallbackListener) getActivity();
        callbackListener.onCallBack("ssss");
    }

    // load initial data
    private void loadData() {
        for (int i = 1; i <= 20; i++) {
            cafeList.add(new CatBarModel(R.drawable.dummybar, "Aufa Bar", "Condong Catur, Yogyakarta", "Open Now"));

        }
        swipeCatBar.setRefreshing(false);
    }

    private void loadmore() {
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
                            cafeList.add(new CatBarModel(R.drawable.dummybar, "Fatih Bar", "Mlati, Yogyakarta", "Closed Now"));
                            mAdapter.notifyItemInserted(cafeList.size());
                        }
                        mAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 5000);

            }
        });
        swipeCatBar.setRefreshing(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Create handle for the RetrofitInstance interface*/

        swipeCatBar = view.findViewById(R.id.Swipe_CatBar);
        recyclerCatBar = view.findViewById(R.id.Recycler_CatBar);

        cafeList = new ArrayList<CatBarModel>();
        handler = new Handler();
        outlets = new ArrayList<CatBarModel>();

        ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
        Call<Outlet> call = service.getAllBar("Bar");
        call.enqueue(new Callback<Outlet>() {
            @Override
            public void onResponse(Call<Outlet> call, Response<Outlet> response) {
                Log.d("Retro", "Sukses");
                if (response.isSuccessful()) {
                    cafeList = response.body().getItems();
                    if (cafeList != null) {
                        //Log.d("Retro4", outlets.get(0).getBarName());
                        generateDataList(cafeList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Outlet> call, Throwable t) {
                Log.d("Retro", "Fail");
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    public void generateDataList(List<CatBarModel> cafeList) {
        //RecyclerView Setting
        recyclerCatBar.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerCatBar.setLayoutManager(mLayoutManager);
        mAdapter = new CatBarAdapter(cafeList, recyclerCatBar, getContext());
        recyclerCatBar.setAdapter(mAdapter);
    }

    //Refresh Page
    private void refresh() {
        swipeCatBar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeCatBar.setRefreshing(true);
                swipeCatBar.setRefreshing(false);
                //mAdapter.clear();
                //loadData();
                //loadmore();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
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
