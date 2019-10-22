/*
 * Decompiled with CFR 0.147.
 */
package io.branch.referral;

import io.branch.referral.AnimatedDialog;

class ShareLinkManager {
    private static int viewItemMinHeight_ = 100;
    AnimatedDialog shareDlg_;

    public void cancelShareLinkDialog(boolean bl) {
        block3: {
            block2: {
                if (this.shareDlg_ == null || !this.shareDlg_.isShowing()) break block2;
                if (!bl) break block3;
                this.shareDlg_.cancel();
            }
            return;
        }
        this.shareDlg_.dismiss();
    }
}

