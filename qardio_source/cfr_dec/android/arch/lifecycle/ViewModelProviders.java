/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Application
 */
package android.arch.lifecycle;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStores;
import android.support.v4.app.FragmentActivity;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewModelProviders {
    @SuppressLint(value={"StaticFieldLeak"})
    private static DefaultFactory sDefaultFactory;

    private static void initializeFactoryIfNeeded(Application application) {
        if (sDefaultFactory == null) {
            sDefaultFactory = new DefaultFactory(application);
        }
    }

    public static ViewModelProvider of(FragmentActivity fragmentActivity) {
        ViewModelProviders.initializeFactoryIfNeeded(fragmentActivity.getApplication());
        return new ViewModelProvider(ViewModelStores.of(fragmentActivity), sDefaultFactory);
    }

    public static ViewModelProvider of(FragmentActivity fragmentActivity, ViewModelProvider.Factory factory) {
        return new ViewModelProvider(ViewModelStores.of(fragmentActivity), factory);
    }

    public static class DefaultFactory
    extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;

        public DefaultFactory(Application application) {
            this.mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> class_) {
            if (AndroidViewModel.class.isAssignableFrom(class_)) {
                ViewModel viewModel;
                try {
                    viewModel = (ViewModel)class_.getConstructor(Application.class).newInstance(new Object[]{this.mApplication});
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    throw new RuntimeException("Cannot create an instance of " + class_, noSuchMethodException);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new RuntimeException("Cannot create an instance of " + class_, illegalAccessException);
                }
                catch (InstantiationException instantiationException) {
                    throw new RuntimeException("Cannot create an instance of " + class_, instantiationException);
                }
                catch (InvocationTargetException invocationTargetException) {
                    throw new RuntimeException("Cannot create an instance of " + class_, invocationTargetException);
                }
                return (T)viewModel;
            }
            return super.create(class_);
        }
    }

}

