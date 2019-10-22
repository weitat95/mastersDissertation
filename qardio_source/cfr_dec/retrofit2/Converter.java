/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public interface Converter<F, T> {
    public T convert(F var1) throws IOException;

    public static abstract class Factory {
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] arrannotation, Annotation[] arrannotation2, Retrofit retrofit) {
            return null;
        }

        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] arrannotation, Retrofit retrofit) {
            return null;
        }

        public Converter<?, String> stringConverter(Type type, Annotation[] arrannotation, Retrofit retrofit) {
            return null;
        }
    }

}

