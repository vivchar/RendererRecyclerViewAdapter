package com.github.vivchar.example

import androidx.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */
abstract class BasePresenter {
	private val disposables = CompositeDisposable()

	@CallSuper
	fun viewHidden() = disposables.clear()

	protected abstract fun viewShown()
	protected fun addSubscription(disposable: Disposable) = disposables.add(disposable)
}