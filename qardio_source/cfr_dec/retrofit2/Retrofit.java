/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.BuiltInConverters;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.OkHttpCall;
import retrofit2.Platform;
import retrofit2.ServiceMethod;
import retrofit2.Utils;

public final class Retrofit {
    private final List<CallAdapter.Factory> adapterFactories;
    private final HttpUrl baseUrl;
    private final Call.Factory callFactory;
    private final Executor callbackExecutor;
    private final List<Converter.Factory> converterFactories;
    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<Method, ServiceMethod>();
    private final boolean validateEagerly;

    Retrofit(Call.Factory factory, HttpUrl httpUrl, List<Converter.Factory> list, List<CallAdapter.Factory> list2, Executor executor, boolean bl) {
        this.callFactory = factory;
        this.baseUrl = httpUrl;
        this.converterFactories = Collections.unmodifiableList(list);
        this.adapterFactories = Collections.unmodifiableList(list2);
        this.callbackExecutor = executor;
        this.validateEagerly = bl;
    }

    private void eagerlyValidateMethods(Class<?> arrmethod) {
        Platform platform = Platform.get();
        for (Method method : arrmethod.getDeclaredMethods()) {
            if (platform.isDefaultMethod(method)) continue;
            this.loadServiceMethod(method);
        }
    }

    public HttpUrl baseUrl() {
        return this.baseUrl;
    }

    public CallAdapter<?> callAdapter(Type type, Annotation[] arrannotation) {
        return this.nextCallAdapter(null, type, arrannotation);
    }

    public Call.Factory callFactory() {
        return this.callFactory;
    }

