/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.ContextThemeWrapper
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
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
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.fragment.ProgressDialogFragment;
import com.getqardio.android.ui.widget.HeightPanelHelper;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.DecimalInputFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InitProfileFragment
extends Fragment
implements HeightPanelHelper.HeightPanelCallback {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL dd, yyyy");
    private View birthdayLayout;
    private TextView birthdayValue;
    private InitProfileFragmentCallback callback;
    private Button doneButton;
    private HeightPanelHelper heightPanelHelper;
    private ProgressDialogFragment progressDialog;
    private View sexLayout;
    private Spinner sexSpinner;
    private TextView sexValue;
    private Date shownDate = new Date();
    private int shownWeightUnit = 0;
    private MenuItem skipButton;
    private boolean watcherEnable = true;
    private Spinner weightSpinner;
    private EditText weightValue;

    private int getSelectedGender() {
        if (this.sexSpinner.getSelectedItemPosition() == 0) {
            return 0;
        }
        return 1;
    }

    private float getShownWeight() {
        String string2 = this.weightValue.getText().toString();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            return Utils.parseNumber(string2).floatValue();
        }
        return 0.0f;
    }

    private void initValues() {
        this.setDOB(1983, 6, 18);
        this.heightPanelHelper.setHeight(180.0f, 0);
        this.setWeight(80.0f, 0);
    }

    private boolean isChangedDataValid() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isWeightValid(boolean bl) {
        Float f = Float.valueOf(this.getShownWeight());
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
                return false;
            }
        }
        if (!bl) return true;
        {
            this.weightValue.setError(null);
        }
        return true;
    }

    private void onProfileDataChanged() {
        boolean bl = false;
        if (this.isChangedDataValid()) {
            Button button = this.doneButton;
            boolean bl2 = bl;
            if (this.heightPanelHelper.isHeightValid(false)) {
                bl2 = bl;
                if (this.isWeightValid(false)) {
                    bl2 = true;
                }
            }
            button.setEnabled(bl2);
            return;
        }
        this.doneButton.setEnabled(false);
    }

    private void recalculateWeight(int n, int n2) {
        if (n != n2) {
            float f = MetricUtils.convertWeight(n, n2, this.getShownWeight());
            this.watcherEnable = false;
            this.setWeight(f, n2);
        }
        return;
        finally {
            this.watcherEnable = true;
        }
    }

    private void saveProfile() {
        Profile profile = new Profile();
        profile.userId = CustomApplication.getApplication().getCurrentUserId();
        profile.syncStatus = 1;
        profile.gender = this.getSelectedGender();
        profile.height = this.heightPanelHelper.getShownHeight();
        profile.heightUnit = this.heightPanelHelper.getShownHeightUnit();
        profile.weight = Float.valueOf(this.getShownWeight());
        profile.weightUnit = this.shownWeightUnit;
        profile.dob = new Date(this.shownDate.getTime());
        profile.locale = Locale.getDefault().getLanguage();
        this.showProgressDialog();
        DataHelper.ProfileHelper.saveProfile((Context)this.getActivity(), profile, true);
    }

    private void selectDOB() {
        if (this.shownDate != null) {
            DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener(){

                public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
                    InitProfileFragment.this.setDOB(n, n2, n3);
                }
            };
            onDateSetListener = new DatePickerDialog((Context)this.getActivity(), onDateSetListener, this.shownDate.getYear() + 1900, this.shownDate.getMonth(), this.shownDate.getDate());
            onDateSetListener.getDatePicker().setMaxDate(System.currentTimeMillis());
            onDateSetListener.show();
        }
    }

    private void setDOB(int n, int n2, int n3) {
        this.shownDate.setYear(n - 1900);
        this.shownDate.setMonth(n2);
        this.shownDate.setDate(n3);
        this.birthdayValue.setText((CharSequence)dateFormat.format(this.shownDate));
        this.onProfileDataChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setWeight(float f, int n) {
        this.shownWeightUnit = n;
        int n2 = 0;
        switch (n) {
            default: {
                n = n2;
                break;
            }
            case 1: {
                n = 0;
                break;
            }
            case 2: {
                n = 1;
                break;
            }
            case 0: {
                n = 2;
            }
        }
        String string2 = String.format("%.1f", Float.valueOf(f));
        this.weightValue.setText((CharSequence)string2);
        this.weightValue.setSelection(this.weightValue.length());
        this.weightValue.setFilters(new InputFilter[]{new DecimalInputFilter()});
        this.weightSpinner.setSelection(n);
    }

    private void setupComponents() {
        new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                if (InitProfileFragment.this.watcherEnable) {
                    InitProfileFragment.this.onProfileDataChanged();
                }
            }
        };
        this.sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
                if (InitProfileFragment.this.watcherEnable) {
                    InitProfileFragment.this.onProfileDataChanged();
                    object = (String)object.getItemAtPosition(n);
                    InitProfileFragment.this.sexValue.setText(object);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.sexLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                InitProfileFragment.this.sexSpinner.performClick();
            }
        });
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)this.getActivity(), (int)2131755014, (int)2130968731);
        arrayAdapter.setDropDownViewResource(2130968781);
        this.sexSpinner.setAdapter((SpinnerAdapter)arrayAdapter);
        this.weightValue.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                if (InitProfileFragment.this.watcherEnable) {
                    InitProfileFragment.this.onProfileDataChanged();
                }
                InitProfileFragment.this.isWeightValid(true);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                if (InitProfileFragment.this.watcherEnable) {
                    InitProfileFragment.this.onProfileDataChanged();
                    int n2 = InitProfileFragment.this.shownWeightUnit;
                    switch (n) {
                        case 0: {
                            InitProfileFragment.this.shownWeightUnit = 1;
                            break;
                        }
                        case 1: {
                            InitProfileFragment.this.shownWeightUnit = 2;
                            break;
                        }
                        case 2: {
                            InitProfileFragment.this.shownWeightUnit = 0;
                            break;
                        }
                    }
                    InitProfileFragment.this.recalculateWeight(n2, InitProfileFragment.this.shownWeightUnit);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        arrayAdapter = ArrayAdapter.createFromResource((Context)this.getActivity(), (int)2131755016, (int)2130968731);
        arrayAdapter.setDropDownViewResource(2130968781);
        this.weightSpinner.setAdapter((SpinnerAdapter)arrayAdapter);
        this.birthdayLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                InitProfileFragment.this.selectDOB();
            }
        });
        this.doneButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                InitProfileFragment.this.saveProfile();
                InitProfileFragment.this.callback.displayStartScreen();
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
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362388);
        this.initValues();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.callback = (InitProfileFragmentCallback)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            classCastException.printStackTrace();
            return;
        }
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886097, menu2);
        this.skipButton = menu2.findItem(2131821495);
        this.skipButton.setEnabled(true);
        this.skipButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            public boolean onMenuItemClick(MenuItem menuItem) {
                InitProfileFragment.this.callback.displayStartScreen();
                return true;
            }
        });
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.cloneInContext((Context)new ContextThemeWrapper((Context)this.getActivity(), 2131493130)).inflate(2130968730, viewGroup, false);
        this.setupFragment();
        this.sexLayout = layoutInflater.findViewById(2131821099);
        this.sexValue = (TextView)layoutInflater.findViewById(2131821102);
        this.sexSpinner = (Spinner)layoutInflater.findViewById(2131821101);
        this.heightPanelHelper = new HeightPanelHelper(layoutInflater.findViewById(2131821089), this);
        this.weightValue = (EditText)layoutInflater.findViewById(2131820869);
        this.weightSpinner = (Spinner)layoutInflater.findViewById(2131821105);
        this.birthdayLayout = layoutInflater.findViewById(2131821106);
        this.birthdayValue = (TextView)layoutInflater.findViewById(2131821108);
        this.doneButton = (Button)layoutInflater.findViewById(2131821109);
        this.setupComponents();
        return layoutInflater;
    }

    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }

    @Override
    public void onHeightChanged() {
        this.onProfileDataChanged();
    }

    @Override
    public void onHeightError() {
    }

    public void onPause() {
        super.onPause();
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean profileHasHeight() {
        return true;
    }

    public static interface InitProfileFragmentCallback {
        public void displayStartScreen();
    }

}

