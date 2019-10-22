/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package android.support.v4.content;

import android.content.SharedPreferences;

public final class SharedPreferencesCompat {

    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper = new Helper();

        private EditorCompat() {
        }

        public static EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        public void apply(SharedPreferences.Editor editor) {
            this.mHelper.apply(editor);
        }

        private static class Helper {
            Helper() {
            }

            public void apply(SharedPreferences.Editor editor) {
                try {
                    editor.apply();
                    return;
                }
                catch (AbstractMethodError abstractMethodError) {
                    editor.commit();
                    return;
                }
            }
        }

    }

}

