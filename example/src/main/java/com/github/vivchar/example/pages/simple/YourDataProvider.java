package com.github.vivchar.example.pages.simple;

import com.github.vivchar.example.pages.simple.items.SimpleViewModel;
import com.github.vivchar.example.pages.simple.items.ViewStateViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.DefaultCompositeViewModel;
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivchar Vitaly on 28.12.17.
 */

public class YourDataProvider {

	public List<ViewModel> generateSimpleItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new SimpleViewModel(String.valueOf(i)));
		}
		return items;
	}

	public List<ViewModel> generateCompositeSimpleItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new DefaultCompositeViewModel(generateSimpleItems()));
		}
		return items;
	}

	public List<ViewModel> generateViewStateItems() {
		final ArrayList<ViewModel> items = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			items.add(new ViewStateViewModel(i, generateSimpleItems()));
		}
		return items;
	}
}