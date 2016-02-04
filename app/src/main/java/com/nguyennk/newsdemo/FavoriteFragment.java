package com.nguyennk.newsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.nguyennk.newsdemo.model.Article;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteFragment extends Fragment {
    @Bind(R.id.rv_favourite)
    UltimateRecyclerView rvFavourite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);

        rvFavourite.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rvFavourite.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this.getContext()).colorResId(R.color.colorAccent).build());

        final ArticleAdapter adapter = new ArticleAdapter(new ArticleAdapter.ArticleAdapterListener() {
            @Override
            public void onItemClick(View v, Article article, int position) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra(ArticleActivity.KEY_ARTICLE, article);

                startActivity(intent);
            }
        });

        rvFavourite.setAdapter(adapter);

        adapter.add(new Select().from(Article.class).<Article>execute());

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Article article = adapter.getData().get(position);

                deleteArticle(position, article);
                super.onSwiped(viewHolder, i);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvFavourite.mRecyclerView);

        return view;
    }

    private void deleteArticle(final int position, final Article article) {
        article.delete();

        final ArticleAdapter adapter = (ArticleAdapter) rvFavourite.getAdapter();
        adapter.getData().remove(position);
        adapter.notifyItemRemoved(position);

        Snackbar.make(rvFavourite, "Article has been removed from your favorite", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                article.save();

                adapter.getData().add(position, article);
                adapter.notifyItemInserted(position);
            }
        }).show();
    }
}
