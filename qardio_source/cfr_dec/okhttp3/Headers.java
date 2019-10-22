/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import okhttp3.internal.Util;

public final class Headers {
    private final String[] namesAndValues;

    Headers(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] arrstring) {
        this.namesAndValues = arrstring;
    }

    private static String get(String[] arrstring, String string2) {
        for (int i = arrstring.length - 2; i >= 0; i -= 2) {
            if (!string2.equalsIgnoreCase(arrstring[i])) continue;
            return arrstring[i + 1];
        }
        return null;
    }

    public static Headers of(String ... arrstring) {
        int n;
        if (arrstring == null) {
            throw new NullPointerException("namesAndValues == null");
        }
        if (arrstring.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        arrstring = (String[])arrstring.clone();
        for (n = 0; n < arrstring.length; ++n) {
            if (arrstring[n] == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            arrstring[n] = arrstring[n].trim();
        }
        for (n = 0; n < arrstring.length; n += 2) {
            String string2 = arrstring[n];
            String string3 = arrstring[n + 1];
            if (string2.length() != 0 && string2.indexOf(0) == -1 && string3.indexOf(0) == -1) continue;
            throw new IllegalArgumentException("Unexpected header: " + string2 + ": " + string3);
        }
        return new Headers(arrstring);
    }

    public boolean equals(Object object) {
        return object instanceof Headers && Arrays.equals(((Headers)object).namesAndValues, this.namesAndValues);
    }

    public String get(String string2) {
        return Headers.get(this.namesAndValues, string2);
    }

    public int hashCode() {
        return Arrays.hashCode(this.namesAndValues);
    }

    public String name(int n) {
        return this.namesAndValues[n * 2];
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        Collections.addAll(builder.namesAndValues, this.namesAndValues);
        return builder;
    }

    public int size() {
        return this.namesAndValues.length / 2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(this.name(i)).append(": ").append(this.value(i)).append("\n");
        }
        return stringBuilder.toString();
    }

    public String value(int n) {
        return this.namesAndValues[n * 2 + 1];
    }

    public List<String> values(String string2) {
        ArrayList<String> arrayList = null;
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            ArrayList<String> arrayList2 = arrayList;
            if (string2.equalsIgnoreCase(this.name(i))) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<String>(2);
                }
                arrayList2.add(this.value(i));
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    public static final class Builder {
        final List<String> namesAndValues = new ArrayList<String>(20);

        private void checkNameAndValue(String string2, String string3) {
            int n;
            char c;
            if (string2 == null) {
                throw new NullPointerException("name == null");
            }
            if (string2.isEmpty()) {
                throw new IllegalArgumentException("name is empty");
            }
            int n2 = string2.length();
            for (n = 0; n < n2; ++n) {
                c = string2.charAt(n);
                if (c > ' ' && c < '\u007f') continue;
                throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", c, n, string2));
            }
            if (string3 == null) {
                throw new NullPointerException("value == null");
            }
            n2 = string3.length();
            for (n = 0; n < n2; ++n) {
                c = string3.charAt(n);
                if ((c > '\u001f' || c == '\t') && c < '\u007f') continue;
                throw new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", c, n, string2, string3));
            }
        }

        public Builder add(String string2, String string3) {
            this.checkNameAndValue(string2, string3);
            return this.addLenient(string2, string3);
        }

        Builder addLenient(String string2) {
            int n = string2.indexOf(":", 1);
            if (n != -1) {
                return this.addLenient(string2.substring(0, n), string2.substring(n + 1));
            }
            if (string2.startsWith(":")) {
                return this.addLenient("", string2.substring(1));
            }
            return this.addLenient("", string2);
        }

        Builder addLenient(String string2, String string3) {
            this.namesAndValues.add(string2);
            this.namesAndValues.add(string3.trim());
            return this;
        }

        public Headers build() {
            return new Headers(this);
        }

        public String get(String string2) {
            for (int i = this.namesAndValues.size() - 2; i >= 0; i -= 2) {
                if (!string2.equalsIgnoreCase(this.namesAndValues.get(i))) continue;
                return this.namesAndValues.get(i + 1);
            }
            return null;
        }

        public Builder removeAll(String string2) {
            int n = 0;
            while (n < this.namesAndValues.size()) {
                int n2 = n;
                if (string2.equalsIgnoreCase(this.namesAndValues.get(n))) {
                    this.namesAndValues.remove(n);
                    this.namesAndValues.remove(n);
                    n2 = n - 2;
                }
                n = n2 + 2;
            }
            return this;
        }

        public Builder set(String string2, String string3) {
            this.checkNameAndValue(string2, string3);
            this.removeAll(string2);
            this.addLenient(string2, string3);
            return this;
        }
    }

}

