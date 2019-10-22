/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.validator;

import com.getqardio.android.utils.validator.DomainValidator;
import com.getqardio.android.utils.validator.InetAddressValidator;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator
implements Serializable {
    private static final Pattern EMAIL_PATTERN;
    private static final EmailValidator EMAIL_VALIDATOR;
    private static final EmailValidator EMAIL_VALIDATOR_WITH_LOCAL;
    private static final Pattern IP_DOMAIN_PATTERN;
    private static final Pattern MATCH_ASCII_PATTERN;
    private static final Pattern USER_PATTERN;
    private final boolean allowLocal;

    static {
        MATCH_ASCII_PATTERN = Pattern.compile("^\\p{ASCII}+$");
        EMAIL_PATTERN = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$");
        IP_DOMAIN_PATTERN = Pattern.compile("^\\[(.*)\\]$");
        USER_PATTERN = Pattern.compile("^\\s*(([^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"[^\"]*\"))(\\.(([^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"[^\"]*\")))*$");
        EMAIL_VALIDATOR = new EmailValidator(false);
        EMAIL_VALIDATOR_WITH_LOCAL = new EmailValidator(true);
    }

    protected EmailValidator(boolean bl) {
        this.allowLocal = bl;
    }

    public static EmailValidator getInstance() {
        return EMAIL_VALIDATOR;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isValid(String string2) {
        Matcher matcher;
        return string2 != null && MATCH_ASCII_PATTERN.matcher(string2).matches() && (matcher = EMAIL_PATTERN.matcher(string2)).matches() && !string2.endsWith(".") && this.isValidUser(matcher.group(1)) && this.isValidDomain(matcher.group(2));
    }

    protected boolean isValidDomain(String string2) {
        Matcher matcher = IP_DOMAIN_PATTERN.matcher(string2);
        if (matcher.matches()) {
            return InetAddressValidator.getInstance().isValid(matcher.group(1));
        }
        return DomainValidator.getInstance(this.allowLocal).isValid(string2);
    }

    protected boolean isValidUser(String string2) {
        return USER_PATTERN.matcher(string2).matches();
    }
}

