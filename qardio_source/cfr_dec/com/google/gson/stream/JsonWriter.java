/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.stream;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter
implements Closeable,
Flushable {
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS;
    private static final String[] REPLACEMENT_CHARS;
    private String deferredName;
    private boolean htmlSafe;
    private String indent;
    private boolean lenient;
    private final Writer out;
    private String separator;
    private boolean serializeNulls;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static {
        REPLACEMENT_CHARS = new String[128];
        for (int i = 0; i <= 31; ++i) {
            JsonWriter.REPLACEMENT_CHARS[i] = String.format("\\u%04x", i);
        }
        JsonWriter.REPLACEMENT_CHARS[34] = "\\\"";
        JsonWriter.REPLACEMENT_CHARS[92] = "\\\\";
        JsonWriter.REPLACEMENT_CHARS[9] = "\\t";
        JsonWriter.REPLACEMENT_CHARS[8] = "\\b";
        JsonWriter.REPLACEMENT_CHARS[10] = "\\n";
        JsonWriter.REPLACEMENT_CHARS[13] = "\\r";
        JsonWriter.REPLACEMENT_CHARS[12] = "\\f";
        HTML_SAFE_REPLACEMENT_CHARS = (String[])REPLACEMENT_CHARS.clone();
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        JsonWriter.HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }

    public JsonWriter(Writer writer) {
        this.push(6);
        this.separator = ":";
        this.serializeNulls = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.out = writer;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void beforeName() throws IOException {
        int n = this.peek();
        if (n == 5) {
            this.out.write(44);
        } else if (n != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        this.newline();
        this.replaceTop(4);
    }

    private void beforeValue() throws IOException {
        switch (this.peek()) {
            default: {
                throw new IllegalStateException("Nesting problem.");
            }
            case 7: {
                if (!this.lenient) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            case 6: {
                this.replaceTop(7);
                return;
            }
            case 1: {
                this.replaceTop(2);
                this.newline();
                return;
            }
            case 2: {
                this.out.append(',');
                this.newline();
                return;
            }
            case 4: 
        }
        this.out.append(this.separator);
        this.replaceTop(5);
    }

    private JsonWriter close(int n, int n2, String string2) throws IOException {
        int n3 = this.peek();
        if (n3 != n2 && n3 != n) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        --this.stackSize;
        if (n3 == n2) {
            this.newline();
        }
        this.out.write(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void newline() throws IOException {
        if (this.indent != null) {
            this.out.write("\n");
            int n = this.stackSize;
            for (int i = 1; i < n; ++i) {
                this.out.write(this.indent);
            }
        }
    }

    private JsonWriter open(int n, String string2) throws IOException {
        this.beforeValue();
        this.push(n);
        this.out.write(string2);
        return this;
    }

    private int peek() {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        return this.stack[this.stackSize - 1];
    }

    private void push(int n) {
        int[] arrn;
        if (this.stackSize == this.stack.length) {
            arrn = new int[this.stackSize * 2];
            System.arraycopy(this.stack, 0, arrn, 0, this.stackSize);
            this.stack = arrn;
        }
        arrn = this.stack;
        int n2 = this.stackSize;
        this.stackSize = n2 + 1;
        arrn[n2] = n;
    }

    private void replaceTop(int n) {
        this.stack[this.stackSize - 1] = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void string(String string2) throws IOException {
        String[] arrstring = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
        this.out.write("\"");
        int n = 0;
        int n2 = string2.length();
        for (int i = 0; i < n2; ++i) {
            int n3;
            block8: {
                String string3;
                block7: {
                    char c;
                    block9: {
                        block6: {
                            String string4;
                            c = string2.charAt(i);
                            if (c >= '\u0080') break block6;
                            string3 = string4 = arrstring[c];
                            if (string4 != null) break block7;
                            n3 = n;
                            break block8;
                        }
                        if (c != '\u2028') break block9;
                        string3 = "\\u2028";
                        break block7;
                    }
                    n3 = n;
                    if (c != '\u2029') break block8;
                    string3 = "\\u2029";
                }
                if (n < i) {
                    this.out.write(string2, n, i - n);
                }
                this.out.write(string3);
                n3 = i + 1;
            }
            n = n3;
        }
        if (n < n2) {
            this.out.write(string2, n, n2 - n);
        }
        this.out.write("\"");
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            this.beforeName();
            this.string(this.deferredName);
            this.deferredName = null;
        }
    }

    public JsonWriter beginArray() throws IOException {
        this.writeDeferredName();
        return this.open(1, "[");
    }

    public JsonWriter beginObject() throws IOException {
        this.writeDeferredName();
        return this.open(3, "{");
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        int n = this.stackSize;
        if (n > 1 || n == 1 && this.stack[n - 1] != 7) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }

    public JsonWriter endArray() throws IOException {
        return this.close(1, 2, "]");
    }

    public JsonWriter endObject() throws IOException {
        return this.close(3, 5, "}");
    }

    @Override
    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }

    public final boolean isHtmlSafe() {
        return this.htmlSafe;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public JsonWriter name(String string2) throws IOException {
        if (string2 == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.deferredName = string2;
        return this;
    }

    public JsonWriter nullValue() throws IOException {
        block3: {
            block2: {
                if (this.deferredName == null) break block2;
                if (!this.serializeNulls) break block3;
                this.writeDeferredName();
            }
            this.beforeValue();
            this.out.write("null");
            return this;
        }
        this.deferredName = null;
        return this;
    }

    public final void setHtmlSafe(boolean bl) {
        this.htmlSafe = bl;
    }

    public final void setIndent(String string2) {
        if (string2.length() == 0) {
            this.indent = null;
            this.separator = ":";
            return;
        }
        this.indent = string2;
        this.separator = ": ";
    }

    public final void setLenient(boolean bl) {
        this.lenient = bl;
    }

    public final void setSerializeNulls(boolean bl) {
        this.serializeNulls = bl;
    }

    public JsonWriter value(long l) throws IOException {
        this.writeDeferredName();
        this.beforeValue();
        this.out.write(Long.toString(l));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriter value(Boolean object) throws IOException {
        if (object == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue();
        Writer writer = this.out;
        object = ((Boolean)object).booleanValue() ? "true" : "false";
        writer.write((String)object);
        return this;
    }

    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        String string2 = number.toString();
        if (!this.lenient && (string2.equals("-Infinity") || string2.equals("Infinity") || string2.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        this.beforeValue();
        this.out.append(string2);
        return this;
    }

    public JsonWriter value(String string2) throws IOException {
        if (string2 == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue();
        this.string(string2);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonWriter value(boolean bl) throws IOException {
        this.writeDeferredName();
        this.beforeValue();
        Writer writer = this.out;
        String string2 = bl ? "true" : "false";
        writer.write(string2);
        return this;
    }
}

