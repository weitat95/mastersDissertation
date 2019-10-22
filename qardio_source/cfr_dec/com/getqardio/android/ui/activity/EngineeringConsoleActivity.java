/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.getqardio.android.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.qardio.ble.bpcollector.mobiledevice.MobileDeviceFactory;
import java.util.Arrays;
import timber.log.Timber;

public class EngineeringConsoleActivity
extends BaseActivity {
    private static final byte[] SEARCH_DATA = new byte[]{-5, 5};
    private CommandResultBroadcastReceiver commandResultBroadcastReceiver;
    private byte[] dataBuffer = new byte[0];
    private boolean displayMode = false;
    private TextView log;

    public EngineeringConsoleActivity() {
        this.commandResultBroadcastReceiver = new CommandResultBroadcastReceiver();
    }

    public static Intent getStartIntent(Context context, boolean bl) {
        context = new Intent(context, EngineeringConsoleActivity.class);
        context.putExtra("com.getqardio.android.extras.DISPLAY_MODE", bl);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onLibData(byte[] object) {
        int n;
        int n2;
        block4: {
            block6: {
                block7: {
                    block5: {
                        if (!this.displayMode) break block5;
                        n2 = this.dataBuffer.length;
                        this.dataBuffer = Arrays.copyOf(this.dataBuffer, this.dataBuffer.length + ((byte[])object).length);
                        System.arraycopy(object, 0, this.dataBuffer, n2, ((byte[])object).length);
                        if (this.dataBuffer.length < 5) break block6;
                        break block7;
                    }
                    String string2 = "RECEIVING: ";
                    int n3 = ((byte[])object).length;
                    int n4 = 0;
                    do {
                        if (n4 >= n3) {
                            this.log.append((CharSequence)(string2 + "\n"));
                            return;
                        }
                        byte by = object[n4];
                        string2 = string2 + String.format("%02x", by & 0xFF).toUpperCase() + " ";
                        ++n4;
                    } while (true);
                }
                int n5 = -1;
                n = 0;
                do {
                    block9: {
                        block8: {
                            n2 = n5;
                            if (n >= this.dataBuffer.length - 1) break block8;
                            if (this.dataBuffer[n] != SEARCH_DATA[0] || this.dataBuffer[n + 1] != SEARCH_DATA[1]) break block9;
                            n2 = n + 2;
                        }
                        if (n2 == -1) break;
                        break block4;
                    }
                    ++n;
                } while (true);
            }
            return;
        }
        n = this.dataBuffer[n2] & 0xFF;
        n2 = this.dataBuffer[n2 + 1] & 0xFF;
        String string3 = String.format("%01x%01x%01x", n >> 4 & 0xF & 0xFF, n & 0xF & 0xFF, n2 >> 4 & 0xFF);
        Timber.d("str1=%s", string3);
        long l = Long.parseLong(string3, 16);
        Timber.d("The decimal equivalent v1 is: %s", l);
        long l2 = Long.parseLong(String.format("%01x", n2 & 0xF & 0xFF));
        Timber.d("The decimal equivalent v2 is: %s", l2);
        this.dataBuffer = new byte[0];
        String string4 = String.format("First Value: %d | Second Value: %d \n", l, l2);
        this.log.append((CharSequence)(string4 + "\n"));
    }

    private void sendCommand(String string2) {
        Timber.d("send command[%s]", string2);
        MobileDeviceFactory.sendEngModeCommand((Context)this, string2);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968700);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        bundle = this.getIntent();
        if (bundle != null) {
            this.displayMode = bundle.getBooleanExtra("com.getqardio.android.extras.DISPLAY_MODE", this.displayMode);
        }
        this.log = (TextView)this.findViewById(2131821040);
        ((EditText)this.findViewById(2131821041)).setOnEditorActionListener(new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                if (n == 4) {
                    EngineeringConsoleActivity.this.sendCommand(textView.getText().toString());
                    textView.setText((CharSequence)"");
                    return true;
                }
                return false;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.finish();
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.commandResultBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.commandResultBroadcastReceiver, new IntentFilter("RAW_DATA"));
    }

    private class CommandResultBroadcastReceiver
    extends BroadcastReceiver {
        private CommandResultBroadcastReceiver() {
        }

        public void onReceive(Context arrby, Intent intent) {
            arrby = intent.getByteArrayExtra("COMMAND_VALUE");
            if (arrby != null) {
                EngineeringConsoleActivity.this.onLibData(arrby);
            }
        }
    }

}

