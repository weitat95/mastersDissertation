/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelStore;

public class ViewModelProvider {
    private final Factory mFactory;
    private final ViewModelStore mViewModelStore;

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory) {
        this.mFactory = factory;
        this.mViewModelStore = viewModelStore;
    }

    public <T extends ViewModel> T get(Class<T> class_) {
        String string2 = class_.getCanonicalName();
        if (string2 == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return this.get("android.arch.lifecycle.ViewModelProvider.DefaultKey:" + string2, class_);
    }

    public <T extends ViewModel> T get(String string2, Class<T> class_) {
        ViewModel viewModel = this.mViewModelStore.get(string2);
        if (class_.isInstance(viewModel)) {
            return (T)viewModel;
        }
        if (viewModel != null) {
            // empty if block
        }
        class_ = this.mFactory.create(class_);
        this.mViewModelStore.put(string2, (ViewModel)((Object)class_));
        return (T)class_;
    }

    public static interface Factory {
        public <T extends ViewModel> T create(Class<T> var1);
    }

    public static class NewInstanceFactory
    implements Factory {
        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            ViewModel viewModel;
            try {
                viewModel = (ViewModel)class_.newInstance();
            }
            catch (InstantiationException instantiationException) {
                throw new RuntimeException("Cannot create an instance of " + class_, instantiationException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException("Cannot create an instance of " + class_, illegalAccessException);
            }
            return (T)viewModel;
        }
    }

}

