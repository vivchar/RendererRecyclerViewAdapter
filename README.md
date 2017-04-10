
# Renderer Recycler View Adapter 

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter) [![Release](https://jitpack.io/v/vivchar/RendererRecyclerViewAdapter.svg)](https://jitpack.io/#vivchar/RendererRecyclerViewAdapter) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RendererRecyclerViewAdapter-green.svg?style=flat)](https://android-arsenal.com/details/1/5442) [![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14) [![API](https://img.shields.io/badge/Size-8%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.vivchar%3ARendererRecyclerViewAdapter%3A1.1.0)





* Now you do not need to implement adapters for RecyclerView. 
* You can easily use several types of cells in a single list.
* Using this library will protect you from the appearance of any business logic in an adapter :)

## Gradle
from 1.0.10, AAR is distributed via jCenter.
```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:1.0.10'
}
```

## Usage

* Step 1. Implement SomeModel

```java
public
class SomeModel implements ItemModel
{

	public static final int TYPE = 0;
	@NonNull
	private final String mTitle;

	public
	SomeModel(@NonNull final String title) {
		mTitle = title;
	}

	@Override
	public
	int getType() {
		return TYPE;
	}

	@NonNull
	public
	String getTitle() {
		return mTitle;
	}
	...
}
```

* Step 2. Implement SomeViewHolder

```java
public
class SomeViewHolder
		extends RecyclerView.ViewHolder
{

	public final TextView mTitle;

	public
	SomeViewHolder(final View itemView) {
		super(itemView);
		mTitle = (TextView) itemView.findViewById(R.id.title);
		...
	}
}
```

* Step 3. Implement SomeViewRenderer

```java
public
class SomeViewRenderer
		extends ViewRenderer<SomeModel, SomeViewHolder>
{
	public
	SomeViewRenderer(final int type, final Context context) {
		super(type, context);
	}

	@Override
	public
	void bindView(@NonNull final SomeModel model, @NonNull final SomeViewHolder holder) {
		holder.mTitle.setText(model.getTitle());
		...
	}

	@NonNull
	@Override
	public
	SomeViewHolder createViewHolder(@Nullable final ViewGroup parent) {
		return new SomeViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.some_item, parent, false));
	}
}
```

* Step 4. Initialize Adapter and register the SomeViewRenderer 

```java
public
class SomeActivity
		extends AppCompatActivity
{

	private RendererRecyclerViewAdapter mRecyclerViewAdapter;
	private RecyclerView mRecyclerView;

	@Override
	protected
	void onCreate(final Bundle savedInstanceState) {
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

### Example

![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example1.gif)

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
