/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.tz;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.tz.CachedDateTimeZone;
import org.joda.time.tz.FixedDateTimeZone;

public class DateTimeZoneBuilder {
    public static DateTimeZone readFrom(DataInput object, String object2) throws IOException {
        switch (object.readUnsignedByte()) {
            default: {
                throw new IOException("Invalid encoding");
            }
            case 70: {
                object = object2 = new FixedDateTimeZone((String)object2, object.readUTF(), (int)DateTimeZoneBuilder.readMillis((DataInput)object), (int)DateTimeZoneBuilder.readMillis((DataInput)object));
                if (((DateTimeZone)object2).equals(DateTimeZone.UTC)) {
                    object = DateTimeZone.UTC;
                }
                return object;
            }
            case 67: {
                return CachedDateTimeZone.forZone(PrecalculatedZone.readFrom((DataInput)object, (String)object2));
            }
            case 80: 
        }
        return PrecalculatedZone.readFrom((DataInput)object, (String)object2);
    }

    public static DateTimeZone readFrom(InputStream inputStream, String string2) throws IOException {
        if (inputStream instanceof DataInput) {
            return DateTimeZoneBuilder.readFrom((DataInput)((Object)inputStream), string2);
        }
        return DateTimeZoneBuilder.readFrom(new DataInputStream(inputStream), string2);
    }

    static long readMillis(DataInput dataInput) throws IOException {
        int n = dataInput.readUnsignedByte();
        switch (n >> 6) {
            default: {
                return (long)(n << 26 >> 26) * 1800000L;
            }
            case 1: {
                return (long)(n << 26 >> 2 | dataInput.readUnsignedByte() << 16 | dataInput.readUnsignedByte() << 8 | dataInput.readUnsignedByte()) * 60000L;
            }
            case 2: {
                return ((long)n << 58 >> 26 | (long)(dataInput.readUnsignedByte() << 24) | (long)(dataInput.readUnsignedByte() << 16) | (long)(dataInput.readUnsignedByte() << 8) | (long)dataInput.readUnsignedByte()) * 1000L;
            }
            case 3: 
        }
        return dataInput.readLong();
    }

