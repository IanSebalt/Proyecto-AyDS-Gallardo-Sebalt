package ayds.songinfo.home.view

import ayds.songinfo.home.model.entities.Song.EmptySong
import ayds.songinfo.home.model.entities.Song
import ayds.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {
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
                                when(song.releaseDatePrecision) {
                                    "year" -> song.releaseDate + if(esbiciesto(song.releaseDate.toInt())) " (Leap year)" else " (Not a leap year)"
                                    "month" -> song.releaseDate.split("-").let {
                                        convertirAMes(it[1]) + ", " + it[0]
                                    }
                                    "day" ->  song.releaseDate.split("-").let {
                                        it[2] + "/" + it[1] + "/" + it[0]
                                    }
                                    else -> "Unknown"
                                }
            else -> "Song not found"
        }
    }

    private fun esbiciesto(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    private fun convertirAMes(mes:String):String{
        return when(mes){
            "01" -> "January"
            "02" -> "February"
            "03" -> "March"
            "04" -> "April"
            "05" -> "May"
            "06" -> "June"
            "07" -> "July"
            "08" -> "August"
            "09" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> "Unknown"
        }
    }

}