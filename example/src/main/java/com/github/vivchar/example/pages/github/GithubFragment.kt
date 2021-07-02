package com.github.vivchar.example.pages.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.vivchar.example.BaseScreenFragment
import com.github.vivchar.example.R
import com.github.vivchar.example.pages.github.items.*
import com.github.vivchar.example.widgets.*
import com.github.vivchar.network.MainManager.Companion.instance
import com.github.vivchar.rendererrecyclerviewadapter.*
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */
class GithubFragment : BaseScreenFragment() {
	private var recyclerViewAdapter = MyAdapter()
	private var recyclerView: RecyclerView? = null
	private var layoutManager: GridLayoutManager? = null
	private var swipeToRefresh: SwipeRefreshLayout? = null
	private var githubPresenter: GithubPresenter? = null
	private var instanceState: Bundle? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		githubPresenter = GithubPresenter(
			router,
			menuController,
			instance.stargazersManager,
			instance.forksManager,
			mainPresenterView
		)

		val categoryRenderer = ViewRenderer<CategoryModel, ViewFinder>(
			R.layout.item_category,
			CategoryModel::class.java
		) { model, finder, _ ->
			finder
				.find(R.id.title) { view: TextView -> view.text = model.name }
				.setOnClickListener(R.id.viewAll) { githubPresenter?.onCategoryClicked(model) }
		}

		val createListRenderer = RecyclerViewRenderer()
		createListRenderer.registerRenderer(
			ViewRenderer<ForkModel, CustomViewFinder>(
				R.layout.item_fork,
				ForkModel::class.java
			) { (name, avatarUrl), finder, _ ->
				finder.setUrlCircled(R.id.fork_avatar, avatarUrl)
				finder.setText(R.id.fork_name, name)
			}
		)
		createListRenderer.registerRenderer(createStargazerRenderer(R.layout.item_user_150))

		recyclerViewAdapter.setDiffCallback(ItemsDiffCallback())
		recyclerViewAdapter.registerRenderer(LoadMoreViewBinder(R.layout.item_load_more))
		recyclerViewAdapter.registerRenderer(createStargazerRenderer(R.layout.item_user_full_width))
		recyclerViewAdapter.registerRenderer(createListRenderer)
		recyclerViewAdapter.registerRenderer(categoryRenderer)

		layoutManager = GridLayoutManager(context, MAX_SPAN_COUNT)
		layoutManager!!.spanSizeLookup = object : SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int {
				return when (recyclerViewAdapter.getType(position)) {
					ForkModel::class.java,
					StargazerModel::class.java -> 1
					else -> 3
				}
			}
		}

		val inflate = inflater.inflate(R.layout.fragment_github, container, false)
		swipeToRefresh = inflate.findViewById(R.id.refresh)
		swipeToRefresh?.setOnRefreshListener { githubPresenter?.onRefresh() }
		recyclerView = inflate.findViewById(R.id.recycler_view)
		recyclerView?.layoutManager = layoutManager
		recyclerView?.adapter = recyclerViewAdapter
		recyclerView?.addItemDecoration(MyItemDecoration())
		recyclerView?.addOnScrollListener(object : EndlessScrollListener() {
			override fun onLoadMore(page: Int, totalItemsCount: Int) {
				githubPresenter?.onLoadMore()
			}
		})
		return inflate
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		instanceState = savedInstanceState
	}

	override fun onResume() {
		super.onResume()
		recyclerViewAdapter.onRestoreInstanceState(instanceState)
		instanceState = null
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		recyclerViewAdapter.onSaveInstanceState(outState)
	}

	override fun onStart() {
		super.onStart()
		githubPresenter?.viewShown()
	}

	override fun onStop() {
		super.onStop()
		githubPresenter?.viewHidden()
	}

	private fun createStargazerRenderer(layout: Int) = StargazerViewRenderer(layout) { model, isChecked ->
		githubPresenter?.onStargazerClicked(model, isChecked)
	}

	private fun createUserRenderer(): ViewRenderer<*, *> {
		/* vivchar: ideally we should use other model */
		return ViewRenderer<StargazerModel, CustomViewFinder>(
			R.layout.item_user_selected,
			StargazerModel::class.java
		) { (_, name, avatarUrl), finder, _ ->
			finder
				.setText(R.id.name, name)
				.setUrl(R.id.avatar, avatarUrl)
		}
	}

	private val mainPresenterView: GithubPresenter.View = object : GithubPresenter.View {
		override fun updateList(list: List<ViewModel>) {
			recyclerViewAdapter.setItems(list)
		}

		override fun showProgressView() {
			swipeToRefresh?.isRefreshing = true
		}

		override fun hideProgressView() {
			swipeToRefresh?.isRefreshing = false
		}

		override fun showMessageView(message: String, url: String) {
			val view = activity!!.window.decorView.findViewById<View>(android.R.id.content)
			Snackbar.make(view, message, Snackbar.LENGTH_LONG)
				.setAction(R.string.view) { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))) }
				.show()
		}

		override fun showMessageView(message: String) {
			val view = activity!!.window.decorView.findViewById<View>(android.R.id.content)
			Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
		}

		override fun showSelectedUsers(list: ArrayList<ViewModel>) {
			val adapter: RendererRecyclerViewAdapter = MyAdapter()
			adapter.registerRenderer(createUserRenderer())
			val inflater = LayoutInflater.from(context)
			val recyclerView = inflater.inflate(R.layout.selected_items_dialog, null) as RecyclerView
			recyclerView.layoutManager = LinearLayoutManager(context)
			recyclerView.adapter = adapter
			adapter.setItems(list)
			val builder = AlertDialog.Builder(context!!)
			builder.setView(recyclerView)
			builder.setTitle(R.string.selected_users)
			builder.setPositiveButton(R.string.ok, null)
			builder.show()
		}

		override fun clearSelections() {
			recyclerViewAdapter.clearViewStates()
			recyclerViewAdapter.notifyDataSetChanged()
		}

		override fun showLoadMoreView() {
			recyclerViewAdapter.showLoadMore()
		}
	}

	companion object {
		const val MAX_SPAN_COUNT = 3
	}
}