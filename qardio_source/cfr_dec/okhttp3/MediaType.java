/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaType {
    private static final Pattern PARAMETER;
    private static final Pattern TYPE_SUBTYPE;
    private final String charset;
    private final String mediaType;
    private final String subtype;
    private final String type;

    static {
        TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
        PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    }

    private MediaType(String string2, String string3, String string4, String string5) {
        this.mediaType = string2;
        this.type = string3;
        this.subtype = string4;
        this.charset = string5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MediaType parse(String string2) {
        Object object = TYPE_SUBTYPE.matcher(string2);
        if (((Matcher)object).lookingAt()) {
            String string3 = ((Matcher)object).group(1).toLowerCase(Locale.US);
            String string4 = ((Matcher)object).group(2).toLowerCase(Locale.US);
            Matcher matcher = null;
            Matcher matcher2 = PARAMETER.matcher(string2);
            int n = ((Matcher)object).end();
            do {
                if (n >= string2.length()) {
                    return new MediaType(string2, string3, string4, (String)((Object)matcher));
                }
                matcher2.region(n, string2.length());
                if (!matcher2.lookingAt()) break;
                String string5 = matcher2.group(1);
                object = matcher;
                if (string5 != null) {
                    if (!string5.equalsIgnoreCase("charset")) {
                        object = matcher;
                    } else {
                        object = matcher2.group(2);
                        if (object != null) {
                            if (((String)object).startsWith("'") && ((String)object).endsWith("'") && ((String)object).length() > 2) {
                                object = ((String)object).substring(1, ((String)object).length() - 1);
                            }
                        } else {
                            object = matcher2.group(3);
                        }
                        if (matcher != null && !((String)object).equalsIgnoreCase((String)((Object)matcher))) {
                            throw new IllegalArgumentException("Multiple different charsets: " + string2);
                        }
                    }
                }
                n = matcher2.end();
                matcher = object;
            } while (true);
        }
        return null;
    }

    public Charset charset() {
        if (this.charset != null) {
            return Charset.forName(this.charset);
        }
        return null;
    }

    public Charset charset(Charset charset) {
        if (this.charset != null) {
            charset = Charset.forName(this.charset);
        }
        return charset;
    }

    public boolean equals(Object object) {
        return object instanceof MediaType && ((MediaType)object).mediaType.equals(this.mediaType);
    }

    public int hashCode() {
        return this.mediaType.hashCode();
    }

    public String toString() {
        return this.mediaType;
    }

    public String type() {
        return this.type;
    }
}

