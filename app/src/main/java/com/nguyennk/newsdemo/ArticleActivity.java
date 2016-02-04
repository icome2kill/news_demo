package com.nguyennk.newsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        article = getIntent().getParcelableExtra(KEY_ARTICLE);

        getSupportActionBar().setTitle(article.getHeadline().getMain());

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setVerticalScrollBarEnabled(true);
        webView.loadUrl(article.getWebUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);

        if (isArticleSaved()) {
            menu.getItem(0).setIcon(R.mipmap.icon_fav_on);
        } else {
            menu.getItem(0).setIcon(R.mipmap.icon_fav_off);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                if (isArticleSaved()) {
                    deleteArticle();
                    item.setIcon(R.mipmap.icon_fav_off);
                } else {
                    saveArticle();
                    item.setIcon(R.mipmap.icon_fav_on);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isArticleSaved() {
        return new Select().from(Article.class).where("webUrl = ?", article.getWebUrl()).count() > 0;
    }

    private void deleteArticle() {
        new Delete().from(Article.class).where("webUrl = ?", article.getWebUrl()).execute();
        Toast.makeText(this, "Article deleted successfully", Toast.LENGTH_SHORT).show();
    }

    private void saveArticle() {
        article.save();
        Toast.makeText(this, "Article saved successfully", Toast.LENGTH_SHORT).show();
    }
}
