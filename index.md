
# Renderer Recycler View Adapter

* Now you do not need to implement adapters for RecyclerView. 
* You can easily use several types of cells in a single list.
* Using this library will protect you from the appearance of any business logic in an adapter :)

## Gradle

* Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

* Step 2. Add the dependency

```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:1.0.9'
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
