/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.qardio_base.measurement_details;

import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsComponent;
import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.model.QBMeasurementDetailsRepository;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenterModule;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenterModule_ProvideViewFactory;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter_Factory;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerQBMeasurementDetailsComponent
implements QBMeasurementDetailsComponent {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Provider<QBMeasurementDetailsRepository> getQBMeasurementDetailsRepositoryProvider;
    private Provider<QBMeasurementDetailsContract.View> provideViewProvider;
    private MembersInjector<QBMeasurementDetailsActivity> qBMeasurementDetailsActivityMembersInjector;
    private Provider<QBMeasurementDetailsPresenter> qBMeasurementDetailsPresenterProvider;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DaggerQBMeasurementDetailsComponent.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    private DaggerQBMeasurementDetailsComponent(Builder builder) {
        if (!$assertionsDisabled && builder == null) {
            throw new AssertionError();
        }
        this.initialize(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.getQBMeasurementDetailsRepositoryProvider = new com_getqardio_android_mvp_common_injection_RepositoryComponent_getQBMeasurementDetailsRepository(builder.repositoryComponent);
        this.provideViewProvider = QBMeasurementDetailsPresenterModule_ProvideViewFactory.create(builder.qBMeasurementDetailsPresenterModule);
        this.qBMeasurementDetailsPresenterProvider = QBMeasurementDetailsPresenter_Factory.create(this.getQBMeasurementDetailsRepositoryProvider, this.provideViewProvider);
        this.qBMeasurementDetailsActivityMembersInjector = QBMeasurementDetailsActivity_MembersInjector.create(this.qBMeasurementDetailsPresenterProvider);
    }

    @Override
    public void inject(QBMeasurementDetailsActivity qBMeasurementDetailsActivity) {
        this.qBMeasurementDetailsActivityMembersInjector.injectMembers(qBMeasurementDetailsActivity);
    }

    public static final class Builder {
        private QBMeasurementDetailsPresenterModule qBMeasurementDetailsPresenterModule;
        private RepositoryComponent repositoryComponent;

        private Builder() {
        }

        public QBMeasurementDetailsComponent build() {
            if (this.qBMeasurementDetailsPresenterModule == null) {
                throw new IllegalStateException(QBMeasurementDetailsPresenterModule.class.getCanonicalName() + " must be set");
            }
            if (this.repositoryComponent == null) {
                throw new IllegalStateException(RepositoryComponent.class.getCanonicalName() + " must be set");
            }
            return new DaggerQBMeasurementDetailsComponent(this);
        }

        public Builder qBMeasurementDetailsPresenterModule(QBMeasurementDetailsPresenterModule qBMeasurementDetailsPresenterModule) {
            this.qBMeasurementDetailsPresenterModule = Preconditions.checkNotNull(qBMeasurementDetailsPresenterModule);
            return this;
        }

        public Builder repositoryComponent(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = Preconditions.checkNotNull(repositoryComponent);
            return this;
        }
    }

    private static class com_getqardio_android_mvp_common_injection_RepositoryComponent_getQBMeasurementDetailsRepository
    implements Provider<QBMeasurementDetailsRepository> {
        private final RepositoryComponent repositoryComponent;

        com_getqardio_android_mvp_common_injection_RepositoryComponent_getQBMeasurementDetailsRepository(RepositoryComponent repositoryComponent) {
            this.repositoryComponent = repositoryComponent;
        }

        @Override
        public QBMeasurementDetailsRepository get() {
            return Preconditions.checkNotNull(this.repositoryComponent.getQBMeasurementDetailsRepository(), "Cannot return null from a non-@Nullable component method");
        }
    }

}

