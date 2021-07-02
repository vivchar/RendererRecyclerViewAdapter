package com.github.vivchar.example

import androidx.fragment.app.Fragment

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
open class BaseScreenFragment : Fragment() {
	val router: UIRouter get() = (activity as MainActivity?)!!.router!!
	val menuController: OptionsMenuController get() = (activity as MainActivity?)!!.menuController!!
}