/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 */
package android.support.v4.text;

import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.text.TextUtilsCompat;
import android.text.SpannableStringBuilder;
import java.util.Locale;

public final class BidiFormatter {
    private static final BidiFormatter DEFAULT_LTR_INSTANCE;
    private static final BidiFormatter DEFAULT_RTL_INSTANCE;
    private static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC;
    private static final String LRM_STRING;
    private static final String RLM_STRING;
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    static {
        DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        LRM_STRING = Character.toString('\u200e');
        RLM_STRING = Character.toString('\u200f');
        DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
        DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    }

    private BidiFormatter(boolean bl, int n, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = bl;
        this.mFlags = n;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    private static int getEntryDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getEntryDir();
    }

    private static int getExitDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getExitDir();
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    private static boolean isRtlLocale(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    private String markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getExitDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getExitDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return "";
    }

    private String markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean bl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getEntryDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getEntryDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return "";
    }

    public boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean bl) {
        if (charSequence == null) {
            return null;
        }
        boolean bl2 = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.getStereoReset() && bl) {
            textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
            spannableStringBuilder.append((CharSequence)this.markBefore(charSequence, textDirectionHeuristicCompat));
        }
        if (bl2 != this.mIsRtlContext) {
            char c = bl2 ? (char)'\u202b' : '\u202a';
            spannableStringBuilder.append(c);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append('\u202c');
        } else {
            spannableStringBuilder.append(charSequence);
        }
        textDirectionHeuristicCompat = spannableStringBuilder;
        if (!bl) return textDirectionHeuristicCompat;
        textDirectionHeuristicCompat = bl2 ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR;
        spannableStringBuilder.append((CharSequence)this.markAfter(charSequence, textDirectionHeuristicCompat));
        return spannableStringBuilder;
    }

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean bl) {
            if (bl) {
                return DEFAULT_RTL_INSTANCE;
            }
            return DEFAULT_LTR_INSTANCE;
        }

        private void initialize(boolean bl) {
            this.mIsRtlContext = bl;
            this.mTextDirectionHeuristicCompat = DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return Builder.getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
        }
    }

    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;

        static {
            for (int i = 0; i < 1792; ++i) {
                DirectionalityEstimator.DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean bl) {
            this.text = charSequence;
            this.isHtml = bl;
            this.length = charSequence.length();
        }

        private static byte getCachedDirectionality(char c) {
            if (c < '\u0700') {
                return DIR_TYPE_CACHE[c];
            }
            return Character.getDirectionality(c);
        }

        private byte skipEntityBackward() {
            int n = this.charIndex;
            while (this.charIndex > 0) {
                int n2;
                CharSequence charSequence = this.text;
                this.charIndex = n2 = this.charIndex - 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '&') {
                    return 12;
                }
                if (this.lastChar != ';') continue;
            }
            this.charIndex = n;
            this.lastChar = (char)59;
            return 13;
        }

        private byte skipEntityForward() {
            while (this.charIndex < this.length) {
                char c;
                CharSequence charSequence = this.text;
                int n = this.charIndex;
                this.charIndex = n + 1;
                this.lastChar = c = charSequence.charAt(n);
                if (c != ';') continue;
            }
            return 12;
        }

        /*
         * Enabled aggressive block sorting
         */
        private byte skipTagBackward() {
            int n = this.charIndex;
            block0 : while (this.charIndex > 0) {
                int n2;
                CharSequence charSequence = this.text;
                this.charIndex = n2 = this.charIndex - 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '<') {
                    return 12;
                }
                if (this.lastChar == '>') break;
                if (this.lastChar != '\"' && this.lastChar != '\'') continue;
                n2 = this.lastChar;
                while (this.charIndex > 0) {
                    char c;
                    int n3;
                    charSequence = this.text;
                    this.charIndex = n3 = this.charIndex - 1;
                    this.lastChar = c = charSequence.charAt(n3);
                    if (c == n2) continue block0;
                }
            }
            this.charIndex = n;
            this.lastChar = (char)62;
            return 13;
        }

        private byte skipTagForward() {
            int n = this.charIndex;
            block0 : while (this.charIndex < this.length) {
                CharSequence charSequence = this.text;
                int n2 = this.charIndex;
                this.charIndex = n2 + 1;
                this.lastChar = charSequence.charAt(n2);
                if (this.lastChar == '>') {
                    return 12;
                }
                if (this.lastChar != '\"' && this.lastChar != '\'') continue;
                n2 = this.lastChar;
                while (this.charIndex < this.length) {
                    char c;
                    charSequence = this.text;
                    int n3 = this.charIndex;
                    this.charIndex = n3 + 1;
                    this.lastChar = c = charSequence.charAt(n3);
                    if (c == n2) continue block0;
                }
            }
            this.charIndex = n;
            this.lastChar = (char)60;
            return 13;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        byte dirTypeBackward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(this.lastChar)) {
                int n = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(n);
                return Character.getDirectionality(n);
            }
            --this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (!this.isHtml) return by2;
            if (this.lastChar == '>') {
                return this.skipTagBackward();
            }
            by2 = by;
            if (this.lastChar != ';') return by2;
            return this.skipEntityBackward();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        byte dirTypeForward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(this.lastChar)) {
                int n = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(n);
                return Character.getDirectionality(n);
            }
            ++this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (!this.isHtml) return by2;
            if (this.lastChar == '<') {
                return this.skipTagForward();
            }
            by2 = by;
            if (this.lastChar != '&') return by2;
            return this.skipEntityForward();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        int getEntryDir() {
            this.charIndex = 0;
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            block13 : while (this.charIndex < this.length && n3 == 0) {
                switch (this.dirTypeForward()) {
                    case 9: {
                        continue block13;
                    }
                    default: {
                        n3 = n;
                        continue block13;
                    }
                    case 14: 
                    case 15: {
                        ++n;
                        n2 = -1;
                        continue block13;
                    }
                    case 16: 
                    case 17: {
                        ++n;
                        n2 = 1;
                        continue block13;
                    }
                    case 18: {
                        --n;
                        n2 = 0;
                        continue block13;
                    }
                    case 0: {
                        if (n == 0) {
                            return -1;
                        }
                        n3 = n;
                        continue block13;
                    }
                    case 1: 
                    case 2: 
                }
                if (n == 0) {
                    return 1;
                }
                n3 = n;
            }
            if (n3 == 0) {
                return 0;
            }
            int n4 = n2;
            if (n2 != 0) return n4;
            block14 : while (this.charIndex > 0) {
                switch (this.dirTypeBackward()) {
                    default: {
                        continue block14;
                    }
                    case 14: 
                    case 15: {
                        if (n3 == n) {
                            return -1;
                        }
                        --n;
                        continue block14;
                    }
                    case 16: 
                    case 17: {
                        if (n3 == n) {
                            return 1;
                        }
                        --n;
                        continue block14;
                    }
                    case 18: 
                }
                ++n;
            }
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        int getExitDir() {
            this.charIndex = this.length;
            int n = 0;
            int n2 = 0;
            block8 : while (this.charIndex > 0) {
                switch (this.dirTypeBackward()) {
                    case 9: {
                        continue block8;
                    }
                    default: {
                        if (n2 != 0) continue block8;
                        n2 = n;
                        continue block8;
                    }
                    case 0: {
                        if (n == 0) return -1;
                        {
                            if (n2 != 0) continue block8;
                            n2 = n;
                            continue block8;
                        }
                    }
                    case 14: 
                    case 15: {
                        if (n2 == n) {
                            return -1;
                        }
                        --n;
                        continue block8;
                    }
                    case 1: 
                    case 2: {
                        if (n == 0) {
                            return 1;
                        }
                        if (n2 != 0) continue block8;
                        n2 = n;
                        continue block8;
                    }
                    case 16: 
                    case 17: {
                        if (n2 == n) {
                            return 1;
                        }
                        --n;
                        continue block8;
                    }
                    case 18: 
                }
                ++n;
            }
            return 0;
        }
    }

}

