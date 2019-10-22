/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

public class FirmwareDescription {
    public String description;
    public String deviceType;
    public Long id;
    public String ipAddress;
    public String locale;
    public Long size;
    public Long updateDate;
    public Long uploadDate;
    public Integer versionBugFix;
    public Integer versionMajor;
    public Integer versionMinor;
    public String versionRevision;

    public boolean isFirmwareNewerOrEqual18() {
        return this.versionMajor >= 1 && this.versionMinor >= 8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isGreater(FirmwareDescription firmwareDescription) {
        block6: {
            block5: {
                if (this.versionMajor > firmwareDescription.versionMajor) break block5;
                if (this.versionMajor < firmwareDescription.versionMajor) {
                    return false;
                }
                if (this.versionMinor > firmwareDescription.versionMinor) break block5;
                if (this.versionMinor < firmwareDescription.versionMinor) {
                    return false;
                }
                if (this.versionBugFix <= firmwareDescription.versionBugFix) break block6;
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return String.format("%d.%d.%d", this.versionMajor, this.versionMinor, this.versionBugFix);
    }
}

