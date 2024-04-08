package ayds.songinfo.utils

import ayds.songinfo.utils.navigation.NavigationUtils
import ayds.songinfo.utils.navigation.NavigationUtilsImpl
import ayds.songinfo.utils.view.ImageLoader
import ayds.songinfo.utils.view.ImageLoaderImpl
import ayds.songinfo.utils.view.DateConversor
import ayds.songinfo.utils.view.DateConversorImpl
import com.squareup.picasso.Picasso

object UtilsInjector {

    val imageLoader: ImageLoader = ImageLoaderImpl(Picasso.get())

    val navigationUtils: NavigationUtils = NavigationUtilsImpl()

    val dateConversor: DateConversor = DateConversorImpl()
}