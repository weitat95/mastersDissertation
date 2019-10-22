/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.text.Editable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.Window
 *  android.widget.EditText
 *  android.widget.ProgressBar
 */
package com.getqardio.android.ui.activity;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;
import com.getqardio.android.dagger.qbase.inviteuser.DaggerInviteUserActivityComponent;
import com.getqardio.android.dagger.qbase.inviteuser.InviteUserActivityComponent;
import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.InviteUserActivity$$Lambda$1;
import com.getqardio.android.ui.activity.InviteUserActivity$$Lambda$2;
import com.getqardio.android.ui.activity.InviteUserActivity$$Lambda$3;
import com.getqardio.android.ui.activity.InviteUserActivity$$Lambda$4;
import com.getqardio.android.utils.InvitationManager;
import com.getqardio.android.utils.analytics.InviteUserAnalytics;
import com.getqardio.android.utils.ui.ActivityUtils;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.Locale;
import timber.log.Timber;

public class InviteUserActivity
extends BaseActivity
implements View.OnClickListener {
    private Dialog dialog;
    private CompositeDisposable disposable = new CompositeDisposable();
    private EditText etInviteeEmail;
    private EditText etInviteeName;
    InviteApi inviteApi;
    private InvitationManager qbInvitationManager;

    public void dismissDialog() {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
            this.dialog = null;
        }
    }

    /* synthetic */ void lambda$onClick$0(Integer n) throws Exception {
        this.dismissDialog();
        if (n == 1000) {
            InviteUserAnalytics.trackUserInvite((Context)this);
            this.showSuccessDialog();
            return;
        }
        if (n == 1001) {
            this.etInviteeEmail.setError((CharSequence)this.getString(2131362004));
            return;
        }
        if (n == 1002) {
            this.etInviteeName.setError((CharSequence)this.getString(2131362002));
            return;
        }
        this.showErrorMessage(this.getString(2131362260));
    }

    /* synthetic */ void lambda$onClick$1(Throwable throwable) throws Exception {
        Timber.e(throwable);
        this.dismissDialog();
        this.showErrorMessage(this.getString(2131362260));
    }

    /* synthetic */ void lambda$showSuccessDialog$2(DialogInterface dialogInterface, int n) {
        this.etInviteeName.setText(null);
        this.etInviteeEmail.setText(null);
        this.dismissDialog();
    }

    /* synthetic */ void lambda$showSuccessDialog$3(DialogInterface dialogInterface, int n) {
        this.dismissDialog();
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(View view) {
        if (view.getId() == 2131820770) {
            this.disposable.dispose();
            this.finish();
            return;
        } else {
            if (view.getId() != 2131820777) return;
            {
                this.showDialog();
                this.disposable.add(this.qbInvitationManager.invite(this.etInviteeName.getText().toString(), this.etInviteeEmail.getText().toString(), this.getResources().getConfiguration().locale.getCountry()).subscribe(InviteUserActivity$$Lambda$1.lambdaFactory$(this), InviteUserActivity$$Lambda$2.lambdaFactory$(this)));
                return;
            }
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DaggerInviteUserActivityComponent.builder().repositoryComponent(((MvpApplication)this.getApplication()).getApplicationComponent()).build().inject(this);
        this.setContentView(2130968606);
        ActivityUtils.changeStatusBarColor(this, -16777216);
        this.getToolbar().setVisibility(8);
        bundle = new Uri.Builder().scheme("res").path(String.valueOf(2130837939)).build();
        ((SimpleDraweeView)((Object)this.findViewById(2131820769))).setImageURI((Uri)bundle);
        this.qbInvitationManager = new InvitationManager(InvitationManager.INVITATION_TYPE_QARDIO_BASE, this.inviteApi);
        this.etInviteeName = (EditText)this.findViewById(2131820774);
        this.etInviteeEmail = (EditText)this.findViewById(2131820776);
        this.findViewById(2131820770).setOnClickListener((View.OnClickListener)this);
        this.findViewById(2131820777).setOnClickListener((View.OnClickListener)this);
        InviteUserAnalytics.trackScreenLoaded((Context)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dismissDialog();
        this.disposable.dispose();
    }

    public void showDialog() {
        this.dialog = new Dialog((Context)this);
        this.dialog.getWindow().setLayout(-1, -1);
        this.dialog.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.dialog.requestWindowFeature(1);
        this.dialog.setCancelable(true);
        View view = this.dialog.getLayoutInflater().inflate(2130968694, null, false);
        ((ProgressBar)view.findViewById(2131821032)).getIndeterminateDrawable().setColorFilter(this.getResources().getColor(2131689548), PorterDuff.Mode.MULTIPLY);
        this.dialog.setContentView(view);
        this.dialog.show();
    }

    public void showErrorMessage(String string2) {
        Snackbar.make(this.findViewById(2131820768), string2, 5000).show();
    }

    public void showSuccessDialog() {
        this.dialog = new AlertDialog.Builder((Context)this, 2131493366).setTitle(this.getString(2131362224)).setMessage(this.getString(2131362225)).setPositiveButton(this.getString(2131362261), InviteUserActivity$$Lambda$3.lambdaFactory$(this)).setNegativeButton(this.getString(2131362247), InviteUserActivity$$Lambda$4.lambdaFactory$(this)).create();
        this.dialog.show();
    }
}

