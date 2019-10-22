/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.remote;

import com.getqardio.android.mvp.common.gson_annotation.FieldPureJson;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyPOJOSerializer {
    private static Gson gson = new Gson();

    /*
     * Enabled aggressive block sorting
     */
    static RequestBody generateRequestBody(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] arrfield = object.getClass().getDeclaredFields();
        int n = arrfield.length;
        int n2 = 0;
        while (n2 < n) {
            String string2;
            Field field = arrfield[n2];
            String string3 = RequestBodyPOJOSerializer.getAnnotationValue(field);
            if (string3 != null && (string2 = RequestBodyPOJOSerializer.getFieldValue(field, object)) != null) {
                if (stringBuilder.length() == 0) {
                    stringBuilder.append(string3).append('=').append(string2);
                } else {
                    stringBuilder.append('&').append(string3).append('=').append(string2);
                }
            }
            ++n2;
        }
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), stringBuilder.toString());
    }

    static RequestBody generateRequestBody(String string2) {
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), string2);
    }

    private static String getAnnotationValue(Field object) {
        if (((AccessibleObject)object).isAnnotationPresent(SerializedName.class)) {
            try {
                object = URLEncoder.encode(((Field)object).getAnnotation(SerializedName.class).value(), "utf-8");
                return object;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException(unsupportedEncodingException);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String getFieldValue(Field object, Object object2) {
        void var0_2;
        try {
            if (((AccessibleObject)object).isAnnotationPresent(FieldPureJson.class)) {
                return gson.toJson(((Field)object).get(object2));
            }
            if (!((Field)object).getType().equals(String.class)) {
                throw new RuntimeException("Field must be string or object marked with FieldPureJson annotation");
            }
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException((Throwable)var0_2);
        }
        try {
            try {
                return URLEncoder.encode(String.valueOf(((Field)object).get(object2)), "utf-8");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException(unsupportedEncodingException);
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new RuntimeException((Throwable)var0_2);
        }
    }
}

