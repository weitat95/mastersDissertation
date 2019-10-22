/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.EditText
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.getqardio.android.datamodel.MeasurementNote;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.adapter.NotesAdapter;
import com.getqardio.android.ui.fragment.EditNoteFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.EditNoteFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.EditNoteFragment$$Lambda$3;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.getqardio.android.utils.ui.KeyboardHelper;
import java.util.Date;
import timber.log.Timber;

public class EditNoteFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
NotesAdapter.OnNoteActionListener {
    private MeasurementNote editableNote = null;
    private EditText noteInput;
    private NotesAdapter notesAdapter;
    private OnNoteEditedListener onNoteEditedListener;

    private void cacheEditableNote(MeasurementNote measurementNote) {
        measurementNote.userId = this.getUserId();
        measurementNote.noteType = 1;
        measurementNote.noteText = this.noteInput.getText().toString();
        measurementNote.lastUsed = new Date();
        DataHelper.NotesHelper.updateMeasurementNoteAsync((Context)this.getActivity(), this.getUserId(), measurementNote);
    }

    private void cacheNewNote() {
        MeasurementNote measurementNote = new MeasurementNote();
        measurementNote.userId = this.getUserId();
        measurementNote.measurementType = this.getMeasurementType();
        measurementNote.noteType = 1;
        measurementNote.iconRes = 5;
        measurementNote.noteText = this.noteInput.getText().toString();
        measurementNote.lastUsed = new Date();
        DataHelper.NotesHelper.addMeasurementNoteAsync((Context)this.getActivity(), this.getUserId(), measurementNote);
    }

    private void deleteAction() {
        this.setNoteInputValue("");
        this.onNoteEdited("");
    }

    private String getMeasurementType() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.MEASUREMENT_TYPE")) {
            return bundle.getString("com.getqardio.android.argument.MEASUREMENT_TYPE");
        }
        return "";
    }

    private String getNote() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.NOTE")) {
            return bundle.getString("com.getqardio.android.argument.NOTE");
        }
        return "";
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void init(View view) {
        this.setHasOptionsMenu(true);
        ListView listView = (ListView)view.findViewById(2131821035);
        this.noteInput = (EditText)view.findViewById(2131820885);
        this.notesAdapter = new NotesAdapter((Context)this.getActivity(), this);
        listView.setAdapter((ListAdapter)this.notesAdapter);
        listView.setOnItemClickListener(EditNoteFragment$$Lambda$2.lambdaFactory$(this));
        this.setNoteInputValue(this.getNote());
        this.noteInput.setOnEditorActionListener(EditNoteFragment$$Lambda$3.lambdaFactory$(this));
        new BackPanelListViewHelper(listView).setCallbacks(this.notesAdapter);
    }

    private boolean isNoteInputChanged() {
        return !this.noteInput.getText().toString().equals(this.noteInput.getTag());
    }

    private boolean isNoteValid(String string2) {
        return !TextUtils.isEmpty((CharSequence)string2.trim());
    }

    public static EditNoteFragment newInstance(long l, String object, String string2) {
        Bundle bundle = new Bundle(3);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putString("com.getqardio.android.argument.NOTE", (String)object);
        bundle.putString("com.getqardio.android.argument.MEASUREMENT_TYPE", string2);
        object = new EditNoteFragment();
        object.setArguments(bundle);
        return object;
    }

    private void onNoteEdited(String string2) {
        if (this.onNoteEditedListener != null) {
            this.onNoteEditedListener.onNoteEdited(string2);
        }
    }

    private void onNoteItemSelected(View view, long l) {
        this.onNoteEdited(this.notesAdapter.extractNoteText(view));
        DataHelper.NotesHelper.setUsageDateAsync((Context)this.getActivity(), this.getUserId(), l, System.currentTimeMillis());
    }

    private void setNoteInputValue(int n) {
        this.setNoteInputValue(this.getString(this.notesAdapter.getStringResourceForNote(n)));
    }

    private void setNoteInputValue(String string2) {
        this.noteInput.setText((CharSequence)string2);
        this.noteInput.setTag((Object)string2);
        this.noteInput.setSelection(this.noteInput.length());
    }

    /* synthetic */ void lambda$init$1(AdapterView adapterView, View view, int n, long l) {
        this.onNoteItemSelected(view, l);
    }

    /* synthetic */ boolean lambda$init$2(TextView textView, int n, KeyEvent keyEvent) {
        return n == 6 && this.textOfNoteEntered();
    }

    /* synthetic */ boolean lambda$onCreateOptionsMenu$0(MenuItem menuItem) {
        this.deleteAction();
        return true;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        AnalyticsScreenTracker.sendScreenWithMeasurementType((Context)this.getActivity(), "Notes", this.getMeasurementType());
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onNoteEditedListener = (OnNoteEditedListener)activity;
            return;
        }
        catch (ClassCastException classCastException) {
            Timber.e(classCastException, "Host activity for EditNoteFragment should implements EditNoteFragment.OnNoteEditedListener", new Object[0]);
            return;
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.NotesHelper.getNotesLoader((Context)this.getActivity(), this.getUserId(), this.getMeasurementType(), DataHelper.NotesHelper.NOTES_LIST_PROJECTION, true);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886084, menu2);
        menu2.findItem(2131821472).setOnMenuItemClickListener(EditNoteFragment$$Lambda$1.lambdaFactory$(this));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968697, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    @Override
    public void onDeleteNote(MeasurementNote measurementNote) {
        Activity activity = this.getActivity();
        if (activity != null) {
            DataHelper.NotesHelper.deleteMeasurementNoteAsync((Context)activity, this.getUserId(), measurementNote._id);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.onNoteEditedListener = null;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onEditNote(MeasurementNote measurementNote) {
        this.editableNote = measurementNote;
        switch (this.editableNote.noteType) {
            case 1: {
                this.setNoteInputValue(this.editableNote.noteText);
            }
            default: {
                break;
            }
            case 0: {
                this.setNoteInputValue(this.editableNote.textRes);
            }
        }
        this.noteInput.requestFocus();
        KeyboardHelper.showKeyboard((Context)this.getActivity(), (View)this.noteInput);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.notesAdapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.notesAdapter.swapCursor(null);
    }

    public void onPause() {
        super.onPause();
        Activity activity = this.getActivity();
        if (activity != null && activity.isFinishing()) {
            KeyboardHelper.hideKeyboard((Context)activity, (View)this.noteInput);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean textOfNoteEntered() {
        String string2 = this.noteInput.getText().toString();
        if (!this.isNoteValid(string2)) {
            return true;
        }
        if (this.isNoteInputChanged()) {
            if (this.editableNote != null) {
                this.cacheEditableNote(this.editableNote);
            } else {
                this.cacheNewNote();
            }
        }
        this.onNoteEdited(string2);
        return false;
    }

    public static interface OnNoteEditedListener {
        public void onNoteEdited(String var1);
    }

}

