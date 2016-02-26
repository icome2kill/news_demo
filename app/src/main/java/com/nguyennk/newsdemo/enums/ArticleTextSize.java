package com.nguyennk.newsdemo.enums;

/**
 * Created by nguye on 2/25/2016.
 */
public enum ArticleTextSize {
    TINY, SMALL, NORMAL, LARGE;

    public int getSize() {
        return this.ordinal() + 11;
    }

    public int getTitleSize() {
        return this.ordinal() + 15;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, 1) + super.toString().substring(1).toLowerCase();
    }
}
