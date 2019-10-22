/*
 * Decompiled with CFR 0.147.
 */
package com.jakewharton.rxrelay2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public abstract class Relay<T>
extends Observable<T>
implements Consumer<T> {
}

