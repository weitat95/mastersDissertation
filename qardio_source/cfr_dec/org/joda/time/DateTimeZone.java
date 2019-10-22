/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.joda.convert.FromString
 *  org.joda.convert.ToString
 */
package org.joda.time;

import java.io.File;
import java.io.Serializable;
import java.security.Permission;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.Chronology;
import org.joda.time.IllegalInstantException;
import org.joda.time.JodaTimePermission;
import org.joda.time.UTCDateTimeZone;
import org.joda.time.chrono.BaseChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.FormatUtils;
import org.joda.time.tz.DefaultNameProvider;
import org.joda.time.tz.FixedDateTimeZone;
import org.joda.time.tz.NameProvider;
import org.joda.time.tz.Provider;
import org.joda.time.tz.UTCProvider;
import org.joda.time.tz.ZoneInfoProvider;

public abstract class DateTimeZone
implements Serializable {
    public static final DateTimeZone UTC = UTCDateTimeZone.INSTANCE;
    private static final AtomicReference<DateTimeZone> cDefault;
    private static final AtomicReference<NameProvider> cNameProvider;
    private static final AtomicReference<Provider> cProvider;
    private final String iID;

    static {
        cProvider = new AtomicReference();
        cNameProvider = new AtomicReference();
        cDefault = new AtomicReference();
    }

    protected DateTimeZone(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.iID = string2;
    }

    private static String convertToAsciiNumber(String charSequence) {
        charSequence = new StringBuilder((String)charSequence);
        for (int i = 0; i < ((StringBuilder)charSequence).length(); ++i) {
            int n = Character.digit(((StringBuilder)charSequence).charAt(i), 10);
            if (n < 0) continue;
            ((StringBuilder)charSequence).setCharAt(i, (char)(n + 48));
        }
        return ((StringBuilder)charSequence).toString();
    }

    private static DateTimeZone fixedOffsetZone(String string2, int n) {
        if (n == 0) {
            return UTC;
        }
        return new FixedDateTimeZone(string2, null, n, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @FromString
    public static DateTimeZone forID(String string2) {
        DateTimeZone dateTimeZone;
        int n;
        if (string2 == null) {
            return DateTimeZone.getDefault();
        }
        if (string2.equals("UTC")) {
            return UTC;
        }
        DateTimeZone dateTimeZone2 = dateTimeZone = DateTimeZone.getProvider().getZone(string2);
        if (dateTimeZone != null) return dateTimeZone2;
        if (!string2.startsWith("+")) {
            if (!string2.startsWith("-")) throw new IllegalArgumentException("The datetime zone id '" + string2 + "' is not recognised");
        }
        if ((long)(n = DateTimeZone.parseOffset(string2)) != 0L) return DateTimeZone.fixedOffsetZone(DateTimeZone.printOffset(n), n);
        return UTC;
    }

    public static DateTimeZone forOffsetMillis(int n) {
        if (n < -86399999 || n > 86399999) {
            throw new IllegalArgumentException("Millis out of range: " + n);
        }
        return DateTimeZone.fixedOffsetZone(DateTimeZone.printOffset(n), n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static DateTimeZone forTimeZone(TimeZone object) {
        int n;
        if (object == null) {
            return DateTimeZone.getDefault();
        }
        String string2 = ((TimeZone)object).getID();
        if (string2 == null) {
            throw new IllegalArgumentException("The TimeZone id must not be null");
        }
        if (string2.equals("UTC")) {
            return UTC;
        }
        Object object2 = null;
        String string3 = DateTimeZone.getConvertedId(string2);
        Provider provider = DateTimeZone.getProvider();
        if (string3 != null) {
            object2 = provider.getZone(string3);
        }
        object = object2;
        if (object2 == null) {
            object = provider.getZone(string2);
        }
        object2 = object;
        if (object != null) return object2;
        if (string3 != null) throw new IllegalArgumentException("The datetime zone id '" + string2 + "' is not recognised");
        if (!string2.startsWith("GMT+")) {
            if (!string2.startsWith("GMT-")) throw new IllegalArgumentException("The datetime zone id '" + string2 + "' is not recognised");
        }
        object = object2 = string2.substring(3);
        if (((String)object2).length() > 2) {
            char c = ((String)object2).charAt(1);
            object = object2;
            if (c > '9') {
                object = object2;
                if (Character.isDigit(c)) {
                    object = DateTimeZone.convertToAsciiNumber((String)object2);
                }
            }
        }
        if ((long)(n = DateTimeZone.parseOffset((String)object)) != 0L) return DateTimeZone.fixedOffsetZone(DateTimeZone.printOffset(n), n);
        return UTC;
    }

    public static Set<String> getAvailableIDs() {
        return DateTimeZone.getProvider().getAvailableIDs();
    }

    private static String getConvertedId(String string2) {
        return LazyInit.CONVERSION_MAP.get(string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static DateTimeZone getDefault() {
        Object object;
        Object object2;
        DateTimeZone dateTimeZone;
        block8: {
            block7: {
                dateTimeZone = cDefault.get();
                object = dateTimeZone;
                if (dateTimeZone != null) return object;
                object2 = dateTimeZone;
                try {
                    object = System.getProperty("user.timezone");
                    object2 = dateTimeZone;
                    if (object != null) {
                        object2 = dateTimeZone;
                        object2 = object = DateTimeZone.forID((String)object);
                    }
                    break block7;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    dateTimeZone = object2;
                    break block8;
                }
                catch (RuntimeException runtimeException) {
                    object2 = dateTimeZone;
                }
            }
            dateTimeZone = object2;
            if (object2 == null) {
                dateTimeZone = DateTimeZone.forTimeZone(TimeZone.getDefault());
            }
        }
        object2 = dateTimeZone;
        if (dateTimeZone == null) {
            object2 = UTC;
        }
        object = object2;
        if (cDefault.compareAndSet(null, (DateTimeZone)object2)) return object;
        return cDefault.get();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    private static NameProvider getDefaultNameProvider() {
        block7: {
            var0 = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
            if (var0 == null) break block7;
            var0 = (NameProvider)Class.forName((String)var0).newInstance();
lbl6:
            // 3 sources
            do {
                var1_3 = var0;
                if (var0 == null) {
                    var1_3 = new DefaultNameProvider();
                }
                return var1_3;
                break;
            } while (true);
            {
                catch (Exception var0_1) {
                    try {
                        throw new RuntimeException(var0_1);
                    }
                    catch (SecurityException var0_2) {
                        var0 = null;
                        ** GOTO lbl6
                    }
                }
            }
        }
        var0 = null;
        ** while (true)
    }

    private static Provider getDefaultProvider() {
        Object object;
        block13: {
            block12: {
                object = System.getProperty("org.joda.time.DateTimeZone.Provider");
                if (object == null) break block12;
                try {
                    object = DateTimeZone.validateProvider((Provider)Class.forName((String)object).newInstance());
                    return object;
                }
                catch (Exception exception) {
                    try {
                        throw new RuntimeException(exception);
                    }
                    catch (SecurityException securityException) {
                        // empty catch block
                    }
                }
            }
            object = System.getProperty("org.joda.time.DateTimeZone.Folder");
            if (object == null) break block13;
            try {
                object = DateTimeZone.validateProvider(new ZoneInfoProvider(new File((String)object)));
                return object;
            }
            catch (Exception exception) {
                try {
                    throw new RuntimeException(exception);
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
            }
        }
        try {
            object = DateTimeZone.validateProvider(new ZoneInfoProvider("org/joda/time/tz/data"));
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new UTCProvider();
        }
    }

    public static NameProvider getNameProvider() {
        NameProvider nameProvider;
        NameProvider nameProvider2 = nameProvider = cNameProvider.get();
        if (nameProvider == null) {
            nameProvider2 = nameProvider = DateTimeZone.getDefaultNameProvider();
            if (!cNameProvider.compareAndSet(null, nameProvider)) {
                nameProvider2 = cNameProvider.get();
            }
        }
        return nameProvider2;
    }

    public static Provider getProvider() {
        Provider provider;
        Provider provider2 = provider = cProvider.get();
        if (provider == null) {
            provider2 = provider = DateTimeZone.getDefaultProvider();
            if (!cProvider.compareAndSet(null, provider)) {
                provider2 = cProvider.get();
            }
        }
        return provider2;
    }

    private static int parseOffset(String string2) {
        return -((int)LazyInit.OFFSET_FORMATTER.parseMillis(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String printOffset(int n) {
        StringBuffer stringBuffer = new StringBuffer();
        if (n >= 0) {
            stringBuffer.append('+');
        } else {
            stringBuffer.append('-');
            n = -n;
        }
        int n2 = n / 3600000;
        FormatUtils.appendPaddedInteger(stringBuffer, n2, 2);
        n -= n2 * 3600000;
        n2 = n / 60000;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, n2, 2);
        if ((n -= n2 * 60000) == 0) {
            return stringBuffer.toString();
        }
        n2 = n / 1000;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, n2, 2);
        if ((n -= n2 * 1000) == 0) {
            return stringBuffer.toString();
        }
        stringBuffer.append('.');
        FormatUtils.appendPaddedInteger(stringBuffer, n, 3);
        return stringBuffer.toString();
    }

    public static void setDefault(DateTimeZone dateTimeZone) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setDefault"));
        }
        if (dateTimeZone == null) {
            throw new IllegalArgumentException("The datetime zone must not be null");
        }
        cDefault.set(dateTimeZone);
    }

    private static Provider validateProvider(Provider provider) {
        Set<String> set = provider.getAvailableIDs();
        if (set == null || set.size() == 0) {
            throw new IllegalArgumentException("The provider doesn't have any available ids");
        }
        if (!set.contains("UTC")) {
            throw new IllegalArgumentException("The provider doesn't support UTC");
        }
        if (!UTC.equals(provider.getZone("UTC"))) {
            throw new IllegalArgumentException("Invalid UTC zone provided");
        }
        return provider;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public long convertLocalToUTC(long var1_1, boolean var3_2) {
        var8_3 = Long.MAX_VALUE;
        var4_4 = this.getOffset(var1_1);
        if (var4_4 == (var5_5 = this.getOffset(var1_1 - (long)var4_4)) || !var3_2 && var4_4 >= 0) ** GOTO lbl-1000
        var6_7 = var10_6 = this.nextTransition(var1_1 - (long)var4_4);
        if (var10_6 == var1_1 - (long)var4_4) {
            var6_7 = Long.MAX_VALUE;
        }
        if ((var10_6 = this.nextTransition(var1_1 - (long)var5_5)) != var1_1 - (long)var5_5) {
            var8_3 = var10_6;
        }
        if (var6_7 != var8_3) {
            if (var3_2) {
                throw new IllegalInstantException(var1_1, this.getID());
            }
        } else lbl-1000:
        // 2 sources
        {
            var4_4 = var5_5;
        }
        if ((var1_1 ^ (var6_7 = var1_1 - (long)var4_4)) >= 0L) return var6_7;
        if (((long)var4_4 ^ var1_1) >= 0L) return var6_7;
        throw new ArithmeticException("Subtracting time zone offset caused overflow");
    }

    public long convertLocalToUTC(long l, boolean bl, long l2) {
        int n = this.getOffset(l2);
        l2 = l - (long)n;
        if (this.getOffset(l2) == n) {
            return l2;
        }
        return this.convertLocalToUTC(l, bl);
    }

    public long convertUTCToLocal(long l) {
        int n = this.getOffset(l);
        long l2 = (long)n + l;
        if ((l ^ l2) < 0L && ((long)n ^ l) >= 0L) {
            throw new ArithmeticException("Adding time zone offset caused overflow");
        }
        return l2;
    }

    public abstract boolean equals(Object var1);

    @ToString
    public final String getID() {
        return this.iID;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getName(long l, Locale object) {
        Object object2 = object;
        if (object == null) {
            object2 = Locale.getDefault();
        }
        if ((object = this.getNameKey(l)) == null) {
            return this.iID;
        }
        NameProvider nameProvider = DateTimeZone.getNameProvider();
        object = nameProvider instanceof DefaultNameProvider ? ((DefaultNameProvider)nameProvider).getName((Locale)object2, this.iID, (String)object, this.isStandardOffset(l)) : nameProvider.getName((Locale)object2, this.iID, (String)object);
        object2 = object;
        if (object != null) return object2;
        return DateTimeZone.printOffset(this.getOffset(l));
    }

    public abstract String getNameKey(long var1);

    public abstract int getOffset(long var1);

    /*
     * Enabled aggressive block sorting
     */
    public int getOffsetFromLocal(long l) {
        long l2;
        int n;
        int n2;
        long l3 = Long.MAX_VALUE;
        int n3 = this.getOffset(l);
        if (n3 != (n = this.getOffset(l2 = l - (long)n3))) {
            long l4;
            if (n3 - n >= 0) return n;
            l2 = l4 = this.nextTransition(l2);
            if (l4 == l - (long)n3) {
                l2 = Long.MAX_VALUE;
            }
            l = (l4 = this.nextTransition(l - (long)n)) == l - (long)n ? l3 : l4;
            if (l2 == l) return n;
            return n3;
        }
        if (n3 < 0) return n;
        l = this.previousTransition(l2);
        if (l >= l2) return n;
        int n4 = n2 = this.getOffset(l);
        if (l2 - l > (long)(n2 - n3)) return n;
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getShortName(long l, Locale object) {
        Object object2 = object;
        if (object == null) {
            object2 = Locale.getDefault();
        }
        if ((object = this.getNameKey(l)) == null) {
            return this.iID;
        }
        NameProvider nameProvider = DateTimeZone.getNameProvider();
        object = nameProvider instanceof DefaultNameProvider ? ((DefaultNameProvider)nameProvider).getShortName((Locale)object2, this.iID, (String)object, this.isStandardOffset(l)) : nameProvider.getShortName((Locale)object2, this.iID, (String)object);
        object2 = object;
        if (object != null) return object2;
        return DateTimeZone.printOffset(this.getOffset(l));
    }

    public abstract int getStandardOffset(long var1);

    public int hashCode() {
        return this.getID().hashCode() + 57;
    }

    public abstract boolean isFixed();

    public boolean isStandardOffset(long l) {
        return this.getOffset(l) == this.getStandardOffset(l);
    }

    public abstract long nextTransition(long var1);

    public abstract long previousTransition(long var1);

    public String toString() {
        return this.getID();
    }

    static final class LazyInit {
        static final Map<String, String> CONVERSION_MAP = LazyInit.buildMap();
        static final DateTimeFormatter OFFSET_FORMATTER = LazyInit.buildFormatter();

        private static DateTimeFormatter buildFormatter() {
            BaseChronology baseChronology = new BaseChronology(){

                @Override
                public DateTimeZone getZone() {
                    return null;
                }

                public String toString() {
                    return this.getClass().getName();
                }

                @Override
                public Chronology withUTC() {
                    return this;
                }

                @Override
                public Chronology withZone(DateTimeZone dateTimeZone) {
                    return this;
                }
            };
            return new DateTimeFormatterBuilder().appendTimeZoneOffset(null, true, 2, 4).toFormatter().withChronology(baseChronology);
        }

        private static Map<String, String> buildMap() {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("GMT", "UTC");
            hashMap.put("WET", "WET");
            hashMap.put("CET", "CET");
            hashMap.put("MET", "CET");
            hashMap.put("ECT", "CET");
            hashMap.put("EET", "EET");
            hashMap.put("MIT", "Pacific/Apia");
            hashMap.put("HST", "Pacific/Honolulu");
            hashMap.put("AST", "America/Anchorage");
            hashMap.put("PST", "America/Los_Angeles");
            hashMap.put("MST", "America/Denver");
            hashMap.put("PNT", "America/Phoenix");
            hashMap.put("CST", "America/Chicago");
            hashMap.put("EST", "America/New_York");
            hashMap.put("IET", "America/Indiana/Indianapolis");
            hashMap.put("PRT", "America/Puerto_Rico");
            hashMap.put("CNT", "America/St_Johns");
            hashMap.put("AGT", "America/Argentina/Buenos_Aires");
            hashMap.put("BET", "America/Sao_Paulo");
            hashMap.put("ART", "Africa/Cairo");
            hashMap.put("CAT", "Africa/Harare");
            hashMap.put("EAT", "Africa/Addis_Ababa");
            hashMap.put("NET", "Asia/Yerevan");
            hashMap.put("PLT", "Asia/Karachi");
            hashMap.put("IST", "Asia/Kolkata");
            hashMap.put("BST", "Asia/Dhaka");
            hashMap.put("VST", "Asia/Ho_Chi_Minh");
            hashMap.put("CTT", "Asia/Shanghai");
            hashMap.put("JST", "Asia/Tokyo");
            hashMap.put("ACT", "Australia/Darwin");
            hashMap.put("AET", "Australia/Sydney");
            hashMap.put("SST", "Pacific/Guadalcanal");
            hashMap.put("NST", "Pacific/Auckland");
            return Collections.unmodifiableMap(hashMap);
        }

    }

}

