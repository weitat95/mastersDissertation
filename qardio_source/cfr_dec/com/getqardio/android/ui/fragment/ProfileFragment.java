/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.ContextThemeWrapper
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.DatePicker
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.io.network.response.BaseError;
import com.getqardio.android.provider.AuthHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.widget.EmailEditText;
import com.getqardio.android.ui.widget.HeightPanelHelper;
import com.getqardio.android.ui.widget.PickContactView;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.ContactsHelper;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.ErrorHelper;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.NotificationHelper;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.analytics.CustomTraits;
import com.getqardio.android.utils.analytics.IdentifyHelper;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.DecimalInputFilter;
import com.getqardio.android.utils.ui.KeyboardHelper;
import com.getqardio.android.utils.ui.UIUtils;
import com.segment.analytics.Traits;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ProfileFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
HeightPanelHelper.HeightPanelCallback {
    private View birthdayLayout;
    private TextView birthdayValue;
    private SimpleDateFormat dateFormat = DateUtils.getProfileDateFormat("LLLL dd, yyyy");
    private EditText doctorEmail;
    private EditText doctorName;
    private TextWatcher doctorNameTextWatcher;
    private EmailEditText email;
    private EditText firstName;
    private TextWatcher firstNameWatcher;
    private HeightPanelHelper heightPanelHelper;
    private boolean isDoctorsEmailChangedByUser = false;
    private boolean isDoctorsNameChangedByUser = false;
    private boolean isFirstNameChanged = false;
    private boolean isHeightChangedByUser = false;
    private boolean isLastNameChanged = false;
    private boolean isSexChangedByUser = false;
    private EditText lastName;
    private TextWatcher lastNameWatcher;
    private EditText password;
    private String passwordValue;
    private PickContactView pickContactView;
    private Profile profile = null;
    private ProfileUpdatedNotificationReceiver profileUpdatedNotificationReceiver = new ProfileUpdatedNotificationReceiver();
    private ProgressDialogFragment progressDialog;
    private MenuItem saveButton;
    private SexItemSelectedListener sexItemSelectedListener = new SexItemSelectedListener();
    private View sexLayout;
    private Spinner sexSpinner;
    private TextView sexValue;
    private boolean showDoctorEmailError = false;
    private Date shownDate = null;
    private Integer shownSex = null;
    private int shownWeightUnit = 0;
    private boolean watcherEnable = true;
    private AdapterView.OnItemSelectedListener weightItemSelectedListener;
    private Spinner weightSpinner;
    private EditText weightValue;

    public ProfileFragment() {
        this.firstNameWatcher = new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment profileFragment = ProfileFragment.this;
                    String string2 = ProfileFragment.this.profile == null ? "" : ProfileFragment.access$600((ProfileFragment)ProfileFragment.this).firstName;
                    boolean bl = !TextUtils.equals((CharSequence)editable, (CharSequence)string2);
                    profileFragment.isFirstNameChanged = bl;
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.watcherEnable && ProfileFragment.this.firstName.isFocused()) {
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        };
        this.lastNameWatcher = new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             */
            public void afterTextChanged(Editable editable) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment profileFragment = ProfileFragment.this;
                    String string2 = ProfileFragment.this.profile == null ? "" : ProfileFragment.access$600((ProfileFragment)ProfileFragment.this).lastName;
                    boolean bl = !TextUtils.equals((CharSequence)editable, (CharSequence)string2);
                    profileFragment.isLastNameChanged = bl;
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.watcherEnable && ProfileFragment.this.lastName.isFocused()) {
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        };
        this.doctorNameTextWatcher = new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.doctorEmail.setError(null);
                    ProfileFragment.this.showDoctorEmailError = false;
                    ProfileFragment.this.isDoctorsEmailChangedByUser = true;
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        };
        this.weightItemSelectedListener = new AdapterView.OnItemSelectedListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                int n2 = ProfileFragment.this.shownWeightUnit;
                switch (n) {
                    case 0: {
                        ProfileFragment.this.shownWeightUnit = 1;
                        break;
                    }
                    case 1: {
                        ProfileFragment.this.shownWeightUnit = 2;
                        break;
                    }
                    case 2: {
                        ProfileFragment.this.shownWeightUnit = 0;
                        break;
                    }
                }
                ProfileFragment.this.recalculateWeight(n2, ProfileFragment.this.shownWeightUnit);
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.onProfileDataChanged();
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void applyData(Profile profile) {
        if (!this.isFirstNameChanged) {
            this.setTextWithoutEvent(this.firstName, profile.firstName);
        }
        if (!this.isLastNameChanged) {
            this.setTextWithoutEvent(this.lastName, profile.lastName);
        }
        this.setTextWithoutEvent(this.email, profile.getEmail());
        if (!this.isSexChangedByUser) {
            this.shownSex = profile.gender;
            if (this.shownSex != null) {
                this.sexItemSelectedListener.setWatcherEnable(false);
                switch (this.shownSex) {
                    case 0: {
                        this.sexSpinner.setSelection(0);
                        this.sexItemSelectedListener.setWatcherEnable(true);
                    }
                    default: {
                        break;
                    }
                    case 1: {
                        this.sexSpinner.setSelection(1);
                        this.sexItemSelectedListener.setWatcherEnable(true);
                    }
                }
            }
        }
        if (profile.height != null && profile.heightUnit != null && !this.isHeightChangedByUser) {
            this.heightPanelHelper.setHeight(profile.height.floatValue(), profile.heightUnit);
        }
        if (profile.weight != null) {
            this.watcherEnable = false;
            this.setWeight(profile.weight.floatValue(), profile.weightUnit);
        }
        if (!this.isDoctorsEmailChangedByUser) {
            this.setTextWithoutEvent(this.doctorEmail, profile.doctorEmail);
        }
        if (!this.isDoctorsNameChangedByUser) {
            this.setTextWithoutEvent(this.doctorName, profile.doctorName);
        }
        this.shownDate = null;
        if (profile.dob != null) {
            this.shownDate = new Date();
            this.shownDate.setTime(profile.dob.getTime());
        }
        if (this.shownDate != null && !this.isProfileValueChangedByUser(this.birthdayValue, this.dateFormat.format(this.shownDate))) {
            this.birthdayValue.setText((CharSequence)this.dateFormat.format(this.shownDate));
        }
        if (this.saveButton != null && !this.isDataChanged()) {
            this.setSaveMenuItemEnabled(false);
        }
        return;
        finally {
            this.watcherEnable = true;
        }
    }

    private void applyPassword(String string2) {
        this.passwordValue = string2;
        this.setTextWithoutEvent(this.password, string2);
    }

    private boolean floatEqual(float f, float f2) {
        return Math.abs(f - f2) < 1.0E-6f;
    }

    private String getDoctorEmailValue() {
        return this.doctorEmail.getText().toString().trim();
    }

    private String getDoctorNameValue() {
        return this.doctorName.getText().toString();
    }

    private String getFirstNameValue() {
        return this.firstName.getText().toString().trim();
    }

    private String getLastNameValue() {
        return this.lastName.getText().toString().trim();
    }

    private String getNewPassword() {
        if (this.isPasswordChanged()) {
            return this.password.getText().toString().trim();
        }
        return null;
    }

    private Float getShownWeight() {
        String string2 = this.weightValue.getText().toString();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            return Utils.parseNumber(string2);
        }
        return null;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void identifyUser() {
        String string2;
        CustomTraits customTraits = new CustomTraits();
        if (this.shownDate != null) {
            customTraits.putAge(DateUtils.calculateDiffYears(this.shownDate, new Date()));
        }
        if (this.shownSex != null) {
            customTraits.putGender(Constants.Gender.int2String(this.shownSex));
        }
        if ((string2 = CustomApplication.getApplication().getCurrentUserTrackingId()) != null) {
            IdentifyHelper.identify((Context)this.getActivity(), string2, customTraits);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isChangedDataValid() {
        String string2;
        boolean bl;
        boolean bl2 = Validator.isNameValid(this.firstName, true, 2131362002, true);
        String string3 = this.lastName.getText().toString();
        if (!string3.equals(string2 = this.profile == null ? "" : this.profile.lastName)) {
            bl2 &= Validator.isNameValid(this.lastName, false, 2131362003, true);
        } else {
            this.lastName.setError(null);
        }
        bl2 = bl = bl2 & Validator.isEmailValid(this.email, 2131362001, 2131361964) & this.isPasswordValid(true);
        if (this.isDoctorsNameChangedByUser) {
            string2 = this.getDoctorNameValue();
            boolean bl3 = TextUtils.isEmpty((CharSequence)this.getDoctorNameValue().trim()) && string2.length() > 0;
            if (bl3) {
                bl2 = false;
            } else if (!Validator.isNameValid(this.doctorName, false, 2131362002, true)) {
                bl2 = false;
            } else {
                this.doctorName.setError(null);
                bl2 = bl;
            }
        }
        if (!TextUtils.isEmpty((CharSequence)this.getDoctorEmailValue()) && !Validator.isEmailValid(this.doctorEmail, 2131362004, 2131361964, this.showDoctorEmailError)) {
            return false;
        }
        this.doctorEmail.setError(null);
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isDataChanged() {
        return this.profile != null && (this.profile.firstName != null && !this.getFirstNameValue().equals(this.profile.firstName) || this.profile.lastName != null && !this.getLastNameValue().equals(this.profile.lastName) || this.isEmailChanged() || this.isPasswordChanged() || this.profile.gender == null && this.shownSex != null || this.profile.gender != null && this.shownSex != null && this.shownSex.intValue() != this.profile.gender.intValue() || this.profile.height == null && this.heightPanelHelper.getShownHeight() != null || this.profile.height != null && this.heightPanelHelper.getShownHeight() != null && !this.floatEqual(this.profile.height.floatValue(), this.heightPanelHelper.getShownHeight().floatValue()) || this.profile.heightUnit != null && this.profile.heightUnit.intValue() != this.heightPanelHelper.getShownHeightUnit() || this.profile.weight == null && this.getShownWeight() != null || this.profile.weight != null && this.getShownWeight() != null && !this.floatEqual(this.profile.weight.floatValue(), this.getShownWeight().floatValue()) || this.profile.weightUnit != null && this.profile.weightUnit != this.shownWeightUnit || this.profile.dob == null && this.shownDate != null || this.profile.dob != null && this.shownDate != null && !DateUtils.sameDate(this.profile.dob, this.shownDate) || !this.getDoctorEmailValue().equals(this.profile.doctorEmail) || !this.getDoctorNameValue().trim().equals(this.profile.doctorName));
    }

    private boolean isEmailChanged() {
        return this.profile != null && !this.email.getText().toString().equals(this.profile.getEmail());
    }

    private boolean isPasswordChanged() {
        return !this.password.getText().toString().trim().equals(this.passwordValue);
    }

    private boolean isPasswordValid(boolean bl) {
        return Validator.isEditValid(Constants.Regexp.PASSWORD_PATTERN, this.password, 2131361884, 2131361884, bl);
    }

    private boolean isProfileValueChangedByUser(TextView textView, String string2) {
        return this.isProfileValueChangedByUser(textView.getText().toString(), string2);
    }

    private boolean isProfileValueChangedByUser(String string2, String string3) {
        return !TextUtils.isEmpty((CharSequence)string2) && !TextUtils.equals((CharSequence)string2, (CharSequence)string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isWeightValid(boolean bl) {
        Float f = this.getShownWeight();
        if (f == null && (this.profile == null || this.profile.weight == null)) {
            if (!bl) return true;
            {
                this.weightValue.setError(null);
                return true;
            }
        } else {
            if (f == null) {
                if (!bl) return false;
                {
                    this.weightValue.setError((CharSequence)this.getString(2131362255));
                }
                return false;
            }
            if (!Validator.isWeightMaxValid(this.shownWeightUnit, f.floatValue())) {
                if (!bl) return false;
                {
                    this.weightValue.setError((CharSequence)this.getString(2131362257));
                }
                return false;
            }
            if (!Validator.isWeightMinValid(this.shownWeightUnit, f.floatValue())) {
                if (!bl) return false;
                {
                    this.weightValue.setError((CharSequence)this.getString(2131362255));
                }
                return false;
            }
            if (!bl) return true;
            {
                this.weightValue.setError(null);
                return true;
            }
        }
    }

    public static ProfileFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    private void onProfileDataChanged() {
        block8: {
            boolean bl;
            boolean bl2;
            block9: {
                block7: {
                    bl2 = false;
                    if (this.saveButton == null) break block7;
                    if (!this.isChangedDataValid()) break block8;
                    bl = Utils.isNetworkAvaliable((Context)this.getActivity());
                    if (!this.isEmailChanged() || bl) break block9;
                    this.email.setError(this.getString(2131361918));
                    this.setSaveMenuItemEnabled(false);
                }
                return;
            }
            if (this.isPasswordChanged() && !bl) {
                this.password.setError((CharSequence)this.getString(2131361919));
                this.setSaveMenuItemEnabled(false);
                return;
            }
            bl = bl2;
            if (this.isDataChanged()) {
                bl = bl2;
                if (this.heightPanelHelper.isHeightValid(false)) {
                    bl = bl2;
                    if (this.isWeightValid(false)) {
                        bl = true;
                    }
                }
            }
            this.setSaveMenuItemEnabled(bl);
            return;
        }
        this.setSaveMenuItemEnabled(false);
    }

    private void onProfileUpdatedNotification(boolean bl, List<BaseError> object) {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if (!bl && object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                BaseError baseError = (BaseError)object.next();
                if (ErrorHelper.getErrorId(baseError.messageKey) != 10) continue;
                this.email.setError(baseError.defaultMessageText);
                this.email.lockError();
            }
        } else {
            this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        }
    }

    private void recalculateWeight(int n, int n2) {
        Float f = this.getShownWeight();
        if (n != n2 && f != null) {
            float f2 = MetricUtils.convertWeight(n, n2, f.floatValue());
            this.watcherEnable = false;
            this.setWeight(f2, n2);
        }
        return;
        finally {
            this.watcherEnable = true;
        }
    }

    private void requestFullProfileSync() {
        DataHelper.ProfileHelper.requestProfileUpdate((Context)CustomApplication.getApplication(), this.getUserId());
    }

    private void saveProfile() {
        boolean bl = this.isEmailChanged();
        Profile profile = new Profile();
        profile.userId = this.profile.userId;
        profile.syncStatus = this.profile.syncStatus;
        profile.firstName = this.getFirstNameValue();
        profile.lastName = this.getLastNameValue();
        if (bl) {
            profile.setEmail(this.email.getText().toString());
        }
        profile.gender = this.shownSex;
        profile.height = this.heightPanelHelper.getShownHeight();
        profile.heightUnit = this.heightPanelHelper.getShownHeightUnit();
        profile.weight = this.getShownWeight();
        profile.weightUnit = this.shownWeightUnit;
        profile.dob = this.shownDate;
        profile.locale = Locale.getDefault().getLanguage();
        profile.doctorName = this.doctorName.getEditableText().toString().trim();
        profile.doctorEmail = this.doctorEmail.getEditableText().toString().trim();
        this.showProgressDialog();
        DataHelper.ProfileHelper.saveProfile((Context)this.getActivity(), profile, true, bl, this.getNewPassword());
    }

    /*
     * Enabled aggressive block sorting
     */
    private void selectDOB() {
        Date date = this.shownDate != null ? this.shownDate : new Date();
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
                ProfileFragment.this.setDOB(n, n2, n3);
            }
        };
        date = new DatePickerDialog((Context)this.getActivity(), 2131493368, onDateSetListener, date.getYear() + 1900, date.getMonth(), date.getDate());
        date.getDatePicker().setMaxDate(System.currentTimeMillis());
        date.show();
    }

    private void setDOB(int n, int n2, int n3) {
        if (this.shownDate == null) {
            this.shownDate = new Date();
        }
        this.shownDate.setYear(n - 1900);
        this.shownDate.setMonth(n2);
        this.shownDate.setDate(n3);
        this.birthdayValue.setText((CharSequence)this.dateFormat.format(this.shownDate));
        this.onProfileDataChanged();
    }

    private void setSaveMenuItemEnabled(boolean bl) {
        UIUtils.enableMenuItemAndChangeColor(this.saveButton, bl);
    }

    private void setTextWithoutEvent(EditText editText, String string2) {
        this.watcherEnable = false;
        try {
            editText.setError(null);
            editText.setText((CharSequence)string2);
            editText.setSelection(editText.length());
            return;
        }
        finally {
            this.watcherEnable = true;
        }
    }

    private void setWeight(float f, int n) {
        this.shownWeightUnit = n;
        n = MetricUtils.getWeightUnitPositionInStringsArray(n);
        String string2 = String.format(Locale.getDefault(), "%.1f", Float.valueOf(f));
        this.weightValue.setText((CharSequence)string2);
        this.weightValue.setSelection(this.weightValue.length());
        this.weightSpinner.setOnItemSelectedListener(null);
        this.weightSpinner.setSelection(n);
        this.weightSpinner.setOnItemSelectedListener(this.weightItemSelectedListener);
    }

    private void setupComponents() {
        this.firstName.addTextChangedListener(this.firstNameWatcher);
        this.lastName.addTextChangedListener(this.lastNameWatcher);
        this.email.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.profile != null) {
                    ProfileFragment.this.email.unlockErrorIfChanged(ProfileFragment.this.profile.getEmail());
                }
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        });
        this.password.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        });
        this.doctorEmail.addTextChangedListener(this.doctorNameTextWatcher);
        this.doctorEmail.setOnKeyListener(new View.OnKeyListener(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean onKey(View view, int n, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) return false;
                if (n != 66) return false;
                boolean bl = true;
                if (!bl) return bl;
                ProfileFragment.this.showDoctorEmailError = true;
                ProfileFragment.this.onProfileDataChanged();
                return bl;
            }
        });
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)this.getActivity(), (int)2131755014, (int)17367048);
        arrayAdapter.setDropDownViewResource(2130968781);
        this.sexSpinner.setAdapter((SpinnerAdapter)arrayAdapter);
        this.sexSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)this.sexItemSelectedListener);
        this.sexLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ProfileFragment.this.sexSpinner.performClick();
            }
        });
        this.doctorName.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.isDoctorsNameChangedByUser = true;
                    ProfileFragment.this.onProfileDataChanged();
                }
            }
        });
        this.weightValue.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                if (ProfileFragment.this.watcherEnable) {
                    ProfileFragment.this.onProfileDataChanged();
                }
                ProfileFragment.this.isWeightValid(true);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.weightSpinner.setOnItemSelectedListener(this.weightItemSelectedListener);
        this.weightValue.setOnKeyListener(new View.OnKeyListener(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean onKey(View view, int n, KeyEvent keyEvent) {
                if (keyEvent.getAction() != 0) return false;
                if (n != 66) return false;
                boolean bl = true;
                if (!bl) return bl;
                ProfileFragment.this.birthdayLayout.callOnClick();
                return bl;
            }
        });
        arrayAdapter = ArrayAdapter.createFromResource((Context)this.getActivity(), (int)2131755016, (int)2130968782);
        arrayAdapter.setDropDownViewResource(2130968781);
        this.weightSpinner.setAdapter((SpinnerAdapter)arrayAdapter);
        this.weightSpinner.setSelection(2);
        this.birthdayLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                ProfileFragment.this.selectDOB();
            }
        });
    }

    private void setupFragment() {
        this.setHasOptionsMenu(true);
    }

    private void showProgressDialog() {
        if (this.progressDialog == null) {
            this.progressDialog = ProgressDialogFragment.newInstance(false);
        }
        this.progressDialog.show(this.getFragmentManager(), null);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362021);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Profile");
        this.requestFullProfileSync();
        this.getLoaderManager().restartLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().restartLoader(2, null, (LoaderManager.LoaderCallbacks)this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        object = ContactsHelper.onActivityResult(this.getActivity(), n, n2, (Intent)object);
        if (object != null) {
            String string2 = ((ContactsHelper.Contact)object).getEmail();
            this.showDoctorEmailError = true;
            this.doctorEmail.removeTextChangedListener(this.doctorNameTextWatcher);
            this.doctorEmail.setText((CharSequence)string2);
            this.onProfileDataChanged();
            this.doctorEmail.addTextChangedListener(this.doctorNameTextWatcher);
            n = this.doctorEmail.getText().length();
            if (n > 0) {
                this.doctorEmail.setSelection(n);
            }
            n = !TextUtils.isEmpty((CharSequence)(object = ((ContactsHelper.Contact)object).getName())) ? ((String)object).length() : 0;
            this.doctorName.setText((CharSequence)object);
            this.doctorName.setSelection(n);
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), this.getUserId(), DataHelper.ProfileHelper.PROFILE_SCREEN_PROJECTION);
            }
            case 2: 
        }
        return AuthHelper.getUserLoader((Context)this.getActivity(), this.getUserId(), AuthHelper.PASSWORD_PROJECTION);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
        menuInflater.inflate(2131886094, menu2);
        this.saveButton = menu2.findItem(2131821493);
        this.setSaveMenuItemEnabled(false);
        this.saveButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            public boolean onMenuItemClick(MenuItem menuItem) {
                View view;
                menuItem = ProfileFragment.this.getActivity();
                if (menuItem != null && (view = menuItem.getCurrentFocus()) != null) {
                    view.clearFocus();
                    KeyboardHelper.hideKeyboard((Context)menuItem, view);
                }
                ProfileFragment.this.identifyUser();
                ProfileFragment.this.saveProfile();
                return true;
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.cloneInContext((Context)new ContextThemeWrapper((Context)this.getActivity(), 2131493130)).inflate(2130968780, viewGroup, false);
        this.setupFragment();
        this.sexLayout = layoutInflater.findViewById(2131821099);
        this.sexValue = (TextView)layoutInflater.findViewById(2131821102);
        this.sexSpinner = (Spinner)layoutInflater.findViewById(2131821101);
        this.firstName = (EditText)layoutInflater.findViewById(2131821207);
        this.firstName.setError(null);
        this.lastName = (EditText)layoutInflater.findViewById(2131821208);
        this.email = (EmailEditText)layoutInflater.findViewById(2131821209);
        this.email.setError(null);
        this.password = (EditText)layoutInflater.findViewById(2131821210);
        this.password.setError(null);
        this.doctorEmail = (EditText)layoutInflater.findViewById(2131821214);
        this.doctorName = (EditText)layoutInflater.findViewById(2131821212);
        this.heightPanelHelper = new HeightPanelHelper(layoutInflater.findViewById(2131821089), this);
        this.weightValue = (EditText)layoutInflater.findViewById(2131820869);
        this.weightValue.setFilters(new InputFilter[]{new DecimalInputFilter()});
        this.weightSpinner = (Spinner)layoutInflater.findViewById(2131821105);
        this.birthdayLayout = layoutInflater.findViewById(2131821106);
        this.birthdayValue = (TextView)layoutInflater.findViewById(2131821108);
        this.pickContactView = (PickContactView)layoutInflater.findViewById(2131821213);
        viewGroup = this.getActivity();
        if (viewGroup instanceof PickContactView.Callback) {
            this.pickContactView.setCallback((PickContactView.Callback)viewGroup);
        } else {
            this.pickContactView.setVisibility(4);
        }
        this.setupComponents();
        return layoutInflater;
    }

    @Override
    public void onHeightChanged() {
        this.isHeightChangedByUser = true;
        this.onProfileDataChanged();
    }

    @Override
    public void onHeightError() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> activity, Cursor cursor) {
        switch (activity.getId()) {
            case 1: {
                if (!cursor.moveToFirst()) return;
                {
                    this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
                    if (this.profile.userId.longValue() != this.getUserId()) return;
                    {
                        this.applyData(this.profile);
                        return;
                    }
                }
            }
            default: {
                return;
            }
            case 2: {
                activity = this.getActivity();
                if (!cursor.moveToFirst() || activity == null) return;
                this.applyPassword(AuthHelper.parsePassword((Context)activity, cursor));
                return;
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        View view;
        Activity activity;
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.profileUpdatedNotificationReceiver);
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
        if ((view = (activity = this.getActivity()).getCurrentFocus()) != null) {
            ((InputMethodManager)activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onResume() {
        super.onResume();
        this.dateFormat = DateUtils.getProfileDateFormat("LLLL dd, yyyy");
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.profileUpdatedNotificationReceiver, new IntentFilter("com.getqardio.android.Notifications.UPDATE_PROFILE"));
    }

    @Override
    public boolean profileHasHeight() {
        return this.profile != null && this.profile.height != null;
    }

    private class ProfileUpdatedNotificationReceiver
    extends BroadcastReceiver {
        private ProfileUpdatedNotificationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (ProfileFragment.this.isAdded()) {
                ProfileFragment.this.onProfileUpdatedNotification(NotificationHelper.UpdateProfileNotification.isSuccessful(intent), ErrorHelper.getErrorsFromIntent(intent));
            }
        }
    }

    private class SexItemSelectedListener
    implements AdapterView.OnItemSelectedListener {
        private boolean watcherEnable = true;

        private SexItemSelectedListener() {
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void onItemSelected(AdapterView<?> object, View object2, int n, long l) {
            void var3_5;
            ProfileFragment profileFragment = ProfileFragment.this;
            int n2 = var3_5 == true ? 1 : 0;
            profileFragment.shownSex = n2;
            String string2 = (String)object.getItemAtPosition((int)var3_5);
            ProfileFragment.this.sexValue.setText((CharSequence)string2);
            if (this.watcherEnable) {
                ProfileFragment.this.isSexChangedByUser = true;
                ProfileFragment.this.onProfileDataChanged();
                return;
            }
            this.watcherEnable = true;
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }

        public void setWatcherEnable(boolean bl) {
            this.watcherEnable = bl;
        }
    }

}

