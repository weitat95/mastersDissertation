/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 */
package com.getkeepsafe.relinker;

import android.content.Context;
import android.util.Log;
import com.getkeepsafe.relinker.ApkLibraryInstaller;
import com.getkeepsafe.relinker.MissingLibraryException;
import com.getkeepsafe.relinker.ReLinker;
import com.getkeepsafe.relinker.SystemLibraryLoader;
import com.getkeepsafe.relinker.TextUtils;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ReLinkerInstance {
    protected boolean force;
    protected final ReLinker.LibraryInstaller libraryInstaller;
    protected final ReLinker.LibraryLoader libraryLoader;
    protected final Set<String> loadedLibraries = new HashSet<String>();
    protected ReLinker.Logger logger;
    protected boolean recursive;

    protected ReLinkerInstance() {
        this(new SystemLibraryLoader(), new ApkLibraryInstaller());
    }

    protected ReLinkerInstance(ReLinker.LibraryLoader libraryLoader, ReLinker.LibraryInstaller libraryInstaller) {
        if (libraryLoader == null) {
            throw new IllegalArgumentException("Cannot pass null library loader");
        }
        if (libraryInstaller == null) {
            throw new IllegalArgumentException("Cannot pass null library installer");
        }
        this.libraryLoader = libraryLoader;
        this.libraryInstaller = libraryInstaller;
    }

    private void loadLibraryInternal(Context context, String string2, String string3) {
        if (this.loadedLibraries.contains(string2) && !this.force) {
            this.log("%s already loaded previously!", string2);
            return;
        }
        try {
            this.libraryLoader.loadLibrary(string2);
            this.loadedLibraries.add(string2);
            this.log("%s (%s) was loaded normally!", string2, string3);
            return;
        }
        catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            this.log("Loading the library normally failed: %s", Log.getStackTraceString((Throwable)unsatisfiedLinkError));
            this.log("%s (%s) was not loaded normally, re-linking...", string2, string3);
            File file = this.getWorkaroundLibFile(context, string2, string3);
            if (!file.exists() || this.force) {
                if (this.force) {
                    this.log("Forcing a re-link of %s (%s)...", string2, string3);
                }
                this.cleanupOldLibFiles(context, string2, string3);
                this.libraryInstaller.installLibrary(context, this.libraryLoader.supportedAbis(), this.libraryLoader.mapLibraryName(string2), file, this);
            }
            try {
                if (this.recursive) {
                    for (String string4 : new ElfParser(file).parseNeededDependencies()) {
                        this.loadLibrary(context, this.libraryLoader.unmapLibraryName(string4));
                    }
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.libraryLoader.loadPath(file.getAbsolutePath());
            this.loadedLibraries.add(string2);
            this.log("%s (%s) was re-linked!", string2, string3);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void cleanupOldLibFiles(Context object, String arrfile, String object22) {
        File file = this.getWorkaroundLibDir((Context)object);
        object = this.getWorkaroundLibFile((Context)object, (String)arrfile, (String)object22);
        if ((arrfile = file.listFiles(new FilenameFilter(this.libraryLoader.mapLibraryName((String)arrfile)){
            final /* synthetic */ String val$mappedLibraryName;
            {
                this.val$mappedLibraryName = string2;
            }

            @Override
            public boolean accept(File file, String string2) {
                return string2.startsWith(this.val$mappedLibraryName);
            }
        })) != null) {
            for (Object object22 : arrfile) {
                if (!this.force && ((File)object22).getAbsolutePath().equals(((File)object).getAbsolutePath())) continue;
                ((File)object22).delete();
            }
        }
    }

    protected File getWorkaroundLibDir(Context context) {
        return context.getDir("lib", 0);
    }

    protected File getWorkaroundLibFile(Context context, String string2, String string3) {
        string2 = this.libraryLoader.mapLibraryName(string2);
        if (TextUtils.isEmpty(string3)) {
            return new File(this.getWorkaroundLibDir(context), string2);
        }
        return new File(this.getWorkaroundLibDir(context), string2 + "." + string3);
    }

    public void loadLibrary(Context context, String string2) {
        this.loadLibrary(context, string2, null, null);
    }

    public void loadLibrary(final Context context, final String string2, final String string3, final ReLinker.LoadListener loadListener) {
        if (context == null) {
            throw new IllegalArgumentException("Given context is null");
        }
        if (TextUtils.isEmpty(string2)) {
            throw new IllegalArgumentException("Given library is either null or empty");
        }
        this.log("Beginning load of %s...", string2);
        if (loadListener == null) {
            this.loadLibraryInternal(context, string2, string3);
            return;
        }
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    ReLinkerInstance.this.loadLibraryInternal(context, string2, string3);
                    loadListener.success();
                    return;
                }
                catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                    loadListener.failure(unsatisfiedLinkError);
                    return;
                }
                catch (MissingLibraryException missingLibraryException) {
                    loadListener.failure(missingLibraryException);
                    return;
                }
            }
        }).start();
    }

    public void log(String string2) {
        if (this.logger != null) {
            this.logger.log(string2);
        }
    }

    public void log(String string2, Object ... arrobject) {
        this.log(String.format(Locale.US, string2, arrobject));
    }

}

