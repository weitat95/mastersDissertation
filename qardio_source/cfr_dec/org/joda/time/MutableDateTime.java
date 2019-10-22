/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadWritableDateTime;
import org.joda.time.base.BaseDateTime;
import org.joda.time.field.AbstractReadableInstantFieldProperty;

public class MutableDateTime
extends BaseDateTime
implements Serializable,
Cloneable,
ReadWritableDateTime {
    private DateTimeField iRoundingField;
    private int iRoundingMode;

    public MutableDateTime() {
    }

    public MutableDateTime(long l, DateTimeZone dateTimeZone) {
        super(l, dateTimeZone);
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError("Clone error");
        }
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        DateTimeField dateTimeField = dateTimeFieldType.getField(this.getChronology());
        if (!dateTimeField.isSupported()) {
            throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
        }
        return new Property(this, dateTimeField);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setMillis(long l) {
        long l2 = l;
        switch (this.iRoundingMode) {
            default: {
                l2 = l;
                break;
            }
            case 1: {
                l2 = this.iRoundingField.roundFloor(l);
                break;
            }
            case 2: {
                l2 = this.iRoundingField.roundCeiling(l);
                break;
            }
            case 3: {
                l2 = this.iRoundingField.roundHalfFloor(l);
                break;
            }
            case 4: {
                l2 = this.iRoundingField.roundHalfCeiling(l);
            }
            case 0: {
                break;
            }
            case 5: {
                l2 = this.iRoundingField.roundHalfEven(l);
            }
        }
        super.setMillis(l2);
    }

    public static final class Property
    extends AbstractReadableInstantFieldProperty {
        private DateTimeField iField;
        private MutableDateTime iInstant;

        Property(MutableDateTime mutableDateTime, DateTimeField dateTimeField) {
            this.iInstant = mutableDateTime;
            this.iField = dateTimeField;
        }

        @Override
        protected Chronology getChronology() {
            return this.iInstant.getChronology();
        }

        @Override
        public DateTimeField getField() {
            return this.iField;
        }

        @Override
        protected long getMillis() {
            return this.iInstant.getMillis();
        }

        public MutableDateTime set(int n) {
            this.iInstant.setMillis(this.getField().set(this.iInstant.getMillis(), n));
            return this.iInstant;
        }
    }

}

