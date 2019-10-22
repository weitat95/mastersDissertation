/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.io.IOException;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
import org.joda.time.format.FormatUtils;
import org.joda.time.format.InternalParser;
import org.joda.time.format.InternalParserDateTimeParser;
import org.joda.time.format.InternalPrinter;

public class DateTimeFormatter {
    private final Chronology iChrono;
    private final int iDefaultYear;
    private final Locale iLocale;
    private final boolean iOffsetParsed;
    private final InternalParser iParser;
    private final Integer iPivotYear;
    private final InternalPrinter iPrinter;
    private final DateTimeZone iZone;

    DateTimeFormatter(InternalPrinter internalPrinter, InternalParser internalParser) {
        this.iPrinter = internalPrinter;
        this.iParser = internalParser;
        this.iLocale = null;
        this.iOffsetParsed = false;
        this.iChrono = null;
        this.iZone = null;
        this.iPivotYear = null;
        this.iDefaultYear = 2000;
    }

    private DateTimeFormatter(InternalPrinter internalPrinter, InternalParser internalParser, Locale locale, boolean bl, Chronology chronology, DateTimeZone dateTimeZone, Integer n, int n2) {
        this.iPrinter = internalPrinter;
        this.iParser = internalParser;
        this.iLocale = locale;
        this.iOffsetParsed = bl;
        this.iChrono = chronology;
        this.iZone = dateTimeZone;
        this.iPivotYear = n;
        this.iDefaultYear = n2;
    }

    private void printTo(Appendable appendable, long l, Chronology object) throws IOException {
        long l2;
        InternalPrinter internalPrinter = this.requirePrinter();
        Chronology chronology = this.selectChronology((Chronology)object);
        DateTimeZone dateTimeZone = chronology.getZone();
        int n = dateTimeZone.getOffset(l);
        long l3 = l2 = (long)n + l;
        int n2 = n;
        object = dateTimeZone;
        if ((l ^ l2) < 0L) {
            l3 = l2;
            n2 = n;
            object = dateTimeZone;
            if (((long)n ^ l) >= 0L) {
                object = DateTimeZone.UTC;
                n2 = 0;
                l3 = l;
            }
        }
        internalPrinter.printTo(appendable, l3, chronology.withUTC(), n2, (DateTimeZone)object, this.iLocale);
    }

    private InternalParser requireParser() {
        InternalParser internalParser = this.iParser;
        if (internalParser == null) {
            throw new UnsupportedOperationException("Parsing not supported");
        }
        return internalParser;
    }

    private InternalPrinter requirePrinter() {
        InternalPrinter internalPrinter = this.iPrinter;
        if (internalPrinter == null) {
            throw new UnsupportedOperationException("Printing not supported");
        }
        return internalPrinter;
    }

    private Chronology selectChronology(Chronology chronology) {
        chronology = DateTimeUtils.getChronology(chronology);
        if (this.iChrono != null) {
            chronology = this.iChrono;
        }
        Chronology chronology2 = chronology;
        if (this.iZone != null) {
            chronology2 = chronology.withZone(this.iZone);
        }
        return chronology2;
    }

    public DateTimeParser getParser() {
        return InternalParserDateTimeParser.of(this.iParser);
    }

    InternalParser getParser0() {
        return this.iParser;
    }

    InternalPrinter getPrinter0() {
        return this.iPrinter;
    }

    /*
     * Enabled aggressive block sorting
     */
    public DateTime parseDateTime(String object) {
        Object object2;
        DateTimeParserBucket dateTimeParserBucket;
        int n;
        InternalParser internalParser = this.requireParser();
        int n2 = internalParser.parseInto(dateTimeParserBucket = new DateTimeParserBucket(0L, (Chronology)(object2 = this.selectChronology(null)), this.iLocale, this.iPivotYear, this.iDefaultYear), (CharSequence)object, 0);
        if (n2 >= 0) {
            n = n2;
            if (n2 < ((String)object).length()) throw new IllegalArgumentException(FormatUtils.createErrorMessage((String)object, n));
            long l = dateTimeParserBucket.computeMillis(true, (String)object);
            if (this.iOffsetParsed && dateTimeParserBucket.getOffsetInteger() != null) {
                object = ((Chronology)object2).withZone(DateTimeZone.forOffsetMillis(dateTimeParserBucket.getOffsetInteger()));
            } else {
                object = object2;
                if (dateTimeParserBucket.getZone() != null) {
                    object = ((Chronology)object2).withZone(dateTimeParserBucket.getZone());
                }
            }
            object = object2 = new DateTime(l, (Chronology)object);
            if (this.iZone == null) return object;
            return ((DateTime)object2).withZone(this.iZone);
        }
        n = ~n2;
        throw new IllegalArgumentException(FormatUtils.createErrorMessage((String)object, n));
    }

    public long parseMillis(String string2) {
        InternalParser internalParser = this.requireParser();
        return new DateTimeParserBucket(0L, this.selectChronology(this.iChrono), this.iLocale, this.iPivotYear, this.iDefaultYear).doParseMillis(internalParser, string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String print(ReadableInstant readableInstant) {
        StringBuilder stringBuilder = new StringBuilder(this.requirePrinter().estimatePrintedLength());
        try {
            this.printTo(stringBuilder, readableInstant);
            do {
                return stringBuilder.toString();
                break;
            } while (true);
        }
        catch (IOException iOException) {
            return stringBuilder.toString();
        }
    }

    public void printTo(Appendable appendable, ReadableInstant readableInstant) throws IOException {
        this.printTo(appendable, DateTimeUtils.getInstantMillis(readableInstant), DateTimeUtils.getInstantChronology(readableInstant));
    }

    public DateTimeFormatter withChronology(Chronology chronology) {
        if (this.iChrono == chronology) {
            return this;
        }
        return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, this.iOffsetParsed, chronology, this.iZone, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withZone(DateTimeZone dateTimeZone) {
        if (this.iZone == dateTimeZone) {
            return this;
        }
        return new DateTimeFormatter(this.iPrinter, this.iParser, this.iLocale, false, this.iChrono, dateTimeZone, this.iPivotYear, this.iDefaultYear);
    }

    public DateTimeFormatter withZoneUTC() {
        return this.withZone(DateTimeZone.UTC);
    }
}

