package com.github.vivchar.example

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */
class OptionsMenuController(private val context: AppCompatActivity) {
	private val invisibleItems = ArrayList<Int>()
	private val itemSelectionSubject: Subject<Int> = PublishSubject.create()

	fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		inflater.inflate(R.menu.main, menu)
	}

	fun onPrepareOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
		for (menuItemID in MenuItemID.ALL) {
			menu.findItem(menuItemID).isVisible = !invisibleItems.contains(menuItemID)
		}
	}

	fun showMenuItem(itemID: Int) {
		if (invisibleItems.contains(itemID)) {
			invisibleItems.remove(Integer.valueOf(itemID))
			context.invalidateOptionsMenu()
		}
	}

	fun hideMenuItem(itemID: Int) {
		if (!invisibleItems.contains(itemID)) {
			invisibleItems.add(itemID)
			context.invalidateOptionsMenu()
		}
	}

	fun onOptionsItemSelected(item: MenuItem) {
		itemSelectionSubject.onNext(item.itemId)
	}

	val itemSelection: Observable<Int>
		get() = itemSelectionSubject.hide()
}