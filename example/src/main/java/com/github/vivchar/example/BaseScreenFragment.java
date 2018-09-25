package com.github.vivchar.example;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Created by Vivchar Vitaly on 12/28/17.
 */

public class BaseScreenFragment extends Fragment {


	@NonNull
	public UIRouter getUIRouter() {
		return ((MainActivity)getActivity()).getUIRouter();
	}

	@NonNull
	public OptionsMenuController getMenuController() {
		return ((MainActivity)getActivity()).getMenuController();
	}
}