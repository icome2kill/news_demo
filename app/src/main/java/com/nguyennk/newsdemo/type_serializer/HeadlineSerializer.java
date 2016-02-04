package com.nguyennk.newsdemo.type_serializer;

import com.activeandroid.serializer.TypeSerializer;
import com.nguyennk.newsdemo.model.Article;

/**
 * Created by nguye on 2/3/2016.
 */
public class HeadlineSerializer extends TypeSerializer{
    @Override
    public Class<?> getDeserializedType() {
        return Article.Headline.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public Object serialize(Object data) {
        if (data == null) {
            return null;
        }
        return ((Article.Headline) data).getMain();
    }

    @Override
    public Object deserialize(Object data) {
        if (data == null) {
            return null;
        }
        Article.Headline headline = new Article.Headline();
        headline.setMain((String) data);
        return headline;
    }
}
