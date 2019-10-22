/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.RequestBuilder;
import retrofit2.Utils;

abstract class ParameterHandler<T> {
    ParameterHandler() {
    }

    abstract void apply(RequestBuilder var1, T var2) throws IOException;

    final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            void apply(RequestBuilder requestBuilder, Object object) throws IOException {
                if (object != null) {
                    int n = Array.getLength(object);
                    for (int i = 0; i < n; ++i) {
                        ParameterHandler.this.apply(requestBuilder, Array.get(object, i));
                    }
                }
            }
        };
    }

    final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            void apply(RequestBuilder requestBuilder, Iterable<T> iterator) throws IOException {
                if (iterator != null) {
                    iterator = iterator.iterator();
                    while (iterator.hasNext()) {
                        Object t = iterator.next();
                        ParameterHandler.this.apply(requestBuilder, t);
                    }
                }
            }
        };
    }

    static final class Body<T>
    extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;

        Body(Converter<T, RequestBody> converter) {
            this.converter = converter;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) {
            if (t == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                RequestBody requestBody = this.converter.convert(t);
                requestBuilder.setBody(requestBody);
                return;
            }
            catch (IOException iOException) {
                throw new RuntimeException("Unable to convert " + t + " to RequestBody", iOException);
            }
        }
    }

    static final class Field<T>
    extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Field(String string2, Converter<T, String> converter, boolean bl) {
            this.name = Utils.checkNotNull(string2, "name == null");
            this.valueConverter = converter;
            this.encoded = bl;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) throws IOException {
            if (t == null) {
                return;
            }
            requestBuilder.addFormField(this.name, this.valueConverter.convert(t), this.encoded);
        }
    }

    static final class FieldMap<T>
    extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        FieldMap(Converter<T, String> converter, boolean bl) {
            this.valueConverter = converter;
            this.encoded = bl;
        }

        @Override
        void apply(RequestBuilder requestBuilder, Map<String, T> object) throws IOException {
            if (object == null) {
                throw new IllegalArgumentException("Field map was null.");
            }
            for (Map.Entry entry : object.entrySet()) {
                String string2 = (String)entry.getKey();
                if (string2 == null) {
                    throw new IllegalArgumentException("Field map contained null key.");
                }
                Object entry2 = entry.getValue();
                if (entry2 == null) {
                    throw new IllegalArgumentException("Field map contained null value for key '" + string2 + "'.");
                }
                requestBuilder.addFormField(string2, this.valueConverter.convert(entry2), this.encoded);
            }
        }
    }

    static final class Header<T>
    extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        Header(String string2, Converter<T, String> converter) {
            this.name = Utils.checkNotNull(string2, "name == null");
            this.valueConverter = converter;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) throws IOException {
            if (t == null) {
                return;
            }
            requestBuilder.addHeader(this.name, this.valueConverter.convert(t));
        }
    }

    static final class HeaderMap<T>
    extends ParameterHandler<Map<String, T>> {
        private final Converter<T, String> valueConverter;

        HeaderMap(Converter<T, String> converter) {
            this.valueConverter = converter;
        }

        @Override
        void apply(RequestBuilder requestBuilder, Map<String, T> object) throws IOException {
            if (object == null) {
                throw new IllegalArgumentException("Header map was null.");
            }
            for (Map.Entry entry : object.entrySet()) {
                String string2 = (String)entry.getKey();
                if (string2 == null) {
                    throw new IllegalArgumentException("Header map contained null key.");
                }
                Object entry2 = entry.getValue();
                if (entry2 == null) {
                    throw new IllegalArgumentException("Header map contained null value for key '" + string2 + "'.");
                }
                requestBuilder.addHeader(string2, this.valueConverter.convert(entry2));
            }
        }
    }

    static final class Part<T>
    extends ParameterHandler<T> {
        private final Converter<T, RequestBody> converter;
        private final Headers headers;

        Part(Headers headers, Converter<T, RequestBody> converter) {
            this.headers = headers;
            this.converter = converter;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) {
            if (t == null) {
                return;
            }
            try {
                RequestBody requestBody = this.converter.convert(t);
                requestBuilder.addPart(this.headers, requestBody);
                return;
            }
            catch (IOException iOException) {
                throw new RuntimeException("Unable to convert " + t + " to RequestBody", iOException);
            }
        }
    }

    static final class PartMap<T>
    extends ParameterHandler<Map<String, T>> {
        private final String transferEncoding;
        private final Converter<T, RequestBody> valueConverter;

        PartMap(Converter<T, RequestBody> converter, String string2) {
            this.valueConverter = converter;
            this.transferEncoding = string2;
        }

        @Override
        void apply(RequestBuilder requestBuilder, Map<String, T> object) throws IOException {
            if (object == null) {
                throw new IllegalArgumentException("Part map was null.");
            }
            for (Map.Entry entry : object.entrySet()) {
                String string2 = (String)entry.getKey();
                if (string2 == null) {
                    throw new IllegalArgumentException("Part map contained null key.");
                }
                Object entry2 = entry.getValue();
                if (entry2 == null) {
                    throw new IllegalArgumentException("Part map contained null value for key '" + string2 + "'.");
                }
                requestBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + string2 + "\"", "Content-Transfer-Encoding", this.transferEncoding), this.valueConverter.convert(entry2));
            }
        }
    }

    static final class Path<T>
    extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Path(String string2, Converter<T, String> converter, boolean bl) {
            this.name = Utils.checkNotNull(string2, "name == null");
            this.valueConverter = converter;
            this.encoded = bl;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) throws IOException {
            if (t == null) {
                throw new IllegalArgumentException("Path parameter \"" + this.name + "\" value must not be null.");
            }
            requestBuilder.addPathParam(this.name, this.valueConverter.convert(t), this.encoded);
        }
    }

    static final class Query<T>
    extends ParameterHandler<T> {
        private final boolean encoded;
        private final String name;
        private final Converter<T, String> valueConverter;

        Query(String string2, Converter<T, String> converter, boolean bl) {
            this.name = Utils.checkNotNull(string2, "name == null");
            this.valueConverter = converter;
            this.encoded = bl;
        }

        @Override
        void apply(RequestBuilder requestBuilder, T t) throws IOException {
            if (t == null) {
                return;
            }
            requestBuilder.addQueryParam(this.name, this.valueConverter.convert(t), this.encoded);
        }
    }

    static final class QueryMap<T>
    extends ParameterHandler<Map<String, T>> {
        private final boolean encoded;
        private final Converter<T, String> valueConverter;

        QueryMap(Converter<T, String> converter, boolean bl) {
            this.valueConverter = converter;
            this.encoded = bl;
        }

        @Override
        void apply(RequestBuilder requestBuilder, Map<String, T> object) throws IOException {
            if (object == null) {
                throw new IllegalArgumentException("Query map was null.");
            }
            for (Map.Entry entry : object.entrySet()) {
                String string2 = (String)entry.getKey();
                if (string2 == null) {
                    throw new IllegalArgumentException("Query map contained null key.");
                }
                Object entry2 = entry.getValue();
                if (entry2 == null) {
                    throw new IllegalArgumentException("Query map contained null value for key '" + string2 + "'.");
                }
                requestBuilder.addQueryParam(string2, this.valueConverter.convert(entry2), this.encoded);
            }
        }
    }

    static final class RawPart
    extends ParameterHandler<MultipartBody.Part> {
        static final RawPart INSTANCE = new RawPart();

        private RawPart() {
        }

        @Override
        void apply(RequestBuilder requestBuilder, MultipartBody.Part part) throws IOException {
            if (part != null) {
                requestBuilder.addPart(part);
            }
        }
    }

    static final class RelativeUrl
    extends ParameterHandler<Object> {
        RelativeUrl() {
        }

        @Override
        void apply(RequestBuilder requestBuilder, Object object) {
            requestBuilder.setRelativeUrl(object);
        }
    }

}

