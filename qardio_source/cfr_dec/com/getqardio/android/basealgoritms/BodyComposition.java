/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.basealgoritms;

public class BodyComposition {
    public static float bmc(int n, int n2, float f, int n3, boolean bl, boolean bl2) {
        if (n3 >= 15 && n3 <= 25 && bl2) {
            return BodyComposition.bmcAthlete(n, n2, f, bl);
        }
        if (n3 >= 63 && bl) {
            return BodyComposition.bmcOldMale(n, n2, f);
        }
        if (n3 >= 63 && !bl) {
            return BodyComposition.bmcOldFemale(n, n2, f);
        }
        return BodyComposition.bmcHealthy(n, n2, f, n3, bl);
    }

    private static float bmcAthlete(int n, int n2, float f, boolean bl) {
        return 0.06f * BodyComposition.ffmAthlete(n, n2, f, bl);
    }

    private static float bmcHealthy(int n, int n2, float f, int n3, boolean bl) {
        return 0.06f * BodyComposition.ffmHealthy(n, n2, f, n3, bl);
    }

    private static float bmcOldFemale(int n, int n2, float f) {
        return 0.06f * BodyComposition.ffmOldFemale(n, n2, f);
    }

    private static float bmcOldMale(int n, int n2, float f) {
        return 0.06f * BodyComposition.ffmOldMale(n, n2, f);
    }

    public static float bmi(float f, int n) {
        float f2 = (float)n / 100.0f;
        return f / (f2 * f2);
    }

    private static float ffmAthlete(int n, int n2, float f, boolean bl) {
        return BodyComposition.tbwAthlete(n, n2, f, bl) / 0.732f;
    }

    private static float ffmHealthy(int n, int n2, float f, int n3, boolean bl) {
        return BodyComposition.tbwHealthy(n, n2, f, n3, bl) / 0.732f;
    }

    private static float ffmOldFemale(int n, int n2, float f) {
        return BodyComposition.tbwOldFemale(n, n2, f) / 0.725f;
    }

    private static float ffmOldMale(int n, int n2, float f) {
        return BodyComposition.tbwOldMale(n, n2, f) / 0.702f;
    }

    public static float mt(int n, int n2, float f, int n3, boolean bl, boolean bl2) {
        if (n3 >= 15 && n3 <= 25 && bl2) {
            return BodyComposition.mtAthlete(n, n2, f, bl);
        }
        if (n3 >= 63 && bl) {
            return BodyComposition.mtOldMale(n, n2, f);
        }
        if (n3 >= 63 && !bl) {
            return BodyComposition.mtOldFemale(n, n2, f);
        }
        return BodyComposition.mtHealthy(n, n2, f, n3, bl);
    }

    private static float mtAthlete(int n, int n2, float f, boolean bl) {
        return 0.208f * BodyComposition.ffmAthlete(n, n2, f, bl);
    }

    private static float mtHealthy(int n, int n2, float f, int n3, boolean bl) {
        return 0.208f * BodyComposition.ffmHealthy(n, n2, f, n3, bl);
    }

    private static float mtOldFemale(int n, int n2, float f) {
        return 0.215f * BodyComposition.ffmOldFemale(n, n2, f);
    }

    private static float mtOldMale(int n, int n2, float f) {
        return 0.238f * BodyComposition.ffmOldMale(n, n2, f);
    }

    public static float tbw(int n, int n2, float f, int n3, boolean bl, boolean bl2) {
        if (n3 >= 15 && n3 <= 25 && bl2) {
            return BodyComposition.tbwAthlete(n, n2, f, bl);
        }
        if (n3 >= 63 && bl) {
            return BodyComposition.tbwOldMale(n, n2, f);
        }
        if (n3 >= 63 && !bl) {
            return BodyComposition.tbwOldFemale(n, n2, f);
        }
        return BodyComposition.tbwHealthy(n, n2, f, n3, bl);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static float tbwAthlete(int n, int n2, float f, boolean bl) {
        boolean bl2;
        if (bl) {
            bl2 = true;
            do {
                return 0.286f + 0.195f * (float)n * (float)n / (float)n2 + 0.385f * f + 5.086f * (float)bl2;
                break;
            } while (true);
        }
        bl2 = false;
        return 0.286f + 0.195f * (float)n * (float)n / (float)n2 + 0.385f * f + 5.086f * (float)bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static float tbwHealthy(int n, int n2, float f, int n3, boolean bl) {
        boolean bl2;
        if (bl) {
            bl2 = true;
            do {
                return 6.58f + 0.3674f * (float)(n * n / n2) + 0.17531f * f - 0.11f * (float)n3 + 2.88f * (float)bl2;
                break;
            } while (true);
        }
        bl2 = false;
        return 6.58f + 0.3674f * (float)(n * n / n2) + 0.17531f * f - 0.11f * (float)n3 + 2.88f * (float)bl2;
    }

    private static float tbwOldFemale(int n, int n2, float f) {
        return 11.9f + 0.2715f * (float)n * (float)n / (float)n2 + 0.1087f * f;
    }

    private static float tbwOldMale(int n, int n2, float f) {
        return 8.3f + 0.3228f * (float)n * (float)n / (float)n2 + 0.1652f * f;
    }
}

