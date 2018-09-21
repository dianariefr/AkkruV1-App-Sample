package com.akkru.user.akkru;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.MyViewHolder> {

    private Context context;
    private List<RecommendationModel> recommendationModelList;

    public RecommendationAdapter(Context context, List<RecommendationModel> recommendationModelList) {
        this.context = context;
        this.recommendationModelList = recommendationModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivRecommendation;
        public TextView tvCafeTitle,tvCafeLocation,tvCafeStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivRecommendation = itemView.findViewById(R.id.list_iv_recommendation);
            tvCafeTitle = itemView.findViewById(R.id.list_tv_title);
            tvCafeLocation = itemView.findViewById(R.id.list_tv_locationn);
            tvCafeStatus = itemView.findViewById(R.id.list_tv_status);


        }
    }


    @NonNull
    @Override
    public RecommendationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recommendation_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendationAdapter.MyViewHolder holder, int position) {
        RecommendationModel recommendationModel = recommendationModelList.get(position);

        //Glide.with(context).load(recommendationModel.getCafeImage()).into(holder.ivRecommendation);

        holder.ivRecommendation.setImageResource(R.drawable.dummybar);
        holder.tvCafeTitle.setText(recommendationModel.getCafeTitle());
        holder.tvCafeLocation.setText(recommendationModel.getCafeLocation());
        holder.tvCafeStatus.setText(recommendationModel.getCafeStatus());
        if (recommendationModel.getCafeStatus()=="Open"){
            holder.tvCafeStatus.setTextColor(Color.GREEN);
        } else{
            holder.tvCafeStatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return recommendationModelList.size();
    }


}
