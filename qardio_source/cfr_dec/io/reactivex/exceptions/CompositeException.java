/*
 * Decompiled with CFR 0.147.
 */
package io.reactivex.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public final class CompositeException
extends RuntimeException {
    private Throwable cause;
    private final List<Throwable> exceptions;
    private final String message;

    public CompositeException(Iterable<? extends Throwable> object) {
        LinkedHashSet<Throwable> linkedHashSet = new LinkedHashSet<Throwable>();
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Throwable throwable = (Throwable)object.next();
                if (throwable instanceof CompositeException) {
                    linkedHashSet.addAll(((CompositeException)throwable).getExceptions());
                    continue;
                }
                if (throwable != null) {
                    linkedHashSet.add(throwable);
                    continue;
                }
                linkedHashSet.add(new NullPointerException("Throwable was null!"));
            }
        } else {
            linkedHashSet.add(new NullPointerException("errors was null"));
        }
        if (linkedHashSet.isEmpty()) {
            throw new IllegalArgumentException("errors is empty");
        }
        arrayList.addAll(linkedHashSet);
        this.exceptions = Collections.unmodifiableList(arrayList);
        this.message = this.exceptions.size() + " exceptions occurred. ";
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public CompositeException(Throwable ... object) {
        void var1_3;
        if (object == null) {
            List<NullPointerException> list = Collections.singletonList(new NullPointerException("exceptions was null"));
        } else {
            List<Throwable> list = Arrays.asList(object);
        }
        this((Iterable<? extends Throwable>)var1_3);
    }

    private void appendStackTrace(StringBuilder stringBuilder, Throwable throwable, String arrstackTraceElement) {
        stringBuilder.append((String)arrstackTraceElement).append(throwable).append('\n');
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            stringBuilder.append("\t\tat ").append(stackTraceElement).append('\n');
        }
        if (throwable.getCause() != null) {
            stringBuilder.append("\tCaused by: ");
            this.appendStackTrace(stringBuilder, throwable.getCause(), "");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private List<Throwable> getListOfCauses(Throwable var1_1) {
        var4_2 = new ArrayList<Throwable>();
        var3_3 = var1_1.getCause();
        if (var3_3 == null) return var4_2;
        var2_4 = var3_3;
        if (var3_3 != var1_1) ** GOTO lbl8
        return var4_2;
lbl-1000:
        // 1 sources
        {
            var2_4 = var1_1;
lbl8:
            // 2 sources
            var4_2.add(var2_4);
            var1_1 = var2_4.getCause();
            if (var1_1 == null) return var4_2;
            ** while (var1_1 != var2_4)
        }
lbl13:
        // 1 sources
        return var4_2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Throwable getRootCause(Throwable var1_1) {
        var3_2 = var1_1.getCause();
        if (var3_2 == null) return var1_1;
        var2_3 = var3_2;
        if (this.cause != var3_2) ** GOTO lbl7
        return var1_1;
lbl-1000:
        // 1 sources
        {
            var2_3 = var1_1;
lbl7:
            // 2 sources
            if ((var1_1 = var2_3.getCause()) == null) return var2_3;
            ** while (var1_1 != var2_3)
        }
lbl9:
        // 1 sources
        return var2_3;
    }

    private void printStackTrace(PrintStreamOrWriter printStreamOrWriter) {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(this).append('\n');
        for (StackTraceElement serializable : this.getStackTrace()) {
            stringBuilder.append("\tat ").append(serializable).append('\n');
        }
        int n = 1;
        for (Throwable throwable : this.exceptions) {
            stringBuilder.append("  ComposedException ").append(n).append(" :\n");
            this.appendStackTrace(stringBuilder, throwable, "\t");
            ++n;
        }
        printStreamOrWriter.println(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Throwable getCause() {
        synchronized (this) {
            if (this.cause != null) return this.cause;
            CompositeExceptionCausalChain compositeExceptionCausalChain = new CompositeExceptionCausalChain();
            HashSet<Throwable> hashSet = new HashSet<Throwable>();
            Throwable throwable = compositeExceptionCausalChain;
            Iterator<Throwable> iterator = this.exceptions.iterator();
            do {
                Iterator<Throwable> iterator2;
                Throwable throwable2;
                if (iterator.hasNext()) {
                    throwable2 = iterator.next();
                    if (hashSet.contains(throwable2)) continue;
                    hashSet.add(throwable2);
                    iterator2 = this.getListOfCauses(throwable2).iterator();
                } else {
                    this.cause = compositeExceptionCausalChain;
                    return this.cause;
                }
                while (iterator2.hasNext()) {
                    Throwable throwable3 = iterator2.next();
                    if (hashSet.contains(throwable3)) {
                        throwable2 = new RuntimeException("Duplicate found in causal chain so cropping to prevent loop ...");
                        continue;
                    }
                    hashSet.add(throwable3);
                }
                try {
                    throwable.initCause(throwable2);
                }
                catch (Throwable throwable4) {}
                throwable = this.getRootCause(throwable);
            } while (true);
        }
    }

    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void printStackTrace() {
        this.printStackTrace(System.err);
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        this.printStackTrace(new WrappedPrintStream(printStream));
    }

    @Override
    public void printStackTrace(PrintWriter printWriter) {
        this.printStackTrace(new WrappedPrintWriter(printWriter));
    }

    static final class CompositeExceptionCausalChain
    extends RuntimeException {
        CompositeExceptionCausalChain() {
        }

        @Override
        public String getMessage() {
            return "Chain of Causes for CompositeException In Order Received =>";
        }
    }

    static abstract class PrintStreamOrWriter {
        PrintStreamOrWriter() {
        }

        abstract void println(Object var1);
    }

    static final class WrappedPrintStream
    extends PrintStreamOrWriter {
        private final PrintStream printStream;

        WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        @Override
        void println(Object object) {
            this.printStream.println(object);
        }
    }

    static final class WrappedPrintWriter
    extends PrintStreamOrWriter {
        private final PrintWriter printWriter;

        WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        @Override
        void println(Object object) {
            this.printWriter.println(object);
        }
    }

}

