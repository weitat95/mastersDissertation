/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time.field;

import java.io.Serializable;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.field.FieldUtils;

public abstract class AbstractReadableInstantFieldProperty
implements Serializable {
    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof AbstractReadableInstantFieldProperty)) {
                    return false;
                }
                object = (AbstractReadableInstantFieldProperty)object;
                if (this.get() != ((AbstractReadableInstantFieldProperty)object).get() || !this.getFieldType().equals(((AbstractReadableInstantFieldProperty)object).getFieldType()) || !FieldUtils.equals(this.getChronology(), ((AbstractReadableInstantFieldProperty)object).getChronology())) break block5;
            }
            return true;
        }
        return false;
    }

    public int get() {
        return this.getField().get(this.getMillis());
    }

    public String getAsShortText(Locale locale) {
        return this.getField().getAsShortText(this.getMillis(), locale);
    }

    public String getAsText(Locale locale) {
        return this.getField().getAsText(this.getMillis(), locale);
    }

    protected Chronology getChronology() {
        throw new UnsupportedOperationException("The method getChronology() was added in v1.4 and needs to be implemented by subclasses of AbstractReadableInstantFieldProperty");
    }

    public abstract DateTimeField getField();

    public DateTimeFieldType getFieldType() {
        return this.getField().getType();
    }

    public int getMaximumTextLength(Locale locale) {
        return this.getField().getMaximumTextLength(locale);
    }

    public int getMaximumValueOverall() {
        return this.getField().getMaximumValue();
    }

    protected abstract long getMillis();

    public int getMinimumValueOverall() {
        return this.getField().getMinimumValue();
    }

    public String getName() {
        return this.getField().getName();
    }

    public int hashCode() {
        return this.get() * 17 + this.getFieldType().hashCode() + this.getChronology().hashCode();
    }

    public String toString() {
        return "Property[" + this.getName() + "]";
    }
}

