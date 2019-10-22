/*
 * Decompiled with CFR 0.147.
 */
package io.realm.exceptions;

import io.realm.internal.Keep;
import java.util.Locale;

@Keep
public class RealmFileException
extends RuntimeException {
    private final Kind kind;

    public RealmFileException(byte by, String string2) {
        super(string2);
        this.kind = Kind.getKind(by);
    }

    public RealmFileException(Kind kind, String string2) {
        super(string2);
        this.kind = kind;
    }

    public RealmFileException(Kind kind, String string2, Throwable throwable) {
        super(string2, throwable);
        this.kind = kind;
    }

    public RealmFileException(Kind kind, Throwable throwable) {
        super(throwable);
        this.kind = kind;
    }

    public Kind getKind() {
        return this.kind;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s Kind: %s.", new Object[]{super.toString(), this.kind});
    }

    @Keep
    public static enum Kind {
        ACCESS_ERROR,
        BAD_HISTORY,
        PERMISSION_DENIED,
        EXISTS,
        NOT_FOUND,
        INCOMPATIBLE_LOCK_FILE,
        FORMAT_UPGRADE_REQUIRED;


        static Kind getKind(byte by) {
            switch (by) {
                default: {
                    throw new RuntimeException("Unknown value for RealmFileException kind.");
                }
                case 0: {
                    return ACCESS_ERROR;
                }
                case 2: {
                    return PERMISSION_DENIED;
                }
                case 3: {
                    return EXISTS;
                }
                case 4: {
                    return NOT_FOUND;
                }
                case 5: {
                    return INCOMPATIBLE_LOCK_FILE;
                }
                case 6: {
                    return FORMAT_UPGRADE_REQUIRED;
                }
                case 1: 
            }
            return BAD_HISTORY;
        }
    }

}

