package com.github.vivchar.example.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by Vivchar Vitaly on 10.07.19.
 */
class AvatarView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

	//Temporary workaround, you should use view's attr to circle view
	fun setUrl(url: String, isCircle: Boolean) {
		RequestOptions().let {
			it.transform(
				if (isCircle) MultiTransformation(BlurTransformation(25), CircleCrop())
				else BlurTransformation(25)
			)
			Glide.with(context)
				.asBitmap()
				.load(url)
				.apply(it)
				.into(this)
		}
	}

	fun setUrl(url: String) = setUrl(url, false)
}