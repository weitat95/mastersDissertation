/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.logger;

import android.content.Context;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.logger.LoggerUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import timber.log.Timber;

public class QardioLogger {
    private static final String TAG = QardioLogger.class.getName();
    private Context context;
    private File logFile;
    private RandomAccessFile randomAccessFile;
    private SimpleDateFormat simpleDateFormat;

    public QardioLogger(Context context) {
        this.init(context);
    }

    private void createLogFile() {
        this.logFile = new File(Utils.getExternalCacheDir(this.context), "qardio_logs.txt");
    }

    private void createRandomAccessFile() {
        try {
            this.randomAccessFile = new RandomAccessFile(this.logFile.getAbsolutePath(), "rw");
            return;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Timber.e(fileNotFoundException, "File not found", new Object[0]);
            return;
        }
    }

    private void init(Context context) {
        this.context = context;
        this.simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Utils.getLocale());
        this.createLogFile();
        if (LoggerUtils.checkLogFile(this.logFile)) {
            this.createRandomAccessFile();
        }
    }

    private void logEvent(String arrby) {
        block4: {
            try {
                if (this.randomAccessFile != null && this.logFile != null) {
                    Date date = new Date();
                    this.randomAccessFile.seek(this.logFile.length());
                    arrby = (this.simpleDateFormat.format(date) + " " + (String)arrby + "\n").getBytes();
                    LoggerUtils.writeAsync(this.randomAccessFile, arrby);
                    if (this.randomAccessFile.length() > 20480L) {
                        LoggerUtils.cutFileAsync(this.randomAccessFile, this.logFile.length(), 20480L);
                        return;
                    }
                    break block4;
                }
                this.recreateLogFileIfNull();
                return;
            }
            catch (IOException iOException) {
                Timber.e(iOException, "Exception occurred during logging event", new Object[0]);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void recreateLogFileIfNull() {
        if (!LoggerUtils.logFileExists(this.logFile)) {
            this.createLogFile();
            this.createRandomAccessFile();
            return;
        } else {
            if (this.randomAccessFile != null) return;
            {
                this.createRandomAccessFile();
                return;
            }
        }
    }

    public void close() {
        try {
            if (this.randomAccessFile != null) {
                this.randomAccessFile.close();
            }
            return;
        }
        catch (IOException iOException) {
            Timber.e(iOException, "Cannot close random access file", new Object[0]);
            return;
        }
    }

    public void getLastLogHalf(ReadCallback readCallback) {
        LoggerUtils.getLast10kb(this.randomAccessFile, this.logFile.length(), 20480L, readCallback);
    }

    public void logBluetoothOffEvent() {
        this.logEvent("Bluetooth turned off");
    }

    public void logBluetoothOnEvent() {
        this.logEvent("Bluetooth turned on");
    }

    public void logDisconnectedEvent() {
        this.logEvent("Disconnected");
    }

    public void logNeedPairingEvent() {
        this.logEvent("Need pairing");
    }

    public void logNeedResetEvent() {
        this.logEvent("Need reset");
    }

    public void logSerialNumberEvent(String string2) {
        this.logEvent(String.format("BPD serial number %s", string2));
    }

    public static interface ReadCallback {
        public void onReadCompleted(String var1);
    }

}

