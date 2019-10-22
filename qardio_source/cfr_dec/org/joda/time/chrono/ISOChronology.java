/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.chrono;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.chrono.ISOYearOfEraDateTimeField;
import org.joda.time.chrono.ZonedChronology;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.RemainderDateTimeField;

public final class ISOChronology
extends AssembledChronology {
    private static final ISOChronology INSTANCE_UTC;
    private static final ConcurrentHashMap<DateTimeZone, ISOChronology> cCache;

    static {
        cCache = new ConcurrentHashMap();
        INSTANCE_UTC = new ISOChronology(GregorianChronology.getInstanceUTC());
        cCache.put(DateTimeZone.UTC, INSTANCE_UTC);
    }

    private ISOChronology(Chronology chronology) {
        super(chronology, null);
    }

    public static ISOChronology getInstance() {
        return ISOChronology.getInstance(DateTimeZone.getDefault());
    }

    public static ISOChronology getInstance(DateTimeZone serializable) {
        DateTimeZone dateTimeZone = serializable;
        if (serializable == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        ISOChronology iSOChronology = cCache.get(dateTimeZone);
        serializable = iSOChronology;
        if (iSOChronology != null || (serializable = cCache.putIfAbsent(dateTimeZone, iSOChronology = new ISOChronology(ZonedChronology.getInstance(INSTANCE_UTC, dateTimeZone)))) != null) {
            return serializable;
        }
        return iSOChronology;
    }

    public static ISOChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    @Override
    protected void assemble(AssembledChronology.Fields fields) {
        if (this.getBase().getZone() == DateTimeZone.UTC) {
            fields.centuryOfEra = new DividedDateTimeField(ISOYearOfEraDateTimeField.INSTANCE, DateTimeFieldType.centuryOfEra(), 100);
            fields.centuries = fields.centuryOfEra.getDurationField();
            fields.yearOfCentury = new RemainderDateTimeField((DividedDateTimeField)fields.centuryOfEra, DateTimeFieldType.yearOfCentury());
            fields.weekyearOfCentury = new RemainderDateTimeField((DividedDateTimeField)fields.centuryOfEra, fields.weekyears, DateTimeFieldType.weekyearOfCentury());
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ISOChronology) {
            object = (ISOChronology)object;
            return this.getZone().equals(((AssembledChronology)object).getZone());
        }
        return false;
    }

    public int hashCode() {
        return "ISO".hashCode() * 11 + this.getZone().hashCode();
    }

    public String toString() {
        String string2 = "ISOChronology";
        DateTimeZone dateTimeZone = this.getZone();
        if (dateTimeZone != null) {
            string2 = "ISOChronology" + '[' + dateTimeZone.getID() + ']';
        }
        return string2;
    }

    @Override
    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    @Override
    public Chronology withZone(DateTimeZone dateTimeZone) {
        DateTimeZone dateTimeZone2 = dateTimeZone;
        if (dateTimeZone == null) {
            dateTimeZone2 = DateTimeZone.getDefault();
        }
        if (dateTimeZone2 == this.getZone()) {
            return this;
        }
        return ISOChronology.getInstance(dateTimeZone2);
    }
}

