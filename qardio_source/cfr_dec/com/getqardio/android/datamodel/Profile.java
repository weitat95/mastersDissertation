/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;
import java.util.Date;

public class Profile
extends BaseEntity {
    public String address;
    public String country;
    public Date dob;
    public String doctorEmail;
    public String doctorName;
    private String email;
    public String firstName;
    public Integer gender;
    public Float height;
    public Integer heightUnit;
    public String lastName;
    public Integer latitude;
    public String locale;
    public Integer longitude;
    public String phone;
    public String qbVisibleName;
    public Long refId;
    public String state;
    public Integer syncStatus;
    public Long userId;
    public Float weight;
    public Integer weightUnit;
    public String zip;

    public String buildFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setEmail(String string2) {
        this.email = string2;
        long l = this.email != null ? (long)string2.hashCode() : 0L;
        this.refId = l;
    }
}

