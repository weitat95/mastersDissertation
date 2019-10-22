/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.FieldNamingStrategy;
import java.lang.reflect.Field;
import java.util.Locale;

public enum FieldNamingPolicy implements FieldNamingStrategy
{
    IDENTITY{

        @Override
        public String translateName(Field field) {
            return field.getName();
        }
    }
    ,
    UPPER_CAMEL_CASE{

        @Override
        public String translateName(Field field) {
            return 2.upperCaseFirstLetter(field.getName());
        }
    }
    ,
    UPPER_CAMEL_CASE_WITH_SPACES{

        @Override
        public String translateName(Field field) {
            return 3.upperCaseFirstLetter(3.separateCamelCase(field.getName(), " "));
        }
    }
    ,
    LOWER_CASE_WITH_UNDERSCORES{

        @Override
        public String translateName(Field field) {
            return 4.separateCamelCase(field.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    }
    ,
    LOWER_CASE_WITH_DASHES{

        @Override
        public String translateName(Field field) {
            return 5.separateCamelCase(field.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };


    private static String modifyString(char c, String string2, int n) {
        if (n < string2.length()) {
            return c + string2.substring(n);
        }
        return String.valueOf(c);
    }

    static String separateCamelCase(String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (Character.isUpperCase(c) && stringBuilder.length() != 0) {
                stringBuilder.append(string3);
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String upperCaseFirstLetter(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        char c = string2.charAt(0);
        do {
            if (n >= string2.length() - 1 || Character.isLetter(c)) {
                if (n != string2.length()) break;
                return stringBuilder.toString();
            }
            stringBuilder.append(c);
            c = string2.charAt(++n);
        } while (true);
        String string3 = string2;
        if (Character.isUpperCase(c)) return string3;
        return stringBuilder.append(FieldNamingPolicy.modifyString(Character.toUpperCase(c), string2, n + 1)).toString();
    }

}

