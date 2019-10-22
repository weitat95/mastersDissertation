/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentProvider
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.ProviderInfo
 *  android.content.res.XmlResourceParser
 *  android.database.Cursor
 *  android.database.MatrixCursor
 *  android.net.Uri
 *  android.os.Environment
 *  android.os.ParcelFileDescriptor
 *  android.text.TextUtils
 *  android.webkit.MimeTypeMap
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider
extends ContentProvider {
    private static final String[] COLUMNS = new String[]{"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static HashMap<String, PathStrategy> sCache = new HashMap();
    private PathStrategy mStrategy;

    private static File buildPath(File file, String ... arrstring) {
        for (String string2 : arrstring) {
            if (string2 == null) continue;
            file = new File(file, string2);
        }
        return file;
    }

    private static Object[] copyOf(Object[] arrobject, int n) {
        Object[] arrobject2 = new Object[n];
        System.arraycopy(arrobject, 0, arrobject2, 0, n);
        return arrobject2;
    }

    private static String[] copyOf(String[] arrstring, int n) {
        String[] arrstring2 = new String[n];
        System.arraycopy(arrstring, 0, arrstring2, 0, n);
        return arrstring2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static PathStrategy getPathStrategy(Context context, String string2) {
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            PathStrategy pathStrategy;
            PathStrategy pathStrategy2 = pathStrategy = sCache.get(string2);
            if (pathStrategy == null) {
                try {
                    pathStrategy2 = FileProvider.parsePathStrategy(context, string2);
                    sCache.put(string2, pathStrategy2);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", iOException);
                }
                catch (XmlPullParserException xmlPullParserException) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", xmlPullParserException);
                }
            }
            return pathStrategy2;
        }
    }

    private static int modeToMode(String string2) {
        if ("r".equals(string2)) {
            return 268435456;
        }
        if ("w".equals(string2) || "wt".equals(string2)) {
            return 738197504;
        }
        if ("wa".equals(string2)) {
            return 704643072;
        }
        if ("rw".equals(string2)) {
            return 939524096;
        }
        if ("rwt".equals(string2)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static PathStrategy parsePathStrategy(Context context, String object) throws IOException, XmlPullParserException {
        SimplePathStrategy simplePathStrategy = new SimplePathStrategy((String)object);
        XmlResourceParser xmlResourceParser = context.getPackageManager().resolveContentProvider((String)object, 128).loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (xmlResourceParser == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        int n;
        while ((n = xmlResourceParser.next()) != 1) {
            if (n != 2) continue;
            File[] arrfile = xmlResourceParser.getName();
            String string2 = xmlResourceParser.getAttributeValue(null, "name");
            String string3 = xmlResourceParser.getAttributeValue(null, "path");
            Object var3_5 = null;
            if ("root-path".equals(arrfile)) {
                object = DEVICE_ROOT;
            } else if ("files-path".equals(arrfile)) {
                object = context.getFilesDir();
            } else if ("cache-path".equals(arrfile)) {
                object = context.getCacheDir();
            } else if ("external-path".equals(arrfile)) {
                object = Environment.getExternalStorageDirectory();
            } else if ("external-files-path".equals(arrfile)) {
                arrfile = ContextCompat.getExternalFilesDirs(context, null);
                object = var3_5;
                if (arrfile.length > 0) {
                    object = arrfile[0];
                }
            } else {
                object = var3_5;
                if ("external-cache-path".equals(arrfile)) {
                    arrfile = ContextCompat.getExternalCacheDirs(context);
                    object = var3_5;
                    if (arrfile.length > 0) {
                        object = arrfile[0];
                    }
                }
            }
            if (object == null) continue;
            simplePathStrategy.addRoot(string2, FileProvider.buildPath((File)object, string3));
        }
        return simplePathStrategy;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.mStrategy = FileProvider.getPathStrategy(context, providerInfo.authority);
    }

    public int delete(Uri uri, String string2, String[] arrstring) {
        if (this.mStrategy.getFileForUri(uri).delete()) {
            return 1;
        }
        return 0;
    }

    public String getType(Uri object) {
        int n = ((File)(object = this.mStrategy.getFileForUri((Uri)object))).getName().lastIndexOf(46);
        if (n >= 0) {
            object = ((File)object).getName().substring(n + 1);
            object = MimeTypeMap.getSingleton().getMimeTypeFromExtension((String)object);
            if (object != null) {
                return object;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public boolean onCreate() {
        return true;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string2) throws FileNotFoundException {
        return ParcelFileDescriptor.open((File)this.mStrategy.getFileForUri(uri), (int)FileProvider.modeToMode(string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public Cursor query(Uri matrixCursor, String[] matrixCursor2, String object, String[] arrstring, String object2) {
        object = this.mStrategy.getFileForUri((Uri)matrixCursor);
        matrixCursor = matrixCursor2;
        if (matrixCursor2 == null) {
            matrixCursor = COLUMNS;
        }
        arrstring = new String[((Object[])matrixCursor).length];
        matrixCursor2 = new Object[((Object[])matrixCursor).length];
        int n = ((Object[])matrixCursor).length;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            if (n2 >= n) {
                matrixCursor = FileProvider.copyOf(arrstring, n3);
                matrixCursor2 = FileProvider.copyOf((Object[])matrixCursor2, n3);
                matrixCursor = new MatrixCursor((String[])matrixCursor, 1);
                matrixCursor.addRow((Object[])matrixCursor2);
                return matrixCursor;
            }
            object2 = matrixCursor[n2];
            if ("_display_name".equals(object2)) {
                arrstring[n3] = "_display_name";
                n4 = n3 + 1;
                matrixCursor2[n3] = ((File)object).getName();
                n3 = n4;
            } else if ("_size".equals(object2)) {
                arrstring[n3] = "_size";
                n4 = n3 + 1;
                matrixCursor2[n3] = Long.valueOf(((File)object).length());
                n3 = n4;
            }
            ++n2;
        } while (true);
    }

    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("No external updates");
    }

    static interface PathStrategy {
        public File getFileForUri(Uri var1);
    }

    static class SimplePathStrategy
    implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap();

        public SimplePathStrategy(String string2) {
            this.mAuthority = string2;
        }

        public void addRoot(String string2, File file) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                File file2 = file.getCanonicalFile();
                this.mRoots.put(string2, file2);
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file, iOException);
            }
        }

        @Override
        public File getFileForUri(Uri object) {
            Object object2 = object.getEncodedPath();
            int n = ((String)object2).indexOf(47, 1);
            Object object3 = Uri.decode((String)((String)object2).substring(1, n));
            object2 = Uri.decode((String)((String)object2).substring(n + 1));
            if ((object3 = this.mRoots.get(object3)) == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + object);
            }
            object = new File((File)object3, (String)object2);
            try {
                object2 = ((File)object).getCanonicalFile();
                if (!((File)object2).getPath().startsWith(((File)object3).getPath())) {
                    throw new SecurityException("Resolved path jumped beyond configured root");
                }
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + object);
            }
            return object2;
        }
    }

}

