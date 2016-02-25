package com.nguyennk.newsdemo.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.nguyennk.newsdemo.details.ArticleActivity;
import com.nguyennk.newsdemo.ArticleAdapter;
import com.nguyennk.newsdemo.R;
import com.nguyennk.newsdemo.business.NewYorkTimesApiEndpoint;
import com.nguyennk.newsdemo.business.NewYorkTimesApiResponse;
import com.nguyennk.newsdemo.model.Article;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguye on 2/1/2016.
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.article_list)
    UltimateRecyclerView articleList;

    private int currentPage = 0;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        articleList.setLayoutManager(new LinearLayoutManager(getContext()));
        articleList.setAdapter(new ArticleAdapter(new ArticleAdapter.ArticleAdapterListener() {
            @Override
            public void onItemClick(View v, Article article, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra(ArticleActivity.KEY_ARTICLE, article);

                startActivity(intent);
            }
        }, true));

        articleList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorAccent).build());
        articleList.enableDefaultSwipeRefresh(true);
        articleList.enableLoadmore();

        articleList.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                articleList.setRefreshing(true);
                loadArticle(false);
            }
        });

        articleList.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                loadArticle(true);
            }
        });

        showLoading();
        loadArticle(false);

        return view;
    }

    private void showLoading() {
        if (dialog == null) {
            dialog = new ProgressDialog(getContext());
            dialog.setMessage("Loading...");
        }
        dialog.show();
    }

    private void hideLoading() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    private void loadArticle(boolean loadNextPage) {
        if (loadNextPage) {
            currentPage = currentPage + 1;
        }
        NewYorkTimesApiEndpoint apiService = NewYorkTimesApiEndpoint.Factory.getApiService();
        Call<NewYorkTimesApiResponse> call = apiService.searchArticle(null, currentPage);
        call.enqueue(new Callback<NewYorkTimesApiResponse>() {
            @Override
            public void onResponse(Response<NewYorkTimesApiResponse> response) {
                List<Article> newData = response.body().getResponse().getArticles();
                ArticleAdapter adapter = (ArticleAdapter) articleList.getAdapter();

                // Refresh. May not be necessary to re populate the list
                List<Article> currentData = adapter.getData();
                if (currentPage == 1 && currentData.size() > 0 && newData.size() > 0) {
                    if (!currentData.get(0).getWebUrl().equalsIgnoreCase(newData.get(0).getWebUrl())) {
                        adapter.clear();
                        adapter.add(newData);
                    } else {
                        Toast.makeText(getContext(), "No more news", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    adapter.add(newData);
                }
                hideLoading();
                articleList.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                if (articleList.getAdapter().getItemCount() == 0) {
                    articleList.showEmptyView();
                }
                articleList.setRefreshing(false);
                hideLoading();
                t.printStackTrace();
            }
        });
    }
}
