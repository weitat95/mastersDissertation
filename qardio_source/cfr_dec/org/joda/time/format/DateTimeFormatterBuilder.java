/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.MutableDateTime;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.DateTimeParserBucket;
import org.joda.time.format.DateTimeParserInternalParser;
import org.joda.time.format.DateTimePrinter;
import org.joda.time.format.DateTimePrinterInternalPrinter;
import org.joda.time.format.FormatUtils;
import org.joda.time.format.InternalParser;
import org.joda.time.format.InternalParserDateTimeParser;
import org.joda.time.format.InternalPrinter;

public class DateTimeFormatterBuilder {
    private ArrayList<Object> iElementPairs = new ArrayList();
    private Object iFormatter;

    private DateTimeFormatterBuilder append0(Object object) {
        this.iFormatter = null;
        this.iElementPairs.add(object);
        this.iElementPairs.add(object);
        return this;
    }

    private DateTimeFormatterBuilder append0(InternalPrinter internalPrinter, InternalParser internalParser) {
        this.iFormatter = null;
        this.iElementPairs.add(internalPrinter);
        this.iElementPairs.add(internalParser);
        return this;
    }

    static void appendUnknownString(Appendable appendable, int n) throws IOException {
        while (--n >= 0) {
            appendable.append('\ufffd');
        }
    }

