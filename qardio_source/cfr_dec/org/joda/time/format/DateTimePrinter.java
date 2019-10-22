/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;

public interface DateTimePrinter {
    public int estimatePrintedLength();

    public void printTo(Writer var1, long var2, Chronology var4, int var5, DateTimeZone var6, Locale var7) throws IOException;

    public void printTo(StringBuffer var1, long var2, Chronology var4, int var5, DateTimeZone var6, Locale var7);
}

