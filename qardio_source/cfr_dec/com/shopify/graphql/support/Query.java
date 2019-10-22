/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

public abstract class Query<T extends Query> {
    protected final StringBuilder _queryBuilder;
    private String aliasSuffix = null;
    private boolean firstSelection = true;

    protected Query(StringBuilder stringBuilder) {
        this._queryBuilder = stringBuilder;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void appendQuotedString(StringBuilder stringBuilder, String arrc) {
        stringBuilder.append('\"');
        arrc = arrc.toCharArray();
        int n = arrc.length;
        int n2 = 0;
        do {
            block7: {
                if (n2 >= n) {
                    stringBuilder.append('\"');
                    return;
                }
                char c = arrc[n2];
                switch (c) {
                    default: {
                        if (c >= ' ') break;
                        stringBuilder.append(String.format("\\u%04x", c));
                        break block7;
                    }
                    case '\"': 
                    case '\\': {
                        stringBuilder.append('\\');
                        stringBuilder.append(c);
                        break block7;
                    }
                    case '\r': {
                        stringBuilder.append("\\r");
                        break block7;
                    }
                    case '\n': {
                        stringBuilder.append("\\n");
                        break block7;
                    }
                }
                stringBuilder.append(c);
            }
            ++n2;
        } while (true);
    }

    private void startSelection() {
        if (this.firstSelection) {
            this.firstSelection = false;
            return;
        }
        this._queryBuilder.append(',');
    }

    protected void startField(String string2) {
        this.startSelection();
        this._queryBuilder.append(string2);
        if (this.aliasSuffix != null) {
            this._queryBuilder.append("__");
            this._queryBuilder.append(this.aliasSuffix);
            this._queryBuilder.append(":");
            this._queryBuilder.append(string2);
            this.aliasSuffix = null;
        }
    }

    protected void startInlineFragment(String string2) {
        if (this.aliasSuffix != null) {
            throw new IllegalStateException("An alias cannot be specified on inline fragments");
        }
        this.startSelection();
        this._queryBuilder.append("... on ");
        this._queryBuilder.append(string2);
        this._queryBuilder.append('{');
    }
}

