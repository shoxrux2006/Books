package uz.gita.bookappShoxruh.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


@Composable
fun GlideUtilUri(uri: Uri, @DrawableRes default: Int): MutableState<Bitmap?> {
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)
    Glide.with(LocalContext.current).asBitmap().placeholder(default).load(uri)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                TODO("Not yet implemented")
            }


        })
    return bitmap!!
}

@Composable
fun GlideUtilURL(url: String, @DrawableRes default: Int): MutableState<Bitmap?> {
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)
    Glide.with(LocalContext.current).asBitmap().placeholder(default).load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                TODO("Not yet implemented")
            }


        })
    return bitmap!!
}