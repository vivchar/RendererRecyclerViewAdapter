package com.github.vivchar.example.pages.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.vivchar.example.R
import com.github.vivchar.example.base.BaseFragment
import com.github.vivchar.example.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

	private val viewModel: HomeViewModel by viewModels()

	override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentHomeBinding.inflate(inflater, container, false)

	override fun onStart() {
		super.onStart()
		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.state.collect { state ->
					render(state)
				}
			}
		}
	}

	private fun render(state: HomeUiState) {
		if (binding.recyclerView.adapter == null) {
			binding.recyclerView.adapter = HomeAdapter(state.screens) { screen ->
				findNavController().navigate(screen.actionId)
			}
		}
	}
}

private class HomeAdapter(
	private val items: List<DemoScreen>,
	private val onClick: (DemoScreen) -> Unit
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

	class ViewHolder(val root: android.view.View) : RecyclerView.ViewHolder(root) {
		val title: TextView = root.findViewById(R.id.title)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = items[position]
		holder.title.text = item.title
		holder.root.setOnClickListener { onClick(item) }
	}

	override fun getItemCount() = items.size
}
