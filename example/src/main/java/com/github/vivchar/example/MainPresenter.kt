package com.github.vivchar.example

/**
 * Created by Vivchar Vitaly on 12/29/17.
 */
internal class MainPresenter(
	private val menuController: OptionsMenuController,
	private val router: UIRouter, firstInit: Boolean
) : BasePresenter() {

	public override fun viewShown() {
		addSubscription(
			menuController.itemSelection.subscribe {
				when (it) {
					MenuItemID.MAIN -> router.openGithubPage()
					MenuItemID.DONE -> Unit
					MenuItemID.DIFF_UTIL -> router.openDiffUtilPage()
					MenuItemID.PAYLOAD -> router.openPayloadPage()
					MenuItemID.VIEW_STATE -> router.openViewStatePage()
					MenuItemID.LOAD_MORE -> router.openLoadMorePage()
					MenuItemID.VIEW_BINDER,
					MenuItemID.VIEW_RENDERER -> router.openViewRendererPage()
					MenuItemID.COMPOSITE_VIEW_RENDERER -> router.openCompositeViewRendererPage()
					MenuItemID.INPUTS_VIEW_STATE -> router.openInputsPage()
				}
			}
		)
	}

	init {
		menuController.hideMenuItem(MenuItemID.DONE)
		if (firstInit) {
			router.openGithubPage()
		}
	}
}