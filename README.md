
# Renderer Recycler View Adapter [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter) [![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14) [![API](https://img.shields.io/badge/Size-11%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.github.vivchar%3ARendererRecyclerViewAdapter%3A1.2.0)

* [Now you do not need to implement adapters for RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-the-Renderer-Adapter);
* [You can easily use several types of cells in a single list](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can reuse cells in different RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can easily add a nested RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Composite-Items);
* [You can easily add the DiffUtil support](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-DiffUtil);
* [You can easily save a state of a Item when scroll](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Recommendations-for-the-View-States);
* [You can easily add a Load More Indicator](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Load-More-Indicator);
* [Can be used without ViewHolder - use ViewBinder instead of ViewRenderer](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-View-Binder);
* Using this library will help you to follow SOLID principles.

## Wiki
https://github.com/vivchar/RendererRecyclerViewAdapter/wiki

## Example

| [All](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/github/GithubFragment.java#L78) | [View Renderer](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewRendererFragment.java#L37) | [View Binder](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewBinderFragment.java#L33) | [Load More](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/LoadMoreFragment.java#L45) |
| --- | --- | --- | --- |
![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/example.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-renderer.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-renderer.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/load-more.gif) |


| [Composite](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/CompositeViewRendererFragment.java#L38) | [ViewState](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewStateFragment.java#L46) | [Diff Util](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/DiffUtilFragment.java#L40) | [Payload](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/PayloadFragment.java#L43) | 
| --- | --- | --- | --- |
| ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/composite.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-state.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/diff-util.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/payload.gif) |
## Gradle
```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:+'
}
```
## Motivation
https://vivchar.github.io/RendererRecyclerViewAdapter

## Usage
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
	<Button android:id = "@+id/button" ... />
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

mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mRecyclerViewAdapter);

mRecyclerViewAdapter.setItems(getItems());
mRecyclerViewAdapter.notifyDataSetChanged();

//...
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
