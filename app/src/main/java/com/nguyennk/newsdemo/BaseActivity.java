package com.nguyennk.newsdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.nguyennk.newsdemo.enums.ArticleTextSize;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by nguye on 2/29/2016.
 */

/**
 * Base activity class for the whole application.
 * Every other activity should extends this.
 */
public class BaseActivity extends AppCompatActivity {
    protected void showArticleTextSizeOptions() {
        String[] titles = new String[ArticleTextSize.values().length];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = ArticleTextSize.values()[i].toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Article text size");
        builder.setSingleChoiceItems(titles, AppConfig.getArticleTextSize(this).ordinal(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArticleTextSize articleTextSize = ArticleTextSize.values()[which];
                AppConfig.setArticleTextSize(BaseActivity.this, articleTextSize);

                EventBus.getDefault().post(articleTextSize);
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }
}
