/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class JsonReader
implements Closeable {
    private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
    private final char[] buffer = new char[1024];
    private final Reader in;
    private boolean lenient = false;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    private int[] pathIndices;
    private String[] pathNames;
    int peeked = 0;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int pos = 0;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess(){

            @Override
            public void promoteNameToValue(JsonReader jsonReader) throws IOException {
                int n;
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader)jsonReader).promoteNameToValue();
                    return;
                }
                int n2 = n = jsonReader.peeked;
                if (n == 0) {
                    n2 = jsonReader.doPeek();
                }
                if (n2 == 13) {
                    jsonReader.peeked = 9;
                    return;
                }
                if (n2 == 12) {
                    jsonReader.peeked = 8;
                    return;
                }
                if (n2 == 14) {
                    jsonReader.peeked = 10;
                    return;
                }
                throw new IllegalStateException("Expected a name but was " + (Object)((Object)jsonReader.peek()) + jsonReader.locationString());
            }
        };
    }

    public JsonReader(Reader reader) {
        int[] arrn = this.stack;
        int n = this.stackSize;
        this.stackSize = n + 1;
        arrn[n] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw this.syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void consumeNonExecutePrefix() throws IOException {
        this.nextNonWhitespace(true);
        --this.pos;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit || this.fillBuffer(NON_EXECUTE_PREFIX.length)) {
            int n = 0;
            do {
                if (n >= NON_EXECUTE_PREFIX.length) {
                    this.pos += NON_EXECUTE_PREFIX.length;
                    return;
                }
                if (this.buffer[this.pos + n] != NON_EXECUTE_PREFIX[n]) break;
                ++n;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean fillBuffer(int n) throws IOException {
        int n2;
        boolean bl = false;
        char[] arrc = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(arrc, this.pos, arrc, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            n2 = this.in.read(arrc, this.limit, arrc.length - this.limit);
            boolean bl2 = bl;
            if (n2 == -1) return bl2;
            this.limit += n2;
            n2 = n;
            if (this.lineNumber == 0) {
                n2 = n;
                if (this.lineStart == 0) {
                    n2 = n;
                    if (this.limit > 0) {
                        n2 = n;
                        if (arrc[0] == '\ufeff') {
                            ++this.pos;
                            ++this.lineStart;
                            n2 = n + 1;
                        }
                    }
                }
            }
            n = n2;
        } while (this.limit < n2);
        return true;
    }

    private boolean isLiteral(char c) throws IOException {
        switch (c) {
            default: {
                return true;
            }
            case '#': 
            case '/': 
            case ';': 
            case '=': 
            case '\\': {
                this.checkLenient();
            }
            case '\t': 
            case '\n': 
            case '\f': 
            case '\r': 
            case ' ': 
            case ',': 
            case ':': 
            case '[': 
            case ']': 
            case '{': 
            case '}': 
        }
        return false;
    }

    private String locationString() {
        int n = this.lineNumber;
        int n2 = this.pos;
        int n3 = this.lineStart;
        return " at line " + (n + 1) + " column " + (n2 - n3 + 1) + " path " + this.getPath();
    }

    private int nextNonWhitespace(boolean bl) throws IOException {
        char[] arrc = this.buffer;
        int n = this.pos;
        int n2 = this.limit;
        block4: do {
            int n3;
            block17: {
                block16: {
                    int n4;
                    block14: {
                        block15: {
                            n3 = n2;
                            n4 = n;
                            if (n != n2) break block14;
                            this.pos = n;
                            if (this.fillBuffer(1)) break block15;
                            if (bl) {
                                throw new EOFException("End of input" + this.locationString());
                            }
                            break block16;
                        }
                        n4 = this.pos;
                        n3 = this.limit;
                    }
                    n = n4 + 1;
                    n2 = arrc[n4];
                    if (n2 == 10) {
                        ++this.lineNumber;
                        this.lineStart = n;
                        n2 = n3;
                        continue;
                    }
                    if (n2 != 32 && n2 != 13) {
                        if (n2 == 9) {
                            n2 = n3;
                            continue;
                        }
                        if (n2 == 47) {
                            this.pos = n;
                            if (n == n3) {
                                --this.pos;
                                boolean bl2 = this.fillBuffer(2);
                                ++this.pos;
                                if (!bl2) {
                                    return n2;
                                }
                            }
                            this.checkLenient();
                            switch (arrc[this.pos]) {
                                default: {
                                    return n2;
                                }
                                case '*': {
                                    ++this.pos;
                                    if (!this.skipTo("*/")) {
                                        throw this.syntaxError("Unterminated comment");
                                    }
                                    n = this.pos + 2;
                                    n2 = this.limit;
                                    continue block4;
                                }
                                case '/': 
                            }
                            ++this.pos;
                            this.skipToEndOfLine();
                            n = this.pos;
                            n2 = this.limit;
                            continue;
                        }
                        if (n2 == 35) {
                            this.pos = n;
                            this.checkLenient();
                            this.skipToEndOfLine();
                            n = this.pos;
                            n2 = this.limit;
                            continue;
                        }
                        this.pos = n;
                        return n2;
                    }
                    break block17;
                }
                return -1;
            }
            n2 = n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String nextQuotedValue(char c) throws IOException {
        char[] arrc = this.buffer;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            int n = this.pos;
            int n2 = this.limit;
            int n3 = n;
            while (n < n2) {
                int n4;
                int n5;
                int n6 = n + 1;
                char c2 = arrc[n];
                if (c2 == c) {
                    this.pos = n6;
                    stringBuilder.append(arrc, n3, n6 - n3 - 1);
                    return stringBuilder.toString();
                }
                if (c2 == '\\') {
                    this.pos = n6;
                    stringBuilder.append(arrc, n3, n6 - n3 - 1);
                    stringBuilder.append(this.readEscapeCharacter());
                    n = this.pos;
                    n4 = this.limit;
                    n5 = n;
                } else {
                    n4 = n2;
                    n = n6;
                    n5 = n3;
                    if (c2 == '\n') {
                        ++this.lineNumber;
                        this.lineStart = n6;
                        n4 = n2;
                        n = n6;
                        n5 = n3;
                    }
                }
                n2 = n4;
                n3 = n5;
            }
            stringBuilder.append(arrc, n3, n - n3);
            this.pos = n;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private String nextUnquotedValue() throws IOException {
        int n;
        StringBuilder stringBuilder;
        void var3_4;
        Object var3_1 = null;
        int n2 = 0;
        block4: do {
            void var3_2;
            if (this.pos + n2 < this.limit) {
                stringBuilder = var3_2;
                n = n2;
                switch (this.buffer[this.pos + n2]) {
                    default: {
                        ++n2;
                        continue block4;
                    }
                    case '#': 
                    case '/': 
                    case ';': 
                    case '=': 
                    case '\\': {
                        this.checkLenient();
                        n = n2;
                        stringBuilder = var3_2;
                        break;
                    }
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': 
                    case ',': 
                    case ':': 
                    case '[': 
                    case ']': 
                    case '{': 
                    case '}': {
                        break;
                    }
                }
                break;
            }
            if (n2 < this.buffer.length) {
                stringBuilder = var3_2;
                n = n2;
                if (!this.fillBuffer(n2 + 1)) break;
                continue;
            }
            stringBuilder = var3_2;
            if (var3_2 == null) {
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(this.buffer, this.pos, n2);
            this.pos += n2;
            n = 0;
            n2 = 0;
            StringBuilder stringBuilder2 = stringBuilder;
            if (!this.fillBuffer(1)) break;
        } while (true);
        if (stringBuilder == null) {
            String string2 = new String(this.buffer, this.pos, n);
        } else {
            stringBuilder.append(this.buffer, this.pos, n);
            String string3 = stringBuilder.toString();
        }
        this.pos += n;
        return var3_4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int peekKeyword() throws IOException {
        String string2;
        String string3;
        int n = this.buffer[this.pos];
        if (n == 116 || n == 84) {
            string2 = "true";
            string3 = "TRUE";
            n = 5;
        } else if (n == 102 || n == 70) {
            string2 = "false";
            string3 = "FALSE";
            n = 6;
        } else {
            if (n != 110 && n != 78) {
                return 0;
            }
            string2 = "null";
            string3 = "NULL";
            n = 7;
        }
        int n2 = string2.length();
        for (int i = 1; i < n2; ++i) {
            if (this.pos + i >= this.limit && !this.fillBuffer(i + 1)) {
                return 0;
            }
            char c = this.buffer[this.pos + i];
            if (c == string2.charAt(i) || c == string3.charAt(i)) continue;
            return 0;
        }
        if ((this.pos + n2 < this.limit || this.fillBuffer(n2 + 1)) && this.isLiteral(this.buffer[this.pos + n2])) {
            return 0;
        }
        this.pos += n2;
        this.peeked = n;
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int peekNumber() throws IOException {
        block17: {
            block18: {
                var15_1 = this.buffer;
                var2_2 = this.pos;
                var9_3 = this.limit;
                var11_4 = 0L;
                var3_5 = false;
                var5_6 = 1;
                var8_7 = 0;
                var4_8 = 0;
                block6: do {
                    block20: {
                        block24: {
                            block23: {
                                block22: {
                                    block21: {
                                        block19: {
                                            var7_11 = var9_3;
                                            var6_10 = var2_2;
                                            if (var2_2 + var4_8 != var9_3) break block19;
                                            if (var4_8 == var15_1.length) {
                                                return 0;
                                            }
                                            if (!this.fillBuffer(var4_8 + 1)) ** GOTO lbl25
                                            var6_10 = this.pos;
                                            var7_11 = this.limit;
                                        }
                                        var1_9 = var15_1[var6_10 + var4_8];
                                        switch (var1_9) {
                                            default: {
                                                if (var1_9 >= '0' && var1_9 <= '9') break;
                                                if (this.isLiteral(var1_9)) {
                                                    return 0;
                                                }
lbl25:
                                                // 3 sources
                                                if (var8_7 != 2 || var5_6 == 0 || var11_4 == Long.MIN_VALUE && !var3_5) break block17;
                                                if (!var3_5) break block6;
                                                break block18;
                                            }
                                            case '-': {
                                                if (var8_7 == 0) {
                                                    var10_12 = true;
                                                    var2_2 = 1;
                                                    var13_13 = var11_4;
                                                    var9_3 = var5_6;
                                                } else {
                                                    if (var8_7 != 5) return 0;
                                                    var2_2 = 6;
                                                    var9_3 = var5_6;
                                                    var10_12 = var3_5;
                                                    var13_13 = var11_4;
                                                }
                                                break block20;
                                            }
                                            case '+': {
                                                if (var8_7 != 5) return 0;
                                                var2_2 = 6;
                                                var9_3 = var5_6;
                                                var10_12 = var3_5;
                                                var13_13 = var11_4;
                                                break block20;
                                            }
                                            case 'E': 
                                            case 'e': {
                                                if (var8_7 != 2) {
                                                    if (var8_7 != 4) return 0;
                                                }
                                                var2_2 = 5;
                                                var9_3 = var5_6;
                                                var10_12 = var3_5;
                                                var13_13 = var11_4;
                                                break block20;
                                            }
                                            case '.': {
                                                if (var8_7 != 2) return 0;
                                                var2_2 = 3;
                                                var9_3 = var5_6;
                                                var10_12 = var3_5;
                                                var13_13 = var11_4;
                                                break block20;
                                            }
                                        }
                                        if (var8_7 != 1 && var8_7 != 0) break block21;
                                        var13_13 = -(var1_9 - 48);
                                        var2_2 = 2;
                                        var9_3 = var5_6;
                                        var10_12 = var3_5;
                                        break block20;
                                    }
                                    if (var8_7 != 2) break block22;
                                    if (var11_4 == 0L) {
                                        return 0;
                                    }
                                    var13_13 = 10L * var11_4 - (long)(var1_9 - 48);
                                    var2_2 = var11_4 > -922337203685477580L || var11_4 == -922337203685477580L && var13_13 < var11_4 ? 1 : 0;
                                    var9_3 = var5_6 & var2_2;
                                    var2_2 = var8_7;
                                    var10_12 = var3_5;
                                    break block20;
                                }
                                if (var8_7 != 3) break block23;
                                var2_2 = 4;
                                var9_3 = var5_6;
                                var10_12 = var3_5;
                                var13_13 = var11_4;
                                break block20;
                            }
                            if (var8_7 == 5) break block24;
                            var9_3 = var5_6;
                            var2_2 = var8_7;
                            var10_12 = var3_5;
                            var13_13 = var11_4;
                            if (var8_7 != 6) break block20;
                        }
                        var2_2 = 7;
                        var9_3 = var5_6;
                        var10_12 = var3_5;
                        var13_13 = var11_4;
                    }
                    ++var4_8;
                    var5_6 = var9_3;
                    var9_3 = var7_11;
                    var8_7 = var2_2;
                    var3_5 = var10_12;
                    var2_2 = var6_10;
                    var11_4 = var13_13;
                } while (true);
                var11_4 = -var11_4;
            }
            this.peekedLong = var11_4;
            this.pos += var4_8;
            this.peeked = 15;
            return 15;
        }
        if (var8_7 != 2 && var8_7 != 4) {
            if (var8_7 != 7) return 0;
        }
        this.peekedNumberLength = var4_8;
        this.peeked = 16;
        return 16;
    }

    private void push(int n) {
        int[] arrn;
        if (this.stackSize == this.stack.length) {
            arrn = new int[this.stackSize * 2];
            int[] arrn2 = new int[this.stackSize * 2];
            String[] arrstring = new String[this.stackSize * 2];
            System.arraycopy(this.stack, 0, arrn, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, arrn2, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, arrstring, 0, this.stackSize);
            this.stack = arrn;
            this.pathIndices = arrn2;
            this.pathNames = arrstring;
        }
        arrn = this.stack;
        int n2 = this.stackSize;
        this.stackSize = n2 + 1;
        arrn[n2] = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    private char readEscapeCharacter() throws IOException {
        if (this.pos == this.limit && !this.fillBuffer(1)) {
            throw this.syntaxError("Unterminated escape sequence");
        }
        char[] arrc = this.buffer;
        int n = this.pos;
        this.pos = n + 1;
        char c = arrc[n];
        switch (c) {
            default: {
                throw this.syntaxError("Invalid escape sequence");
            }
            case 'u': {
                int n2;
                if (this.pos + 4 > this.limit && !this.fillBuffer(4)) {
                    throw this.syntaxError("Unterminated escape sequence");
                }
                c = '\u0000';
                n = n2 = this.pos;
                do {
                    if (n >= n2 + 4) {
                        this.pos += 4;
                        return c;
                    }
                    char c2 = this.buffer[n];
                    char c3 = (char)(c << 4);
                    if (c2 >= '0' && c2 <= '9') {
                        c = (char)(c2 - 48 + c3);
                    } else if (c2 >= 'a' && c2 <= 'f') {
                        c = (char)(c2 - 97 + 10 + c3);
                    } else {
                        if (c2 < 'A' || c2 > 'F') throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                        c = (char)(c2 - 65 + 10 + c3);
                    }
                    ++n;
                } while (true);
            }
            case 't': {
                return '\t';
            }
            case 'b': {
                return '\b';
            }
            case 'n': {
                return '\n';
            }
            case 'r': {
                return '\r';
            }
            case 'f': {
                return '\f';
            }
            case '\n': {
                ++this.lineNumber;
                this.lineStart = this.pos;
                return c;
            }
            case '\"': 
            case '\'': 
            case '/': 
            case '\\': 
        }
        return c;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void skipQuotedValue(char c) throws IOException {
        char[] arrc = this.buffer;
        do {
            int n = this.pos;
            int n2 = this.limit;
            while (n < n2) {
                int n3;
                int n4 = n + 1;
                char c2 = arrc[n];
                if (c2 == c) {
                    this.pos = n4;
                    return;
                }
                if (c2 == '\\') {
                    this.pos = n4;
                    this.readEscapeCharacter();
                    n = this.pos;
                    n3 = this.limit;
                } else {
                    n3 = n2;
                    n = n4;
                    if (c2 == '\n') {
                        ++this.lineNumber;
                        this.lineStart = n4;
                        n3 = n2;
                        n = n4;
                    }
                }
                n2 = n3;
            }
            this.pos = n;
        } while (this.fillBuffer(1));
        throw this.syntaxError("Unterminated string");
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean skipTo(String string2) throws IOException {
        while (this.pos + string2.length() <= this.limit || this.fillBuffer(string2.length())) {
            if (this.buffer[this.pos] == '\n') {
                ++this.lineNumber;
                this.lineStart = this.pos + 1;
            } else {
                int n = 0;
                do {
                    if (n >= string2.length()) {
                        return true;
                    }
                    if (this.buffer[this.pos + n] != string2.charAt(n)) break;
                    ++n;
                } while (true);
            }
            ++this.pos;
        }
        return false;
    }

    private void skipToEndOfLine() throws IOException {
        int n;
        do {
            if (this.pos < this.limit || this.fillBuffer(1)) {
                char[] arrc = this.buffer;
                n = this.pos;
                this.pos = n + 1;
                if ((n = arrc[n]) != 10) continue;
                ++this.lineNumber;
                this.lineStart = this.pos;
            }
            return;
        } while (n != 13);
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int n = 0;
            block5 : while (this.pos + n < this.limit) {
                switch (this.buffer[this.pos + n]) {
                    default: {
                        ++n;
                        continue block5;
                    }
                    case '#': 
                    case '/': 
                    case ';': 
                    case '=': 
                    case '\\': {
                        this.checkLenient();
                    }
                    case '\t': 
                    case '\n': 
                    case '\f': 
                    case '\r': 
                    case ' ': 
                    case ',': 
                    case ':': 
                    case '[': 
                    case ']': 
                    case '{': 
                    case '}': 
                }
                this.pos += n;
                return;
            }
            this.pos += n;
        } while (this.fillBuffer(1));
    }

    private IOException syntaxError(String string2) throws IOException {
        throw new MalformedJsonException(string2 + this.locationString());
    }

    public void beginArray() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 3) {
            this.push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_ARRAY but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    public void beginObject() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 1) {
            this.push(3);
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected BEGIN_OBJECT but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    @Override
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    /*
     * Enabled aggressive block sorting
     */
    int doPeek() throws IOException {
        int n;
        int n2 = this.stack[this.stackSize - 1];
        if (n2 == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else if (n2 == 2) {
            switch (this.nextNonWhitespace(true)) {
                case 44: {
                    break;
                }
                default: {
                    throw this.syntaxError("Unterminated array");
                }
                case 93: {
                    this.peeked = 4;
                    return 4;
                }
                case 59: {
                    this.checkLenient();
                    break;
                }
            }
        } else {
            if (n2 == 3 || n2 == 5) {
                this.stack[this.stackSize - 1] = 4;
                if (n2 == 5) {
                    switch (this.nextNonWhitespace(true)) {
                        default: {
                            throw this.syntaxError("Unterminated object");
                        }
                        case 125: {
                            this.peeked = 2;
                            return 2;
                        }
                        case 59: {
                            this.checkLenient();
                            break;
                        }
                        case 44: 
                    }
                }
                int n3 = this.nextNonWhitespace(true);
                switch (n3) {
                    default: {
                        this.checkLenient();
                        --this.pos;
                        if (!this.isLiteral((char)n3)) throw this.syntaxError("Expected name");
                        this.peeked = 14;
                        return 14;
                    }
                    case 34: {
                        this.peeked = 13;
                        return 13;
                    }
                    case 39: {
                        this.checkLenient();
                        this.peeked = 12;
                        return 12;
                    }
                    case 125: {
                        if (n2 == 5) throw this.syntaxError("Expected name");
                        {
                            this.peeked = 2;
                            return 2;
                        }
                    }
                }
            }
            if (n2 == 4) {
                this.stack[this.stackSize - 1] = 5;
                switch (this.nextNonWhitespace(true)) {
                    case 58: {
                        break;
                    }
                    default: {
                        throw this.syntaxError("Expected ':'");
                    }
                    case 61: {
                        this.checkLenient();
                        if (this.pos >= this.limit && !this.fillBuffer(1) || this.buffer[this.pos] != '>') break;
                        ++this.pos;
                        break;
                    }
                }
            } else if (n2 == 6) {
                if (this.lenient) {
                    this.consumeNonExecutePrefix();
                }
                this.stack[this.stackSize - 1] = 7;
            } else if (n2 == 7) {
                if (this.nextNonWhitespace(false) == -1) {
                    this.peeked = 17;
                    return 17;
                }
                this.checkLenient();
                --this.pos;
            } else if (n2 == 8) {
                throw new IllegalStateException("JsonReader is closed");
            }
        }
        switch (this.nextNonWhitespace(true)) {
            default: {
                --this.pos;
                n2 = this.peekKeyword();
                if (n2 == 0) break;
                return n2;
            }
            case 93: {
                if (n2 == 1) {
                    this.peeked = 4;
                    return 4;
                }
            }
            case 44: 
            case 59: {
                if (n2 != 1 && n2 != 2) {
                    throw this.syntaxError("Unexpected value");
                }
                this.checkLenient();
                --this.pos;
                this.peeked = 7;
                return 7;
            }
            case 39: {
                this.checkLenient();
                this.peeked = 8;
                return 8;
            }
            case 34: {
                this.peeked = 9;
                return 9;
            }
            case 91: {
                this.peeked = 3;
                return 3;
            }
            case 123: {
                this.peeked = 1;
                return 1;
            }
        }
        n2 = n = this.peekNumber();
        if (n != 0) {
            return n2;
        }
        if (!this.isLiteral(this.buffer[this.pos])) {
            throw this.syntaxError("Expected value");
        }
        this.checkLenient();
        this.peeked = 10;
        return 10;
    }

    public void endArray() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 4) {
            --this.stackSize;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_ARRAY but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    public void endObject() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 2) {
            --this.stackSize;
            this.pathNames[this.stackSize] = null;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            this.peeked = 0;
            return;
        }
        throw new IllegalStateException("Expected END_OBJECT but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder().append('$');
        int n = 0;
        int n2 = this.stackSize;
        while (n < n2) {
            switch (this.stack[n]) {
                case 1: 
                case 2: {
                    stringBuilder.append('[').append(this.pathIndices[n]).append(']');
                }
                default: {
                    break;
                }
                case 3: 
                case 4: 
                case 5: {
                    stringBuilder.append('.');
                    if (this.pathNames[n] == null) break;
                    stringBuilder.append(this.pathNames[n]);
                }
            }
            ++n;
        }
        return stringBuilder.toString();
    }

    public boolean hasNext() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        return n2 != 2 && n2 != 4;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    public boolean nextBoolean() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 5) {
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            return true;
        }
        if (n2 == 6) {
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public double nextDouble() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 15) {
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            return this.peekedLong;
        }
        if (n2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else if (n2 == 8 || n2 == 9) {
            char c = n2 == 8 ? (char)'\'' : '\"';
            this.peekedString = this.nextQuotedValue(c);
        } else if (n2 == 10) {
            this.peekedString = this.nextUnquotedValue();
        } else if (n2 != 11) {
            throw new IllegalStateException("Expected a double but was " + (Object)((Object)this.peek()) + this.locationString());
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] arrn = this.pathIndices;
        n2 = this.stackSize - 1;
        arrn[n2] = arrn[n2] + 1;
        return d;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int nextInt() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 15) {
            n2 = (int)this.peekedLong;
            if (this.peekedLong != (long)n2) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + this.locationString());
            }
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n = this.stackSize - 1;
            arrn[n] = arrn[n] + 1;
            return n2;
        }
        if (n2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            int[] arrn;
            if (n2 != 8 && n2 != 9 && n2 != 10) {
                throw new IllegalStateException("Expected an int but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            if (n2 == 10) {
                this.peekedString = this.nextUnquotedValue();
            } else {
                char c = n2 == 8 ? (char)'\'' : '\"';
                this.peekedString = this.nextQuotedValue(c);
            }
            try {
                n2 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                arrn = this.pathIndices;
                n = this.stackSize - 1;
            }
            catch (NumberFormatException numberFormatException) {}
            arrn[n] = arrn[n] + 1;
            return n2;
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        n2 = (int)d;
        if ((double)n2 != d) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] arrn = this.pathIndices;
        n = this.stackSize - 1;
        arrn[n] = arrn[n] + 1;
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long nextLong() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 15) {
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            return this.peekedLong;
        }
        if (n2 == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            long l;
            int[] arrn;
            if (n2 != 8 && n2 != 9 && n2 != 10) {
                throw new IllegalStateException("Expected a long but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            if (n2 == 10) {
                this.peekedString = this.nextUnquotedValue();
            } else {
                char c = n2 == 8 ? (char)'\'' : '\"';
                this.peekedString = this.nextQuotedValue(c);
            }
            try {
                l = Long.parseLong(this.peekedString);
                this.peeked = 0;
                arrn = this.pathIndices;
                n2 = this.stackSize - 1;
            }
            catch (NumberFormatException numberFormatException) {}
            arrn[n2] = arrn[n2] + 1;
            return l;
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        long l = (long)d;
        if ((double)l != d) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + this.locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] arrn = this.pathIndices;
        n2 = this.stackSize - 1;
        arrn[n2] = arrn[n2] + 1;
        return l;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String nextName() throws IOException {
        String string2;
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 14) {
            string2 = this.nextUnquotedValue();
        } else if (n2 == 12) {
            string2 = this.nextQuotedValue('\'');
        } else {
            if (n2 != 13) {
                throw new IllegalStateException("Expected a name but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            string2 = this.nextQuotedValue('\"');
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = string2;
        return string2;
    }

    public void nextNull() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 7) {
            this.peeked = 0;
            int[] arrn = this.pathIndices;
            n2 = this.stackSize - 1;
            arrn[n2] = arrn[n2] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + (Object)((Object)this.peek()) + this.locationString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String nextString() throws IOException {
        String string2;
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        if (n2 == 10) {
            string2 = this.nextUnquotedValue();
        } else if (n2 == 8) {
            string2 = this.nextQuotedValue('\'');
        } else if (n2 == 9) {
            string2 = this.nextQuotedValue('\"');
        } else if (n2 == 11) {
            string2 = this.peekedString;
            this.peekedString = null;
        } else if (n2 == 15) {
            string2 = Long.toString(this.peekedLong);
        } else {
            if (n2 != 16) {
                throw new IllegalStateException("Expected a string but was " + (Object)((Object)this.peek()) + this.locationString());
            }
            string2 = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        }
        this.peeked = 0;
        int[] arrn = this.pathIndices;
        n2 = this.stackSize - 1;
        arrn[n2] = arrn[n2] + 1;
        return string2;
    }

    public JsonToken peek() throws IOException {
        int n;
        int n2 = n = this.peeked;
        if (n == 0) {
            n2 = this.doPeek();
        }
        switch (n2) {
            default: {
                throw new AssertionError();
            }
            case 1: {
                return JsonToken.BEGIN_OBJECT;
            }
            case 2: {
                return JsonToken.END_OBJECT;
            }
            case 3: {
                return JsonToken.BEGIN_ARRAY;
            }
            case 4: {
                return JsonToken.END_ARRAY;
            }
            case 12: 
            case 13: 
            case 14: {
                return JsonToken.NAME;
            }
            case 5: 
            case 6: {
                return JsonToken.BOOLEAN;
            }
            case 7: {
                return JsonToken.NULL;
            }
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return JsonToken.STRING;
            }
            case 15: 
            case 16: {
                return JsonToken.NUMBER;
            }
            case 17: 
        }
        return JsonToken.END_DOCUMENT;
    }

    public final void setLenient(boolean bl) {
        this.lenient = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void skipValue() throws IOException {
        int n;
        int n2 = 0;
        do {
            int n3 = n = this.peeked;
            if (n == 0) {
                n3 = this.doPeek();
            }
            if (n3 == 3) {
                this.push(1);
                n = n2 + 1;
            } else if (n3 == 1) {
                this.push(3);
                n = n2 + 1;
            } else if (n3 == 4) {
                --this.stackSize;
                n = n2 - 1;
            } else if (n3 == 2) {
                --this.stackSize;
                n = n2 - 1;
            } else if (n3 == 14 || n3 == 10) {
                this.skipUnquotedValue();
                n = n2;
            } else if (n3 == 8 || n3 == 12) {
                this.skipQuotedValue('\'');
                n = n2;
            } else if (n3 == 9 || n3 == 13) {
                this.skipQuotedValue('\"');
                n = n2;
            } else {
                n = n2;
                if (n3 == 16) {
                    this.pos += this.peekedNumberLength;
                    n = n2;
                }
            }
            this.peeked = 0;
            n2 = n;
        } while (n != 0);
        int[] arrn = this.pathIndices;
        n = this.stackSize - 1;
        arrn[n] = arrn[n] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    public String toString() {
        return this.getClass().getSimpleName() + this.locationString();
    }

}

