package com.github.vivchar.example

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

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
		for (@MenuItemID menuItemID in MenuItemID.ALL) {
			menu.findItem(menuItemID).isVisible = !invisibleItems.contains(menuItemID)
		}
	}

	fun showMenuItem(@MenuItemID itemID: Int) {
		if (invisibleItems.contains(itemID)) {
			invisibleItems.remove(Integer.valueOf(itemID))
			context.invalidateOptionsMenu()
		}
	}

	fun hideMenuItem(@MenuItemID itemID: Int) {
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