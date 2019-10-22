/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.validator;

import com.getqardio.android.utils.validator.RegexValidator;
import java.io.Serializable;

public class InetAddressValidator
implements Serializable {
    private static final InetAddressValidator VALIDATOR = new InetAddressValidator();
    private final RegexValidator ipv4Validator = new RegexValidator("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

    public static InetAddressValidator getInstance() {
        return VALIDATOR;
    }

    public boolean isValid(String string2) {
        return this.isValidInet4Address(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isValidInet4Address(String arrstring) {
        if ((arrstring = this.ipv4Validator.match((String)arrstring)) != null) {
            int n = 0;
            do {
                if (n > 3) {
                    return true;
                }
                String string2 = arrstring[n];
                if (string2 == null || string2.length() <= 0) break;
                try {
                    int n2 = Integer.parseInt(string2);
                    if (n2 > 255) break;
                    ++n;
                }
                catch (NumberFormatException numberFormatException) {
                    return false;
                }
            } while (true);
        }
        return false;
    }
}

