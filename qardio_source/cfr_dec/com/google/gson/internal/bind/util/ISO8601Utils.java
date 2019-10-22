/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

public class ISO8601Utils {
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone("UTC");

    private static boolean checkOffset(String string2, int n, char c) {
        return n < string2.length() && string2.charAt(n) == c;
    }

    private static int indexOfNonDigit(String string2, int n) {
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (c < '0' || c > '9') {
                return n;
            }
            ++n;
        }
        return string2.length();
    }

    /*
     * Exception decompiling
     */
    public static Date parse(String var0, ParsePosition var1_1) throws ParseException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int parseInt(String string2, int n, int n2) throws NumberFormatException {
        int n3;
        if (n < 0 || n2 > string2.length() || n > n2) {
            throw new NumberFormatException(string2);
        }
        int n4 = 0;
        if (n < n2) {
            n3 = n + 1;
            n4 = Character.digit(string2.charAt(n), 10);
            if (n4 < 0) {
                throw new NumberFormatException("Invalid number: " + string2.substring(n, n2));
            }
            n4 = -n4;
        } else {
            n3 = n;
        }
        while (n3 < n2) {
            int n5 = Character.digit(string2.charAt(n3), 10);
            if (n5 < 0) {
                throw new NumberFormatException("Invalid number: " + string2.substring(n, n2));
            }
            n4 = n4 * 10 - n5;
            ++n3;
        }
        return -n4;
    }
}

