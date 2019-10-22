/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.graphics.Typeface
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.datamodel.MeasurementNote;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;

public class NotesAdapter
extends CursorAdapter
implements BackPanelListViewHelper.BackPanelCallbacks {
    private View.OnClickListener deleteNoteListener;
    private View.OnClickListener editNoteListener;
    private OnNoteActionListener onNoteActionListener;
    private Typeface robotoLightTypeface;

    public NotesAdapter(Context context, OnNoteActionListener onNoteActionListener) {
        super(context, null, 0);
        this.onNoteActionListener = onNoteActionListener;
        this.robotoLightTypeface = Typeface.create((String)"sans-serif-light", (int)0);
        this.deleteNoteListener = new View.OnClickListener(){

            public void onClick(View view) {
                if (NotesAdapter.this.onNoteActionListener != null) {
                    NotesAdapter.this.onNoteActionListener.onDeleteNote((MeasurementNote)view.getTag());
                }
            }
        };
        this.editNoteListener = new View.OnClickListener(){

            public void onClick(View view) {
                if (NotesAdapter.this.onNoteActionListener != null) {
                    NotesAdapter.this.onNoteActionListener.onEditNote(((TagHolder)view.getTag()).note);
                }
                BackPanelListViewHelper.hideBackPanelWithAnimation(((TagHolder)view.getTag()).listItemView);
            }
        };
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void bindView(View view, Context object, Cursor object2) {
        void var3_4;
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        MeasurementNote measurementNote = DataHelper.NotesHelper.parseMeasurementNote((Cursor)var3_4);
        viewHolder.icon.setImageResource(this.getImageResourceForNote(measurementNote.iconRes));
        switch (measurementNote.noteType) {
            case 0: {
                viewHolder.noteText.setText(this.getStringResourceForNote(measurementNote.textRes));
            }
            default: {
                break;
            }
            case 1: {
                viewHolder.noteText.setText((CharSequence)measurementNote.noteText);
            }
        }
        viewHolder.deleteButton.setTag((Object)measurementNote);
        ((TagHolder)viewHolder.editButton.getTag()).note = measurementNote;
        BackPanelListViewHelper.hideBackPanel(view);
    }

    public String extractNoteText(View view) {
        return ((ViewHolder)view.getTag()).noteText.getText().toString();
    }

    public int getImageResourceForNote(int n) {
        switch (n) {
            default: {
                return 2130837753;
            }
            case 1: {
                return 2130837831;
            }
            case 2: {
                return 2130837779;
            }
            case 3: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return 2130837767;
            }
            case 4: {
                return 2130837768;
            }
            case 11: 
        }
        return 2130837768;
    }

    public int getStringResourceForNote(int n) {
        switch (n) {
            default: {
                return 2131362496;
            }
            case 1: {
                return 2131362063;
            }
            case 2: {
                return 2131361943;
            }
            case 3: {
                return 2131361924;
            }
            case 4: {
                return 2131361925;
            }
            case 6: {
                return 2131361953;
            }
            case 7: {
                return 2131362057;
            }
            case 8: {
                return 2131361882;
            }
            case 9: {
                return 2131361846;
            }
            case 10: {
                return 2131361942;
            }
            case 11: 
        }
        return 2131362086;
    }

    @Override
    public boolean hasBackPanel(int n) {
        Cursor cursor = this.getCursor();
        cursor.moveToPosition(n);
        return DataHelper.NotesHelper.parseMeasurementNote((Cursor)cursor).noteType != 0;
    }

    @Override
    public View newView(Context context, Cursor object, ViewGroup object2) {
        context = BackPanelListViewHelper.wrapListViewItem(context, 2130968755, 2130968696, object2);
        object = new ViewHolder();
        object.icon = (ImageView)context.findViewById(2131821159);
        object.noteText = (TextView)context.findViewById(2131821160);
        object.noteText.setTypeface(this.robotoLightTypeface);
        object.deleteButton = context.findViewById(2131821034);
        object.deleteButton.setOnClickListener(this.deleteNoteListener);
        object.editButton = context.findViewById(2131821033);
        object.editButton.setOnClickListener(this.editNoteListener);
        object2 = new TagHolder();
        object2.listItemView = context;
        object.editButton.setTag(object2);
        context.setTag(object);
        return context;
    }

    public static interface OnNoteActionListener {
        public void onDeleteNote(MeasurementNote var1);

        public void onEditNote(MeasurementNote var1);
    }

    private static class TagHolder {
        View listItemView;
        MeasurementNote note;

        private TagHolder() {
        }
    }

    private static class ViewHolder {
        View deleteButton;
        View editButton;
        ImageView icon;
        TextView noteText;

        private ViewHolder() {
        }
    }

}