    private static final class DSTZone
    extends DateTimeZone {
        final Recurrence iEndRecurrence;
        final int iStandardOffset;
        final Recurrence iStartRecurrence;

        DSTZone(String string2, int n, Recurrence recurrence, Recurrence recurrence2) {
            super(string2);
            this.iStandardOffset = n;
            this.iStartRecurrence = recurrence;
            this.iEndRecurrence = recurrence2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Recurrence findMatchingRecurrence(long l) {
            long l2;
            int n = this.iStandardOffset;
            Recurrence recurrence = this.iStartRecurrence;
            Recurrence recurrence2 = this.iEndRecurrence;
            try {
                l2 = recurrence.next(l, n, recurrence2.getSaveMillis());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                l2 = l;
            }
            catch (ArithmeticException arithmeticException) {
                l2 = l;
            }
            try {
                long l3;
                l = l3 = recurrence2.next(l, n, recurrence.getSaveMillis());
            }
            catch (ArithmeticException arithmeticException) {
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            if (l2 > l) {
                return recurrence;
            }
            return recurrence2;
        }

        static DSTZone readFrom(DataInput dataInput, String string2) throws IOException {
            return new DSTZone(string2, (int)DateTimeZoneBuilder.readMillis(dataInput), Recurrence.readFrom(dataInput), Recurrence.readFrom(dataInput));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof DSTZone)) {
                        return false;
                    }
                    object = (DSTZone)object;
                    if (!this.getID().equals(((DateTimeZone)object).getID()) || this.iStandardOffset != ((DSTZone)object).iStandardOffset || !this.iStartRecurrence.equals(((DSTZone)object).iStartRecurrence) || !this.iEndRecurrence.equals(((DSTZone)object).iEndRecurrence)) break block5;
                }
                return true;
            }
            return false;
        }

        @Override
        public String getNameKey(long l) {
            return this.findMatchingRecurrence(l).getNameKey();
        }

        @Override
        public int getOffset(long l) {
            return this.iStandardOffset + this.findMatchingRecurrence(l).getSaveMillis();
        }

        @Override
        public int getStandardOffset(long l) {
            return this.iStandardOffset;
        }

        @Override
        public boolean isFixed() {
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long nextTransition(long l) {
            long l2;
            block11: {
                long l3;
                block10: {
                    int n = this.iStandardOffset;
                    Recurrence recurrence = this.iStartRecurrence;
                    Recurrence recurrence2 = this.iEndRecurrence;
                    try {
                        l2 = l3 = recurrence.next(l, n, recurrence2.getSaveMillis());
                        if (l > 0L) {
                            l2 = l3;
                            if (l3 < 0L) {
                                l2 = l;
                            }
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        l2 = l;
                    }
                    catch (ArithmeticException arithmeticException) {
                        l2 = l;
                    }
                    try {
                        l3 = recurrence2.next(l, n, recurrence.getSaveMillis());
                        if (l > 0L && l3 < 0L) {
                        }
                        break block10;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                    }
                    catch (ArithmeticException arithmeticException) {}
                    break block11;
                }
                l = l3;
            }
            if (l2 > l) {
                return l;
            }
            return l2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public long previousTransition(long l) {
            long l2;
            long l3;
            block11: {
                block10: {
                    ++l;
                    int n = this.iStandardOffset;
                    Recurrence recurrence = this.iStartRecurrence;
                    Recurrence recurrence2 = this.iEndRecurrence;
                    try {
                        l2 = l3 = recurrence.previous(l, n, recurrence2.getSaveMillis());
                        if (l < 0L) {
                            l2 = l3;
                            if (l3 > 0L) {
                                l2 = l;
                            }
                        }
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        l2 = l;
                    }
                    catch (ArithmeticException arithmeticException) {
                        l2 = l;
                    }
                    try {
                        l3 = recurrence2.previous(l, n, recurrence.getSaveMillis());
                        if (l < 0L && l3 > 0L) {
                        }
                        break block10;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                    }
                    catch (ArithmeticException arithmeticException) {}
                    break block11;
                }
                l = l3;
            }
            l3 = l;
            if (l2 > l) {
                l3 = l2;
            }
            return l3 - 1L;
        }
    }

    private static final class OfYear {
        final boolean iAdvance;
        final int iDayOfMonth;
        final int iDayOfWeek;
        final int iMillisOfDay;
        final char iMode;
        final int iMonthOfYear;

        OfYear(char c, int n, int n2, int n3, boolean bl, int n4) {
            if (c != 'u' && c != 'w' && c != 's') {
                throw new IllegalArgumentException("Unknown mode: " + c);
            }
            this.iMode = c;
            this.iMonthOfYear = n;
            this.iDayOfMonth = n2;
            this.iDayOfWeek = n3;
            this.iAdvance = bl;
            this.iMillisOfDay = n4;
        }

        static OfYear readFrom(DataInput dataInput) throws IOException {
            return new OfYear((char)dataInput.readUnsignedByte(), dataInput.readUnsignedByte(), dataInput.readByte(), dataInput.readUnsignedByte(), dataInput.readBoolean(), (int)DateTimeZoneBuilder.readMillis(dataInput));
        }

        private long setDayOfMonth(Chronology chronology, long l) {
            if (this.iDayOfMonth >= 0) {
                return chronology.dayOfMonth().set(l, this.iDayOfMonth);
            }
            l = chronology.dayOfMonth().set(l, 1);
            l = chronology.monthOfYear().add(l, 1);
            return chronology.dayOfMonth().add(l, this.iDayOfMonth);
        }

        private long setDayOfMonthNext(Chronology chronology, long l) {
            try {
                long l2 = this.setDayOfMonth(chronology, l);
                return l2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                if (this.iMonthOfYear == 2 && this.iDayOfMonth == 29) {
                    while (!chronology.year().isLeap(l)) {
                        l = chronology.year().add(l, 1);
                    }
                    return this.setDayOfMonth(chronology, l);
                }
                throw illegalArgumentException;
            }
        }

        private long setDayOfMonthPrevious(Chronology chronology, long l) {
            try {
                long l2 = this.setDayOfMonth(chronology, l);
                return l2;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                if (this.iMonthOfYear == 2 && this.iDayOfMonth == 29) {
                    while (!chronology.year().isLeap(l)) {
                        l = chronology.year().add(l, -1);
                    }
                    return this.setDayOfMonth(chronology, l);
                }
                throw illegalArgumentException;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private long setDayOfWeek(Chronology chronology, long l) {
            int n = chronology.dayOfWeek().get(l);
            int n2 = this.iDayOfWeek - n;
            long l2 = l;
            if (n2 == 0) return l2;
            if (this.iAdvance) {
                n = n2;
                if (n2 >= 0) return chronology.dayOfWeek().add(l, n);
                n = n2 + 7;
                do {
                    return chronology.dayOfWeek().add(l, n);
                    break;
                } while (true);
            }
            n = n2;
            if (n2 <= 0) return chronology.dayOfWeek().add(l, n);
            n = n2 - 7;
            return chronology.dayOfWeek().add(l, n);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof OfYear)) {
                        return false;
                    }
                    object = (OfYear)object;
                    if (this.iMode != ((OfYear)object).iMode || this.iMonthOfYear != ((OfYear)object).iMonthOfYear || this.iDayOfMonth != ((OfYear)object).iDayOfMonth || this.iDayOfWeek != ((OfYear)object).iDayOfWeek || this.iAdvance != ((OfYear)object).iAdvance || this.iMillisOfDay != ((OfYear)object).iMillisOfDay) break block5;
                }
                return true;
            }
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        public long next(long l, int n, int n2) {
            if (this.iMode == 'w') {
                n += n2;
            } else if (this.iMode != 's') {
                n = 0;
            }
            long l2 = (long)n + l;
            ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
            l = ((Chronology)iSOChronology).monthOfYear().set(l2, this.iMonthOfYear);
            l = ((Chronology)iSOChronology).millisOfDay().set(l, 0);
            long l3 = this.setDayOfMonthNext(iSOChronology, ((Chronology)iSOChronology).millisOfDay().add(l, this.iMillisOfDay));
            if (this.iDayOfWeek == 0) {
                l = l3;
                if (l3 > l2) return l - (long)n;
                l = this.setDayOfMonthNext(iSOChronology, ((Chronology)iSOChronology).year().add(l3, 1));
                return l - (long)n;
            }
            l = l3 = this.setDayOfWeek(iSOChronology, l3);
            if (l3 > l2) return l - (long)n;
            l = ((Chronology)iSOChronology).year().add(l3, 1);
            l = this.setDayOfWeek(iSOChronology, this.setDayOfMonthNext(iSOChronology, ((Chronology)iSOChronology).monthOfYear().set(l, this.iMonthOfYear)));
            return l - (long)n;
        }

        /*
         * Enabled aggressive block sorting
         */
        public long previous(long l, int n, int n2) {
            if (this.iMode == 'w') {
                n += n2;
            } else if (this.iMode != 's') {
                n = 0;
            }
            long l2 = (long)n + l;
            ISOChronology iSOChronology = ISOChronology.getInstanceUTC();
            l = ((Chronology)iSOChronology).monthOfYear().set(l2, this.iMonthOfYear);
            l = ((Chronology)iSOChronology).millisOfDay().set(l, 0);
            long l3 = this.setDayOfMonthPrevious(iSOChronology, ((Chronology)iSOChronology).millisOfDay().add(l, this.iMillisOfDay));
            if (this.iDayOfWeek == 0) {
                l = l3;
                if (l3 < l2) return l - (long)n;
                l = this.setDayOfMonthPrevious(iSOChronology, ((Chronology)iSOChronology).year().add(l3, -1));
                return l - (long)n;
            }
            l = l3 = this.setDayOfWeek(iSOChronology, l3);
            if (l3 < l2) return l - (long)n;
            l = ((Chronology)iSOChronology).year().add(l3, -1);
            l = this.setDayOfWeek(iSOChronology, this.setDayOfMonthPrevious(iSOChronology, ((Chronology)iSOChronology).monthOfYear().set(l, this.iMonthOfYear)));
            return l - (long)n;
        }

        public String toString() {
            return "[OfYear]\nMode: " + this.iMode + '\n' + "MonthOfYear: " + this.iMonthOfYear + '\n' + "DayOfMonth: " + this.iDayOfMonth + '\n' + "DayOfWeek: " + this.iDayOfWeek + '\n' + "AdvanceDayOfWeek: " + this.iAdvance + '\n' + "MillisOfDay: " + this.iMillisOfDay + '\n';
        }
    }

    private static final class PrecalculatedZone
    extends DateTimeZone {
        private final String[] iNameKeys;
        private final int[] iStandardOffsets;
        private final DSTZone iTailZone;
        private final long[] iTransitions;
        private final int[] iWallOffsets;

        private PrecalculatedZone(String string2, long[] arrl, int[] arrn, int[] arrn2, String[] arrstring, DSTZone dSTZone) {
            super(string2);
            this.iTransitions = arrl;
            this.iWallOffsets = arrn;
            this.iStandardOffsets = arrn2;
            this.iNameKeys = arrstring;
            this.iTailZone = dSTZone;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        static PrecalculatedZone readFrom(DataInput var0, String var1_2) throws IOException {
            var4_3 = var0.readUnsignedShort();
            var6_4 = new String[var4_3];
            for (var2_8 = 0; var2_8 < var4_3; ++var2_8) {
                var6_4[var2_8] = var0.readUTF();
            }
            var5_9 = var0.readInt();
            var7_10 = new long[var5_9];
            var8_11 = new int[var5_9];
            var9_12 = new int[var5_9];
            var10_13 = new String[var5_9];
            var2_8 = 0;
            do {
                block5: {
                    if (var2_8 >= var5_9) {
                        var6_5 = null;
                        if (var0.readBoolean() == false) return new PrecalculatedZone(var1_2, var7_10, var8_11, var9_12, var10_13, (DSTZone)var6_7);
                        var6_6 = DSTZone.readFrom(var0, var1_2);
                        return new PrecalculatedZone(var1_2, var7_10, var8_11, var9_12, var10_13, (DSTZone)var6_7);
                    }
                    var7_10[var2_8] = DateTimeZoneBuilder.readMillis(var0);
                    var8_11[var2_8] = (int)DateTimeZoneBuilder.readMillis(var0);
                    var9_12[var2_8] = (int)DateTimeZoneBuilder.readMillis(var0);
                    if (var4_3 >= 256) ** GOTO lbl25
                    try {
                        var3_14 = var0.readUnsignedByte();
                        break block5;
lbl25:
                        // 1 sources
                        var3_14 = var0.readUnsignedShort();
                    }
                    catch (ArrayIndexOutOfBoundsException var0_1) {
                        throw new IOException("Invalid encoding");
                    }
                }
                var10_13[var2_8] = var6_4[var3_14];
                ++var2_8;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof PrecalculatedZone)) {
                return false;
            }
            object = (PrecalculatedZone)object;
            if (!this.getID().equals(((DateTimeZone)object).getID()) || !Arrays.equals(this.iTransitions, ((PrecalculatedZone)object).iTransitions) || !Arrays.equals(this.iNameKeys, ((PrecalculatedZone)object).iNameKeys) || !Arrays.equals(this.iWallOffsets, ((PrecalculatedZone)object).iWallOffsets) || !Arrays.equals(this.iStandardOffsets, ((PrecalculatedZone)object).iStandardOffsets)) return false;
            if (this.iTailZone == null) {
                if (((PrecalculatedZone)object).iTailZone != null) return false;
                return true;
            }
            if (this.iTailZone.equals(((PrecalculatedZone)object).iTailZone)) return true;
            return false;
        }

        @Override
        public String getNameKey(long l) {
            long[] arrl = this.iTransitions;
            int n = Arrays.binarySearch(arrl, l);
            if (n >= 0) {
                return this.iNameKeys[n];
            }
            if ((n ^= 0xFFFFFFFF) < arrl.length) {
                if (n > 0) {
                    return this.iNameKeys[n - 1];
                }
                return "UTC";
            }
            if (this.iTailZone == null) {
                return this.iNameKeys[n - 1];
            }
            return this.iTailZone.getNameKey(l);
        }

        @Override
        public int getOffset(long l) {
            long[] arrl = this.iTransitions;
            int n = Arrays.binarySearch(arrl, l);
            if (n >= 0) {
                return this.iWallOffsets[n];
            }
            if ((n ^= 0xFFFFFFFF) < arrl.length) {
                if (n > 0) {
                    return this.iWallOffsets[n - 1];
                }
                return 0;
            }
            if (this.iTailZone == null) {
                return this.iWallOffsets[n - 1];
            }
            return this.iTailZone.getOffset(l);
        }

        @Override
        public int getStandardOffset(long l) {
            long[] arrl = this.iTransitions;
            int n = Arrays.binarySearch(arrl, l);
            if (n >= 0) {
                return this.iStandardOffsets[n];
            }
            if ((n ^= 0xFFFFFFFF) < arrl.length) {
                if (n > 0) {
                    return this.iStandardOffsets[n - 1];
                }
                return 0;
            }
            if (this.iTailZone == null) {
                return this.iStandardOffsets[n - 1];
            }
            return this.iTailZone.getStandardOffset(l);
        }

        @Override
        public boolean isFixed() {
            return false;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public long nextTransition(long l) {
            long[] arrl = this.iTransitions;
            int n = Arrays.binarySearch(arrl, l);
            n = n >= 0 ? ++n : (n ^= 0xFFFFFFFF);
            if (n < arrl.length) {
                return arrl[n];
            }
            long l2 = l;
            if (this.iTailZone == null) return l2;
            long l3 = arrl[arrl.length - 1];
            l2 = l;
            if (l >= l3) return this.iTailZone.nextTransition(l2);
            l2 = l3;
            return this.iTailZone.nextTransition(l2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public long previousTransition(long l) {
            long l2;
            long[] arrl = this.iTransitions;
            int n = Arrays.binarySearch(arrl, l);
            if (n >= 0) {
                l2 = l;
                if (l <= Long.MIN_VALUE) return l2;
                return l - 1L;
            }
            if ((n ^= 0xFFFFFFFF) < arrl.length) {
                l2 = l;
                if (n <= 0) return l2;
                long l3 = arrl[n - 1];
                l2 = l;
                if (l3 <= Long.MIN_VALUE) return l2;
                return l3 - 1L;
            }
            if (this.iTailZone != null && (l2 = this.iTailZone.previousTransition(l)) < l) {
                return l2;
            }
            long l4 = arrl[n - 1];
            l2 = l;
            if (l4 <= Long.MIN_VALUE) return l2;
            return l4 - 1L;
        }
    }

    private static final class Recurrence {
        final String iNameKey;
        final OfYear iOfYear;
        final int iSaveMillis;

        Recurrence(OfYear ofYear, String string2, int n) {
            this.iOfYear = ofYear;
            this.iNameKey = string2;
            this.iSaveMillis = n;
        }

        static Recurrence readFrom(DataInput dataInput) throws IOException {
            return new Recurrence(OfYear.readFrom(dataInput), dataInput.readUTF(), (int)DateTimeZoneBuilder.readMillis(dataInput));
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block5: {
                block4: {
                    if (this == object) break block4;
                    if (!(object instanceof Recurrence)) {
                        return false;
                    }
                    object = (Recurrence)object;
                    if (this.iSaveMillis != ((Recurrence)object).iSaveMillis || !this.iNameKey.equals(((Recurrence)object).iNameKey) || !this.iOfYear.equals(((Recurrence)object).iOfYear)) break block5;
                }
                return true;
            }
            return false;
        }

        public String getNameKey() {
            return this.iNameKey;
        }

        public int getSaveMillis() {
            return this.iSaveMillis;
        }

        public long next(long l, int n, int n2) {
            return this.iOfYear.next(l, n, n2);
        }

        public long previous(long l, int n, int n2) {
            return this.iOfYear.previous(l, n, n2);
        }

        public String toString() {
            return this.iOfYear + " named " + this.iNameKey + " at " + this.iSaveMillis;
        }
    }

}

