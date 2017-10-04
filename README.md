
# Renderer Recycler View Adapter [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter) [![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14) [![API](https://img.shields.io/badge/Size-11%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.vivchar%3ARendererRecyclerViewAdapter%3A1.2.0)

* Now you do not need to implement adapters for RecyclerView;
* You can easily use several types of cells in a single list;
* You can reuse cells in different RecyclerView;
* You can easily add a nested RecyclerView;
* You can easily add the DiffUtil support;
* Using this library will protect you from the appearance of any business logic in an adapter :blush:.

## Example
![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example-1.2.0.gif)

## Gradle
```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:+'
}
```

## Usage
* Step 1. Implement SomeModel

```java
public class SomeModel implements ItemModel {

	public static final int TYPE = 0;
	private String mTitle;

	public SomeModel(String title) {
		mTitle = title;
	}

	@Override
	public int getType() {
		return TYPE;
	}

	public String getTitle() {
		return mTitle;
	}
	...
}
```

* Step 2. Implement SomeViewHolder

```java
public class SomeViewHolder extends RecyclerView.ViewHolder {

	public TextView mTitle;

	public SomeViewHolder(View itemView) {
		super(itemView);
		mTitle = (TextView) itemView.findViewById(R.id.title);
		...
	}
}
```

* Step 3. Implement SomeViewRenderer

```java
public class SomeViewRenderer extends ViewRenderer<SomeModel, SomeViewHolder> {

	public SomeViewRenderer(int type, Context context) {
		super(type, context);
	}

	@Override
	public void bindView(SomeModel model, SomeViewHolder holder) {
		holder.mTitle.setText(model.getTitle());
		...
	}

	@Override
	public SomeViewHolder createViewHolder(ViewGroup parent) {
		return new SomeViewHolder(inflate(R.layout.some_item, parent));
	}
}
```

* Step 4. Initialize Adapter and register the SomeViewRenderer 

```java
public class SomeActivity extends AppCompatActivity {

	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mRecyclerViewAdapter = new RendererRecyclerViewAdapter();
		mRecyclerViewAdapter.registerRenderer(new SomeViewRenderer(SomeModel.TYPE, this));
		mRecyclerViewAdapter.registerRenderer(...); /* you can use several types of cells */

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mRecyclerViewAdapter);

		mRecyclerViewAdapter.setItems(getItems());
		mRecyclerViewAdapter.notifyDataSetChanged();
		
		//Or you can use method with the DiffUtil support
		//mRecyclerViewAdapter.setItems(getItems(), new YourDiffCallBack());
	}
	...
}
```

## License

    Copyright 2017 Vitaly Vivchar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
