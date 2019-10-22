/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.logger;

import com.getqardio.android.utils.logger.QardioLogger;
import com.getqardio.android.utils.logger.ReverseLineReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import timber.log.Timber;

public class LoggerUtils {
    private static final String TAG = LoggerUtils.class.getName();

    static boolean checkLogFile(File file) {
        boolean bl = true;
        if (!LoggerUtils.logFileExists(file)) {
            bl = LoggerUtils.createNewFile(file);
        }
        return bl;
    }

    static boolean createNewFile(File file) {
        try {
            boolean bl = file.createNewFile();
            return bl;
        }
        catch (IOException iOException) {
            Timber.e(iOException, "Cannot create file - %s", file.getAbsolutePath());
            return false;
        }
    }

    static void cutFileAsync(final RandomAccessFile randomAccessFile, long l, long l2) throws IOException {
        LoggerUtils.getLast10kb(randomAccessFile, l, l2, new QardioLogger.ReadCallback(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onReadCompleted(String arrby) {
                try {
                    randomAccessFile.setLength(0L);
                    RandomAccessFile randomAccessFile2 = randomAccessFile;
                    arrby = arrby != null ? arrby.getBytes() : new byte[0];
                    LoggerUtils.writeAsync(randomAccessFile2, arrby);
                    return;
                }
                catch (IOException iOException) {
                    Timber.e(iOException, "Cannot write to the file", new Object[0]);
                    return;
                }
            }
        });
    }

    static void getLast10kb(final RandomAccessFile randomAccessFile, final long l, final long l2, final QardioLogger.ReadCallback readCallback) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    String string2 = LoggerUtils.readLastKilobytes(new ReverseLineReader(randomAccessFile, "UTF-8"), l, l2);
                    if (readCallback != null) {
                        readCallback.onReadCompleted(string2);
                    }
                    return;
                }
                catch (IOException iOException) {
                    Timber.e(iOException, "Cannot read file", new Object[0]);
                    return;
                }
            }
        }).start();
    }

    static boolean logFileExists(File file) {
        return file != null && file.exists();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String readLastKilobytes(ReverseLineReader reverseLineReader, long l, long l2) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        long l3 = 0L;
        if (l < l2) {
            l2 = l3;
        } else {
            l = l2 / 2L;
            l2 = l3;
        }
        while (l2 < l) {
            String string2 = reverseLineReader.readLine() + "\n";
            stringBuilder.insert(0, string2);
            l2 += (long)string2.getBytes().length;
        }
        return stringBuilder.toString();
    }

    static void writeAsync(final RandomAccessFile randomAccessFile, final byte[] arrby) {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    randomAccessFile.write(arrby);
                    return;
                }
                catch (IOException iOException) {
                    Timber.e(iOException, "Cannot write to the file", new Object[0]);
                    return;
                }
            }
        }).start();
    }

}

