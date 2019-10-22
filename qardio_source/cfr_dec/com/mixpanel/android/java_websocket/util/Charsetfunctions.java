/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.java_websocket.util;

import com.mixpanel.android.java_websocket.exceptions.InvalidDataException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class Charsetfunctions {
    public static CodingErrorAction codingErrorAction = CodingErrorAction.REPORT;

    public static byte[] asciiBytes(String arrby) {
        try {
            arrby = arrby.getBytes("ASCII");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String stringAscii(byte[] object, int n, int n2) {
        try {
            object = new String((byte[])object, n, n2, "ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static String stringUtf8(ByteBuffer byteBuffer) throws InvalidDataException {
        Object object = Charset.forName("UTF8").newDecoder();
        ((CharsetDecoder)object).onMalformedInput(codingErrorAction);
        ((CharsetDecoder)object).onUnmappableCharacter(codingErrorAction);
        try {
            byteBuffer.mark();
            object = ((CharsetDecoder)object).decode(byteBuffer).toString();
            byteBuffer.reset();
            return object;
        }
        catch (CharacterCodingException characterCodingException) {
            throw new InvalidDataException(1007, characterCodingException);
        }
    }

    public static byte[] utf8Bytes(String arrby) {
        try {
            arrby = arrby.getBytes("UTF8");
            return arrby;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }
}

