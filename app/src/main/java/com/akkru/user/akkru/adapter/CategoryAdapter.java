package com.akkru.user.akkru.adapter;

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

import com.akkru.user.akkru.R;
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

public class CategoryAdapter extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<CategoryModel> list;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private onLoadMoreListener onLoadMoreListener2;

    public CategoryAdapter(List<CategoryModel> catRecomendationModelList, RecyclerView recyclerView) {
        list = catRecomendationModelList;

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

            vh = new RecViewHolder(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.progressbar, viewGroup, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof RecViewHolder) {

            CategoryModel singleRec = (CategoryModel) list.get(i);



            ((RecViewHolder) viewHolder).IMGBarThumbn.setImageResource(singleRec.getBarThumbnail());
            ((RecViewHolder) viewHolder).TVBarName.setText(singleRec.getBarName());
            ((RecViewHolder) viewHolder).TVBarAddress.setText(singleRec.getBarAddress());
            ((RecViewHolder) viewHolder).TVBarStatus.setText(singleRec.getBarStatus());

            ((RecViewHolder) viewHolder).recModel = singleRec;
            if (singleRec.getBarStatus() == "Open Now") {
                ((RecViewHolder) viewHolder).TVBarStatus.setTextColor(Color.GREEN);
            } else {
                ((RecViewHolder) viewHolder).TVBarStatus.setTextColor(Color.RED);
            }

        } else {
            ((ProgressViewHolder) viewHolder).progressBar.setIndeterminate(true);
        }
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
    public static class RecViewHolder extends RecyclerView.ViewHolder {
        public TextView TVBarName;
        public TextView TVBarAddress;
        public ImageView IMGBarThumbn;
        public TextView TVBarStatus;
        public CategoryModel recModel;

        public RecViewHolder(View v) {
            super(v);
            IMGBarThumbn = v.findViewById(R.id.img_BarThumbn);
            TVBarName = v.findViewById(R.id.TV_BarName);
            TVBarAddress =v.findViewById(R.id.TV_BarAddress);
            TVBarStatus = v.findViewById(R.id.TV_BarStatus);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),
                            "OnClick :" + recModel.getBarName() + " \n " + recModel.getBarAddress(),
                            Toast.LENGTH_SHORT).show();
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

