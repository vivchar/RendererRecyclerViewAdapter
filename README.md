
# Renderer RecyclerView Adapter [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vivchar/RendererRecyclerViewAdapter) [![API](https://img.shields.io/badge/API-14%2B-yellow.svg?style=flat)](https://android-arsenal.com/api?level=14)

* [Now you do not need to implement adapters for RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-the-Renderer-Adapter);
* [You can easily use several types of cells in a single list](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can reuse cells in different RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Simple-Items);
* [You can easily add a nested RecyclerView](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Composite-Items);
* [You can easily add the DiffUtil](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-DiffUtil);
* [You can easily add the DiffUtil Payloads](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/PayloadFragment.java#L43);
* [You can easily save a state of a Item when scroll](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-ViewState);
* [You can easily add a Load More Indicator](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-Load-More-Indicator);
* [Can be used without ViewHolder - use ViewBinder instead of ViewRenderer](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Quick-start-with-View-Binder);
* Using this library will help you to follow SOLID principles.

## Articles
* [A RecyclerView with multiple item types  —  Medium](https://medium.com/@vivchar/a-recyclerview-with-multiple-item-types-dfba3979050)
* [How to easily add Nested RecyclerView  —  Medium](https://medium.com/@vivchar/easy-handling-of-lists-rendererrecyclerviewadapter-part-2-3b18c8ea6f1b)
* [Simplifying the work with RecyclerView  —  Medium](https://medium.com/@vivchar/simplifying-the-work-with-recyclerview-a64027bca8c3)

## Examples

| [All](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/github/GithubFragment.java#L78) | [View Renderer](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewRendererFragment.java#L37) | [View Binder](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewBinderFragment.java#L33) | [Load More](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/LoadMoreFragment.java#L45) |
| --- | --- | --- | --- |
![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/example.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-renderer.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-renderer.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/load-more.gif) |


| [Composite](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/CompositeViewRendererFragment.java#L38) | [ViewState](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/ViewStateFragment.java#L46) | [Diff Util](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/DiffUtilFragment.java#L40) | [Payload](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/src/main/java/com/github/vivchar/example/pages/simple/PayloadFragment.java#L43) | 
| --- | --- | --- | --- |
| ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/composite.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/view-state.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/diff-util.gif) | ![Example](https://github.com/vivchar/RendererRecyclerViewAdapter/blob/master/example/payload.gif) |

## Wiki
https://github.com/vivchar/RendererRecyclerViewAdapter/wiki

## Gradle
```gradle
dependencies {
    compile 'com.github.vivchar:RendererRecyclerViewAdapter:2.5.1'
}
```

## Usage
* **Step 1:** Add the ViewModel interface to your UI model

```java
public class SomeModel implements ViewModel {
	...
}
```

* **Step 2:** Create your item_layout
```xml
<LinearLayout ... >
	<TextView android:id = "@+id/title" ... />
	<ImageView android:id = "@+id/image" ... />
	<Button android:id = "@+id/button" ... />
	<CustomView android:id = "@+id/custom" ... />
</LinearLayout>
```

* **Step 3:** Initialize Adapter and register the ViewBinder
```java
mRecyclerViewAdapter = new RendererRecyclerViewAdapter();

mRecyclerViewAdapter.registerRenderer(new ViewBinder<>(
	R.layout.item_layout,
	SomeModel.class,
	(model, finder, payloads) -> finder
		.find(R.id.custom, (ViewProvider<CustomView>) customView -> { ... })
		.setBackground(R.id.image, model.getBackground())
		.setText(R.id.text, model.getText())
		.setOnClickListener(R.id.button, v -> { ... })
));
mRecyclerViewAdapter.registerRenderer(...); /* you can use several types of cells */

/* Regular code:
mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
mRecyclerView.setAdapter(mRecyclerViewAdapter);

mRecyclerViewAdapter.setItems(getItems());
mRecyclerViewAdapter.notifyDataSetChanged();
*/
```

## Release notes
See [the Releases Page](https://github.com/vivchar/RendererRecyclerViewAdapter/releases)

## Migrations
* [Migration to v2.5.0](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Steps-for-migration-to-v2.5.0)
* [Migration to v2.5.0 (by TheJuki)](https://github.com/vivchar/RendererRecyclerViewAdapter/wiki/Migration-to-2.5.0)

## Projects using RendererRecyclerViewAdapter
* [Camfrog](https://play.google.com/store/apps/details?id=com.camshare.camfrog.android): Group Video Chat;
* [Paltalk](https://play.google.com/store/apps/details?id=com.paltalk.chat.android): Free group video calls & chat rooms;
* [DeskRoll](https://play.google.com/store/apps/details?id=com.deskroll.client1): Gives you secure remote access to computers;
* [MedikTest](https://play.google.com/store/apps/details?id=com.anisov.medical.accreditation): Preparation for accreditation of doctors;
* [KFormMaster](https://github.com/TheJuki/KFormMaster): Easily build generic forms with minimal effort.

Send me a pull request with modified README.md to get a shoutout!

## Communication
* If you need help, please use [Stack Overflow](https://stackoverflow.com/search?tab=newest&q=rendererrecyclerviewadapter).
* If you found a bug, please open [Issue](https://github.com/vivchar/RendererRecyclerViewAdapter/labels/bug).
* If you have a feature request, please open [Issue](https://github.com/vivchar/RendererRecyclerViewAdapter/labels/feature%20request).
* If you want to contribute, please submit [Pull request](https://github.com/vivchar/RendererRecyclerViewAdapter/pulls).

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
