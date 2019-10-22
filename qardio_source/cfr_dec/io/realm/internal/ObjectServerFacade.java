/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package io.realm.internal;

import android.content.Context;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectServerFacade {
    private static final ObjectServerFacade nonSyncFacade = new ObjectServerFacade();
    private static ObjectServerFacade syncFacade = null;

    static {
        try {
            syncFacade = (ObjectServerFacade)Class.forName("io.realm.internal.SyncObjectServerFacade").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            return;
        }
        catch (InstantiationException instantiationException) {
            throw new RealmException("Failed to init SyncObjectServerFacade", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RealmException("Failed to init SyncObjectServerFacade", illegalAccessException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new RealmException("Failed to init SyncObjectServerFacade", noSuchMethodException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RealmException("Failed to init SyncObjectServerFacade", invocationTargetException.getTargetException());
        }
        catch (ClassNotFoundException classNotFoundException) {
            return;
        }
    }

    public static ObjectServerFacade getFacade(boolean bl) {
        if (bl) {
            return syncFacade;
        }
        return nonSyncFacade;
    }

    public static ObjectServerFacade getSyncFacadeIfPossible() {
        if (syncFacade != null) {
            return syncFacade;
        }
        return nonSyncFacade;
    }

    public void downloadRemoteChanges(RealmConfiguration realmConfiguration) {
    }

    public String getSyncServerCertificateAssetName(RealmConfiguration realmConfiguration) {
        return null;
    }

    public String getSyncServerCertificateFilePath(RealmConfiguration realmConfiguration) {
        return null;
    }

    public Object[] getUserAndServerUrl(RealmConfiguration realmConfiguration) {
        return new Object[6];
    }

    public void init(Context context) {
    }

    public void realmClosed(RealmConfiguration realmConfiguration) {
    }

    public void wrapObjectStoreSessionIfRequired(RealmConfiguration realmConfiguration) {
    }
}