    private void checkParser(DateTimeParser dateTimeParser) {
        if (dateTimeParser == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private void checkPrinter(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean csStartsWith(CharSequence charSequence, int n, String string2) {
        int n2 = string2.length();
        if (charSequence.length() - n >= n2) {
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    return true;
                }
                if (charSequence.charAt(n + n3) != string2.charAt(n3)) break;
                ++n3;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean csStartsWithIgnoreCase(CharSequence charSequence, int n, String string2) {
        int n2 = string2.length();
        if (charSequence.length() - n >= n2) {
            int n3 = 0;
            do {
                char c;
                if (n3 >= n2) {
                    return true;
                }
                char c2 = charSequence.charAt(n + n3);
                if (c2 != (c = string2.charAt(n3)) && (c2 = Character.toUpperCase(c2)) != (c = Character.toUpperCase(c)) && Character.toLowerCase(c2) != Character.toLowerCase(c)) break;
                ++n3;
            } while (true);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Object getFormatter() {
        Object object;
        Object object2;
        block3: {
            Object object3;
            block4: {
                Object object4;
                block5: {
                    object = object2 = this.iFormatter;
                    if (object2 != null) return object;
                    object = object2;
                    if (this.iElementPairs.size() != 2) break block3;
                    object4 = this.iElementPairs.get(0);
                    object3 = this.iElementPairs.get(1);
                    if (object4 == null) break block4;
                    if (object4 == object3) break block5;
                    object = object2;
                    if (object3 != null) break block3;
                }
                object = object4;
                break block3;
            }
            object = object3;
        }
        object2 = object;
        if (object == null) {
            object2 = new Composite(this.iElementPairs);
        }
        this.iFormatter = object2;
        return object2;
    }

    private boolean isParser(Object object) {
        if (object instanceof InternalParser) {
            if (object instanceof Composite) {
                return ((Composite)object).isParser();
            }
            return true;
        }
        return false;
    }

    private boolean isPrinter(Object object) {
        if (object instanceof InternalPrinter) {
            if (object instanceof Composite) {
                return ((Composite)object).isPrinter();
            }
            return true;
        }
        return false;
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter == null) {
            throw new IllegalArgumentException("No formatter supplied");
        }
        return this.append0(dateTimeFormatter.getPrinter0(), dateTimeFormatter.getParser0());
    }

    public DateTimeFormatterBuilder append(DateTimeParser dateTimeParser) {
        this.checkParser(dateTimeParser);
        return this.append0(null, DateTimeParserInternalParser.of(dateTimeParser));
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser[] arrdateTimeParser) {
        int n = 0;
        if (dateTimePrinter != null) {
            this.checkPrinter(dateTimePrinter);
        }
        if (arrdateTimeParser == null) {
            throw new IllegalArgumentException("No parsers supplied");
        }
        int n2 = arrdateTimeParser.length;
        if (n2 == 1) {
            if (arrdateTimeParser[0] == null) {
                throw new IllegalArgumentException("No parser supplied");
            }
            return this.append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), DateTimeParserInternalParser.of(arrdateTimeParser[0]));
        }
        InternalParser[] arrinternalParser = new InternalParser[n2];
        while (n < n2 - 1) {
            InternalParser internalParser;
            arrinternalParser[n] = internalParser = DateTimeParserInternalParser.of(arrdateTimeParser[n]);
            if (internalParser == null) {
                throw new IllegalArgumentException("Incomplete parser array");
            }
            ++n;
        }
        arrinternalParser[n] = DateTimeParserInternalParser.of(arrdateTimeParser[n]);
        return this.append0(DateTimePrinterInternalPrinter.of(dateTimePrinter), new MatchingParser(arrinternalParser));
    }

    public DateTimeFormatterBuilder appendCenturyOfEra(int n, int n2) {
        return this.appendSignedDecimal(DateTimeFieldType.centuryOfEra(), n, n2);
    }

    public DateTimeFormatterBuilder appendClockhourOfDay(int n) {
        return this.appendDecimal(DateTimeFieldType.clockhourOfDay(), n, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfHalfday(int n) {
        return this.appendDecimal(DateTimeFieldType.clockhourOfHalfday(), n, 2);
    }

    public DateTimeFormatterBuilder appendDayOfMonth(int n) {
        return this.appendDecimal(DateTimeFieldType.dayOfMonth(), n, 2);
    }

    public DateTimeFormatterBuilder appendDayOfWeek(int n) {
        return this.appendDecimal(DateTimeFieldType.dayOfWeek(), n, 1);
    }

    public DateTimeFormatterBuilder appendDayOfWeekShortText() {
        return this.appendShortText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfWeekText() {
        return this.appendText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfYear(int n) {
        return this.appendDecimal(DateTimeFieldType.dayOfYear(), n, 3);
    }

    public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType dateTimeFieldType, int n, int n2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        int n3 = n2;
        if (n2 < n) {
            n3 = n;
        }
        if (n < 0 || n3 <= 0) {
            throw new IllegalArgumentException();
        }
        if (n <= 1) {
            return this.append0(new UnpaddedNumber(dateTimeFieldType, n3, false));
        }
        return this.append0(new PaddedNumber(dateTimeFieldType, n3, false, n));
    }

    public DateTimeFormatterBuilder appendEraText() {
        return this.appendText(DateTimeFieldType.era());
    }

    public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType dateTimeFieldType, int n) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal number of digits: " + n);
        }
        return this.append0(new FixedNumber(dateTimeFieldType, n, false));
    }

    public DateTimeFormatterBuilder appendFraction(DateTimeFieldType dateTimeFieldType, int n, int n2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        int n3 = n2;
        if (n2 < n) {
            n3 = n;
        }
        if (n < 0 || n3 <= 0) {
            throw new IllegalArgumentException();
        }
        return this.append0(new Fraction(dateTimeFieldType, n, n3));
    }

    public DateTimeFormatterBuilder appendFractionOfHour(int n, int n2) {
        return this.appendFraction(DateTimeFieldType.hourOfDay(), n, n2);
    }

    public DateTimeFormatterBuilder appendFractionOfMinute(int n, int n2) {
        return this.appendFraction(DateTimeFieldType.minuteOfDay(), n, n2);
    }

    public DateTimeFormatterBuilder appendFractionOfSecond(int n, int n2) {
        return this.appendFraction(DateTimeFieldType.secondOfDay(), n, n2);
    }

    public DateTimeFormatterBuilder appendHalfdayOfDayText() {
        return this.appendText(DateTimeFieldType.halfdayOfDay());
    }

    public DateTimeFormatterBuilder appendHourOfDay(int n) {
        return this.appendDecimal(DateTimeFieldType.hourOfDay(), n, 2);
    }

    public DateTimeFormatterBuilder appendHourOfHalfday(int n) {
        return this.appendDecimal(DateTimeFieldType.hourOfHalfday(), n, 2);
    }

    public DateTimeFormatterBuilder appendLiteral(char c) {
        return this.append0(new CharacterLiteral(c));
    }

    public DateTimeFormatterBuilder appendLiteral(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        DateTimeFormatterBuilder dateTimeFormatterBuilder = this;
        switch (string2.length()) {
            default: {
                dateTimeFormatterBuilder = this.append0(new StringLiteral(string2));
            }
            case 0: {
                return dateTimeFormatterBuilder;
            }
            case 1: 
        }
        return this.append0(new CharacterLiteral(string2.charAt(0)));
    }

    public DateTimeFormatterBuilder appendMinuteOfHour(int n) {
        return this.appendDecimal(DateTimeFieldType.minuteOfHour(), n, 2);
    }

    public DateTimeFormatterBuilder appendMonthOfYear(int n) {
        return this.appendDecimal(DateTimeFieldType.monthOfYear(), n, 2);
    }

    public DateTimeFormatterBuilder appendMonthOfYearShortText() {
        return this.appendShortText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendMonthOfYearText() {
        return this.appendText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeParser dateTimeParser) {
        this.checkParser(dateTimeParser);
        return this.append0(null, new MatchingParser(new InternalParser[]{DateTimeParserInternalParser.of(dateTimeParser), null}));
    }

    public DateTimeFormatterBuilder appendSecondOfMinute(int n) {
        return this.appendDecimal(DateTimeFieldType.secondOfMinute(), n, 2);
    }

    public DateTimeFormatterBuilder appendShortText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        return this.append0(new TextField(dateTimeFieldType, true));
    }

    public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType dateTimeFieldType, int n, int n2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        int n3 = n2;
        if (n2 < n) {
            n3 = n;
        }
        if (n < 0 || n3 <= 0) {
            throw new IllegalArgumentException();
        }
        if (n <= 1) {
            return this.append0(new UnpaddedNumber(dateTimeFieldType, n3, true));
        }
        return this.append0(new PaddedNumber(dateTimeFieldType, n3, true, n));
    }

    public DateTimeFormatterBuilder appendText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        return this.append0(new TextField(dateTimeFieldType, false));
    }

    public DateTimeFormatterBuilder appendTimeZoneId() {
        return this.append0(TimeZoneId.INSTANCE, TimeZoneId.INSTANCE);
    }

    public DateTimeFormatterBuilder appendTimeZoneName() {
        return this.append0(new TimeZoneName(0, null), null);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String string2, String string3, boolean bl, int n, int n2) {
        return this.append0(new TimeZoneOffset(string2, string3, bl, n, n2));
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String string2, boolean bl, int n, int n2) {
        return this.append0(new TimeZoneOffset(string2, string2, bl, n, n2));
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> object) {
        object = new TimeZoneName(1, (Map<String, DateTimeZone>)object);
        return this.append0((InternalPrinter)object, (InternalParser)object);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int n, boolean bl) {
        return this.append0(new TwoDigitYear(DateTimeFieldType.weekyear(), n, bl));
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int n, boolean bl) {
        return this.append0(new TwoDigitYear(DateTimeFieldType.year(), n, bl));
    }

    public DateTimeFormatterBuilder appendWeekOfWeekyear(int n) {
        return this.appendDecimal(DateTimeFieldType.weekOfWeekyear(), n, 2);
    }

    public DateTimeFormatterBuilder appendWeekyear(int n, int n2) {
        return this.appendSignedDecimal(DateTimeFieldType.weekyear(), n, n2);
    }

    public DateTimeFormatterBuilder appendYear(int n, int n2) {
        return this.appendSignedDecimal(DateTimeFieldType.year(), n, n2);
    }

    public DateTimeFormatterBuilder appendYearOfEra(int n, int n2) {
        return this.appendDecimal(DateTimeFieldType.yearOfEra(), n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DateTimeFormatter toFormatter() {
        Object object = this.getFormatter();
        InternalPrinter internalPrinter = this.isPrinter(object) ? (InternalPrinter)object : null;
        object = this.isParser(object) ? (InternalParser)object : null;
        if (internalPrinter == null && object == null) {
            throw new UnsupportedOperationException("Both printing and parsing not supported");
        }
        return new DateTimeFormatter(internalPrinter, (InternalParser)object);
    }

    public DateTimeParser toParser() {
        Object object = this.getFormatter();
        if (this.isParser(object)) {
            return InternalParserDateTimeParser.of((InternalParser)object);
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    static class CharacterLiteral
    implements InternalParser,
    InternalPrinter {
        private final char iValue;

        CharacterLiteral(char c) {
            this.iValue = c;
        }

        @Override
        public int estimateParsedLength() {
            return 1;
        }

        @Override
        public int estimatePrintedLength() {
            return 1;
        }

        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            char c;
            if (n >= charSequence.length()) {
                return ~n;
            }
            char c2 = charSequence.charAt(n);
            if (c2 != (c = this.iValue) && (c2 = Character.toUpperCase(c2)) != (c = Character.toUpperCase(c)) && Character.toLowerCase(c2) != Character.toLowerCase(c)) {
                return ~n;
            }
            return n + 1;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }
    }

    static class Composite
    implements InternalParser,
    InternalPrinter {
        private final int iParsedLengthEstimate;
        private final InternalParser[] iParsers;
        private final int iPrintedLengthEstimate;
        private final InternalPrinter[] iPrinters;

        /*
         * Enabled aggressive block sorting
         */
        Composite(List<Object> object) {
            int n;
            int n2;
            int n3;
            int n4 = 0;
            ArrayList<Object> arrayList = new ArrayList<Object>();
            ArrayList<Object> arrayList2 = new ArrayList<Object>();
            this.decompose((List<Object>)object, arrayList, arrayList2);
            if (arrayList.contains(null) || arrayList.isEmpty()) {
                this.iPrinters = null;
                this.iPrintedLengthEstimate = 0;
            } else {
                n2 = arrayList.size();
                this.iPrinters = new InternalPrinter[n2];
                n3 = 0;
                for (n = 0; n < n2; n3 += object.estimatePrintedLength(), ++n) {
                    object = (InternalPrinter)arrayList.get(n);
                    this.iPrinters[n] = object;
                }
                this.iPrintedLengthEstimate = n3;
            }
            if (arrayList2.contains(null) || arrayList2.isEmpty()) {
                this.iParsers = null;
                this.iParsedLengthEstimate = 0;
                return;
            }
            n2 = arrayList2.size();
            this.iParsers = new InternalParser[n2];
            n3 = 0;
            n = n4;
            do {
                if (n >= n2) {
                    this.iParsedLengthEstimate = n3;
                    return;
                }
                object = (InternalParser)arrayList2.get(n);
                n3 += object.estimateParsedLength();
                this.iParsers[n] = object;
                ++n;
            } while (true);
        }

        private void addArrayToList(List<Object> list, Object[] arrobject) {
            if (arrobject != null) {
                for (int i = 0; i < arrobject.length; ++i) {
                    list.add(arrobject[i]);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int n = list.size();
            int n2 = 0;
            while (n2 < n) {
                Object object = list.get(n2);
                if (object instanceof Composite) {
                    this.addArrayToList(list2, ((Composite)object).iPrinters);
                } else {
                    list2.add(object);
                }
                if ((object = list.get(n2 + 1)) instanceof Composite) {
                    this.addArrayToList(list3, ((Composite)object).iParsers);
                } else {
                    list3.add(object);
                }
                n2 += 2;
            }
            return;
        }

        @Override
        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        @Override
        public int estimatePrintedLength() {
            return this.iPrintedLengthEstimate;
        }

        boolean isParser() {
            return this.iParsers != null;
        }

        boolean isPrinter() {
            return this.iPrinters != null;
        }

        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            InternalParser[] arrinternalParser = this.iParsers;
            if (arrinternalParser == null) {
                throw new UnsupportedOperationException();
            }
            int n2 = arrinternalParser.length;
            int n3 = 0;
            int n4 = n;
            for (n = n3; n < n2 && n4 >= 0; ++n) {
                n4 = arrinternalParser[n].parseInto(dateTimeParserBucket, charSequence, n4);
            }
            return n4;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            InternalPrinter[] arrinternalPrinter = this.iPrinters;
            if (arrinternalPrinter == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            int n2 = arrinternalPrinter.length;
            for (int i = 0; i < n2; ++i) {
                arrinternalPrinter[i].printTo(appendable, l, chronology, n, dateTimeZone, locale);
            }
        }
    }

    static class FixedNumber
    extends PaddedNumber {
        protected FixedNumber(DateTimeFieldType dateTimeFieldType, int n, boolean bl) {
            super(dateTimeFieldType, n, bl, n);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            block10: {
                block7: {
                    int n3;
                    block8: {
                        int n4;
                        block9: {
                            n2 = super.parseInto(dateTimeParserBucket, charSequence, n);
                            if (n2 < 0 || n2 == (n4 = this.iMaxParsedDigits + n)) break block7;
                            n3 = n4;
                            if (!this.iSigned) break block8;
                            if ((n = (int)charSequence.charAt(n)) == 45) break block9;
                            n3 = n4;
                            if (n != 43) break block8;
                        }
                        n3 = n4 + 1;
                    }
                    if (n2 > n3) {
                        return ~(n3 + 1);
                    }
                    if (n2 < n3) break block10;
                }
                return n2;
            }
            return ~n2;
        }
    }

    static class Fraction
    implements InternalParser,
    InternalPrinter {
        private final DateTimeFieldType iFieldType;
        protected int iMaxDigits;
        protected int iMinDigits;

        protected Fraction(DateTimeFieldType dateTimeFieldType, int n, int n2) {
            this.iFieldType = dateTimeFieldType;
            int n3 = n2;
            if (n2 > 18) {
                n3 = 18;
            }
            this.iMinDigits = n;
            this.iMaxDigits = n3;
        }

        /*
         * Enabled aggressive block sorting
         */
        private long[] getFractionData(long l, DateTimeField dateTimeField) {
            long l2 = dateTimeField.getDurationField().getUnitMillis();
            int n = this.iMaxDigits;
            do {
                long l3;
                switch (n) {
                    default: {
                        l3 = 1L;
                        break;
                    }
                    case 1: {
                        l3 = 10L;
                        break;
                    }
                    case 2: {
                        l3 = 100L;
                        break;
                    }
                    case 3: {
                        l3 = 1000L;
                        break;
                    }
                    case 4: {
                        l3 = 10000L;
                        break;
                    }
                    case 5: {
                        l3 = 100000L;
                        break;
                    }
                    case 6: {
                        l3 = 1000000L;
                        break;
                    }
                    case 7: {
                        l3 = 10000000L;
                        break;
                    }
                    case 8: {
                        l3 = 100000000L;
                        break;
                    }
                    case 9: {
                        l3 = 1000000000L;
                        break;
                    }
                    case 10: {
                        l3 = 10000000000L;
                        break;
                    }
                    case 11: {
                        l3 = 100000000000L;
                        break;
                    }
                    case 12: {
                        l3 = 1000000000000L;
                        break;
                    }
                    case 13: {
                        l3 = 10000000000000L;
                        break;
                    }
                    case 14: {
                        l3 = 100000000000000L;
                        break;
                    }
                    case 15: {
                        l3 = 1000000000000000L;
                        break;
                    }
                    case 16: {
                        l3 = 10000000000000000L;
                        break;
                    }
                    case 17: {
                        l3 = 100000000000000000L;
                        break;
                    }
                    case 18: {
                        l3 = 1000000000000000000L;
                    }
                }
                if (l2 * l3 / l3 == l2) {
                    return new long[]{l3 * l / l2, n};
                }
                --n;
            } while (true);
        }

        @Override
        public int estimateParsedLength() {
            return this.iMaxDigits;
        }

        @Override
        public int estimatePrintedLength() {
            return this.iMaxDigits;
        }

        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            DateTimeField dateTimeField = this.iFieldType.getField(dateTimeParserBucket.getChronology());
            int n2 = Math.min(this.iMaxDigits, charSequence.length() - n);
            long l = 0L;
            long l2 = dateTimeField.getDurationField().getUnitMillis() * 10L;
            int n3 = 0;
            do {
                char c;
                if (n3 >= n2 || (c = charSequence.charAt(n + n3)) < '0' || c > '9') {
                    l /= 10L;
                    if (n3 != 0) break;
                    return ~n;
                }
                ++n3;
                l += (long)(c - 48) * (l2 /= 10L);
            } while (true);
            if (l > Integer.MAX_VALUE) {
                return ~n;
            }
            dateTimeParserBucket.saveField(new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), MillisDurationField.INSTANCE, dateTimeField.getDurationField()), (int)l);
            return n3 + n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected void printTo(Appendable var1_1, long var2_2, Chronology var4_3) throws IOException {
            block9: {
                var4_3 = this.iFieldType.getField((Chronology)var4_3);
                var6_5 = this.iMinDigits;
                try {
                    var2_2 /* !! */  = var4_3.remainder(var2_2 /* !! */ );
                    ** if (var2_2 /* !! */  != 0L) goto lbl-1000
                }
                catch (RuntimeException var4_4) {
                    DateTimeFormatterBuilder.appendUnknownString(var1_1, var6_5);
                }
lbl-1000:
                // 2 sources
                {
                    while (--var6_5 >= 0) {
                        var1_1.append('0');
                    }
                    return;
                }
lbl-1000:
                // 1 sources
                {
                    break block9;
                }
                return;
            }
            var4_3 = this.getFractionData(var2_2 /* !! */ , (DateTimeField)var4_3);
            var2_2 /* !! */  = (long)var4_3[0];
            var5_6 = (int)var4_3[1];
            var4_3 = (Integer.MAX_VALUE & var2_2 /* !! */ ) == var2_2 /* !! */  ? Integer.toString((int)var2_2 /* !! */ ) : Long.toString(var2_2 /* !! */ );
            var7_7 = var4_3.length();
            while (var7_7 < var5_6) {
                var1_1.append('0');
                --var6_5;
                --var5_6;
            }
            if (var6_5 < var5_6) {
                do {
                    if (var6_5 >= var5_6 || var7_7 <= 1 || var4_3.charAt(var7_7 - 1) != '0') {
                        if (var7_7 >= var4_3.length()) break;
                        var5_6 = 0;
                        while (var5_6 < var7_7) {
                            var1_1.append(var4_3.charAt(var5_6));
                            ++var5_6;
                        }
                        return;
                    }
                    --var5_6;
                    --var7_7;
                } while (true);
            }
            var1_1.append((CharSequence)var4_3);
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            this.printTo(appendable, l, chronology);
        }
    }

    static class MatchingParser
    implements InternalParser {
        private final int iParsedLengthEstimate;
        private final InternalParser[] iParsers;

        MatchingParser(InternalParser[] arrinternalParser) {
            this.iParsers = arrinternalParser;
            int n = 0;
            int n2 = arrinternalParser.length;
            while (--n2 >= 0) {
                int n3;
                InternalParser internalParser = arrinternalParser[n2];
                if (internalParser == null || (n3 = internalParser.estimateParsedLength()) <= n) continue;
                n = n3;
            }
            this.iParsedLengthEstimate = n;
        }

        @Override
        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            Object object;
            int n3;
            int n4;
            block10: {
                InternalParser[] arrinternalParser = this.iParsers;
                int n5 = arrinternalParser.length;
                Object object2 = dateTimeParserBucket.saveState();
                object = null;
                n4 = n;
                n2 = n;
                for (n3 = 0; n3 < n5; ++n3) {
                    InternalParser internalParser = arrinternalParser[n3];
                    if (internalParser == null) {
                        if (n2 <= n) {
                            return n;
                        }
                        n3 = 1;
                        break block10;
                    }
                    int n6 = internalParser.parseInto(dateTimeParserBucket, charSequence, n);
                    if (n6 >= n) {
                        if (n6 > n2) {
                            if (n6 >= charSequence.length() || n3 + 1 >= n5 || arrinternalParser[n3 + 1] == null) {
                                return n6;
                            }
                            object = dateTimeParserBucket.saveState();
                            n2 = n6;
                        }
                    } else if (n6 < 0 && (n6 ^= 0xFFFFFFFF) > n4) {
                        n4 = n6;
                    }
                    dateTimeParserBucket.restoreState(object2);
                }
                n3 = 0;
            }
            if (n2 > n || n2 == n && n3 != 0) {
                if (object != null) {
                    dateTimeParserBucket.restoreState(object);
                }
                return n2;
            }
            return ~n4;
        }
    }

    static abstract class NumberFormatter
    implements InternalParser,
    InternalPrinter {
        protected final DateTimeFieldType iFieldType;
        protected final int iMaxParsedDigits;
        protected final boolean iSigned;

        NumberFormatter(DateTimeFieldType dateTimeFieldType, int n, boolean bl) {
            this.iFieldType = dateTimeFieldType;
            this.iMaxParsedDigits = n;
            this.iSigned = bl;
        }

        @Override
        public int estimateParsedLength() {
            return this.iMaxParsedDigits;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            block14: {
                int n3;
                int n4;
                char c;
                char c2;
                block13: {
                    block12: {
                        int n5 = Math.min(this.iMaxParsedDigits, charSequence.length() - n);
                        n2 = 0;
                        c = '\u0000';
                        n4 = 0;
                        do {
                            n3 = n4;
                            c2 = c;
                            if (n2 >= n5) break;
                            char c3 = charSequence.charAt(n + n2);
                            if (n2 == 0 && (c3 == '-' || c3 == '+') && this.iSigned) {
                                c = c3 == '-' ? (char)'\u0001' : '\u0000';
                                n4 = c3 == '+' ? 1 : 0;
                                n3 = n4;
                                c2 = c;
                                if (n2 + 1 >= n5) break;
                                c3 = charSequence.charAt(n + n2 + 1);
                                n3 = n4;
                                c2 = c;
                                if (c3 < '0') break;
                                if (c3 <= '9') {
                                    ++n2;
                                    n5 = Math.min(n5 + 1, charSequence.length() - n);
                                    continue;
                                }
                            } else {
                                n3 = n4;
                                c2 = c;
                                if (c3 < '0') break;
                                if (c3 <= '9') {
                                    ++n2;
                                    continue;
                                }
                            }
                            break block12;
                            break;
                        } while (true);
                        c = c2;
                        n4 = n3;
                    }
                    if (n2 == 0) {
                        return ~n;
                    }
                    if (n2 < 9) break block13;
                    if (n4 != 0) {
                        n2 = n + n2;
                        n = Integer.parseInt(((Object)charSequence.subSequence(n + 1, n2)).toString());
                    } else {
                        n2 = n + n2;
                        n = Integer.parseInt(((Object)charSequence.subSequence(n, n2)).toString());
                    }
                    break block14;
                }
                n4 = c != '\u0000' || n4 != 0 ? n + 1 : n;
                try {
                    c2 = charSequence.charAt(n4);
                    n3 = n + n2;
                    n2 = c2 - 48;
                    n = n4 + 1;
                    n4 = n2;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    return ~n;
                }
                while (n < n3) {
                    n4 = charSequence.charAt(n) + ((n4 << 3) + (n4 << 1)) - 48;
                    ++n;
                }
                n = n4;
                n2 = n3;
                if (c != '\u0000') {
                    n = -n4;
                    n2 = n3;
                }
            }
            dateTimeParserBucket.saveField(this.iFieldType, n);
            return n2;
        }
    }

    static class PaddedNumber
    extends NumberFormatter {
        protected final int iMinPrintedDigits;

        protected PaddedNumber(DateTimeFieldType dateTimeFieldType, int n, boolean bl, int n2) {
            super(dateTimeFieldType, n, bl);
            this.iMinPrintedDigits = n2;
        }

        @Override
        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.appendPaddedInteger(appendable, this.iFieldType.getField(chronology).get(l), this.iMinPrintedDigits);
                return;
            }
            catch (RuntimeException runtimeException) {
                DateTimeFormatterBuilder.appendUnknownString(appendable, this.iMinPrintedDigits);
                return;
            }
        }
    }

    static class StringLiteral
    implements InternalParser,
    InternalPrinter {
        private final String iValue;

        StringLiteral(String string2) {
            this.iValue = string2;
        }

        @Override
        public int estimateParsedLength() {
            return this.iValue.length();
        }

        @Override
        public int estimatePrintedLength() {
            return this.iValue.length();
        }

        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            if (DateTimeFormatterBuilder.csStartsWithIgnoreCase(charSequence, n, this.iValue)) {
                return this.iValue.length() + n;
            }
            return ~n;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(this.iValue);
        }
    }

