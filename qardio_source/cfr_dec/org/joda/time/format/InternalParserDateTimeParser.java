/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
import org.joda.time.format.DateTimeParserInternalParser;
import org.joda.time.format.InternalParser;

class InternalParserDateTimeParser
implements DateTimeParser,
InternalParser {
    private final InternalParser underlying;

    private InternalParserDateTimeParser(InternalParser internalParser) {
        this.underlying = internalParser;
    }

    static DateTimeParser of(InternalParser internalParser) {
        if (internalParser instanceof DateTimeParserInternalParser) {
            return ((DateTimeParserInternalParser)internalParser).getUnderlying();
        }
        if (internalParser instanceof DateTimeParser) {
            return (DateTimeParser)((Object)internalParser);
        }
        if (internalParser == null) {
            return null;
        }
        return new InternalParserDateTimeParser(internalParser);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof InternalParserDateTimeParser) {
            object = (InternalParserDateTimeParser)object;
            return this.underlying.equals(((InternalParserDateTimeParser)object).underlying);
        }
        return false;
    }

    @Override
    public int estimateParsedLength() {
        return this.underlying.estimateParsedLength();
    }

    @Override
    public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
        return this.underlying.parseInto(dateTimeParserBucket, charSequence, n);
    }

    @Override
    public int parseInto(DateTimeParserBucket dateTimeParserBucket, String string2, int n) {
        return this.underlying.parseInto(dateTimeParserBucket, string2, n);
    }
}

