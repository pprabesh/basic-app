package com.example.newstockMarkt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newstockmarkt.R;
import com.example.newstockMarkt.Models.NewsModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<NewsModel> newsData;
    private Context mContext;
    newsListEventListner newsListEventListner;

    NewsAdapter(Context context) {
        this.mContext = context;
    }

    public ArrayList<NewsModel> getNewsData() {
        return newsData;
    }

    public NewsAdapter.newsListEventListner getNewsListEventListner() {
        return newsListEventListner;
    }

    public void setNewsListEventListner(NewsAdapter.newsListEventListner newsListEventListner) {
        this.newsListEventListner = newsListEventListner;
    }

    public void setNewsData(ArrayList<NewsModel> newsData) {
        this.newsData = newsData;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.news_item_layout, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        NewsModel newsModel = newsData.get(position);
        holder.titleTxt.setText(newsModel.getTitle());
        holder.descTxt.setText(newsModel.getDescription());
        holder.publicerTxt.setText(newsModel.getPublicer());
        holder.dateTxt.setText(newsModel.getPublishedDate());
        Glide.with(mContext)
                .load(newsModel.getThumbNailUrl())
                .placeholder(R.drawable.newspaper)
                .into(holder.newsImg);
        holder.linkTxt.setText(newsModel.getNewsUrl());
    }

    @Override
    public int getItemCount() {
        return newsData.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        TextView descTxt;
        ImageView newsImg;
        TextView publicerTxt;
        TextView dateTxt;
        TextView linkTxt;

        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.title);
            descTxt = itemView.findViewById(R.id.desc);
            newsImg = itemView.findViewById(R.id.thumbImg);
            publicerTxt = itemView.findViewById(R.id.publicer);
            dateTxt = itemView.findViewById(R.id.publishedDate);
            linkTxt = itemView.findViewById(R.id.link);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newsListEventListner != null) {
                        newsListEventListner.onClick(newsData.get(getAdapterPosition()).getNewsUrl());
                    }
                }
            });
        }
    }

    interface newsListEventListner {
        void onClick(String url);
    }

}
