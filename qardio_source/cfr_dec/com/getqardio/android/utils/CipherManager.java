/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.Signature
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.util.Base64
 */
package com.getqardio.android.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.Settings;
import android.util.Base64;
import com.getqardio.android.utils.Utils;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import timber.log.Timber;

public class CipherManager {
    private static byte[] mSignatureHash;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String decrypt(Context var0, String var1_7) {
        if (var1_8 == null) {
            return null;
        }
        try {
            var2_9 = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            var2_9.init(2, new SecretKeySpec(CipherManager.getKey(var0 /* !! */ ), "AES"));
            return new String(var2_9.doFinal(Base64.decode((byte[])var1_8.getBytes(), (int)0)));
        }
        catch (BadPaddingException var0_2) {}
        ** GOTO lbl-1000
        catch (NoSuchAlgorithmException var0_4) {
            ** GOTO lbl-1000
        }
        catch (NoSuchPaddingException var0_5) {
            ** GOTO lbl-1000
        }
        catch (InvalidKeyException var0_6) {
            ** GOTO lbl-1000
        }
        catch (IllegalBlockSizeException var0_7) {}
lbl-1000:
        // 5 sources
        {
            Timber.e((Throwable)var0_3);
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String encrypt(Context var0, String var1_7) {
        if (var1_8 == null) {
            return null;
        }
        try {
            var2_9 = Cipher.getInstance("AES/ECB/PKCS5Padding");
            var2_9.init(1, new SecretKeySpec(CipherManager.getKey(var0 /* !! */ ), "AES"));
            return Base64.encodeToString((byte[])var2_9.doFinal(var1_8.getBytes()), (int)0);
        }
        catch (BadPaddingException var0_2) {}
        ** GOTO lbl-1000
        catch (NoSuchAlgorithmException var0_4) {
            ** GOTO lbl-1000
        }
        catch (NoSuchPaddingException var0_5) {
            ** GOTO lbl-1000
        }
        catch (InvalidKeyException var0_6) {
            ** GOTO lbl-1000
        }
        catch (IllegalBlockSizeException var0_7) {}
lbl-1000:
        // 5 sources
        {
            Timber.e((Throwable)var0_3);
            return null;
        }
    }

    private static byte[] getKey(Context context) {
        if (mSignatureHash == null) {
            byte[] arrby = Settings.Secure.getString((ContentResolver)context.getContentResolver(), (String)"android_id").getBytes();
            mSignatureHash = CipherManager.md5(Utils.mergeArrays(CipherManager.getSignature(context), arrby));
        }
        return mSignatureHash;
    }

    private static byte[] getSignature(Context arrby) {
        try {
            arrby = arrby.getPackageManager().getPackageInfo((String)arrby.getPackageName(), (int)64).signatures[0].toByteArray();
            return arrby;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Timber.e(nameNotFoundException);
            return null;
        }
    }

    private static byte[] md5(byte[] arrby) {
        try {
            arrby = MessageDigest.getInstance("MD5").digest(arrby);
            return arrby;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Timber.e(noSuchAlgorithmException);
            return null;
        }
    }
}

