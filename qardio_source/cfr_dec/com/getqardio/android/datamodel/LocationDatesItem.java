/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

public class LocationDatesItem {
    public boolean clustersExist;
    public long endDate;
    public long startDate;

    public LocationDatesItem(long l, long l2, boolean bl) {
        this.startDate = l;
        this.endDate = l2;
        this.clustersExist = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (!(object instanceof LocationDatesItem)) {
                    return false;
                }
                object = (LocationDatesItem)object;
                if (this.clustersExist != ((LocationDatesItem)object).clustersExist) {
                    return false;
                }
                if (this.endDate != ((LocationDatesItem)object).endDate) {
                    return false;
                }
                if (this.startDate != ((LocationDatesItem)object).startDate) break block7;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        int n2 = (int)(this.startDate ^ this.startDate >>> 32);
        int n3 = (int)(this.endDate ^ this.endDate >>> 32);
        if (this.clustersExist) {
            n = 1;
            do {
                return (n2 * 31 + n3) * 31 + n;
                break;
            } while (true);
        }
        n = 0;
        return (n2 * 31 + n3) * 31 + n;
    }
}

