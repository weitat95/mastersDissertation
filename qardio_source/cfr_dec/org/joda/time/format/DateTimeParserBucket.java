/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.format;

import java.util.Arrays;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.IllegalInstantException;
import org.joda.time.format.FormatUtils;
import org.joda.time.format.InternalParser;

public class DateTimeParserBucket {
    private final Chronology iChrono;
    private final Integer iDefaultPivotYear;
    private final int iDefaultYear;
    private final DateTimeZone iDefaultZone;
    private final Locale iLocale;
    private final long iMillis;
    private Integer iOffset;
    private Integer iPivotYear;
    private SavedField[] iSavedFields;
    private int iSavedFieldsCount;
    private boolean iSavedFieldsShared;
    private Object iSavedState;
    private DateTimeZone iZone;

    public DateTimeParserBucket(long l, Chronology object, Locale locale, Integer n, int n2) {
        object = DateTimeUtils.getChronology((Chronology)object);
        this.iMillis = l;
        this.iDefaultZone = ((Chronology)object).getZone();
        this.iChrono = ((Chronology)object).withUTC();
        object = locale;
        if (locale == null) {
            object = Locale.getDefault();
        }
        this.iLocale = object;
        this.iDefaultYear = n2;
        this.iDefaultPivotYear = n;
        this.iZone = this.iDefaultZone;
        this.iPivotYear = this.iDefaultPivotYear;
        this.iSavedFields = new SavedField[8];
    }