    static class TextField
    implements InternalParser,
    InternalPrinter {
        private static Map<Locale, Map<DateTimeFieldType, Object[]>> cParseCache = new ConcurrentHashMap<Locale, Map<DateTimeFieldType, Object[]>>();
        private final DateTimeFieldType iFieldType;
        private final boolean iShort;

        TextField(DateTimeFieldType dateTimeFieldType, boolean bl) {
            this.iFieldType = dateTimeFieldType;
            this.iShort = bl;
        }

        private String print(long l, Chronology object, Locale locale) {
            object = this.iFieldType.getField((Chronology)object);
            if (this.iShort) {
                return ((DateTimeField)object).getAsShortText(l, locale);
            }
            return ((DateTimeField)object).getAsText(l, locale);
        }

        @Override
        public int estimateParsedLength() {
            return this.estimatePrintedLength();
        }

        @Override
        public int estimatePrintedLength() {
            if (this.iShort) {
                return 6;
            }
            return 20;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            Locale locale = dateTimeParserBucket.getLocale();
            Object object = cParseCache.get(locale);
            if (object == null) {
                object = new ConcurrentHashMap<DateTimeFieldType, Object[]>();
                cParseCache.put(locale, (Map<DateTimeFieldType, Object[]>)object);
            }
            Object object2 = object.get(this.iFieldType);
            if (object2 == null) {
                object2 = new ConcurrentHashMap(32);
                MutableDateTime.Property property = new MutableDateTime(0L, DateTimeZone.UTC).property(this.iFieldType);
                int n3 = property.getMaximumValueOverall();
                if (n3 - n2 > 32) {
                    return ~n;
                }
                int n4 = property.getMaximumTextLength(locale);
                for (n2 = property.getMinimumValueOverall(); n2 <= n3; ++n2) {
                    property.set(n2);
                    object2.put(property.getAsShortText(locale), Boolean.TRUE);
                    object2.put(property.getAsShortText(locale).toLowerCase(locale), Boolean.TRUE);
                    object2.put(property.getAsShortText(locale).toUpperCase(locale), Boolean.TRUE);
                    object2.put(property.getAsText(locale), Boolean.TRUE);
                    object2.put(property.getAsText(locale).toLowerCase(locale), Boolean.TRUE);
                    object2.put(property.getAsText(locale).toUpperCase(locale), Boolean.TRUE);
                }
                n2 = n4;
                if ("en".equals(locale.getLanguage())) {
                    n2 = n4;
                    if (this.iFieldType == DateTimeFieldType.era()) {
                        object2.put("BCE", Boolean.TRUE);
                        object2.put("bce", Boolean.TRUE);
                        object2.put("CE", Boolean.TRUE);
                        object2.put("ce", Boolean.TRUE);
                        n2 = 3;
                    }
                }
                object.put(this.iFieldType, new Object[]{object2, n2});
                object = object2;
            } else {
                object = (Map)object2[0];
                n2 = (Integer)object2[1];
            }
            n2 = Math.min(charSequence.length(), n2 + n);
            while (n2 > n) {
                object2 = ((Object)charSequence.subSequence(n, n2)).toString();
                if (object.containsKey(object2)) {
                    dateTimeParserBucket.saveField(this.iFieldType, (String)object2, locale);
                    return n2;
                }
                --n2;
            }
            return ~n;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                appendable.append(this.print(l, chronology, locale));
                return;
            }
            catch (RuntimeException runtimeException) {
                appendable.append('\ufffd');
                return;
            }
        }
    }

    static enum TimeZoneId implements InternalParser,
    InternalPrinter
    {
        INSTANCE;

        private static final List<String> ALL_IDS;
        private static final List<String> BASE_GROUPED_IDS;
        private static final Map<String, List<String>> GROUPED_IDS;
        static final int MAX_LENGTH;
        static final int MAX_PREFIX_LENGTH;

        /*
         * Enabled aggressive block sorting
         */
        static {
            BASE_GROUPED_IDS = new ArrayList<String>();
            ALL_IDS = new ArrayList<String>(DateTimeZone.getAvailableIDs());
            Collections.sort(ALL_IDS);
            GROUPED_IDS = new HashMap<String, List<String>>();
            Iterator<String> iterator = ALL_IDS.iterator();
            int n = 0;
            int n2 = 0;
            do {
                if (!iterator.hasNext()) {
                    MAX_LENGTH = n2;
                    MAX_PREFIX_LENGTH = n;
                    return;
                }
                String string2 = iterator.next();
                int n3 = string2.indexOf(47);
                if (n3 >= 0) {
                    int n4 = n3;
                    if (n3 < string2.length()) {
                        n4 = n3 + 1;
                    }
                    n = Math.max(n, n4);
                    String string3 = string2.substring(0, n4 + 1);
                    String string4 = string2.substring(n4);
                    if (!GROUPED_IDS.containsKey(string3)) {
                        GROUPED_IDS.put(string3, new ArrayList());
                    }
                    GROUPED_IDS.get(string3).add(string4);
                } else {
                    BASE_GROUPED_IDS.add(string2);
                }
                n2 = Math.max(n2, string2.length());
            } while (true);
        }

        @Override
        public int estimateParsedLength() {
            return MAX_LENGTH;
        }

        @Override
        public int estimatePrintedLength() {
            return MAX_LENGTH;
        }

        /*
         * Unable to fully structure code
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int parseInto(DateTimeParserBucket var1_1, CharSequence var2_2, int var3_3) {
            var10_4 = TimeZoneId.BASE_GROUPED_IDS;
            var6_5 = var2_2.length();
            var5_6 = Math.min(var6_5, TimeZoneId.MAX_PREFIX_LENGTH + var3_3);
            var9_7 = "";
            for (var4_8 = var3_3; var4_8 < var5_6; ++var4_8) {
                if (var2_2.charAt(var4_8) != '/') continue;
                var9_7 = var2_2.subSequence(var3_3, var4_8 + 1).toString();
                var5_6 = var3_3 + var9_7.length();
                if (var4_8 < var6_5) {
                    var7_9 = var9_7 + var2_2.charAt(var4_8 + 1);
lbl11:
                    // 2 sources
                    do {
                        var10_4 = TimeZoneId.GROUPED_IDS.get(var7_9);
                        if (var10_4 != null) break;
                        return ~var3_3;
                        break;
                    } while (true);
                    var4_8 = var5_6;
lbl16:
                    // 2 sources
                    do {
                        block8: {
                            var7_9 = null;
                            block3: for (var5_6 = 0; var5_6 < var10_4.size(); ++var5_6) {
                                var11_11 = var10_4.get(var5_6);
                                if (!DateTimeFormatterBuilder.csStartsWith(var2_2, var4_8, var11_11)) break block8;
                                var8_10 = var11_11;
                                if (var7_9 != null) {
                                    if (var11_11.length() <= var7_9.length()) break block8;
                                    var8_10 = var11_11;
                                }
lbl25:
                                // 4 sources
                                do {
                                    var7_9 = var8_10;
                                    continue block3;
                                    break;
                                } while (true);
                            }
                            if (var7_9 == null) return ~var3_3;
                            var1_1.setZone(DateTimeZone.forID(var9_7 + var7_9));
                            return var7_9.length() + var4_8;
                        }
                        var8_10 = var7_9;
                        ** continue;
                        break;
                    } while (true);
                }
                var7_9 = var9_7;
                ** continue;
            }
            var4_8 = var3_3;
            ** while (true)
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void printTo(Appendable appendable, long l, Chronology object, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            object = dateTimeZone != null ? dateTimeZone.getID() : "";
            appendable.append((CharSequence)object);
        }
    }

    static class TimeZoneName
    implements InternalParser,
    InternalPrinter {
        private final Map<String, DateTimeZone> iParseLookup;
        private final int iType;

        TimeZoneName(int n, Map<String, DateTimeZone> map) {
            this.iType = n;
            this.iParseLookup = map;
        }

        private String print(long l, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone == null) {
                return "";
            }
            switch (this.iType) {
                default: {
                    return "";
                }
                case 0: {
                    return dateTimeZone.getName(l, locale);
                }
                case 1: 
            }
            return dateTimeZone.getShortName(l, locale);
        }

        @Override
        public int estimateParsedLength() {
            if (this.iType == 1) {
                return 4;
            }
            return 20;
        }

        @Override
        public int estimatePrintedLength() {
            if (this.iType == 1) {
                return 4;
            }
            return 20;
        }

        /*
         * Unable to fully structure code
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int parseInto(DateTimeParserBucket var1_1, CharSequence var2_2, int var3_3) {
            block6: {
                block7: {
                    var6_4 = this.iParseLookup;
                    if (var6_4 != null) lbl-1000:
                    // 2 sources
                    {
                        do {
                            var4_5 = null;
                            block1: for (String var7_8 : var6_4.keySet()) {
                                if (!DateTimeFormatterBuilder.csStartsWith(var2_2, var3_3, var7_8)) break block6;
                                var5_7 = var7_8;
                                if (var4_5 != null) {
                                    if (var7_8.length() <= var4_5.length()) break block6;
                                    var5_7 = var7_8;
                                }
lbl11:
                                // 4 sources
                                do {
                                    var4_5 = var5_7;
                                    continue block1;
                                    break;
                                } while (true);
                            }
                            break block7;
                            break;
                        } while (true);
                    }
                    var6_4 = DateTimeUtils.getDefaultTimeZoneNames();
                    ** while (true)
                }
                if (var4_5 == null) return ~var3_3;
                var1_1.setZone(var6_4.get(var4_5));
                return var4_5.length() + var3_3;
            }
            var5_7 = var4_5;
            ** while (true)
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            appendable.append(this.print(l - (long)n, dateTimeZone, locale));
        }
    }

    static class TimeZoneOffset
    implements InternalParser,
    InternalPrinter {
        private final int iMaxFields;
        private final int iMinFields;
        private final boolean iShowSeparators;
        private final String iZeroOffsetParseText;
        private final String iZeroOffsetPrintText;

        TimeZoneOffset(String string2, String string3, boolean bl, int n, int n2) {
            int n3 = 4;
            this.iZeroOffsetPrintText = string2;
            this.iZeroOffsetParseText = string3;
            this.iShowSeparators = bl;
            if (n <= 0 || n2 < n) {
                throw new IllegalArgumentException();
            }
            if (n > 4) {
                n2 = 4;
                n = n3;
            }
            this.iMinFields = n;
            this.iMaxFields = n2;
        }

        private int digitCount(CharSequence charSequence, int n, int n2) {
            n2 = Math.min(charSequence.length() - n, n2);
            int n3 = 0;
            char c;
            while (n2 > 0 && (c = charSequence.charAt(n + n3)) >= '0' && c <= '9') {
                ++n3;
                --n2;
            }
            return n3;
        }

        @Override
        public int estimateParsedLength() {
            return this.estimatePrintedLength();
        }

        @Override
        public int estimatePrintedLength() {
            int n;
            int n2 = n = this.iMinFields + 1 << 1;
            if (this.iShowSeparators) {
                n2 = n + (this.iMinFields - 1);
            }
            n = n2;
            if (this.iZeroOffsetPrintText != null) {
                n = n2;
                if (this.iZeroOffsetPrintText.length() > n2) {
                    n = this.iZeroOffsetPrintText.length();
                }
            }
            return n;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            boolean bl;
            block28: {
                boolean bl2;
                int n3;
                int n4;
                block35: {
                    block33: {
                        int n5;
                        block34: {
                            block32: {
                                block30: {
                                    block31: {
                                        block29: {
                                            block27: {
                                                bl2 = false;
                                                n2 = charSequence.length() - n;
                                                if (this.iZeroOffsetParseText != null) {
                                                    if (this.iZeroOffsetParseText.length() == 0) {
                                                        if (n2 <= 0 || (n3 = charSequence.charAt(n)) != 45 && n3 != 43) {
                                                            dateTimeParserBucket.setOffset(0);
                                                            return n;
                                                        }
                                                    } else if (DateTimeFormatterBuilder.csStartsWithIgnoreCase(charSequence, n, this.iZeroOffsetParseText)) {
                                                        dateTimeParserBucket.setOffset(0);
                                                        return n + this.iZeroOffsetParseText.length();
                                                    }
                                                }
                                                if (n2 <= 1) {
                                                    return ~n;
                                                }
                                                n3 = charSequence.charAt(n);
                                                if (n3 == 45) {
                                                    bl = true;
                                                } else {
                                                    if (n3 != 43) {
                                                        return ~n;
                                                    }
                                                    bl = false;
                                                }
                                                if (this.digitCount(charSequence, n3 = n + 1, 2) < 2) {
                                                    return ~n3;
                                                }
                                                n = FormatUtils.parseTwoDigits(charSequence, n3);
                                                if (n > 23) {
                                                    return ~n3;
                                                }
                                                n *= 3600000;
                                                n4 = n2 - 1 - 2;
                                                n3 += 2;
                                                if (n4 > 0) break block27;
                                                n2 = n3;
                                                break block28;
                                            }
                                            n5 = charSequence.charAt(n3);
                                            if (n5 != 58) break block29;
                                            n2 = n3 + 1;
                                            --n4;
                                            bl2 = true;
                                            break block30;
                                        }
                                        if (n5 < 48) break block31;
                                        n2 = n3;
                                        if (n5 <= 57) break block30;
                                    }
                                    n2 = n3;
                                    break block28;
                                }
                                if ((n3 = this.digitCount(charSequence, n2, 2)) == 0 && !bl2) break block28;
                                if (n3 < 2) {
                                    return ~n2;
                                }
                                n3 = FormatUtils.parseTwoDigits(charSequence, n2);
                                if (n3 > 59) {
                                    return ~n2;
                                }
                                n += n3 * 60000;
                                n5 = n4 - 2;
                                n3 = n2 + 2;
                                if (n5 > 0) break block32;
                                n2 = n3;
                                break block28;
                            }
                            n4 = n5;
                            n2 = n3;
                            if (!bl2) break block33;
                            if (charSequence.charAt(n3) == ':') break block34;
                            n2 = n3;
                            break block28;
                        }
                        n4 = n5 - 1;
                        n2 = n3 + 1;
                    }
                    if ((n3 = this.digitCount(charSequence, n2, 2)) == 0 && !bl2) break block28;
                    if (n3 < 2) {
                        return ~n2;
                    }
                    n3 = FormatUtils.parseTwoDigits(charSequence, n2);
                    if (n3 > 59) {
                        return ~n2;
                    }
                    n += n3 * 1000;
                    n3 = n4 - 2;
                    n2 += 2;
                    if (n3 <= 0) break block28;
                    n3 = n2;
                    if (!bl2) break block35;
                    if (charSequence.charAt(n2) != '.' && charSequence.charAt(n2) != ',') break block28;
                    n3 = n2 + 1;
                }
                if ((n4 = this.digitCount(charSequence, n3, 3)) == 0 && !bl2) {
                    n2 = n3;
                } else {
                    if (n4 < 1) {
                        return ~n3;
                    }
                    n2 = n3 + 1;
                    n3 = (charSequence.charAt(n3) - 48) * 100 + n;
                    if (n4 > 1) {
                        n = n2 + 1;
                        n3 = (charSequence.charAt(n2) - 48) * 10 + n3;
                        if (n4 > 2) {
                            n2 = n3 + (charSequence.charAt(n) - 48);
                            n3 = n + 1;
                            n = n2;
                            n2 = n3;
                        } else {
                            n2 = n;
                            n = n3;
                        }
                    } else {
                        n = n3;
                    }
                }
            }
            if (bl) {
                n = -n;
            }
            dateTimeParserBucket.setOffset(n);
            return n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            block10: {
                block9: {
                    if (dateTimeZone == null) break block9;
                    if (n == 0 && this.iZeroOffsetPrintText != null) {
                        appendable.append(this.iZeroOffsetPrintText);
                        return;
                    }
                    if (n >= 0) {
                        appendable.append('+');
                    } else {
                        appendable.append('-');
                        n = -n;
                    }
                    int n2 = n / 3600000;
                    FormatUtils.appendPaddedInteger(appendable, n2, 2);
                    if (this.iMaxFields == 1 || (n -= n2 * 3600000) == 0 && this.iMinFields <= 1) break block9;
                    n2 = n / 60000;
                    if (this.iShowSeparators) {
                        appendable.append(':');
                    }
                    FormatUtils.appendPaddedInteger(appendable, n2, 2);
                    if (this.iMaxFields == 2 || (n -= n2 * 60000) == 0 && this.iMinFields <= 2) break block9;
                    n2 = n / 1000;
                    if (this.iShowSeparators) {
                        appendable.append(':');
                    }
                    FormatUtils.appendPaddedInteger(appendable, n2, 2);
                    if (this.iMaxFields != 3 && ((n -= n2 * 1000) != 0 || this.iMinFields > 3)) break block10;
                }
                return;
            }
            if (this.iShowSeparators) {
                appendable.append('.');
            }
            FormatUtils.appendPaddedInteger(appendable, n, 3);
        }
    }

    static class TwoDigitYear
    implements InternalParser,
    InternalPrinter {
        private final boolean iLenientParse;
        private final int iPivot;
        private final DateTimeFieldType iType;

        TwoDigitYear(DateTimeFieldType dateTimeFieldType, int n, boolean bl) {
            this.iType = dateTimeFieldType;
            this.iPivot = n;
            this.iLenientParse = bl;
        }

        private int getTwoDigitYear(long l, Chronology chronology) {
            int n;
            block2: {
                int n2;
                try {
                    n = n2 = this.iType.getField(chronology).get(l);
                    if (n2 >= 0) break block2;
                }
                catch (RuntimeException runtimeException) {
                    return -1;
                }
                n = -n2;
            }
            return n % 100;
        }

        @Override
        public int estimateParsedLength() {
            if (this.iLenientParse) {
                return 4;
            }
            return 2;
        }

        @Override
        public int estimatePrintedLength() {
            return 2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int parseInto(DateTimeParserBucket dateTimeParserBucket, CharSequence charSequence, int n) {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            block18: {
                block19: {
                    block17: {
                        n2 = 0;
                        n5 = charSequence.length() - n;
                        if (this.iLenientParse) break block17;
                        n6 = n;
                        if (Math.min(2, n5) < 2) {
                            return ~n;
                        }
                        break block18;
                    }
                    n4 = 0;
                    n3 = 0;
                    n6 = 0;
                    while (n4 < n5) {
                        char c = charSequence.charAt(n + n4);
                        if (n4 == 0 && (c == '-' || c == '+')) {
                            n3 = c == '-' ? 1 : 0;
                            if (n3 != 0) {
                                ++n4;
                                n6 = 1;
                                continue;
                            }
                            ++n;
                            n6 = 1;
                            --n5;
                            continue;
                        }
                        if (c < '0' || c > '9') break;
                        ++n4;
                    }
                    if (n4 == 0) {
                        return ~n;
                    }
                    if (n6 != 0) break block19;
                    n6 = n;
                    if (n4 == 2) break block18;
                }
                if (n4 >= 9) {
                    n6 = n + n4;
                    n4 = Integer.parseInt(((Object)charSequence.subSequence(n, n6)).toString());
                } else {
                    n6 = n3 != 0 ? n + 1 : n;
                    try {
                        n2 = charSequence.charAt(n6);
                        n5 = n + n4;
                        n = n2 - 48;
                    }
                    catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                        return ~n;
                    }
                    for (n4 = n6 + 1; n4 < n5; ++n4) {
                        n = charSequence.charAt(n4) + ((n << 3) + (n << 1)) - 48;
                    }
                    n4 = n;
                    n6 = n5;
                    if (n3 != 0) {
                        n4 = -n;
                        n6 = n5;
                    }
                }
                dateTimeParserBucket.saveField(this.iType, n4);
                return n6;
            }
            if ((n = (int)charSequence.charAt(n6)) < 48 || n > 57) {
                return ~n6;
            }
            n -= 48;
            n3 = charSequence.charAt(n6 + 1);
            if (n3 < 48 || n3 > 57) {
                return ~n6;
            }
            n4 = (n << 1) + (n << 3) + n3 - 48;
            n = this.iPivot;
            if (dateTimeParserBucket.getPivotYear() != null) {
                n = dateTimeParserBucket.getPivotYear();
            }
            n = (n5 = n - 50) >= 0 ? n5 % 100 : (n5 + 1) % 100 + 99;
            n3 = n2;
            if (n4 < n) {
                n3 = 100;
            }
            dateTimeParserBucket.saveField(this.iType, n3 + n5 - n + n4);
            return n6 + 2;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            n = this.getTwoDigitYear(l, chronology);
            if (n < 0) {
                appendable.append('\ufffd');
                appendable.append('\ufffd');
                return;
            }
            FormatUtils.appendPaddedInteger(appendable, n, 2);
        }
    }

    static class UnpaddedNumber
    extends NumberFormatter {
        protected UnpaddedNumber(DateTimeFieldType dateTimeFieldType, int n, boolean bl) {
            super(dateTimeFieldType, n, bl);
        }

        @Override
        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        @Override
        public void printTo(Appendable appendable, long l, Chronology chronology, int n, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.appendUnpaddedInteger(appendable, this.iFieldType.getField(chronology).get(l));
                return;
            }
            catch (RuntimeException runtimeException) {
                appendable.append('\ufffd');
                return;
            }
        }
    }

}

