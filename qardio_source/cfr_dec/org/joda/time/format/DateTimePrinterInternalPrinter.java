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
import org.joda.time.format.InternalPrinterDateTimePrinter;

class DateTimePrinterInternalPrinter
implements InternalPrinter {
    private final DateTimePrinter underlying;

    private DateTimePrinterInternalPrinter(DateTimePrinter dateTimePrinter) {
        this.underlying = dateTimePrinter;
    }

    static InternalPrinter of(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter instanceof InternalPrinterDateTimePrinter) {
            return (InternalPrinter)((Object)dateTimePrinter);
        }
        if (dateTimePrinter == null) {
            return null;
        }
        return new DateTimePrinterInternalPrinter(dateTimePrinter);
    }

    @Override
    public int estimatePrintedLength() {
        return this.underlying.estimatePrintedLength();
    }

    @Override
    public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
        if (appendable instanceof StringBuffer) {
            appendable = (StringBuffer)appendable;
            this.underlying.printTo((StringBuffer)appendable, l, chronology, n, dateTimeZone, locale);
            return;
        }
        if (appendable instanceof Writer) {
            appendable = (Writer)appendable;
            this.underlying.printTo((Writer)appendable, l, chronology, n, dateTimeZone, locale);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer(this.estimatePrintedLength());
        this.underlying.printTo(stringBuffer, l, chronology, n, dateTimeZone, locale);
        appendable.append(stringBuffer);
    }
}

