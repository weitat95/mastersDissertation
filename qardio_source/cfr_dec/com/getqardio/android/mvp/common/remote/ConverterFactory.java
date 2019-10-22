/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.gson_annotation.FieldPureJson;
import com.getqardio.android.mvp.common.gson_annotation.PojoWithPureJson;
import com.getqardio.android.mvp.common.remote.ConverterFactory$$Lambda$1;
import com.getqardio.android.mvp.common.remote.ConverterFactory$$Lambda$2;
import com.getqardio.android.mvp.common.remote.ConverterFactory$$Lambda$3;
import com.getqardio.android.mvp.common.remote.ConverterFactory$$Lambda$4;
import com.getqardio.android.mvp.common.remote.RequestBodyPOJOSerializer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ConverterFactory
extends Converter.Factory {
    private final Gson gson;

    private ConverterFactory(Gson gson) {
        this.gson = gson;
    }

    static /* synthetic */ boolean access$000(ConverterFactory converterFactory, Class class_, Class class_2) {
        return converterFactory.classMarkedWithAnnotation(class_, class_2);
    }

    static /* synthetic */ Object access$100(ConverterFactory converterFactory, Object object, JsonObject jsonObject) {
        return converterFactory.convertDataJsonInObject(object, jsonObject);
    }

    private boolean classMarkedWithAnnotation(Class class_, Class class_2) {
        return Observable.fromArray(class_.getAnnotations()).map(ConverterFactory$$Lambda$1.lambdaFactory$()).filter(ConverterFactory$$Lambda$2.lambdaFactory$(class_2)).isEmpty().blockingGet() == false;
    }

    private <T> T convertDataJsonInObject(Object object, JsonObject jsonObject) {
        if (this.classMarkedWithAnnotation(object.getClass(), PojoWithPureJson.class)) {
            this.injectDataInPureJsonField(object, jsonObject);
        }
        return (T)object;
    }

    public static Converter.Factory create(Gson gson) {
        return new ConverterFactory(gson);
    }

    private void doInjectDataInField(Object object, JsonObject object2, Field field) {
        object2 = ((JsonObject)object2).get(field.getAnnotation(SerializedName.class).value()).getAsString();
        object2 = this.gson.fromJson(((String)object2).substring(0, ((String)object2).length()), field.getType());
        try {
            field.set(object, object2);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
    }

    private boolean fieldMarkedWithAnnotation(Field field, Class class_) {
        return Observable.fromArray(field.getAnnotations()).map(ConverterFactory$$Lambda$3.lambdaFactory$()).filter(ConverterFactory$$Lambda$4.lambdaFactory$(class_)).isEmpty().blockingGet() == false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void injectDataInPureJsonField(Object var1_1, JsonElement var2_3) {
        block11: {
            var6_4 = var1_1.getClass().getDeclaredFields();
            var5_5 = var6_4.length;
            var3_6 = 0;
            block2 : while (var3_6 < var5_5) {
                var7_8 = var6_4[var3_6];
                if (Modifier.isPublic(var7_8.getModifiers())) {
                    if (this.fieldMarkedWithAnnotation(var7_8, FieldPureJson.class)) {
                        this.doInjectDataInField(var1_1, (JsonObject)var2_3, var7_8);
                    } else if (!var7_8.getType().isPrimitive() && !var7_8.getType().getName().startsWith("java.lang")) {
                        try {
                            var9_10 /* !! */  = var7_8.getAnnotation(SerializedName.class).value();
                            var8_9 = var2_3.getAsJsonObject().get(var9_10 /* !! */ );
                            if (var8_9.isJsonArray()) {
                                var4_7 = 0;
                                break block11;
                            }
                            this.injectDataInPureJsonField(var7_8.get(var1_1), var8_9.getAsJsonObject().get(var9_10 /* !! */ ));
                        }
                        catch (IllegalAccessException var1_2) {
                            throw new RuntimeException(var1_2);
                        }
                    }
                }
                do {
                    ++var3_6;
                    continue block2;
                    break;
                } while (true);
            }
            return;
        }
        do {
            if (var4_7 >= var8_9.getAsJsonArray().size()) ** continue;
            var9_10 /* !! */  = ((List)var7_8.get(var1_1)).get(var4_7);
            if (var9_10 /* !! */  != null) {
                this.injectDataInPureJsonField(var9_10 /* !! */ , var8_9.getAsJsonArray().get(var4_7));
            }
            ++var4_7;
        } while (true);
    }

    static /* synthetic */ boolean lambda$classMarkedWithAnnotation$0(Class class_, Class class_2) throws Exception {
        return class_2.equals(class_);
    }

    static /* synthetic */ boolean lambda$fieldMarkedWithAnnotation$1(Class class_, Class class_2) throws Exception {
        return class_2.equals(class_);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] arrannotation, Annotation[] arrannotation2, Retrofit retrofit) {
        return new GsonRequestBodyConverter();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] arrannotation, Retrofit retrofit) {
        return new GsonResponseBodyConverter(this.gson, TypeToken.get(type));
    }

    final class GsonRequestBodyConverter<T>
    implements Converter<T, RequestBody> {
        GsonRequestBodyConverter() {
        }

        @Override
        public RequestBody convert(T t) throws IOException {
            if (t instanceof String) {
                return RequestBodyPOJOSerializer.generateRequestBody((String)t);
            }
            return RequestBodyPOJOSerializer.generateRequestBody(t);
        }
    }

    final class GsonResponseBodyConverter<T>
    implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final JsonParser parser;
        private final TypeToken<T> typeToken;

        GsonResponseBodyConverter(Gson gson, TypeToken<T> typeToken) {
            this.gson = gson;
            this.typeToken = typeToken;
            this.parser = new JsonParser();
        }

        /*
         * Exception decompiling
         */
        @Override
        public T convert(ResponseBody var1_1) throws IOException {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 6[CATCHBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

    }

}

