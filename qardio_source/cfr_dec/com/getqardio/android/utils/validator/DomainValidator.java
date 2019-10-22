/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.validator;

import com.getqardio.android.utils.validator.RegexValidator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class DomainValidator
implements Serializable {
    private static final String[] COUNTRY_CODE_TLDS;
    private static final List COUNTRY_CODE_TLD_LIST;
    private static final DomainValidator DOMAIN_VALIDATOR;
    private static final DomainValidator DOMAIN_VALIDATOR_WITH_LOCAL;
    private static final String[] GENERIC_TLDS;
    private static final List GENERIC_TLD_LIST;
    private static final String[] INFRASTRUCTURE_TLDS;
    private static final List INFRASTRUCTURE_TLD_LIST;
    private static final String[] LOCAL_TLDS;
    private static final List LOCAL_TLD_LIST;
    private final boolean allowLocal;
    private final RegexValidator domainRegex = new RegexValidator("^(?:\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*\\.)+(\\p{Alpha}{2,})$");
    private final RegexValidator hostnameRegex = new RegexValidator("\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*");

    static {
        DOMAIN_VALIDATOR = new DomainValidator(false);
        DOMAIN_VALIDATOR_WITH_LOCAL = new DomainValidator(true);
        INFRASTRUCTURE_TLDS = new String[]{"arpa", "root"};
        GENERIC_TLDS = new String[]{"aero", "asia", "biz", "cat", "com", "coop", "info", "jobs", "link", "mobi", "museum", "name", "net", "org", "pro", "tel", "travel", "gov", "edu", "mil", "int"};
        COUNTRY_CODE_TLDS = new String[]{"ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "yu", "za", "zm", "zw"};
        LOCAL_TLDS = new String[]{"localhost", "localdomain"};
        INFRASTRUCTURE_TLD_LIST = Arrays.asList(INFRASTRUCTURE_TLDS);
        GENERIC_TLD_LIST = Arrays.asList(GENERIC_TLDS);
        COUNTRY_CODE_TLD_LIST = Arrays.asList(COUNTRY_CODE_TLDS);
        LOCAL_TLD_LIST = Arrays.asList(LOCAL_TLDS);
    }

    private DomainValidator(boolean bl) {
        this.allowLocal = bl;
    }

    private String chompLeadingDot(String string2) {
        String string3 = string2;
        if (string2.startsWith(".")) {
            string3 = string2.substring(1);
        }
        return string3;
    }

    public static DomainValidator getInstance(boolean bl) {
        if (bl) {
            return DOMAIN_VALIDATOR_WITH_LOCAL;
        }
        return DOMAIN_VALIDATOR;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isValid(String string2) {
        boolean bl = false;
        String[] arrstring = this.domainRegex.match(string2);
        if (arrstring != null && arrstring.length > 0) {
            return this.isValidTld(arrstring[0]);
        }
        boolean bl2 = bl;
        if (!this.allowLocal) return bl2;
        bl2 = bl;
        if (!this.hostnameRegex.isValid(string2)) return bl2;
        return true;
    }

    public boolean isValidCountryCodeTld(String string2) {
        return COUNTRY_CODE_TLD_LIST.contains(this.chompLeadingDot(string2.toLowerCase()));
    }

    public boolean isValidGenericTld(String string2) {
        return GENERIC_TLD_LIST.contains(this.chompLeadingDot(string2.toLowerCase()));
    }

    public boolean isValidInfrastructureTld(String string2) {
        return INFRASTRUCTURE_TLD_LIST.contains(this.chompLeadingDot(string2.toLowerCase()));
    }

    public boolean isValidLocalTld(String string2) {
        return LOCAL_TLD_LIST.contains(this.chompLeadingDot(string2.toLowerCase()));
    }

    public boolean isValidTld(String string2) {
        return this.allowLocal && this.isValidLocalTld(string2) || this.isValidInfrastructureTld(string2) || this.isValidGenericTld(string2) || this.isValidCountryCodeTld(string2);
    }
}

