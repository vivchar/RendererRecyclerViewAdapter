package com.github.vivchar.example;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	private UIRouter mUIRouter;
	private OptionsMenuController mMenuController;
	private MainPresenter mPresenter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUIRouter = new UIRouter(this);
		mMenuController = new OptionsMenuController(this);
		setContentView(R.layout.main);
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mPresenter = new MainPresenter(mMenuController, mUIRouter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPresenter.viewShown();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mPresenter.viewHidden();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		mMenuController.onCreateOptionsMenu(menu, getMenuInflater());
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		mMenuController.onPrepareOptionsMenu(menu, getMenuInflater());
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		mMenuController.onOptionsItemSelected(item);
		return super.onOptionsItemSelected(item);
	}

	@NonNull
	public UIRouter getUIRouter() {
		return mUIRouter;
	}

	@NonNull
	public OptionsMenuController getMenuController() {
		return mMenuController;
	}
}