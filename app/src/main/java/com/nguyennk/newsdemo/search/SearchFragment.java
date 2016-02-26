package com.nguyennk.newsdemo.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.nguyennk.newsdemo.ArticleAdapter;
import com.nguyennk.newsdemo.R;
import com.nguyennk.newsdemo.business.NewYorkTimesApiEndpoint;
import com.nguyennk.newsdemo.business.NewYorkTimesApiResponse;
import com.nguyennk.newsdemo.details.ArticleActivity;
import com.nguyennk.newsdemo.model.Article;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    @Bind(R.id.edt_query)
    EditText edtQuery;
    @Bind(R.id.btn_clear)
    ImageButton btnClear;
    @Bind(R.id.rv_search_result)
    UltimateRecyclerView rvSearchResult;

    private int currentPage = 0;
    private String query = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtQuery.setText("");

                ((SearchResultAdapter) rvSearchResult.getAdapter()).clear();
            }
        });
        edtQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    query = edtQuery.getText().toString();
                    if (query.length() != 0) {
                        currentPage = 0;

                        performSearch(query, true);
                        return true;
                    }
                }
                return false;
            }
        });
        edtQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    btnClear.setVisibility(View.GONE);
                } else {
                    btnClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchResult.setAdapter(new SearchResultAdapter(new ArticleAdapter.ArticleAdapterListener() {
            @Override
            public void onItemClick(View v, Article article, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra(ArticleActivity.KEY_ARTICLE, article);

                startActivity(intent);
            }
        }));
        rvSearchResult.enableLoadmore();
        rvSearchResult.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                performSearch(query, false);
            }
        });
        rvSearchResult.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).colorResId(R.color.colorAccent).build());
        rvSearchResult.hideEmptyView();

        return view;
    }

    private ProgressDialog dialog;

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

    private void performSearch(String query, boolean showLoading) {
        if (showLoading) {
            showLoading();
        }
        NewYorkTimesApiEndpoint apiService = NewYorkTimesApiEndpoint.Factory.getApiService();
        Call<NewYorkTimesApiResponse> call = apiService.searchArticle(query, currentPage);
        call.enqueue(new Callback<NewYorkTimesApiResponse>() {
            @Override
            public void onResponse(Response<NewYorkTimesApiResponse> response) {
                List<Article> newData = response.body().getResponse().getArticles();
                if (newData.size() == 0) {
                    rvSearchResult.showEmptyView();
                } else {
                    rvSearchResult.hideEmptyView();
                }

                if (currentPage == 0) {
                    ((SearchResultAdapter) rvSearchResult.getAdapter()).clear();
                }
                ((SearchResultAdapter) rvSearchResult.getAdapter()).add(newData);

                currentPage++;
                hideLoading();
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();
                t.printStackTrace();
            }
        });
    }
}
