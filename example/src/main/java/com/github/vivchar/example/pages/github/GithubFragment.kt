package com.github.vivchar.example.pages.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.R
import com.github.vivchar.example.base.BaseFragment
import com.github.vivchar.example.databinding.FragmentGithubBinding
import com.github.vivchar.example.pages.github.items.*
import com.github.vivchar.example.widgets.*
import com.github.vivchar.rendererrecyclerviewadapter.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class GithubFragment : BaseFragment<FragmentGithubBinding>() {

	private val viewModel: GithubViewModel by viewModels()
	private val recyclerViewAdapter = MyAdapter()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentGithubBinding.inflate(inflater, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupMenu()
		setupRecyclerView()
		setupSwipeRefresh()
		observeState()
		observeEvents()
	}

	private fun setupMenu() {
		requireActivity().addMenuProvider(object : MenuProvider {
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.github, menu)
			}

			override fun onPrepareMenu(menu: Menu) {
				menu.findItem(R.id.done)?.isVisible = viewModel.showDoneButton
			}

			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return when (menuItem.itemId) {
					R.id.done -> {
						viewModel.onDoneClicked()
						true
					}
					else -> false
				}
			}
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
	}

	private fun setupRecyclerView() {
		val createListRenderer = RecyclerViewRenderer()
		createListRenderer.registerRenderer(
			ViewRenderer<ForkModel, CustomViewFinder>(
				R.layout.item_fork, ForkModel::class.java
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
		recyclerViewAdapter.registerRenderer(
			ViewRenderer<CategoryModel, ViewFinder>(
				R.layout.item_category, CategoryModel::class.java
			) { model, finder, _ ->
				finder
					.find(R.id.title) { view: TextView -> view.text = model.name }
					.setOnClickListener(R.id.viewAll) { viewModel.onCategoryClicked(model) }
			}
		)

		val layoutManager = GridLayoutManager(context, MAX_SPAN_COUNT)
		layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
			override fun getSpanSize(position: Int): Int {
				return when (recyclerViewAdapter.getType(position)) {
					ForkModel::class.java, StargazerModel::class.java -> 1
					else -> 3
				}
			}
		}

		binding.recyclerView.layoutManager = layoutManager
		binding.recyclerView.adapter = recyclerViewAdapter
		binding.recyclerView.addItemDecoration(MyItemDecoration())
		binding.recyclerView.addOnScrollListener(object : EndlessScrollListener() {
			override fun onLoadMore(page: Int, totalItemsCount: Int) {
				viewModel.onLoadMore()
			}
		})
	}

	private fun setupSwipeRefresh() {
		binding.refresh.setOnRefreshListener { viewModel.onRefresh() }
	}

	private fun observeState() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					binding.refresh.isRefreshing = state.isLoading
					if (state.showLoadMore) {
						recyclerViewAdapter.showLoadMore()
					}
					recyclerViewAdapter.setItems(state.items)
					requireActivity().invalidateOptionsMenu()
				}
			}
		}
	}

	private fun observeEvents() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.events.collect { event ->
					when (event) {
						is GithubEvent.ShowSnackbar -> {
							val view = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
							val snackbar = Snackbar.make(view, event.message, Snackbar.LENGTH_LONG)
							event.url?.let { url ->
								snackbar.setAction(R.string.view) {
									startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
								}
							}
							snackbar.show()
						}
						is GithubEvent.ShowSelectedUsers -> showSelectedUsersDialog(event.users)
						is GithubEvent.ClearSelections -> {
							recyclerViewAdapter.clearViewStates()
							recyclerViewAdapter.notifyDataSetChanged()
						}
					}
				}
			}
		}
	}

	private fun createStargazerRenderer(layout: Int) = StargazerViewRenderer(layout) { model, isChecked ->
		viewModel.onStargazerClicked(model, isChecked)
	}

	private fun showSelectedUsersDialog(list: ArrayList<com.github.vivchar.rendererrecyclerviewadapter.ViewModel>) {
		val adapter: RendererRecyclerViewAdapter = MyAdapter()
		adapter.registerRenderer(
			ViewRenderer<StargazerModel, CustomViewFinder>(
				R.layout.item_user_selected, StargazerModel::class.java
			) { (_, name, avatarUrl), finder, _ ->
				finder.setText(R.id.name, name).setUrl(R.id.avatar, avatarUrl)
			}
		)
		val recyclerView = LayoutInflater.from(context).inflate(R.layout.selected_items_dialog, null) as RecyclerView
		recyclerView.layoutManager = LinearLayoutManager(context)
		recyclerView.adapter = adapter
		adapter.setItems(list)
		AlertDialog.Builder(requireContext())
			.setView(recyclerView)
			.setTitle(R.string.selected_users)
			.setPositiveButton(R.string.ok, null)
			.show()
	}

	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		savedInstanceState?.let {
			recyclerViewAdapter.onRestoreInstanceState(it)
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		recyclerViewAdapter.onSaveInstanceState(outState)
	}

	companion object {
		const val MAX_SPAN_COUNT = 3
	}
}
