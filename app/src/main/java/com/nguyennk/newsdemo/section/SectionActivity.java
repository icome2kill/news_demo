package com.nguyennk.newsdemo.section;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.nguyennk.newsdemo.ArticleAdapter;
import com.nguyennk.newsdemo.BaseActivity;
import com.nguyennk.newsdemo.R;
import com.nguyennk.newsdemo.business.NewYorkTimesApiEndpoint;
import com.nguyennk.newsdemo.business.NewYorkTimesApiResponse;
import com.nguyennk.newsdemo.details.ArticleActivity;
import com.nguyennk.newsdemo.model.Article;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguye on 2/24/2016.
 */
public class SectionActivity extends BaseActivity {
    public static final String KEY_SECTION = "key_section";

    private String currentSection;
    private int currentPage;

    @Bind(R.id.rv_articles)
    UltimateRecyclerView rvArticles;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        ButterKnife.bind(this);

        currentSection  = buildQuery(getIntent().getStringExtra(KEY_SECTION));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_SECTION));
        tabLayout.setVisibility(View.GONE);

        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        rvArticles.setAdapter(new ArticleAdapter(new ArticleAdapter.ArticleAdapterListener() {
            @Override
            public void onItemClick(View v, Article article, int position) {
                Intent intent = new Intent(SectionActivity.this, ArticleActivity.class);
                intent.putExtra(ArticleActivity.KEY_ARTICLE, article);

                startActivity(intent);
            }
        }, true));
        EventBus.getDefault().register(rvArticles.getAdapter());

        rvArticles.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                rvArticles.setRefreshing(true);
                loadArticle(false);
            }
        });
        rvArticles.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.colorAccent).build());
        rvArticles.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                loadArticle(true);
            }
        });

        loadArticle(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(rvArticles);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_text_size:
                showArticleTextSizeOptions();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return true;
    }

    private void loadArticle(boolean loadNextPage) {
        if (loadNextPage) {
            currentPage++;
        }
        Call<NewYorkTimesApiResponse> call = NewYorkTimesApiEndpoint.Factory.getApiService().getAllArticle(currentSection, currentPage);
        call.enqueue(new Callback<NewYorkTimesApiResponse>() {
            @Override
            public void onResponse(Response<NewYorkTimesApiResponse> response) {
                List<Article> newData = response.body().getResponse().getArticles();
                ArticleAdapter adapter = (ArticleAdapter) rvArticles.getAdapter();

                // Refresh. May not be necessary to re populate the list
                List<Article> currentData = adapter.getData();
                if (currentPage == 1 && currentData.size() > 0 && newData.size() > 0) {
                    if (!currentData.get(0).getWebUrl().equalsIgnoreCase(newData.get(0).getWebUrl())) {
                        adapter.clear();
                        adapter.add(newData);
                    } else {
                        Toast.makeText(SectionActivity.this, "No more news", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    adapter.add(newData);
                }
                rvArticles.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                if (rvArticles.getAdapter().getItemCount() == 0) {
                    rvArticles.showEmptyView();
                }
                rvArticles.setRefreshing(false);
                t.printStackTrace();
            }
        });
    }

    private String buildQuery(String section) {
        return "section_name:(\"" + section + "\")";
    }
}
