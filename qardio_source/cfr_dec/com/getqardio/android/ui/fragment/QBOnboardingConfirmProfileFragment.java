/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.Window
 *  android.widget.Button
 *  android.widget.DatePicker
 *  android.widget.EditText
 *  android.widget.RadioGroup
 *  android.widget.RadioGroup$OnCheckedChangeListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import com.getqardio.android.ui.widget.CustomEditText;
import com.getqardio.android.ui.widget.HeightPanelHelper;
import com.getqardio.android.ui.widget.SimpleTextWatcher;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.ui.KeyboardHelper;
import com.getqardio.android.utils.ui.UIUtils;
import com.getqardio.android.utils.wizard.OnDoneClickListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QBOnboardingConfirmProfileFragment
extends AbstractQBOnboardingFragment
implements HeightPanelHelper.HeightPanelCallback {
    private TextView birthdayValue;
    private QBOnboardingDataProvider dataProvider;
    private SimpleDateFormat dateFormat = DateUtils.getProfileDateFormat("LLLL dd, yyyy");
    private Button doneBtn;
    private RadioGroup genderRadioGroup;
    private HeightPanelHelper heightPanelHelper;
    private Profile profile;
    private boolean profileValid = false;
    private EditText qbNameEditText;
    Pattern qbNamePattern = Pattern.compile("[a-zA-Z0-9]+");
    private Date shownDate = null;
    private Integer shownSex;
    private Integer shownWeightUnit;
    private RadioGroup weightUnitRadioGroup;

    private void applyProfileData() {
        this.profile = this.dataProvider.getProfile();
        if (this.profile != null && this.profile.userId.equals(CustomApplication.getApplication().getCurrentUserId())) {
            this.applyProfileData(this.profile);
        }
    }

    private void applyProfileData(Profile object) {
        String string2;
        if (((Profile)object).dob != null) {
            this.shownDate = new Date();
            this.shownDate.setTime(((Profile)object).dob.getTime());
            this.birthdayValue.setText((CharSequence)this.dateFormat.format(this.shownDate));
        }
        if (((Profile)object).gender != null) {
            this.showGender((Profile)object);
        }
        if (((Profile)object).weightUnit != null) {
            this.showWeightUnit((Profile)object);
        }
        if (((Profile)object).height != null && ((Profile)object).heightUnit != null) {
            this.heightPanelHelper.setHeight(((Profile)object).height.floatValue(), ((Profile)object).heightUnit);
        }
        String string3 = string2 = ((Profile)object).qbVisibleName;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            string3 = ((Profile)object).firstName;
        }
        if (string3 != null) {
            string3 = string3.toLowerCase().replaceAll("[^a-z0-9]", "");
            object = string3;
            if (string3.length() > 6) {
                object = string3.substring(0, 6).toLowerCase();
            }
            this.qbNameEditText.setText((CharSequence)object);
            if (TextUtils.isEmpty((CharSequence)object)) {
                UIUtils.findTopTextInputLayout((View)this.qbNameEditText).setError(this.getString(2131362332));
            }
        }
        this.onProfileDataChanged();
    }

    private Date buildDate(int n, int n2, int n3) {
        Date date = new Date();
        date.setYear(n - 1900);
        date.setMonth(n2);
        date.setDate(n3);
        return date;
    }

    private void checkBirthDate() {
        if (this.profile != null && this.profile.dob == null) {
            Date date = new Date();
            date.setYear(date.getYear() - 35);
            this.profile.dob = date;
        }
    }

    private void checkDoneButton() {
        this.doneBtn.setEnabled(this.profileValid);
    }

    private boolean isDobValid() {
        return this.shownDate != null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isProfileDataChanged() {
        boolean bl = false;
        if (this.profile == null) return true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = true;
        boolean bl6 = true;
        boolean bl7 = true;
        if (this.shownSex != null) {
            bl2 = !this.shownSex.equals(this.profile.gender);
        }
        if (this.profile.height != null) {
            bl5 = !this.profile.height.equals(this.heightPanelHelper.getShownHeight());
        }
        if (this.profile.heightUnit != null) {
            bl6 = !this.profile.heightUnit.equals(this.heightPanelHelper.getShownHeightUnit());
        }
        if (this.profile.weightUnit != null) {
            bl4 = !this.profile.weightUnit.equals(this.shownWeightUnit);
        }
        if (this.profile.dob != null) {
            bl3 = !this.profile.dob.equals(this.shownDate);
        }
        if (this.profile.qbVisibleName != null) {
            bl7 = !this.profile.qbVisibleName.equals(this.qbNameEditText.getText().toString());
        }
        if (bl2) return true;
        if (bl5) return true;
        if (bl6) return true;
        if (bl4) return true;
        if (bl3) return true;
        if (!bl7) return bl;
        return true;
    }

    public static QBOnboardingConfirmProfileFragment newInstance() {
        return new QBOnboardingConfirmProfileFragment();
    }

    private void onProfileDataChanged() {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.isDobValid()) {
            bl2 = bl;
            if (this.heightPanelHelper.isHeightValid(false)) {
                bl2 = bl;
                if (this.genderRadioGroup.getCheckedRadioButtonId() != -1) {
                    bl2 = bl;
                    if (this.weightUnitRadioGroup.getCheckedRadioButtonId() != -1) {
                        bl2 = bl;
                        if (this.qbNamePattern.matcher((CharSequence)this.qbNameEditText.getText()).matches()) {
                            bl2 = true;
                        }
                    }
                }
            }
        }
        this.profileValid = bl2;
        this.checkDoneButton();
    }

    /*
     * Enabled aggressive block sorting
     */
    private Profile saveProfile() {
        int n = 0;
        Profile profile = new Profile();
        if (this.profile != null) {
            profile.userId = this.profile.userId;
            profile.refId = this.profile.refId;
            profile.syncStatus = this.profile.syncStatus;
            profile.firstName = this.profile.firstName;
            profile.lastName = this.profile.lastName;
            profile.setEmail(this.profile.getEmail());
            profile.weight = this.profile.weight;
            profile.locale = this.profile.locale;
            profile.doctorName = this.profile.doctorName;
            profile.doctorEmail = this.profile.doctorEmail;
        }
        int n2 = this.shownSex == null ? 0 : this.shownSex;
        profile.gender = n2;
        n2 = this.shownWeightUnit == null ? n : this.shownWeightUnit;
        profile.weightUnit = n2;
        profile.dob = this.shownDate;
        profile.height = this.heightPanelHelper.getShownHeight();
        profile.heightUnit = this.heightPanelHelper.getShownHeightUnit();
        profile.qbVisibleName = this.qbNameEditText.getText().toString().toLowerCase();
        DataHelper.ProfileHelper.saveProfile((Context)this.getActivity(), profile, true);
        return profile;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void selectDOB() {
        Date date = this.shownDate != null ? this.shownDate : this.buildDate(1983, 6, 18);
        DatePickerDialog.OnDateSetListener onDateSetListener = QBOnboardingConfirmProfileFragment$$Lambda$5.lambdaFactory$(this);
        date = new DatePickerDialog((Context)this.getActivity(), 2131493368, onDateSetListener, date.getYear() + 1900, date.getMonth(), date.getDate());
        date.getDatePicker().setMaxDate(System.currentTimeMillis());
        date.show();
    }

    private void setDOB(int n, int n2, int n3) {
        if (this.shownDate == null) {
            this.shownDate = new Date();
        }
        this.shownDate = this.buildDate(n, n2, n3);
        ((TextView)this.rootView.findViewById(2131821108)).setText((CharSequence)this.dateFormat.format(this.shownDate));
        this.onProfileDataChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showGender(Profile profile) {
        int n = 0;
        int n2 = 0;
        if (profile.gender != null) {
            switch (profile.gender) {
                case 0: {
                    this.shownSex = 0;
                    n2 = 2131821244;
                }
                default: {
                    break;
                }
                case 1: {
                    this.shownSex = 1;
                    n2 = 2131821245;
                }
            }
            n = n2;
            if (n2 != 0) {
                this.genderRadioGroup.check(n2);
                n = n2;
            }
        }
        if (n != 0) {
            this.genderRadioGroup.check(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showWeightUnit(Profile profile) {
        int n = 0;
        switch (profile.weightUnit) {
            case 0: {
                this.shownWeightUnit = 0;
                n = 2131821247;
                break;
            }
            case 1: {
                this.shownWeightUnit = 1;
                n = 2131821248;
                break;
            }
            case 2: {
                this.shownWeightUnit = 2;
                n = 2131821249;
                break;
            }
        }
        if (n != 0) {
            this.weightUnitRadioGroup.check(n);
        }
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968794;
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$onViewCreated$0(RadioGroup radioGroup, int n) {
        n = n == 2131821244 ? 0 : 1;
        this.shownSex = n;
        this.onProfileDataChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$onViewCreated$1(RadioGroup radioGroup, int n) {
        switch (n) {
            case 2131821247: {
                this.shownWeightUnit = 0;
                break;
            }
            case 2131821248: {
                this.shownWeightUnit = 1;
                break;
            }
            case 2131821249: {
                this.shownWeightUnit = 2;
                break;
            }
        }
        this.onProfileDataChanged();
    }

    /* synthetic */ void lambda$onViewCreated$2(View view) {
        this.checkBirthDate();
        if (this.isProfileDataChanged()) {
            this.profile = this.saveProfile();
            this.dataProvider.setProfile(this.profile);
        }
        KeyboardHelper.hideKeyboard((Context)this.getActivity(), view);
        this.onDoneClickListener.onDoneClick();
    }

    /* synthetic */ void lambda$onViewCreated$3(View view) {
        this.selectDOB();
    }

    /* synthetic */ void lambda$selectDOB$4(DatePicker datePicker, int n, int n2, int n3) {
        this.setDOB(n, n2, n3);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        QardioBaseDeviceAnalyticsTracker.trackConfirmProfileScreen((Context)this.getActivity());
        this.dataProvider = (QBOnboardingDataProvider)this.getParentFragment();
        this.applyProfileData();
    }

    @Override
    public void onHeightChanged() {
        this.onProfileDataChanged();
    }

    @Override
    public void onHeightError() {
        this.onProfileDataChanged();
    }

    public void onPause() {
        this.getActivity().getWindow().setSoftInputMode(48);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getActivity().getWindow().setSoftInputMode(16);
    }

    @Override
    public void onViewCreated(View object, Bundle bundle) {
        super.onViewCreated((View)object, bundle);
        this.qbNameEditText = (EditText)object.findViewById(2131821250);
        this.qbNameEditText.addTextChangedListener((TextWatcher)new SimpleTextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void afterTextChanged(Editable editable) {
                TextInputLayout textInputLayout = UIUtils.findTopTextInputLayout((View)QBOnboardingConfirmProfileFragment.this.qbNameEditText);
                if (QBOnboardingConfirmProfileFragment.this.qbNamePattern.matcher(editable.toString()).matches()) {
                    textInputLayout.setError(null);
                } else {
                    textInputLayout.setError(QBOnboardingConfirmProfileFragment.this.getString(2131362332));
                }
                QBOnboardingConfirmProfileFragment.this.onProfileDataChanged();
            }
        });
        this.heightPanelHelper = new HeightPanelHelper(this.rootView.findViewById(2131821089), this, 2130968774);
        object = (CustomEditText)this.rootView.findViewById(2131821094);
        object.setTextColor(this.getResources().getColor(2131689621));
        object.setHintTextColor(this.getResources().getColor(2131689621));
        object = this.rootView.findViewById(2131821106);
        this.birthdayValue = (TextView)this.rootView.findViewById(2131821108);
        this.birthdayValue.setTextColor(-1);
        this.genderRadioGroup = (RadioGroup)this.rootView.findViewById(2131821243);
        this.genderRadioGroup.setOnCheckedChangeListener(QBOnboardingConfirmProfileFragment$$Lambda$1.lambdaFactory$(this));
        this.weightUnitRadioGroup = (RadioGroup)this.rootView.findViewById(2131821246);
        this.weightUnitRadioGroup.setOnCheckedChangeListener(QBOnboardingConfirmProfileFragment$$Lambda$2.lambdaFactory$(this));
        this.doneBtn = (Button)this.rootView.findViewById(2131821251);
        this.doneBtn.setOnClickListener(QBOnboardingConfirmProfileFragment$$Lambda$3.lambdaFactory$(this));
        object.setOnClickListener(QBOnboardingConfirmProfileFragment$$Lambda$4.lambdaFactory$(this));
    }

    @Override
    public boolean profileHasHeight() {
        return true;
    }

}

