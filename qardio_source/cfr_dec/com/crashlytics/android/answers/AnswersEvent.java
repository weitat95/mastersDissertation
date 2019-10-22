/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.AnswersAttributes;
import com.crashlytics.android.answers.AnswersEventValidator;
import io.fabric.sdk.android.Fabric;
import java.util.Map;

public abstract class AnswersEvent<T extends AnswersEvent> {
    final AnswersAttributes customAttributes;
    final AnswersEventValidator validator = new AnswersEventValidator(20, 100, Fabric.isDebuggable());

    public AnswersEvent() {
        this.customAttributes = new AnswersAttributes(this.validator);
    }

    Map<String, Object> getCustomAttributes() {
        return this.customAttributes.attributes;
    }

    public T putCustomAttribute(String string2, Number number) {
        this.customAttributes.put(string2, number);
        return (T)this;
    }

    public T putCustomAttribute(String string2, String string3) {
        this.customAttributes.put(string2, string3);
        return (T)this;
    }
}

