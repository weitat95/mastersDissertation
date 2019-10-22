/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.graphql.support;

import java.util.HashSet;
import java.util.Set;

public abstract class Arguments {
    private final Set<String> arguments = new HashSet<String>();
    private boolean firstArgument;
    private final StringBuilder query;

    protected Arguments(StringBuilder stringBuilder, boolean bl) {
        this.query = stringBuilder;
        this.firstArgument = bl;
    }

    public static void end(Arguments arguments) {
        if (!arguments.firstArgument) {
            arguments.query.append(')');
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void startArgument(String string2) {
        if (!this.arguments.add(string2)) {
            throw new RuntimeException("Already specified argument " + string2);
        }
        if (this.firstArgument) {
            this.firstArgument = false;
            this.query.append('(');
        } else {
            this.query.append(',');
        }
        this.query.append(string2);
        this.query.append(':');
    }
}

