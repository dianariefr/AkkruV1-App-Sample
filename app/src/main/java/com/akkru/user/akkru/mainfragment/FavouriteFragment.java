package com.akkru.user.akkru.mainfragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akkru.user.akkru.CallbackListener;
import com.akkru.user.akkru.R;
import com.akkru.user.akkru.adapter.CatBarAdapter;
import com.akkru.user.akkru.adapter.CategoryAdapter;
import com.akkru.user.akkru.model.CatBarModel;
import com.akkru.user.akkru.model.CategoryModel;
import com.akkru.user.akkru.onLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavouriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavouriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouriteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //Inisialisasi
    private CatBarAdapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private List<CatBarModel> List;
    protected Handler handler;
    private CallbackListener callbackListener;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavouriteFragment newInstance(String param1, String param2) {
        FavouriteFragment fragment = new FavouriteFragment();
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
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Binding
        //swipeCatCafe = rootView.findViewById(R.id.Swipe_CatCafe);
        recyclerView = view.findViewById(R.id.Recycler_Favorite);
        swipeRefreshLayout = view.findViewById(R.id.swipe_fav);
        List = new ArrayList<CatBarModel>();
        handler = new Handler();

        //RecyclerView Setting
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CatBarAdapter(List, recyclerView, getContext());
        recyclerView.setAdapter(mAdapter);

        //Load Initial Data
        loadData();

        //LOAD MORE
        //loadmore();

        //Refresh
        refresh();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof CallbackListener)
            callbackListener = (CallbackListener) getActivity();
        callbackListener.onCallBack("Fav");
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    // load initial data
    private void loadData() {
        for (int i = 1; i <= 5; i++) {
            List.add(new CatBarModel(R.drawable.dummybar, "Aufa Cafe", "Condong Catur, Yogyakarta", "Open Now"));

        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadmore(){
        mAdapter.setOnLoadMoreListener(new onLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                List.add(null);
                mAdapter.notifyItemInserted(List.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        List.remove(List.size() - 1);
                        mAdapter.notifyItemRemoved(List.size());
                        //add items one by one
                        int start = List.size();
                        int end = start + 20;
                        for (int i = start + 1; i <= end; i++) {
                            List.add(new CatBarModel(R.drawable.dummybar, "Fatih Cafe", "Mlati, Yogyakarta", "Closed Now"));
                            mAdapter.notifyItemInserted(List.size());
                        }
                        mAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 5000);
            }
        }); swipeRefreshLayout.setRefreshing(false);
    }

    //Refresh Page
    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setRefreshing(false);
                //mAdapter.clear();
                //loadData();
                //loadmore();
            }
        });
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
