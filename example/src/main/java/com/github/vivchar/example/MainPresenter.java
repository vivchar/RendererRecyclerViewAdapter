package com.github.vivchar.example;

import androidx.annotation.NonNull;

import static com.github.vivchar.example.MenuItemID.*;

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */

class MainPresenter extends BasePresenter {

	@NonNull
	private final OptionsMenuController mMenuController;
	@NonNull
	private final UIRouter mUIRouter;

	public MainPresenter(@NonNull final OptionsMenuController menuController, @NonNull final UIRouter UIRouter, final boolean firstInit) {
		mMenuController = menuController;
		mUIRouter = UIRouter;
		mMenuController.hideMenuItem(DONE);
		if (firstInit) {
			mUIRouter.openGithubPage();
		}
	}

	@Override
	protected void viewShown() {
		addSubscription(mMenuController.getItemSelection().subscribe(menuItemID -> {
			@MenuItemID final int menuID = menuItemID;
			switch (menuID) {
				case MAIN:
					mUIRouter.openGithubPage();
					break;
				case DONE:
					break;
				case DIFF_UTIL:
					mUIRouter.openDiffUtilPage();
					break;
				case PAYLOAD:
					mUIRouter.openPayloadPage();
					break;
				case VIEW_STATE:
					mUIRouter.openViewStatePage();
					break;
				case LOAD_MORE:
					mUIRouter.openLoadMorePage();
					break;
				case VIEW_BINDER: //removed in v3.0.0
				case VIEW_RENDERER:
					mUIRouter.openViewRendererPage();
					break;
				case COMPOSITE_VIEW_RENDERER:
					mUIRouter.openCompositeViewRendererPage();
					break;
				case INPUTS_VIEW_STATE:
					mUIRouter.openInputsPage();
					break;
			}
		}));
	}
}
