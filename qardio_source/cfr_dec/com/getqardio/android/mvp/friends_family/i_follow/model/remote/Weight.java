/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.common.gson_annotation.PojoWithPureJson;
import com.google.gson.annotations.SerializedName;

@PojoWithPureJson
public class Weight {

    public class ExtraInfo {
        @SerializedName(value="age")
        public Integer age;
        @SerializedName(value="battery")
        public Long battery;
        @SerializedName(value="bmi")
        public Float bmi;
        @SerializedName(value="bmc")
        public Integer bone;
        @SerializedName(value="fat")
        public Integer fat;
        @SerializedName(value="height")
        public Integer height;
        @SerializedName(value="id")
        public String id;
        @SerializedName(value="z")
        public Integer impedance;
        @SerializedName(value="mt")
        public Integer muscle;
        @SerializedName(value="sex")
        public String sex;
        @SerializedName(value="source")
        public String source;
        @SerializedName(value="user")
        public String user;
        @SerializedName(value="userid")
        public Long userId;
        @SerializedName(value="tbw")
        public Integer water;

        public Integer getAge() {
            return this.age;
        }

        public Long getBattery() {
            return this.battery;
        }

        public Float getBmi() {
            return this.bmi;
        }

        public Integer getBone() {
            return this.bone;
        }

        public Integer getFat() {
            return this.fat;
        }

        public Integer getHeight() {
            return this.height;
        }

        public String getId() {
            return this.id;
        }

        public Integer getImpedance() {
            return this.impedance;
        }

        public Integer getMuscle() {
            return this.muscle;
        }

        public String getSex() {
            return this.sex;
        }

        public String getSource() {
            return this.source;
        }

        public String getUser() {
            return this.user;
        }

        public Long getUserId() {
            return this.userId;
        }

        public Integer getWater() {
            return this.water;
        }
    }

}

