/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        block5: do {
            int n;
            char[] arrc;
            if (this.pos >= this.length) {
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            switch (this.chars[this.pos]) {
                default: {
                    arrc = this.chars;
                    n = this.end;
                    this.end = n + 1;
                    arrc[n] = this.chars[this.pos];
                    ++this.pos;
                    continue block5;
                }
                case '+': 
                case ',': 
                case ';': {
                    return new String(this.chars, this.beg, this.end - this.beg);
                }
                case '\\': {
                    arrc = this.chars;
                    n = this.end;
                    this.end = n + 1;
                    arrc[n] = this.getEscaped();
                    ++this.pos;
                    continue block5;
                }
                case ' ': 
            }
            this.cur = this.end;
            ++this.pos;
            arrc = this.chars;
            n = this.end;
            this.end = n + 1;
            arrc[n] = 32;
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                arrc = this.chars;
                n = this.end;
                this.end = n + 1;
                arrc[n] = 32;
                ++this.pos;
            }
            if (this.pos == this.length || this.chars[this.pos] == ',' || this.chars[this.pos] == '+' || this.chars[this.pos] == ';') break;
        } while (true);
        return new String(this.chars, this.beg, this.cur - this.beg);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getByte(int n) {
        if (n + 1 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        }
        int n2 = this.chars[n];
        if (n2 >= 48 && n2 <= 57) {
            n2 -= 48;
        } else if (n2 >= 97 && n2 <= 102) {
            n2 -= 87;
        } else {
            if (n2 < 65) throw new IllegalStateException("Malformed DN: " + this.dn);
            if (n2 > 70) throw new IllegalStateException("Malformed DN: " + this.dn);
            n2 -= 55;
        }
        if ((n = this.chars[n + 1]) >= 48 && n <= 57) {
            n -= 48;
            return (n2 << 4) + n;
        }
        if (n >= 97 && n <= 102) {
            return (n2 << 4) + (n -= 87);
        }
        if (n < 65) throw new IllegalStateException("Malformed DN: " + this.dn);
        if (n > 70) throw new IllegalStateException("Malformed DN: " + this.dn);
        return (n2 << 4) + (n -= 55);
    }

    private char getEscaped() {
        ++this.pos;
        if (this.pos == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        switch (this.chars[this.pos]) {
            default: {
                return this.getUTF8();
            }
            case ' ': 
            case '\"': 
            case '#': 
            case '%': 
            case '*': 
            case '+': 
            case ',': 
            case ';': 
            case '<': 
            case '=': 
            case '>': 
            case '\\': 
            case '_': 
        }
        return this.chars[this.pos];
    }

    /*
     * Enabled aggressive block sorting
     */
    private char getUTF8() {
        int n;
        char c = '?';
        int n2 = this.getByte(this.pos);
        ++this.pos;
        if (n2 < 128) {
            return (char)n2;
        }
        char c2 = c;
        if (n2 < 192) return c2;
        c2 = c;
        if (n2 > 247) return c2;
        if (n2 <= 223) {
            n = 1;
            n2 &= 0x1F;
        } else if (n2 <= 239) {
            n = 2;
            n2 &= 0xF;
        } else {
            n = 3;
            n2 &= 7;
        }
        int n3 = 0;
        int n4 = n2;
        n2 = n3;
        while (n2 < n) {
            ++this.pos;
            c2 = c;
            if (this.pos == this.length) return c2;
            c2 = c;
            if (this.chars[this.pos] != '\\') return c2;
            ++this.pos;
            n3 = this.getByte(this.pos);
            ++this.pos;
            c2 = c;
            if ((n3 & 0xC0) != 128) return c2;
            n4 = (n4 << 6) + (n3 & 0x3F);
            ++n2;
        }
        return (char)n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String hexAV() {
        int n;
        int n2;
        if (this.pos + 4 >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.beg = this.pos++;
        do {
            block9: {
                block8: {
                    block7: {
                        if (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') break block7;
                        this.end = this.pos;
                        break block8;
                    }
                    if (this.chars[this.pos] == ' ') {
                        this.end = this.pos++;
                        while (this.pos < this.length && this.chars[this.pos] == ' ') {
                            ++this.pos;
                        }
                    }
                    break block9;
                }
                if ((n = this.end - this.beg) >= 5 && (n & 1) != 0) break;
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
            if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                char[] arrc = this.chars;
                n2 = this.pos;
                arrc[n2] = (char)(arrc[n2] + 32);
            }
            ++this.pos;
        } while (true);
        byte[] arrby = new byte[n / 2];
        n2 = 0;
        int n3 = this.beg + 1;
        while (n2 < arrby.length) {
            arrby[n2] = (byte)this.getByte(n3);
            n3 += 2;
            ++n2;
        }
        return new String(this.chars, this.beg, n);
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            ++this.pos;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            ++this.pos;
        }
        if (this.pos >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                ++this.pos;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        ++this.pos;
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            ++this.pos;
        }
        if (!(this.end - this.beg <= 4 || this.chars[this.beg + 3] != '.' || this.chars[this.beg] != 'O' && this.chars[this.beg] != 'o' || this.chars[this.beg + 1] != 'I' && this.chars[this.beg + 1] != 'i' || this.chars[this.beg + 2] != 'D' && this.chars[this.beg + 2] != 'd')) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String quotedAV() {
        ++this.pos;
        this.end = this.beg = this.pos;
        do {
            if (this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
            if (this.chars[this.pos] == '\"') {
                ++this.pos;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    ++this.pos;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            this.chars[this.end] = this.chars[this.pos] == '\\' ? this.getEscaped() : this.chars[this.pos];
            ++this.pos;
            ++this.end;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String findMostSpecific(String string2) {
        String string3;
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String string4 = string3 = this.nextAT();
        if (string3 == null) {
            return null;
        }
        do {
            string3 = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                default: {
                    string3 = this.escapedAV();
                    break;
                }
                case '\"': {
                    string3 = this.quotedAV();
                    break;
                }
                case '#': {
                    string3 = this.hexAV();
                }
                case '+': 
                case ',': 
                case ';': 
            }
            if (string2.equalsIgnoreCase(string4)) return string3;
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            ++this.pos;
            string4 = string3 = this.nextAT();
        } while (string3 != null);
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }
}

