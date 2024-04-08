package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong
import ayds.songinfo.utils.UtilsInjector
import ayds.songinfo.utils.view.DateConversor


interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {
    private val dateConversor: DateConversor = UtilsInjector.dateConversor
    override fun getSongDescriptionText(song: Song): String {

        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: " +
                        dateConversor.convertDate(song.releaseDate, song.releaseDatePrecision)
            else -> "Song not found"
        }
    }

}