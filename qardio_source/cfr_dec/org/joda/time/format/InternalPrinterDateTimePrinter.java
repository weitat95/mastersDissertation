/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimePrinter;
import org.joda.time.format.InternalPrinter;

class InternalPrinterDateTimePrinter
implements DateTimePrinter,
InternalPrinter {
    private final InternalPrinter underlying;

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof InternalPrinterDateTimePrinter) {
            object = (InternalPrinterDateTimePrinter)object;
            return this.underlying.equals(((InternalPrinterDateTimePrinter)object).underlying);
        }
        return false;
    }

    @Override
    public int estimatePrintedLength() {
        return this.underlying.estimatePrintedLength();
    }

    @Override
    public void printTo(Writer writer, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
        this.underlying.printTo(writer, l, chronology, n, dateTimeZone, locale);
    }

    @Override
    public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
        this.underlying.printTo(appendable, l, chronology, n, dateTimeZone, locale);
    }

    @Override
    public void printTo(StringBuffer stringBuffer, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) {
        try {
            this.underlying.printTo(stringBuffer, l, chronology, n, dateTimeZone, locale);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }
}

