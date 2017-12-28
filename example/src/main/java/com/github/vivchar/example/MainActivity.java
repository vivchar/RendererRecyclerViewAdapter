package com.github.vivchar.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	private boolean mDoneItemVisibility = false;
	private UIRouter mUIRouter;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUIRouter = new UIRouter(this);
		setContentView(R.layout.main);
		final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mUIRouter.openGithubPage();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
		menu.findItem(R.id.done).setVisible(mDoneItemVisibility);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.main:
				mUIRouter.openGithubPage();
				break;
			case R.id.done:
				break;
			case R.id.view_state:
				break;
			case R.id.composite_view_state:
				break;
			case R.id.composite_view_renderer:
				mUIRouter.openCompositeViewRendererPage();
				break;
			case R.id.view_renderer:
				mUIRouter.openViewRendererPage();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}