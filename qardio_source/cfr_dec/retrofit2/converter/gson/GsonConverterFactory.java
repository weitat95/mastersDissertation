/*
 * Decompiled with CFR 0.147.
 */
package retrofit2.converter.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonRequestBodyConverter;
import retrofit2.converter.gson.GsonResponseBodyConverter;

public final class GsonConverterFactory
extends Converter.Factory {
    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static GsonConverterFactory create() {
        return GsonConverterFactory.create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type object, Annotation[] arrannotation, Annotation[] arrannotation2, Retrofit retrofit) {
        object = this.gson.getAdapter(TypeToken.get((Type)object));
        return new GsonRequestBodyConverter(this.gson, object);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type object, Annotation[] arrannotation, Retrofit retrofit) {
        object = this.gson.getAdapter(TypeToken.get((Type)object));
        return new GsonResponseBodyConverter(this.gson, object);
    }
}

