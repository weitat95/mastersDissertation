/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 */
package net.danlew.android.joda;

import android.content.Context;
import android.content.res.Resources;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import net.danlew.android.joda.R;
import net.danlew.android.joda.ResUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.DateTimeZoneBuilder;
import org.joda.time.tz.Provider;

public class ResourceZoneInfoProvider
implements Provider {
    private final Map<String, Object> iZoneInfoMap;
    private Context mAppContext;

    public ResourceZoneInfoProvider(Context context) throws IOException {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        this.mAppContext = context.getApplicationContext();
        this.iZoneInfoMap = ResourceZoneInfoProvider.loadZoneInfoMap(this.openResource("ZoneInfoMap"));
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DateTimeZone loadZoneData(String object) {
        InputStream inputStream;
        InputStream inputStream2 = null;
        InputStream inputStream3 = null;
        inputStream3 = inputStream = this.openResource((String)object);
        inputStream2 = inputStream;
        DateTimeZone dateTimeZone = DateTimeZoneBuilder.readFrom(inputStream, (String)object);
        inputStream3 = inputStream;
        inputStream2 = inputStream;
        this.iZoneInfoMap.put((String)object, new SoftReference<DateTimeZone>(dateTimeZone));
        object = dateTimeZone;
        if (inputStream == null) return object;
        try {
            inputStream.close();
            return dateTimeZone;
        }
        catch (IOException iOException) {
            return dateTimeZone;
        }
        catch (IOException iOException) {
            inputStream2 = inputStream3;
            this.uncaughtException(iOException);
            inputStream2 = inputStream3;
            this.iZoneInfoMap.remove(object);
            object = null;
            if (inputStream3 == null) return object;
            try {
                inputStream3.close();
                return null;
            }
            catch (IOException iOException2) {
                return null;
            }
            catch (Throwable throwable) {
                if (inputStream2 == null) throw throwable;
                try {
                    inputStream2.close();
                }
                catch (IOException iOException3) {
                    throw throwable;
                }
                do {
                    throw throwable;
                    break;
                } while (true);
            }
        }
    }

    private static Map<String, Object> loadZoneInfoMap(InputStream inputStream) throws IOException {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>();
        inputStream = new DataInputStream(inputStream);
        ResourceZoneInfoProvider.readZoneInfoMap((DataInputStream)inputStream, concurrentHashMap);
        concurrentHashMap.put("UTC", new SoftReference<DateTimeZone>(DateTimeZone.UTC));
        return concurrentHashMap;
        finally {
            ((FilterInputStream)inputStream).close();
        }
    }

    private InputStream openResource(String string2) throws IOException {
        if (this.mAppContext == null) {
            throw new RuntimeException("Need to call JodaTimeAndroid.init() before using joda-time-android");
        }
        String string3 = ResUtils.getTzResource(string2);
        int n = ResUtils.getIdentifier(R.raw.class, string3);
        if (n == 0) {
            throw new IOException("Resource not found: \"" + string2 + "\" (resName: \"" + string3 + "\")");
        }
        return this.mAppContext.getResources().openRawResource(n);
    }

    private static void readZoneInfoMap(DataInputStream dataInputStream, Map<String, Object> map) throws IOException {
        int n;
        int n2 = dataInputStream.readUnsignedShort();
        String[] arrstring = new String[n2];
        for (n = 0; n < n2; ++n) {
            arrstring[n] = dataInputStream.readUTF().intern();
        }
        n2 = dataInputStream.readUnsignedShort();
        for (n = 0; n < n2; ++n) {
            try {
                map.put(arrstring[dataInputStream.readUnsignedShort()], arrstring[dataInputStream.readUnsignedShort()]);
                continue;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new IOException("Corrupt zone info map");
            }
        }
    }

    @Override
    public Set<String> getAvailableIDs() {
        return new TreeSet<String>(this.iZoneInfoMap.keySet());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public DateTimeZone getZone(String string2) {
        block6: {
            Object object;
            block5: {
                Object object2;
                object = null;
                if (string2 == null || (object2 = this.iZoneInfoMap.get(string2)) == null) break block5;
                if (string2.equals(object2)) {
                    return this.loadZoneData(string2);
                }
                if (!(object2 instanceof SoftReference)) {
                    return this.getZone((String)object2);
                }
                object = object2 = (DateTimeZone)((SoftReference)object2).get();
                if (object2 == null) break block6;
            }
            return object;
        }
        return this.loadZoneData(string2);
    }

    protected void uncaughtException(Exception exception) {
        exception.printStackTrace();
    }
}

