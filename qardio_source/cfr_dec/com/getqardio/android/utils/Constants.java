/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils;

import java.util.regex.Pattern;

public abstract class Constants {
    public static final String ANALYTICS_WRITE_KEY;
    public static final String MIXPANEL_PROJECT_TOKEN;

    /*
     * Enabled aggressive block sorting
     */
    static {
        String string2 = "release".equals("internal") ? "1s4N1OGkVmXnHjOAgml7enzf5eU4D9Gv" : "SaiumiTWMt5jRS4ROJBmytdwDglO99M6";
        ANALYTICS_WRITE_KEY = string2;
        string2 = "release".equals("internal") ? "58e2712324f037b46b65e7b27ad4aa94" : "8c1b99c8c8478122625acf27e65d0442";
        MIXPANEL_PROJECT_TOKEN = string2;
    }

    public static class Gender {
        public static String int2String(int n) {
            switch (n) {
                default: {
                    return "m";
                }
                case 0: {
                    return "m";
                }
                case 1: 
            }
            return "f";
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int string2Int(String string2) {
            if ("m".equals(string2) || !"f".equals(string2)) {
                return 0;
            }
            return 1;
        }
    }

    public static class HeightUnit {
        public static String int2String(int n) {
            switch (n) {
                default: {
                    return "CM";
                }
                case 0: {
                    return "CM";
                }
                case 1: 
            }
            return "FT";
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int string2Int(String string2) {
            if ("CM".equals(string2) || !"FT".equals(string2)) {
                return 0;
            }
            return 1;
        }
    }

    public static interface Regexp {
        public static final Pattern FIRST_NAME_PATTERN;
        public static final Pattern PASSWORD_PATTERN;

        static {
            PASSWORD_PATTERN = Pattern.compile(".{6,30}");
            FIRST_NAME_PATTERN = Pattern.compile("^[\\p{L}][\\p{L} .'`-]*[\\p{L}]$");
        }
    }

    public static class WeightUnit {
        public static String int2String(int n) {
            switch (n) {
                default: {
                    return "KG";
                }
                case 0: {
                    return "KG";
                }
                case 1: {
                    return "LBS";
                }
                case 2: 
            }
            return "STONE";
        }

        /*
         * Enabled aggressive block sorting
         */
        public static int string2Int(String string2) {
            block5: {
                block4: {
                    if ("KG".equals(string2)) break block4;
                    if ("LBS".equals(string2)) {
                        return 1;
                    }
                    if ("STONE".equals(string2)) break block5;
                }
                return 0;
            }
            return 2;
        }
    }

    public static class WifiLevel {
        public static int calculateSignalLevel(int n) {
            switch (n) {
                default: {
                    return 2130837901;
                }
                case 0: {
                    return 2130837901;
                }
                case 1: {
                    return 2130838042;
                }
                case 2: {
                    return 2130838043;
                }
                case 3: 
            }
            return 2130838044;
        }
    }

}

