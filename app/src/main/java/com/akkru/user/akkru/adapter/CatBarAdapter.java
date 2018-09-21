package com.akkru.user.akkru.adapter;

import android.content.Context;
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
import com.akkru.user.akkru.model.CatBarModel;
import com.akkru.user.akkru.onLoadMoreListener;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dar9617 on 8/25/18.
 *
 * @Author [Dian Arief]
 * @Email sg8.dian@gmail.com
 * @Github https://github.com/dar9617
 * @TIM Akkrue
 */

public class CatBarAdapter extends RecyclerView.Adapter{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<CatBarModel> list;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private onLoadMoreListener onLoadMoreListener2;

    public CatBarAdapter(List<CatBarModel> catBarModelList, RecyclerView recyclerView, Context context) {
        list = catBarModelList;
        this.context = context;
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

    private Context context;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CafeViewHolder) {

            CatBarModel singleCafe = (CatBarModel) list.get(i);


            //((CafeViewHolder) viewHolder).IMGBarThumbn.setImageResource(singleCafe.getBarThumbnail());
            ((CafeViewHolder) viewHolder).TVBarName.setText(singleCafe.getBarName());
            ((CafeViewHolder) viewHolder).TVBarAddress.setText(singleCafe.getBarAddress());
            ((CafeViewHolder) viewHolder).TVBarStatus.setText(singleCafe.getBarStatus());

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(list.get(i).getBarUrl().getUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(((CafeViewHolder) viewHolder).IMGBarThumbn);

            ((CafeViewHolder) viewHolder).cafeModel = singleCafe;
            if (singleCafe.getBarStatus()=="Open Now"){
                ((CafeViewHolder) viewHolder).TVBarStatus.setTextColor(Color.GREEN);
            } else{
                ((CafeViewHolder) viewHolder).TVBarStatus.setTextColor(Color.RED);
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
    public static class CafeViewHolder extends RecyclerView.ViewHolder {
        public TextView TVBarName;
        public TextView TVBarAddress;
        public ImageView IMGBarThumbn;
        public TextView TVBarStatus;
        public CatBarModel cafeModel;

        public CafeViewHolder(View v) {
            super(v);
            IMGBarThumbn = v.findViewById(R.id.img_BarThumbn);
            TVBarName = v.findViewById(R.id.TV_BarName);
            TVBarAddress =v.findViewById(R.id.TV_BarAddress);
            TVBarStatus = v.findViewById(R.id.TV_BarStatus);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),
                            "OnClick :" + cafeModel.getBarName() + " \n " + cafeModel.getBarAddress(),
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
    public void addAll(List<CatBarModel> list2) {
        list.addAll(list2);
        notifyDataSetChanged();
    }
}
