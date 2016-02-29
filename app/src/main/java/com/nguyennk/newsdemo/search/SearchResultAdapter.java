package com.nguyennk.newsdemo.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nguyennk.newsdemo.AppConfig;
import com.nguyennk.newsdemo.ArticleAdapter;
import com.nguyennk.newsdemo.R;
import com.nguyennk.newsdemo.enums.ArticleTextSize;
import com.nguyennk.newsdemo.model.Article;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nguye on 2/26/2016.
 */
public class SearchResultAdapter extends UltimateViewAdapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<Article> onlineArticle = new ArrayList<>();

    private ArticleAdapter.ArticleAdapterListener listener;

    public SearchResultAdapter(ArticleAdapter.ArticleAdapterListener listener) {
        this.listener = listener;
    }

    public void add(List<Article> newData) {
        onlineArticle.addAll(newData);
        notifyDataSetChanged();
    }

    public void clear() {
        onlineArticle.clear();
        notifyDataSetChanged();
    }

    @Subscribe
    public void onArticleTextSizeChanged(ArticleTextSize textSize) {
        notifyDataSetChanged();
    }

    @Override
    public SearchResultViewHolder getViewHolder(View view) {
        SearchResultViewHolder holder = (SearchResultViewHolder) view.getTag();

        if (holder == null) {
            holder = new SearchResultViewHolder(view);
            view.setTag(holder);
        }

        return holder;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent) {
        return onCreateViewHolder(parent, 0);
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_search_result, parent, false);
        SearchResultViewHolder viewHolder = new SearchResultViewHolder(view);

        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return onlineArticle.size();
    }

    @Override
    public int getAdapterItemCount() {
        return onlineArticle.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(final SearchResultViewHolder holder, final int position) {
        final Article article = onlineArticle.get(position);

        holder.tvTitle.setText(article.getHeadline().getMain());
        holder.tvTitle.setTextSize(AppConfig.getArticleTextSize(holder.itemView.getContext()).getTitleSize());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, article, position);
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
       return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    static class SearchResultViewHolder extends UltimateRecyclerviewViewHolder {
        @Bind(R.id.tv_title)
        TextView tvTitle;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
