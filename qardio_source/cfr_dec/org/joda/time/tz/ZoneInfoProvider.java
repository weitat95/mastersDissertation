/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.DateTimeZoneBuilder;
import org.joda.time.tz.Provider;

public class ZoneInfoProvider
implements Provider {
    private final File iFileDir;
    private final ClassLoader iLoader;
    private final String iResourcePath;
    private final Set<String> iZoneInfoKeys;
    private final Map<String, Object> iZoneInfoMap;

    public ZoneInfoProvider(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("No file directory provided");
        }
        if (!file.exists()) {
            throw new IOException("File directory doesn't exist: " + file);
        }
        if (!file.isDirectory()) {
            throw new IOException("File doesn't refer to a directory: " + file);
        }
        this.iFileDir = file;
        this.iResourcePath = null;
        this.iLoader = null;
        this.iZoneInfoMap = ZoneInfoProvider.loadZoneInfoMap(this.openResource("ZoneInfoMap"));
        this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet<String>(this.iZoneInfoMap.keySet()));
    }

    public ZoneInfoProvider(String string2) throws IOException {
        this(string2, null, false);
    }

    private ZoneInfoProvider(String object, ClassLoader classLoader, boolean bl) throws IOException {
        if (object == null) {
            throw new IllegalArgumentException("No resource path provided");
        }
        String string2 = object;
        if (!((String)object).endsWith("/")) {
            string2 = (String)object + '/';
        }
        this.iFileDir = null;
        this.iResourcePath = string2;
        object = classLoader;
        if (classLoader == null) {
            object = classLoader;
            if (!bl) {
                object = this.getClass().getClassLoader();
            }
        }
        this.iLoader = object;
        this.iZoneInfoMap = ZoneInfoProvider.loadZoneInfoMap(this.openResource("ZoneInfoMap"));
        this.iZoneInfoKeys = Collections.unmodifiableSortedSet(new TreeSet<String>(this.iZoneInfoMap.keySet()));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private DateTimeZone loadZoneData(String var1_1) {
        block18: {
            var2_8 = var3_7 = this.openResource(var1_1);
            var4_10 = DateTimeZoneBuilder.readFrom(var3_7, var1_1);
            var2_8 = var3_7;
            this.iZoneInfoMap.put(var1_1, new SoftReference<DateTimeZone>(var4_10));
            if (var3_7 == null) break block18;
            try {
                var3_7.close();
            }
            catch (IOException var1_4) {
                return var4_10;
            }
        }
        return var4_10;
        catch (IOException var4_11) {
            var3_7 = null;
lbl15:
            // 2 sources
            do {
                block19: {
                    var2_8 = var3_7;
                    this.uncaughtException((Exception)var4_12);
                    var2_8 = var3_7;
                    this.iZoneInfoMap.remove(var1_1);
                    if (var3_7 == null) break block19;
                    var3_7.close();
                }
lbl26:
                // 2 sources
                do {
                    return null;
                    break;
                } while (true);
                break;
            } while (true);
        }
        catch (Throwable var1_2) {
            var2_8 = null;
lbl30:
            // 2 sources
            do {
                if (var2_8 != null) {
                    var2_8.close();
                }
lbl34:
                // 4 sources
                do {
                    throw var1_3;
                    break;
                } while (true);
                break;
            } while (true);
        }
        {
            catch (IOException var1_5) {
                ** continue;
            }
        }
        {
            catch (IOException var2_9) {
                ** continue;
            }
        }
        {
            catch (Throwable var1_6) {
                ** continue;
            }
        }
        catch (IOException var4_13) {
            ** continue;
        }
    }

    private static Map<String, Object> loadZoneInfoMap(InputStream inputStream) throws IOException {
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>();
        inputStream = new DataInputStream(inputStream);
        ZoneInfoProvider.readZoneInfoMap((DataInputStream)inputStream, concurrentHashMap);
        concurrentHashMap.put("UTC", new SoftReference<DateTimeZone>(DateTimeZone.UTC));
        return concurrentHashMap;
        finally {
            ((FilterInputStream)inputStream).close();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private InputStream openResource(String object) throws IOException {
        Object object2;
        if (this.iFileDir != null) {
            return new FileInputStream(new File(this.iFileDir, (String)object));
        }
        final String string2 = this.iResourcePath.concat((String)object);
        object = object2 = AccessController.doPrivileged(new PrivilegedAction<InputStream>(){

            @Override
            public InputStream run() {
                if (ZoneInfoProvider.this.iLoader != null) {
                    return ZoneInfoProvider.this.iLoader.getResourceAsStream(string2);
                }
                return ClassLoader.getSystemResourceAsStream(string2);
            }
        });
        if (object2 != null) return object;
        object2 = new StringBuilder(40).append("Resource not found: \"").append(string2).append("\" ClassLoader: ");
        if (this.iLoader != null) {
            object = this.iLoader.toString();
            do {
                throw new IOException(((StringBuilder)object2).append((String)object).toString());
                break;
            } while (true);
        }
        object = "system";
        throw new IOException(((StringBuilder)object2).append((String)object).toString());
    }

    private static void readZoneInfoMap(DataInputStream dataInputStream, Map<String, Object> map) throws IOException {
        int n;
        int n2 = 0;
        int n3 = dataInputStream.readUnsignedShort();
        String[] arrstring = new String[n3];
        for (n = 0; n < n3; ++n) {
            arrstring[n] = dataInputStream.readUTF().intern();
        }
        n3 = dataInputStream.readUnsignedShort();
        for (n = n2; n < n3; ++n) {
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
        return this.iZoneInfoKeys;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public DateTimeZone getZone(String string2) {
        if (string2 == null) {
            return null;
        }
        Object object = this.iZoneInfoMap.get(string2);
        if (object == null) {
            return null;
        }
        if (object instanceof SoftReference) {
            DateTimeZone dateTimeZone = (DateTimeZone)((SoftReference)object).get();
            object = dateTimeZone;
            if (dateTimeZone != null) return object;
            return this.loadZoneData(string2);
        }
        if (!string2.equals(object)) return this.getZone((String)object);
        return this.loadZoneData(string2);
    }

    protected void uncaughtException(Exception exception) {
        exception.printStackTrace();
    }

}

