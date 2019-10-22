/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.app.ActivityManager$RunningAppProcessInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.crashlytics.android.core;

import android.app.ActivityManager;
import android.os.Build;
import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.TrimmedThrowableData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.common.IdManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SessionProtobufHelper {
    private static final ByteString SIGNAL_DEFAULT_BYTE_STRING = ByteString.copyFromUtf8("0");
    private static final ByteString UNITY_PLATFORM_BYTE_STRING = ByteString.copyFromUtf8("Unity");

    private static int getBinaryImageSize(ByteString byteString, ByteString byteString2) {
        int n;
        int n2 = n = 0 + CodedOutputStream.computeUInt64Size(1, 0L) + CodedOutputStream.computeUInt64Size(2, 0L) + CodedOutputStream.computeBytesSize(3, byteString);
        if (byteString2 != null) {
            n2 = n + CodedOutputStream.computeBytesSize(4, byteString2);
        }
        return n2;
    }

    private static int getDeviceIdentifierSize(IdManager.DeviceIdentifierType deviceIdentifierType, String string2) {
        return CodedOutputStream.computeEnumSize(1, deviceIdentifierType.protobufIndex) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string2));
    }

    private static int getEventAppCustomAttributeSize(String string2, String string3) {
        int n = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(string2));
        string2 = string3;
        if (string3 == null) {
            string2 = "";
        }
        return n + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string2));
    }

    private static int getEventAppExecutionExceptionSize(TrimmedThrowableData trimmedThrowableData, int n, int n2) {
        int n3;
        block7: {
            int n4;
            block6: {
                n4 = 0 + CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(trimmedThrowableData.className));
                StackTraceElement[] arrstackTraceElement = trimmedThrowableData.localizedMessage;
                n3 = n4;
                if (arrstackTraceElement != null) {
                    n3 = n4 + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8((String)arrstackTraceElement));
                }
                arrstackTraceElement = trimmedThrowableData.stacktrace;
                int n5 = arrstackTraceElement.length;
                for (n4 = 0; n4 < n5; ++n4) {
                    int n6 = SessionProtobufHelper.getFrameSize(arrstackTraceElement[n4], true);
                    n3 += CodedOutputStream.computeTagSize(4) + CodedOutputStream.computeRawVarint32Size(n6) + n6;
                }
                trimmedThrowableData = trimmedThrowableData.cause;
                n4 = n3;
                if (trimmedThrowableData == null) break block6;
                if (n >= n2) break block7;
                n = SessionProtobufHelper.getEventAppExecutionExceptionSize(trimmedThrowableData, n + 1, n2);
                n4 = n3 + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(n) + n);
            }
            return n4;
        }
        n = 0;
        while (trimmedThrowableData != null) {
            trimmedThrowableData = trimmedThrowableData.cause;
            ++n;
        }
        return n3 + CodedOutputStream.computeUInt32Size(7, n);
    }

    private static int getEventAppExecutionSignalSize() {
        return 0 + CodedOutputStream.computeBytesSize(1, SIGNAL_DEFAULT_BYTE_STRING) + CodedOutputStream.computeBytesSize(2, SIGNAL_DEFAULT_BYTE_STRING) + CodedOutputStream.computeUInt64Size(3, 0L);
    }

    private static int getEventAppExecutionSize(TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2) {
        int n2;
        int n3;
        int n4 = SessionProtobufHelper.getThreadSize(thread, arrstackTraceElement, 4, true);
        n4 = 0 + (CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(n4) + n4);
        int n5 = arrthread.length;
        for (n2 = 0; n2 < n5; ++n2) {
            n3 = SessionProtobufHelper.getThreadSize(arrthread[n2], list.get(n2), 0, false);
            n4 += CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(n3) + n3;
        }
        n = SessionProtobufHelper.getEventAppExecutionExceptionSize(trimmedThrowableData, 1, n);
        n2 = CodedOutputStream.computeTagSize(2);
        n5 = CodedOutputStream.computeRawVarint32Size(n);
        n3 = SessionProtobufHelper.getEventAppExecutionSignalSize();
        int n6 = CodedOutputStream.computeTagSize(3);
        int n7 = CodedOutputStream.computeRawVarint32Size(n3);
        int n8 = SessionProtobufHelper.getBinaryImageSize(byteString, byteString2);
        return n4 + (n2 + n5 + n) + (n6 + n7 + n3) + (CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(n8) + n8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getEventAppSize(TrimmedThrowableData iterator, Thread entry, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2) {
        int n3;
        n = SessionProtobufHelper.getEventAppExecutionSize(iterator, entry, arrstackTraceElement, arrthread, list, n, byteString, byteString2);
        n = n3 = 0 + (CodedOutputStream.computeTagSize(1) + CodedOutputStream.computeRawVarint32Size(n) + n);
        if (map != null) {
            iterator = map.entrySet().iterator();
            do {
                n = n3;
                if (!iterator.hasNext()) break;
                entry = iterator.next();
                n = SessionProtobufHelper.getEventAppCustomAttributeSize(entry.getKey(), entry.getValue());
                n3 += CodedOutputStream.computeTagSize(2) + CodedOutputStream.computeRawVarint32Size(n) + n;
            } while (true);
        }
        n3 = n;
        if (runningAppProcessInfo != null) {
            boolean bl = runningAppProcessInfo.importance != 100;
            n3 = n + CodedOutputStream.computeBoolSize(3, bl);
        }
        return n3 + CodedOutputStream.computeUInt32Size(4, n2);
    }

    private static int getEventDeviceSize(Float f, int n, boolean bl, int n2, long l, long l2) {
        int n3 = 0;
        if (f != null) {
            n3 = 0 + CodedOutputStream.computeFloatSize(1, f.floatValue());
        }
        return n3 + CodedOutputStream.computeSInt32Size(2, n) + CodedOutputStream.computeBoolSize(3, bl) + CodedOutputStream.computeUInt32Size(4, n2) + CodedOutputStream.computeUInt64Size(5, l) + CodedOutputStream.computeUInt64Size(6, l2);
    }

    private static int getEventLogSize(ByteString byteString) {
        return CodedOutputStream.computeBytesSize(1, byteString);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getFrameSize(StackTraceElement stackTraceElement, boolean bl) {
        int n;
        int n2 = 2;
        int n3 = stackTraceElement.isNativeMethod() ? 0 + CodedOutputStream.computeUInt64Size(1, Math.max(stackTraceElement.getLineNumber(), 0)) : 0 + CodedOutputStream.computeUInt64Size(1, 0L);
        n3 = n = n3 + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            n3 = n + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        n = n3;
        if (!stackTraceElement.isNativeMethod()) {
            n = n3;
            if (stackTraceElement.getLineNumber() > 0) {
                n = n3 + CodedOutputStream.computeUInt64Size(4, stackTraceElement.getLineNumber());
            }
        }
        if (bl) {
            n3 = n2;
            return n + CodedOutputStream.computeUInt32Size(5, n3);
        }
        n3 = 0;
        return n + CodedOutputStream.computeUInt32Size(5, n3);
    }

    private static int getSessionAppOrgSize(ByteString byteString) {
        return 0 + CodedOutputStream.computeBytesSize(1, byteString);
    }

    private static int getSessionAppSize(ByteString byteString, ByteString byteString2, ByteString byteString3, ByteString byteString4, ByteString byteString5, int n, ByteString byteString6) {
        int n2 = CodedOutputStream.computeBytesSize(1, byteString);
        int n3 = CodedOutputStream.computeBytesSize(2, byteString3);
        int n4 = CodedOutputStream.computeBytesSize(3, byteString4);
        int n5 = SessionProtobufHelper.getSessionAppOrgSize(byteString2);
        n2 = n3 = 0 + n2 + n3 + n4 + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(n5) + n5) + CodedOutputStream.computeBytesSize(6, byteString5);
        if (byteString6 != null) {
            n2 = n3 + CodedOutputStream.computeBytesSize(8, UNITY_PLATFORM_BYTE_STRING) + CodedOutputStream.computeBytesSize(9, byteString6);
        }
        return n2 + CodedOutputStream.computeEnumSize(10, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int getSessionDeviceSize(int n, ByteString iterator, ByteString entry, int n2, long l, long l2, boolean bl, Map<IdManager.DeviceIdentifierType, String> map, int n3, ByteString byteString, ByteString byteString2) {
        int n4 = CodedOutputStream.computeBytesSize(1, iterator);
        int n5 = CodedOutputStream.computeEnumSize(3, n);
        n = entry == null ? 0 : CodedOutputStream.computeBytesSize(4, entry);
        n2 = n = 0 + n4 + n5 + n + CodedOutputStream.computeUInt32Size(5, n2) + CodedOutputStream.computeUInt64Size(6, l) + CodedOutputStream.computeUInt64Size(7, l2) + CodedOutputStream.computeBoolSize(10, bl);
        if (map != null) {
            iterator = map.entrySet().iterator();
            do {
                n2 = n;
                if (!iterator.hasNext()) break;
                entry = iterator.next();
                n2 = SessionProtobufHelper.getDeviceIdentifierSize(entry.getKey(), entry.getValue());
                n += CodedOutputStream.computeTagSize(11) + CodedOutputStream.computeRawVarint32Size(n2) + n2;
            } while (true);
        }
        n4 = CodedOutputStream.computeUInt32Size(12, n3);
        n = byteString == null ? 0 : CodedOutputStream.computeBytesSize(13, byteString);
        if (byteString2 == null) {
            n3 = 0;
            return n2 + n4 + n + n3;
        }
        n3 = CodedOutputStream.computeBytesSize(14, byteString2);
        return n2 + n4 + n + n3;
    }

    private static int getSessionEventSize(long l, String string2, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2, ByteString byteString, ByteString byteString2, Float f, int n3, boolean bl, long l2, long l3, ByteString byteString3) {
        int n4 = CodedOutputStream.computeUInt64Size(1, l);
        int n5 = CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string2));
        n = SessionProtobufHelper.getEventAppSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2, map, runningAppProcessInfo, n2);
        int n6 = CodedOutputStream.computeTagSize(3);
        int n7 = CodedOutputStream.computeRawVarint32Size(n);
        n2 = SessionProtobufHelper.getEventDeviceSize(f, n3, bl, n2, l2, l3);
        n = n2 = 0 + n4 + n5 + (n6 + n7 + n) + (CodedOutputStream.computeTagSize(5) + CodedOutputStream.computeRawVarint32Size(n2) + n2);
        if (byteString3 != null) {
            n = SessionProtobufHelper.getEventLogSize(byteString3);
            n = n2 + (CodedOutputStream.computeTagSize(6) + CodedOutputStream.computeRawVarint32Size(n) + n);
        }
        return n;
    }

    private static int getSessionOSSize(ByteString byteString, ByteString byteString2, boolean bl) {
        return 0 + CodedOutputStream.computeEnumSize(1, 3) + CodedOutputStream.computeBytesSize(2, byteString) + CodedOutputStream.computeBytesSize(3, byteString2) + CodedOutputStream.computeBoolSize(4, bl);
    }

    private static int getThreadSize(Thread thread, StackTraceElement[] arrstackTraceElement, int n, boolean bl) {
        int n2 = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(thread.getName())) + CodedOutputStream.computeUInt32Size(2, n);
        int n3 = arrstackTraceElement.length;
        for (n = 0; n < n3; ++n) {
            int n4 = SessionProtobufHelper.getFrameSize(arrstackTraceElement[n], bl);
            n2 += CodedOutputStream.computeTagSize(3) + CodedOutputStream.computeRawVarint32Size(n4) + n4;
        }
        return n2;
    }

    private static ByteString stringToByteString(String string2) {
        if (string2 == null) {
            return null;
        }
        return ByteString.copyFromUtf8(string2);
    }

    public static void writeBeginSession(CodedOutputStream codedOutputStream, String string2, String string3, long l) throws Exception {
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(string3));
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(string2));
        codedOutputStream.writeUInt64(3, l);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void writeFrame(CodedOutputStream codedOutputStream, int n, StackTraceElement stackTraceElement, boolean bl) throws Exception {
        int n2 = 4;
        codedOutputStream.writeTag(n, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getFrameSize(stackTraceElement, bl));
        if (stackTraceElement.isNativeMethod()) {
            codedOutputStream.writeUInt64(1, Math.max(stackTraceElement.getLineNumber(), 0));
        } else {
            codedOutputStream.writeUInt64(1, 0L);
        }
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()));
        if (stackTraceElement.getFileName() != null) {
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(stackTraceElement.getFileName()));
        }
        if (!stackTraceElement.isNativeMethod() && stackTraceElement.getLineNumber() > 0) {
            codedOutputStream.writeUInt64(4, stackTraceElement.getLineNumber());
        }
        n = bl ? n2 : 0;
        codedOutputStream.writeUInt32(5, n);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void writeSessionApp(CodedOutputStream codedOutputStream, String object, String object2, String object3, String object4, String object5, int n, String string2) throws Exception {
        ByteString byteString = ByteString.copyFromUtf8((String)object);
        object2 = ByteString.copyFromUtf8((String)object2);
        object3 = ByteString.copyFromUtf8((String)object3);
        object4 = ByteString.copyFromUtf8((String)object4);
        object5 = ByteString.copyFromUtf8((String)object5);
        object = string2 != null ? ByteString.copyFromUtf8(string2) : null;
        codedOutputStream.writeTag(7, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionAppSize(byteString, (ByteString)object2, (ByteString)object3, (ByteString)object4, (ByteString)object5, n, (ByteString)object));
        codedOutputStream.writeBytes(1, byteString);
        codedOutputStream.writeBytes(2, (ByteString)object3);
        codedOutputStream.writeBytes(3, (ByteString)object4);
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionAppOrgSize((ByteString)object2));
        codedOutputStream.writeBytes(1, (ByteString)object2);
        codedOutputStream.writeBytes(6, (ByteString)object5);
        if (object != null) {
            codedOutputStream.writeBytes(8, UNITY_PLATFORM_BYTE_STRING);
            codedOutputStream.writeBytes(9, (ByteString)object);
        }
        codedOutputStream.writeEnum(10, n);
    }

    /*
     * WARNING - void declaration
     */
    public static void writeSessionDevice(CodedOutputStream codedOutputStream, String iterator, int n, String object, int n2, long l, long l2, boolean bl, Map<IdManager.DeviceIdentifierType, String> object22, int n3, String object3, String string2) throws Exception {
        void var13_13;
        ByteString byteString;
        void var11_11;
        iterator = ByteString.copyFromUtf8(iterator);
        ByteString byteString2 = SessionProtobufHelper.stringToByteString((String)object);
        object = SessionProtobufHelper.stringToByteString((String)var13_13);
        byteString = SessionProtobufHelper.stringToByteString((String)((Object)byteString));
        codedOutputStream.writeTag(9, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionDeviceSize(n, (ByteString)((Object)iterator), byteString2, n2, l, l2, bl, (Map<IdManager.DeviceIdentifierType, String>)object22, (int)var11_11, byteString, (ByteString)object));
        codedOutputStream.writeBytes(1, (ByteString)((Object)iterator));
        codedOutputStream.writeEnum(3, n);
        codedOutputStream.writeBytes(4, byteString2);
        codedOutputStream.writeUInt32(5, n2);
        codedOutputStream.writeUInt64(6, l);
        codedOutputStream.writeUInt64(7, l2);
        codedOutputStream.writeBool(10, bl);
        for (Map.Entry entry : object22.entrySet()) {
            codedOutputStream.writeTag(11, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getDeviceIdentifierSize((IdManager.DeviceIdentifierType)((Object)entry.getKey()), (String)entry.getValue()));
            codedOutputStream.writeEnum(1, ((IdManager.DeviceIdentifierType)entry.getKey()).protobufIndex);
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String)entry.getValue()));
        }
        codedOutputStream.writeUInt32(12, (int)var11_11);
        if (byteString != null) {
            codedOutputStream.writeBytes(13, byteString);
        }
        if (object != null) {
            codedOutputStream.writeBytes(14, (ByteString)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void writeSessionEvent(CodedOutputStream codedOutputStream, long l, String string2, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, Map<String, String> map, LogFileManager logFileManager, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n, String object, String object2, Float f, int n2, boolean bl, long l2, long l3) throws Exception {
        ByteString byteString = ByteString.copyFromUtf8((String)object);
        object = object2 == null ? null : ByteString.copyFromUtf8(((String)object2).replace("-", ""));
        object2 = logFileManager.getByteStringForLog();
        if (object2 == null) {
            Fabric.getLogger().d("CrashlyticsCore", "No log data to include with this event.");
        }
        logFileManager.clearLog();
        codedOutputStream.writeTag(10, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionEventSize(l, string2, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, 8, map, runningAppProcessInfo, n, byteString, (ByteString)object, f, n2, bl, l2, l3, (ByteString)object2));
        codedOutputStream.writeUInt64(1, l);
        codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(string2));
        SessionProtobufHelper.writeSessionEventApp(codedOutputStream, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, 8, byteString, (ByteString)object, map, runningAppProcessInfo, n);
        SessionProtobufHelper.writeSessionEventDevice(codedOutputStream, f, n2, bl, n, l2, l3);
        SessionProtobufHelper.writeSessionEventLog(codedOutputStream, (ByteString)object2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void writeSessionEventApp(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2, Map<String, String> map, ActivityManager.RunningAppProcessInfo runningAppProcessInfo, int n2) throws Exception {
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2, map, runningAppProcessInfo, n2));
        SessionProtobufHelper.writeSessionEventAppExecution(codedOutputStream, trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2);
        if (map != null && !map.isEmpty()) {
            SessionProtobufHelper.writeSessionEventAppCustomAttributes(codedOutputStream, map);
        }
        if (runningAppProcessInfo != null) {
            boolean bl = runningAppProcessInfo.importance != 100;
            codedOutputStream.writeBool(3, bl);
        }
        codedOutputStream.writeUInt32(4, n2);
    }

    /*
     * WARNING - void declaration
     */
    private static void writeSessionEventAppCustomAttributes(CodedOutputStream codedOutputStream, Map<String, String> object2) throws Exception {
        for (Map.Entry entry : object2.entrySet()) {
            void var1_6;
            String string2;
            codedOutputStream.writeTag(2, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppCustomAttributeSize((String)entry.getKey(), (String)entry.getValue()));
            codedOutputStream.writeBytes(1, ByteString.copyFromUtf8((String)entry.getKey()));
            String string3 = string2 = (String)entry.getValue();
            if (string2 == null) {
                String string4 = "";
            }
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8((String)var1_6));
        }
    }

    private static void writeSessionEventAppExecution(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, Thread thread, StackTraceElement[] arrstackTraceElement, Thread[] arrthread, List<StackTraceElement[]> list, int n, ByteString byteString, ByteString byteString2) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionSize(trimmedThrowableData, thread, arrstackTraceElement, arrthread, list, n, byteString, byteString2));
        SessionProtobufHelper.writeThread(codedOutputStream, thread, arrstackTraceElement, 4, true);
        int n2 = arrthread.length;
        for (int i = 0; i < n2; ++i) {
            SessionProtobufHelper.writeThread(codedOutputStream, arrthread[i], list.get(i), 0, false);
        }
        SessionProtobufHelper.writeSessionEventAppExecutionException(codedOutputStream, trimmedThrowableData, 1, n, 2);
        codedOutputStream.writeTag(3, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionSignalSize());
        codedOutputStream.writeBytes(1, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeBytes(2, SIGNAL_DEFAULT_BYTE_STRING);
        codedOutputStream.writeUInt64(3, 0L);
        codedOutputStream.writeTag(4, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getBinaryImageSize(byteString, byteString2));
        codedOutputStream.writeUInt64(1, 0L);
        codedOutputStream.writeUInt64(2, 0L);
        codedOutputStream.writeBytes(3, byteString);
        if (byteString2 != null) {
            codedOutputStream.writeBytes(4, byteString2);
        }
    }

    private static void writeSessionEventAppExecutionException(CodedOutputStream codedOutputStream, TrimmedThrowableData trimmedThrowableData, int n, int n2, int n3) throws Exception {
        block7: {
            block6: {
                codedOutputStream.writeTag(n3, 2);
                codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventAppExecutionExceptionSize(trimmedThrowableData, 1, n2));
                codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(trimmedThrowableData.className));
                StackTraceElement[] arrstackTraceElement = trimmedThrowableData.localizedMessage;
                if (arrstackTraceElement != null) {
                    codedOutputStream.writeBytes(3, ByteString.copyFromUtf8((String)arrstackTraceElement));
                }
                arrstackTraceElement = trimmedThrowableData.stacktrace;
                int n4 = arrstackTraceElement.length;
                for (n3 = 0; n3 < n4; ++n3) {
                    SessionProtobufHelper.writeFrame(codedOutputStream, 4, arrstackTraceElement[n3], true);
                }
                trimmedThrowableData = trimmedThrowableData.cause;
                if (trimmedThrowableData == null) break block6;
                if (n >= n2) break block7;
                SessionProtobufHelper.writeSessionEventAppExecutionException(codedOutputStream, trimmedThrowableData, n + 1, n2, 6);
            }
            return;
        }
        n = 0;
        while (trimmedThrowableData != null) {
            trimmedThrowableData = trimmedThrowableData.cause;
            ++n;
        }
        codedOutputStream.writeUInt32(7, n);
    }

    private static void writeSessionEventDevice(CodedOutputStream codedOutputStream, Float f, int n, boolean bl, int n2, long l, long l2) throws Exception {
        codedOutputStream.writeTag(5, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventDeviceSize(f, n, bl, n2, l, l2));
        if (f != null) {
            codedOutputStream.writeFloat(1, f.floatValue());
        }
        codedOutputStream.writeSInt32(2, n);
        codedOutputStream.writeBool(3, bl);
        codedOutputStream.writeUInt32(4, n2);
        codedOutputStream.writeUInt64(5, l);
        codedOutputStream.writeUInt64(6, l2);
    }

    private static void writeSessionEventLog(CodedOutputStream codedOutputStream, ByteString byteString) throws Exception {
        if (byteString != null) {
            codedOutputStream.writeTag(6, 2);
            codedOutputStream.writeRawVarint32(SessionProtobufHelper.getEventLogSize(byteString));
            codedOutputStream.writeBytes(1, byteString);
        }
    }

    public static void writeSessionOS(CodedOutputStream codedOutputStream, boolean bl) throws Exception {
        ByteString byteString = ByteString.copyFromUtf8(Build.VERSION.RELEASE);
        ByteString byteString2 = ByteString.copyFromUtf8(Build.VERSION.CODENAME);
        codedOutputStream.writeTag(8, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getSessionOSSize(byteString, byteString2, bl));
        codedOutputStream.writeEnum(1, 3);
        codedOutputStream.writeBytes(2, byteString);
        codedOutputStream.writeBytes(3, byteString2);
        codedOutputStream.writeBool(4, bl);
    }

    public static void writeSessionUser(CodedOutputStream codedOutputStream, String object, String string2, String string3) throws Exception {
        int n;
        Object object2 = object;
        if (object == null) {
            object2 = "";
        }
        object = ByteString.copyFromUtf8((String)object2);
        object2 = SessionProtobufHelper.stringToByteString(string2);
        ByteString byteString = SessionProtobufHelper.stringToByteString(string3);
        int n2 = n = 0 + CodedOutputStream.computeBytesSize(1, (ByteString)object);
        if (string2 != null) {
            n2 = n + CodedOutputStream.computeBytesSize(2, (ByteString)object2);
        }
        n = n2;
        if (string3 != null) {
            n = n2 + CodedOutputStream.computeBytesSize(3, byteString);
        }
        codedOutputStream.writeTag(6, 2);
        codedOutputStream.writeRawVarint32(n);
        codedOutputStream.writeBytes(1, (ByteString)object);
        if (string2 != null) {
            codedOutputStream.writeBytes(2, (ByteString)object2);
        }
        if (string3 != null) {
            codedOutputStream.writeBytes(3, byteString);
        }
    }

    private static void writeThread(CodedOutputStream codedOutputStream, Thread thread, StackTraceElement[] arrstackTraceElement, int n, boolean bl) throws Exception {
        codedOutputStream.writeTag(1, 2);
        codedOutputStream.writeRawVarint32(SessionProtobufHelper.getThreadSize(thread, arrstackTraceElement, n, bl));
        codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(thread.getName()));
        codedOutputStream.writeUInt32(2, n);
        int n2 = arrstackTraceElement.length;
        for (n = 0; n < n2; ++n) {
            SessionProtobufHelper.writeFrame(codedOutputStream, 3, arrstackTraceElement[n], bl);
        }
    }
}

