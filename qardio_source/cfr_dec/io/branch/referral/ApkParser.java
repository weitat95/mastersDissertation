/*
 * Decompiled with CFR 0.147.
 */
package io.branch.referral;

class ApkParser {
    public static int endDocTag = 1048833;
    public static int endTag;
    public static int startTag;

    static {
        startTag = 1048834;
        endTag = 1048835;
    }

    ApkParser() {
    }

    private boolean validURI(String string2) {
        return string2 != null && !string2.equals("http") && !string2.equals("https") && !string2.equals("geo") && !string2.equals("*") && !string2.equals("package") && !string2.equals("sms") && !string2.equals("smsto") && !string2.equals("mms") && !string2.equals("mmsto") && !string2.equals("tel") && !string2.equals("voicemail") && !string2.equals("file") && !string2.equals("content") && !string2.equals("mailto");
    }

    public int LEW(byte[] arrby, int n) {
        return arrby[n + 3] << 24 & 0xFF000000 | arrby[n + 2] << 16 & 0xFF0000 | arrby[n + 1] << 8 & 0xFF00 | arrby[n] & 0xFF;
    }

    public String compXmlString(byte[] arrby, int n, int n2, int n3) {
        if (n3 < 0) {
            return null;
        }
        return this.compXmlStringAt(arrby, n2 + this.LEW(arrby, n3 * 4 + n));
    }

    public String compXmlStringAt(byte[] arrby, int n) {
        int n2 = arrby[n + 1] << 8 & 0xFF00 | arrby[n] & 0xFF;
        byte[] arrby2 = new byte[n2];
        for (int i = 0; i < n2; ++i) {
            arrby2[i] = arrby[n + 2 + i * 2];
        }
        return new String(arrby2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String decompressXML(byte[] arrby) {
        int n;
        int n2;
        int n3 = 36 + this.LEW(arrby, 16) * 4;
        int n4 = n = this.LEW(arrby, 12);
        do {
            n2 = n;
            if (n4 >= arrby.length - 4) break;
            if (this.LEW(arrby, n4) == startTag) {
                n2 = n4;
                break;
            }
            n4 += 4;
        } while (true);
        block1 : while (n2 < arrby.length) {
            n4 = this.LEW(arrby, n2);
            if (n4 != startTag) {
                if (n4 == endTag) {
                    n2 += 24;
                    continue;
                }
                if (n4 != endDocTag) return "bnc_no_value";
                return "bnc_no_value";
            }
            int n5 = this.LEW(arrby, n2 + 28);
            n4 = n2 + 36;
            n = 0;
            do {
                String string2;
                n2 = n4;
                if (n >= n5) continue block1;
                n2 = this.LEW(arrby, n4 + 4);
                int n6 = this.LEW(arrby, n4 + 8);
                int n7 = this.LEW(arrby, n4 + 16);
                n4 += 20;
                if (this.compXmlString(arrby, 36, n3, n2).equals("scheme") && this.validURI(string2 = n6 != -1 ? this.compXmlString(arrby, 36, n3, n6) : "resourceID 0x" + Integer.toHexString(n7))) {
                    return string2;
                }
                ++n;
            } while (true);
        }
        return "bnc_no_value";
    }
}

