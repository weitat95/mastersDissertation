/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package android.support.transition;

import android.support.transition.R;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
    private Runnable mExitAction;
    private ViewGroup mSceneRoot;

    static Scene getCurrentScene(View view) {
        return (Scene)view.getTag(R.id.transition_current_scene);
    }

    static void setCurrentScene(View view, Scene scene) {
        view.setTag(R.id.transition_current_scene, (Object)scene);
    }

    public void exit() {
        if (Scene.getCurrentScene((View)this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }
}