    public <T> T create(final Class<T> class_) {
        Utils.validateServiceInterface(class_);
        if (this.validateEagerly) {
            this.eagerlyValidateMethods(class_);
        }
        ClassLoader classLoader = class_.getClassLoader();
        InvocationHandler invocationHandler = new InvocationHandler(){
            private final Platform platform = Platform.get();

            @Override
            public Object invoke(Object object, Method object2, Object ... arrobject) throws Throwable {
                if (((Method)object2).getDeclaringClass() == Object.class) {
                    return ((Method)object2).invoke(this, arrobject);
                }
                if (this.platform.isDefaultMethod((Method)object2)) {
                    return this.platform.invokeDefaultMethod((Method)object2, class_, object, arrobject);
                }
                object = Retrofit.this.loadServiceMethod((Method)object2);
                object2 = new OkHttpCall(object, arrobject);
                return ((ServiceMethod)object).callAdapter.adapt(object2);
            }
        };
        return (T)Proxy.newProxyInstance(classLoader, new Class[]{class_}, invocationHandler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ServiceMethod loadServiceMethod(Method method) {
        Map<Method, ServiceMethod> map = this.serviceMethodCache;
        synchronized (map) {
            ServiceMethod serviceMethod;
            ServiceMethod serviceMethod2 = serviceMethod = this.serviceMethodCache.get(method);
            if (serviceMethod == null) {
                serviceMethod2 = new ServiceMethod.Builder(this, method).build();
                this.serviceMethodCache.put(method, serviceMethod2);
            }
            return serviceMethod2;
        }
    }

    public CallAdapter<?> nextCallAdapter(CallAdapter.Factory factory, Type object, Annotation[] arrannotation) {
        int n;
        int n2;
        Utils.checkNotNull(object, "returnType == null");
        Utils.checkNotNull(arrannotation, "annotations == null");
        int n3 = this.adapterFactories.size();
        for (n = n2 = this.adapterFactories.indexOf(factory) + 1; n < n3; ++n) {
            CallAdapter<?> callAdapter = this.adapterFactories.get(n).get((Type)object, arrannotation, this);
            if (callAdapter == null) continue;
            return callAdapter;
        }
        object = new StringBuilder("Could not locate call adapter for ").append(object).append(".\n");
        if (factory != null) {
            ((StringBuilder)object).append("  Skipped:");
            for (n = 0; n < n2; ++n) {
                ((StringBuilder)object).append("\n   * ").append(this.adapterFactories.get(n).getClass().getName());
            }
            ((StringBuilder)object).append('\n');
        }
        ((StringBuilder)object).append("  Tried:");
        n = this.adapterFactories.size();
        while (n2 < n) {
            ((StringBuilder)object).append("\n   * ").append(this.adapterFactories.get(n2).getClass().getName());
            ++n2;
        }
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public <T> Converter<T, RequestBody> nextRequestBodyConverter(Converter.Factory factory, Type object, Annotation[] arrannotation, Annotation[] arrannotation2) {
        int n;
        int n2;
        Utils.checkNotNull(object, "type == null");
        Utils.checkNotNull(arrannotation, "parameterAnnotations == null");
        Utils.checkNotNull(arrannotation2, "methodAnnotations == null");
        int n3 = this.converterFactories.size();
        for (n2 = n = this.converterFactories.indexOf(factory) + 1; n2 < n3; ++n2) {
            Converter<?, RequestBody> converter = this.converterFactories.get(n2).requestBodyConverter((Type)object, arrannotation, arrannotation2, this);
            if (converter == null) continue;
            return converter;
        }
        object = new StringBuilder("Could not locate RequestBody converter for ").append(object).append(".\n");
        if (factory != null) {
            ((StringBuilder)object).append("  Skipped:");
            for (n2 = 0; n2 < n; ++n2) {
                ((StringBuilder)object).append("\n   * ").append(this.converterFactories.get(n2).getClass().getName());
            }
            ((StringBuilder)object).append('\n');
        }
        ((StringBuilder)object).append("  Tried:");
        n2 = this.converterFactories.size();
        while (n < n2) {
            ((StringBuilder)object).append("\n   * ").append(this.converterFactories.get(n).getClass().getName());
            ++n;
        }
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public <T> Converter<ResponseBody, T> nextResponseBodyConverter(Converter.Factory factory, Type object, Annotation[] arrannotation) {
        int n;
        int n2;
        Utils.checkNotNull(object, "type == null");
        Utils.checkNotNull(arrannotation, "annotations == null");
        int n3 = this.converterFactories.size();
        for (n = n2 = this.converterFactories.indexOf(factory) + 1; n < n3; ++n) {
            Converter<ResponseBody, ?> converter = this.converterFactories.get(n).responseBodyConverter((Type)object, arrannotation, this);
            if (converter == null) continue;
            return converter;
        }
        object = new StringBuilder("Could not locate ResponseBody converter for ").append(object).append(".\n");
        if (factory != null) {
            ((StringBuilder)object).append("  Skipped:");
            for (n = 0; n < n2; ++n) {
                ((StringBuilder)object).append("\n   * ").append(this.converterFactories.get(n).getClass().getName());
            }
            ((StringBuilder)object).append('\n');
        }
        ((StringBuilder)object).append("  Tried:");
        n = this.converterFactories.size();
        while (n2 < n) {
            ((StringBuilder)object).append("\n   * ").append(this.converterFactories.get(n2).getClass().getName());
            ++n2;
        }
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public <T> Converter<T, RequestBody> requestBodyConverter(Type type, Annotation[] arrannotation, Annotation[] arrannotation2) {
        return this.nextRequestBodyConverter(null, type, arrannotation, arrannotation2);
    }

    public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] arrannotation) {
        return this.nextResponseBodyConverter(null, type, arrannotation);
    }

    public <T> Converter<T, String> stringConverter(Type type, Annotation[] arrannotation) {
        Utils.checkNotNull(type, "type == null");
        Utils.checkNotNull(arrannotation, "annotations == null");
        int n = this.converterFactories.size();
        for (int i = 0; i < n; ++i) {
            Converter<?, String> converter = this.converterFactories.get(i).stringConverter(type, arrannotation, this);
            if (converter == null) continue;
            return converter;
        }
        return BuiltInConverters.ToStringConverter.INSTANCE;
    }

    public static final class Builder {
        private List<CallAdapter.Factory> adapterFactories;
        private HttpUrl baseUrl;
        private Call.Factory callFactory;
        private Executor callbackExecutor;
        private List<Converter.Factory> converterFactories = new ArrayList<Converter.Factory>();
        private Platform platform;
        private boolean validateEagerly;

        public Builder() {
            this(Platform.get());
        }

        Builder(Platform platform) {
            this.adapterFactories = new ArrayList<CallAdapter.Factory>();
            this.platform = platform;
            this.converterFactories.add(new BuiltInConverters());
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(Utils.checkNotNull(factory, "factory == null"));
            return this;
        }

        public Builder baseUrl(String string2) {
            Utils.checkNotNull(string2, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(string2);
            if (httpUrl == null) {
                throw new IllegalArgumentException("Illegal URL: " + string2);
            }
            return this.baseUrl(httpUrl);
        }

        public Builder baseUrl(HttpUrl httpUrl) {
            Utils.checkNotNull(httpUrl, "baseUrl == null");
            List<String> list = httpUrl.pathSegments();
            if (!"".equals(list.get(list.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + httpUrl);
            }
            this.baseUrl = httpUrl;
            return this;
        }

        public Retrofit build() {
            Object object;
            if (this.baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            Object object2 = this.callFactory;
            Call.Factory factory = object2;
            if (object2 == null) {
                factory = new OkHttpClient();
            }
            object2 = object = this.callbackExecutor;
            if (object == null) {
                object2 = this.platform.defaultCallbackExecutor();
            }
            object = new ArrayList<CallAdapter.Factory>(this.adapterFactories);
            object.add(this.platform.defaultCallAdapterFactory((Executor)object2));
            ArrayList<Converter.Factory> arrayList = new ArrayList<Converter.Factory>(this.converterFactories);
            return new Retrofit(factory, this.baseUrl, arrayList, (List<CallAdapter.Factory>)object, (Executor)object2, this.validateEagerly);
        }

        public Builder callFactory(Call.Factory factory) {
            this.callFactory = Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        public Builder client(OkHttpClient okHttpClient) {
            return this.callFactory(Utils.checkNotNull(okHttpClient, "client == null"));
        }
    }

}

