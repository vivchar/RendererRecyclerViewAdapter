package com.github.vivchar.example.pages.simple;

import com.github.vivchar.example.pages.simple.DiffUtilFragment.DiffViewModel;
import com.github.vivchar.example.pages.simple.PayloadFragment.PayloadViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.github.vivchar.example.pages.simple.ViewRendererFragment.RectViewModel;
import static com.github.vivchar.example.pages.simple.ViewStateFragment.StateViewModel;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class YourDataProvider {

	final ArrayList<PayloadViewModel> mPayloadItems = new ArrayList<>();

	private ArrayList<LoadMoreFragment.SimpleViewModel> mLoadMoreItems = new ArrayList<>();
	private ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(2, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

	public List<ViewModel> generateDiffItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new DiffViewModel(i, String.valueOf(i)));
		}
		return items;
	}

	public List<ViewModel> generateSquareItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new RectViewModel(String.valueOf(i)));
		}
		return items;
	}

	public List<ViewModel> generateCompositeSimpleItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new DefaultCompositeViewModel(generateDiffItems()));
		}
		return items;
	}

	public List<ViewModel> generateStateItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new StateViewModel(i, generateDiffItems()));
		}
		return items;
	}

	public List<PayloadViewModel> generatePayloadItems() {
		if (mPayloadItems.isEmpty()) {
			for (int i = 0; i < 50; i++) {
				mPayloadItems.add(new PayloadViewModel(i, String.valueOf(i), "model ID: " + i));
			}
		}
		return mPayloadItems;
	}

	public List<PayloadViewModel> generateChangedPayloadItems(final PayloadViewModel model) {
		/* Just for example */
		final ArrayList<PayloadViewModel> newList = new ArrayList<>();

		for (final PayloadViewModel viewModel : mPayloadItems) {
			if (viewModel.getID() == model.getID()) {
				newList.add(new PayloadViewModel(
						model.getID(),
						String.valueOf(Integer.valueOf(model.getText()) + 1),
						"model ID: " + model.getID()
				));
			} else {
				newList.add(viewModel);
			}
		}

		mPayloadItems.clear();
		mPayloadItems.addAll(newList);

		return mPayloadItems;
	}

	public List<? extends ViewModel> generateLoadMoreItems() {
		final int size = mLoadMoreItems.size();
		for (int i = size; i < size + 30; i++) {
			mLoadMoreItems.add(new LoadMoreFragment.SimpleViewModel(String.valueOf(i)));
		}
		return mLoadMoreItems;
	}

	public void generateLoadMoreItems(final Listener listener) {
		mExecutor.execute(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			final int size = mLoadMoreItems.size();
			for (int i = size; i < size + 30; i++) {
				mLoadMoreItems.add(new LoadMoreFragment.SimpleViewModel(String.valueOf(i)));
			}
			listener.onChanged(new ArrayList<>(mLoadMoreItems));
		});
	}

	public interface Listener {

		void onChanged(List<? extends ViewModel> list);
	}
}