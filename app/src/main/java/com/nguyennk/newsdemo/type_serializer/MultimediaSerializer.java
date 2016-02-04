package com.nguyennk.newsdemo.type_serializer;

import com.activeandroid.serializer.TypeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nguyennk.newsdemo.model.Article;

import java.util.List;

/**
 * Created by nguye on 2/3/2016.
 */
public class MultimediaSerializer extends TypeSerializer{
    private Gson gson = new GsonBuilder().create();
    @Override
    public Class<?> getDeserializedType() {
        return List.class;
//        return new ArrayList<Article.Multimedia>().getClass();
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

        return gson.toJson(data);
    }

    @Override
    public Object deserialize(Object data) {
        if (data == null) {
            return null;
        }

        return gson.fromJson((String) data, new TypeToken<List<Article.Multimedia>>(){}.getType());
    }
}
