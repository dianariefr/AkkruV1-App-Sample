package com.akkru.user.akkru.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akkru.user.akkru.DetailActivity;
import com.akkru.user.akkru.MainActivity;
import com.akkru.user.akkru.R;
import com.akkru.user.akkru.model.CatBarModel;
import com.akkru.user.akkru.model.CategoryModel;
import com.akkru.user.akkru.onLoadMoreListener;

import java.util.List;

/**
 * Created by dar9617 on 8/26/18.
 *
 * @Author [Dian Arief]
 * @Email sg8.dian@gmail.com
 * @Github https://github.com/dar9617
 * @TIM Akkrue
 */

public class CatCafeAdapter extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<CategoryModel> list;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private onLoadMoreListener onLoadMoreListener2;

    public CatCafeAdapter(List<CategoryModel> catCafeModelList, RecyclerView recyclerView) {
        list = catCafeModelList;

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = gridLayoutManager.getItemCount();
                            lastVisibleItem = gridLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener2 != null) {
                                    onLoadMoreListener2.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder vh;
        if (i == VIEW_ITEM) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_catbar, viewGroup, false);

            vh = new CafeViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CafeViewHolder) {

            CategoryModel singleCafe = (CategoryModel) list.get(i);


            ((CafeViewHolder) viewHolder).IMGBarThumbn.setImageResource(singleCafe.getBarThumbnail());
            ((CafeViewHolder) viewHolder).TVBarName.setText(singleCafe.getBarName());
            ((CafeViewHolder) viewHolder).TVBarAddress.setText(singleCafe.getBarAddress());
            ((CafeViewHolder) viewHolder).TVBarStatus.setText(singleCafe.getBarStatus());

            ((CafeViewHolder) viewHolder).cafeModel = singleCafe;
            if (singleCafe.getBarStatus()=="Open Now"){
                ((CafeViewHolder) viewHolder).TVBarStatus.setTextColor(Color.GREEN);
            } else{
                ((CafeViewHolder) viewHolder).TVBarStatus.setTextColor(Color.RED);
            }

        } else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //goes to new activity passing the item name
//                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
//            }
//        });
    }

    public void setLoaded() {
        loading = false;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnLoadMoreListener(onLoadMoreListener onLoadMoreListener1) {
        this.onLoadMoreListener2 = onLoadMoreListener1;
    }

    //
    public static class CafeViewHolder extends RecyclerView.ViewHolder {
        public TextView TVBarName;
        public TextView TVBarAddress;
        public ImageView IMGBarThumbn;
        public TextView TVBarStatus;
        public CategoryModel cafeModel;

        public CafeViewHolder(View v) {
            super(v);
            IMGBarThumbn = v.findViewById(R.id.img_BarThumbn);
            TVBarName = v.findViewById(R.id.TV_BarName);
            TVBarAddress =v.findViewById(R.id.TV_BarAddress);
            TVBarStatus = v.findViewById(R.id.TV_BarStatus);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    v.getContext().startActivity(intent);
//                    Toast.makeText(v.getContext(),
//                            "OnClick :" + cafeModel.getBarName() + " \n " + cafeModel.getBarAddress(),
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar =  v.findViewById(R.id.progressBar1);
        }
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<CategoryModel> list2) {
        list.addAll(list2);
        notifyDataSetChanged();
    }
}
