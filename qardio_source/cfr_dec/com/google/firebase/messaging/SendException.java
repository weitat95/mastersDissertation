/*
 * Decompiled with CFR 0.147.
 */
package com.google.firebase.messaging;

import java.util.Locale;

public final class SendException
extends Exception {
    private final int mErrorCode;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    SendException(String var1_1) {
        block18: {
            var3_2 = 1;
            super(var1_1);
            if (var1_1 == null) ** GOTO lbl-1000
            var1_1 = var1_1.toLowerCase(Locale.US);
            var2_3 = -1;
            switch (var1_1.hashCode()) {
                case -920906446: {
                    if (var1_1.equals("invalid_parameters")) {
                        var2_3 = 0;
                        ** break;
                    }
                    ** GOTO lbl25
                }
                case -95047692: {
                    if (var1_1.equals("missing_to")) {
                        var2_3 = 1;
                        ** break;
                    }
                    ** GOTO lbl25
                }
                case -617027085: {
                    if (var1_1.equals("messagetoobig")) {
                        var2_3 = 2;
                        ** break;
                    }
                    ** GOTO lbl25
                }
                case -1743242157: {
                    if (var1_1.equals("service_not_available")) {
                        var2_3 = 3;
                    }
                }
lbl25:
                // 10 sources
                default: {
                    break block18;
                }
                case -1290953729: 
            }
            if (var1_1.equals("toomanymessages")) {
                var2_3 = 4;
            }
        }
        switch (var2_3) {
            default: lbl-1000:
            // 2 sources
            {
                var3_2 = 0;
                break;
            }
            case 2: {
                var3_2 = 2;
                break;
            }
            case 3: {
                var3_2 = 3;
            }
            case 0: 
            case 1: {
                break;
            }
            case 4: {
                var3_2 = 4;
            }
        }
        this.mErrorCode = var3_2;
    }
}

