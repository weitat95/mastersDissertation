/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

public final class Cookie {
    private static final Pattern DAY_OF_MONTH_PATTERN;
    private static final Pattern MONTH_PATTERN;
    private static final Pattern TIME_PATTERN;
    private static final Pattern YEAR_PATTERN;
    private final String domain;
    private final long expiresAt;
    private final boolean hostOnly;
    private final boolean httpOnly;
    private final String name;
    private final String path;
    private final boolean persistent;
    private final boolean secure;
    private final String value;

    static {
        YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
        MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
        DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
        TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
    }

    private Cookie(String string2, String string3, long l, String string4, String string5, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.name = string2;
        this.value = string3;
        this.expiresAt = l;
        this.domain = string4;
        this.path = string5;
        this.secure = bl;
        this.httpOnly = bl2;
        this.hostOnly = bl3;
        this.persistent = bl4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int dateCharacterOffset(String string2, int n, int n2, boolean bl) {
        while (n < n2) {
            char c;
            char c2 = string2.charAt(n);
            c2 = c2 < ' ' && c2 != '\t' || c2 >= '\u007f' || c2 >= '0' && c2 <= '9' || c2 >= 'a' && c2 <= 'z' || c2 >= 'A' && c2 <= 'Z' || c2 == ':' ? (char)'\u0001' : '\u0000';
            if (c2 == (c = !bl ? (char)'\u0001' : '\u0000')) {
                return n;
            }
            ++n;
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean domainMatch(HttpUrl object, String string2) {
        return ((String)(object = ((HttpUrl)object).host())).equals(string2) || ((String)object).endsWith(string2) && ((String)object).charAt(((String)object).length() - string2.length() - 1) == '.' && !Util.verifyAsIpAddress((String)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Cookie parse(long var0, HttpUrl var2_1, String var3_2) {
        var5_3 = var3_2.length();
        var4_4 = Util.delimiterOffset(var3_2, 0, var5_3, ';');
        var6_5 = Util.delimiterOffset(var3_2, 0, var4_4, '=');
        if (var6_5 == var4_4) {
            return null;
        }
        var27_6 = Util.trimSubstring(var3_2, 0, var6_5);
        if (var27_6.isEmpty() != false) return null;
        if (Util.indexOfControlOrNonAscii(var27_6) != -1) {
            return null;
        }
        var28_7 = Util.trimSubstring(var3_2, var6_5 + 1, var4_4);
        if (Util.indexOfControlOrNonAscii(var28_7) != -1) {
            return null;
        }
        var8_8 = 253402300799999L;
        var10_9 = -1L;
        var24_10 = null;
        var23_11 = null;
        var19_12 = false;
        var20_13 = false;
        var17_14 = true;
        var18_15 = false;
        ++var4_4;
        do {
            block29: {
                block31: {
                    block32: {
                        block30: {
                            block28: {
                                if (var4_4 >= var5_3) break block28;
                                var6_5 = Util.delimiterOffset(var3_2, var4_4, var5_3, ';');
                                var7_16 = Util.delimiterOffset(var3_2, var4_4, var6_5, '=');
                                var29_27 = Util.trimSubstring(var3_2, var4_4, var7_16);
                                var25_22 = var7_16 < var6_5 ? Util.trimSubstring(var3_2, var7_16 + 1, var6_5) : "";
                                if (var29_27.equalsIgnoreCase("expires")) {
                                    var12_17 = Cookie.parseExpires(var25_22, 0, var25_22.length());
                                    var16_19 = true;
                                    var14_18 = var10_9;
                                    var22_21 = var17_14;
                                    var21_20 = var19_12;
                                    var26_26 = var23_11;
                                    var25_22 = var24_10;
                                    ** GOTO lbl146
                                } else {
                                    if (var29_27.equalsIgnoreCase("max-age")) {
                                        var14_18 = Cookie.parseMaxAge(var25_22);
                                        var16_19 = true;
                                        var12_17 = var8_8;
                                        var25_22 = var24_10;
                                        var26_26 = var23_11;
                                        var21_20 = var19_12;
                                        var22_21 = var17_14;
                                    }
                                    if (var29_27.equalsIgnoreCase("domain")) {
                                        var25_22 = Cookie.parseDomain(var25_22);
                                        var22_21 = false;
                                        var12_17 = var8_8;
                                        var26_26 = var23_11;
                                        var21_20 = var19_12;
                                        var16_19 = var18_15;
                                        var14_18 = var10_9;
                                    }
                                    if (var29_27.equalsIgnoreCase("path")) {
                                        var26_26 = var25_22;
                                        var12_17 = var8_8;
                                        var25_22 = var24_10;
                                        var21_20 = var19_12;
                                        var22_21 = var17_14;
                                        var16_19 = var18_15;
                                        var14_18 = var10_9;
                                    } else if (var29_27.equalsIgnoreCase("secure")) {
                                        var21_20 = true;
                                        var12_17 = var8_8;
                                        var25_22 = var24_10;
                                        var26_26 = var23_11;
                                        var22_21 = var17_14;
                                        var16_19 = var18_15;
                                        var14_18 = var10_9;
                                    } else {
                                        var12_17 = var8_8;
                                        var25_22 = var24_10;
                                        var26_26 = var23_11;
                                        var21_20 = var19_12;
                                        var22_21 = var17_14;
                                        var16_19 = var18_15;
                                        var14_18 = var10_9;
                                        if (var29_27.equalsIgnoreCase("httponly")) {
                                            var20_13 = true;
                                            var12_17 = var8_8;
                                            var25_22 = var24_10;
                                            var26_26 = var23_11;
                                            var21_20 = var19_12;
                                            var22_21 = var17_14;
                                            var16_19 = var18_15;
                                            var14_18 = var10_9;
                                        }
                                    }
                                }
                                break block29;
                            }
                            if (var10_9 != Long.MIN_VALUE) break block30;
                            var8_8 = Long.MIN_VALUE;
                            break block31;
                        }
                        if (var10_9 == -1L) break block31;
                        var8_8 = var10_9 <= 9223372036854775L ? var10_9 * 1000L : Long.MAX_VALUE;
                        if ((var10_9 = var0 + var8_8) < var0) break block32;
                        var8_8 = var10_9;
                        if (var10_9 <= 253402300799999L) break block31;
                    }
                    var8_8 = 253402300799999L;
                }
                if (var24_10 == null) {
                    var25_22 = var2_1.host();
                } else {
                    var25_22 = var24_10;
                    if (!Cookie.domainMatch((HttpUrl)var2_1, var24_10)) {
                        return null;
                    }
                }
                if (var23_11 != null) {
                    var3_2 = var23_11;
                    if (var23_11.startsWith("/") != false) return new Cookie(var27_6, var28_7, var8_8, var25_22, var3_2, var19_12, var20_13, var17_14, var18_15);
                }
                if ((var4_4 = (var2_1 = var2_1.encodedPath()).lastIndexOf(47)) != 0) {
                    var3_2 = var2_1.substring(0, var4_4);
                    return new Cookie(var27_6, var28_7, var8_8, var25_22, var3_2, var19_12, var20_13, var17_14, var18_15);
                }
                var3_2 = "/";
                return new Cookie(var27_6, var28_7, var8_8, var25_22, var3_2, var19_12, var20_13, var17_14, var18_15);
                catch (NumberFormatException var25_23) {
                    var12_17 = var8_8;
                    var25_22 = var24_10;
                    var26_26 = var23_11;
                    var21_20 = var19_12;
                    var22_21 = var17_14;
                    var16_19 = var18_15;
                    var14_18 = var10_9;
                }
                break block29;
                catch (IllegalArgumentException var25_24) {
                    var12_17 = var8_8;
                    var25_22 = var24_10;
                    var26_26 = var23_11;
                    var21_20 = var19_12;
                    var22_21 = var17_14;
                    var16_19 = var18_15;
                    var14_18 = var10_9;
                }
                break block29;
                catch (IllegalArgumentException var25_25) {
                    var12_17 = var8_8;
                    var25_22 = var24_10;
                    var26_26 = var23_11;
                    var21_20 = var19_12;
                    var22_21 = var17_14;
                    var16_19 = var18_15;
                    var14_18 = var10_9;
                }
            }
            var4_4 = var6_5 + 1;
            var8_8 = var12_17;
            var24_10 = var25_22;
            var23_11 = var26_26;
            var19_12 = var21_20;
            var17_14 = var22_21;
            var18_15 = var16_19;
            var10_9 = var14_18;
        } while (true);
    }

    public static Cookie parse(HttpUrl httpUrl, String string2) {
        return Cookie.parse(System.currentTimeMillis(), httpUrl, string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List<Cookie> parseAll(HttpUrl httpUrl, Headers arrayList) {
        List<String> list = ((Headers)((Object)arrayList)).values("Set-Cookie");
        arrayList = null;
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            Cookie cookie = Cookie.parse(httpUrl, list.get(i));
            if (cookie == null) continue;
            ArrayList<Cookie> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList();
            }
            arrayList2.add(cookie);
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    private static String parseDomain(String string2) {
        if (string2.endsWith(".")) {
            throw new IllegalArgumentException();
        }
        String string3 = string2;
        if (string2.startsWith(".")) {
            string3 = string2.substring(1);
        }
        if ((string2 = Util.domainToAscii(string3)) == null) {
            throw new IllegalArgumentException();
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static long parseExpires(String object, int n, int n2) {
        int n3 = Cookie.dateCharacterOffset((String)object, n, n2, false);
        int n4 = -1;
        int n5 = -1;
        int n6 = -1;
        int n7 = -1;
        int n8 = -1;
        n = -1;
        Matcher matcher = TIME_PATTERN.matcher((CharSequence)object);
        while (n3 < n2) {
            int n9;
            int n10;
            int n11;
            int n12;
            int n13;
            int n14 = Cookie.dateCharacterOffset((String)object, n3 + 1, n2, true);
            matcher.region(n3, n14);
            if (n4 == -1 && matcher.usePattern(TIME_PATTERN).matches()) {
                n11 = Integer.parseInt(matcher.group(1));
                n3 = Integer.parseInt(matcher.group(2));
                n10 = Integer.parseInt(matcher.group(3));
                n13 = n;
                n9 = n8;
                n12 = n7;
            } else if (n7 == -1 && matcher.usePattern(DAY_OF_MONTH_PATTERN).matches()) {
                n12 = Integer.parseInt(matcher.group(1));
                n11 = n4;
                n3 = n5;
                n9 = n8;
                n10 = n6;
                n13 = n;
            } else if (n8 == -1 && matcher.usePattern(MONTH_PATTERN).matches()) {
                String string2 = matcher.group(1).toLowerCase(Locale.US);
                n9 = MONTH_PATTERN.pattern().indexOf(string2) / 4;
                n12 = n7;
                n11 = n4;
                n3 = n5;
                n10 = n6;
                n13 = n;
            } else {
                n12 = n7;
                n11 = n4;
                n3 = n5;
                n9 = n8;
                n10 = n6;
                n13 = n;
                if (n == -1) {
                    n12 = n7;
                    n11 = n4;
                    n3 = n5;
                    n9 = n8;
                    n10 = n6;
                    n13 = n;
                    if (matcher.usePattern(YEAR_PATTERN).matches()) {
                        n13 = Integer.parseInt(matcher.group(1));
                        n12 = n7;
                        n11 = n4;
                        n3 = n5;
                        n9 = n8;
                        n10 = n6;
                    }
                }
            }
            n14 = Cookie.dateCharacterOffset((String)object, n14 + 1, n2, false);
            n7 = n12;
            n4 = n11;
            n5 = n3;
            n8 = n9;
            n6 = n10;
            n = n13;
            n3 = n14;
        }
        n2 = n;
        if (n >= 70) {
            n2 = n;
            if (n <= 99) {
                n2 = n + 1900;
            }
        }
        n = n2;
        if (n2 >= 0) {
            n = n2;
            if (n2 <= 69) {
                n = n2 + 2000;
            }
        }
        if (n < 1601) {
            throw new IllegalArgumentException();
        }
        if (n8 == -1) {
            throw new IllegalArgumentException();
        }
        if (n7 < 1 || n7 > 31) {
            throw new IllegalArgumentException();
        }
        if (n4 < 0 || n4 > 23) {
            throw new IllegalArgumentException();
        }
        if (n5 < 0 || n5 > 59) {
            throw new IllegalArgumentException();
        }
        if (n6 >= 0 && n6 <= 59) {
            object = new GregorianCalendar(Util.UTC);
            ((Calendar)object).setLenient(false);
            ((Calendar)object).set(1, n);
            ((Calendar)object).set(2, n8 - 1);
            ((Calendar)object).set(5, n7);
            ((Calendar)object).set(11, n4);
            ((Calendar)object).set(12, n5);
            ((Calendar)object).set(13, n6);
            ((Calendar)object).set(14, 0);
            return ((Calendar)object).getTimeInMillis();
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static long parseMaxAge(String string2) {
        long l = Long.MIN_VALUE;
        try {
            long l2;
            l2 = l = (l2 = Long.parseLong(string2));
            if (l > 0L) return l2;
            return Long.MIN_VALUE;
        }
        catch (NumberFormatException numberFormatException) {
            if (!string2.matches("-?\\d+")) throw numberFormatException;
            if (!string2.startsWith("-")) return Long.MAX_VALUE;
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof Cookie)) break block2;
                object = (Cookie)object;
                if (((Cookie)object).name.equals(this.name) && ((Cookie)object).value.equals(this.value) && ((Cookie)object).domain.equals(this.domain) && ((Cookie)object).path.equals(this.path) && ((Cookie)object).expiresAt == this.expiresAt && ((Cookie)object).secure == this.secure && ((Cookie)object).httpOnly == this.httpOnly && ((Cookie)object).persistent == this.persistent && ((Cookie)object).hostOnly == this.hostOnly) break block3;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.name.hashCode();
        int n3 = this.value.hashCode();
        int n4 = this.domain.hashCode();
        int n5 = this.path.hashCode();
        int n6 = (int)(this.expiresAt ^ this.expiresAt >>> 32);
        int n7 = this.secure ? 0 : 1;
        int n8 = this.httpOnly ? 0 : 1;
        int n9 = this.persistent ? 0 : 1;
        if (this.hostOnly) {
            return ((((((((n2 + 527) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n;
        }
        n = 1;
        return ((((((((n2 + 527) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n9) * 31 + n;
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return this.toString(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    String toString(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name);
        stringBuilder.append('=');
        stringBuilder.append(this.value);
        if (this.persistent) {
            if (this.expiresAt == Long.MIN_VALUE) {
                stringBuilder.append("; max-age=0");
            } else {
                stringBuilder.append("; expires=").append(HttpDate.format(new Date(this.expiresAt)));
            }
        }
        if (!this.hostOnly) {
            stringBuilder.append("; domain=");
            if (bl) {
                stringBuilder.append(".");
            }
            stringBuilder.append(this.domain);
        }
        stringBuilder.append("; path=").append(this.path);
        if (this.secure) {
            stringBuilder.append("; secure");
        }
        if (this.httpOnly) {
            stringBuilder.append("; httponly");
        }
        return stringBuilder.toString();
    }

    public String value() {
        return this.value;
    }
}

