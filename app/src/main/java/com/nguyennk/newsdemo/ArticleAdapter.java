package com.nguyennk.newsdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nguyennk.newsdemo.model.Article;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nguye on 2/3/2016.
 */
public class ArticleAdapter extends UltimateViewAdapter<ArticleAdapter.ArticleViewHolder> {
    private static final PrettyTime PRETTY_TIME = new PrettyTime();

    private List<Article> data = new ArrayList<>();
    private ArticleAdapterListener itemClickListener;

    public ArticleAdapter(ArticleAdapterListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<Article> getData() {
        return this.data;
    }

    public void add(List<Article> newData) {
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_home, parent, false);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);

        return viewHolder;
    }

    @Override
    public ArticleViewHolder getViewHolder(View view) {
        ArticleViewHolder holder = (ArticleViewHolder) view.getTag();
        if (holder == null) {
            holder = new ArticleViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_home, parent, false);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);
        view.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {
        final Article article = data.get(position);

        holder.tvTitle.setText(article.getHeadline().getMain());
        holder.tvContent.setText(article.getSnippet());
        holder.tvSource.setText(article.getSource());

        List<Article.Multimedia> multimedia = article.getMultimedia();
        if (multimedia.size() != 0) {
            Article.Multimedia suitableMedia = multimedia.get(0);
            for (Article.Multimedia media : multimedia) {
                if (media.getHeight() == media.getWidth()) {
                    suitableMedia = media;
                    break;
                }
            }
            String imageSrc = suitableMedia.getUrl();
            Picasso.with(holder.itemView.getContext()).load("http://nytimes.com/" + imageSrc).into(holder.imvArticleImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.imvArticleImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    holder.imvArticleImage.setVisibility(View.GONE);
                }
            });
        } else {
            holder.imvArticleImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, article, position);
            }
        });

        holder.tvTime.setText(PRETTY_TIME.format(article.getPubDate()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getAdapterItemCount() {
        return data.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    interface ArticleAdapterListener {
        void onItemClick(View v, Article article, int position);
    }

    static class ArticleViewHolder extends UltimateRecyclerviewViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_source)
        TextView tvSource;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.imv_article_image)
        ImageView imvArticleImage;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
