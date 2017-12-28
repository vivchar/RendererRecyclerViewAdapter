
# Renderer Recycler View Adapter [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter) [![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14) [![API](https://img.shields.io/badge/Size-11%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.vivchar%3ARendererRecyclerViewAdapter%3A1.2.0)

* [Now you do not need to implement adapters for RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-the-Renderer-Adapter);
* [You can easily use several types of cells in a single list](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can reuse cells in different RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can easily add a nested RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Composite-Items);
* [You can easily add the DiffUtil support](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-DiffUtil);
* [You can easily save a state of a Item when scroll](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Recommendations-for-the-View-States);
* [You can easily add a Load More Indicator](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Load-More-Indicator);
* Using this library will help you to follow SOLID principles.

## Wiki
https://github.com/vivchar/RendererRecyclerViewAdapter/wiki

## Example
![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/example.gif)

## Gradle
```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:+'
}
```
## Motivation
https://vivchar.github.io/RendererRecyclerViewAdapter

## Usage (v2.2.0 and above)
* Step 1. Add the ViewModel interface to your UI model

```java
public class SomeModel implements ViewModel {
	...
}
```

* Step 2. Create your item_layout
```xml
<LinearLayout ... >
	<TextView android:id = "@+id/title" ... />
</LinearLayout>

```

* Step 3. Initialize Adapter and register the ViewBinder
```java
//...

mRecyclerViewAdapter = new RendererRecyclerViewAdapter();

mRecyclerViewAdapter.registerRenderer(new ViewBinder<>(
	R.layout.item_layout,
	SomeModel.class,
	getContext(),
	(model, finder, payloads) -> finder
		.find(R.id.title, (ViewProvider<TextView>) textView -> { ... })
		.setOnClickListener(R.id.button, v -> { ... })
));

mRecyclerViewAdapter.registerRenderer(...); /* you can use several types of cells */

//...
```

## Usage (All versions)
* Step 1. Add the ViewModel interface to your UI model

```java
public class SomeModel implements ViewModel {
	...
}
```

* Step 2. Implement SomeViewHolder

```java
public class SomeViewHolder extends ViewHolder {

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

	public SomeViewRenderer(Context context) {
		super(SomeModel.class, context);
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
		mRecyclerViewAdapter.registerRenderer(new SomeViewRenderer(this));
		mRecyclerViewAdapter.registerRenderer(...); /* you can use several types of cells */

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mRecyclerViewAdapter);

		mRecyclerViewAdapter.setItems(getItems());
		mRecyclerViewAdapter.notifyDataSetChanged();
	}
	...
}
```

## Migration to v2.0
//TODO

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
