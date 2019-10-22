/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build
 *  android.os.Process
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.segment.analytics.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.segment.analytics.internal.Iso8601Utils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Utils {
    public static <T> T assertNotNull(T t, String string2) {
        if (t == null) {
            throw new NullPointerException(string2 + " == null");
        }
        return t;
    }

    public static String assertNotNullOrEmpty(String string2, String string3) {
        if (Utils.isNullOrEmpty(string2)) {
            throw new NullPointerException(string3 + " cannot be null or empty");
        }
        return string2;
    }

    public static BufferedReader buffer(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static void copySharedPreferences(SharedPreferences object, SharedPreferences sharedPreferences) {
        sharedPreferences = sharedPreferences.edit();
        for (Map.Entry entry : object.getAll().entrySet()) {
            String string2 = (String)entry.getKey();
            Object entry2 = entry.getValue();
            if (entry2 instanceof String) {
                sharedPreferences.putString(string2, (String)entry2);
                continue;
            }
            if (entry2 instanceof Set) {
                sharedPreferences.putStringSet(string2, (Set)entry2);
                continue;
            }
            if (entry2 instanceof Integer) {
                sharedPreferences.putInt(string2, ((Integer)entry2).intValue());
                continue;
            }
            if (entry2 instanceof Long) {
                sharedPreferences.putLong(string2, ((Long)entry2).longValue());
                continue;
            }
            if (entry2 instanceof Float) {
                sharedPreferences.putFloat(string2, ((Float)entry2).floatValue());
                continue;
            }
            if (!(entry2 instanceof Boolean)) continue;
            sharedPreferences.putBoolean(string2, ((Boolean)entry2).booleanValue());
        }
        sharedPreferences.apply();
    }

    public static void createDirectory(File file) throws IOException {
        if (!(file.exists() || file.mkdirs() || file.isDirectory())) {
            throw new IOException("Could not create directory at " + file);
        }
    }

    public static <T> Map<String, T> createMap() {
        return new NullableConcurrentHashMap();
    }

    public static String getDeviceId(Context object) {
        String string2 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id");
        if (!(Utils.isNullOrEmpty(string2) || "9774d56d682e549c".equals(string2) || "unknown".equals(string2) || "000000000000000".equals(string2))) {
            return string2;
        }
        if (!Utils.isNullOrEmpty(Build.SERIAL)) {
            return Build.SERIAL;
        }
        if (Utils.hasPermission(object, "android.permission.READ_PHONE_STATE") && Utils.hasFeature(object, "android.hardware.telephony") && !Utils.isNullOrEmpty((CharSequence)(object = ((TelephonyManager)Utils.getSystemService(object, "phone")).getDeviceId()))) {
            return object;
        }
        return UUID.randomUUID().toString();
    }

    private static int getIdentifier(Context context, String string2, String string3) {
        return context.getResources().getIdentifier(string3, string2, context.getPackageName());
    }

    public static InputStream getInputStream(HttpURLConnection httpURLConnection) throws IOException {
        try {
            InputStream inputStream = httpURLConnection.getInputStream();
            return inputStream;
        }
        catch (IOException iOException) {
            return httpURLConnection.getErrorStream();
        }
    }

    public static String getResourceString(Context context, String string2) {
        int n = Utils.getIdentifier(context, "string", string2);
        if (n != 0) {
            return context.getResources().getString(n);
        }
        return null;
    }

    public static SharedPreferences getSegmentSharedPreferences(Context context, String string2) {
        return context.getSharedPreferences("analytics-android-" + string2, 0);
    }

    public static <T> T getSystemService(Context context, String string2) {
        return (T)context.getSystemService(string2);
    }

    private static int getTrimmedLength(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        for (n = 0; n < n2 && charSequence.charAt(n) <= ' '; ++n) {
        }
        while (n2 > n && charSequence.charAt(n2 - 1) <= ' ') {
            --n2;
        }
        return n2 - n;
    }

    public static boolean hasFeature(Context context, String string2) {
        return context.getPackageManager().hasSystemFeature(string2);
    }

    public static boolean hasPermission(Context context, String string2) {
        return context.checkCallingOrSelfPermission(string2) == 0;
    }

    public static <T> List<T> immutableCopyOf(List<T> list) {
        if (Utils.isNullOrEmpty(list)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList<T>(list));
    }

    public static <K, V> Map<K, V> immutableCopyOf(Map<K, V> map) {
        return Collections.unmodifiableMap(new LinkedHashMap<K, V>(map));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isConnected(Context context) {
        return !Utils.hasPermission(context, "android.permission.ACCESS_NETWORK_STATE") || (context = ((ConnectivityManager)Utils.getSystemService(context, "connectivity")).getActiveNetworkInfo()) != null && context.isConnectedOrConnecting();
    }

    private static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNullOrEmpty(CharSequence charSequence) {
        return Utils.isEmpty(charSequence) || Utils.getTrimmedLength(charSequence) == 0;
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isOnClassPath(String string2) {
        try {
            Class.forName(string2);
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    public static String readFully(BufferedReader bufferedReader) throws IOException {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        while ((string2 = bufferedReader.readLine()) != null) {
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    public static String readFully(InputStream inputStream) throws IOException {
        return Utils.readFully(Utils.buffer(inputStream));
    }

    public static String toISO8601Date(Date date) {
        return Utils.toISO8601String(date);
    }

    public static String toISO8601String(Date date) {
        return Iso8601Utils.format(date);
    }

    public static JSONObject toJsonObject(Map<String, ?> object) {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry jSONException : object.entrySet()) {
            Object object2 = Utils.wrap(jSONException.getValue());
            try {
                jSONObject.put((String)jSONException.getKey(), object2);
            }
            catch (JSONException jSONException2) {}
        }
        return jSONObject;
    }

    public static <T> Map<String, T> transform(Map<String, T> object, Map<String, String> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(object.size());
        for (Map.Entry entry : object.entrySet()) {
            String string2 = (String)entry.getKey();
            if (!map.containsKey(string2)) {
                linkedHashMap.put(string2, entry.getValue());
                continue;
            }
            if (Utils.isNullOrEmpty(string2 = map.get(string2))) continue;
            linkedHashMap.put(string2, entry.getValue());
        }
        return linkedHashMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Object wrap(Object object) {
        Object object2;
        block17: {
            if (object == null) {
                return JSONObject.NULL;
            }
            object2 = object;
            if (object instanceof JSONArray) return object2;
            object2 = object;
            if (object instanceof JSONObject) return object2;
            object2 = object;
            if (object.equals(JSONObject.NULL)) return object2;
            if (object instanceof Collection) {
                return new JSONArray((Collection)object);
            }
            if (!object.getClass().isArray()) break block17;
            int n = Array.getLength(object);
            object = new JSONArray();
            int n2 = 0;
            while (n2 < n) {
                object.put(Utils.wrap(Array.get(object, n2)));
                ++n2;
            }
            return object;
        }
        try {
            if (object instanceof Map) {
                return Utils.toJsonObject((Map)object);
            }
            object2 = object;
        }
        catch (Exception exception) {
            return JSONObject.NULL;
        }
        if (object instanceof Boolean) return object2;
        object2 = object;
        if (object instanceof Byte) return object2;
        object2 = object;
        if (object instanceof Character) return object2;
        object2 = object;
        if (object instanceof Double) return object2;
        object2 = object;
        if (object instanceof Float) return object2;
        object2 = object;
        if (object instanceof Integer) return object2;
        object2 = object;
        if (object instanceof Long) return object2;
        object2 = object;
        if (object instanceof Short) return object2;
        object2 = object;
        if (object instanceof String) return object2;
        return object.toString();
    }

    public static class AnalyticsNetworkExecutorService
    extends ThreadPoolExecutor {
        public AnalyticsNetworkExecutorService() {
            super(1, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new AnalyticsThreadFactory());
        }
    }

    private static class AnalyticsThread
    extends Thread {
        private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger(1);

        public AnalyticsThread(Runnable runnable) {
            super(runnable, "Segment-" + SEQUENCE_GENERATOR.getAndIncrement());
        }

        @Override
        public void run() {
            Process.setThreadPriority((int)10);
            super.run();
        }
    }

    public static class AnalyticsThreadFactory
    implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            return new AnalyticsThread(runnable);
        }
    }

    public static class NullableConcurrentHashMap<K, V>
    extends ConcurrentHashMap<K, V> {
        public NullableConcurrentHashMap() {
        }

        public NullableConcurrentHashMap(Map<? extends K, ? extends V> map) {
            super(map);
        }

        @Override
        public V put(K k, V v) {
            if (k == null || v == null) {
                return null;
            }
            return super.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> object) {
            for (Map.Entry entry : object.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

}

