/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class JsonTreeReader
extends JsonReader {
    private static final Object SENTINEL_CLOSED;
    private static final Reader UNREADABLE_READER;
    private int[] pathIndices;
    private String[] pathNames;
    private Object[] stack = new Object[32];
    private int stackSize = 0;

    static {
        UNREADABLE_READER = new Reader(){

            @Override
            public void close() throws IOException {
                throw new AssertionError();
            }

            @Override
            public int read(char[] arrc, int n, int n2) throws IOException {
                throw new AssertionError();
            }
        };
        SENTINEL_CLOSED = new Object();
    }

    public JsonTreeReader(JsonElement jsonElement) {
        super(UNREADABLE_READER);
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        this.push(jsonElement);
    }

    private void expect(JsonToken jsonToken) throws IOException {
        if (this.peek() != jsonToken) {
            throw new IllegalStateException("Expected " + (Object)((Object)jsonToken) + " but was " + (Object)((Object)this.peek()) + this.locationString());
        }
    }

    private String locationString() {
        return " at path " + this.getPath();
    }

    private Object peekStack() {
        return this.stack[this.stackSize - 1];
    }

    private Object popStack() {
        int n;
        Object object = this.stack;
        this.stackSize = n = this.stackSize - 1;
        object = object[n];
        this.stack[this.stackSize] = null;
        return object;
    }

    private void push(Object object) {
        Object[] arrobject;
        if (this.stackSize == this.stack.length) {
            arrobject = new Object[this.stackSize * 2];
            int[] arrn = new int[this.stackSize * 2];
            String[] arrstring = new String[this.stackSize * 2];
            System.arraycopy(this.stack, 0, arrobject, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, arrn, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, arrstring, 0, this.stackSize);
            this.stack = arrobject;
            this.pathIndices = arrn;
            this.pathNames = arrstring;
        }
        arrobject = this.stack;
        int n = this.stackSize;
        this.stackSize = n + 1;
        arrobject[n] = object;
    }

    @Override
    public void beginArray() throws IOException {
        this.expect(JsonToken.BEGIN_ARRAY);
        this.push(((JsonArray)this.peekStack()).iterator());
        this.pathIndices[this.stackSize - 1] = 0;
    }

    @Override
    public void beginObject() throws IOException {
        this.expect(JsonToken.BEGIN_OBJECT);
        this.push(((JsonObject)this.peekStack()).entrySet().iterator());
    }

    @Override
    public void close() throws IOException {
        this.stack = new Object[]{SENTINEL_CLOSED};
        this.stackSize = 1;
    }

    @Override
    public void endArray() throws IOException {
        this.expect(JsonToken.END_ARRAY);
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            int[] arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
    }

    @Override
    public void endObject() throws IOException {
        this.expect(JsonToken.END_OBJECT);
        this.popStack();
        this.popStack();
        if (this.stackSize > 0) {
            int[] arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder().append('$');
        int n = 0;
        while (n < this.stackSize) {
            Object[] arrobject;
            int n2;
            if (this.stack[n] instanceof JsonArray) {
                arrobject = this.stack;
                n2 = ++n;
                if (arrobject[n] instanceof Iterator) {
                    stringBuilder.append('[').append(this.pathIndices[n]).append(']');
                    n2 = n;
                }
            } else {
                n2 = n;
                if (this.stack[n] instanceof JsonObject) {
                    arrobject = this.stack;
                    n2 = ++n;
                    if (arrobject[n] instanceof Iterator) {
                        stringBuilder.append('.');
                        n2 = n;
                        if (this.pathNames[n] != null) {
                            stringBuilder.append(this.pathNames[n]);
                            n2 = n;
                        }
                    }
                }
            }
            n = n2 + 1;
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean hasNext() throws IOException {
        JsonToken jsonToken = this.peek();
        return jsonToken != JsonToken.END_OBJECT && jsonToken != JsonToken.END_ARRAY;
    }

    @Override
    public boolean nextBoolean() throws IOException {
        this.expect(JsonToken.BOOLEAN);
        boolean bl = ((JsonPrimitive)this.popStack()).getAsBoolean();
        if (this.stackSize > 0) {
            int[] arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
        return bl;
    }

    @Override
    public double nextDouble() throws IOException {
        int[] arrn = this.peek();
        if (arrn != JsonToken.NUMBER && arrn != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + arrn + this.locationString());
        }
        double d = ((JsonPrimitive)this.peekStack()).getAsDouble();
        if (!this.isLenient() && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + d);
        }
        this.popStack();
        if (this.stackSize > 0) {
            arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
        return d;
    }

    @Override
    public int nextInt() throws IOException {
        int[] arrn = this.peek();
        if (arrn != JsonToken.NUMBER && arrn != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + arrn + this.locationString());
        }
        int n = ((JsonPrimitive)this.peekStack()).getAsInt();
        this.popStack();
        if (this.stackSize > 0) {
            arrn = this.pathIndices;
            int n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
        }
        return n;
    }

    @Override
    public long nextLong() throws IOException {
        int[] arrn = this.peek();
        if (arrn != JsonToken.NUMBER && arrn != JsonToken.STRING) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.NUMBER) + " but was " + arrn + this.locationString());
        }
        long l = ((JsonPrimitive)this.peekStack()).getAsLong();
        this.popStack();
        if (this.stackSize > 0) {
            arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
        return l;
    }

    @Override
    public String nextName() throws IOException {
        String string2;
        this.expect(JsonToken.NAME);
        Map.Entry entry = (Map.Entry)((Iterator)this.peekStack()).next();
        this.pathNames[this.stackSize - 1] = string2 = (String)entry.getKey();
        this.push(entry.getValue());
        return string2;
    }

    @Override
    public void nextNull() throws IOException {
        this.expect(JsonToken.NULL);
        this.popStack();
        if (this.stackSize > 0) {
            int[] arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
    }

    @Override
    public String nextString() throws IOException {
        Object object = this.peek();
        if (object != JsonToken.STRING && object != JsonToken.NUMBER) {
            throw new IllegalStateException("Expected " + (Object)((Object)JsonToken.STRING) + " but was " + (Object)object + this.locationString());
        }
        object = ((JsonPrimitive)this.popStack()).getAsString();
        if (this.stackSize > 0) {
            int[] arrn = this.pathIndices;
            int n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
        }
        return object;
    }

    @Override
    public JsonToken peek() throws IOException {
        if (this.stackSize == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object object = this.peekStack();
        if (object instanceof Iterator) {
            boolean bl = this.stack[this.stackSize - 2] instanceof JsonObject;
            if ((object = (Iterator)object).hasNext()) {
                if (bl) {
                    return JsonToken.NAME;
                }
                this.push(object.next());
                return this.peek();
            }
            if (bl) {
                return JsonToken.END_OBJECT;
            }
            return JsonToken.END_ARRAY;
        }
        if (object instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        }
        if (object instanceof JsonArray) {
            return JsonToken.BEGIN_ARRAY;
        }
        if (object instanceof JsonPrimitive) {
            if (((JsonPrimitive)(object = (JsonPrimitive)object)).isString()) {
                return JsonToken.STRING;
            }
            if (((JsonPrimitive)object).isBoolean()) {
                return JsonToken.BOOLEAN;
            }
            if (((JsonPrimitive)object).isNumber()) {
                return JsonToken.NUMBER;
            }
            throw new AssertionError();
        }
        if (object instanceof JsonNull) {
            return JsonToken.NULL;
        }
        if (object == SENTINEL_CLOSED) {
            throw new IllegalStateException("JsonReader is closed");
        }
        throw new AssertionError();
    }

    public void promoteNameToValue() throws IOException {
        this.expect(JsonToken.NAME);
        Map.Entry entry = (Map.Entry)((Iterator)this.peekStack()).next();
        this.push(entry.getValue());
        this.push(new JsonPrimitive((String)entry.getKey()));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void skipValue() throws IOException {
        if (this.peek() == JsonToken.NAME) {
            this.nextName();
            this.pathNames[this.stackSize - 2] = "null";
        } else {
            this.popStack();
            this.pathNames[this.stackSize - 1] = "null";
        }
        int[] arrn = this.pathIndices;
        int n = this.stackSize - 1;
        arrn[n] = arrn[n] + 1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

