/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.settings.CachedSettingsIo;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import org.json.JSONObject;

class DefaultCachedSettingsIo
implements CachedSettingsIo {
    private final Kit kit;

    public DefaultCachedSettingsIo(Kit kit) {
        this.kit = kit;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    @Override
    public JSONObject readCachedSettings() {
        block11: {
            Fabric.getLogger().d("Fabric", "Reading cached settings...");
            var3_1 = null;
            var5_4 = null;
            var2_5 = null;
            var4_7 = null;
            var1_8 = var5_4;
            var6_10 = new File(new FileStoreImpl(this.kit).getFilesDir(), "com.crashlytics.settings.json");
            var1_8 = var5_4;
            if (!var6_10.exists()) break block11;
            var1_8 = var5_4;
            var2_5 = new FileInputStream(var6_10);
            var1_8 = new JSONObject(CommonUtils.streamToString((InputStream)var2_5));
lbl15:
            // 2 sources
            do {
                CommonUtils.closeOrLog((Closeable)var2_5, "Error while closing settings cache file.");
                return var1_8;
                break;
            } while (true);
        }
        var1_8 = var5_4;
        try {
            try {
                Fabric.getLogger().d("Fabric", "No cached settings found.");
                var1_8 = var4_7;
                ** continue;
            }
            catch (Exception var1_9) {
                var2_5 = var3_1;
                var3_1 = var1_9;
lbl28:
                // 2 sources
                do {
                    var1_8 = var2_5;
                    Fabric.getLogger().e("Fabric", "Failed to fetch cached settings", var3_1);
                    CommonUtils.closeOrLog((Closeable)var2_5, "Error while closing settings cache file.");
                    return null;
                    break;
                } while (true);
            }
        }
        catch (Throwable var2_6) lbl-1000:
        // 2 sources
        {
            do {
                CommonUtils.closeOrLog(var1_8, "Error while closing settings cache file.");
                throw var2_5;
                break;
            } while (true);
        }
        catch (Throwable var3_2) {
            var1_8 = var2_5;
            var2_5 = var3_2;
            ** continue;
        }
        catch (Exception var3_3) {
            ** continue;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void writeCachedSettings(long var1_1, JSONObject var3_2) {
        Fabric.getLogger().d("Fabric", "Writing settings to cache file...");
        if (var3_2 /* !! */  == null) return;
        var5_10 = null;
        var6_12 = null;
        var4_13 = var5_10;
        var3_2 /* !! */ .put("expires_at", var1_1);
        var4_14 = var5_10;
        var5_10 = new FileWriter(new File(new FileStoreImpl(this.kit).getFilesDir(), "com.crashlytics.settings.json"));
        var5_10.write(var3_2 /* !! */ .toString());
        var5_10.flush();
        CommonUtils.closeOrLog((Closeable)var5_10, "Failed to close settings writer.");
        return;
        catch (Exception var5_11) {
            block7: {
                var3_3 = var6_12;
                break block7;
                catch (Throwable var3_7) {
                    var4_19 = var5_10;
                    ** GOTO lbl-1000
                }
                catch (Exception var4_20) {
                    var3_8 = var5_10;
                    var5_10 = var4_20;
                }
            }
            var4_16 = var3_4;
            try {
                Fabric.getLogger().e("Fabric", "Failed to cache settings", (Throwable)var5_10);
            }
            catch (Throwable var3_5) lbl-1000:
            // 2 sources
            {
                CommonUtils.closeOrLog((Closeable)var4_18, "Failed to close settings writer.");
                throw var3_6;
            }
            CommonUtils.closeOrLog((Closeable)var3_4, "Failed to close settings writer.");
            return;
        }
    }
}

