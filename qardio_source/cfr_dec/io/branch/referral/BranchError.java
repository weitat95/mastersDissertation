/*
 * Decompiled with CFR 0.147.
 */
package io.branch.referral;

public class BranchError {
    int errorCode_ = -113;
    String errorMessage_ = "";

    public BranchError(String string2, int n) {
        this.errorMessage_ = string2 + this.initErrorCodeAndGetLocalisedMessage(n);
    }

    private String initErrorCodeAndGetLocalisedMessage(int n) {
        if (n == -113) {
            this.errorCode_ = -113;
            return " Branch API Error: poor network connectivity. Please try again later.";
        }
        if (n == -114) {
            this.errorCode_ = -114;
            return " Branch API Error: Please enter your branch_key in your project's manifest file first.";
        }
        if (n == -104) {
            this.errorCode_ = -104;
            return " Did you forget to call init? Make sure you init the session before making Branch calls.";
        }
        if (n == -101) {
            this.errorCode_ = -101;
            return " Unable to initialize Branch. Check network connectivity or that your branch key is valid.";
        }
        if (n == -102) {
            this.errorCode_ = -102;
            return " Please add 'android.permission.INTERNET' in your applications manifest file.";
        }
        if (n == -105) {
            this.errorCode_ = -105;
            return " Unable to create a URL with that alias. If you want to reuse the alias, make sure to submit the same properties for all arguments and that the user is the same owner.";
        }
        if (n == -106) {
            this.errorCode_ = -106;
            return " That Branch referral code is already in use.";
        }
        if (n == -107) {
            this.errorCode_ = -107;
            return " Unable to redeem rewards. Please make sure you have credits available to redeem.";
        }
        if (n == -108) {
            this.errorCode_ = -108;
            return "BranchApp class can be used only with API level 14 or above. Please make sure your minimum API level supported is 14. If you wish to use API level below 14 consider calling getInstance(Context) instead.";
        }
        if (n == -109) {
            this.errorCode_ = -109;
            return "Branch instance is not created. Make  sure your Application class is an instance of BranchLikedApp.";
        }
        if (n == -110) {
            this.errorCode_ = -110;
            return " Unable create share options. Couldn't find applications on device to share the link.";
        }
        if (n == -111) {
            this.errorCode_ = -111;
            return " Request to Branch server timed out. Please check your internet connectivity";
        }
        if (n >= 500 || n == -112) {
            this.errorCode_ = -112;
            return " Unable to reach the Branch servers, please try again shortly.";
        }
        if (n == 409 || n == -115) {
            this.errorCode_ = -115;
            return " A resource with this identifier already exists.";
        }
        if (n >= 400 || n == -116) {
            this.errorCode_ = -116;
            return " The request was invalid.";
        }
        this.errorCode_ = -113;
        return " Check network connectivity and that you properly initialized.";
    }

    public String getMessage() {
        return this.errorMessage_;
    }

    public String toString() {
        return this.getMessage();
    }
}

