package com.github.vivchar.example

import android.util.Log
import androidx.annotation.CallSuper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by Vivchar Vitaly on 10.10.17.
 */
abstract class BasePresenter {
	private val disposables = CompositeDisposable()

	@CallSuper
	fun unsubscribe() = disposables.clear()

	abstract fun subscribe()

	protected fun addSubscription(disposable: Disposable) = disposables.add(disposable)

	fun <T> Observable<T>.listen(onNext: (T) -> Unit): Disposable {
		return this
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				onNext,
				{ Log.e(BasePresenter::class.java.simpleName, "Default OnError: ${it.message}") },
				{ Log.d(BasePresenter::class.java.simpleName, "Default OnComplete action") }
			)
			.apply { addSubscription(this) }
	}
}