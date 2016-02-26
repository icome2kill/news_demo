package com.nguyennk.newsdemo;

import android.content.Context;
import android.content.SharedPreferences;

import com.nguyennk.newsdemo.enums.ArticleTextSize;

/**
 * Created by nguye on 2/25/2016.
 */
public class AppConfig {
    private AppConfig() {}

    public static ArticleTextSize getArticleTextSize(Context context) {
        SharedPreferences pref = getSharedPreference(context);
        return ArticleTextSize.values()[pref.getInt("ARTICLE_TEXT_SIZE", ArticleTextSize.NORMAL.ordinal())];
    }

    public static void setArticleTextSize(Context context, ArticleTextSize size) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt("ARTICLE_TEXT_SIZE", size.ordinal());
        editor.commit();
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("NewsDemo", Context.MODE_PRIVATE);
    }
}
