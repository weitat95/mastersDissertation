/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.os.Bundle
 */
package com.segment.analytics;

import android.app.Activity;
import android.os.Bundle;
import com.segment.analytics.ProjectSettings;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;
import com.segment.analytics.internal.Utils;
import java.util.Map;

abstract class IntegrationOperation {
    static final IntegrationOperation FLUSH = new IntegrationOperation(){

        @Override
        void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
            integration.flush();
        }

        public String toString() {
            return "Flush";
        }
    };
    static final IntegrationOperation RESET = new IntegrationOperation(){

        @Override
        void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
            integration.reset();
        }

        public String toString() {
            return "Reset";
        }
    };

    private IntegrationOperation() {
    }

    static IntegrationOperation alias(final AliasPayload aliasPayload) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                if (12.isIntegrationEnabled(aliasPayload.integrations(), string2)) {
                    integration.alias(aliasPayload);
                }
            }

            public String toString() {
                return aliasPayload.toString();
            }
        };
    }

    static IntegrationOperation group(final GroupPayload groupPayload) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                if (9.isIntegrationEnabled(groupPayload.integrations(), string2)) {
                    integration.group(groupPayload);
                }
            }

            public String toString() {
                return groupPayload.toString();
            }
        };
    }

    static IntegrationOperation identify(final IdentifyPayload identifyPayload) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                if (8.isIntegrationEnabled(identifyPayload.integrations(), string2)) {
                    integration.identify(identifyPayload);
                }
            }

            public String toString() {
                return identifyPayload.toString();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean isIntegrationEnabled(ValueMap valueMap, String string2) {
        block6: {
            block5: {
                if (Utils.isNullOrEmpty(valueMap)) break block5;
                if ("Segment.io".equals(string2)) {
                    return true;
                }
                if (valueMap.containsKey(string2)) {
                    return valueMap.getBoolean(string2, true);
                }
                if (valueMap.containsKey("All")) break block6;
            }
            return true;
        }
        return valueMap.getBoolean("All", true);
    }

    static IntegrationOperation onActivityCreated(final Activity activity, final Bundle bundle) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityCreated(activity, bundle);
            }

            public String toString() {
                return "Activity Created";
            }
        };
    }

    static IntegrationOperation onActivityDestroyed(final Activity activity) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityDestroyed(activity);
            }

            public String toString() {
                return "Activity Destroyed";
            }
        };
    }

    static IntegrationOperation onActivityPaused(final Activity activity) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityPaused(activity);
            }

            public String toString() {
                return "Activity Paused";
            }
        };
    }

    static IntegrationOperation onActivityResumed(final Activity activity) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityResumed(activity);
            }

            public String toString() {
                return "Activity Resumed";
            }
        };
    }

    static IntegrationOperation onActivitySaveInstanceState(final Activity activity, final Bundle bundle) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivitySaveInstanceState(activity, bundle);
            }

            public String toString() {
                return "Activity Save Instance";
            }
        };
    }

    static IntegrationOperation onActivityStarted(final Activity activity) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityStarted(activity);
            }

            public String toString() {
                return "Activity Started";
            }
        };
    }

    static IntegrationOperation onActivityStopped(final Activity activity) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                integration.onActivityStopped(activity);
            }

            public String toString() {
                return "Activity Stopped";
            }
        };
    }

    static IntegrationOperation screen(final ScreenPayload screenPayload) {
        return new IntegrationOperation(){

            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings projectSettings) {
                if (11.isIntegrationEnabled(screenPayload.integrations(), string2)) {
                    integration.screen(screenPayload);
                }
            }

            public String toString() {
                return screenPayload.toString();
            }
        };
    }

    static IntegrationOperation track(final TrackPayload trackPayload) {
        return new IntegrationOperation(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void run(String string2, Integration<?> integration, ProjectSettings valueMap) {
                ValueMap valueMap2 = trackPayload.integrations();
                ValueMap valueMap3 = ((ProjectSettings)valueMap).trackingPlan();
                if (Utils.isNullOrEmpty(valueMap3)) {
                    if (!10.isIntegrationEnabled(valueMap2, string2)) return;
                    {
                        integration.track(trackPayload);
                        return;
                    }
                } else {
                    valueMap = valueMap3.getValueMap(trackPayload.event());
                    if (Utils.isNullOrEmpty(valueMap)) {
                        if (!Utils.isNullOrEmpty(valueMap2)) {
                            if (!10.isIntegrationEnabled(valueMap2, string2)) return;
                            {
                                integration.track(trackPayload);
                                return;
                            }
                        } else {
                            valueMap = valueMap3.getValueMap("__default");
                            if (Utils.isNullOrEmpty(valueMap)) {
                                integration.track(trackPayload);
                                return;
                            }
                            if (!valueMap.getBoolean("enabled", true) && !"Segment.io".equals(string2)) return;
                            {
                                integration.track(trackPayload);
                                return;
                            }
                        }
                    } else if (!valueMap.getBoolean("enabled", true)) {
                        if (!"Segment.io".equals(string2)) return;
                        {
                            integration.track(trackPayload);
                            return;
                        }
                    } else {
                        valueMap3 = new ValueMap();
                        if (!Utils.isNullOrEmpty(valueMap = valueMap.getValueMap("integrations"))) {
                            valueMap3.putAll(valueMap);
                        }
                        valueMap3.putAll(valueMap2);
                        if (!10.isIntegrationEnabled(valueMap3, string2)) return;
                        {
                            integration.track(trackPayload);
                            return;
                        }
                    }
                }
            }

            public String toString() {
                return trackPayload.toString();
            }
        };
    }

    abstract void run(String var1, Integration<?> var2, ProjectSettings var3);

}

