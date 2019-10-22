/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JsonElement parse(JsonReader jsonReader) throws JsonIOException, JsonSyntaxException {
        boolean bl = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            JsonElement jsonElement = Streams.parse(jsonReader);
            return jsonElement;
        }
        catch (StackOverflowError stackOverflowError) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", stackOverflowError);
        }
        catch (OutOfMemoryError outOfMemoryError) {
            throw new JsonParseException("Failed parsing JSON source: " + jsonReader + " to Json", outOfMemoryError);
        }
        finally {
            jsonReader.setLenient(bl);
        }
    }

    public JsonElement parse(Reader closeable) throws JsonIOException, JsonSyntaxException {
        JsonElement jsonElement;
        try {
            closeable = new JsonReader((Reader)closeable);
            jsonElement = this.parse((JsonReader)closeable);
            if (!jsonElement.isJsonNull() && ((JsonReader)closeable).peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
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
        return jsonElement;
    }

    public JsonElement parse(String string2) throws JsonSyntaxException {
        return this.parse(new StringReader(string2));
    }
}

