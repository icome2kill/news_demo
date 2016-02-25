package com.nguyennk.newsdemo.details;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.nguyennk.newsdemo.R;
import com.nguyennk.newsdemo.model.Article;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {
    public static final String KEY_ARTICLE = "key_article";

    private Article article;

    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        tabLayout.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        article = getIntent().getParcelableExtra(KEY_ARTICLE);

        getSupportActionBar().setTitle(article.getSectionName());

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setVerticalScrollBarEnabled(true);

        webView.loadUrl(article.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);

        if (isArticleSaved()) {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (isArticleSaved()) {
                    deleteArticle();
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                } else {
                    saveArticle();
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isArticleSaved() {
        return new Select().from(Article.class).where("webUrl = ?", article.getWebUrl()).count() > 0;
    }

    private void deleteArticle() {
        new Delete().from(Article.class).where("webUrl = ?", article.getWebUrl()).execute();
        Toast.makeText(this, "Article deleted", Toast.LENGTH_SHORT).show();
    }

    private void saveArticle() {
        article.save();
        Toast.makeText(this, "Article saved", Toast.LENGTH_SHORT).show();
    }
}
