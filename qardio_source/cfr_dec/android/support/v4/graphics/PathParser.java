/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Path
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

public class PathParser {
    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] arrf) {
        arrayList.add(new PathDataNode(c, arrf));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean canMorph(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        if (arrpathDataNode != null && arrpathDataNode2 != null && arrpathDataNode.length == arrpathDataNode2.length) {
            int n = 0;
            do {
                if (n >= arrpathDataNode.length) {
                    return true;
                }
                if (arrpathDataNode[n].mType != arrpathDataNode2[n].mType || arrpathDataNode[n].mParams.length != arrpathDataNode2[n].mParams.length) break;
                ++n;
            } while (true);
        }
        return false;
    }

    static float[] copyOfRange(float[] arrf, int n, int n2) {
        if (n > n2) {
            throw new IllegalArgumentException();
        }
        int n3 = arrf.length;
        if (n < 0 || n > n3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        n3 = Math.min(n2 -= n, n3 - n);
        float[] arrf2 = new float[n2];
        System.arraycopy(arrf, n, arrf2, 0, n3);
        return arrf2;
    }

    public static PathDataNode[] createNodesFromPathData(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = 0;
        int n2 = 1;
        ArrayList<PathDataNode> arrayList = new ArrayList<PathDataNode>();
        while (n2 < string2.length()) {
            String string3 = string2.substring(n, n2 = PathParser.nextStart(string2, n2)).trim();
            if (string3.length() > 0) {
                float[] arrf = PathParser.getFloats(string3);
                PathParser.addNode(arrayList, string3.charAt(0), arrf);
            }
            n = n2++;
        }
        if (n2 - n == 1 && n < string2.length()) {
            PathParser.addNode(arrayList, string2.charAt(n), new float[0]);
        }
        return arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String string2) {
        Path path = new Path();
        PathDataNode[] arrpathDataNode = PathParser.createNodesFromPathData(string2);
        if (arrpathDataNode != null) {
            try {
                PathDataNode.nodesToPath(arrpathDataNode, path);
                return path;
            }
            catch (RuntimeException runtimeException) {
                throw new RuntimeException("Error in parsing " + string2, runtimeException);
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static PathDataNode[] deepCopyNodes(PathDataNode[] arrpathDataNode) {
        if (arrpathDataNode == null) {
            return null;
        }
        PathDataNode[] arrpathDataNode2 = new PathDataNode[arrpathDataNode.length];
        int n = 0;
        do {
            PathDataNode[] arrpathDataNode3 = arrpathDataNode2;
            if (n >= arrpathDataNode.length) return arrpathDataNode3;
            arrpathDataNode2[n] = new PathDataNode(arrpathDataNode[n]);
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void extract(String string2, int n, ExtractFloatResult extractFloatResult) {
        int n2;
        boolean bl = false;
        extractFloatResult.mEndWithNegOrDot = false;
        boolean bl2 = false;
        boolean bl3 = false;
        for (n2 = n; n2 < string2.length(); ++n2) {
            boolean bl4;
            boolean bl5;
            boolean bl6;
            boolean bl7 = false;
            switch (string2.charAt(n2)) {
                default: {
                    bl6 = bl2;
                    bl5 = bl7;
                    bl4 = bl;
                    break;
                }
                case ' ': 
                case ',': {
                    bl4 = true;
                    bl5 = bl7;
                    bl6 = bl2;
                    break;
                }
                case '-': {
                    bl4 = bl;
                    bl5 = bl7;
                    bl6 = bl2;
                    if (n2 == n) break;
                    bl4 = bl;
                    bl5 = bl7;
                    bl6 = bl2;
                    if (bl3) break;
                    bl4 = true;
                    extractFloatResult.mEndWithNegOrDot = true;
                    bl5 = bl7;
                    bl6 = bl2;
                    break;
                }
                case '.': {
                    if (!bl2) {
                        bl6 = true;
                        bl4 = bl;
                        bl5 = bl7;
                        break;
                    }
                    bl4 = true;
                    extractFloatResult.mEndWithNegOrDot = true;
                    bl5 = bl7;
                    bl6 = bl2;
                    break;
                }
                case 'E': 
                case 'e': {
                    bl5 = true;
                    bl4 = bl;
                    bl6 = bl2;
                }
            }
            if (bl4) break;
            bl = bl4;
            bl3 = bl5;
            bl2 = bl6;
        }
        extractFloatResult.mEndPosition = n2;
    }

    private static float[] getFloats(String string2) {
        if (string2.charAt(0) == 'z' || string2.charAt(0) == 'Z') {
            return new float[0];
        }
        float[] arrf = new float[string2.length()];
        int n = 1;
        ExtractFloatResult extractFloatResult = new ExtractFloatResult();
        int n2 = string2.length();
        int n3 = 0;
        do {
            int n4;
            if (n < n2) {
                block11: {
                    PathParser.extract(string2, n, extractFloatResult);
                    n4 = extractFloatResult.mEndPosition;
                    if (n >= n4) break block11;
                    int n5 = n3 + 1;
                    arrf[n3] = Float.parseFloat(string2.substring(n, n4));
                    n3 = n5;
                }
                if (extractFloatResult.mEndWithNegOrDot) {
                    n = n4;
                    continue;
                }
            }
            try {
                arrf = PathParser.copyOfRange(arrf, 0, n3);
                return arrf;
            }
            catch (NumberFormatException numberFormatException) {
                throw new RuntimeException("error in parsing \"" + string2 + "\"", numberFormatException);
            }
            n = n4 + 1;
        } while (true);
    }

    private static int nextStart(String string2, int n) {
        char c;
        while (n < string2.length() && (((c = string2.charAt(n)) - 65) * (c - 90) > 0 && (c - 97) * (c - 122) > 0 || c == 'e' || c == 'E')) {
            ++n;
        }
        return n;
    }

    public static void updateNodes(PathDataNode[] arrpathDataNode, PathDataNode[] arrpathDataNode2) {
        for (int i = 0; i < arrpathDataNode2.length; ++i) {
            arrpathDataNode[i].mType = arrpathDataNode2[i].mType;
            for (int j = 0; j < arrpathDataNode2[i].mParams.length; ++j) {
                arrpathDataNode[i].mParams[j] = arrpathDataNode2[i].mParams[j];
            }
        }
    }

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        public float[] mParams;
        public char mType;

        PathDataNode(char c, float[] arrf) {
            this.mType = c;
            this.mParams = arrf;
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            this.mParams = PathParser.copyOfRange(pathDataNode.mParams, 0, pathDataNode.mParams.length);
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void addCommand(Path path, float[] arrf, char c, char c2, float[] arrf2) {
            int n = 2;
            float f = arrf[0];
            float f2 = arrf[1];
            float f3 = arrf[2];
            float f4 = arrf[3];
            float f5 = arrf[4];
            float f6 = arrf[5];
            switch (c2) {
                case 'Z': 
                case 'z': {
                    path.close();
                    f = f5;
                    f2 = f6;
                    f3 = f5;
                    f4 = f6;
                    path.moveTo(f, f2);
                    break;
                }
                case 'L': 
                case 'M': 
                case 'T': 
                case 'l': 
                case 'm': 
                case 't': {
                    n = 2;
                    break;
                }
                case 'H': 
                case 'V': 
                case 'h': 
                case 'v': {
                    n = 1;
                    break;
                }
                case 'C': 
                case 'c': {
                    n = 6;
                    break;
                }
                case 'Q': 
                case 'S': 
                case 'q': 
                case 's': {
                    n = 4;
                    break;
                }
                case 'A': 
                case 'a': {
                    n = 7;
                    break;
                }
            }
            char c3 = '\u0000';
            char c4 = c;
            c = c3;
            float f7 = f6;
            f6 = f5;
            float f8 = f4;
            float f9 = f3;
            while (c < arrf2.length) {
                switch (c2) {
                    default: {
                        f4 = f7;
                        f3 = f8;
                        f5 = f9;
                        break;
                    }
                    case 'm': {
                        f += arrf2[c + '\u0000'];
                        f2 += arrf2[c + '\u0001'];
                        if (c > '\u0000') {
                            path.rLineTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                            f5 = f9;
                            f3 = f8;
                            f4 = f7;
                            break;
                        }
                        path.rMoveTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f6 = f;
                        f4 = f2;
                        f5 = f9;
                        f3 = f8;
                        break;
                    }
                    case 'M': {
                        f = arrf2[c + '\u0000'];
                        f2 = arrf2[c + '\u0001'];
                        if (c > '\u0000') {
                            path.lineTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                            f5 = f9;
                            f3 = f8;
                            f4 = f7;
                            break;
                        }
                        path.moveTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f6 = f;
                        f4 = f2;
                        f5 = f9;
                        f3 = f8;
                        break;
                    }
                    case 'l': {
                        path.rLineTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f += arrf2[c + '\u0000'];
                        f2 += arrf2[c + '\u0001'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'L': {
                        path.lineTo(arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f = arrf2[c + '\u0000'];
                        f2 = arrf2[c + '\u0001'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'h': {
                        path.rLineTo(arrf2[c + '\u0000'], 0.0f);
                        f += arrf2[c + '\u0000'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'H': {
                        path.lineTo(arrf2[c + '\u0000'], f2);
                        f = arrf2[c + '\u0000'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'v': {
                        path.rLineTo(0.0f, arrf2[c + '\u0000']);
                        f2 += arrf2[c + '\u0000'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'V': {
                        path.lineTo(f, arrf2[c + '\u0000']);
                        f2 = arrf2[c + '\u0000'];
                        f5 = f9;
                        f3 = f8;
                        f4 = f7;
                        break;
                    }
                    case 'c': {
                        path.rCubicTo(arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3], arrf2[c + 4], arrf2[c + 5]);
                        f5 = f + arrf2[c + 2];
                        f3 = f2 + arrf2[c + 3];
                        f += arrf2[c + 4];
                        f2 += arrf2[c + 5];
                        f4 = f7;
                        break;
                    }
                    case 'C': {
                        path.cubicTo(arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3], arrf2[c + 4], arrf2[c + 5]);
                        f = arrf2[c + 4];
                        f2 = arrf2[c + 5];
                        f5 = arrf2[c + 2];
                        f3 = arrf2[c + 3];
                        f4 = f7;
                        break;
                    }
                    case 's': {
                        f5 = 0.0f;
                        f3 = 0.0f;
                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                            f5 = f - f9;
                            f3 = f2 - f8;
                        }
                        path.rCubicTo(f5, f3, arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3]);
                        f5 = f + arrf2[c + '\u0000'];
                        f3 = f2 + arrf2[c + '\u0001'];
                        f += arrf2[c + 2];
                        f2 += arrf2[c + 3];
                        f4 = f7;
                        break;
                    }
                    case 'S': {
                        f3 = f;
                        f5 = f2;
                        if (c4 == 'c' || c4 == 's' || c4 == 'C' || c4 == 'S') {
                            f3 = 2.0f * f - f9;
                            f5 = 2.0f * f2 - f8;
                        }
                        path.cubicTo(f3, f5, arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3]);
                        f5 = arrf2[c + '\u0000'];
                        f3 = arrf2[c + '\u0001'];
                        f = arrf2[c + 2];
                        f2 = arrf2[c + 3];
                        f4 = f7;
                        break;
                    }
                    case 'q': {
                        path.rQuadTo(arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3]);
                        f5 = f + arrf2[c + '\u0000'];
                        f3 = f2 + arrf2[c + '\u0001'];
                        f += arrf2[c + 2];
                        f2 += arrf2[c + 3];
                        f4 = f7;
                        break;
                    }
                    case 'Q': {
                        path.quadTo(arrf2[c + '\u0000'], arrf2[c + '\u0001'], arrf2[c + 2], arrf2[c + 3]);
                        f5 = arrf2[c + '\u0000'];
                        f3 = arrf2[c + '\u0001'];
                        f = arrf2[c + 2];
                        f2 = arrf2[c + 3];
                        f4 = f7;
                        break;
                    }
                    case 't': {
                        f3 = 0.0f;
                        f5 = 0.0f;
                        if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                            f3 = f - f9;
                            f5 = f2 - f8;
                        }
                        path.rQuadTo(f3, f5, arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f3 = f + f3;
                        f4 = f2 + f5;
                        f += arrf2[c + '\u0000'];
                        f2 += arrf2[c + '\u0001'];
                        f5 = f3;
                        f3 = f4;
                        f4 = f7;
                        break;
                    }
                    case 'T': {
                        f3 = f;
                        f5 = f2;
                        if (c4 == 'q' || c4 == 't' || c4 == 'Q' || c4 == 'T') {
                            f3 = 2.0f * f - f9;
                            f5 = 2.0f * f2 - f8;
                        }
                        path.quadTo(f3, f5, arrf2[c + '\u0000'], arrf2[c + '\u0001']);
                        f = f5;
                        f8 = arrf2[c + '\u0000'];
                        f2 = arrf2[c + '\u0001'];
                        f5 = f3;
                        f3 = f;
                        f4 = f7;
                        f = f8;
                        break;
                    }
                    case 'a': {
                        f5 = arrf2[c + 5];
                        f3 = arrf2[c + 6];
                        f4 = arrf2[c + '\u0000'];
                        f8 = arrf2[c + '\u0001'];
                        f9 = arrf2[c + 2];
                        boolean bl = arrf2[c + 3] != 0.0f;
                        boolean bl2 = arrf2[c + 4] != 0.0f;
                        PathDataNode.drawArc(path, f, f2, f5 + f, f3 + f2, f4, f8, f9, bl, bl2);
                        f5 = f += arrf2[c + 5];
                        f3 = f2 += arrf2[c + 6];
                        f4 = f7;
                        break;
                    }
                    case 'A': {
                        f5 = arrf2[c + 5];
                        f3 = arrf2[c + 6];
                        f4 = arrf2[c + '\u0000'];
                        f8 = arrf2[c + '\u0001'];
                        f9 = arrf2[c + 2];
                        boolean bl = arrf2[c + 3] != 0.0f;
                        boolean bl2 = arrf2[c + 4] != 0.0f;
                        PathDataNode.drawArc(path, f, f2, f5, f3, f4, f8, f9, bl, bl2);
                        f = arrf2[c + 5];
                        f2 = arrf2[c + 6];
                        f5 = f;
                        f3 = f2;
                        f4 = f7;
                    }
                }
                c4 = c2;
                c = (char)(c + n);
                f9 = f5;
                f8 = f3;
                f7 = f4;
            }
            arrf[0] = f;
            arrf[1] = f2;
            arrf[2] = f9;
            arrf[3] = f8;
            arrf[4] = f6;
            arrf[5] = f7;
        }

        private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            int n = (int)Math.ceil(Math.abs(4.0 * d9 / 3.141592653589793));
            double d10 = d8;
            double d11 = Math.cos(d7);
            double d12 = Math.sin(d7);
            d7 = Math.cos(d10);
            d8 = Math.sin(d10);
            double d13 = -d3 * d11 * d8 - d4 * d12 * d7;
            double d14 = -d3 * d12 * d8 + d4 * d11 * d7;
            double d15 = d9 / (double)n;
            d8 = d6;
            d7 = d5;
            d9 = d10;
            d6 = d14;
            d5 = d13;
            for (int i = 0; i < n; ++i) {
                double d16 = d9 + d15;
                d14 = Math.sin(d16);
                double d17 = Math.cos(d16);
                double d18 = d3 * d11 * d17 + d - d4 * d12 * d14;
                d13 = d3 * d12 * d17 + d2 + d4 * d11 * d14;
                d10 = -d3 * d11 * d14 - d4 * d12 * d17;
                d14 = -d3 * d12 * d14 + d4 * d11 * d17;
                d17 = Math.tan((d16 - d9) / 2.0);
                d9 = Math.sin(d16 - d9) * (Math.sqrt(4.0 + 3.0 * d17 * d17) - 1.0) / 3.0;
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float)(d7 + d9 * d5), (float)(d8 + d9 * d6), (float)(d18 - d9 * d10), (float)(d13 - d9 * d14), (float)d18, (float)d13);
                d9 = d16;
                d7 = d18;
                d8 = d13;
                d5 = d10;
                d6 = d14;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean bl, boolean bl2) {
            double d = Math.toRadians(f7);
            double d2 = Math.cos(d);
            double d3 = Math.sin(d);
            double d4 = ((double)f * d2 + (double)f2 * d3) / (double)f5;
            double d5 = ((double)(-f) * d3 + (double)f2 * d2) / (double)f6;
            double d6 = ((double)f3 * d2 + (double)f4 * d3) / (double)f5;
            double d7 = ((double)(-f3) * d3 + (double)f4 * d2) / (double)f6;
            double d8 = d4 - d6;
            double d9 = d5 - d7;
            double d10 = (d4 + d6) / 2.0;
            double d11 = (d5 + d7) / 2.0;
            double d12 = d8 * d8 + d9 * d9;
            if (d12 == 0.0) {
                Log.w((String)"PathParser", (String)" Points are coincident");
                return;
            }
            double d13 = 1.0 / d12 - 0.25;
            if (d13 < 0.0) {
                Log.w((String)"PathParser", (String)("Points are too far apart " + d12));
                float f8 = (float)(Math.sqrt(d12) / 1.99999);
                PathDataNode.drawArc(path, f, f2, f3, f4, f5 * f8, f6 * f8, f7, bl, bl2);
                return;
            }
            d12 = Math.sqrt(d13);
            d8 = d12 * d8;
            d9 = d12 * d9;
            if (bl == bl2) {
                d10 -= d9;
                d11 += d8;
            } else {
                d10 += d9;
                d11 -= d8;
            }
            d4 = Math.atan2(d5 - d11, d4 - d10);
            bl = (d7 = Math.atan2(d7 - d11, d6 - d10) - d4) >= 0.0;
            d6 = d7;
            if (bl2 != bl) {
                d6 = d7 > 0.0 ? d7 - 6.283185307179586 : d7 + 6.283185307179586;
            }
            PathDataNode.arcToBezier(path, (d10 *= (double)f5) * d2 - (d11 *= (double)f6) * d3, d10 * d3 + d11 * d2, f5, f6, f, f2, d, d4, d6);
        }

        public static void nodesToPath(PathDataNode[] arrpathDataNode, Path path) {
            float[] arrf = new float[6];
            char c = 'm';
            for (int i = 0; i < arrpathDataNode.length; ++i) {
                PathDataNode.addCommand(path, arrf, c, arrpathDataNode[i].mType, arrpathDataNode[i].mParams);
                c = arrpathDataNode[i].mType;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            for (int i = 0; i < pathDataNode.mParams.length; ++i) {
                this.mParams[i] = pathDataNode.mParams[i] * (1.0f - f) + pathDataNode2.mParams[i] * f;
            }
        }
    }

}

