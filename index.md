# Motivation

As of late, I often had to rework a lot of list adapters. And each time I was desperate as the adapter contained the business logic, networking queries, application routing and much more. All this was very difficult to change. At first I was just moving the unrelated stuff from adapters to presenters, fragments and other classes. Eventually, I realized that it would not hurt if I could:

1. “Secure” my adapters against the introduction of inappropriate logic;
2. Reuse cell bindings;
3. Achieve some versatility when working with different cell types.

If you are familiar with some of these problems, then welcome. 

Looking for an off-the-shelf solution, I found `AdapterDelegates` but it failed to achieve the first of my objectives.

## Requirements

To start with, I have highlighted several requirements that I have already articulated:

- it must work with `RecyclerView` without implementing a new adapter;
- I must be able to reuse cells in another `RecyclerView`;
- I must be able to easily add other cell types into `RecyclerView`.

## Implementation

First I’ve taken a look at things I always do within the adapter, to do that I created a test implementation and analyzed the methods that I used:
```java
public class Test extends RecyclerView.Adapter {

	@Override
	public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
	}

	@Override public void onBindViewHolder(final ViewHolder holder, final int position) {
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	public void setItems(@NonNull final ArrayList items) {
	}
}
```

There were 4 methods overall. `setItems()` is the one that draws attention. It must be able to accept different model lists, so we create an empty interface and update the code in our test adapter:
```java
public interface ItemModel { }

public class Test extends RecyclerView.Adapter {

        @NonNull
	private final ArrayList<ItemModel> mItems = new ArrayList<>();

        ....
	
	@Override
	public int getItemCount() {
                return mItems.size();
	}

        public void setItems(@NonNull final ArrayList<ItemModel> items) {
		mItems.clear();
		mItems.addAll(items);
	}
}
```

Now we must figure something out with `onCreateViewHolder()` and `onBindViewHolder()`. If I want to enable my adapter to bind different viewers, then it should better be able to delegate it somewhere. It will allow us to reuse the implementation afterwards. We create an abstract class that will be able to handle only one cell type and a specific `ViewHolder` of course. To this end we use generics in order to avoid type casts. Let’s call it `ViewRenderer` (it’s the best I could think of).
```java
public abstract class ViewRenderer <M extends ItemModel, VH extends RecyclerView.ViewHolder> {

	public abstract	void bindView(@NonNull M model, @NonNull VH holder);

	@NonNull
	public abstract	VH createViewHolder(@Nullable ViewGroup parent);
}
```

Let’s try using it in our adapter. Let’s rename the adapter into something that makes more sense and then update the code:

```java
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter {

        ...

        private ViewRenderer mRenderer;

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		return mRenderer.createViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		mRenderer.bindView(item, holder);
	}

        public void registerRenderer(@NonNull final ViewRenderer renderer) {
                mRenderer = renderer;
	}
	
        ...
}
```

It all looks okay for now. Still, our adapter must be able to work with multiple view types. To that end, the adapter has `getItemViewType()`, and we override it in the adapter. Let’s try to get cell type directly from the model by adding `getItemViewType()` into the interface and updating the adapter’s method:

```java
public interface ItemModel {
	int getType();
}

public class RendererRecyclerViewAdapter extends RecyclerView.Adapter {

        ...
	
	@Override
	public int getItemViewType(final int position) {
		final ItemModel item = getItem(position);
		return item.getType();
	}

	private	ItemModel getItem(final int position) {
		return mItems.get(position);
	}

        ...
}
```

In the meanwhile we’ll improve support of multiple `ViewRenderers`:

```java
public class RendererRecyclerViewAdapter extends RecyclerView.Adapter {

        ...
	@NonNull
	private final SparseArray<ViewRenderer> mRenderers = new SparseArray<>();

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
		final ViewRenderer renderer = mRenderers.get(viewType);
		if (renderer != null) {
			return renderer.createViewHolder(parent);
		}

		throw new RuntimeException("Not supported Item View Type: " + viewType);
	}

	public void registerRenderer(@NonNull final ViewRenderer renderer) {
		final int type = renderer.getType();

		if (mRenderers.get(type) == null) {
			mRenderers.put(type, renderer);
		} else {
			throw new RuntimeException("ViewRenderer already exist with this type: " + type);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		final ItemModel item = getItem(position);

		final ViewRenderer renderer = mRenderers.get(item.getType());
		if (renderer != null) {
			renderer.bindView(item, holder);
		} else {
			throw new RuntimeException("Not supported View Holder: " + holder);
		}
	}

        ...
}
```

As we see, the renderer now has `getType()` that’s necessary to find the necessary renderer for a specific viewer. Now the adapter is finalized. We implement specific `ItemModel`, `ViewHolder` and `ViewRenderer` classes:

```java
public class SomeModel implements ItemModel {

    public static final int TYPE = 0;
    @NonNull
    private final String mTitle;

    public SomeModel(@NonNull final String title) {
        mTitle = title;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }
    
    ...

}
```

```java
public class SomeViewHolder extends RecyclerView.ViewHolder {

    public final TextView mTitle;

    public SomeViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        ...
    }
}
```

```java
public class SomeViewRenderer extends ViewRenderer<SomeModel, SomeViewHolder> {

    public SomeViewRenderer(final int type, final Context context) {
        super(type, context);
    }

    @Override public
    void bindView(@NonNull final SomeModel model, @NonNull final SomeViewHolder holder) {
        ...
    }

    @NonNull
    @Override
    public SomeViewHolder createViewHolder(@Nullable final ViewGroup parent) {
        return new SomeViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.some_item, parent, false));
    }
}
```

Now `ViewRender` has a constructor and two parameters for it: `ViewRenderer(int viewType, Context context);` I hope there’s no need to explain why we need them. Now we can let our adapter and `RecyclerView` get to know each other:

```java
public class SomeActivity extends AppCompatActivity {

    private RendererRecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mRecyclerViewAdapter = new RendererRecyclerViewAdapter();
        mRecyclerViewAdapter.registerRenderer(new SomeViewRenderer(SomeModel.TYPE, this));
//        mRecyclerViewAdapter.registerRenderer(...); 

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mRecyclerViewAdapter.setItems(getItems());
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    ...
}
```

## Conclusion

With relative small effort, we made a working version of the adapter that can be easily used with multiple cell types. All we had to do was implement `ViewRenderer` for each respective cell type and register it in the adapter. By now this implementation has performed well in several large projects.
