/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.ParameterHandler;
import retrofit2.RequestBuilder;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Utils;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.OPTIONS;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

final class ServiceMethod<T> {
    static final Pattern PARAM_NAME_REGEX;
    static final Pattern PARAM_URL_REGEX;
    private final HttpUrl baseUrl;
    final CallAdapter<?> callAdapter;
    final Call.Factory callFactory;
    private final MediaType contentType;
    private final boolean hasBody;
    private final okhttp3.Headers headers;
    private final String httpMethod;
    private final boolean isFormEncoded;
    private final boolean isMultipart;
    private final ParameterHandler<?>[] parameterHandlers;
    private final String relativeUrl;
    private final Converter<ResponseBody, T> responseConverter;

    static {
        PARAM_URL_REGEX = Pattern.compile("\\{([a-zA-Z][a-zA-Z0-9_-]*)\\}");
        PARAM_NAME_REGEX = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]*");
    }

    ServiceMethod(Builder<T> builder) {
        this.callFactory = builder.retrofit.callFactory();
        this.callAdapter = builder.callAdapter;
        this.baseUrl = builder.retrofit.baseUrl();
        this.responseConverter = builder.responseConverter;
        this.httpMethod = builder.httpMethod;
        this.relativeUrl = builder.relativeUrl;
        this.headers = builder.headers;
        this.contentType = builder.contentType;
        this.hasBody = builder.hasBody;
        this.isFormEncoded = builder.isFormEncoded;
        this.isMultipart = builder.isMultipart;
        this.parameterHandlers = builder.parameterHandlers;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static Class<?> boxIfPrimitive(Class<?> class_) {
        void var1_2;
        if (Boolean.TYPE == class_) {
            return var1_2;
        }
        if (Byte.TYPE == class_) {
            return Byte.class;
        }
        if (Character.TYPE == class_) {
            return Character.class;
        }
        if (Double.TYPE == class_) {
            return Double.class;
        }
        if (Float.TYPE == class_) {
            return Float.class;
        }
        if (Integer.TYPE == class_) {
            return Integer.class;
        }
        if (Long.TYPE == class_) {
            return Long.class;
        }
        Class<?> class_2 = class_;
        if (Short.TYPE != class_) {
            return var1_2;
        }
        return Short.class;
    }

    static Set<String> parsePathParameters(String object) {
        object = PARAM_URL_REGEX.matcher((CharSequence)object);
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        while (((Matcher)object).find()) {
            linkedHashSet.add(((Matcher)object).group(1));
        }
        return linkedHashSet;
    }

    /*
     * Enabled aggressive block sorting
     */
    Request toRequest(Object ... arrobject) throws IOException {
        ParameterHandler<?>[] arrparameterHandler;
        RequestBuilder requestBuilder = new RequestBuilder(this.httpMethod, this.baseUrl, this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart);
        int n = arrobject != null ? arrobject.length : 0;
        if (n != (arrparameterHandler = this.parameterHandlers).length) {
            throw new IllegalArgumentException("Argument count (" + n + ") doesn't match expected count (" + arrparameterHandler.length + ")");
        }
        int n2 = 0;
        while (n2 < n) {
            arrparameterHandler[n2].apply(requestBuilder, arrobject[n2]);
            ++n2;
        }
        return requestBuilder.build();
    }

    T toResponse(ResponseBody responseBody) throws IOException {
        return this.responseConverter.convert(responseBody);
    }

    static final class Builder<T> {
        CallAdapter<?> callAdapter;
        MediaType contentType;
        boolean gotBody;
        boolean gotField;
        boolean gotPart;
        boolean gotPath;
        boolean gotQuery;
        boolean gotUrl;
        boolean hasBody;
        okhttp3.Headers headers;
        String httpMethod;
        boolean isFormEncoded;
        boolean isMultipart;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        ParameterHandler<?>[] parameterHandlers;
        final Type[] parameterTypes;
        String relativeUrl;
        Set<String> relativeUrlParamNames;
        Converter<ResponseBody, T> responseConverter;
        Type responseType;
        final Retrofit retrofit;

        public Builder(Retrofit retrofit, Method method) {
            this.retrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        private CallAdapter<?> createCallAdapter() {
            Type type = this.method.getGenericReturnType();
            if (Utils.hasUnresolvableType(type)) {
                throw this.methodError("Method return type must not include a type variable or wildcard: %s", type);
            }
            if (type == Void.TYPE) {
                throw this.methodError("Service methods cannot return void.", new Object[0]);
            }
            Object object = this.method.getAnnotations();
            try {
                object = this.retrofit.callAdapter(type, (Annotation[])object);
                return object;
            }
            catch (RuntimeException runtimeException) {
                throw this.methodError(runtimeException, "Unable to create call adapter for %s", type);
            }
        }

        private Converter<ResponseBody, T> createResponseConverter() {
            Object object = this.method.getAnnotations();
            try {
                object = this.retrofit.responseBodyConverter(this.responseType, (Annotation[])object);
                return object;
            }
            catch (RuntimeException runtimeException) {
                throw this.methodError(runtimeException, "Unable to create converter for %s", this.responseType);
            }
        }

        private RuntimeException methodError(String string2, Object ... arrobject) {
            return this.methodError(null, string2, arrobject);
        }

        private RuntimeException methodError(Throwable throwable, String string2, Object ... arrobject) {
            string2 = String.format(string2, arrobject);
            return new IllegalArgumentException(string2 + "\n    for method " + this.method.getDeclaringClass().getSimpleName() + "." + this.method.getName(), throwable);
        }

        private RuntimeException parameterError(int n, String string2, Object ... arrobject) {
            return this.methodError(string2 + " (parameter #" + (n + 1) + ")", arrobject);
        }

        private RuntimeException parameterError(Throwable throwable, int n, String string2, Object ... arrobject) {
            return this.methodError(throwable, string2 + " (parameter #" + (n + 1) + ")", arrobject);
        }

        /*
         * Enabled aggressive block sorting
         */
        private okhttp3.Headers parseHeaders(String[] arrstring) {
            Headers.Builder builder = new Headers.Builder();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String string2 = arrstring[n2];
                int n3 = string2.indexOf(58);
                if (n3 == -1 || n3 == 0 || n3 == string2.length() - 1) {
                    throw this.methodError("@Headers value must be in the form \"Name: Value\". Found: \"%s\"", string2);
                }
                Object object = string2.substring(0, n3);
                string2 = string2.substring(n3 + 1).trim();
                if ("Content-Type".equalsIgnoreCase((String)object)) {
                    object = MediaType.parse(string2);
                    if (object == null) {
                        throw this.methodError("Malformed content type: %s", string2);
                    }
                    this.contentType = object;
                } else {
                    builder.add((String)object, string2);
                }
                ++n2;
            }
            return builder.build();
        }

        private void parseHttpMethodAndPath(String string2, String string3, boolean bl) {
            if (this.httpMethod != null) {
                throw this.methodError("Only one HTTP method is allowed. Found: %s and %s.", this.httpMethod, string2);
            }
            this.httpMethod = string2;
            this.hasBody = bl;
            if (string3.isEmpty()) {
                return;
            }
            int n = string3.indexOf(63);
            if (n != -1 && n < string3.length() - 1 && PARAM_URL_REGEX.matcher(string2 = string3.substring(n + 1)).find()) {
                throw this.methodError("URL query string \"%s\" must not have replace block. For dynamic query parameters use @Query.", string2);
            }
            this.relativeUrl = string3;
            this.relativeUrlParamNames = ServiceMethod.parsePathParameters(string3);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void parseMethodAnnotation(Annotation object) {
            if (object instanceof DELETE) {
                this.parseHttpMethodAndPath("DELETE", ((DELETE)object).value(), false);
                return;
            }
            if (object instanceof GET) {
                this.parseHttpMethodAndPath("GET", ((GET)object).value(), false);
                return;
            }
            if (object instanceof HEAD) {
                this.parseHttpMethodAndPath("HEAD", ((HEAD)object).value(), false);
                if (Void.class.equals((Object)this.responseType)) return;
                {
                    throw this.methodError("HEAD method must use Void as response type.", new Object[0]);
                }
            }
            if (object instanceof PATCH) {
                this.parseHttpMethodAndPath("PATCH", ((PATCH)object).value(), true);
                return;
            }
            if (object instanceof POST) {
                this.parseHttpMethodAndPath("POST", ((POST)object).value(), true);
                return;
            }
            if (object instanceof PUT) {
                this.parseHttpMethodAndPath("PUT", ((PUT)object).value(), true);
                return;
            }
            if (object instanceof OPTIONS) {
                this.parseHttpMethodAndPath("OPTIONS", ((OPTIONS)object).value(), false);
                return;
            }
            if (object instanceof HTTP) {
                object = (HTTP)object;
                this.parseHttpMethodAndPath(object.method(), object.path(), object.hasBody());
                return;
            }
            if (object instanceof Headers) {
                if (((Object)(object = ((Headers)object).value())).length == 0) {
                    throw this.methodError("@Headers annotation is empty.", new Object[0]);
                }
                this.headers = this.parseHeaders((String[])object);
                return;
            }
            if (object instanceof Multipart) {
                if (this.isFormEncoded) {
                    throw this.methodError("Only one encoding annotation is allowed.", new Object[0]);
                }
                this.isMultipart = true;
                return;
            }
            if (!(object instanceof FormUrlEncoded)) return;
            {
                if (this.isMultipart) {
                    throw this.methodError("Only one encoding annotation is allowed.", new Object[0]);
                }
            }
            this.isFormEncoded = true;
        }

        /*
         * Enabled aggressive block sorting
         */
        private ParameterHandler<?> parseParameter(int n, Type type, Annotation[] arrannotation) {
            ParameterHandler<?> parameterHandler = null;
            int n2 = arrannotation.length;
            for (int i = 0; i < n2; ++i) {
                ParameterHandler<?> parameterHandler2 = this.parseParameterAnnotation(n, type, arrannotation, arrannotation[i]);
                if (parameterHandler2 == null) continue;
                if (parameterHandler != null) {
                    throw this.parameterError(n, "Multiple Retrofit annotations found, only one allowed.", new Object[0]);
                }
                parameterHandler = parameterHandler2;
            }
            if (parameterHandler == null) {
                throw this.parameterError(n, "No Retrofit annotation found.", new Object[0]);
            }
            return parameterHandler;
        }

        private ParameterHandler<?> parseParameterAnnotation(int n, Type type, Annotation[] arrannotation, Annotation type2) {
            if (type2 instanceof Url) {
                if (this.gotUrl) {
                    throw this.parameterError(n, "Multiple @Url method annotations found.", new Object[0]);
                }
                if (this.gotPath) {
                    throw this.parameterError(n, "@Path parameters may not be used with @Url.", new Object[0]);
                }
                if (this.gotQuery) {
                    throw this.parameterError(n, "A @Url parameter must not come after a @Query", new Object[0]);
                }
                if (this.relativeUrl != null) {
                    throw this.parameterError(n, "@Url cannot be used with @%s URL", this.httpMethod);
                }
                this.gotUrl = true;
                if (type == HttpUrl.class || type == String.class || type == URI.class || type instanceof Class && "android.net.Uri".equals(((Class)type).getName())) {
                    return new ParameterHandler.RelativeUrl();
                }
                throw this.parameterError(n, "@Url must be okhttp3.HttpUrl, String, java.net.URI, or android.net.Uri type.", new Object[0]);
            }
            if (type2 instanceof Path) {
                if (this.gotQuery) {
                    throw this.parameterError(n, "A @Path parameter must not come after a @Query.", new Object[0]);
                }
                if (this.gotUrl) {
                    throw this.parameterError(n, "@Path parameters may not be used with @Url.", new Object[0]);
                }
                if (this.relativeUrl == null) {
                    throw this.parameterError(n, "@Path can only be used with relative url on @%s", this.httpMethod);
                }
                this.gotPath = true;
                type2 = (Path)((Object)type2);
                String string2 = type2.value();
                this.validatePathName(n, string2);
                return new ParameterHandler.Path(string2, this.retrofit.stringConverter(type, arrannotation), type2.encoded());
            }
            if (type2 instanceof Query) {
                Object object = (Query)((Object)type2);
                type2 = object.value();
                boolean bl = object.encoded();
                object = Utils.getRawType(type);
                this.gotQuery = true;
                if (Iterable.class.isAssignableFrom((Class<?>)object)) {
                    if (!(type instanceof ParameterizedType)) {
                        throw this.parameterError(n, ((Class)object).getSimpleName() + " must include generic type (e.g., " + ((Class)object).getSimpleName() + "<String>)", new Object[0]);
                    }
                    type = Utils.getParameterUpperBound(0, (ParameterizedType)type);
                    return new ParameterHandler.Query((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl).iterable();
                }
                if (((Class)object).isArray()) {
                    type = ServiceMethod.boxIfPrimitive(((Class)object).getComponentType());
                    return new ParameterHandler.Query((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl).array();
                }
                return new ParameterHandler.Query((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl);
            }
            if (type2 instanceof QueryMap) {
                Type type3 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom((Class<?>)type3)) {
                    throw this.parameterError(n, "@QueryMap parameter type must be Map.", new Object[0]);
                }
                if (!((type = Utils.getSupertype(type, type3, Map.class)) instanceof ParameterizedType)) {
                    throw this.parameterError(n, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                type3 = Utils.getParameterUpperBound(0, (ParameterizedType)(type = (ParameterizedType)type));
                if (String.class != type3) {
                    throw this.parameterError(n, "@QueryMap keys must be of type String: " + type3, new Object[0]);
                }
                type = Utils.getParameterUpperBound(1, type);
                return new ParameterHandler.QueryMap(this.retrofit.stringConverter(type, arrannotation), ((QueryMap)((Object)type2)).encoded());
            }
            if (type2 instanceof Header) {
                type2 = ((Header)((Object)type2)).value();
                Class<?> class_ = Utils.getRawType(type);
                if (Iterable.class.isAssignableFrom(class_)) {
                    if (!(type instanceof ParameterizedType)) {
                        throw this.parameterError(n, class_.getSimpleName() + " must include generic type (e.g., " + class_.getSimpleName() + "<String>)", new Object[0]);
                    }
                    type = Utils.getParameterUpperBound(0, (ParameterizedType)type);
                    return new ParameterHandler.Header((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation)).iterable();
                }
                if (class_.isArray()) {
                    type = ServiceMethod.boxIfPrimitive(class_.getComponentType());
                    return new ParameterHandler.Header((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation)).array();
                }
                return new ParameterHandler.Header((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation));
            }
            if (type2 instanceof HeaderMap) {
                type2 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom((Class<?>)type2)) {
                    throw this.parameterError(n, "@HeaderMap parameter type must be Map.", new Object[0]);
                }
                if (!((type = Utils.getSupertype(type, type2, Map.class)) instanceof ParameterizedType)) {
                    throw this.parameterError(n, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                type2 = Utils.getParameterUpperBound(0, type = (ParameterizedType)type);
                if (String.class != type2) {
                    throw this.parameterError(n, "@HeaderMap keys must be of type String: " + type2, new Object[0]);
                }
                type = Utils.getParameterUpperBound(1, type);
                return new ParameterHandler.HeaderMap(this.retrofit.stringConverter(type, arrannotation));
            }
            if (type2 instanceof Field) {
                if (!this.isFormEncoded) {
                    throw this.parameterError(n, "@Field parameters can only be used with form encoding.", new Object[0]);
                }
                Class<?> class_ = (Field)((Object)type2);
                type2 = class_.value();
                boolean bl = class_.encoded();
                this.gotField = true;
                class_ = Utils.getRawType(type);
                if (Iterable.class.isAssignableFrom(class_)) {
                    if (!(type instanceof ParameterizedType)) {
                        throw this.parameterError(n, class_.getSimpleName() + " must include generic type (e.g., " + class_.getSimpleName() + "<String>)", new Object[0]);
                    }
                    type = Utils.getParameterUpperBound(0, (ParameterizedType)type);
                    return new ParameterHandler.Field((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl).iterable();
                }
                if (class_.isArray()) {
                    type = ServiceMethod.boxIfPrimitive(class_.getComponentType());
                    return new ParameterHandler.Field((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl).array();
                }
                return new ParameterHandler.Field((String)((Object)type2), this.retrofit.stringConverter(type, arrannotation), bl);
            }
            if (type2 instanceof FieldMap) {
                if (!this.isFormEncoded) {
                    throw this.parameterError(n, "@FieldMap parameters can only be used with form encoding.", new Object[0]);
                }
                Type type4 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom((Class<?>)type4)) {
                    throw this.parameterError(n, "@FieldMap parameter type must be Map.", new Object[0]);
                }
                if (!((type = Utils.getSupertype(type, type4, Map.class)) instanceof ParameterizedType)) {
                    throw this.parameterError(n, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                type4 = Utils.getParameterUpperBound(0, type = (ParameterizedType)type);
                if (String.class != type4) {
                    throw this.parameterError(n, "@FieldMap keys must be of type String: " + type4, new Object[0]);
                }
                type = Utils.getParameterUpperBound(1, type);
                type = this.retrofit.stringConverter(type, arrannotation);
                this.gotField = true;
                return new ParameterHandler.FieldMap(type, ((FieldMap)((Object)type2)).encoded());
            }
            if (type2 instanceof Part) {
                if (!this.isMultipart) {
                    throw this.parameterError(n, "@Part parameters can only be used with multipart encoding.", new Object[0]);
                }
                Object object = (Part)((Object)type2);
                this.gotPart = true;
                String string3 = object.value();
                type2 = Utils.getRawType(type);
                if (string3.isEmpty()) {
                    if (Iterable.class.isAssignableFrom((Class<?>)type2)) {
                        if (!(type instanceof ParameterizedType)) {
                            throw this.parameterError(n, type2.getSimpleName() + " must include generic type (e.g., " + type2.getSimpleName() + "<String>)", new Object[0]);
                        }
                        if (!MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(Utils.getParameterUpperBound(0, (ParameterizedType)type)))) {
                            throw this.parameterError(n, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                        return ParameterHandler.RawPart.INSTANCE.iterable();
                    }
                    if (type2.isArray()) {
                        if (!MultipartBody.Part.class.isAssignableFrom(type2.getComponentType())) {
                            throw this.parameterError(n, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                        }
                        return ParameterHandler.RawPart.INSTANCE.array();
                    }
                    if (MultipartBody.Part.class.isAssignableFrom((Class<?>)type2)) {
                        return ParameterHandler.RawPart.INSTANCE;
                    }
                    throw this.parameterError(n, "@Part annotation must supply a name or use MultipartBody.Part parameter type.", new Object[0]);
                }
                object = okhttp3.Headers.of("Content-Disposition", "form-data; name=\"" + string3 + "\"", "Content-Transfer-Encoding", object.encoding());
                if (Iterable.class.isAssignableFrom((Class<?>)type2)) {
                    if (!(type instanceof ParameterizedType)) {
                        throw this.parameterError(n, type2.getSimpleName() + " must include generic type (e.g., " + type2.getSimpleName() + "<String>)", new Object[0]);
                    }
                    if (MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(type = Utils.getParameterUpperBound(0, (ParameterizedType)type)))) {
                        throw this.parameterError(n, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                    }
                    return new ParameterHandler.Part((okhttp3.Headers)object, this.retrofit.requestBodyConverter(type, arrannotation, this.methodAnnotations)).iterable();
                }
                if (type2.isArray()) {
                    type = ServiceMethod.boxIfPrimitive(type2.getComponentType());
                    if (MultipartBody.Part.class.isAssignableFrom((Class<?>)type)) {
                        throw this.parameterError(n, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                    }
                    return new ParameterHandler.Part((okhttp3.Headers)object, this.retrofit.requestBodyConverter(type, arrannotation, this.methodAnnotations)).array();
                }
                if (MultipartBody.Part.class.isAssignableFrom((Class<?>)type2)) {
                    throw this.parameterError(n, "@Part parameters using the MultipartBody.Part must not include a part name in the annotation.", new Object[0]);
                }
                return new ParameterHandler.Part((okhttp3.Headers)object, this.retrofit.requestBodyConverter(type, arrannotation, this.methodAnnotations));
            }
            if (type2 instanceof PartMap) {
                if (!this.isMultipart) {
                    throw this.parameterError(n, "@PartMap parameters can only be used with multipart encoding.", new Object[0]);
                }
                this.gotPart = true;
                Type type5 = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom((Class<?>)type5)) {
                    throw this.parameterError(n, "@PartMap parameter type must be Map.", new Object[0]);
                }
                if (!((type = Utils.getSupertype(type, type5, Map.class)) instanceof ParameterizedType)) {
                    throw this.parameterError(n, "Map must include generic types (e.g., Map<String, String>)", new Object[0]);
                }
                type5 = Utils.getParameterUpperBound(0, type = (ParameterizedType)type);
                if (String.class != type5) {
                    throw this.parameterError(n, "@PartMap keys must be of type String: " + type5, new Object[0]);
                }
                if (MultipartBody.Part.class.isAssignableFrom(Utils.getRawType(type = Utils.getParameterUpperBound(1, (ParameterizedType)type)))) {
                    throw this.parameterError(n, "@PartMap values cannot be MultipartBody.Part. Use @Part List<Part> or a different value type instead.", new Object[0]);
                }
                return new ParameterHandler.PartMap(this.retrofit.requestBodyConverter(type, arrannotation, this.methodAnnotations), ((PartMap)((Object)type2)).encoding());
            }
            if (type2 instanceof Body) {
                if (this.isFormEncoded || this.isMultipart) {
                    throw this.parameterError(n, "@Body parameters cannot be used with form or multi-part encoding.", new Object[0]);
                }
                if (this.gotBody) {
                    throw this.parameterError(n, "Multiple @Body method annotations found.", new Object[0]);
                }
                try {
                    arrannotation = this.retrofit.requestBodyConverter(type, arrannotation, this.methodAnnotations);
                }
                catch (RuntimeException runtimeException) {
                    throw this.parameterError(runtimeException, n, "Unable to create @Body converter for %s", type);
                }
                this.gotBody = true;
                return new ParameterHandler.Body(arrannotation);
            }
            return null;
        }

        private void validatePathName(int n, String string2) {
            if (!PARAM_NAME_REGEX.matcher(string2).matches()) {
                throw this.parameterError(n, "@Path parameter name must match %s. Found: %s", PARAM_URL_REGEX.pattern(), string2);
            }
            if (!this.relativeUrlParamNames.contains(string2)) {
                throw this.parameterError(n, "URL \"%s\" does not contain \"{%s}\".", this.relativeUrl, string2);
            }
        }

        public ServiceMethod build() {
            int n;
            this.callAdapter = this.createCallAdapter();
            this.responseType = this.callAdapter.responseType();
            if (this.responseType == Response.class || this.responseType == okhttp3.Response.class) {
                throw this.methodError("'" + Utils.getRawType(this.responseType).getName() + "' is not a valid response body type. Did you mean ResponseBody?", new Object[0]);
            }
            this.responseConverter = this.createResponseConverter();
            Object object = this.methodAnnotations;
            int n2 = ((Annotation[])object).length;
            for (n = 0; n < n2; ++n) {
                this.parseMethodAnnotation(object[n]);
            }
            if (this.httpMethod == null) {
                throw this.methodError("HTTP method annotation is required (e.g., @GET, @POST, etc.).", new Object[0]);
            }
            if (!this.hasBody) {
                if (this.isMultipart) {
                    throw this.methodError("Multipart can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                }
                if (this.isFormEncoded) {
                    throw this.methodError("FormUrlEncoded can only be specified on HTTP methods with request body (e.g., @POST).", new Object[0]);
                }
            }
            n2 = this.parameterAnnotationsArray.length;
            this.parameterHandlers = new ParameterHandler[n2];
            for (n = 0; n < n2; ++n) {
                object = this.parameterTypes[n];
                if (Utils.hasUnresolvableType((Type)object)) {
                    throw this.parameterError(n, "Parameter type must not include a type variable or wildcard: %s", object);
                }
                Annotation[] arrannotation = this.parameterAnnotationsArray[n];
                if (arrannotation == null) {
                    throw this.parameterError(n, "No Retrofit annotation found.", new Object[0]);
                }
                this.parameterHandlers[n] = this.parseParameter(n, (Type)object, arrannotation);
            }
            if (this.relativeUrl == null && !this.gotUrl) {
                throw this.methodError("Missing either @%s URL or @Url parameter.", this.httpMethod);
            }
            if (!this.isFormEncoded && !this.isMultipart && !this.hasBody && this.gotBody) {
                throw this.methodError("Non-body HTTP method cannot contain @Body.", new Object[0]);
            }
            if (this.isFormEncoded && !this.gotField) {
                throw this.methodError("Form-encoded method must contain at least one @Field.", new Object[0]);
            }
            if (this.isMultipart && !this.gotPart) {
                throw this.methodError("Multipart method must contain at least one @Part.", new Object[0]);
            }
            return new ServiceMethod(this);
        }
    }

}

