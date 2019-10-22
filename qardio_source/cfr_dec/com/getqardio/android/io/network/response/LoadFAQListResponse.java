/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.Faq;
import java.util.ArrayList;
import java.util.List;

public class LoadFAQListResponse {
    private int count = 0;
    private List<Faq> questions = new ArrayList<Faq>();

    public void addQuestion(Faq faq) {
        this.questions.add(faq);
    }

    public int getCount() {
        return this.count;
    }

    public List<Faq> getQuestions() {
        return this.questions;
    }

    public void setCount(int n) {
        this.count = n;
    }
}