    static int compareReverse(DurationField durationField, DurationField durationField2) {
        if (durationField == null || !durationField.isSupported()) {
            if (durationField2 == null || !durationField2.isSupported()) {
                return 0;
            }
            return -1;
        }
        if (durationField2 == null || !durationField2.isSupported()) {
            return 1;
        }
        return -durationField.compareTo(durationField2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private SavedField obtainSaveField() {
        int n = this.iSavedFieldsCount;
        Object object = this.iSavedFields;
        if (n == ((SavedField[])object).length || this.iSavedFieldsShared) {
            int n2 = n == ((SavedField[])object).length ? n * 2 : ((SavedField[])object).length;
            SavedField[] arrsavedField = new SavedField[n2];
            System.arraycopy(object, 0, arrsavedField, 0, n);
            this.iSavedFields = arrsavedField;
            this.iSavedFieldsShared = false;
            object = arrsavedField;
        }
        this.iSavedState = null;
        SavedField savedField = object[n];
        if (savedField == null) {
            SavedField savedField2;
            object[n] = savedField2 = new SavedField();
            object = savedField2;
        } else {
            object = savedField;
        }
        this.iSavedFieldsCount = n + 1;
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static void sort(SavedField[] var0, int var1_1) {
        var2_2 = 0;
        if (var1_1 <= 10) ** GOTO lbl6
        Arrays.sort(var0, 0, var1_1);
        return;
        {
            ++var2_2;
lbl6:
            // 2 sources
            if (var2_2 >= var1_1) return;
            var3_3 = var2_2;
            do {
                if (var3_3 <= 0 || var0[var3_3 - 1].compareTo(var0[var3_3]) <= 0) continue block0;
                var4_4 = var0[var3_3];
                var0[var3_3] = var0[var3_3 - 1];
                var0[var3_3 - 1] = var4_4;
                --var3_3;
            } while (true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long computeMillis(boolean var1_1, CharSequence var2_2) {
        block9: {
            var9_3 = this.iSavedFields;
            var4_10 = this.iSavedFieldsCount;
            if (this.iSavedFieldsShared) {
                var9_4 = (SavedField[])this.iSavedFields.clone();
                this.iSavedFields = var9_4;
                this.iSavedFieldsShared = false;
            }
            DateTimeParserBucket.sort((SavedField[])var9_5, var4_10);
            if (var4_10 > 0) {
                var10_11 = DurationFieldType.months().getField(this.iChrono);
                var11_12 = DurationFieldType.days().getField(this.iChrono);
                var12_13 = var9_5[0].iField.getDurationField();
                if (DateTimeParserBucket.compareReverse(var12_13, (DurationField)var10_11) >= 0 && DateTimeParserBucket.compareReverse(var12_13, var11_12) <= 0) {
                    this.saveField(DateTimeFieldType.year(), this.iDefaultYear);
                    return this.computeMillis(var1_1, var2_2);
                }
            }
            var5_14 = this.iMillis;
            for (var3_15 = 0; var3_15 < var4_10; ++var3_15) {
                try {
                    var5_14 = var9_5[var3_15].set(var5_14, var1_1);
                    continue;
                }
                catch (IllegalFieldValueException var9_6) {
                    if (var2_2 == null) throw var9_6;
                    var9_6.prependMessage("Cannot parse \"" + var2_2 + '\"');
                    throw var9_6;
                }
            }
            var7_16 = var5_14;
            if (!var1_1) break block9;
            var3_15 = 0;
            ** GOTO lbl30
lbl-1000:
            // 2 sources
            {
                var5_14 = var10_11.set(var5_14, var1_1);
                ++var3_15;
lbl30:
                // 2 sources
                var7_16 = var5_14;
                if (var3_15 >= var4_10) break;
                var10_11 = var9_5[var3_15];
                if (var3_15 == var4_10 - 1) {
                    var1_1 = true;
                    continue;
                }
                var1_1 = false;
                ** while (true)
            }
        }
        if (this.iOffset != null) {
            return var7_16 - (long)this.iOffset.intValue();
        }
        var5_14 = var7_16;
        if (this.iZone == null) return var5_14;
        var3_15 = this.iZone.getOffsetFromLocal(var7_16);
        var5_14 = var7_16 -= (long)var3_15;
        if (var3_15 == this.iZone.getOffset(var7_16)) return var5_14;
        var9_7 = var10_11 = "Illegal instant due to time zone offset transition (" + this.iZone + ')';
        if (var2_2 == null) throw new IllegalInstantException((String)var9_9);
        var9_8 = "Cannot parse \"" + var2_2 + "\": " + (String)var10_11;
        throw new IllegalInstantException((String)var9_9);
    }

    public long computeMillis(boolean bl, String string2) {
        return this.computeMillis(bl, (CharSequence)string2);
    }

    long doParseMillis(InternalParser internalParser, CharSequence charSequence) {
        int n;
        int n2 = internalParser.parseInto(this, charSequence, 0);
        if (n2 >= 0) {
            n = n2;
            if (n2 >= charSequence.length()) {
                return this.computeMillis(true, charSequence);
            }
        } else {
            n = ~n2;
        }
        throw new IllegalArgumentException(FormatUtils.createErrorMessage(((Object)charSequence).toString(), n));
    }

    public Chronology getChronology() {
        return this.iChrono;
    }

    public Locale getLocale() {
        return this.iLocale;
    }

    public Integer getOffsetInteger() {
        return this.iOffset;
    }

    public Integer getPivotYear() {
        return this.iPivotYear;
    }

    public DateTimeZone getZone() {
        return this.iZone;
    }

    public boolean restoreState(Object object) {
        if (object instanceof SavedState && ((SavedState)object).restoreState(this)) {
            this.iSavedState = object;
            return true;
        }
        return false;
    }

    public void saveField(DateTimeField dateTimeField, int n) {
        this.obtainSaveField().init(dateTimeField, n);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, int n) {
        this.obtainSaveField().init(dateTimeFieldType.getField(this.iChrono), n);
    }

    public void saveField(DateTimeFieldType dateTimeFieldType, String string2, Locale locale) {
        this.obtainSaveField().init(dateTimeFieldType.getField(this.iChrono), string2, locale);
    }

    public Object saveState() {
        if (this.iSavedState == null) {
            this.iSavedState = new SavedState();
        }
        return this.iSavedState;
    }

    public void setOffset(Integer n) {
        this.iSavedState = null;
        this.iOffset = n;
    }

    public void setZone(DateTimeZone dateTimeZone) {
        this.iSavedState = null;
        this.iZone = dateTimeZone;
    }

    static class SavedField
    implements Comparable<SavedField> {
        DateTimeField iField;
        Locale iLocale;
        String iText;
        int iValue;

        SavedField() {
        }

        @Override
        public int compareTo(SavedField object) {
            object = ((SavedField)object).iField;
            int n = DateTimeParserBucket.compareReverse(this.iField.getRangeDurationField(), ((DateTimeField)object).getRangeDurationField());
            if (n != 0) {
                return n;
            }
            return DateTimeParserBucket.compareReverse(this.iField.getDurationField(), ((DateTimeField)object).getDurationField());
        }

        void init(DateTimeField dateTimeField, int n) {
            this.iField = dateTimeField;
            this.iValue = n;
            this.iText = null;
            this.iLocale = null;
        }

        void init(DateTimeField dateTimeField, String string2, Locale locale) {
            this.iField = dateTimeField;
            this.iValue = 0;
            this.iText = string2;
            this.iLocale = locale;
        }

        /*
         * Enabled aggressive block sorting
         */
        long set(long l, boolean bl) {
            l = this.iText == null ? this.iField.setExtended(l, this.iValue) : this.iField.set(l, this.iText, this.iLocale);
            long l2 = l;
            if (!bl) return l2;
            return this.iField.roundFloor(l);
        }
    }

    class SavedState {
        final Integer iOffset;
        final SavedField[] iSavedFields;
        final int iSavedFieldsCount;
        final DateTimeZone iZone;

        SavedState() {
            this.iZone = DateTimeParserBucket.this.iZone;
            this.iOffset = DateTimeParserBucket.this.iOffset;
            this.iSavedFields = DateTimeParserBucket.this.iSavedFields;
            this.iSavedFieldsCount = DateTimeParserBucket.this.iSavedFieldsCount;
        }

        boolean restoreState(DateTimeParserBucket dateTimeParserBucket) {
            if (dateTimeParserBucket != DateTimeParserBucket.this) {
                return false;
            }
            dateTimeParserBucket.iZone = this.iZone;
            dateTimeParserBucket.iOffset = this.iOffset;
            dateTimeParserBucket.iSavedFields = this.iSavedFields;
            if (this.iSavedFieldsCount < dateTimeParserBucket.iSavedFieldsCount) {
                dateTimeParserBucket.iSavedFieldsShared = true;
            }
            dateTimeParserBucket.iSavedFieldsCount = this.iSavedFieldsCount;
            return true;
        }
    }

}

