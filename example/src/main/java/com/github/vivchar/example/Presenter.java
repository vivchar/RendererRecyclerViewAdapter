package com.github.vivchar.example;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */

public abstract class Presenter {

	@NonNull
	private final CompositeDisposable disposables = new CompositeDisposable();

	protected abstract void viewShown();

	@CallSuper
	public void viewHidden() {
		disposables.clear();
	}

	protected void addSubscription(@NonNull final Disposable disposable) {
		disposables.add(disposable);
	}
}
