/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.validator;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator
implements Serializable {
    private final Pattern[] patterns;

    public RegexValidator(String string2) {
        this(string2, true);
    }

    public RegexValidator(String string2, boolean bl) {
        this(new String[]{string2}, bl);
    }

    /*
     * Enabled aggressive block sorting
     */
    public RegexValidator(String[] arrstring, boolean bl) {
        if (arrstring == null || arrstring.length == 0) {
            throw new IllegalArgumentException("Regular expressions are missing");
        }
        this.patterns = new Pattern[arrstring.length];
        int n = bl ? 0 : 2;
        int n2 = 0;
        while (n2 < arrstring.length) {
            if (arrstring[n2] == null || arrstring[n2].length() == 0) {
                throw new IllegalArgumentException("Regular expression[" + n2 + "] is missing");
            }
            this.patterns[n2] = Pattern.compile(arrstring[n2], n);
            ++n2;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isValid(String string2) {
        if (string2 != null) {
            Pattern[] arrpattern = this.patterns;
            int n = arrpattern.length;
            for (int i = 0; i < n; ++i) {
                if (!arrpattern[i].matcher(string2).matches()) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String[] match(String arrstring) {
        String[] arrstring2 = null;
        if (arrstring == null) {
            return arrstring2;
        }
        Pattern[] arrpattern = this.patterns;
        int n = arrpattern.length;
        int n2 = 0;
        do {
            String[] arrstring3 = arrstring2;
            if (n2 >= n) return arrstring3;
            Matcher matcher = arrpattern[n2].matcher((CharSequence)arrstring);
            if (matcher.matches()) {
                n = matcher.groupCount();
                arrstring = new String[n];
                n2 = 0;
                do {
                    arrstring3 = arrstring;
                    if (n2 >= n) return arrstring3;
                    arrstring[n2] = matcher.group(n2 + 1);
                    ++n2;
                } while (true);
            }
            ++n2;
        } while (true);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RegexValidator{");
        for (int i = 0; i < this.patterns.length; ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(this.patterns[i].pattern());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

