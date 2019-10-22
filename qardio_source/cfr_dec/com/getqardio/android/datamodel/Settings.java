/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class Settings
extends BaseEntity {
    public Integer albums = 0;
    public Boolean allowBpExportSHealth = false;
    public Boolean allowBpImportSHealth = false;
    public Boolean allowIntegrationGoogleFit = false;
    public Boolean allowLocation = true;
    public Boolean allowMixpanelNotifications = true;
    public Boolean allowNotifications = true;
    public Boolean allowWeightExportSHealth = false;
    public Boolean allowWeightImportSHealth = false;
    public Integer measurementsNumber = 1;
    public Integer pauseSize = 15;
    public Boolean showPhoto = false;
    public Integer syncStatus = 0;
    public Long userId = -1L;

    public static Settings getDefault(long l) {
        Settings settings = new Settings();
        settings.userId = l;
        settings.syncStatus = 0;
        settings.measurementsNumber = 1;
        settings.pauseSize = 15;
        settings.showPhoto = false;
        settings.allowLocation = true;
        settings.albums = 0;
        settings.allowBpImportSHealth = false;
        settings.allowBpExportSHealth = false;
        settings.allowWeightExportSHealth = false;
        settings.allowWeightImportSHealth = false;
        settings.allowNotifications = true;
        settings.allowIntegrationGoogleFit = false;
        settings.allowMixpanelNotifications = true;
        return settings;
    }

    public void cleanAlbums() {
        this.albums = 0;
    }

    public void setUseFlickr() {
        this.albums = this.albums | 1;
    }

    public void setUsePhotosFromDevice() {
        this.albums = this.albums | 2;
    }

    public boolean useFlickr() {
        return (this.albums & 1) == 1;
    }

    public boolean usePhotosFromDevice() {
        return (this.albums & 2) == 2;
    }
}

