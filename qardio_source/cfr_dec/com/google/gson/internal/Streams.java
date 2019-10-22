/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.EOFException;
import java.io.IOException;
import java.io.Writer;

public final class Streams {
    public static JsonElement parse(JsonReader object) throws JsonParseException {
        boolean bl = true;
        ((JsonReader)object).peek();
        bl = false;
        try {
            object = TypeAdapters.JSON_ELEMENT.read((JsonReader)object);
            return object;
        }
        catch (EOFException eOFException) {
            if (bl) {
                return JsonNull.INSTANCE;
            }
            throw new JsonSyntaxException(eOFException);
        }
        catch (MalformedJsonException malformedJsonException) {
            throw new JsonSyntaxException(malformedJsonException);
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
        catch (NumberFormatException numberFormatException) {
            throw new JsonSyntaxException(numberFormatException);
        }
    }

    public static void write(JsonElement jsonElement, JsonWriter jsonWriter) throws IOException {
        TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonElement);
    }

    public static Writer writerForAppendable(Appendable appendable) {
        if (appendable instanceof Writer) {
            return (Writer)appendable;
        }
        return new AppendableWriter(appendable);
    }

    private static final class AppendableWriter
    extends Writer {
        private final Appendable appendable;
        private final CurrentWrite currentWrite = new CurrentWrite();

        AppendableWriter(Appendable appendable) {
            this.appendable = appendable;
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void write(int n) throws IOException {
            this.appendable.append((char)n);
        }

        @Override
        public void write(char[] arrc, int n, int n2) throws IOException {
            this.currentWrite.chars = arrc;
            this.appendable.append(this.currentWrite, n, n + n2);
        }

        static class CurrentWrite
        implements CharSequence {
            char[] chars;

            CurrentWrite() {
            }

            @Override
            public char charAt(int n) {
                return this.chars[n];
            }

            @Override
            public int length() {
                return this.chars.length;
            }

            @Override
            public CharSequence subSequence(int n, int n2) {
                return new String(this.chars, n, n2 - n);
            }
        }

    }

}

