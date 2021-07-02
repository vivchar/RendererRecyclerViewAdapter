package com.github.vivchar.example

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.github.vivchar.example.pages.github.GithubFragment
import com.github.vivchar.example.pages.simple.*

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
class UIRouter(context: AppCompatActivity) {
	private val fragmentManager: FragmentManager = context.supportFragmentManager

	private fun showFragment(fragment: BaseScreenFragment) {
		try {
			fragmentManager.beginTransaction()
				.replace(R.id.screen_container, fragment, fragment::class.simpleName)
				.commitAllowingStateLoss()
		} catch (ignored: IllegalStateException) {
		}
	}

	fun openViewRendererPage() = showFragment(ViewRendererFragment())
	fun openCompositeViewRendererPage() = showFragment(CompositeViewRendererFragment())
	fun openViewStatePage() = showFragment(ViewStateFragment())
	fun openDiffUtilPage() = showFragment(DiffUtilFragment())
	fun openPayloadPage() = showFragment(PayloadFragment())
	fun openLoadMorePage() = showFragment(LoadMoreFragment())
	fun openInputsPage() = showFragment(InputsFragment())
	fun openGithubPage() = showFragment(GithubFragment())
}