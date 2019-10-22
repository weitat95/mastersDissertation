/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network;

import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.datamodel.DeviceAssociation;
import com.getqardio.android.datamodel.FirmwareDescription;
import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import com.getqardio.android.datamodel.Goal;
import com.getqardio.android.datamodel.LogoutResponse;
import com.getqardio.android.datamodel.PregnancyData;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.QardioBaseSettings;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.io.network.response.BPMeasurementsResponse;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.io.network.response.BaseResponse;
import com.getqardio.android.io.network.response.ClaimMeasurementResponse;
import com.getqardio.android.io.network.response.CreateNewUserResponse;
import com.getqardio.android.io.network.response.ForgotPasswordResponse;
import com.getqardio.android.io.network.response.GetFlickrPublicPhotosResponse;
import com.getqardio.android.io.network.response.LoadFAQListResponse;
import com.getqardio.android.io.network.response.LoginResponse;
import com.getqardio.android.io.network.response.StatisticResponse;
import com.getqardio.android.io.network.response.TooltipsResponse;
import com.getqardio.android.io.network.response.WeightMeasurementsResponse;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.Timber;

public abstract class JSONParser {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Float getNotNullData(JSONObject object, String string2, Float f) {
        float f2;
        string2 = object.optString(string2, null);
        object = f;
        if (string2 == null) return object;
        object = f;
        if (JSONObject.NULL.toString().equals(string2)) return object;
        try {
            f2 = Float.parseFloat(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return f;
        }
        return Float.valueOf(f2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Integer getNotNullData(JSONObject object, String string2, Integer n) {
        int n2;
        string2 = object.optString(string2, null);
        object = n;
        if (string2 == null) return object;
        object = n;
        if (JSONObject.NULL.toString().equals(string2)) return object;
        try {
            n2 = Integer.parseInt(string2);
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
        return n2;
    }

    public static JSONObject getResponseData(String string2) throws JSONException {
        if ((string2 = new JSONObject(string2)).optString("status").equals("success")) {
            return string2.getJSONObject("data");
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void parseBPMeasurement(JSONObject jSONObject, BPMeasurement bPMeasurement) throws JSONException {
        bPMeasurement.deviceId = jSONObject.optString("deviceId", "");
        bPMeasurement.sys = JSONParser.getNotNullData(jSONObject, "data1", 0);
        bPMeasurement.dia = JSONParser.getNotNullData(jSONObject, "data2", 0);
        bPMeasurement.pulse = JSONParser.getNotNullData(jSONObject, "data3", 0);
        bPMeasurement.note = !jSONObject.isNull("note") ? jSONObject.optString("note", "") : "";
        bPMeasurement.irregularHeartBeat = jSONObject.optBoolean("irregularHeartBeat", false);
        bPMeasurement.measureDate = new Date(jSONObject.optLong("time", 0L));
        bPMeasurement.timezone = jSONObject.optString("timezone", "");
        if (!jSONObject.isNull("longitude")) {
            bPMeasurement.longitude = jSONObject.optDouble("longitude", 0.0);
        }
        if (!jSONObject.isNull("latitude")) {
            bPMeasurement.latitude = jSONObject.optDouble("latitude", 0.0);
        }
        if (!jSONObject.isNull("tag")) {
            bPMeasurement.tag = jSONObject.optInt("tag", 0);
        }
        bPMeasurement.source = jSONObject.optInt("source", 0);
        if (bPMeasurement.sys <= 0) {
            bPMeasurement.sys = null;
        }
        if (bPMeasurement.dia <= 0) {
            bPMeasurement.dia = null;
        }
        if (bPMeasurement.pulse <= 0) {
            bPMeasurement.pulse = null;
        }
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<BPMeasurementsResponse, List<BaseError>> parseBPMeasurements(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<ClaimMeasurementResponse, List<BaseError>> parseClaimMeasurements(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<CreateNewUserResponse, List<BaseError>> parseCreateNewUser(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    private static BaseResponse<String, List<BaseError>> parseDataAsString(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static BaseResponse<String, List<BaseError>> parseDeleteCurrentGoal(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    public static BaseResponse<String, List<BaseError>> parseDeleteMeasurements(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    private static void parseErrors(BaseResponse<?, List<BaseError>> baseResponse, JSONArray jSONArray) throws JSONException {
        baseResponse.setStatus(BaseResponse.Status.FAILURE);
        baseResponse.setData(null);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); ++i) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            arrayList.add(new BaseError(jSONObject.getString("messageKey"), jSONObject.getString("defaultMessageText")));
        }
        baseResponse.setError(arrayList);
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<LoadFAQListResponse, List<BaseError>> parseFAQList(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<FirmwareDescription, List<BaseError>> parseFirmwareDescription(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void parseFirmwareDescription(JSONObject jSONObject, FirmwareDescription firmwareDescription) throws JSONException {
        if (!jSONObject.isNull("id")) {
            firmwareDescription.id = jSONObject.optLong("id", -1L);
        }
        firmwareDescription.deviceType = jSONObject.optString("deviceType", "");
        if (!jSONObject.isNull("versionMajor")) {
            firmwareDescription.versionMajor = jSONObject.optInt("versionMajor", -1);
        }
        if (!jSONObject.isNull("versionMinor")) {
            firmwareDescription.versionMinor = jSONObject.optInt("versionMinor", -1);
        }
        if (!jSONObject.isNull("versionBugFix")) {
            firmwareDescription.versionBugFix = jSONObject.optInt("versionBugFix", -1);
        }
        firmwareDescription.versionRevision = jSONObject.optString("versionRevision", "");
        firmwareDescription.description = jSONObject.optString("description", "");
        if (!jSONObject.isNull("uploadDate")) {
            firmwareDescription.uploadDate = jSONObject.optLong("uploadDate", 0L);
        }
        if (!jSONObject.isNull("updateDate")) {
            firmwareDescription.updateDate = jSONObject.optLong("updateDate", 0L);
        }
        if (!jSONObject.isNull("size")) {
            firmwareDescription.size = jSONObject.optLong("size", 0L);
        }
        if (!jSONObject.isNull("tftpIP")) {
            firmwareDescription.ipAddress = jSONObject.optString("tftpIP", null);
        }
    }

    private static void parseFlickrPhotoMetadata(JSONObject jSONObject, FlickrPhotoMetadata flickrPhotoMetadata) throws JSONException {
        flickrPhotoMetadata.id = jSONObject.getString("id");
        flickrPhotoMetadata.urlZ = jSONObject.getString("url_z");
    }

    public static GetFlickrPublicPhotosResponse parseFlickrPublicPhotos(String string2) {
        GetFlickrPublicPhotosResponse getFlickrPublicPhotosResponse;
        block7: {
            block6: {
                getFlickrPublicPhotosResponse = new GetFlickrPublicPhotosResponse();
                string2 = new JSONObject(string2);
                getFlickrPublicPhotosResponse.stat = string2.getString("stat");
                if (!getFlickrPublicPhotosResponse.isSuccessful()) break block6;
                string2 = string2.getJSONObject("photos").getJSONArray("photo");
                int n = 0;
                do {
                    if (n < string2.length()) {
                        FlickrPhotoMetadata flickrPhotoMetadata = new FlickrPhotoMetadata();
                        JSONParser.parseFlickrPhotoMetadata(string2.getJSONObject(n), flickrPhotoMetadata);
                        getFlickrPublicPhotosResponse.photos.add(flickrPhotoMetadata);
                        ++n;
                        continue;
                    }
                    break block7;
                    break;
                } while (true);
            }
            try {
                getFlickrPublicPhotosResponse.code = string2.getInt("code");
                getFlickrPublicPhotosResponse.message = string2.getString("message");
            }
            catch (JSONException jSONException) {
                getFlickrPublicPhotosResponse.setError(jSONException.getLocalizedMessage());
                return getFlickrPublicPhotosResponse;
            }
        }
        return getFlickrPublicPhotosResponse;
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<ForgotPasswordResponse, List<BaseError>> parseForgotPassword(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<Goal, List<BaseError>> parseGetCurrentGoal(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<Profile, List<BaseError>> parseGetProfileInfo(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<Settings, List<BaseError>> parseGetSettings(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static Goal parseGoal(JSONObject jSONObject) throws JSONException {
        Goal goal = new Goal();
        if (!jSONObject.isNull("targetPerWeek")) {
            goal.targetPerWeek = Float.valueOf(MetricUtils.convertWeightFromHectograms((float)jSONObject.optDouble("targetPerWeek")));
        }
        if (!jSONObject.isNull("type")) {
            goal.type = jSONObject.optString("type");
        }
        if (!jSONObject.isNull("startDate")) {
            goal.startDate = jSONObject.optLong("startDate");
        }
        if (!jSONObject.isNull("target")) {
            goal.target = Float.valueOf(MetricUtils.convertWeightFromHectograms((float)jSONObject.optDouble("target")));
        }
        if (!jSONObject.isNull("excellentBoundary")) {
            goal.excellentBoundary = jSONObject.optInt("excellentBoundary");
        }
        if (!jSONObject.isNull("neutralBoundary")) {
            goal.neutralBoundary = jSONObject.optInt("neutralBoundary");
        }
        if (!jSONObject.isNull("positiveBoundary")) {
            goal.positiveBoundary = jSONObject.optInt("positiveBoundary");
        }
        return goal;
    }

    public static BaseResponse<LoginResponse, List<BaseError>> parseLogin(String string2) {
        BaseResponse<LoginResponse, List<BaseError>> baseResponse = new BaseResponse<LoginResponse, List<BaseError>>();
        try {
            string2 = new JSONObject(string2);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setExpiresIn(string2.optLong("expires_in"));
            loginResponse.setAccessToken(string2.optString("access_token"));
            loginResponse.setTokenType(string2.optString("token_type"));
            loginResponse.setRefreshToken(string2.optString("refresh_token"));
            loginResponse.setScope(string2.optString("scope"));
            loginResponse.setUserId(string2.optString("user_id"));
            baseResponse.setData(loginResponse);
            baseResponse.setStatus(BaseResponse.Status.SUCCESS);
            return baseResponse;
        }
        catch (JSONException jSONException) {
            JSONParser.setParseErrorResult(baseResponse, jSONException);
            return baseResponse;
        }
    }

    public static List<BaseError> parseLoginFailed(String string2) {
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        try {
            string2 = new JSONObject(string2);
            arrayList.add(new BaseError(string2.optString("error"), string2.optString("error_description")));
            return arrayList;
        }
        catch (JSONException jSONException) {
            arrayList.add(new BaseError("", jSONException.getLocalizedMessage()));
            return arrayList;
        }
    }

    public static BaseResponse<LogoutResponse, List<BaseError>> parseLogout(String string2) {
        BaseResponse<LogoutResponse, List<BaseError>> baseResponse = new BaseResponse<LogoutResponse, List<BaseError>>();
        try {
            string2 = new JSONObject(string2);
            LogoutResponse logoutResponse = new LogoutResponse();
            logoutResponse.setStatus(string2.optString("status"));
            logoutResponse.setAuthToken(string2.optString("authToken"));
            baseResponse.setData(logoutResponse);
            baseResponse.setStatus(BaseResponse.Status.SUCCESS);
            return baseResponse;
        }
        catch (JSONException jSONException) {
            JSONParser.setParseErrorResult(baseResponse, jSONException);
            return baseResponse;
        }
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<PregnancyData[], List<BaseError>> parsePregnancyMode(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static void parseProfile(JSONObject jSONObject, Profile profile) throws JSONException {
        profile.setEmail(jSONObject.optString("email", ""));
        profile.firstName = jSONObject.optString("firstName", "");
        profile.lastName = jSONObject.optString("lastName", "");
        if (!jSONObject.isNull("phone")) {
            profile.phone = jSONObject.optString("phone", "");
        }
        if (!jSONObject.isNull("height")) {
            profile.height = Float.valueOf((float)jSONObject.optDouble("height", 0.0));
        }
        if (!jSONObject.isNull("heightUnit")) {
            profile.heightUnit = Constants.HeightUnit.string2Int(jSONObject.optString("heightUnit", ""));
        }
        if (!jSONObject.isNull("weight")) {
            profile.weight = Float.valueOf((float)jSONObject.optDouble("weight", 0.0));
        }
        if (!jSONObject.isNull("weightUnit")) {
            profile.weightUnit = Constants.WeightUnit.string2Int(jSONObject.optString("weightUnit", ""));
        }
        if (!jSONObject.isNull("dob")) {
            profile.dob = new Date(jSONObject.optLong("dob", 0L));
        }
        if (!jSONObject.isNull("sex")) {
            profile.gender = Constants.Gender.string2Int(jSONObject.optString("sex", ""));
        }
        if (!jSONObject.isNull("address")) {
            profile.address = jSONObject.optString("address", "");
        }
        if (!jSONObject.isNull("latitude")) {
            profile.latitude = (int)(jSONObject.optDouble("latitude", 0.0) * 1000000.0);
        }
        if (!jSONObject.isNull("longitude")) {
            profile.longitude = (int)(jSONObject.optDouble("longitude", 0.0) * 1000000.0);
        }
        if (!jSONObject.isNull("zipCode")) {
            profile.zip = jSONObject.optString("zipCode", "");
        }
        if (!jSONObject.isNull("state")) {
            profile.state = jSONObject.optString("state", "");
        }
        if (!jSONObject.isNull("country")) {
            profile.country = jSONObject.optString("country", "");
        }
        if (!jSONObject.isNull("locale")) {
            profile.locale = jSONObject.optString("locale", "");
        }
        if (!jSONObject.isNull("doctorName")) {
            profile.doctorName = jSONObject.optString("doctorName", "");
        }
        if (!jSONObject.isNull("doctorEmail")) {
            profile.doctorEmail = jSONObject.optString("doctorEmail", "");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static QardioBaseSettings parseQardioBaseSettings(String string2) {
        QardioBaseSettings qardioBaseSettings = new QardioBaseSettings();
        try {
            Object object = new JSONObject(string2);
            string2 = object.has("z") ? object.getString("z") : "0";
            object = object.has("haptic") ? object.getString("haptic") : "0";
            qardioBaseSettings.enableHaptic = ((String)object).equals("1");
            qardioBaseSettings.enableComposition = string2.equals("1");
            return qardioBaseSettings;
        }
        catch (JSONException jSONException) {
            Timber.e(jSONException, "Could parse json for QardioBase Settings", new Object[0]);
            return null;
        }
    }

    /*
     * Exception decompiling
     */
    public static void parseReminderDeletion(String var0, BaseResponse<?, List<BaseError>> var1_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static BaseResponse<String, List<BaseError>> parseRemoveDeviceAssociation(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    public static BaseResponse<String, List<BaseError>> parseSaveCurrentGoal(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    public static BaseResponse<String, List<BaseError>> parseSaveMeasurements(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<Long, List<BaseError>> parseSetPregnancyModeResponse(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<String, List<BaseError>> parseSetProfileInfo(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<String, List<BaseError>> parseSetSettings(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void parseSettings(String arrstring, Settings settings) throws JSONException {
        int n = 0;
        int n2 = (arrstring = new JSONObject((String)arrstring)).optInt("pause", -1);
        if (n2 != -1) {
            settings.pauseSize = n2;
        }
        if ((n2 = arrstring.optInt("numberMeasurements", -1)) != -1) {
            settings.measurementsNumber = n2;
        }
        boolean bl = arrstring.optInt("photoFeed", 0) != 0;
        settings.showPhoto = bl;
        settings.allowLocation = null;
        settings.albums = 0;
        arrstring = arrstring.optString("selectedAlbums", "").split(",");
        n2 = arrstring.length;
        do {
            if (n >= n2) {
                settings.allowBpExportSHealth = null;
                settings.allowBpImportSHealth = null;
                settings.allowWeightExportSHealth = null;
                settings.allowWeightImportSHealth = null;
                settings.allowIntegrationGoogleFit = null;
                settings.allowNotifications = null;
                return;
            }
            String string2 = arrstring[n];
            if ("android_photos".equals(string2)) {
                settings.albums = settings.albums | 2;
            } else if ("Qardio Flickr".equals(string2)) {
                settings.albums = settings.albums | 1;
            }
            ++n;
        } while (true);
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<StatisticResponse, List<BaseError>> parseStatistic(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static void parseStatusAndErrors(String var0, BaseResponse<?, List<BaseError>> var1_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<TooltipsResponse, List<BaseError>> parseTooltipsList(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static BaseResponse<String, List<BaseError>> parseUpdateStatistic(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    public static BaseResponse<String, List<BaseError>> parseUploadDeviceAssociation(String string2) {
        return JSONParser.parseDataAsString(string2);
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<List<DeviceAssociation>, List<BaseError>> parseUserActiveDeviceAssociations(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void parseWeightMeasurement(JSONObject jSONObject, WeightMeasurement weightMeasurement) throws JSONException {
        weightMeasurement.weight = JSONParser.getNotNullData(jSONObject, "data1", Float.valueOf(0.0f));
        weightMeasurement.z = JSONParser.getNotNullData(jSONObject, "data2", 0);
        if (!jSONObject.isNull("fullName")) {
            weightMeasurement.fullName = jSONObject.optString("fullName", null);
        }
        weightMeasurement.deviceId = !jSONObject.isNull("deviceId") ? jSONObject.optString("deviceId", "") : "";
        JSONObject jSONObject2 = new JSONObject(jSONObject.optString("data3", "{}"));
        weightMeasurement.measurementId = jSONObject2.optString("id", null);
        weightMeasurement.height = jSONObject2.optInt("height", 0);
        weightMeasurement.battery = jSONObject2.optInt("battery", 0);
        weightMeasurement.sex = jSONObject2.optString("sex", null);
        weightMeasurement.age = jSONObject2.optInt("age", 0);
        try {
            weightMeasurement.qbUserId = Long.parseLong(jSONObject2.optString("userid", "0"));
        }
        catch (NumberFormatException numberFormatException) {}
        weightMeasurement.user = jSONObject2.optString("user", "");
        weightMeasurement.fat = jSONObject2.optInt("fat", 0);
        weightMeasurement.water = jSONObject2.optInt("tbw", 0);
        weightMeasurement.bone = jSONObject2.optInt("bmc", 0);
        weightMeasurement.muscle = jSONObject2.optInt("mt", 0);
        if (!jSONObject.isNull("source")) {
            weightMeasurement.source = jSONObject2.optString("source", "BLEBASE");
        }
        weightMeasurement.note = !jSONObject.isNull("note") ? jSONObject.optString("note", "") : "";
        weightMeasurement.memberName = jSONObject.optString("memberName", "");
        weightMeasurement.visitor = jSONObject.optBoolean("visitor", false);
        weightMeasurement.measureDate = new Date(jSONObject.optLong("time", 0L));
        weightMeasurement.timezone = jSONObject.optString("timezone", "");
        weightMeasurement.measurementSource = jSONObject.optInt("source");
        if (weightMeasurement.fat <= 0) {
            weightMeasurement.fat = null;
        }
        if (weightMeasurement.muscle <= 0) {
            weightMeasurement.muscle = null;
        }
        if (weightMeasurement.water <= 0) {
            weightMeasurement.water = null;
        }
        if (weightMeasurement.bone <= 0) {
            weightMeasurement.bone = null;
        }
        if (weightMeasurement.height <= 0) {
            weightMeasurement.height = null;
        }
    }

    /*
     * Exception decompiling
     */
    public static BaseResponse<WeightMeasurementsResponse, List<BaseError>> parseWeightMeasurements(String var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static void setParseErrorResult(BaseResponse<?, List<BaseError>> baseResponse, Exception exception) {
        baseResponse.setStatus(BaseResponse.Status.FAILURE);
        baseResponse.setData(null);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        arrayList.add(new BaseError("", exception.getLocalizedMessage()));
        baseResponse.setError(arrayList);
    }

    private static void setParseErrorResult(BaseResponse<?, List<BaseError>> baseResponse, JSONException jSONException) {
        baseResponse.setStatus(BaseResponse.Status.FAILURE);
        baseResponse.setData(null);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        arrayList.add(new BaseError("", jSONException.getLocalizedMessage()));
        baseResponse.setError(arrayList);
    }

    private static void setUnknownStatusResult(BaseResponse<?, List<BaseError>> baseResponse) {
        baseResponse.setStatus(BaseResponse.Status.UNKNOWN);
        baseResponse.setData(null);
        ArrayList<BaseError> arrayList = new ArrayList<BaseError>(1);
        arrayList.add(new BaseError("Unknown status", "Unknown status"));
        baseResponse.setError(arrayList);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String storeSettings(Settings object) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (((Settings)object).measurementsNumber != null) {
                jSONObject.put("numberMeasurements", ((Settings)object).measurementsNumber.intValue());
            }
            if (((Settings)object).pauseSize != null) {
                jSONObject.put("pause", ((Settings)object).pauseSize.intValue());
            }
            if (((Settings)object).showPhoto != null) {
                int n = ((Settings)object).showPhoto != false ? 1 : 0;
                jSONObject.put("photoFeed", n);
            }
            if (((Settings)object).albums == null) return jSONObject.toString();
            StringBuilder stringBuilder = new StringBuilder();
            if (((Settings)object).useFlickr()) {
                stringBuilder.append("Qardio Flickr");
            }
            if (((Settings)object).usePhotosFromDevice()) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append("android_photos");
            }
            jSONObject.put("selectedAlbums", (Object)stringBuilder.toString());
            return jSONObject.toString();
        }
        catch (JSONException jSONException) {
            Timber.e(jSONException);
            return null;
        }
    }

    public static String storeWeightMeasurement(WeightMeasurement object) {
        try {
            JSONObject jSONObject = new JSONObject();
            if (((WeightMeasurement)object).height != null) {
                jSONObject.put("height", ((WeightMeasurement)object).height.intValue());
            }
            if (((WeightMeasurement)object).sex != null) {
                jSONObject.put("sex", (Object)((WeightMeasurement)object).sex);
            }
            if (((WeightMeasurement)object).battery != null) {
                jSONObject.put("battery", ((WeightMeasurement)object).battery.intValue());
            }
            if (((WeightMeasurement)object).age != null) {
                jSONObject.put("age", ((WeightMeasurement)object).age.intValue());
            }
            if (((WeightMeasurement)object).fat != null) {
                jSONObject.put("fat", ((WeightMeasurement)object).fat.intValue());
            }
            if (((WeightMeasurement)object).muscle != null) {
                jSONObject.put("mt", ((WeightMeasurement)object).muscle.intValue());
            }
            if (((WeightMeasurement)object).water != null) {
                jSONObject.put("tbw", ((WeightMeasurement)object).water.intValue());
            }
            if (((WeightMeasurement)object).bone != null) {
                jSONObject.put("bmc", ((WeightMeasurement)object).bone.intValue());
            }
            if (((WeightMeasurement)object).height != null && ((WeightMeasurement)object).height > 0 && ((WeightMeasurement)object).weight != null && ((WeightMeasurement)object).weight.floatValue() > 0.0f) {
                jSONObject.put("bmi", (Object)Float.valueOf(QardioBaseUtils.bmi(((WeightMeasurement)object).weight.floatValue(), ((WeightMeasurement)object).height)).toString());
            }
            if (((WeightMeasurement)object).z != null) {
                jSONObject.put("z", ((WeightMeasurement)object).z.intValue());
            }
            if (((WeightMeasurement)object).source != null) {
                jSONObject.put("source", (Object)((WeightMeasurement)object).source);
            }
            if (((WeightMeasurement)object).measurementId != null) {
                jSONObject.put("id", (Object)((WeightMeasurement)object).measurementId);
            }
            if (((WeightMeasurement)object).qbUserId != null) {
                jSONObject.put("userid", (Object)((WeightMeasurement)object).qbUserId);
            }
            if (((WeightMeasurement)object).user != null) {
                jSONObject.put("user", (Object)((WeightMeasurement)object).user);
            }
            if (((WeightMeasurement)object).measurementSource != null) {
                jSONObject.put("source", (Object)((WeightMeasurement)object).source);
            }
            if (((WeightMeasurement)object).firmwareVersion != null) {
                jSONObject.put("fw", (Object)((WeightMeasurement)object).firmwareVersion);
            }
            object = jSONObject.toString();
            return object;
        }
        catch (JSONException jSONException) {
            Timber.e(jSONException);
            return null;
        }
    }
}

