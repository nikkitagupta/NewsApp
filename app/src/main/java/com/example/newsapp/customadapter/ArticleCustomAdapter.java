package com.example.newsapp.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsapp.R;
import com.example.newsapp.model.ArticleModel;

import java.util.List;

public class ArticleCustomAdapter extends RecyclerView.Adapter<ArticleCustomAdapter.ArticleViewHolder> {

    private Context context;
    private List<ArticleModel> articleList;

    public ArticleCustomAdapter(Context context, List<ArticleModel> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_list_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.tvTitle.setText(this.articleList.get(position).getTitle().toString());
        holder.tvPublishDate.setText(this.articleList.get(position).getPublishedAt().toString());
        Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions()
                        .centerCropTransform())
                .load(articleList.get(position).getUrlToImage())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(articleList != null){
            return articleList.size();
        } else {
            return 0;
        }
    }

    public void setArticleList(List<ArticleModel> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvPublishDate;
        ImageView imageView;

        public ArticleViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView)itemView.findViewById(R.id.titleText);
            tvPublishDate = (TextView)itemView.findViewById(R.id.publishText);
            imageView = (ImageView)itemView.findViewById(R.id.article_icon);
        }
    }
}
