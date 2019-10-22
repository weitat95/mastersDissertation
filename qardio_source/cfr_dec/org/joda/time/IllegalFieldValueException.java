/*
 * Decompiled with CFR 0.147.
 */
package org.joda.time;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationFieldType;

public class IllegalFieldValueException
extends IllegalArgumentException {
    private final DateTimeFieldType iDateTimeFieldType;
    private final DurationFieldType iDurationFieldType;
    private final String iFieldName;
    private final Number iLowerBound;
    private String iMessage;
    private final Number iNumberValue;
    private final String iStringValue;
    private final Number iUpperBound;

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, Number number, Number number2, Number number3) {
        super(IllegalFieldValueException.createMessage(dateTimeFieldType.getName(), number, number2, number3, null));
        this.iDateTimeFieldType = dateTimeFieldType;
        this.iDurationFieldType = null;
        this.iFieldName = dateTimeFieldType.getName();
        this.iNumberValue = number;
        this.iStringValue = null;
        this.iLowerBound = number2;
        this.iUpperBound = number3;
        this.iMessage = super.getMessage();
    }

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, Number number, String string2) {
        super(IllegalFieldValueException.createMessage(dateTimeFieldType.getName(), number, null, null, string2));
        this.iDateTimeFieldType = dateTimeFieldType;
        this.iDurationFieldType = null;
        this.iFieldName = dateTimeFieldType.getName();
        this.iNumberValue = number;
        this.iStringValue = null;
        this.iLowerBound = null;
        this.iUpperBound = null;
        this.iMessage = super.getMessage();
    }

    public IllegalFieldValueException(DateTimeFieldType dateTimeFieldType, String string2) {
        super(IllegalFieldValueException.createMessage(dateTimeFieldType.getName(), string2));
        this.iDateTimeFieldType = dateTimeFieldType;
        this.iDurationFieldType = null;
        this.iFieldName = dateTimeFieldType.getName();
        this.iStringValue = string2;
        this.iNumberValue = null;
        this.iLowerBound = null;
        this.iUpperBound = null;
        this.iMessage = super.getMessage();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String createMessage(String charSequence, Number number, Number number2, Number number3, String string2) {
        charSequence = new StringBuilder().append("Value ").append(number).append(" for ").append((String)charSequence).append(' ');
        if (number2 == null) {
            if (number3 == null) {
                ((StringBuilder)charSequence).append("is not supported");
            } else {
                ((StringBuilder)charSequence).append("must not be larger than ").append(number3);
            }
        } else if (number3 == null) {
            ((StringBuilder)charSequence).append("must not be smaller than ").append(number2);
        } else {
            ((StringBuilder)charSequence).append("must be in the range [").append(number2).append(',').append(number3).append(']');
        }
        if (string2 != null) {
            ((StringBuilder)charSequence).append(": ").append(string2);
        }
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String createMessage(String string2, String string3) {
        StringBuffer stringBuffer = new StringBuffer().append("Value ");
        if (string3 == null) {
            stringBuffer.append("null");
        } else {
            stringBuffer.append('\"');
            stringBuffer.append(string3);
            stringBuffer.append('\"');
        }
        stringBuffer.append(" for ").append(string2).append(' ').append("is not supported");
        return stringBuffer.toString();
    }

    @Override
    public String getMessage() {
        return this.iMessage;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void prependMessage(String string2) {
        if (this.iMessage == null) {
            this.iMessage = string2;
            return;
        } else {
            if (string2 == null) return;
            {
                this.iMessage = string2 + ": " + this.iMessage;
                return;
            }
        }
    }
}

