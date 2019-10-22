/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.events.EventTransform;
import io.fabric.sdk.android.services.events.EventsStorage;
import io.fabric.sdk.android.services.events.EventsStorageListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class EventsFilesManager<T> {
    protected final Context context;
    protected final CurrentTimeProvider currentTimeProvider;
    private final int defaultMaxFilesToKeep;
    protected final EventsStorage eventStorage;
    protected volatile long lastRollOverTime;
    protected final List<EventsStorageListener> rollOverListeners = new CopyOnWriteArrayList<EventsStorageListener>();
    protected final EventTransform<T> transform;

    public EventsFilesManager(Context context, EventTransform<T> eventTransform, CurrentTimeProvider currentTimeProvider, EventsStorage eventsStorage, int n) throws IOException {
        this.context = context.getApplicationContext();
        this.transform = eventTransform;
        this.eventStorage = eventsStorage;
        this.currentTimeProvider = currentTimeProvider;
        this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
        this.defaultMaxFilesToKeep = n;
    }

    private void rollFileOverIfNeeded(int n) throws IOException {
        if (!this.eventStorage.canWorkingFileStore(n, this.getMaxByteSizePerFile())) {
            String string2 = String.format(Locale.US, "session analytics events file is %d bytes, new event is %d bytes, this is over flush limit of %d, rolling it over", this.eventStorage.getWorkingFileUsedSizeInBytes(), n, this.getMaxByteSizePerFile());
            CommonUtils.logControlled(this.context, 4, "Fabric", string2);
            this.rollFileOver();
        }
    }

    private void triggerRollOverOnListeners(String string2) {
        for (EventsStorageListener eventsStorageListener : this.rollOverListeners) {
            try {
                eventsStorageListener.onRollOver(string2);
            }
            catch (Exception exception) {
                CommonUtils.logControlledError(this.context, "One of the roll over listeners threw an exception", exception);
            }
        }
    }

    public void deleteAllEventsFiles() {
        this.eventStorage.deleteFilesInRollOverDirectory(this.eventStorage.getAllFilesInRollOverDirectory());
        this.eventStorage.deleteWorkingFile();
    }

    public void deleteOldestInRollOverIfOverMax() {
        ArrayList<File> arrayList = this.eventStorage.getAllFilesInRollOverDirectory();
        int n = this.getMaxFilesToKeep();
        if (arrayList.size() <= n) {
            return;
        }
        int n2 = arrayList.size() - n;
        CommonUtils.logControlled(this.context, String.format(Locale.US, "Found %d files in  roll over directory, this is greater than %d, deleting %d oldest files", arrayList.size(), n, n2));
        Object object = new TreeSet<FileWithTimestamp>(new Comparator<FileWithTimestamp>(){

            @Override
            public int compare(FileWithTimestamp fileWithTimestamp, FileWithTimestamp fileWithTimestamp2) {
                return (int)(fileWithTimestamp.timestamp - fileWithTimestamp2.timestamp);
            }
        });
        for (File file : arrayList) {
            ((TreeSet)object).add((FileWithTimestamp)new FileWithTimestamp(file, this.parseCreationTimestampFromFileName(file.getName())));
        }
        arrayList = new ArrayList<File>();
        object = ((TreeSet)object).iterator();
        while (object.hasNext()) {
            arrayList.add(((FileWithTimestamp)object.next()).file);
            if (arrayList.size() != n2) continue;
        }
        this.eventStorage.deleteFilesInRollOverDirectory(arrayList);
    }

    public void deleteSentFiles(List<File> list) {
        this.eventStorage.deleteFilesInRollOverDirectory(list);
    }

    protected abstract String generateUniqueRollOverFileName();

    public List<File> getBatchOfFilesToSend() {
        return this.eventStorage.getBatchOfFilesToSend(1);
    }

    protected int getMaxByteSizePerFile() {
        return 8000;
    }

    protected int getMaxFilesToKeep() {
        return this.defaultMaxFilesToKeep;
    }

    public long parseCreationTimestampFromFileName(String arrstring) {
        if ((arrstring = arrstring.split("_")).length != 3) {
            return 0L;
        }
        try {
            long l = Long.valueOf(arrstring[2]);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            return 0L;
        }
    }

    public void registerRollOverListener(EventsStorageListener eventsStorageListener) {
        if (eventsStorageListener != null) {
            this.rollOverListeners.add(eventsStorageListener);
        }
    }

    public boolean rollFileOver() throws IOException {
        boolean bl = false;
        String string2 = null;
        if (!this.eventStorage.isWorkingFileEmpty()) {
            string2 = this.generateUniqueRollOverFileName();
            this.eventStorage.rollOver(string2);
            CommonUtils.logControlled(this.context, 4, "Fabric", String.format(Locale.US, "generated new file %s", string2));
            this.lastRollOverTime = this.currentTimeProvider.getCurrentTimeMillis();
            bl = true;
        }
        this.triggerRollOverOnListeners(string2);
        return bl;
    }

    public void writeEvent(T object) throws IOException {
        object = this.transform.toBytes(object);
        this.rollFileOverIfNeeded(((T)object).length);
        this.eventStorage.add((byte[])object);
    }

    static class FileWithTimestamp {
        final File file;
        final long timestamp;

        public FileWithTimestamp(File file, long l) {
            this.file = file;
            this.timestamp = l;
        }
    }

}

