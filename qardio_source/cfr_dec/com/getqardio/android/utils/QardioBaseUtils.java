/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.utils;

import android.content.Context;
import android.text.TextUtils;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.basealgoritms.BodyComposition;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.data.BaseUser;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.util.BaseUserUtil;
import com.getqardio.android.util.ModeUtil;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.wifi.QBWifiConfig;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public class QardioBaseUtils {
    /*
     * Enabled aggressive block sorting
     */
    public static BaseUser baseUserFromProfile(Profile profile, String string2) {
        int n;
        boolean bl = profile.gender == 0;
        int n2 = n = 0;
        if (profile.height != null) {
            n2 = n;
            if (profile.heightUnit != null) {
                n2 = Math.round(MetricUtils.convertHeight(profile.heightUnit, 0, profile.height.floatValue()));
            }
        }
        n = profile.weightUnit == null ? 0 : profile.weightUnit;
        int n3 = n2;
        if (n2 == 0) {
            n3 = 175;
        }
        String string3 = profile.qbVisibleName;
        return new BaseUser((int)profile.refId.longValue(), string3, n3, bl, profile.dob, n, 0, string2);
    }

    public static Float bmc(WeightMeasurement weightMeasurement) {
        if (!QardioBaseUtils.isUsefulMeasurement(weightMeasurement) || weightMeasurement.z == 0) {
            return null;
        }
        return Float.valueOf(BodyComposition.bmc(weightMeasurement.height, weightMeasurement.z, weightMeasurement.weight.floatValue(), weightMeasurement.age, QardioBaseUtils.isMale(weightMeasurement.sex), false));
    }

    public static float bmi(float f, int n) {
        return BodyComposition.bmi(f, n);
    }

    public static Float bonePercentage(WeightMeasurement weightMeasurement) {
        Float f = QardioBaseUtils.bmc(weightMeasurement);
        if (f == null || weightMeasurement.weight == null) {
            return null;
        }
        return Float.valueOf(f.floatValue() / weightMeasurement.weight.floatValue() * 100.0f);
    }

    public static int calculateDiffYears(Date date, Date date2) {
        return DateUtils.calculateDiffYears(date, date2);
    }

    public static boolean canCalculateBmi(WeightMeasurement weightMeasurement) {
        return weightMeasurement.weight != null && weightMeasurement.height != null && weightMeasurement.weight.floatValue() > 0.0f && weightMeasurement.height > 0;
    }

    public static List<WifiAp> convertWifiResult(String object) {
        Timber.d("convertWifiResult result - %s", object);
        object = new JSONObject((String)object).getJSONArray("scan");
        ArrayList<WifiAp> arrayList = new ArrayList<WifiAp>(object.length());
        int n = 0;
        do {
            if (n >= object.length()) break;
            JSONObject jSONObject = object.getJSONObject(n);
            String string2 = jSONObject.getString("ssid");
            String string3 = jSONObject.getString("sec");
            int n2 = jSONObject.getInt("rssi");
            arrayList.add(new WifiAp(string2, WifiAp.getSecurityString(string3), n2 - 100));
            ++n;
            continue;
            break;
        } while (true);
        try {
            object = QardioBaseUtils.removeRepeatedWifiAP(arrayList);
            return object;
        }
        catch (JSONException jSONException) {
            return new ArrayList<WifiAp>();
        }
    }

    public static JSONObject createGoalCommand(float f, float f2, int n, int n2) {
        return BaseUserUtil.createGoalCommand(f, f2, n, n2);
    }

    public static JSONObject createImpedanceAndHapticSetting(QardioBaseDevice.BaseMode baseMode) {
        switch (1.$SwitchMap$com$getqardio$android$baseble$QardioBaseDevice$BaseMode[baseMode.ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return BaseUserUtil.createImpedanceCommand(true, true);
            }
            case 2: {
                return BaseUserUtil.createImpedanceCommand(true, true);
            }
            case 3: {
                return BaseUserUtil.createImpedanceCommand(false, true);
            }
            case 4: 
        }
        return BaseUserUtil.createImpedanceCommand(false, false);
    }

    public static JSONObject createModeCommand(QardioBaseDevice.BaseMode baseMode, int n, int n2) {
        switch (1.$SwitchMap$com$getqardio$android$baseble$QardioBaseDevice$BaseMode[baseMode.ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return ModeUtil.createNormalModeCommand(n, n2);
            }
            case 3: {
                return ModeUtil.createWeightOnlyModeCommand(n, n2);
            }
            case 2: {
                return ModeUtil.createSmartFeedbackModeCommand(n, n2);
            }
            case 4: 
        }
        return ModeUtil.createPregnancyModeCommand(n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void fillWeightMeasurementWithProfileData(WeightMeasurement weightMeasurement, Profile object) {
        int n = ((Profile)object).heightUnit == null ? 0 : ((Profile)object).heightUnit;
        weightMeasurement.height = Math.round(MetricUtils.convertHeight(n, 0, ((Profile)object).height.floatValue()));
        weightMeasurement.sex = Constants.Gender.int2String(((Profile)object).gender);
        weightMeasurement.age = QardioBaseUtils.calculateDiffYears(((Profile)object).dob, new Date());
        object = ((Profile)object).buildFullName();
        weightMeasurement.fullName = object = ((String)object).substring(0, Math.min(((String)object).length(), 9));
        weightMeasurement.user = object;
    }

    public static boolean impedanceFromMode(QardioBaseDevice.BaseMode baseMode) {
        boolean bl = true;
        switch (1.$SwitchMap$com$getqardio$android$baseble$QardioBaseDevice$BaseMode[baseMode.ordinal()]) {
            default: {
                bl = false;
            }
            case 1: 
            case 2: {
                return bl;
            }
            case 3: {
                return false;
            }
            case 4: 
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int indexOf(String string2, long l) {
        try {
            string2 = new JSONObject(string2).getJSONArray("users");
            for (int i = 0; i < string2.length(); ++i) {
                long l2;
                String string3 = string2.getJSONObject(i).optString("id", "0");
                boolean bl = TextUtils.isEmpty((CharSequence)string3);
                if (bl) continue;
                try {
                    l2 = Long.parseLong(string3);
                }
                catch (NumberFormatException numberFormatException) {
                    l2 = Long.parseLong(string3.substring(0, string3.length() - 1));
                }
                if (l2 != l) continue;
                return i;
            }
            return -1;
        }
        catch (JSONException jSONException) {
            return -1;
        }
    }

    private static boolean isMale(String string2) {
        return Constants.Gender.string2Int(string2) == 0;
    }

    private static boolean isUsefulMeasurement(WeightMeasurement weightMeasurement) {
        return weightMeasurement != null && weightMeasurement.z != null && weightMeasurement.age != null && weightMeasurement.height != null && weightMeasurement.sex != null && weightMeasurement.weight != null;
    }

    public static int maskFromMode(QardioBaseDevice.BaseMode baseMode) {
        switch (1.$SwitchMap$com$getqardio$android$baseble$QardioBaseDevice$BaseMode[baseMode.ordinal()]) {
            default: {
                return 0;
            }
            case 1: {
                return 4;
            }
            case 2: {
                return 35;
            }
            case 4: {
                return 43;
            }
            case 3: 
        }
        return 38;
    }

    public static Float mt(WeightMeasurement weightMeasurement) {
        if (weightMeasurement.height == null || weightMeasurement.z == 0) {
            return null;
        }
        return Float.valueOf(BodyComposition.mt(weightMeasurement.height, weightMeasurement.z, weightMeasurement.weight.floatValue(), weightMeasurement.age, QardioBaseUtils.isMale(weightMeasurement.sex), false));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Float musclePercentage(WeightMeasurement weightMeasurement) {
        Float f;
        if (weightMeasurement.z == null || weightMeasurement.z == 0 || (f = QardioBaseUtils.mt(weightMeasurement)) == null) {
            return null;
        }
        float f2 = weightMeasurement.weight.floatValue();
        return Float.valueOf(f.floatValue() / f2 * 100.0f);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean needsOnBoarding(Context context) {
        CustomApplication.getApplication();
        return CustomApplication.getApplication().getCurrentUserId() != null && !DataHelper.OnBoardingHelper.isOnboardingFinished(context, CustomApplication.getApplication().getCurrentUserId());
    }

    public static int noProfiles(String string2) {
        try {
            int n = new JSONObject(string2).getJSONArray("users").length();
            return n;
        }
        catch (JSONException jSONException) {
            return 0;
        }
    }

    public static QBWifiConfig parseWifiConfig(String object) {
        if (!TextUtils.isEmpty((CharSequence)object)) {
            try {
                object = new Gson().fromJson((String)object, QBWifiConfig.class);
                return object;
            }
            catch (Exception exception) {
                return QBWifiConfig.empty();
            }
        }
        return QBWifiConfig.empty();
    }

    private static List<WifiAp> removeRepeatedWifiAP(List<WifiAp> object) {
        ArrayList<WifiAp> arrayList = new ArrayList<WifiAp>();
        object = object.iterator();
        while (object.hasNext()) {
            WifiAp wifiAp = (WifiAp)object.next();
            if (arrayList.size() != 0 && arrayList.contains(wifiAp)) continue;
            arrayList.add(wifiAp);
        }
        return arrayList;
    }

    public static Integer tbw(WeightMeasurement weightMeasurement) {
        if (!QardioBaseUtils.isUsefulMeasurement(weightMeasurement) || weightMeasurement.z == 0) {
            return null;
        }
        return Math.round(Float.valueOf(BodyComposition.tbw(weightMeasurement.height, weightMeasurement.z, weightMeasurement.weight.floatValue(), weightMeasurement.age, QardioBaseUtils.isMale(weightMeasurement.sex), false)).floatValue());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Float waterPercentage(WeightMeasurement weightMeasurement) {
        Integer n;
        if (weightMeasurement.z == 0 || (n = QardioBaseUtils.tbw(weightMeasurement)) == null) {
            return null;
        }
        float f = weightMeasurement.weight.floatValue();
        return Float.valueOf(n.floatValue() / f * 100.0f);
    }

    public static float weightForBmi(float f, int n) {
        float f2 = (float)n / 100.0f;
        return (float)((double)f * Math.pow(f2, 2.0));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static WeightMeasurement weightMeasurementFromBase(String string2, Profile object) throws JSONException {
        string2 = new JSONObject(string2);
        WeightMeasurement weightMeasurement = new WeightMeasurement();
        QardioBaseUtils.fillWeightMeasurementWithProfileData(weightMeasurement, (Profile)object);
        weightMeasurement.measurementId = string2.getString("id");
        weightMeasurement.weight = Float.valueOf(Float.parseFloat(string2.getString("weight")));
        weightMeasurement.measureDate = new Date();
        weightMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(weightMeasurement.measureDate));
        if (string2.has("userid")) {
            try {
                weightMeasurement.qbUserId = Long.parseLong(string2.getString("userid"));
            }
            catch (NumberFormatException numberFormatException) {
                weightMeasurement.qbUserId = null;
            }
        }
        if (string2.has("z") && string2.has("fat")) {
            try {
                object = Integer.parseInt(string2.getString("fat"));
                if ((Integer)object > 0) {
                    weightMeasurement.fat = object;
                    weightMeasurement.z = Integer.parseInt(string2.getString("z"));
                }
            }
            catch (NumberFormatException numberFormatException) {}
        }
        if (string2.has("mt")) {
            weightMeasurement.muscle = Integer.parseInt(string2.getString("mt"));
        }
        if (string2.has("tbw")) {
            weightMeasurement.water = Integer.parseInt(string2.getString("tbw"));
        }
        if (string2.has("bmc")) {
            weightMeasurement.bone = Integer.parseInt(string2.getString("bmc"));
        }
        return weightMeasurement;
    }

    public static WeightMeasurement weightMeasurementGuestFromBase(String string2) throws JSONException {
        string2 = new JSONObject(string2);
        WeightMeasurement weightMeasurement = new WeightMeasurement();
        weightMeasurement.measurementId = string2.getString("id");
        weightMeasurement.weight = Float.valueOf(Float.parseFloat(string2.getString("weight")));
        weightMeasurement.measureDate = new Date();
        weightMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(weightMeasurement.measureDate));
        return weightMeasurement;
    }

}

