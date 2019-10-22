/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core;

import com.crashlytics.android.core.ByteString;
import com.crashlytics.android.core.CodedOutputStream;
import com.crashlytics.android.core.LogFileManager;
import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.CustomAttributeData;
import com.crashlytics.android.core.internal.models.DeviceData;
import com.crashlytics.android.core.internal.models.SessionEventData;
import com.crashlytics.android.core.internal.models.SignalData;
import com.crashlytics.android.core.internal.models.ThreadData;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class NativeCrashWriter {
    private static final SignalData DEFAULT_SIGNAL = new SignalData("", "", 0L);
    private static final BinaryImageMessage[] EMPTY_BINARY_IMAGE_MESSAGES;
    private static final ProtobufMessage[] EMPTY_CHILDREN;
    private static final CustomAttributeMessage[] EMPTY_CUSTOM_ATTRIBUTE_MESSAGES;
    private static final FrameMessage[] EMPTY_FRAME_MESSAGES;
    private static final ThreadMessage[] EMPTY_THREAD_MESSAGES;

    static {
        EMPTY_CHILDREN = new ProtobufMessage[0];
        EMPTY_THREAD_MESSAGES = new ThreadMessage[0];
        EMPTY_FRAME_MESSAGES = new FrameMessage[0];
        EMPTY_BINARY_IMAGE_MESSAGES = new BinaryImageMessage[0];
        EMPTY_CUSTOM_ATTRIBUTE_MESSAGES = new CustomAttributeMessage[0];
    }

    /*
     * Enabled aggressive block sorting
     */
    private static RepeatedMessage createBinaryImagesMessage(BinaryImageData[] arrbinaryImageData) {
        ProtobufMessage[] arrprotobufMessage = arrbinaryImageData != null ? new BinaryImageMessage[arrbinaryImageData.length] : EMPTY_BINARY_IMAGE_MESSAGES;
        int n = 0;
        while (n < arrprotobufMessage.length) {
            arrprotobufMessage[n] = new BinaryImageMessage(arrbinaryImageData[n]);
            ++n;
        }
        return new RepeatedMessage(arrprotobufMessage);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static RepeatedMessage createCustomAttributesMessage(CustomAttributeData[] arrcustomAttributeData) {
        ProtobufMessage[] arrprotobufMessage = arrcustomAttributeData != null ? new CustomAttributeMessage[arrcustomAttributeData.length] : EMPTY_CUSTOM_ATTRIBUTE_MESSAGES;
        int n = 0;
        while (n < arrprotobufMessage.length) {
            arrprotobufMessage[n] = new CustomAttributeMessage(arrcustomAttributeData[n]);
            ++n;
        }
        return new RepeatedMessage(arrprotobufMessage);
    }

    private static ProtobufMessage createDeviceMessage(DeviceData deviceData) {
        if (deviceData == null) {
            return new NullMessage();
        }
        return new DeviceMessage((float)deviceData.batteryCapacity / 100.0f, deviceData.batteryVelocity, deviceData.proximity, deviceData.orientation, deviceData.totalPhysicalMemory - deviceData.availablePhysicalMemory, deviceData.totalInternalStorage - deviceData.availableInternalStorage);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static EventMessage createEventMessage(SessionEventData sessionEventData, LogFileManager object, Map<String, String> object2) throws IOException {
        Object object3 = sessionEventData.signal != null ? sessionEventData.signal : DEFAULT_SIGNAL;
        object2 = new ApplicationMessage(new ExecutionMessage(new SignalMessage((SignalData)object3), NativeCrashWriter.createThreadsMessage(sessionEventData.threads), NativeCrashWriter.createBinaryImagesMessage(sessionEventData.binaryImages)), NativeCrashWriter.createCustomAttributesMessage(NativeCrashWriter.mergeCustomAttributes(sessionEventData.customAttributes, (Map<String, String>)object2)));
        object3 = NativeCrashWriter.createDeviceMessage(sessionEventData.deviceData);
        ByteString byteString = ((LogFileManager)object).getByteStringForLog();
        if (byteString == null) {
            Fabric.getLogger().d("CrashlyticsCore", "No log data to include with this event.");
        }
        ((LogFileManager)object).clearLog();
        object = byteString != null ? new LogMessage(byteString) : new NullMessage();
        return new EventMessage(sessionEventData.timestamp, "ndk-crash", new ProtobufMessage[]{object2, object3, object});
    }

    /*
     * Enabled aggressive block sorting
     */
    private static RepeatedMessage createFramesMessage(ThreadData.FrameData[] arrframeData) {
        ProtobufMessage[] arrprotobufMessage = arrframeData != null ? new FrameMessage[arrframeData.length] : EMPTY_FRAME_MESSAGES;
        int n = 0;
        while (n < arrprotobufMessage.length) {
            arrprotobufMessage[n] = new FrameMessage(arrframeData[n]);
            ++n;
        }
        return new RepeatedMessage(arrprotobufMessage);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static RepeatedMessage createThreadsMessage(ThreadData[] arrthreadData) {
        ProtobufMessage[] arrprotobufMessage = arrthreadData != null ? new ThreadMessage[arrthreadData.length] : EMPTY_THREAD_MESSAGES;
        int n = 0;
        while (n < arrprotobufMessage.length) {
            ThreadData threadData = arrthreadData[n];
            arrprotobufMessage[n] = new ThreadMessage(threadData, NativeCrashWriter.createFramesMessage(threadData.frames));
            ++n;
        }
        return new RepeatedMessage(arrprotobufMessage);
    }

    private static CustomAttributeData[] mergeCustomAttributes(CustomAttributeData[] arrobject, Map<String, String> arrcustomAttributeData) {
        arrcustomAttributeData = new TreeMap<String, String>((Map<String, String>)arrcustomAttributeData);
        if (arrobject != null) {
            for (Object object : arrobject) {
                arrcustomAttributeData.put(((CustomAttributeData)object).key, ((CustomAttributeData)object).value);
            }
        }
        arrobject = arrcustomAttributeData.entrySet().toArray(new Map.Entry[arrcustomAttributeData.size()]);
        arrcustomAttributeData = new CustomAttributeData[arrobject.length];
        for (int i = 0; i < arrcustomAttributeData.length; ++i) {
            arrcustomAttributeData[i] = new CustomAttributeData((String)arrobject[i].getKey(), (String)arrobject[i].getValue());
        }
        return arrcustomAttributeData;
    }

    public static void writeNativeCrash(SessionEventData sessionEventData, LogFileManager logFileManager, Map<String, String> map, CodedOutputStream codedOutputStream) throws IOException {
        NativeCrashWriter.createEventMessage(sessionEventData, logFileManager, map).write(codedOutputStream);
    }

    private static final class ApplicationMessage
    extends ProtobufMessage {
        public ApplicationMessage(ExecutionMessage executionMessage, RepeatedMessage repeatedMessage) {
            super(3, executionMessage, repeatedMessage);
        }
    }

    private static final class BinaryImageMessage
    extends ProtobufMessage {
        private final long baseAddr;
        private final String filePath;
        private final long imageSize;
        private final String uuid;

        public BinaryImageMessage(BinaryImageData binaryImageData) {
            super(4, new ProtobufMessage[0]);
            this.baseAddr = binaryImageData.baseAddress;
            this.imageSize = binaryImageData.size;
            this.filePath = binaryImageData.path;
            this.uuid = binaryImageData.id;
        }

        @Override
        public int getPropertiesSize() {
            int n = CodedOutputStream.computeUInt64Size(1, this.baseAddr);
            int n2 = CodedOutputStream.computeUInt64Size(2, this.imageSize);
            return CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.filePath)) + n + n2 + CodedOutputStream.computeBytesSize(4, ByteString.copyFromUtf8(this.uuid));
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeUInt64(1, this.baseAddr);
            codedOutputStream.writeUInt64(2, this.imageSize);
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(this.filePath));
            codedOutputStream.writeBytes(4, ByteString.copyFromUtf8(this.uuid));
        }
    }

    private static final class CustomAttributeMessage
    extends ProtobufMessage {
        private final String key;
        private final String value;

        public CustomAttributeMessage(CustomAttributeData customAttributeData) {
            super(2, new ProtobufMessage[0]);
            this.key = customAttributeData.key;
            this.value = customAttributeData.value;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getPropertiesSize() {
            String string2;
            int n = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.key));
            if (this.value == null) {
                string2 = "";
                do {
                    return n + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string2));
                    break;
                } while (true);
            }
            string2 = this.value;
            return n + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(string2));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(this.key));
            String string2 = this.value == null ? "" : this.value;
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(string2));
        }
    }

    private static final class DeviceMessage
    extends ProtobufMessage {
        private final float batteryLevel;
        private final int batteryVelocity;
        private final long diskUsed;
        private final int orientation;
        private final boolean proximityOn;
        private final long ramUsed;

        public DeviceMessage(float f, int n, boolean bl, int n2, long l, long l2) {
            super(5, new ProtobufMessage[0]);
            this.batteryLevel = f;
            this.batteryVelocity = n;
            this.proximityOn = bl;
            this.orientation = n2;
            this.ramUsed = l;
            this.diskUsed = l2;
        }

        @Override
        public int getPropertiesSize() {
            return 0 + CodedOutputStream.computeFloatSize(1, this.batteryLevel) + CodedOutputStream.computeSInt32Size(2, this.batteryVelocity) + CodedOutputStream.computeBoolSize(3, this.proximityOn) + CodedOutputStream.computeUInt32Size(4, this.orientation) + CodedOutputStream.computeUInt64Size(5, this.ramUsed) + CodedOutputStream.computeUInt64Size(6, this.diskUsed);
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeFloat(1, this.batteryLevel);
            codedOutputStream.writeSInt32(2, this.batteryVelocity);
            codedOutputStream.writeBool(3, this.proximityOn);
            codedOutputStream.writeUInt32(4, this.orientation);
            codedOutputStream.writeUInt64(5, this.ramUsed);
            codedOutputStream.writeUInt64(6, this.diskUsed);
        }
    }

    private static final class EventMessage
    extends ProtobufMessage {
        private final String crashType;
        private final long time;

        public EventMessage(long l, String string2, ProtobufMessage ... arrprotobufMessage) {
            super(10, arrprotobufMessage);
            this.time = l;
            this.crashType = string2;
        }

        @Override
        public int getPropertiesSize() {
            return CodedOutputStream.computeUInt64Size(1, this.time) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.crashType));
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeUInt64(1, this.time);
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.crashType));
        }
    }

    private static final class ExecutionMessage
    extends ProtobufMessage {
        public ExecutionMessage(SignalMessage signalMessage, RepeatedMessage repeatedMessage, RepeatedMessage repeatedMessage2) {
            super(1, repeatedMessage, signalMessage, repeatedMessage2);
        }
    }

    private static final class FrameMessage
    extends ProtobufMessage {
        private final long address;
        private final String file;
        private final int importance;
        private final long offset;
        private final String symbol;

        public FrameMessage(ThreadData.FrameData frameData) {
            super(3, new ProtobufMessage[0]);
            this.address = frameData.address;
            this.symbol = frameData.symbol;
            this.file = frameData.file;
            this.offset = frameData.offset;
            this.importance = frameData.importance;
        }

        @Override
        public int getPropertiesSize() {
            return CodedOutputStream.computeUInt64Size(1, this.address) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.symbol)) + CodedOutputStream.computeBytesSize(3, ByteString.copyFromUtf8(this.file)) + CodedOutputStream.computeUInt64Size(4, this.offset) + CodedOutputStream.computeUInt32Size(5, this.importance);
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeUInt64(1, this.address);
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.symbol));
            codedOutputStream.writeBytes(3, ByteString.copyFromUtf8(this.file));
            codedOutputStream.writeUInt64(4, this.offset);
            codedOutputStream.writeUInt32(5, this.importance);
        }
    }

    private static final class LogMessage
    extends ProtobufMessage {
        ByteString logBytes;

        public LogMessage(ByteString byteString) {
            super(6, new ProtobufMessage[0]);
            this.logBytes = byteString;
        }

        @Override
        public int getPropertiesSize() {
            return CodedOutputStream.computeBytesSize(1, this.logBytes);
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeBytes(1, this.logBytes);
        }
    }

    private static final class NullMessage
    extends ProtobufMessage {
        public NullMessage() {
            super(0, new ProtobufMessage[0]);
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public void write(CodedOutputStream codedOutputStream) throws IOException {
        }
    }

    private static abstract class ProtobufMessage {
        private final ProtobufMessage[] children;
        private final int tag;

        /*
         * Enabled aggressive block sorting
         */
        public ProtobufMessage(int n, ProtobufMessage ... arrprotobufMessage) {
            this.tag = n;
            if (arrprotobufMessage == null) {
                arrprotobufMessage = EMPTY_CHILDREN;
            }
            this.children = arrprotobufMessage;
        }

        public int getPropertiesSize() {
            return 0;
        }

        public int getSize() {
            int n = this.getSizeNoTag();
            return n + CodedOutputStream.computeRawVarint32Size(n) + CodedOutputStream.computeTagSize(this.tag);
        }

        public int getSizeNoTag() {
            int n = this.getPropertiesSize();
            ProtobufMessage[] arrprotobufMessage = this.children;
            int n2 = arrprotobufMessage.length;
            for (int i = 0; i < n2; ++i) {
                n += arrprotobufMessage[i].getSize();
            }
            return n;
        }

        public void write(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeTag(this.tag, 2);
            codedOutputStream.writeRawVarint32(this.getSizeNoTag());
            this.writeProperties(codedOutputStream);
            ProtobufMessage[] arrprotobufMessage = this.children;
            int n = arrprotobufMessage.length;
            for (int i = 0; i < n; ++i) {
                arrprotobufMessage[i].write(codedOutputStream);
            }
        }

        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
        }
    }

    private static final class RepeatedMessage
    extends ProtobufMessage {
        private final ProtobufMessage[] messages;

        public RepeatedMessage(ProtobufMessage ... arrprotobufMessage) {
            super(0, new ProtobufMessage[0]);
            this.messages = arrprotobufMessage;
        }

        @Override
        public int getSize() {
            int n = 0;
            ProtobufMessage[] arrprotobufMessage = this.messages;
            int n2 = arrprotobufMessage.length;
            for (int i = 0; i < n2; ++i) {
                n += arrprotobufMessage[i].getSize();
            }
            return n;
        }

        @Override
        public void write(CodedOutputStream codedOutputStream) throws IOException {
            ProtobufMessage[] arrprotobufMessage = this.messages;
            int n = arrprotobufMessage.length;
            for (int i = 0; i < n; ++i) {
                arrprotobufMessage[i].write(codedOutputStream);
            }
        }
    }

    private static final class SignalMessage
    extends ProtobufMessage {
        private final long sigAddr;
        private final String sigCode;
        private final String sigName;

        public SignalMessage(SignalData signalData) {
            super(3, new ProtobufMessage[0]);
            this.sigName = signalData.name;
            this.sigCode = signalData.code;
            this.sigAddr = signalData.faultAddress;
        }

        @Override
        public int getPropertiesSize() {
            return CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.sigName)) + CodedOutputStream.computeBytesSize(2, ByteString.copyFromUtf8(this.sigCode)) + CodedOutputStream.computeUInt64Size(3, this.sigAddr);
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(this.sigName));
            codedOutputStream.writeBytes(2, ByteString.copyFromUtf8(this.sigCode));
            codedOutputStream.writeUInt64(3, this.sigAddr);
        }
    }

    private static final class ThreadMessage
    extends ProtobufMessage {
        private final int importance;
        private final String name;

        public ThreadMessage(ThreadData threadData, RepeatedMessage repeatedMessage) {
            super(1, repeatedMessage);
            this.name = threadData.name;
            this.importance = threadData.importance;
        }

        private boolean hasName() {
            return this.name != null && this.name.length() > 0;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public int getPropertiesSize() {
            int n;
            if (this.hasName()) {
                n = CodedOutputStream.computeBytesSize(1, ByteString.copyFromUtf8(this.name));
                do {
                    return CodedOutputStream.computeUInt32Size(2, this.importance) + n;
                    break;
                } while (true);
            }
            n = 0;
            return CodedOutputStream.computeUInt32Size(2, this.importance) + n;
        }

        @Override
        public void writeProperties(CodedOutputStream codedOutputStream) throws IOException {
            if (this.hasName()) {
                codedOutputStream.writeBytes(1, ByteString.copyFromUtf8(this.name));
            }
            codedOutputStream.writeUInt32(2, this.importance);
        }
    }

}

